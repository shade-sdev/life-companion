package dev.shade.config.totp;

import com.atlassian.onetime.core.TOTP;
import com.atlassian.onetime.model.EmailAddress;
import com.atlassian.onetime.model.Issuer;
import com.atlassian.onetime.model.TOTPSecret;
import com.atlassian.onetime.service.DefaultTOTPService;
import com.atlassian.onetime.service.TOTPService;
import com.atlassian.onetime.service.TOTPVerificationResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.shade.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@Slf4j
public class TwoFactorAuthenticationService {

    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    private static final String DATA_URL = "data:image/png;base64";

    private static final String ISSUER = "Life Companion";

    private static final String EMAIL = "noreply@lifecompanion.com";

    private static final TOTPService TOTP_SERVICE = new DefaultTOTPService();

    public TOTPSecret generateSecret() {
        return TOTPSecret.Companion.fromBase32EncodedString(generateBase32String());
    }

    public void verify(Optional<User> user, String code) {
        boolean isTrustFactorEnabled = user.map(User::getSecurity)
                                           .map(User.Security::isTwoFactorEnabled)
                                           .orElse(false);

        if (!isTrustFactorEnabled) {
            return;
        }

        String secret = user.map(User::getSecurity)
                            .map(User.Security::getTwoFactorSecretKey)
                            .orElse(StringUtils.EMPTY);

        TOTP totpCode = new TOTP(code);
        TOTPSecret totpSecret = TOTPSecret.Companion.fromBase32EncodedString(secret);
        TOTPVerificationResult verify = TOTP_SERVICE.verify(totpCode, totpSecret);

        if (verify.isFailure()) {
            throw new AccessDeniedException("2FA code invalid");
        }
    }

    public String generateQRCodeImage(String secretKey) {
        TOTPSecret totpSecret = TOTPSecret.Companion.fromBase32EncodedString(secretKey);
        String otpAuthUri = this.generateQRCodeURI(totpSecret).toString();

        int width = 300;
        int height = 300;

        try {
            Writer writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(otpAuthUri, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageConfig config = new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);

            byte[] imageBytes = outputStream.toByteArray();
            String base64Image = Base64.encodeBase64String(imageBytes);

            return String.format("%s,%s", DATA_URL, base64Image);
        } catch (Exception e) {
            log.error("Could not generate QRCode: ", e);
        }
        return DATA_URL;
    }

    private URI generateQRCodeURI(TOTPSecret secret) {
        return TOTP_SERVICE.generateTOTPUrl(secret,
                                            new EmailAddress(EMAIL),
                                            new Issuer(ISSUER));
    }

    private String generateBase32String() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(32);

        for (int i = 0; i < 32; i++) {
            int randomIndex = random.nextInt(BASE32_CHARS.length());
            char randomChar = BASE32_CHARS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
