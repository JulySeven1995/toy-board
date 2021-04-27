package com.board.common.type;

public enum UserType implements CommonType {

    ADMIN("관리자", "ADMIN"),
    GENERAL("일반 사용자", "GENERAL"),
    DORMANT("휴면 계정", "DORMANT"),
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

}
