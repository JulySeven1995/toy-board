package com.board.common.converter;

import com.board.common.type.CommonType;
import com.board.common.util.EnumValueConverterUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

public class AbstractEnumAttributeConverter<E extends Enum<E> & CommonType> implements AttributeConverter<E, String> {

    private final Class<E> targetEnumClass;

    private final boolean nullable;

    private final String enumName;

    public AbstractEnumAttributeConverter(Class<E> targetEnumClass, boolean nullable, String enumName) {

        this.targetEnumClass = targetEnumClass;
        this.nullable = nullable;
        this.enumName = enumName;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {

        if (!nullable && attribute == null) {

            throw new IllegalArgumentException(String.format("[%s] Can Not Save As `NULL`.", enumName));
        }
        return EnumValueConverterUtils.toCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {

        if (!nullable && StringUtils.isEmpty(dbData)) {

            throw new IllegalArgumentException(String.format("[%s] Saved As `NULL` Or `Empty[%s]` On Database.", enumName, dbData));
        }

        return EnumValueConverterUtils.ofCode(targetEnumClass, dbData);
    }
}