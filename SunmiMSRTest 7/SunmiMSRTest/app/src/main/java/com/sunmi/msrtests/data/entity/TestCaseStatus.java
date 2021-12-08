package com.sunmi.msrtests.data.entity;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.sunmi.msrtests.data.converter.CardTypeConverter;
import com.sunmi.msrtests.data.converter.IndustryTypeConverter;
import com.sunmi.msrtests.data.converter.TestCaseExecutionStatusConverter;
import com.sunmi.msrtests.data.converter.TransactionTypeConverter;
import com.sunmi.msrtests.utilities.Enums;
import com.sunmi.msrtests.utilities.SharedPreference;

@Entity(primaryKeys = {"testCaseNumber"})
public class TestCaseStatus {

    @SerializedName("Test Case Number")
    @NonNull
    public String testCaseNumber;

    @SerializedName("Dependent Testcase Number")
    public String dependentTestCaseNumber;

    @SerializedName("Industry")
    @TypeConverters(IndustryTypeConverter.class)
    public Enums.IndustryType industry;

    @SerializedName("Card Type")
    @TypeConverters(CardTypeConverter.class)
    public Enums.CardType cardType;

    @SerializedName("TestCaseExecutionStatus")
    @TypeConverters(TestCaseExecutionStatusConverter.class)
    public Enums.TestCaseExecutionStatus executionStatus;

    @SerializedName("Transaction Type")
    @TypeConverters(TransactionTypeConverter.class)
    public Enums.TransactionType transactionType;

    @SerializedName("IsSelected")
    public Boolean isSelected;

    @SerializedName("ErrorIfAny")
    public String errorIfAny;

    public Boolean getDisplaySelection(){
        boolean val = SharedPreference.read(SharedPreference.keyIsTestcaseExecutionInProgress,false);
        return (!val);
    }

    public static  TestCaseStatus getTestCaseStatus(FDRCTestCase fdrcTest){
        TestCaseStatus newObj = new TestCaseStatus();
        newObj.executionStatus = Enums.TestCaseExecutionStatus.Unknown;
        newObj.testCaseNumber = fdrcTest.testCaseNumber;
        newObj.cardType = fdrcTest.cardType;
        newObj.industry = fdrcTest.industry;
        newObj.errorIfAny="";
        newObj.isSelected = false;
        newObj.transactionType = fdrcTest.transactionType;
        newObj.dependentTestCaseNumber = fdrcTest.dependentTestCaseNumber;
        return  newObj;
    }
}
