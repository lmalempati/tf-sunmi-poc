package com.sunmi.msrtests.data.converter;

import androidx.room.TypeConverter;

import com.sunmi.msrtests.utilities.Enums;


public class IndustryTypeConverter {
    @TypeConverter
    public Enums.IndustryType IndustryTypeFromString(String value) {
        return Enums.IndustryType.from(value);
    }

    @TypeConverter
    public String IndustryTypeToString(Enums.IndustryType enumValue) {
        if(enumValue == null) {
            return "";
        }
        return enumValue.value;
    }
}
