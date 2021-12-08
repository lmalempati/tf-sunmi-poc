package com.sunmi.msrtests.data.utilities;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.utilities.Enums;

import java.util.HashMap;
public  class TxRequestParamHelper {

    public static String merchantIdKey="mid";
    public static String merchantNameKey="MerchName";
    public static String merchantAddressKey="MerchAddr";
    public static String merchantCityKey="MerchCity";
    public static String merchantStateKey="MerchState";
    public static String merchantPostalCodeKey="MerchPostalCode";

    public static String getTermLocInd(FDRCTestCase testcase)
    {
        if(testcase == null )
            return "";
        return "0";
    }
    public static String getAci(FDRCTestCase testcase)
    {
        if(testcase == null )
            return "";
        if((testcase.transactionType== Enums.TransactionType.Sale) && (testcase.cardType == Enums.CardType.Visa)){
            return "Y";
        }
        return "";
    }
    public static HashMap<String,String> getMerchantXMLValuesFromDescription(FDRCTestCase testcase){
        HashMap<String,String> result = new HashMap<>();
        if(testcase == null || testcase.description ==null || testcase.description.trim().length() == 0)
            return null;
        String[] notes = testcase.description.split("\\.");
        for(int i=0;i<notes.length;i++)
        {
            int xmlTagStartIndex = notes[i].indexOf("Send XML Tags ");
            int xmlTagEndIndex = notes[i].indexOf(" with values");

            if(xmlTagStartIndex >= 0 && xmlTagEndIndex > xmlTagStartIndex){
                String tagsSection = notes[i].substring(xmlTagStartIndex,xmlTagEndIndex);
                String[] tagsResults = tagsSection.split(",");
                if(tagsResults.length > 0){
                    int valuesTagEndIndex = notes[i].indexOf(" respectively");
                    if( valuesTagEndIndex > xmlTagEndIndex) {
                        String valuesSection = notes[i].substring(xmlTagEndIndex, valuesTagEndIndex);
                        String[] valueResults = valuesSection.split(",");
                        if (tagsResults.length == valueResults.length) {
                            for(int j=0;j<notes.length;j++) {
                                result.put(tagsResults[j],valueResults[j]);
                            }
                            break;
                        }
                    }
                }
                break;
            }

        }
        String midValue = getMerchantId(testcase.description);
        if(midValue != null && midValue.length() >0)
        {
            result.put("mid",midValue);
        }
        if (result.isEmpty())
            return null;
        return result;
    }
    private static String getMerchantId(String description){

        if(description == null || description.trim().length() == 0)
            return "";
        String[] notes = description.split("\\.");
        String midText = "Use mid ";
        for(int i=0;i<notes.length;i++)
        {

            int startIndex = notes[i].indexOf(midText);
            if(startIndex >= 0){
                String subString = notes[i].substring(startIndex+midText.length());
                String[] results = subString.split("'");
                if(results.length > 1)
                    return results[1];
            }

        }
        return "";
    }
}
