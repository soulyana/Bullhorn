package me.soulyana.bullhorn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserRepository users;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUDS(users);
    }

    private String[] everyone = {"/", "/register", "/login", "/assets/**"};
    private String[] appUser = {"/posts", "/comments"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(everyone).permitAll()
                .antMatchers(appUser).hasAuthority("APPUSER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        //For H2
        http.csrf().disable();

        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("AppUser")
                .password(passwordEncoder().encode("bullhorn")).authorities("APPUSER")
                .and()
                .passwordEncoder(passwordEncoder());
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }
}
