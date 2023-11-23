package com.accountmanagement.domain.manager.usecase;

import com.accountmanagement.domain.common.usecase.UseCase;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterManagerUseCase implements UseCase, Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
