package com.heima.travel.exception;

//自定义异常处理器，用于封装异常信息，对异常进行分类,继承的运行时异常
public class BusinessException extends RuntimeException{
    /*
       属性
       1. code 状态码
       2. message 异常信息(在父类中)
    */
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    //有参构造，2参数
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    //有参构造，3参数
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
