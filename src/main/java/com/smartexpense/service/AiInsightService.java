package com.smartexpense.service;

import com.smartexpense.dto.AiInsightDto;
import com.smartexpense.dto.CategoryExpenseDto;
import com.smartexpense.dto.DashboardSummaryDto;
import com.smartexpense.dto.TransactionDto;
import com.smartexpense.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiInsightService {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private TransactionService transactionService;

    public AiInsightDto generateSpendingInsights() {
        DashboardSummaryDto summary = dashboardService.getDashboardSummary();
        List<TransactionDto> allTx = transactionService.getAllUserTransactions();

        BigDecimal income = summary.getTotalIncome();
        BigDecimal expenses = summary.getTotalExpenses();
        BigDecimal balance = summary.getCurrentBalance();

        List<CategoryExpenseDto> categoryExpenses = summary.getCategoryExpenses();

        List<String> keyObservations = new ArrayList<>();
        List<String> actionableSuggestions = new ArrayList<>();

        String topCategoryName = "None";
        String spendingSummaryText;
        String financialHealthStatus = "STABLE";

        if (expenses.compareTo(BigDecimal.ZERO) == 0 && income.compareTo(BigDecimal.ZERO) == 0) {
            spendingSummaryText = "No financial transactions recorded yet. Add income and expense entries to enable AI analytical insights.";
            keyObservations.add("Your account is freshly setup without active transaction data.");
            actionableSuggestions.add("Start by adding your monthly income (e.g., Salary) and regular daily expenses.");
            return new AiInsightDto(
                    "None",
                    spendingSummaryText,
                    keyObservations,
                    actionableSuggestions,
                    "STABLE",
                    "AI recommendations provide analytical guidance based on transaction history and do not constitute certified financial advice."
            );
        }

        // Determine top spending category
        if (!categoryExpenses.isEmpty()) {
            CategoryExpenseDto topCat = categoryExpenses.get(0);
            topCategoryName = topCat.getCategory().name();
            keyObservations.add("Your highest expense category is " + topCat.getCategory().name() + " totaling ₹" + topCat.getAmount() + " (" + topCat.getPercentage() + "% of total spending).");
            
            if (topCat.getPercentage() > 40.0) {
                actionableSuggestions.add("Your spending on " + topCat.getCategory().name() + " consumes over 40% of total expenses. Consider setting a strict monthly cap for " + topCat.getCategory().name() + ".");
            }
        }

        // Financial ratio & health analysis
        if (income.compareTo(BigDecimal.ZERO) > 0) {
            double expenseRatio = expenses.multiply(new BigDecimal("100"))
                    .divide(income, 2, RoundingMode.HALF_UP)
                    .doubleValue();

            if (expenseRatio > 90.0) {
                financialHealthStatus = "WARNING";
                spendingSummaryText = "Critical Expense Warning: You are spending " + expenseRatio + "% of your total income. High financial vulnerability detected.";
                keyObservations.add("Your current monthly cash flow burn rate is extremely high.");
                actionableSuggestions.add("Urgent: Reduce discretionary expenditures in Shopping, Food, and Entertainment immediately.");
                actionableSuggestions.add("Aim to retain at least 20% of your monthly income as emergency savings buffer.");
            } else if (expenseRatio > 70.0) {
                financialHealthStatus = "NEEDS_ATTENTION";
                spendingSummaryText = "Moderate Spending Alert: You are spending " + expenseRatio + "% of your total income. Savings margin is constrained.";
                keyObservations.add("You maintain a positive cash flow balance of ₹" + balance + ", but discretionary expenses are high.");
                actionableSuggestions.add("Review subscription services and daily food orders to optimize your monthly balance.");
            } else if (expenseRatio > 40.0) {
                financialHealthStatus = "STABLE";
                spendingSummaryText = "Healthy Financial Balance: You spend " + expenseRatio + "% of your total income, leaving a solid balance of ₹" + balance + ".";
                keyObservations.add("Your spending-to-income ratio is well controlled.");
                actionableSuggestions.add("Allocate part of your net monthly savings into investment or emergency reserve funds.");
            } else {
                financialHealthStatus = "EXCELLENT";
                spendingSummaryText = "Excellent Money Management: Outstanding savings capacity! You spend only " + expenseRatio + "% of your income.";
                keyObservations.add("You have built a substantial balance surplus of ₹" + balance + ".");
                actionableSuggestions.add("Explore high-yield savings or diversified mutual fund investments for passive wealth generation.");
            }
        } else {
            financialHealthStatus = "NEEDS_ATTENTION";
            spendingSummaryText = "Expense entries exist without registered income records. Current net position is negative ₹" + expenses + ".";
            keyObservations.add("Total expenses stand at ₹" + expenses + " with ₹0 registered income.");
            actionableSuggestions.add("Add your salary or income entries to calculate accurate net savings rates.");
        }

        // Check for unusual high single transactions
        for (TransactionDto tx : allTx) {
            if (tx.getType() == TransactionType.EXPENSE && income.compareTo(BigDecimal.ZERO) > 0) {
                double singleTxRatio = tx.getAmount().multiply(new BigDecimal("100"))
                        .divide(income, 2, RoundingMode.HALF_UP)
                        .doubleValue();
                if (singleTxRatio >= 25.0) {
                    keyObservations.add("Unusual spending flagged: Single transaction of ₹" + tx.getAmount() + " on " + tx.getCategory() + " (" + tx.getDescription() + ") accounted for " + singleTxRatio + "% of income.");
                    break;
                }
            }
        }

        // Additional category-specific tips
        for (CategoryExpenseDto cat : categoryExpenses) {
            if (cat.getCategory().name().equals("Food") && cat.getAmount().compareTo(new BigDecimal("3000")) > 0) {
                actionableSuggestions.add("Food & Dining expenses are higher than average. Meal planning and reducing food delivery apps can save up to ₹1,500/month.");
            }
            if (cat.getCategory().name().equals("Shopping") && cat.getPercentage() > 25.0) {
                actionableSuggestions.add("Shopping takes up " + cat.getPercentage() + "% of expenses. Practice a 48-hour cooling-off rule before non-essential purchases.");
            }
        }

        if (actionableSuggestions.isEmpty()) {
            actionableSuggestions.add("Continue monitoring weekly expenditures and stick to categorized budget caps.");
        }

        return new AiInsightDto(
                topCategoryName,
                spendingSummaryText,
                keyObservations,
                actionableSuggestions,
                financialHealthStatus,
                "AI recommendations provide analytical guidance based on transaction history and do not constitute certified financial advice."
        );
    }
}
