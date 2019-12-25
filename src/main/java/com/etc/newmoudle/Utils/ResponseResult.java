package com.etc.newmoudle.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
public class ResponseResult {
    Integer status;//状态码
    String msg;//响应消息
    Object data;//返回的数据


    public ResponseResult(Object data){
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    //方法
    public static ResponseResult build(Integer status, String msg, Object data){
        return  new ResponseResult(status,msg,data);
    }
    //成功
    public static ResponseResult ok(Object data){
        return  new ResponseResult(data);
    }
    //错误消息在msg中(500)
    public static ResponseResult errorMsg(String msg){
        return  new ResponseResult(500,msg,null);
    }
}
