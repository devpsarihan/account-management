package com.accountmanagement.domain.manager.handler;

import com.accountmanagement.domain.common.handler.UseCaseHandler;
import com.accountmanagement.domain.common.util.DomainComponent;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.port.ManagerPort;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class RegisterManagerUseCaseHandler implements UseCaseHandler<ManagerDto, RegisterManagerUseCase> {

    private final ManagerPort managerPort;

    @Override
    public ManagerDto handle(final RegisterManagerUseCase useCase) {
        return managerPort.registerManager(useCase);
    }
}
