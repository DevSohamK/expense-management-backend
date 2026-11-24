package in.org.moneytrail.serviceimpl;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.entity.Transaction;
import in.org.moneytrail.exception.TransactionNotFoundException;
import in.org.moneytrail.mapper.TransactionMapper;
import in.org.moneytrail.mapper.TransactionMapperMapStruct;
import in.org.moneytrail.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
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
    private final TransactionMapperMapStruct mapperMapStruct;

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

//        Transaction newTransaction = mapper.toEntity(transaction);

        Transaction newTransaction = mapperMapStruct.toEntity(transaction);

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

//        return mapper.toResponse(newTransaction);

        return mapperMapStruct.toResponse(newTransaction);
    }

    @Override
    public TransactionResponse findById(Long transactionId) {
        return Optional.ofNullable(inMemoryTransactionRepository.get(transactionId))
                .map(mapperMapStruct::toResponse)
//                .orElseThrow(() -> new RuntimeException("Transaction with id :: " + transactionId + " is not found"));
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return inMemoryTransactionRepository.values()
                .stream()
                .map(mapperMapStruct::toResponse)
                .toList();
    }

    @Override
    public TransactionResponse updateTransactionById(Long transactionId, TransactionRequest updatedTransaction) {
        Optional.ofNullable(inMemoryTransactionRepository.get(transactionId)).orElseThrow(() -> new TransactionNotFoundException(transactionId));
        Transaction existingTransaction = inMemoryTransactionRepository.get(transactionId);
        mapper.updateEntity(existingTransaction, updatedTransaction);
        return mapper.toResponse(existingTransaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        Optional.ofNullable(inMemoryTransactionRepository.get(transactionId)).orElseThrow(() -> new TransactionNotFoundException(transactionId));
        inMemoryTransactionRepository.remove(transactionId);
    }

    @Override
    public Page<TransactionResponse> findAllTransactions(Pageable pageable) {
        List<Transaction> transactions = inMemoryTransactionRepository.values()
                .stream()
                .sorted(Comparator.comparing(
                        Transaction::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .toList();

        int start = (int) pageable.getOffset();
        if (start >= transactions.size()) {
            return new PageImpl<>(List.of(), pageable, transactions.size());
        }
        int end = Math.min(start + pageable.getPageSize(), transactions.size());

        List<TransactionResponse> response = transactions
                .subList(start, end)
                .stream()
                .map(mapperMapStruct::toResponse)
                .toList();

        return new PageImpl<>(response, pageable, transactions.size());
    }
}
