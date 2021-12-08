package com.sunmi.msrtests.model;

import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.utilities.SharedPreference;

import java.util.ArrayList;

public class TestcaseGroup {
    public String name;
    public boolean isSelected = false ;
    public Boolean getDisplaySelection(){
        boolean val = SharedPreference.read(SharedPreference.keyIsTestcaseExecutionInProgress,false);
        return (!val);
    }
    public ArrayList<TestCaseStatus> testCases= new ArrayList<TestCaseStatus>();

    public TestcaseGroup(String name)
    {
        this.name=name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name;
    }
}