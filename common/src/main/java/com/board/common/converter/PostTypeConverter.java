package com.board.common.converter;

import com.board.common.type.PostType;

public class PostTypeConverter extends AbstractEnumAttributeConverter<PostType> {

    public PostTypeConverter() {

        super(PostType.class, Boolean.FALSE, PostType.class.getName());
    }
}
