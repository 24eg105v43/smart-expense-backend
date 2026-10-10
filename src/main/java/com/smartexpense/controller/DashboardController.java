package com.smartexpense.controller;

import com.smartexpense.dto.CategoryExpenseDto;
import com.smartexpense.dto.DashboardSummaryDto;
import com.smartexpense.dto.MonthlyAnalysisDto;
import com.smartexpense.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/income")
    public ResponseEntity<Map<String, Object>> getTotalIncome() {
        Map<String, Object> res = new HashMap<>();
        res.put("totalIncome", dashboardService.getTotalIncome());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/expenses")
    public ResponseEntity<Map<String, Object>> getTotalExpenses() {
        Map<String, Object> res = new HashMap<>();
        res.put("totalExpenses", dashboardService.getTotalExpenses());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getCurrentBalance() {
        Map<String, Object> res = new HashMap<>();
        res.put("currentBalance", dashboardService.getCurrentBalance());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/category-expenses")
    public ResponseEntity<List<CategoryExpenseDto>> getCategoryExpenses() {
        return ResponseEntity.ok(dashboardService.getCategoryExpenses());
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyAnalysisDto> getMonthlyAnalysis(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        
        LocalDate now = LocalDate.now();
        int reqYear = (year != null) ? year : now.getYear();
        int reqMonth = (month != null) ? month : now.getMonthValue();

        return ResponseEntity.ok(dashboardService.getMonthlyAnalysis(reqYear, reqMonth));
    }
}
