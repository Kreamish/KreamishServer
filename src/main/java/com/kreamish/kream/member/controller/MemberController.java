package com.kreamish.kream.member.controller;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.member.dto.MemberDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRegisterRequestDto;
import com.kreamish.kream.member.dto.MemberRegisterResponseDto;
import com.kreamish.kream.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(
        summary = "회원 등록",
        description = "회원 가입"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "정상 회원 생성")
    })
    public ResponseEntity<ApiUtils.ApiResult<MemberRegisterResponseDto>> registerMember(
        @RequestBody @Valid MemberRegisterRequestDto registerDto
    ) {
        Long memberId = memberService.registerMember(registerDto);
        if (memberId == null || memberId == 0L) {
            throw new IllegalStateException(String.format("memberId is null or 0.. memberId -> %d", memberId));
        }

        return ResponseEntity.created(URI.create("/members/" + memberId))
                .body(ApiUtils.success(new MemberRegisterResponseDto(memberId)));
    }

    @GetMapping("/{member-id}")
    @Operation(
        summary = "회원 조회",
        description = "회원 정보 상세 조회"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content),
        @ApiResponse(responseCode = "200", description = "정상 반환"),
    })
    public ResponseEntity<ApiUtils.ApiResult<MemberDetailResponseDto>> inquiryMember(
        @PathVariable(value = "member-id") Long memberId
    ) {
        return ResponseEntity.ok(ApiUtils.success(memberService.getMemberWithMemberRole(memberId)));
    }

    @GetMapping
    @Operation(
        summary = "전체 회원 조회",
        description = "전체 회원 정보 상세 조회 페이징 적용"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "회원이 존재하지 않는 경우"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @Parameters({
        @Parameter(name = "page", description = "받고자 하는 페이지의 번호 default: 0"),
        @Parameter(name = "size", description = "한 페이지 당 개수 default: 10")
    })
    public ResponseEntity<ApiUtils.ApiResult<Page<MemberDetailResponseDto>>> inquiryMemberList(
        @Schema(hidden = true) @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<MemberDetailResponseDto> response = memberService.getMemberPageList(pageable);
        if (response.isEmpty()) {
            return new ResponseEntity<>(ApiUtils.success(response), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
