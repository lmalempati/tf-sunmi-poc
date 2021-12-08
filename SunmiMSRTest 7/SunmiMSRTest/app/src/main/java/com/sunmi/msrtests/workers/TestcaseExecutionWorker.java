package com.sunmi.msrtests.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.utilities.Constants;
import com.sunmi.msrtests.utilities.Enums;

public class TestcaseExecutionWorker extends Worker {
    private TestcaseHandler _testcaseHandler = null;
    public TestcaseExecutionWorker(TestCaseStatus testcaseToExecute, @NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        _testcaseHandler = TestcaseHandlerFactory.getInstance().GetTestcaseHandler(testcaseToExecute);
    }


    @NonNull
    @Override
    public Result doWork() {
        TestCaseStatus result = _testcaseHandler.executeTestcase();
        Data.Builder builder = new Data.Builder();
        builder.putString(Constants.KeyTestCaseNumber,result.testCaseNumber);
        builder.putInt(Constants.KeyTestCaseExecutionStatus,result.executionStatus.value);
        Data output = builder.build();
        if(result.executionStatus == Enums.TestCaseExecutionStatus.Pending  || result.executionStatus == Enums.TestCaseExecutionStatus.Unknown )
            return Result.retry();
        return Result.success(output);
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
