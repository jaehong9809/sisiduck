//package com.a702.finafanbe.core.savings.dto.apidto;
//
//import com.a702.finafanbe.core.savings.dto.CreateFundingRequest;
//import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//public class ApiCreateSavingAccountRequest {
//
//    private BaseRequestHeaderIncludeUserKey Header;
//    private String accountTypeUniqueNo;
//
//    public static ApiCreateSavingAccountRequest create(CreateFundingRequest request) {
//        return new ApiCreateSavingAccountRequest(header, request.getAccountTypeUniqueNo());
//    }
//}