package com.s_hashtag.common.domain.member.model.entity;

import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String loginId;

    private String name;

    private String password;

    private String role;

    @Builder
    public Member(String loginId, String name, String password, String role) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
