package com.std.sbb.answer;

import com.std.sbb.question.Question;
import com.std.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Answer {
    @Id // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne // 아래 처럼 다른 엔티티 클래스 리모콘을 저장할 때는 꼭 관계를 적어준다.
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;
}
