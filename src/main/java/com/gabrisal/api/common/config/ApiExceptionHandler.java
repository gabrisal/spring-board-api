package com.gabrisal.api.common.config;

import com.gabrisal.api.common.exception.ResponseMessage;
import com.gabrisal.api.common.exception.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    /*
     TODO 다양한 에러처리 추가 필요
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseMessage> handleAll(final Exception ex) {
        log.error("[ERROR] Exception Type ::: " + ex.getClass().getSimpleName());
        log.error("[ERROR] Message ::: ", ex.getMessage());
        ex.printStackTrace();
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SERVER_ERROR.getStatusValue());
        return ResponseEntity.badRequest().body(resMsg);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleMissingPathVariable(final ConstraintViolationException ex) {
        log.error("::::::::::handleMissingPathVariable::::::::::::::");
        ResponseMessage resMsg = new ResponseMessage();
        List<String> errMsg = ex.getConstraintViolations()
                .stream()
                        .map(ConstraintViolation::getMessage)
                                .collect(Collectors.toList());
        // TODO: Valid 별 에러코드 분기
        resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
        resMsg.setErrMsg(errMsg.get(0).toString());
        return ResponseEntity.badRequest().body(resMsg);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseMessage resMsg = new ResponseMessage();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError err : allErrors) {
            log.error("[ERROR] ::: {}", err);
            log.error("[ERROR] getCodes ::: {}", Arrays.toString(err.getCodes()));
            String message = Arrays.stream(Objects.requireNonNull(err.getCodes()))
                    .map(c -> {
                        Object[] args = err.getArguments();
                        Locale locale = LocaleContextHolder.getLocale();
                        try {
                            return messageSource.getMessage(c, args, locale);
                        } catch (NoSuchMessageException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .findFirst()
                    .orElse(err.getDefaultMessage());
            log.error("[ERROR] message: {}", message);
            // TODO: Valid 별 에러코드 분기
            // XXX: getAllErrors 순서 확인
            resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
            resMsg.setErrMsg(message);
            return ResponseEntity.badRequest().body(resMsg);
        }
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

}
