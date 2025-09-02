package com.inside.ddf.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String userId;        // 아이디
    private String userPassword;  // 비밀번호(평문)
    private String nickNm;        // 닉네임

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDt;    // 생년월일

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pregStart;  // 임신 시작일
}
