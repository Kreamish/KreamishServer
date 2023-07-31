package com.kreamish.kream.member.repository;

import com.kreamish.kream.member.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.memberRole where m.memberId = :memberId")
    Optional<Member> findMemberWithMemberRole(@Param("memberId") Long memberId);

    Page<Member> findByMemberRoleIsNotNull(Pageable pageable);
}
