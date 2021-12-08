package com.sunmi.msrtests.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;

import java.util.List;

@Dao
public interface FDRCTestCaseDao {

    @Query("SELECT * FROM FDRCTestCase")
    List<FDRCTestCase> getAllFDRCTestCases();

    @Insert
    void insertFDRCTestCase(FDRCTestCase...testcase);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFDRCTestCases(List<FDRCTestCase> testcases);

    @Query("SELECT * FROM FDRCTestCase WHERE testCaseNumber = :nextTestcaseNumber LIMIT 1")
    FDRCTestCase getTestCaseByNumber(String nextTestcaseNumber);

    @Query("SELECT * FROM FDRCTestCase WHERE industry = :industryValue AND  cardType = :cardTypeValue")
    List<FDRCTestCase> getAllMotoVisaFDRCTestCases(String industryValue, String cardTypeValue);

    @Query("SELECT DISTINCT industry FROM FDRCTestCase")
    List<String> getDistinctIndustryTypes();


    @Query("SELECT DISTINCT cardType FROM FDRCTestCase")
    List<String> getDistinctCardTypes();

    @Query("UPDATE FDRCTestCase SET dependentTestCaseNumber = :dependentTestcase  WHERE testCaseNumber = :testcaseNumber")
    int updateDependentTestcase(String testcaseNumber, String dependentTestcase);
}
