package com.sunmi.msrtests.service;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sunmi.msrtests.configuration.GlobalState;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.data.repository.TestCaseRepository;
import com.sunmi.msrtests.model.TestcaseGroup;
import com.sunmi.msrtests.utilities.Constants;
import com.sunmi.msrtests.workers.TestcaseExecutionWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestcaseExecutionService {

    private TestCaseRepository _repo = new TestCaseRepository(GlobalState.getInstance().getContext());
    private static TestcaseExecutionService _singleton;

    public static TestcaseExecutionService getInstance (){
        if(_singleton == null){
            _singleton = new TestcaseExecutionService();
        }
        return _singleton;
    }
    public void execute(List<TestcaseGroup> testcaseGroupListToRun){
        List<OneTimeWorkRequest> workerList = new ArrayList<OneTimeWorkRequest>();
        androidx.work.Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        if(_repo.isTestcasesAlreadySelectedByUser()){
            // First make sure the Work manager is not running already
            // todo
            for (TestcaseGroup group:testcaseGroupListToRun) {
                for (TestCaseStatus item:group.testCases) {

                    OneTimeWorkRequest testcaseWorker =
                            new OneTimeWorkRequest.Builder(TestcaseExecutionWorker.class)
                                    .setConstraints(constraints)
                                    .addTag(item.testCaseNumber)
                                    .setBackoffCriteria(BackoffPolicy.LINEAR,Constants.TestcaseRetryIntervalInMinutes,
                                            TimeUnit.MINUTES)
                                    .build();
                    workerList.add(testcaseWorker);

                }
            }
            if(!workerList.isEmpty()) {
                WorkManager.getInstance(GlobalState.getInstance().getContext()).enqueueUniqueWork(Constants.TagTestcaseExecution,ExistingWorkPolicy.KEEP,workerList);
            }
        }
    }

}
