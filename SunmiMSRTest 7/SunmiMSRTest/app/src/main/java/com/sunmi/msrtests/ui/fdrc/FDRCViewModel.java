package com.sunmi.msrtests.ui.fdrc;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunmi.msrtests.R;
import com.sunmi.msrtests.configuration.GlobalState;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.repository.TestCaseRepository;
import com.sunmi.msrtests.model.TestcaseGroup;
import com.sunmi.msrtests.utilities.JSONUtilities;
import com.sunmi.msrtests.utilities.SharedPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FDRCViewModel extends ViewModel {



    private List<TestcaseGroup> _currentTestGroups = null;
    private MutableLiveData<Boolean> _isBusy= new MutableLiveData<Boolean>();;
    public MutableLiveData<Boolean> getIsBusy() {
        return _isTestInProgress;
    }
    public void setIsBusy(boolean value) {
        _isTestInProgress.postValue(value);
    }


    private MutableLiveData<Boolean> _isTestInProgress= new MutableLiveData<Boolean>();;
    public MutableLiveData<Boolean> getIsTestInProgress() {
        return _isTestInProgress;
    }
    public void setIsTestInProgress(boolean value) {
        _isTestInProgress.postValue(value);
    }

    public FDRCViewModel() {
        SharedPreference.init(GlobalState.getInstance().getContext());
        setIsBusy(false);
        Boolean isTestsLoaded = SharedPreference.read(SharedPreference.keyTestCasesLoaded,false);
        setIsDataSynced(isTestsLoaded);
    }

    private MutableLiveData<Boolean> _isDataSynced = new MutableLiveData<Boolean>();;
    public MutableLiveData<Boolean> getIsDataSynced() {
        return _isDataSynced;
    }
    public void setIsDataSynced(boolean value) {
        _isDataSynced.postValue(value);
    }

    private MutableLiveData<Boolean> _isFilterChanged= new MutableLiveData<Boolean>();;
    public MutableLiveData<Boolean> getIsFilterChanged() {
        return _isFilterChanged;
    }
    public void setIsFilterChanged(boolean value) {
        _isFilterChanged.postValue(value);
    }

    public List<TestcaseGroup>getTestCaseGroups() {
        this.setIsBusy(true);
        TestCaseRepository repo = new TestCaseRepository(GlobalState.getInstance().getContext());
        boolean isIndustryFilterSelected = SharedPreference.read(SharedPreference.keyIsIndustryFilterSelected,true);
        boolean isTestInProgress = SharedPreference.read(SharedPreference.keyIsTestcaseExecutionInProgress,false);
        this.getIsTestInProgress().setValue(isTestInProgress);
        if(isIndustryFilterSelected) {
            _currentTestGroups = repo.getTestcasesGroupedByIndustry();
        }else{
            _currentTestGroups = repo.getTestCasesGroupedByCardType();
        }
        this.setIsBusy(false);
        return _currentTestGroups;
    }
    public Boolean startExecuteSelectedTestcase(){
        this.setIsBusy(true);
        TestCaseRepository repo = new TestCaseRepository(GlobalState.getInstance().getContext());
        Boolean  isTestSaved = repo.saveSelectedTestcase(_currentTestGroups);
        SharedPreference.write(SharedPreference.keyIsTestcaseExecutionInProgress,isTestSaved);
        this.getIsTestInProgress().setValue(isTestSaved);
        this.setIsBusy(false);
        return isTestSaved;
    }
    public void resetActiveTests(){
        this.setIsBusy(true);
        TestCaseRepository repo = new TestCaseRepository(GlobalState.getInstance().getContext());
        Boolean  isTestStopped = repo.resetAllActiveTestcase();
        SharedPreference.write(SharedPreference.keyIsTestcaseExecutionInProgress,!isTestStopped);
        this.getIsTestInProgress().setValue(!isTestStopped);
        this.setIsBusy(false);
    }
    public void fetchTestCasesFromAssets()
    {
        this.setIsBusy(true);
        SharedPreference.init(GlobalState.getInstance().getContext());
        boolean isExcelTestcasesLoaded = SharedPreference.read(SharedPreference.keyTestCasesLoaded,false);
        if(!isExcelTestcasesLoaded){
            TestCaseRepository repo = new TestCaseRepository(GlobalState.getInstance().getContext());
            Set<FDRCTestCase> setOfTestcasesFromExcel = JSONUtilities.getListOfJsonObjects(GlobalState.getInstance().getContext(), R.raw.testscripts_fdrc);
            List<FDRCTestCase> listOfTestcasesFromExcel = new ArrayList<FDRCTestCase>(setOfTestcasesFromExcel);
            if (repo.saveFDRCTestCaseData(listOfTestcasesFromExcel) ) {
                repo.resolveTestcaseDependencies();
                SharedPreference.write(SharedPreference.keyTestCasesLoaded, true);
                this.setIsDataSynced(true);
            }
        }
        this.setIsBusy(false);
    }
}