//package com.a702.finafanbe.core.savings.dto.apidto;
//
//import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//public class ApiCreateSavingAccountRequest {
//
//    private BaseRequestHeaderIncludeUserKey header;
//    private String accountTypeUniqueNo;
//
//    public static ApiCreateSavingAccountRequest create(CreateSavingAccountRequest request) {
//        return new ApiCreateSavingAccountRequest(request.getHeader(), request.getAccountTypeUniqueNo());
//    }
//}