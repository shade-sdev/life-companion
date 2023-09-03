package dev.shade.shared.security.permissionevalutor.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccessType {

    MINE_CREATE("MINE_CREATE"),
    MINE_READ("MINE_READ"),
    MINE_UPDATE("MINE_UPDATE"),
    MINE_DELETE("MINE_DELETE"),
    MINE_MANAGEMENT("MINE_MANAGEMENT"),

    OTHER_CREATE("OTHER_CREATE"),
    OTHER_READ("OTHER_READ"),
    OTHER_UPDATE("OTHER_UPDATE"),
    OTHER_DELETE("OTHER_DELETE"),
    OTHER_MANAGEMENT("OTHER_MANAGEMENT");

    private final String permission;
}
