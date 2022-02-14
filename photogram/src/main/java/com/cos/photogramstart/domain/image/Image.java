package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String caption; // 사진 부가 설명
	private String postimageUrl; // 사진 -> 서버의 특정 폴더에 저장 -DB에 경로를 Insert
	
	@JoinColumn(name = "userid") //오브젝트X ,FK로 저장
	@ManyToOne
	private User user; //누가 업로드 한 건지.

	//이미지 좋아요
	//이미지 댓글
	
	private LocalDateTime createDate; //시간
	
	@PrePersist
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
}
