package com.huangzhipeng.cms.entity;
/**
*@author huangzhipeng
*@version 创建时间：2019年9月20日 下午1:37:39
*类功能说明
*/
public class VoteStatic {
	
	//
	String optionKey;
	Integer voteNum;
	String optionTitle;
	Integer voteNumTotal;
	
	public Integer getVoteNumTotal() {
		return voteNumTotal;
	}
	public void setVoteNumTotal(Integer voteNumTotal) {
		this.voteNumTotal = voteNumTotal;
	}
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	public Integer getVoteNum() {
		return voteNum;
	}
	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	
}
