package dev.shade.domain.user;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(toBuilder = true)
public class Permission implements Serializable {

    String name;
}
