package com.example.board_springboot.common.exception;

import com.example.board_springboot.common.utils.TimeParsingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board_springboot.common.exception.ErrorHttpStatusMapper.mapToStatus;
import static com.example.board_springboot.common.utils.TimeParsingUtils.formatterString;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleWSApiException(
            CustomException exception, WebRequest request
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers, 
            HttpStatus status, 
            WebRequest request
    ) {

        log.error("MethodArgumentNotValidException: ", exception);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .date(TimeParsingUtils.formatterString(LocalDateTime.now()))
                .message(Optional.ofNullable(exception.getBindingResult()
                                .getFieldError())
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse(exception.getMessage()))
                .description(request.getDescription(false))
                .errorCode(ErrorCode.INVALID_REQUEST)
                .build();

        log.error("errorDetails: {}", errorDetails);
        return  new ResponseEntity<>(errorDetails, mapToStatus(errorDetails.getErrorCode()));
    }
}
