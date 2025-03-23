package gr.hua.it21774.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.DefaultTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import gr.hua.it21774.context.LdapUserDetailsContextMapper;
import gr.hua.it21774.service.ExternalUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final AuthEntryPointJwt authEntryPointJwt;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    private final String ldapUrl;
    private final String userDnPattern;
    private final String userSearchFilter;
    private final ExternalUserDetailsService externalUserDetailsService;

    public SecurityConfig(AuthEntryPointJwt authEntryPointJwt, AuthTokenFilter authenticationJwtTokenFilter,
            @Value("${ldap.url}") String ldapUrl,
            @Value("${ldap.user-dn-pattern}") String userDnPattern,
            @Value("${ldap.search-filter}") String userSearchFilter,
            ExternalUserDetailsService externalUserDetailsService) {
        this.authEntryPointJwt = authEntryPointJwt;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
        this.ldapUrl = ldapUrl;
        this.userDnPattern = userDnPattern;
        this.userSearchFilter = userSearchFilter;
        this.externalUserDetailsService = externalUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        http
                .cors(cors -> cors.configurationSource((request) -> corsConfiguration))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login/**", "/login-external/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/upload/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.POST, "/assignment-dates").hasAuthority("SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/assignment-dates").hasAuthority("SECRETARY")
                        .requestMatchers(HttpMethod.POST, "/reviewing-dates").hasAuthority("SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/reviewing-dates").hasAuthority("SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/external-users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/external-users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/hua-users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/theses").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/theses/me").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/theses/{id}").hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.PUT, "/theses/{id}/assign-student")
                        .hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/theses/my-assignment").hasAuthority("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/theses/{id}/reset")
                        .hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.PUT, "/theses/{id}/status")
                        .hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/theses/assigned-reviews").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/theses/{id}/grade").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/theses/{id}/publish").hasAuthority("STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/theses/{id}/requests").hasAuthority("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/theses/{id}/requests").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/theses/{id}/requests/{pdf}").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/theses/{id}/tasks").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/theses/{id}/tasks").hasAnyAuthority("STUDENT", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/theses/{id}/tasks/{taskId}")
                        .hasAnyAuthority("STUDENT", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/theses/{id}/tasks/{taskId}").hasAuthority("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/theses/{id}/tasks/{taskId}/{filename}")
                        .hasAnyAuthority("STUDENT", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/theses/{id}/**").hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.POST, "/assign-reviewers").hasAnyAuthority("PROFESSOR", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/search-students").hasAnyAuthority("PROFESSOR", "SECRETARY")

                        .requestMatchers("/actuator/health/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPointJwt));

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapUrl);
        ldapContextSource.setAuthenticationStrategy(new DefaultTlsDirContextAuthenticationStrategy());

        return ldapContextSource;
    }

    @Bean
    @Qualifier(value = "ldap-auth")
    AuthenticationManager ldapAuthenticationManager(LdapContextSource ldapContextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(
                ldapContextSource);

        factory.setUserDnPatterns(userDnPattern);
        factory.setUserSearchFilter(userSearchFilter);
        factory.setUserDetailsContextMapper(new LdapUserDetailsContextMapper());

        return factory.createAuthenticationManager();
    }

    @Bean
    @Qualifier(value = "external-auth")
    AuthenticationManager externalAuthenticationManager() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(externalUserDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoAuthProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
