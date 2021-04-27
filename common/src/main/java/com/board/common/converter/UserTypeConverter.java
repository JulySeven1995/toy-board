package com.board.common.converter;

import com.board.common.type.UserType;

import javax.persistence.Converter;

@Converter
public class UserTypeConverter extends AbstractEnumAttributeConverter<UserType> {

    public UserTypeConverter() {
        super(UserType.class, Boolean.FALSE, UserType.class.getName());
    }
}
