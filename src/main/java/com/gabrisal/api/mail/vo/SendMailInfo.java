package com.gabrisal.api.mail.vo;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class SendMailInfo {

    private List<String> receiverMailList;
    private String subject;
    private String content;

}
