package com.accountmanagement.infra.adapter.manager.rest.model.response;

import com.accountmanagement.domain.manager.model.ManagerDto;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse implements Serializable {

    private UUID id;
    private String token;

    public static ManagerResponse toModel(ManagerDto managerDto) {
        return ManagerResponse.builder()
            .id(managerDto.getId())
            .token(managerDto.getToken())
            .build();
    }
}
