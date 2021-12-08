package com.sunmi.msrtests.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;

import java.util.List;

@Dao
public interface TestCaseStatusDao {

    @Query("SELECT * FROM TestCaseStatus")
    List<TestCaseStatus> getAllTestCaseStatuses();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTestCaseStatuses(List<TestCaseStatus> testcases);

    @Query("DELETE  FROM TestCaseStatus WHERE testCaseNumber <> null")
    void clearAllTestCaseStatuses();

    @Query("SELECT * FROM TestCaseStatus WHERE industry = :industryValue")
    List<TestCaseStatus> getTestcaseForIndustry(String industryValue);


    @Query("SELECT * FROM TestCaseStatus WHERE cardType = :cardTypeValue")
    List<TestCaseStatus> getTestcaseForCardType(String cardTypeValue);

    @Query("SELECT COUNT(testCaseNumber) FROM TestCaseStatus WHERE isSelected = 1")
    Integer getTotalActiveTestcase();

    @Query("UPDATE TestCaseStatus SET executionStatus = :executionStatus AND isSelected = 0")
    void resetAllSelectedTestcase(int executionStatus);

    @Query("SELECT * FROM TestCaseStatus WHERE industry = :industryValue AND isSelected = 1")
    List<TestCaseStatus> getCurrentlyRunningTestcaseForIndustry(String industryValue);


    @Query("SELECT * FROM TestCaseStatus WHERE cardType = :cardTypeValue AND isSelected = 1")
    List<TestCaseStatus> getCurrentlyRunningTestcaseForCardType(String cardTypeValue);


    @Query("SELECT * FROM TestCaseStatus WHERE testCaseNumber = :testcaseNo LIMIT 1")
    TestCaseStatus getTestCaseStatusForTestcaseNo(String testcaseNo);

    @Update
    int updateTestCaseStatus(TestCaseStatus testCaseStatus);

    @Query("SELECT DISTINCT industry FROM TestCaseStatus")
    List<String> getDistinctIndustryTypes();


    @Query("SELECT DISTINCT cardType FROM TestCaseStatus")
    List<String> getDistinctCardTypes();

    @Query("SELECT DISTINCT industry FROM TestCaseStatus WHERE isSelected=1")
    List<String> getActiveDistinctIndustryTypes();


    @Query("SELECT DISTINCT cardType FROM TestCaseStatus  WHERE isSelected=1")
    List<String> getActiveDistinctCardTypes();

}
