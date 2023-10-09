package com.kreamish.kream.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMemberInfo {

    public static final Long NOT_LOGGED_IN_MEMBER_ID = 0L;
    
    private Long memberId;
    
    public Boolean isNotLoggedIn() {
        return NOT_LOGGED_IN_MEMBER_ID.equals(memberId);
    }
}