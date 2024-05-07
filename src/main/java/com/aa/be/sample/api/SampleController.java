package com.aa.be.sample.api;

import com.aa.be.common.model.CommonResp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Operation(
            summary = "sample api",
            description = "sample api",
            tags = {"Sample"})
    @GetMapping(value = "/v1/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResp<String>> sampleApi() {
        return ResponseEntity.ok(CommonResp.onSuccess("hello world"));
    }
}
