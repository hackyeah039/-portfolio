package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
// NotNull = Null값 체크
// NotBlank= 빈값 or Null or 공백 체크
// NotEmpty= 빈값 or null 체크
@Data
public class CommentDto {
	@NotBlank 
	private String content;
	@NotNull 
	private Integer imageid;
}
