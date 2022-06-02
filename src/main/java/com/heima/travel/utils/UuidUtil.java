package com.heima.travel.utils;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 * UUID，全局唯一码，根据网卡的mac地址，当前系统时间，cpu类型等等计算出一个32个长度唯一值。
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
	/**
	 * 测试
	 */
	public static void main(String[] args) {
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
	}
}
