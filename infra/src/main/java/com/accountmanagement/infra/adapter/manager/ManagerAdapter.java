package com.accountmanagement.infra.adapter.manager;

import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.domain.manager.model.ManagerDto;
import com.accountmanagement.domain.manager.model.enumeration.Role;
import com.accountmanagement.domain.manager.port.ManagerPort;
import com.accountmanagement.domain.manager.usecase.GetManagerByIdUseCase;
import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
import com.accountmanagement.domain.manager.usecase.UpdateManagerUsedCreditLimitUseCase;
import com.accountmanagement.infra.adapter.manager.jpa.entity.ManagerEntity;
import com.accountmanagement.infra.adapter.manager.jpa.repository.ManagerRepository;
import com.accountmanagement.infra.configuration.security.JwtService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerAdapter implements ManagerPort {

    private final ManagerRepository managerRepository;
    private final Environment environment;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public ManagerDto registerManager(final RegisterManagerUseCase useCase) {
        if(managerRepository.findByEmail(useCase.getEmail()).isPresent()){
            throw new AccountManagementException(ErrorCode.MANAGER_ALREADY_REGISTERED_ERROR);
        }
        ManagerEntity managerEntity = managerRepository.save(ManagerEntity.builder()
            .firstName(useCase.getFirstName())
            .lastName(useCase.getLastName())
            .email(useCase.getEmail())
            .password(passwordEncoder.encode(useCase.getPassword()))
            .maxCreditLimit(getDefaultMaxCreditLimit())
            .role(Role.USER)
            .build());
        ManagerDto manager = managerEntity.toModel();
        manager.setToken(jwtService.generateToken(managerEntity));
        return manager;
    }

    @Transactional(readOnly = true)
    @Override
    public ManagerDto loginManager(final LoginManagerUseCase useCase) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(useCase.getEmail(), useCase.getPassword()));
        ManagerEntity managerEntity = managerRepository.findByEmail(useCase.getEmail())
            .orElseThrow(() -> new AccountManagementException(ErrorCode.MANAGER_NOT_FOUND_ERROR));
        ManagerDto manager = managerEntity.toModel();
        manager.setToken(jwtService.generateToken(managerEntity));
        return manager;
    }

    @Transactional(readOnly = true)
    @Override
    public ManagerDto getManagerById(final GetManagerByIdUseCase useCase) {
        return managerRepository.findById(useCase.getId()).orElseThrow(() -> new AccountManagementException(
            ErrorCode.MANAGER_NOT_FOUND_ERROR)).toModel();
    }

    @Override
    public void updateManagerUsedCreditLimit(final UpdateManagerUsedCreditLimitUseCase useCase) {
        managerRepository.updateManagerUsedCreditLimit(useCase.getUsedCreditLimit(), useCase.getId());
    }

    private BigDecimal getDefaultMaxCreditLimit() {
        String defaultMaxCreditLimit = environment.getProperty("manager.defaultMaxCreditLimit");
        if (StringUtils.isEmpty(defaultMaxCreditLimit)) {
            throw new AccountManagementException(ErrorCode.MANAGER_EMPTY_DEFAULT_CREDIT_LIMIT_ERROR);
        }
        return new BigDecimal(defaultMaxCreditLimit);
    }
}
