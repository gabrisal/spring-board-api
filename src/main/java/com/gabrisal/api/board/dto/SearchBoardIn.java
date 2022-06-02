package com.gabrisal.api.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "게시판 조회 요청")
@Getter
@Setter
@NoArgsConstructor
public class SearchBoardIn {

    @Schema(description = "게시글 ID", required = true, example = "1")
    private int boardId;

}
