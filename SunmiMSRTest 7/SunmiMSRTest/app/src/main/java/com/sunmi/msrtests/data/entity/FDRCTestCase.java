package com.sunmi.msrtests.data.entity;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.sunmi.msrtests.data.converter.CardEntryModeConverter;
import com.sunmi.msrtests.data.converter.CardTypeConverter;
import com.sunmi.msrtests.data.converter.IndustryTypeConverter;
import com.sunmi.msrtests.data.converter.TransactionTypeConverter;
import com.sunmi.msrtests.data.utilities.TxRequestParamHelper;
import com.sunmi.msrtests.utilities.Enums;

import java.util.HashMap;

@TypeConverters({IndustryTypeConverter.class,CardTypeConverter.class,TransactionTypeConverter.class,CardEntryModeConverter.class})
@Entity(primaryKeys = {"testCaseNumber"})
public class FDRCTestCase {

    @SerializedName("Payment Type")
    public String paymentType;

    @SerializedName("Industry")
    @TypeConverters(IndustryTypeConverter.class)
    public Enums.IndustryType industry;

    @SerializedName("Group ID")
    @NonNull
    public String groupID;

    @SerializedName("Test Case Number")
    @NonNull
    public String testCaseNumber;


    @SerializedName("Dependent Testcase Number")
    public String dependentTestCaseNumber;

    @SerializedName("Card Type")
    @TypeConverters(CardTypeConverter.class)
    public Enums.CardType cardType;

    @SerializedName("Transaction Type")
    @TypeConverters(TransactionTypeConverter.class)
    public Enums.TransactionType transactionType;


    @SerializedName("Entry Mode")
    @TypeConverters(CardEntryModeConverter.class)
    public Enums.CardEntryMode entryMode;


    @SerializedName("Account Number ")
    public String accountNumber;

    @SerializedName("Transaction Amount")

    public String transactionAmount;
    @SerializedName("Description")

    public String description;
    @SerializedName("AVS Billing Address")

    public String aVSBillingAddress;
    @SerializedName("AVS BillingPostal Code")

    public String aVSBillingPostalCode;
    @SerializedName("AVS Result Code")

    public String aVSResultCode;
    @SerializedName("CCV Indicator")

    public String cCVIndicator;
    @SerializedName("CCV Data")

    public String cCVData;
    @SerializedName("CCV Result Code")

    public String cCVResultCode;
    @SerializedName("Trans.Currency")

    public String transCurrency;
    @SerializedName("NON US Merchant")

    public String nONUSMerchant;
    @SerializedName("Merchant Country Code")

    public String merchantCountryCode;
    @SerializedName("Response Code")

    public String responseCode;
    @SerializedName("EBT Type ")

    public String eBTType;
    @SerializedName("Check Service Provider")

    public String checkServiceProvider;
    @SerializedName("Check Entry Mode")

    public String checkEntryMode;
    @SerializedName("MICR")

    public String micr;
    @SerializedName("Driver's License")

    public String driverSLicense;
    @SerializedName("Driver LicenseDOB")

    public String driverLicenseDOB;
    @SerializedName("StateCode")

    public String stateCode;
    @SerializedName("EncryptionType")

    public String encryptionType;
    @SerializedName("TokenType")

    public String tokenType;
    @SerializedName("CertificationType")

    public String certificationType;
    @SerializedName("MCC")

    public String mcc;
    @SerializedName("Bill Payment Indicator")

    public String billPaymentIndicator;
    @SerializedName("Additional Amount")

    public String additionalAmount;
    @SerializedName("Additional Amount Type")

    public String additionalAmountType;
    @SerializedName("DCC Indicator")

    public String dCCIndicator;
    @SerializedName("Cardholder Currency Code")

    public String cardholderCurrencyCode;
    @SerializedName("DCC Rate")

    public String dCCRate;
    @SerializedName("Payment Type Indicator")

    public String paymentTypeIndicator;
    @SerializedName("Tax Indicator")

    public String taxIndicator;
    @SerializedName("Item SeqNo")

    public String itemSeqNo;
    @SerializedName("Deferred Payment Plan")

    public String deferredPaymentPlan;
    @SerializedName("Business Payment Type")

    public String businessPaymentType;


    @Ignore
    public String merchantId="";

    @Ignore
    public String merchantName="";

    @Ignore
    public String merchantAddress="";

    @Ignore
    public String merchantState="";
    @Ignore
    public String merchantCity="";

    @Ignore
    public String merchantPostalCode="";

    @Ignore
    public String aci="";

    public void syncDescriptionFields(){
        HashMap<String,String> merchantKeyValues = TxRequestParamHelper.getMerchantXMLValuesFromDescription(this);
        if(merchantKeyValues!=null && !merchantKeyValues.isEmpty()) {
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantIdKey))
                this.merchantId = merchantKeyValues.get(TxRequestParamHelper.merchantIdKey);
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantNameKey))
                this.merchantName = merchantKeyValues.get(TxRequestParamHelper.merchantNameKey);
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantAddressKey))
                this.merchantAddress = merchantKeyValues.get(TxRequestParamHelper.merchantAddressKey);
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantCityKey))
                this.merchantCity = merchantKeyValues.get(TxRequestParamHelper.merchantCityKey);
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantStateKey))
                this.merchantState = merchantKeyValues.get(TxRequestParamHelper.merchantStateKey);
            if(merchantKeyValues.containsKey(TxRequestParamHelper.merchantPostalCodeKey))
                this.merchantPostalCode = merchantKeyValues.get(TxRequestParamHelper.merchantPostalCodeKey);
        }
        this.aci = TxRequestParamHelper.getAci(this);
    }

    public TestCaseStatus getTestCaseStatus(){
        return TestCaseStatus.getTestCaseStatus(this);
    }


}
