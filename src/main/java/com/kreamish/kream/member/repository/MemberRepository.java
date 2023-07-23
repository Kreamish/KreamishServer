package com.kreamish.kream.member.repository;

import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    void save(MemberRole build);
}