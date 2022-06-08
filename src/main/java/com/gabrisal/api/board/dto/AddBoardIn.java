package com.gabrisal.api.board.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.gabrisal.api.board.model.Tag;
import com.gabrisal.api.common.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "게시판 등록/수정 요청")
@Getter @Setter
@NoArgsConstructor
public class AddBoardIn {

    @Positive(groups = ValidationGroups.modifyBoardGroup.class)
    @Schema(description = "게시글 ID", example = "1")
    private int boardId;

    // XXX: 공통으로 모두 적용하고 특이케이스만 지정할 순 없을까?
    @NotBlank(groups = { ValidationGroups.createBoardGroup.class, ValidationGroups.modifyBoardGroup.class })
    @Schema(description = "게시글 제목", required = true, example = "제목")
    private String boardTitle;

    @NotBlank(groups = { ValidationGroups.createBoardGroup.class, ValidationGroups.modifyBoardGroup.class })
    @Size(max = 200
        , groups = { ValidationGroups.createBoardGroup.class, ValidationGroups.modifyBoardGroup.class })
    @Schema(description = "게시글 내용", required = true, example = "내용")
    private String boardContent;

    // XXX: 공백인 경우
//    @NotBlank(groups = { ValidationGroups.createBoardGroup.class, ValidationGroups.modifyBoardGroup.class })
    @JsonSetter(nulls = Nulls.SKIP)
    @Schema(description = "등록한 사용자 ID", required = true, example = "gabrisal")
    private String regUserId = "JiYoung";

    @Size(max = 5
        , groups = { ValidationGroups.createBoardGroup.class, ValidationGroups.modifyBoardGroup.class })
    @Schema(description = "태그 목록")
    private List<Tag> tagList;

}
