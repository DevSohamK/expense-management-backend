package in.org.moneytrail.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {
    private Long id;
    private String title;
    private BigDecimal amount;
    private String category;
    private TransactionType transactionType;
    private LocalDate date;
    private String description;
    private LocalDateTime createdAt;
}
