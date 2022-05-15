package com.gabrisal.api.board.controller;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.service.BoardService;
import com.gabrisal.api.common.ResponseMessage;
import com.gabrisal.api.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> getBoardList() {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardList());
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @GetMapping("/one")
    public ResponseEntity<ResponseMessage> getBoardById(@RequestBody SearchBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardById(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> saveBoard(@RequestBody AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.saveBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> modifyBoard(@RequestBody AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.modifyBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteBoard(@RequestBody SearchBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.deleteBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }
}
