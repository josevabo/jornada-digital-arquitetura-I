package tech.ada.banco.dto;

import lombok.*;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvestimentoDTO {

    private UUID uuid;

    @NonNull
    private Integer numeroConta;

    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal valorOperacao;



}
