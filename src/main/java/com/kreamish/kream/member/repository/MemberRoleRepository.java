package com.kreamish.kream.member.repository;

import com.kreamish.kream.member.entity.MemberRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    Optional<MemberRole> findByName(String name);
}
