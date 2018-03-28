package com.dg11185.hnyz.bean;

/**
 *
 */
public class User {
	private long ids;	// 编号
	private String openid;	// 微信ID
	private String nickname;  // 用户微信昵称
	private int sex;  // 性别
	private String province; //省份
	private String city;  //城市
	private String country;  //国家
	private String headimgurl;  //头像
	private String privilegess; //特权
	private String unionid;

	public long getIds() {return ids;}
	public void setIds(long ids) {
		this.ids = ids;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getPrivilegess() {
		return privilegess;
	}
	public void setPrivilegess(String privilegess) {
		this.privilegess = privilegess;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
