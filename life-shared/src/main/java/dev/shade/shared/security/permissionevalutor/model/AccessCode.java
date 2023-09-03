package dev.shade.shared.security.permissionevalutor.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccessCode {

    C("C"),
    R("R"),
    U("U"),
    D("D");

    private final String code;
}
