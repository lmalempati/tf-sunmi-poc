package com.sunmi.msrtests.utilities;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.HashMap;

public class Enums {


    public enum TestCaseExecutionStatus {
        @SerializedName("0")
        Unknown(0),
        @SerializedName("1")
        Running(1),
        @SerializedName("2")
        Passed(2),
        @SerializedName("3")
        Failed(3),
        @SerializedName("4")
        Pending(4);
        public final int value;

        TestCaseExecutionStatus(int intValue) { value = intValue; }

        private static final Map<Integer, TestCaseExecutionStatus> _map = new HashMap<Integer, TestCaseExecutionStatus>();
        static
        {
            for(TestCaseExecutionStatus profile : TestCaseExecutionStatus.values())
                _map.put(profile.value, profile);
        }
        public static TestCaseExecutionStatus from(int intValue)
        {
            return _map.get(intValue);
        }
    }

    public enum IndustryType {
        @SerializedName("Moto")
        Moto("Moto"),

        @SerializedName("Retail")
        Retail("Retail"),

        @SerializedName("Restaurant")
        Restaurant("Restaurant"),

        @SerializedName("Retail or Restaurant")
        RetailOrRestaurant("Retail or Restaurant");

        public final String value;

        IndustryType(String textValue) { value = textValue; }

        private static final Map<String, IndustryType> _map = new HashMap<String, IndustryType>();
        static
        {
            for(IndustryType profile : IndustryType.values())
                _map.put(profile.value, profile);
        }
        public static IndustryType from(String textValue)
        {
            return _map.get(textValue);
        }
    }


    public enum TransactionType {

        @SerializedName("Authorization")
        Authorization("Authorization"),

        @SerializedName("Completion")
        Completion("Completion"),

        @SerializedName("Sale")
        Sale("Sale"),

        @SerializedName("Refund")
        Refund("Refund"),

        @SerializedName("Verification")
        Verification("Verification"),

        @SerializedName("BalanceInquiry")
        BalanceInquiry("BalanceInquiry");

        public final String value;

        TransactionType(String textValue) { value = textValue; }

        private static final Map<String, TransactionType> _map = new HashMap<String, TransactionType>();
        static
        {
            for(TransactionType profile : TransactionType.values())
                _map.put(profile.value, profile);
        }
        public static TransactionType from(String textValue)
        {
            return _map.get(textValue);
        }
    }

    public enum CardType {

        @SerializedName("Amex")
        Amex("Amex"),

        @SerializedName("Visa")
        Visa("Visa"),

        @SerializedName("MasterCard")
        MasterCard("MasterCard"),

        @SerializedName("Diners")
        Diners("Diners"),

        @SerializedName("Discover")
        Discover("Discover"),

        @SerializedName("JCB")
        JCB("JCB");

        public final String value;

        CardType(String textValue) { value = textValue; }

        private static final Map<String, CardType> _map = new HashMap<String, CardType>();
        static
        {
            for(CardType profile : CardType.values())
                _map.put(profile.value, profile);
        }
        public static CardType from(String textValue)
        {
            return _map.get(textValue);
        }
    }

    public enum CardEntryMode {

        @SerializedName("Keyed")
        Keyed("Keyed"),

        @SerializedName("Swiped")
        Swiped("Swiped"),

        @SerializedName("Contactless")
        Contactless("Contactless");

        public final String value;

        CardEntryMode(String textValue) { value = textValue; }

        private static final Map<String, CardEntryMode> _map = new HashMap<String, CardEntryMode>();
        static
        {
            for(CardEntryMode profile : CardEntryMode.values())
                _map.put(profile.value, profile);
        }
        public static CardEntryMode from(String textValue)
        {
            return _map.get(textValue);
        }
    }

}