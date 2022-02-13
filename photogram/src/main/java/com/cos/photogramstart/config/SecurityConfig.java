package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //시큐리티 활성화
@Configuration //IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Config가 IOC에 등록될 때, Bean읽어서 얘도 같이 등록하고,
	//new BCryptPasswordEncoder() 새로 만듦
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/","user/**","image/**","/subscribe/**","/comment/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/auth/signin") //GET
		.loginProcessingUrl("/auth/signin") //POST -> 스프링 시큐리티가 로그인 프로세스를 진행
		.defaultSuccessUrl("/");
		
	}
}
