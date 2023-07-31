package com.kreamish.kream.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleRegisterResponseDto {
    private Long memberRoleId;
    private String name;
}
