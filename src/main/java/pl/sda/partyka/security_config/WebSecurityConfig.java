package pl.sda.partyka.security_config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.sda.partyka.service.UserService;

import static pl.sda.partyka.utils.Utils.DEVELOP_PROFILE;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Value("${running.profile}")
    private String runningProfile;


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (runningProfile.equals(DEVELOP_PROFILE)) {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/events/**").permitAll()
                    .antMatchers("/base/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true).clearAuthentication(true)
                    .logoutSuccessUrl("/").permitAll()
                    .and()
                    .csrf().ignoringAntMatchers("/base/**")
                    .and()
                    .headers().frameOptions().sameOrigin();
        } else {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/events/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true).clearAuthentication(true)
                    .logoutSuccessUrl("/").permitAll();
        }
    }
}
