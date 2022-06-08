package com.gabrisal.api.board.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;

@Schema(description = "태그 Model")
@Builder @Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Schema(description = "태그 ID", required = true, example = "1")
    private int tagId;

    @Schema(description = "태그명", required = true, example = "태그")
    private String tagName;

    @Schema(description = "삭제여부", example = "0")
    private boolean delYn;

    @Schema(description = "최초등록사용자ID", example = "gabrisal")
    private String frstRegUserId;

    @Schema(description = "최초등록사용자IP주소")
    private String frstRegUserIpAddr;

    @Schema(description = "최초등록일시", example = "20220602")
    @JsonFormat(pattern = "yyyyMMdd", shape = JsonFormat.Shape.STRING)
    private Timestamp frstRegDttm;

    @Schema(description = "최종수정사용자ID", example = "gabrisal")
    private String lastUpdUserId;

    @Schema(description = "최종수정사용자IP주소")
    private String lastUpdUserIpAddr;

    @Schema(description = "최종수정일시", example = "20220602")
    @JsonFormat(pattern = "yyyyMMdd", shape = JsonFormat.Shape.STRING)
    private Timestamp lastUpdDttm;

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag) {
            return tagName.equals(((Tag)obj).tagName);
        }
        return false;
    }
}
