package com.smartexpense.service;

import com.smartexpense.dto.TransactionDto;
import com.smartexpense.dto.TransactionRequest;
import com.smartexpense.model.Transaction;
import com.smartexpense.model.User;
import com.smartexpense.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public List<TransactionDto> getAllUserTransactions() {
        User user = userService.getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDescIdDesc(user);
        return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        User user = userService.getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUserAndDateBetweenOrderByDateDescIdDesc(user, startDate, endDate);
        return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(Long id) {
        User user = userService.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to transaction");
        }

        return convertToDto(transaction);
    }

    @Transactional
    public TransactionDto createTransaction(TransactionRequest request) {
        User user = userService.getCurrentUser();

        Transaction transaction = new Transaction(
                request.getAmount(),
                request.getType(),
                request.getCategory(),
                request.getDate(),
                request.getDescription(),
                user
        );

        Transaction saved = transactionRepository.save(transaction);
        return convertToDto(saved);
    }

    @Transactional
    public TransactionDto updateTransaction(Long id, TransactionRequest request) {
        User user = userService.getCurrentUser();

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to transaction");
        }

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());

        Transaction updated = transactionRepository.save(transaction);
        return convertToDto(updated);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        User user = userService.getCurrentUser();

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to transaction");
        }

        transactionRepository.delete(transaction);
    }

    private TransactionDto convertToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.getCreatedAt()
        );
    }
}
