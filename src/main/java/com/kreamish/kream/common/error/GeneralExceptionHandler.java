package com.kreamish.kream.common.error;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult<?>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
            ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiResult<?>> handleNoSuchElementException(
        NoSuchElementException e
    ) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResult<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST);
    }
}
