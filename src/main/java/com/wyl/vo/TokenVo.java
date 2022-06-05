package com.wyl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * token
     */
    private String token;
}
