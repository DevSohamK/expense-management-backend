package in.org.moneytrail.service;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionResponse saveTransaction(TransactionRequest transaction);
    TransactionResponse findById(Long transactionId);
    List<TransactionResponse> getAllTransactions();
    TransactionResponse updateTransactionById(Long transactionId, TransactionRequest updatedTransaction);
    void deleteTransactionById(Long transactionId);
    Page<TransactionResponse> findAllTransactions(Pageable pageable);
}