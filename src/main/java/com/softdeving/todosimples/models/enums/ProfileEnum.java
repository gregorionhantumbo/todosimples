package com.softdeving.todosimples.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final Integer code; // 🔹 Adicionado 'final' para garantir imutabilidade
    private final String description;

    public static ProfileEnum toEnum(Integer code) {
        if (Objects.isNull(code)) return null;

        for (ProfileEnum profile : ProfileEnum.values()) {
            if (code.equals(profile.getCode())) {
                return profile; // 🔹 Agora retorna o ProfileEnum correto
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
