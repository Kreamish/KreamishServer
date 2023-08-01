package com.kreamish.kream.member.controller;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.member.dto.MemberRoleDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterRequestDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterResponseDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateRequestDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateResponseDto;
import com.kreamish.kream.member.service.MemberRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-roles")
public class MemberRoleController {

    private final MemberRoleService memberRoleService;

    @PostMapping
    public ResponseEntity<ApiUtils.ApiResult<MemberRoleRegisterResponseDto>> registerMemberRole(
        @RequestBody MemberRoleRegisterRequestDto requestDto
    ) {
        MemberRoleRegisterResponseDto response = memberRoleService.saveMemberRole(requestDto);
        return new ResponseEntity<>(ApiUtils.success(response), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiUtils.ApiResult<MemberRoleUpdateResponseDto>> updateMember(
        @RequestBody MemberRoleUpdateRequestDto requestDto
    ) {
        Long memberRoleId = requestDto.getMemberRoleId();
        if (memberRoleId == null || memberRoleId == 0L) {
            throw new IllegalArgumentException("memberRoleId 값은 null 이거나, 0일 수 없습니다.");
        }
        return ResponseEntity.ok(ApiUtils.success(memberRoleService.updateMember(requestDto)));
    }

    @GetMapping("/{member-role-id}")
    public ResponseEntity<ApiUtils.ApiResult<MemberRoleDetailResponseDto>> inquiryMemberRole(
        @PathVariable("member-role-id") Long memberRoleId
    ) {
        return ResponseEntity.ok(ApiUtils.success(memberRoleService.getMemberRole(memberRoleId)));
    }

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<List<MemberRoleDetailResponseDto>>> inquiryAllMemberRole() {
        List<MemberRoleDetailResponseDto> response = memberRoleService.getAllMemberRoles();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(ApiUtils.success(response), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
