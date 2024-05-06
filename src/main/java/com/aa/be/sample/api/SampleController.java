package com.aa.be.sample.api;

import com.aa.be.common.model.CommonReturnVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping
    public ResponseEntity<CommonReturnVO<String>> sampleApi() {
        return new ResponseEntity<>(CommonReturnVO.onSuccess("sampleApi"), HttpStatus.OK);
    }
}
