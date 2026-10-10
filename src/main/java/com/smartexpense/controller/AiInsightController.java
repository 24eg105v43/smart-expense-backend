package com.smartexpense.controller;

import com.smartexpense.dto.AiInsightDto;
import com.smartexpense.service.AiInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AiInsightController {

    @Autowired
    private AiInsightService aiInsightService;

    @GetMapping("/insights")
    public ResponseEntity<AiInsightDto> getSpendingInsights() {
        return ResponseEntity.ok(aiInsightService.generateSpendingInsights());
    }
}
