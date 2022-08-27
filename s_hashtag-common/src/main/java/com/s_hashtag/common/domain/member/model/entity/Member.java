package com.s_hashtag.common.domain.member.model.entity;

import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
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
}
