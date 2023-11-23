package com.accountmanagement.domain.manager.port;

import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.usecase.GetManagerByIdUseCase;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.domain.manager.usecase.UpdateManagerUsedCreditLimitUseCase;

public interface ManagerPort {

    ManagerDto registerManager(final RegisterManagerUseCase useCase);

    ManagerDto loginManager(final LoginManagerUseCase useCase);

    ManagerDto getManagerById(final GetManagerByIdUseCase useCase);

    void updateManagerUsedCreditLimit(final UpdateManagerUsedCreditLimitUseCase useCase);
}
