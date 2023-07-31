package com.kreamish.kream.member.service;

import com.kreamish.kream.member.dto.MemberRoleDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterRequestDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterResponseDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateRequestDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateResponseDto;
import java.util.List;

public interface MemberRoleService {
    MemberRoleRegisterResponseDto saveMemberRole(MemberRoleRegisterRequestDto requestDto);

    MemberRoleUpdateResponseDto updateMember(MemberRoleUpdateRequestDto requestDto);

    MemberRoleDetailResponseDto getMemberRole(Long memberRoleId);

    List<MemberRoleDetailResponseDto> getAllMemberRoles();
}
