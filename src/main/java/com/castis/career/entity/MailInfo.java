package com.castis.career.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MailInfo {

    @Id
    private String mail_info_id;

    private String send_address;

    private String admin_address;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;
}