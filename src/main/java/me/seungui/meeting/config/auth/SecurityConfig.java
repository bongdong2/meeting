package me.seungui.meeting.config.auth;

import lombok.RequiredArgsConstructor;
import me.seungui.meeting.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOauth2UserService);
    }

    // @EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다.
    // csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션들을 disable한다.
    // authorizeRequests() : URL별 권한 관리를 설정하는 옵션의 시작점, 이게 선언되어야 antMatcher 옵션을 사용할 수 있다.
    // antMatchers : 권한 관리 대상을 지정하는 옵션, URL, HTTP 메소드 별로 관리 가능, "/"등 지정된 URL들은 permilAll() 옵션을 통해 전체 열람 권한 부여, POST 메소드이면서 "/api/v1/**" 주소를 가진 API는 USER권한을 가진 사람만 가능하도록 했다.
    // anyRequest : 설정된 값드 이외 나머지 URL들을 나타낸다.
}
