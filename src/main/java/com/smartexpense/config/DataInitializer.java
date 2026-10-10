package com.smartexpense.config;

import com.smartexpense.model.Category;
import com.smartexpense.model.Transaction;
import com.smartexpense.model.TransactionType;
import com.smartexpense.model.User;
import com.smartexpense.repository.TransactionRepository;
import com.smartexpense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("demo@smartexpense.com")) {
            User demoUser = new User(
                    "Rahul Sharma",
                    "demo@smartexpense.com",
                    passwordEncoder.encode("password123")
            );
            userRepository.save(demoUser);

            LocalDate today = LocalDate.now();

            Transaction t1 = new Transaction(new BigDecimal("50000.00"), TransactionType.INCOME, Category.Salary, today.minusDays(5), "Monthly Salary Credit", demoUser);
            Transaction t2 = new Transaction(new BigDecimal("8500.00"), TransactionType.INCOME, Category.Freelance, today.minusDays(2), "Web Design Consulting", demoUser);
            
            Transaction t3 = new Transaction(new BigDecimal("4500.00"), TransactionType.EXPENSE, Category.Food, today.minusDays(4), "Monthly Grocery & Supermarket", demoUser);
            Transaction t4 = new Transaction(new BigDecimal("1200.00"), TransactionType.EXPENSE, Category.Food, today.minusDays(1), "Restaurant Dinner with Friends", demoUser);
            Transaction t5 = new Transaction(new BigDecimal("1800.00"), TransactionType.EXPENSE, Category.Transportation, today.minusDays(3), "Monthly Metro Pass & Fuel", demoUser);
            Transaction t6 = new Transaction(new BigDecimal("3500.00"), TransactionType.EXPENSE, Category.Bills, today.minusDays(6), "Electricity & Internet Bill", demoUser);
            Transaction t7 = new Transaction(new BigDecimal("2200.00"), TransactionType.EXPENSE, Category.Shopping, today.minusDays(2), "Sneakers & Clothing", demoUser);
            Transaction t8 = new Transaction(new BigDecimal("1500.00"), TransactionType.EXPENSE, Category.Entertainment, today.minusDays(1), "Movie & OTT Subscription", demoUser);
            Transaction t9 = new Transaction(new BigDecimal("2500.00"), TransactionType.EXPENSE, Category.Healthcare, today.minusDays(5), "Medical Checkup & Pharmacy", demoUser);

            transactionRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9));
            System.out.println("Initialized Demo User (demo@smartexpense.com / password123) with seed financial transactions.");
        }
    }
}
