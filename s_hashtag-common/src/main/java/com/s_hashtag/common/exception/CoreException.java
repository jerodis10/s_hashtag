package com.s_hashtag.common.exception;

public class CoreException extends HashtagMapException {

    public CoreException(CoreExceptionStatus coreExceptionStatus, String detailMessage) {
        super(coreExceptionStatus.getMessage(), coreExceptionStatus.getCode(), detailMessage);
    }
}


