package com.kreamish.kream.member.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MemberRegisterRequestDto {

    @Length(min = 4, max = 200)
    private String email;

    @Length(min = 4, max = 256)
    private String password;

    private Long memberRoleId;
}
