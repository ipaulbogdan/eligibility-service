package com.idorasi.eligibility.controller;

import com.idorasi.eligibility.dto.ApiResponse;
import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.service.EligibilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eligibility")
@AllArgsConstructor
public class EligibilityController {

    private EligibilityService eligibilityService;

    @PostMapping("/verify")
    @PreAuthorize("hasRole('API_USER')")
    public ApiResponse<ApiResponse.EligibilityRecordDto> find(@RequestBody @Valid EligibilityRequest eligibilityRequest) {
        return eligibilityService.findEligibilityRecord(eligibilityRequest);
    }
}
