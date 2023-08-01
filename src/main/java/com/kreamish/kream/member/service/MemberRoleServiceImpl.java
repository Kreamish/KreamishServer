package com.kreamish.kream.member.service;

import com.kreamish.kream.member.dto.MemberRoleDetailResponseDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterRequestDto;
import com.kreamish.kream.member.dto.MemberRoleRegisterResponseDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateRequestDto;
import com.kreamish.kream.member.dto.MemberRoleUpdateResponseDto;
import com.kreamish.kream.member.entity.MemberRole;
import com.kreamish.kream.member.repository.MemberRoleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    @Override
    public MemberRoleRegisterResponseDto saveMemberRole(
        MemberRoleRegisterRequestDto requestDto
    ) {
        MemberRole memberRole = MemberRole.builder()
            .name(requestDto.getName())
            .build();

        if (memberRoleRepository.findByName(requestDto.getName()).isPresent()) {
            throw new IllegalArgumentException(
                String.format("이미 존재하는 이름의 MemberRole 입니다. memberRoleId -> %s", memberRoleRepository.findByName(requestDto.getName())
                    .get().getName())
            );
        }

        memberRoleRepository.save(memberRole);
        return MemberRoleRegisterResponseDto.builder()
            .memberRoleId(memberRole.getMemberRoleId())
            .name(memberRole.getName())
            .build();
    }

    @Override
    public MemberRoleUpdateResponseDto updateMember(MemberRoleUpdateRequestDto requestDto) {
        MemberRole memberRole = memberRoleRepository.findById(requestDto.getMemberRoleId())
            .orElseThrow(() -> new NoSuchElementException("memberRoleId 로 해당 MemberRole 을 찾을 수 없습니다."));

        String prevName = memberRole.getName();
        memberRole.changeName(requestDto.getName());

        return MemberRoleUpdateResponseDto.builder()
            .memberRoleId(memberRole.getMemberRoleId())
            .prevName(prevName)
            .afterName(memberRole.getName())
            .build();
    }

    @Override
    public MemberRoleDetailResponseDto getMemberRole(Long memberRoleId) {
        MemberRole memberRole = memberRoleRepository.findById(memberRoleId)
            .orElseThrow(() -> new NoSuchElementException("memberRoleId 로 해당 MemberRole 을 찾을 수 없습니다."));

        return MemberRoleDetailResponseDto.builder()
            .memberRoleId(memberRole.getMemberRoleId())
            .name(memberRole.getName())
            .build();
    }

    @Override
    public List<MemberRoleDetailResponseDto> getAllMemberRoles() {
        return memberRoleRepository.findAll()
            .stream().map(role -> MemberRoleDetailResponseDto.builder()
                .memberRoleId(role.getMemberRoleId())
                .name(role.getName())
                .build()).collect(Collectors.toList()
            );
    }
}
