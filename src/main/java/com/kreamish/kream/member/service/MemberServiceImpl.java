package com.kreamish.kream.member.service;

import com.kreamish.kream.member.dto.MemberDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRegisterRequestDto;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.entity.MemberRole;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.member.repository.MemberRoleRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public Long registerMember(MemberRegisterRequestDto registerDto) {
        Optional<MemberRole> findMemberRole = memberRoleRepository.findById(registerDto.getMemberRoleId());
        if (findMemberRole.isEmpty()) {
            throw new IllegalArgumentException("memberRoleId 로 조회된 memberRole 이 없습니다.");
        }
        MemberRole memberRole = findMemberRole.get();

        Member saveMember = memberRepository.save(Member.builder()
            .memberRole(memberRole)
            .email(registerDto.getEmail())
            .password(registerDto.getPassword())
            .build());

        return saveMember.getMemberId();
    }

    public MemberDetailResponseDto getMemberWithMemberRole(Long memberId) {
        Optional<Member> findMember = memberRepository.findMemberWithMemberRole(memberId);
        if (findMember.isEmpty()) {
            throw new NoSuchElementException("memberId 로 해당 회원을 찾을 수 없습니다.");
        }

        Member member = findMember.get();
        MemberRole memberRole = member.getMemberRole();

        return MemberDetailResponseDto.builder()
            .memberRole(MemberDetailResponseDto.getMemberRole(memberRole))
            .memberId(memberId)
            .email(member.getEmail())
            .password(member.getPassword())
            .build();
    }

    @Override
    public Page<MemberDetailResponseDto> getMemberPageList(
        Pageable pageable
    ) {
        return memberRepository.findByMemberRoleIsNotNull(
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
            )
        ).map(member -> MemberDetailResponseDto.builder()
            .memberId(member.getMemberId())
            .memberRole(
                MemberDetailResponseDto.getMemberRole(member.getMemberRole())
            )
            .email(member.getEmail())
            .build()
        );
    }
}