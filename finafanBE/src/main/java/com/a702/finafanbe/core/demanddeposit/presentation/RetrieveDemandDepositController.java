package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class RetrieveDemandDepositController {

    private final DemandDepositFacade demandDepositFacade;

    @GetMapping("/products")
    public ResponseEntity<ResponseData<RetrieveProductsResponse>> getDemandDepositList(
//            @AuthMember User user
    ) {
        return ResponseUtil.success(demandDepositFacade.getProducts());
    }

}
