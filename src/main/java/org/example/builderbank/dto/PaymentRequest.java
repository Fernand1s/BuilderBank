package org.example.builderbank.dto;

import java.time.LocalDate;

public record PaymentRequest(
        String customerName,
        String cpfCnpj,
        String custumerEnde,
        String custumerCida,
        Double value,
        LocalDate dueDate,
        String bankSelection,
        String cedenteNome,
        String cedenteCpfCnpj,
        String cedenteAgencia

) {}
