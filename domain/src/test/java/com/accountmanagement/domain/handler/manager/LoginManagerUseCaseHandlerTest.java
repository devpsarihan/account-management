package com.accountmanagement.domain.handler.manager;

import static com.accountmanagement.constant.DomainTestConstant.MOCK_EMAIL;
import static com.accountmanagement.constant.DomainTestConstant.MOCK_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.accountmanagement.domain.adapter.ManagerFakeAdapter;
import com.accountmanagement.domain.manager.handler.LoginManagerUseCaseHandler;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import org.junit.Test;

public class LoginManagerUseCaseHandlerTest {

    private final ManagerFakeAdapter managerFakeAdapter = new ManagerFakeAdapter();
    private final LoginManagerUseCaseHandler useCaseHandler = new LoginManagerUseCaseHandler(managerFakeAdapter);

    @Test
    public void testHandle_WhenGivenUseCase_ShouldReturnManagerDto() {
        LoginManagerUseCase useCase = LoginManagerUseCase.builder()
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
        assertEquals(useCase.getEmail(), managerDto.getEmail());
    }
}
