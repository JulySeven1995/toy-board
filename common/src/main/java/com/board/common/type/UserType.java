package com.board.common.type;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements CommonType, GrantedAuthority {

    ADMIN("관리자", "ROLE_ADMIN"),
    GENERAL("일반 사용자", "ROLE_GENERAL"),
    DORMANT("휴면 계정", "ROLE_DORMANT"),
    ;

    private final String desc;
    private final String code;

    UserType(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    @Override
    public String getDesc() {

        return desc;
    }

    @Override
    public String getCode() {

        return code;
    }

    @Override
    public String getAuthority() {

        return code;
    }
}
