package com.smartexpense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartExpenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartExpenseApplication.class, args);
        System.out.println("=================================================");
        System.out.println(" Smart Expense Backend Started Successfully!    ");
        System.out.println(" REST API Base URL: http://localhost:8080/api    ");
        System.out.println(" Database: MySQL (smartexpense_db)               ");
        System.out.println("=================================================");
    }
}
