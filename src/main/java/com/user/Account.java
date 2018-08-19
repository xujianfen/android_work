package com.user;

import java.util.List;

public class Account {

	private int id;//id 1
	private String username;//用户名 2
	private String password;//密码 3
	private String phone;//手机号 4
	private String attention;//关注漫画 5
	private int attentionnum;//关注漫画数量 6
	private String idAccount;//账号7
	private String history;//阅读历史8
	private int comicsnum;//我的漫画数量  9
	private String comics;//漫画作品 10
	private String head;//头像 12
	private String cover;//封面 13
	private String gender;//性别 14
	private String introduce;//介绍 15
	private String industry;//工作室 16
	public Account(){
		super();
	}
	public Account(int id, String username, String password, String phone, String attention,
				   int attentionnum, String idAccount, String history, int comicsnum, String comics,
				   String head, String cover, String gender, String introduce, String industry) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.attention = attention;
		this.attentionnum = attentionnum;
		this.idAccount = idAccount;
		this.history = history;
		this.comicsnum = comicsnum;
		this.comics = comics;
		this.head = head;
		this.cover = cover;
		this.gender = gender;
		this.introduce = introduce;
		this.industry = industry;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", attention=" + attention + ", attentionnum=" + attentionnum + ", idAccount=" + idAccount
				+ ", history=" + history + ", comicsnum=" + comicsnum + ", comics=" + comics + ", head=" + head
				+ ", cover=" + cover + ", gender=" + gender + ", introduce=" + introduce + ", industry=" + industry
				+ "]";
	}


	public String toNewString() {
		return "Account {\"id\":" + id + ",\"username\":" + username + ", \"password\":" + password + ",\"phone\":" + phone
				+ ", \"attention\":" + attention + ", \"attentionnum\":" + attentionnum + ", \"idAccount\":" + idAccount
				+ ", \"history\":" + history + ", \"comicsnum\":" + comicsnum + ", \"comics\":" + comics + ", \"head\":" + head
				+ ", \"cover\":" + cover + ", \"gender\":" + gender + ", \"introduce\":" + introduce + ", \"industry\":" + industry
				+ "}";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public int getAttentionnum() {
		return attentionnum;
	}
	public void setAttentionnum(int attentionnum) {
		this.attentionnum = attentionnum;
	}
	public String getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public int getComicsnum() {
		return comicsnum;
	}
	public void setComicsnum(int comicsnum) {
		this.comicsnum = comicsnum;
	}
	public String getComics() {
		return comics;
	}
	public void setComics(String comics) {
		this.comics = comics;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
