package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
				System.out.println("들어옴");
				OAuth2User oauth2User = super.loadUser(userRequest);
				
				OAuth2UserInfo oAuth2UserInfo = null;
				
				// attribute값을 대조해서 플랫폼 구별
				if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
					oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
				}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
					oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
				}else {
					System.out.println("가입을 진행할 수 없습니다.");
				}
				
		        String provider =oAuth2UserInfo.getProvider();
		        String email = oAuth2UserInfo.getEmail();
		        String username =oAuth2UserInfo.getProviderId();
		        String providerID = provider+"_"+username;
		        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		        String role ="ROLE_USER";
				String name  = (String) oAuth2UserInfo.getName();
//				String username = "facebook_"+(String) userInfo.get("id");
//				String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
//				String email = (String) userInfo.get("email");
//				String name = (String) userInfo.get("name");
				
				User userEntity = userRepository.findByUsername(username);
				

				if(userEntity == null) { // OAuth2.0 최초 로그인
					
					User user = User.builder()
							.username(providerID)
							.password(password)
							.provider(provider)
							.email(email)
							.name(name)
							.role(role)
							.build();
					System.out.println("최초 로그인 끝");
					return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes()); //세션 return
				}else { // OAuth2.0 이미 아이디가 있음
					System.out.println("이미 아이디가 있음");
					return new PrincipalDetails(userEntity, oauth2User.getAttributes());
				}
	}
}
