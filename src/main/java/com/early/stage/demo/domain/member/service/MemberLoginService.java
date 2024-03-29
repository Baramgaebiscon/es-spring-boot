package com.early.stage.demo.domain.member.service;

import com.early.stage.demo.domain.member.entity.Member;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * use dummy data for last login date of member
 */
@Service
public class MemberLoginService {

    public void modifyMemberLoginDate(long memberId, LocalDateTime loginTime) {
        Member.lastLoginTime = loginTime;
    }

    public Date findMemberLoginDate(long memberId) {
        return (Date) Timestamp.valueOf(Member.lastLoginTime);
    }
}
