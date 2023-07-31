package com.kreamish.kream.member.dto;

import com.kreamish.kream.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponseDto {

    private Long memberId;
    private String email;
    private String password;
    private MemberDetailResponseDtoMemberRole memberRole;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDetailResponseDtoMemberRole {
        private Long memberRoleId;
        private String name;
    }

    public static MemberDetailResponseDtoMemberRole getMemberRole(MemberRole memberRole){
        return MemberDetailResponseDtoMemberRole.builder()
            .memberRoleId(memberRole.getMemberRoleId())
            .name(memberRole.getName())
            .build();
    }
}
