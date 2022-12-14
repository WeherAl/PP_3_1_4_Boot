package ru.kata.spring.boot_security.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImp userDetails;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImp userDetails) {
        this.successUserHandler = successUserHandler;
        this.userDetails = userDetails;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**", "/", "/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(encoder());
    }

    @Bean
    public static ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}