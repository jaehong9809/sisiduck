package com.a702.finafanbe.global.common.financialnetwork.util;

public class ApiConstants {

    public static final String INQUIRE_DEMAND_DEPOSIT_ACCOUNT_PATH = "/demandDeposit/inquireDemandDepositAccount";
    public static final String CREATE_DEMAND_DEPOSIT_PATH = "/demandDeposit/createDemandDeposit";
    public static final String UPDATE_TRANSFER_LIMIT_PATH = "/demandDeposit/updateTransferLimit";
    public static final String CREATE_DEPOSIT = "/demandDeposit/createDemandDepositAccount";

    public String extractApiName(String path){
        String[] splits = path.split("/");
        return splits.length > 0 ? splits[splits.length-1] : "";
    }
}
