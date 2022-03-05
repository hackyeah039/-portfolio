package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	@Transactional
	public Comment writeComment(String content,int imageid,int userid) {
		
		Image image =new Image();
		image.setId(imageid);
		User userEntity = userRepository.findById(userid).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을수 없습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setUser(userEntity);
		comment.setImage(image);
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void deleteComment(int id) {
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}
//	Image image = Image.builder()
//			.id(imageId)
//			.build();
//	
//	Comment comment = Comment.builder()
//			.content(content)
//			.image(image)
//			.user(user)
//			.build();
