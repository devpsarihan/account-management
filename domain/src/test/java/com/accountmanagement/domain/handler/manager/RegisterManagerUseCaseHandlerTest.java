package com.accountmanagement.domain.handler.manager;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_EMAIL;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_FIRST_NAME;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_LAST_NAME;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.accountmanagement.domain.adapter.ManagerFakeAdapter;
import com.accountmanagement.domain.manager.handler.RegisterManagerUseCaseHandler;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import org.junit.Test;

public class RegisterManagerUseCaseHandlerTest {

    private final ManagerFakeAdapter managerFakeAdapter = new ManagerFakeAdapter();
    private final RegisterManagerUseCaseHandler useCaseHandler = new RegisterManagerUseCaseHandler(managerFakeAdapter);

    @Test
    public void testHandle_WhenGivenUseCase_ShouldReturnManagerDto() {
        RegisterManagerUseCase useCase = RegisterManagerUseCase.builder()
            .firstName(MOCK_FIRST_NAME)
            .lastName(MOCK_LAST_NAME)
            .email(MOCK_EMAIL)
            .password(MOCK_PASSWORD)
            .build();
        ManagerDto managerDto = useCaseHandler.handle(useCase);

        assertNotNull(managerDto);
        assertNotNull(managerDto.getFirstName());
        assertNotNull(managerDto.getLastName());
        assertNotNull(managerDto.getMaxCreditLimit());
        assertNotNull(managerDto.getUsedCreditLimit());
        assertNotNull(managerDto.getRole());
        assertNotNull(managerDto.getToken());
        assertEquals(useCase.getFirstName(), managerDto.getFirstName());
        assertEquals(useCase.getLastName(), managerDto.getLastName());
        assertEquals(useCase.getEmail(), managerDto.getEmail());
    }
}
