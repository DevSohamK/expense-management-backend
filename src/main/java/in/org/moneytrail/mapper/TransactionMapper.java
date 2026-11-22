package in.org.moneytrail.mapper;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequest request){
        return Transaction.builder()
                .title(request.title().trim())
                .amount(request.amount())
                .transactionType(request.transactionType())
                .category(request.category().trim())
                .date(request.date())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();
    }


    public TransactionResponse toResponse(Transaction t){
        return new TransactionResponse(
                t.getId(),
                t.getTitle(),
                t.getAmount(),
                t.getCategory(),
                t.getTransactionType(),
                t.getDate(),
                t.getDescription(),
                t.getCreatedAt());
    }

    public void updateEntity(Transaction existing, TransactionRequest updatedRequest){
        existing.setTitle(updatedRequest.title());
        existing.setAmount(updatedRequest.amount());
        existing.setTransactionType(updatedRequest.transactionType());
        existing.setCategory(updatedRequest.category());
        existing.setDate(updatedRequest.date());
        existing.setDescription(updatedRequest.description());
    }

}
