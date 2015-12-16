package cn.ifactory.hypm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 用户
 * @author yaha
 *
 */
@Entity
@Table(name="hypm_user")
public class User extends BaseEntity {
	@Column(unique=true,nullable=false)
	private String username;
	@Column(nullable=false)
	private String nickname;
	@Column(nullable=false)
	@JsonIgnore
	private String password;
	@Transient
	@JsonIgnore
	private String confirmPassword;
	@OneToOne(targetEntity=Image.class,fetch=FetchType.LAZY)
	@JoinColumn(name="picture_id")
	@JsonIgnore
	private Image picture;
	private String email;
	private String telphone;
	private boolean admin = false;
	private boolean useable = false;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Image getPicture() {
		return picture;
	}
	public void setPicture(Image picture) {
		this.picture = picture;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isUseable() {
		return useable;
	}
	public void setUseable(boolean useable) {
		this.useable = useable;
	}
	
	
	
	
}
