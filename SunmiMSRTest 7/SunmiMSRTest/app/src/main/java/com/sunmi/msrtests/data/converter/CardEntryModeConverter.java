package com.sunmi.msrtests.data.converter;

import androidx.room.TypeConverter;

import com.sunmi.msrtests.utilities.Enums;

public class CardEntryModeConverter {
    @TypeConverter
    public Enums.CardEntryMode CardEntryModeFromString(String value) {
        return Enums.CardEntryMode.from(value);
    }

    @TypeConverter
    public String CardEntryModeToString(Enums.CardEntryMode enumValue) {
        if(enumValue == null) {
            return "";
        }
        return enumValue.value;
    }
}
