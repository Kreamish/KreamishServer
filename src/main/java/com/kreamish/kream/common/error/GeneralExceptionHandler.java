package com.kreamish.kream.common.error;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import java.util.NoSuchElementException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult<Void>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
            ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiResult<Void>> handleNoSuchElementException(
        NoSuchElementException e
    ) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
        MissingServletRequestParameterException.class,
        IllegalArgumentException.class,
        IllegalStateException.class})
    public ResponseEntity<ApiResult<Void>> handleMethodArgumentNotValidException(
        Exception e) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ApiResult<Void>> handleTypeMismatchException(TypeMismatchException e) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST);
    }
}
