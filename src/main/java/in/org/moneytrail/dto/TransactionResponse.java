package in.org.moneytrail.dto;

import in.org.moneytrail.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String title,
        BigDecimal amount,
        String category,
        TransactionType transactionType,
        LocalDate date,
        String description,
        LocalDateTime createdAt
) {
}
