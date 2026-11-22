package in.org.moneytrail.controller;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.saveTransaction(transactionRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findTransaction(@PathVariable("id") Long transactionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.findById(transactionId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TransactionResponse>> fetchAllTransactions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.getAllTransactions());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable("id") Long transactionId, @RequestBody @Valid TransactionRequest updatedTransactionRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.updateTransactionById(transactionId, updatedTransactionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.noContent().build();
    }
}
