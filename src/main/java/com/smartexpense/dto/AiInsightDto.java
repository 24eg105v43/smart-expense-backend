package com.smartexpense.dto;

import java.util.List;

public class AiInsightDto {
    private String topSpendingCategory;
    private String spendingSummary;
    private List<String> keyObservations;
    private List<String> actionableSuggestions;
    private String financialHealthStatus; // EXCELLENT, STABLE, NEEDS_ATTENTION, WARNING
    private String disclaimer;

    public AiInsightDto() {}

    public AiInsightDto(String topSpendingCategory, String spendingSummary, List<String> keyObservations, List<String> actionableSuggestions, String financialHealthStatus, String disclaimer) {
        this.topSpendingCategory = topSpendingCategory;
        this.spendingSummary = spendingSummary;
        this.keyObservations = keyObservations;
        this.actionableSuggestions = actionableSuggestions;
        this.financialHealthStatus = financialHealthStatus;
        this.disclaimer = disclaimer;
    }

    public String getTopSpendingCategory() {
        return topSpendingCategory;
    }

    public void setTopSpendingCategory(String topSpendingCategory) {
        this.topSpendingCategory = topSpendingCategory;
    }

    public String getSpendingSummary() {
        return spendingSummary;
    }

    public void setSpendingSummary(String spendingSummary) {
        this.spendingSummary = spendingSummary;
    }

    public List<String> getKeyObservations() {
        return keyObservations;
    }

    public void setKeyObservations(List<String> keyObservations) {
        this.keyObservations = keyObservations;
    }

    public List<String> getActionableSuggestions() {
        return actionableSuggestions;
    }

    public void setActionableSuggestions(List<String> actionableSuggestions) {
        this.actionableSuggestions = actionableSuggestions;
    }

    public String getFinancialHealthStatus() {
        return financialHealthStatus;
    }

    public void setFinancialHealthStatus(String financialHealthStatus) {
        this.financialHealthStatus = financialHealthStatus;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
