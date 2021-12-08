package com.sunmi.msrtests.data.converter;

import androidx.room.TypeConverter;

import com.sunmi.msrtests.utilities.Enums;

public class CardTypeConverter {
    @TypeConverter
    public Enums.CardType CardTypeFromString(String value) {
        return Enums.CardType.from(value);
    }

    @TypeConverter
    public String CardTypeToString(Enums.CardType enumValue) {
        if(enumValue == null) {
            return "";
        }
        return enumValue.value;
    }
}
