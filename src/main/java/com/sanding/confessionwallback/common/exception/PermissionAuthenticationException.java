package com.sanding.confessionwallback.common.exception;

/**
 * 权限认证异常
 */
public class PermissionAuthenticationException extends BaseException{
    public PermissionAuthenticationException(){

    }

    public PermissionAuthenticationException(String msg){
        super(msg);
    }
}
