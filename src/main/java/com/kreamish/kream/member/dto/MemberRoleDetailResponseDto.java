package com.kreamish.kream.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRoleDetailResponseDto {
    private Long memberRoleId;
    private String name;
}
