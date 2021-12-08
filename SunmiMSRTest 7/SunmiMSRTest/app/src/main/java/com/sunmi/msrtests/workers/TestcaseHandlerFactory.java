package com.sunmi.msrtests.workers;

import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.workers.SaleTestcaseHandler;

public class TestcaseHandlerFactory {
    private static TestcaseHandlerFactory _singleton;

    public static TestcaseHandlerFactory getInstance (){
        if(_singleton == null){
            _singleton = new TestcaseHandlerFactory();
        }
        return _singleton;
    }

    public TestcaseHandlerFactory() {
    }

    public TestcaseHandler GetTestcaseHandler(TestCaseStatus testCase) {

        if(testCase !=null)
        {
            switch (testCase.transactionType)
            {
                case Authorization:
                    break;
                case Completion:
                    break;
                case Sale:
                    return new SaleTestcaseHandler(testCase);
                case Refund:
                    break;
                case Verification:
                    break;
                case BalanceInquiry:
                    break;
            }
        }
        throw new IllegalArgumentException("Unknown Handler class");
    }

    public static void dispose() {
        _singleton = null;
    }

}
