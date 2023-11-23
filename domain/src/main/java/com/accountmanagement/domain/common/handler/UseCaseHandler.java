package com.accountmanagement.domain.common.handler;

import com.accountmanagement.domain.common.usecase.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
