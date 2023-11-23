package com.accountmanagement.domain.bill.model.payload;

import com.accountmanagement.domain.bill.model.enumeration.BillStatus;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPayload implements Serializable {

    private UUID billId;
    private BillStatus status;
}
