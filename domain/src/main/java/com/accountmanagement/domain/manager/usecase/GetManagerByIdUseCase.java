package com.accountmanagement.domain.manager.usecase;

import com.accountmanagement.domain.common.usecase.UseCase;
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
public class GetManagerByIdUseCase implements UseCase, Serializable {

    private UUID id;
}
