package com.gabrisal.api.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    /*
     TODO 다양한 에러처리 추가 필요
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseMessage> handleAll(final Exception ex) {
        log.info("========== ERROR =======================================");
        log.info("========== CLASS NAME : " + ex.getClass().getName());
        log.error("error", ex);
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SERVER_ERROR.getStatusValue());
        return ResponseEntity.badRequest().body(resMsg);
    }
}
