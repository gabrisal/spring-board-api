package com.gabrisal.api.board.controller;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.service.BoardService;
import com.gabrisal.api.common.exception.ResponseMessage;
import com.gabrisal.api.common.exception.StatusEnum;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Board API - 게시판 관리 API")
@ApiResponses({
    @ApiResponse(code = 200, message = "정상")
    , @ApiResponse(code = 500, message = "시스템 오류")
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @ApiOperation(value = "게시판 다건 조회", notes = "게시판 전체 목록을 조회한다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> getBoardList() {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardList());
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @ApiOperation(value = "게시판 단건 조회", notes = "게시판 ID로 게시글 정보를 조회한다.")
    @GetMapping("/one/{boardId}")
    public ResponseEntity<ResponseMessage> getBoardById(@PathVariable int boardId) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardById(boardId));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @ApiOperation(value = "게시글 등록", notes = "게시글 한 건을 등록한다.")
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> saveBoard(@RequestBody AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.saveBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 한 건을 수정한다.")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> modifyBoard(@RequestBody AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.modifyBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시판 ID로 게시글을 삭제한다.")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteBoard(@RequestBody SearchBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.deleteBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }
}
