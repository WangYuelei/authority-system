package com.wyl.config.security.exception;



/**
 * 自定义异常类
 */

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证异常类
 */
public class CustomerAuthenticationException extends AuthenticationException {
    public CustomerAuthenticationException(String message){
        super(message);
    }
}
