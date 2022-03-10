package com.cos.photogramstart.config.auth;

import java.util.ArrayList; 
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
    // 일반 로그인 시에 사용 생산자	
	public PrincipalDetails (User user) {
		this.user=user;
	}
    // OAuth 로그인 시에 사용되는 생성자	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes =attributes;
	}
    //해당 User의 권한을 리턴하는 기능	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()->{return user.getRole();});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}


}
