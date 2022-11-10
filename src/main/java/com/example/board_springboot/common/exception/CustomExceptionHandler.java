package com.example.board_springboot.common.exception;

import com.example.board_springboot.common.utils.TimeParsingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board_springboot.common.exception.ErrorHttpStatusMapper.mapToStatus;
import static com.example.board_springboot.common.utils.TimeParsingUtils.formatterString;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleWSApiException(
            CustomException exception,
            WebRequest request
    ) {
        log.error("CustomException: ", exception);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .date(formatterString(LocalDateTime.now()))
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .errorCode(exception.getErrorCode())
                .build();

        log.error("errorDetails: {}", errorDetails);
        return new ResponseEntity<>(errorDetails, mapToStatus(errorDetails.getErrorCode()));
    }

    // 값유효 검증 메서드
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(
            MethodArgumentNotValidException e,
            WebRequest request
    ) {
        log.error("MethodArgumentNotValidException: ", e);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .date(TimeParsingUtils.formatterString(LocalDateTime.now()))
                .message(Optional.ofNullable(e.getBindingResult()
                                .getFieldError())
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse(e.getMessage()))
                .description(request.getDescription(false))
                .errorCode(ErrorCode.INVALID_REQUEST)
                .build();

        log.info("errorCode: {}", errorDetails.getErrorCode());

        return new ResponseEntity<>(errorDetails, mapToStatus(errorDetails.getErrorCode()));
    }

    // 그외 모든 Exception 처리
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> GlobalException(
            Exception exception,
            WebRequest request
    ) {
        log.error("Exception: ", exception);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .date(formatterString(LocalDateTime.now()))
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .build();

        log.error("errorDetails: {}", errorDetails);
        return new ResponseEntity<>(errorDetails, mapToStatus(errorDetails.getErrorCode()));
    }

}
