package com.cos.photogramstart.domain.like;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name="likes_id",
						columnNames = {"imageid","userid"}
				)
		}
)
//어떤 이미지를 누가 좋아요
public class Likes { //    N

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@JoinColumn(name = "imageid")
	@ManyToOne
	private Image image;// 1
	
	@JoinColumn(name = "userid")
	@ManyToOne
	private User user; //      
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	
 
}
