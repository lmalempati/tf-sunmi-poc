package com.sunmi.msrtests.workers;

import com.sunmi.msrtests.configuration.GlobalState;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.data.repository.TestCaseRepository;
import com.sunmi.msrtests.data.utilities.TxRequestParamHelper;
import com.sunmi.msrtests.workers.TestcaseHandler;

import java.util.HashMap;

import fdrc.base.Request;
import fdrc.base.Response;
import fdrc.client.Client;
import fdrc.utils.Utils;

public class SaleTestcaseHandler implements TestcaseHandler {
    private TestCaseStatus testCaseStatus =null;
    TestCaseRepository testcaseRepository = new TestCaseRepository(GlobalState.getInstance().getContext());
    public SaleTestcaseHandler(TestCaseStatus testcase){
        this.testCaseStatus = testcase;
    }
    @Override
    public TestCaseStatus executeTestcase() {
        // Get the testcase current status
        FDRCTestCase testCase = testcaseRepository.getFDRCTestcaseForTestcaseNumber(testCaseStatus.testCaseNumber);
        testCase.syncDescriptionFields();
        //testCaseStatus = testcaseRepository.getTestcaseStatusForTestcaseNumber(testCase.testCaseNumber);


        // Execute the testcase
        Request request = new Request();
        request.txnCrncy = testCase.transCurrency;
        request.termLocInd = TxRequestParamHelper.getTermLocInd(testCase);
        request.acctNum = testCase.accountNumber;
        request.groupID= testCase.groupID;
        request.aci = testCase.aci;
        request.cardType  = testCase.cardType.value;

        request.merchantMID=testCase.merchantId;
        request.merchCatCode =  "5967";
        request.termCatCode = "00";// 00 for all transactions, Refer the specification
        request.pymtType = testCase.paymentType;
        request.txnType = testCase.transactionType.value;
        request.cardExpiryDate = "20251231";
        request.ccvInd = testCase.cCVIndicator;
        request.avsBillingAddr = testCase.aVSBillingAddress;
        request.ccvData = testCase.cCVData;
        request.avsBillingPostalCode= testCase.aVSBillingPostalCode;
        request.posEntryMode = "011";
        request.cardCaptCap = "0";// should be 1 , refer specification
        request.txnAmt = Double.parseDouble( testCase.transactionAmount);
        request.termEntryCapablt = "03";
        request.posCondCode = "08";
        request.custSvcPhoneNumber= "40489000000";
        request.merchName = testCase.merchantName;
        request.merchAddr = testCase.merchantAddress;
        request.merchCity = testCase.merchantCity;
        request.merchState = testCase.merchantState;
        request.merchPostalCode = testCase.merchantPostalCode ;
        request.partAuthrztnApprvlCapablt = "1";
        Client client = new Client();
        Response h = client.processRequest(request);

        return null;
    }
}
