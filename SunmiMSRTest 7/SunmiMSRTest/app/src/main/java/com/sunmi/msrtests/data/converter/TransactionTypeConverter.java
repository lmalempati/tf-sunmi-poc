package com.sunmi.msrtests.data.converter;

import androidx.room.TypeConverter;

import com.sunmi.msrtests.utilities.Enums;


public class TransactionTypeConverter {
    @TypeConverter
    public Enums.TransactionType TransactionTypeFromString(String value) {
        return Enums.TransactionType.from(value);
    }

    @TypeConverter
    public String TransactionTypeToString(Enums.TransactionType enumValue) {
        if(enumValue == null) {
            return "";
        }
        return enumValue.value;
    }
}
