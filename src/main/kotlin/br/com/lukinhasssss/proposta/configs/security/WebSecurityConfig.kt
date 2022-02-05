package br.com.lukinhasssss.proposta.configs.security

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

//    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private val issuer: String = String()
//
//    @Bean
//    fun jwtDecoder(): JwtDecoder {
//        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
//        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
//        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer)
//        jwtDecoder.setJwtValidator(withAudience)
//        return jwtDecoder
//    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_proposal:read")
            .antMatchers(HttpMethod.GET, "/proposals/**").hasAuthority("SCOPE_proposal:read")
            .antMatchers(HttpMethod.GET, "/cards/**").hasAuthority("SCOPE_proposal:read")
            .antMatchers(HttpMethod.POST, "/cards/**").hasAuthority("SCOPE_proposal:write")
            .antMatchers(HttpMethod.POST, "/proposals/**").hasAuthority("SCOPE_proposal:write")
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer().jwt()
    }
}
