package com.kreamish.kream.member.service;

import com.kreamish.kream.member.dto.MemberDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRegisterRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    Long registerMember(MemberRegisterRequestDto registerDto);

    MemberDetailResponseDto getMemberWithMemberRole(Long memberId);

    Page<MemberDetailResponseDto> getMemberPageList(Pageable pageable);
}
