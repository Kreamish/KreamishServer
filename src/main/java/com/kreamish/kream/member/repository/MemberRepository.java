package com.kreamish.kream.member.repository;

import com.kreamish.kream.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
