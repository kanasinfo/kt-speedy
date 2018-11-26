package com.kanasinfo.aliyun.sms

import java.lang.RuntimeException

class SmsSendException: RuntimeException {
    private var code: String
    private var msg: String
    constructor(code: String, msg: String): super("the error code: $code, message: $msg"){
        this.code = code
        this.msg = msg

    }

}