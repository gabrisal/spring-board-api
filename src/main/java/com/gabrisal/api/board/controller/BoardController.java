package com.gabrisal.api.board.controller;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.service.BoardService;
import com.gabrisal.api.common.exception.ResponseMessage;
import com.gabrisal.api.common.exception.StatusEnum;
import com.gabrisal.api.common.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Tag(name = "board", description = "게시판 관리 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @Operation(summary = "게시판 다건 조회", description = "게시판 전체 목록을 조회한다.", tags = "board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 다건 조회 정상")
            , @ApiResponse(responseCode = "500", description = "시스템 오류")
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> getBoardList() {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardList());
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @Operation(summary = "게시판 단건 조회", description = "게시판 ID로 게시글 정보를 조회한다.", tags = "board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 단건 조회 정상")
            , @ApiResponse(responseCode = "500", description = "시스템 오류")
    })
    @GetMapping("/one/{boardId}")
    public ResponseEntity<ResponseMessage> getBoardById(@Parameter(name = "boardId", description = "게시글 ID", in = ParameterIn.PATH, required = true, example = "2")
                                                            @PathVariable @Positive int boardId) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.getBoardById(boardId));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @Operation(summary = "게시글 등록", description = "게시글 한 건을 등록한다.", tags = "board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 등록 정상")
            , @ApiResponse(responseCode = "500", description = "시스템 오류")
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> saveBoard(@RequestBody @Validated(ValidationGroups.createBoardGroup.class) AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.saveBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @Operation(summary = "게시글 수정", description = "게시글 한 건을 수정한다.", tags = "board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 정상")
            , @ApiResponse(responseCode = "500", description = "시스템 오류")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> modifyBoard(@RequestBody @Validated(ValidationGroups.modifyBoardGroup.class) AddBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.modifyBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }

    @Operation(summary = "게시글 삭제", description = "게시판 ID로 게시글을 삭제한다.", tags = "board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 정상")
            , @ApiResponse(responseCode = "500", description = "시스템 오류")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteBoard(@RequestBody @Valid SearchBoardIn in) {
        ResponseMessage resMsg = new ResponseMessage();
        resMsg.setData(service.deleteBoard(in));
        resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
        resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        return ResponseEntity.ok(resMsg);
    }
}
