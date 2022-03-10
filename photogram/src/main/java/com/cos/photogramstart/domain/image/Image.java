package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime; 
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.like.Likes;
import com.cos.photogramstart.domain.user.User;
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
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String caption; // 사진 부가 설명
	private String postimageUrl; // 사진 -> 서버의 특정 폴더에 저장 -DB에 경로를 Insert
	
	@JsonIgnoreProperties({"images"}) // 유저 엔티티의 images 컬럼은 무시하라는 것
	@JoinColumn(name = "userid") //오브젝트X ,FK로 저장
	@ManyToOne(fetch = FetchType.EAGER)
	private User user; //누가 업로드 한 건지.

	//이미지 좋아요
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	
	//이미지 댓글
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
	private LocalDateTime createDate; //시간
	
	@Transient //데이터 테이블에서 사용하지 않는 칼럼 가리는 어노테이션
	private boolean likeState; 
	
	@Transient
	private int likeCount;
	
	@PrePersist
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}

	//Sysout 시에 image클래스를 호출하면, getter를 다 호출하다가 36번째 줄 User도 호출하고, 그럼 User엔티티안의 List<image>도 호출해서 계속
	//순환 된다. 그걸 방지하기 위해서 toString()을 만듦
	// => 한마디로 오브젝트 콘솔에 출력시에 문제 방지하기 위해서 User를 제외하고 만듦 ==> Sysout(entity) == Sysout(entity.toString()) 이라서 
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postimageUrl=" + postimageUrl + ", createDate=" + createDate + "]";
//	}
	
	
}
