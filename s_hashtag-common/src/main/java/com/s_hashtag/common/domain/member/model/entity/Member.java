package com.s_hashtag.common.domain.member.model.entity;

import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void chaneMember(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @Builder
    public Member(String loginId, String name, String password, String role) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
