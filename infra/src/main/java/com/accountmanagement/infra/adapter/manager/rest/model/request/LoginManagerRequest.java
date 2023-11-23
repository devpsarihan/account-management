package com.accountmanagement.infra.adapter.manager.rest.model.request;

import com.accountmanagement.domain.manager.usecase.LoginManagerUseCase;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginManagerRequest implements Serializable {

    @NotEmpty
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty
    private String password;

    public LoginManagerUseCase toModel(){
        return LoginManagerUseCase.builder()
            .email(this.email)
            .password(this.password)
            .build();
    }
}
