package com.sanding.confessionwallback.common.exception;
//不允许删除
public class DeletionNotAllowedException extends BaseException {

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }

}
