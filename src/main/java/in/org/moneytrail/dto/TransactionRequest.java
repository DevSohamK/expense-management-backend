package in.org.moneytrail.dto;

import in.org.moneytrail.entity.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionRequest(
        @NotBlank(message = "Title is blank")
        @Size(max = 1000, message = "title must be at most 1000 characters")
        String title,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Amount can have at most 2 decimal places")
        BigDecimal amount,

        @NotNull(message = "Category is required")
        String category,

        @NotNull(message = "Transaction type is required")
        TransactionType transactionType,

        @NotNull(message = "Date is required")
        LocalDate date,

        @Size(max = 5000, message = "Description must be at most 5000 characters")
        String description
) {
}
