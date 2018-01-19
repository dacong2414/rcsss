/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.model.exception;

import com.ambition.rcsss.model.common.CodeEnum;

/**
 *
 * @author 晁宇航
 * @version $Id: BaseException.java, v 0.1 2017年6月16日 下午7:21:40 晁宇航 Exp $
 */
public class BaseException extends RuntimeException {

    /** */
    private static final long serialVersionUID = 7980605393981373039L;

    private CodeEnum          code;

    public BaseException() {

    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(CodeEnum code) {
        super(code.getMsg());
        this.code = code;
    }

    public CodeEnum getCode() {
        return code;
    }
}
