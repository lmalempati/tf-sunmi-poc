package com.sunmi.msrtests.data.repository;

import android.content.Context;
import com.sunmi.msrtests.data.AppDatabase;
import com.sunmi.msrtests.data.dao.FDRCTestCaseDao;
import com.sunmi.msrtests.data.dao.TestCaseStatusDao;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.model.TestcaseGroup;
import com.sunmi.msrtests.utilities.Enums;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TestCaseRepository {
    private FDRCTestCaseDao _fdrcTestCaseDao;
    private TestCaseStatusDao _testCaseStatusDao;
    private ExecutorService _executorService;

    public TestCaseRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        _fdrcTestCaseDao = db.fdrcTestCaseDao();
        _testCaseStatusDao = db.testCaseStatusDao();
        _executorService = Executors.newSingleThreadExecutor();
    }
    public Boolean isTestcasesAlreadySelectedByUser(){
        Boolean result = false;
        try {
            Integer count = _testCaseStatusDao.getTotalActiveTestcase();
            result = (count > 0 ) ;
        } catch (Exception executionException) {
            result = false;
        }
        return result;
    }
    public List<TestcaseGroup> getTestCasesGroupedByCardType() {
        return getTestcaseGroupedByIndustryOrCardType(false);
    }
    public List<TestcaseGroup> getTestcasesGroupedByIndustry() {
        return getTestcaseGroupedByIndustryOrCardType(true);
    }
    public Boolean saveSelectedTestcase(List<TestcaseGroup> listOfGroup){
        if(listOfGroup == null || listOfGroup.isEmpty())
            return false;
        List<TestCaseStatus> listToInsert = new ArrayList<TestCaseStatus>();
        for (TestcaseGroup group:listOfGroup) {
            listToInsert.addAll(group.testCases);
        }
        return insertSelectedTestcase(listToInsert);
    }
    public Boolean resetAllActiveTestcase()
    {
        Future<Boolean> daoResult=null;
        Boolean result = false;
        try {
            daoResult = _executorService.submit(() -> {
                _testCaseStatusDao.resetAllSelectedTestcase(Enums.TestCaseExecutionStatus.Unknown.value);
                return true;
            });
            result = daoResult.get();
        } catch (Exception executionException) {
            result = false;
        }
        return result;
    }
    private Boolean insertSelectedTestcase(List<TestCaseStatus> listOfTestCases){
        Future<Boolean> daoResult=null;
        Boolean result = false;
        try {
            daoResult = _executorService.submit(() -> {
                _testCaseStatusDao.insertAllTestCaseStatuses(listOfTestCases);
                return true;
            });
            result = daoResult.get();
        } catch (Exception executionException) {
            result = false;
        }
        return result;
    }
    public Boolean saveFDRCTestCaseData(List<FDRCTestCase> listOfTestCases){
        Future<Boolean> daoResult=null;
        Boolean result = false;
        try {
            daoResult = _executorService.submit(() -> {

                _fdrcTestCaseDao.insertAllFDRCTestCases(listOfTestCases);
                List<TestCaseStatus> itemStatusList = new ArrayList<TestCaseStatus>();
                for (FDRCTestCase objFdrc : listOfTestCases) {
                    itemStatusList.add(objFdrc.getTestCaseStatus());
                }
                _testCaseStatusDao.insertAllTestCaseStatuses(itemStatusList);
                return true;
            });
            result = daoResult.get();
        } catch (Exception executionException) {
            result = false;
        }
        return result;
    }
    private String getNextTestcaseNumber(String testcaseNumber)
    {
        if(testcaseNumber == null || testcaseNumber.trim().isEmpty())
            return null;
        try {
            Long testcaseNo = Long.parseLong(testcaseNumber);
            testcaseNo++;
            return String.valueOf(testcaseNo);

        }catch(Exception e){

        }
        return null;
    }
    public Boolean resolveTestcaseDependencies(){

        try {
            List<FDRCTestCase> allTestcase = _fdrcTestCaseDao.getAllFDRCTestCases();
            for (FDRCTestCase testcase:allTestcase) {
                FDRCTestCase fdrcTestCase = _fdrcTestCaseDao.getTestCaseByNumber(testcase.testCaseNumber);
                if(fdrcTestCase == null)
                    continue;
                if(fdrcTestCase.dependentTestCaseNumber!=null && !fdrcTestCase.dependentTestCaseNumber.isEmpty()) {
                    continue;
                }
                String nextTestcaseNumber = getNextTestcaseNumber(fdrcTestCase.testCaseNumber);
                FDRCTestCase associatedTestcase = _fdrcTestCaseDao.getTestCaseByNumber(nextTestcaseNumber);
                if(associatedTestcase != null){
                    int result1 = _fdrcTestCaseDao.updateDependentTestcase(fdrcTestCase.testCaseNumber,associatedTestcase.testCaseNumber);
                    int result2 = _fdrcTestCaseDao.updateDependentTestcase(associatedTestcase.testCaseNumber,fdrcTestCase.testCaseNumber);
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public TestCaseStatus getTestcaseStatusForTestcaseNumber(String testcaseNo){
        Future<TestCaseStatus> daoResult=null;
        TestCaseStatus result = null;
        try{
            daoResult = _executorService.submit(() -> {
                return _testCaseStatusDao.getTestCaseStatusForTestcaseNo(testcaseNo);
            });
            result = daoResult.get();
        }catch (Exception executionException) {
            result = null;
        }
        return result;
    }
    public FDRCTestCase getFDRCTestcaseForTestcaseNumber(String testcaseNo){
        Future<FDRCTestCase> daoResult=null;
        FDRCTestCase result = null;
        try{
            daoResult = _executorService.submit(() -> {
                return _fdrcTestCaseDao.getTestCaseByNumber(testcaseNo);
            });
            result = daoResult.get();
        }catch (Exception executionException) {
            result = null;
        }
        return result;
    }
    public int updateTestcaseStatus(TestCaseStatus testcaseStatus){
        Future<Integer> daoResult=null;
        Integer result = -1;
        try{
            daoResult = _executorService.submit(() -> {
                return _testCaseStatusDao.updateTestCaseStatus(testcaseStatus);
            });
            result = daoResult.get();
        }catch (Exception executionException) {
            result = -1;
        }
        return result;
    }



    private List<TestcaseGroup> getTestcaseGroupedByIndustryOrCardType(boolean isIndustry){
        Future<List<TestcaseGroup>> daoResult;
        List<TestcaseGroup> result = null;

        try {
            daoResult = _executorService.submit(() -> {
                List<TestcaseGroup> res = null;
                List<String> distinctGroupByKeys = null;
                Boolean isInProgress = this.isTestcasesAlreadySelectedByUser();
                if(isIndustry) {
                    if(isInProgress) {
                        distinctGroupByKeys = _testCaseStatusDao.getActiveDistinctIndustryTypes();
                    }else{
                        distinctGroupByKeys = _testCaseStatusDao.getDistinctIndustryTypes();
                    }
                }else {
                    if(isInProgress) {
                        distinctGroupByKeys = _testCaseStatusDao.getActiveDistinctCardTypes();
                    }else {
                        distinctGroupByKeys = _testCaseStatusDao.getDistinctCardTypes();
                    }
                }

                for (String groupByKeyValue : distinctGroupByKeys)
                {
                    if (!groupByKeyValue.trim().isEmpty() && groupByKeyValue.trim().length() > 0) {
                        if (res == null)
                            res = new ArrayList<TestcaseGroup>();
                        TestcaseGroup testGruop = new TestcaseGroup(groupByKeyValue);
                        List<TestCaseStatus> listOfTests = null;
                        if(isIndustry){
                            if(isInProgress){
                                listOfTests=_testCaseStatusDao.getCurrentlyRunningTestcaseForIndustry(groupByKeyValue);
                            }
                            else{
                                listOfTests = _testCaseStatusDao.getTestcaseForIndustry(groupByKeyValue);
                            }
                        }else{
                            if(isInProgress){
                                listOfTests=_testCaseStatusDao.getTestcaseForCardType(groupByKeyValue);
                            }
                            else{
                                listOfTests = _testCaseStatusDao.getCurrentlyRunningTestcaseForCardType(groupByKeyValue);
                            }
                        }
                        testGruop.isSelected = isInProgress;
                        testGruop.testCases.addAll(listOfTests);
                        res.add(testGruop);
                    }
                }
                return res;
            });
            result = daoResult.get();
        } catch (Exception executionException) {
        }
        return result;
    }
    public FDRCTestCase getFirstTestCase(){
        Future<List<FDRCTestCase>> daoResult=null;
        List<FDRCTestCase> result = null;
        try{
            daoResult = _executorService.submit(() -> {
                return _fdrcTestCaseDao.getAllMotoVisaFDRCTestCases("Moto","Visa");
            });
            result = daoResult.get();
        }catch (Exception executionException) {
            result = null;
            return null;
        }
        if(result != null){
            for (FDRCTestCase testcase:result
            ) {
                if(testcase.transactionType == Enums.TransactionType.Sale)
                    return testcase;
            }
        }
        return null;
    }

}
