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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @Operation(summary = "게시글 엑셀 업로드", description = "게시글을 엑셀업로드로 등록한다.", tags = "board")
    @PostMapping(value = "/upload/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> saveBoardByUploadExcel(@RequestPart("uploadFile") MultipartFile excelFile) {
        ResponseMessage resMsg = new ResponseMessage();

        try {
            if (excelFile == null || excelFile.isEmpty() || !excelFile.getOriginalFilename().endsWith("xlsx")) {
                throw new RuntimeException("엑셀파일을 등록해 주세요.");
            }
            resMsg.setData(service.saveBoardByUploadExcel(excelFile));
            resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
            resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        } catch (Exception e) {
            resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
            resMsg.setErrMsg(StatusEnum.SERVER_ERROR.getStatusValue());
        }

        return ResponseEntity.ok(resMsg);
    }

    @Operation(summary = "게시글 엑셀 다운로드", description = "게시글 전체 목록을 엑셀파일로 다운로드한다.", tags = "board")
    @GetMapping("/download/excel")
    public ResponseEntity<ResponseMessage> getBoadListByExcel(HttpServletResponse response) {
        ResponseMessage resMsg = new ResponseMessage();

        try {
            service.getBoadListByExcel(response);
            resMsg.setErrCode(StatusEnum.SUCCESS.getStatusCode());
            resMsg.setErrMsg(StatusEnum.SUCCESS.getStatusValue());
        } catch (Exception e) {
            resMsg.setErrCode(StatusEnum.SERVER_ERROR.getStatusCode());
            resMsg.setErrMsg(StatusEnum.SERVER_ERROR.getStatusValue());
        }

        return ResponseEntity.ok(resMsg);
    }
}
