package com.huangzhipeng.cms.entity;

import java.io.Serializable;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月18日 下午7:36:00
*类功能说明
*/
public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6096125469885563254L;
	
	private Integer id;
	private String name;
	private Integer channelId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", channelId=" + channelId + "]";
	}
	

}
