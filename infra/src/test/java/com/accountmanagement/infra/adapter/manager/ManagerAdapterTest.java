package com.accountmanagement.infra.adapter.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.usecase.GetManagerByIdUseCase;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.infra.adapter.manager.jpa.entity.ManagerEntity;
import com.accountmanagement.infra.adapter.manager.jpa.repository.ManagerRepository;
import com.accountmanagement.infra.configuration.security.JwtService;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ManagerAdapterTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private Environment environment;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ManagerAdapter managerAdapter;

    @Test
    public void testRegisterManager_WhenGivenValidManager_ShouldReturnSuccess() {
        RegisterManagerUseCase registerManagerUseCase = new RegisterManagerUseCase();
        registerManagerUseCase.setFirstName("John");
        registerManagerUseCase.setLastName("Doe");
        registerManagerUseCase.setEmail("john.doe@example.com");
        registerManagerUseCase.setPassword("password");

        when(environment.getProperty("manager.defaultMaxCreditLimit")).thenReturn("200");
        when(managerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(createMockManagerEntity());
        when(jwtService.generateToken(any(ManagerEntity.class))).thenReturn("mocked-token");
        when(passwordEncoder.encode(any())).thenReturn("encoded-password");

        ManagerDto result = managerAdapter.registerManager(registerManagerUseCase);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertNotNull(result.getToken());
        assertEquals("mocked-token", result.getToken());

        verify(managerRepository, times(1)).save(any(ManagerEntity.class));
    }

    @Test
    void testRegisterManager_WhenGivenValidManager_ShouldThrowException() {
        RegisterManagerUseCase useCase = new RegisterManagerUseCase("John", "Doe", "john.doe@example.com", "password");
        when(managerRepository.findByEmail(anyString())).thenReturn(Optional.of(new ManagerEntity()));

        AccountManagementException exception = assertThrows(AccountManagementException.class,
            () -> managerAdapter.registerManager(useCase));

        assertEquals(ErrorCode.MANAGER_ALREADY_REGISTERED_ERROR.getCode(), exception.getCode());
        verify(jwtService, never()).generateToken(any(ManagerEntity.class));
    }

    @Test
    void testRegisterManager_WhenManagerDefaultMaxCreditLimitNull_ShouldThrowException() {
        RegisterManagerUseCase useCase = new RegisterManagerUseCase("John", "Doe", "john.doe@example.com", "password");
        when(managerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(environment.getProperty("manager.defaultMaxCreditLimit")).thenReturn(null);

        AccountManagementException exception = assertThrows(AccountManagementException.class,
            () -> managerAdapter.registerManager(useCase));

        assertEquals(ErrorCode.MANAGER_EMPTY_DEFAULT_CREDIT_LIMIT_ERROR.getCode(), exception.getCode());
        verify(jwtService, never()).generateToken(any(ManagerEntity.class));
    }

    @Test
    void testLoginManager_WhenGivenValidCredentials_ShouldLogin() {
        LoginManagerUseCase loginUseCase = new LoginManagerUseCase("test@example.com", "password");
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setEmail("test@example.com");
        managerEntity.setPassword("encodedPassword");

        when(managerRepository.findByEmail(any())).thenReturn(Optional.of(managerEntity));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        ManagerDto result = managerAdapter.loginManager(loginUseCase);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());

        verify(jwtService).generateToken(managerEntity);
    }

    @Test
    void testLoginManager_WhenGivenManager_ShouldThrowException() {
        LoginManagerUseCase useCase = new LoginManagerUseCase("test@example.com", "password");
        when(managerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        AccountManagementException exception = assertThrows(AccountManagementException.class,
            () -> managerAdapter.loginManager(useCase));
        assertEquals(ErrorCode.MANAGER_NOT_FOUND_ERROR.getCode(), exception.getCode());
    }

    @Test
    void testGetManagerById_WhenGivenManagerId_ShouldSuccess() {
        GetManagerByIdUseCase useCase = new GetManagerByIdUseCase(UUID.randomUUID());
        when(managerRepository.findById(any())).thenReturn(Optional.of(new ManagerEntity()));

        ManagerDto result = managerAdapter.getManagerById(useCase);

        assertNotNull(result);
    }

    @Test
    void testGetManagerById_WhenGivenManagerId_ShouldThrowException() {
        GetManagerByIdUseCase useCase = new GetManagerByIdUseCase(UUID.randomUUID());
        when(managerRepository.findById(any())).thenReturn(Optional.empty());

        AccountManagementException exception = assertThrows(AccountManagementException.class,
            () -> managerAdapter.getManagerById(useCase));
        assertEquals(ErrorCode.MANAGER_NOT_FOUND_ERROR.getCode(), exception.getCode());
    }

    private ManagerEntity createMockManagerEntity() {
        return ManagerEntity.builder()
            .id(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .password("encoded-password")
            .maxCreditLimit(BigDecimal.TEN)
            .build();
    }
}
