/**
 * 
 */
package com.ternnetwork.baseframework.service.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *bsierp_ssj
 * @author wenfeng.xu
 *2011-5-19下午09:02:53
 */
public interface ImageService {
	/**
	 * 给定原始图片的长宽和定制长宽
	 * 返回优化后的长宽
	 * @author wenfeng.xu
	 * @param cusW
	 * @param cusH
	 * @param srcW
	 * @param srcH
	 * @return
	 */
	public Integer[] filterSize(Integer cusW, Integer cusH, Integer srcW,Integer srcH) ;
	
	public byte[] imageScaling(HttpServletResponse response,HttpServletRequest request);
	

}
