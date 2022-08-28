package com.s_hashtag.common.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import com.s_hashtag.common.domain.member.model.entity.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.s_hashtag.common.domain.member.model.entity.QMember.member;

@Primary
@Repository
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberDto> findAll() {
        return queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.loginId,
                        member.name,
                        member.role,
                        member.password))
                .from(member)
                .fetch();
    }

    @Override
    public Optional<MemberDto> findById(String id) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.loginId,
                        member.name,
                        member.role,
                        member.password))
                .from(member)
                .where(member.loginId.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<MemberDto> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.loginId,
                        member.name,
                        member.role,
                        member.password))
                .from(member)
                .where(member.name.eq(name))
                .fetchOne());
    }

    @Override
    public void save(MemberDto memberDto) {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(memberDto.getLoginId()))
                .fetchOne();

        if(findMember == null) {
            Member member = Member.builder()
                    .loginId(memberDto.getLoginId())
                    .name(memberDto.getName())
                    .role(memberDto.getRole())
                    .password(memberDto.getPassword())
                    .build();

            em.persist(member);
        } else {
            findMember = Member.builder()
                    .loginId(memberDto.getLoginId())
                    .name(memberDto.getName())
                    .role(memberDto.getRole())
                    .password(memberDto.getPassword())
                    .build();
//            findMember.setLoginId(memberDto.getLoginId());
//            findMember.setName(memberDto.getName());
//            findMember.setRole(memberDto.getRole());
//            findMember.setPassword(memberDto.getPassword());
        }
    }

    @Override
    public void delete(List<String> idList) {
        List<Member> findMembers = queryFactory
                .selectFrom(member)
                .where(member.loginId.in(idList))
                .fetch();

        for(Member memberItem : findMembers) {
            em.remove(memberItem);
        }
    }
}
