package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
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
    public ResponseEntity<RetrieveProductsResponse> getDemandDepositList(
//            @AuthMember User userId
    ) {
        return demandDepositFacade.getProducts();
    }

}
