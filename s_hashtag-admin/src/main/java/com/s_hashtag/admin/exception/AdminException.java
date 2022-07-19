package com.s_hashtag.admin.exception;

import com.s_hashtag.common.exception.CommonExceptionStatus;
import com.s_hashtag.common.exception.HashtagMapException;

public class AdminException extends HashtagMapException {
    public AdminException(final String errorMessage, final String errorCode) {
        super(errorMessage, errorCode);
    }

    public AdminException(AdminExceptionStatus adminExceptionStatus) {
        super(adminExceptionStatus.getMessage(), adminExceptionStatus.getCode());
    }

    public AdminException(AdminExceptionStatus adminExceptionStatus, String detailMessage) {
        super(adminExceptionStatus.getMessage(), adminExceptionStatus.getCode(), detailMessage);
    }

    public AdminException(final CommonExceptionStatus commonExceptionStatus, final String detailMessage) {
        super(commonExceptionStatus, detailMessage);
    }
}
