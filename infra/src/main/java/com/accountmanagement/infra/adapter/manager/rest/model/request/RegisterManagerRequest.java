package com.accountmanagement.infra.adapter.manager.rest.model.request;

import com.accountmanagement.domain.manager.usecase.RegisterManagerUseCase;
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
public class RegisterManagerRequest implements Serializable {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty
    private String password;

    public RegisterManagerUseCase toModel() {
        return RegisterManagerUseCase.builder()
            .firstName(this.firstName)
            .lastName(this.lastName)
            .email(this.email)
            .password(this.password)
            .build();
    }
}
