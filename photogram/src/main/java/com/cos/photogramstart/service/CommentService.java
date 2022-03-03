package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	@Transactional
	public Comment writeComment(User user, String content, int imageId) {
		
		Image image = Image.builder()
				.id(imageId)
				.build();
		
		Comment comment = Comment.builder()
				.content(content)
				.image(image)
				.user(user)
				.build();
		
		return commentRepository.save(comment);
		
	}
	
	@Transactional
	public void deleteComment(int id, int principalId) {
		Comment commententity = commentRepository.findById(id).get();
		if(commententity.getUser().getId() == principalId) {
			commentRepository.deleteById(id);
		}
	}
}
