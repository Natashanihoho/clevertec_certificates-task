package ru.clevertec.ecl.api.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSequenceDto {

    private Integer sequence;
    private boolean isFirstOrderSaved;
}
