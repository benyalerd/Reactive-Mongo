package com.example.api.controller;

import com.example.api.service.MerchantService;
import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.dto.response.InsertResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @Operation(summary = "register merchant")
    @ApiResponse(responseCode = "200", description = "insert merchant success", content = @Content(schema = @Schema(implementation = InsertResponse.class)))
    @PostMapping("/insert")
    public Mono<InsertResponse> insertMerchant(@RequestBody @Validated InsertMerchantRequest insertMerchantRequest) {
        return merchantService.insertMerchant(insertMerchantRequest);
    }

}
