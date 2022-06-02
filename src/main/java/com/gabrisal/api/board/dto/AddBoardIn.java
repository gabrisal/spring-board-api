package com.gabrisal.api.board.dto;

import com.gabrisal.api.board.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "게시판 등록/수정 요청")
@Getter
@Setter
@NoArgsConstructor
public class AddBoardIn {

    @Schema(description = "게시글 ID", required = false, example = "1")
    private int boardId;

    @Schema(description = "게시글 제목", required = true, example = "제목")
    private String boardTitle;

    @Schema(description = "게시글 내용", required = true, example = "내용")
    private String boardContent;

    @Schema(description = "등록한 사용자 ID", required = true, example = "gabrisal")
    private String regUserId;

    @Schema(description = "태그 목록", required = false)
    private List<Tag> tagList;

}
