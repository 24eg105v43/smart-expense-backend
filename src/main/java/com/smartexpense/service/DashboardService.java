package com.smartexpense.service;

import com.smartexpense.dto.CategoryExpenseDto;
import com.smartexpense.dto.DashboardSummaryDto;
import com.smartexpense.dto.MonthlyAnalysisDto;
import com.smartexpense.dto.TransactionDto;
import com.smartexpense.model.Category;
import com.smartexpense.model.Transaction;
import com.smartexpense.model.TransactionType;
import com.smartexpense.model.User;
import com.smartexpense.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public BigDecimal getTotalIncome() {
        User user = userService.getCurrentUser();
        BigDecimal income = transactionRepository.sumAmountByUserAndType(user, TransactionType.INCOME);
        return income != null ? income : BigDecimal.ZERO;
    }

    public BigDecimal getTotalExpenses() {
        User user = userService.getCurrentUser();
        BigDecimal expenses = transactionRepository.sumAmountByUserAndType(user, TransactionType.EXPENSE);
        return expenses != null ? expenses : BigDecimal.ZERO;
    }

    public BigDecimal getCurrentBalance() {
        return getTotalIncome().subtract(getTotalExpenses());
    }

    public List<CategoryExpenseDto> getCategoryExpenses() {
        User user = userService.getCurrentUser();
        List<Object[]> rawCategorySums = transactionRepository.findCategorySumsByUserAndType(user, TransactionType.EXPENSE);

        BigDecimal totalExpenses = getTotalExpenses();
        List<CategoryExpenseDto> list = new ArrayList<>();

        for (Object[] row : rawCategorySums) {
            Category category = (Category) row[0];
            BigDecimal amount = (BigDecimal) row[1];

            double percentage = 0.0;
            if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                percentage = amount.multiply(new BigDecimal("100"))
                        .divide(totalExpenses, 2, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            list.add(new CategoryExpenseDto(category, amount, percentage));
        }

        // Sort descending by amount
        list.sort((a, b) -> b.getAmount().compareTo(a.getAmount()));
        return list;
    }

    public MonthlyAnalysisDto getMonthlyAnalysis(int year, int month) {
        User user = userService.getCurrentUser();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        BigDecimal mIncome = transactionRepository.sumAmountByUserAndTypeAndDateBetween(
                user, TransactionType.INCOME, startDate, endDate);
        if (mIncome == null) mIncome = BigDecimal.ZERO;

        BigDecimal mExpenses = transactionRepository.sumAmountByUserAndTypeAndDateBetween(
                user, TransactionType.EXPENSE, startDate, endDate);
        if (mExpenses == null) mExpenses = BigDecimal.ZERO;

        BigDecimal mBalance = mIncome.subtract(mExpenses);

        double savingsRate = 0.0;
        if (mIncome.compareTo(BigDecimal.ZERO) > 0) {
            savingsRate = mBalance.multiply(new BigDecimal("100"))
                    .divide(mIncome, 2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        List<Object[]> rawCategorySums = transactionRepository.findCategorySumsByUserAndTypeAndDateBetween(
                user, TransactionType.EXPENSE, startDate, endDate);

        List<CategoryExpenseDto> categoryExpenses = new ArrayList<>();
        for (Object[] row : rawCategorySums) {
            Category category = (Category) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            double percentage = 0.0;
            if (mExpenses.compareTo(BigDecimal.ZERO) > 0) {
                percentage = amount.multiply(new BigDecimal("100"))
                        .divide(mExpenses, 2, RoundingMode.HALF_UP)
                        .doubleValue();
            }
            categoryExpenses.add(new CategoryExpenseDto(category, amount, percentage));
        }
        categoryExpenses.sort((a, b) -> b.getAmount().compareTo(a.getAmount()));

        return new MonthlyAnalysisDto(
                yearMonth.getMonth().name(),
                year,
                mIncome,
                mExpenses,
                mBalance,
                savingsRate,
                categoryExpenses
        );
    }

    public DashboardSummaryDto getDashboardSummary() {
        User user = userService.getCurrentUser();
        LocalDate now = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(now);
        LocalDate startDate = currentYearMonth.atDay(1);
        LocalDate endDate = currentYearMonth.atEndOfMonth();

        BigDecimal totalIncome = getTotalIncome();
        BigDecimal totalExpenses = getTotalExpenses();
        BigDecimal currentBalance = totalIncome.subtract(totalExpenses);

        BigDecimal mIncome = transactionRepository.sumAmountByUserAndTypeAndDateBetween(
                user, TransactionType.INCOME, startDate, endDate);
        if (mIncome == null) mIncome = BigDecimal.ZERO;

        BigDecimal mExpenses = transactionRepository.sumAmountByUserAndTypeAndDateBetween(
                user, TransactionType.EXPENSE, startDate, endDate);
        if (mExpenses == null) mExpenses = BigDecimal.ZERO;

        BigDecimal mBalance = mIncome.subtract(mExpenses);

        List<CategoryExpenseDto> categoryExpenses = getCategoryExpenses();

        List<Transaction> recentTxList = transactionRepository.findByUserOrderByDateDescIdDesc(user);
        List<TransactionDto> recentTx = recentTxList.stream()
                .limit(5)
                .map(t -> new TransactionDto(
                        t.getId(), t.getAmount(), t.getType(), t.getCategory(), t.getDate(), t.getDescription(), t.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new DashboardSummaryDto(
                totalIncome,
                totalExpenses,
                currentBalance,
                mIncome,
                mExpenses,
                mBalance,
                categoryExpenses,
                recentTx
        );
    }
}
