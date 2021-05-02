package com.board.common.type;

public enum PostType implements CommonType {

    NORMAL("정상 게시글", "NORMAL"),
    SPECIAL("특별한 게시글", "SPECIAL"),
    ;

    private final String desc;
    private final String code;

    PostType(String desc, String code) {
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
