package com.accountmanagement.infra.adapter.bill.rest.v1;

import com.accountmanagement.domain.bill.model.BillDto;
import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import com.accountmanagement.domain.bill.usecase.CreateBillUseCase;
import com.accountmanagement.domain.bill.usecase.GetAllByManagerIdAndBillStatusUseCase;
import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.infra.adapter.bill.rest.model.request.CreateBillRequest;
import com.accountmanagement.infra.adapter.bill.rest.model.response.BillResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bills")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BillController {

    private final UseCaseHandler<BillDto, CreateBillUseCase> createBillUseCaseHandler;
    private final UseCaseHandler<List<BillDto>, GetAllByManagerIdAndBillStatusUseCase> getAllByManagerIdAndBillStatusUseCaseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody CreateBillRequest request) {
        BillDto bill = createBillUseCaseHandler.handle(request.toModel());
        return ResponseEntity.of(Optional.of(BillResponse.toModel(bill)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BillResponse>> getAllByManagerIdAndBillStatus(
        @RequestParam UUID managerId, @RequestParam BillStatus status) {
        List<BillResponse> bills = getAllByManagerIdAndBillStatusUseCaseHandler.handle(
                GetAllByManagerIdAndBillStatusUseCase.builder().managerId(managerId).status(status).build()).stream()
            .map(BillResponse::toModel).collect(Collectors.toList());
        return ResponseEntity.of(Optional.of(bills));
    }
}
