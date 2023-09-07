package id.fazzbca.daily_news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //disable csrf
        http.csrf(csrf ->{
            csrf.disable();
        });

        //session management
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        //authorize request
        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers("news/add").hasRole("CREATOR")
            .requestMatchers("/news/all").permitAll()
            .requestMatchers("news/read/{id}").permitAll()
            .requestMatchers("news/recent").permitAll()
            .requestMatchers("news/comment/{id}").permitAll()
            .requestMatchers("user/login").permitAll()
            .requestMatchers("user/register").permitAll()
            .requestMatchers("user/change-password/{id}").hasRole("USER")
            .requestMatchers("user/change-password/{id}").hasRole("ADMIN")
            .requestMatchers("admin/login").permitAll()
            .requestMatchers("admin/change-password/{id}").hasRole("ADMIN")
            .requestMatchers("creator/login").permitAll()
            .requestMatchers("creator/register").permitAll()
            .requestMatchers("creator/change-password/{id}").hasRole("CREATOR")
            .requestMatchers("creator/change-password/{id}").hasRole("ADMIN")
            .requestMatchers("category/add").hasRole("ADMIN")
            .requestMatchers("category/edit/{id}").hasRole("ADMIN")
            .requestMatchers("category/delete/{id}").hasRole("ADMIN")
            .requestMatchers("category/recycle/{id}").hasRole("ADMIN")
            .requestMatchers("category/available").permitAll()
            .requestMatchers("comment/add").hasRole("USER")
            .requestMatchers("files/add").hasRole("CREATOR")
            .requestMatchers("files/news/{imageId}").permitAll()
            .anyRequest().fullyAuthenticated();
        });

        //set authentication provider
        http.authenticationProvider(authenticationProvider());

        // set filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //basic auth
        // http.httpBasic();

        return http.build();
    }

    //untuk autentikasi user yang mau akses request dan/atau login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //DaoAuthenticationProvider : provider utuk mencari email dan match kan
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
}
