package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(length = 40,unique = true)
	private String username;
	@Column(nullable = false)
	private String password;	
	@Column(nullable = false)
	private String name;
	private String website; //웹사이트
	private String bio; //자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; //사진
	private String role; //권한
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"})
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role +", createDate="
				+ createDate + "]";
	}
}
