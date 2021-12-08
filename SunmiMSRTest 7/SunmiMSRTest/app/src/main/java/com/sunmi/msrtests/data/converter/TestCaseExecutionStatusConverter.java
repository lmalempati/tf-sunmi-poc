package com.sunmi.msrtests.data.converter;

import androidx.room.TypeConverter;

import com.sunmi.msrtests.utilities.Enums;


public class TestCaseExecutionStatusConverter {
    @TypeConverter
    public Enums.TestCaseExecutionStatus TestCaseExecutionStatusFromInt(int value) {
        return Enums.TestCaseExecutionStatus.from(value);
    }

    @TypeConverter
    public int TestCaseExecutionStatusToInt(Enums.TestCaseExecutionStatus enumValue) {
        if(enumValue == null) {
            return 0;
        }
        return enumValue.value;
    }
}
