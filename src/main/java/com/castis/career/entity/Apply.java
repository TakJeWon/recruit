package com.castis.career.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="board_id")
    private Board board;

    private String name;

    private String birthday;

    private String email;

    private String filename;

    private String filepath;

    @CreationTimestamp
    private LocalDateTime creation_time;
}
