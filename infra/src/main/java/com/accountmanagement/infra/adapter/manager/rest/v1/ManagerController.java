package com.accountmanagement.infra.adapter.manager.rest.v1;

import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.infra.adapter.manager.rest.model.request.LoginManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.request.RegisterManagerRequest;
import com.accountmanagement.infra.adapter.manager.rest.model.response.ManagerResponse;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final UseCaseHandler<ManagerDto, RegisterManagerUseCase> createManagerUseCaseHandler;
    private final UseCaseHandler<ManagerDto, LoginManagerUseCase> loginManagerUseCaseHandler;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ManagerResponse> registerManager(@Valid @RequestBody RegisterManagerRequest request) {
        ManagerDto manager = createManagerUseCaseHandler.handle(request.toModel());
        return ResponseEntity.of(Optional.of(ManagerResponse.builder()
            .id(manager.getId()).token(manager.getToken()).build()));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ManagerResponse> loginManager(@Valid @RequestBody LoginManagerRequest request) {
        ManagerDto manager = loginManagerUseCaseHandler.handle(request.toModel());
        return ResponseEntity.of(Optional.of(ManagerResponse.builder()
            .id(manager.getId()).token(manager.getToken()).build()));
    }
}
