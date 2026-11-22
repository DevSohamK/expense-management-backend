package in.org.moneytrail.serviceimpl;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.entity.Transaction;
import in.org.moneytrail.mapper.TransactionMapper;
import in.org.moneytrail.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Transaction> inMemoryTransactionRepository = new ConcurrentHashMap<>();
    private final TransactionMapper mapper;

    @Override
    public TransactionResponse saveTransaction(TransactionRequest transaction) {
//        Transaction newTransaction = Transaction.builder()
//                .id(idGenerator.incrementAndGet())
//                .transactionType(transaction.transactionType())
//                .title(transaction.title())
//                .amount(transaction.amount())
//                .category(transaction.category())
//                .createdAt(LocalDateTime.now())
//                .date(transaction.date())
//                .description(transaction.description())
//                .build();

        Transaction newTransaction = mapper.toEntity(transaction);
        newTransaction.setId(idGenerator.incrementAndGet());

        inMemoryTransactionRepository.put(newTransaction.getId(), newTransaction);

//        return new TransactionResponse(newTransaction.getId(),
//                newTransaction.getTitle(),
//                newTransaction.getAmount(),
//                newTransaction.getCategory(),
//                newTransaction.getTransactionType(),
//                newTransaction.getDate(),
//                newTransaction.getDescription(),
//                newTransaction.getCreatedAt());

        return mapper.toResponse(newTransaction);
    }

    @Override
    public TransactionResponse findById(Long transactionId) {
        return Optional.ofNullable(inMemoryTransactionRepository.get(transactionId))
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Transaction with id :: " + transactionId + " is not found"));
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return inMemoryTransactionRepository.values()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public TransactionResponse updateTransactionById(Long transactionId, TransactionRequest updatedTransaction) {
        Optional.ofNullable(inMemoryTransactionRepository.get(transactionId)).orElseThrow(() -> new RuntimeException("Transaction with id :: " + transactionId + " is not found"));
        Transaction existingTransaction = inMemoryTransactionRepository.get(transactionId);
        mapper.updateEntity(existingTransaction, updatedTransaction);
        return mapper.toResponse(existingTransaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        Optional.ofNullable(inMemoryTransactionRepository.get(transactionId)).orElseThrow(() -> new RuntimeException("Transaction with id :: " + transactionId + " is not found"));
        inMemoryTransactionRepository.remove(transactionId);
    }
}
