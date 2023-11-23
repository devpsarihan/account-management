package com.accountmanagement.domain.adapter;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_MANAGER_ID;
import static com.accountmanagement.constant.DomainTestConstant.buildManagerDto;

import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.port.ManagerPort;
import com.accountmanagement.domain.manager.usecase.GetManagerByIdUseCase;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.domain.manager.usecase.UpdateManagerUsedCreditLimitUseCase;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManagerFakeAdapter implements ManagerPort {

    private final Map<UUID, ManagerDto> managers = new HashMap<>();

    @Override
    public ManagerDto registerManager(final RegisterManagerUseCase useCase) {
        ManagerDto managerDto = buildManagerDto();
        managers.put(MOCK_MANAGER_ID, managerDto);
        return managerDto;
    }

    @Override
    public ManagerDto loginManager(final LoginManagerUseCase useCase) {
        ManagerDto managerDto = buildManagerDto();
        managers.put(MOCK_MANAGER_ID, managerDto);
        return managerDto;
    }

    @Override
    public ManagerDto getManagerById(final GetManagerByIdUseCase useCase) {
        return managers.get(useCase.getId());
    }

    @Override
    public void updateManagerUsedCreditLimit(final UpdateManagerUsedCreditLimitUseCase useCase) {

    }
}