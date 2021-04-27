package com.board.common.util;

import com.board.common.type.CommonType;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

public class EnumValueConverterUtils {

    public static <T extends Enum<T> & CommonType> T ofCode(Class<T> enumClass, String code) {

        return EnumSet.allOf(enumClass).stream()
                .filter(i -> i.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("enum=[%s], Code=[%s] Do Not Exist.", enumClass.getName(), code)));
    }

    public static <T extends Enum<T> & CommonType> String toCode(T enumValue) {

        if (enumValue == null) {

            return StringUtils.EMPTY;
        }

        return enumValue.getCode();
    }
}