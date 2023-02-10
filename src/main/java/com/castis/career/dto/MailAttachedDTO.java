package com.castis.career.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailAttachedDTO {

    private String address;
    private String ccAddress;
    private String title;
    private String content;
    private String fileName;
//    private List<> attachFileList = new ArrayList<>();

}
