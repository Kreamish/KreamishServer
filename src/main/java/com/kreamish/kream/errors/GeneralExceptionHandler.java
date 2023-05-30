package com.kreamish.kream.errors;

import com.kreamish.kream.common.ApiUtils;
import com.kreamish.kream.common.ApiUtils.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult<?>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
