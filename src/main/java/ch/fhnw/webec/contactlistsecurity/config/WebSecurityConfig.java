package ch.fhnw.webec.contactlistsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/login", "/stylesheet.css").permitAll()
                    .antMatchers(HttpMethod.GET, "/").authenticated()
                    .antMatchers("/add.html").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/").hasRole("ADMIN")
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .loginPage("/login");
    }

    @Bean

    @Override
    protected UserDetailsService userDetailsService() {
        final User.UserBuilder builder = User.withDefaultPasswordEncoder();
        final UserDetails user = builder.username("user").password("user").roles("USER").build();
        final UserDetails admin = builder.username("admin").password("admin").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
