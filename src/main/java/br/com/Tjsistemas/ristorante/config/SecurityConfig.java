package br.com.Tjsistemas.ristorante.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.Tjsistemas.ristorante.security.AppUserDetailsService;
import br.com.Tjsistemas.ristorante.security.MyAuthenticationProvider;
import br.com.Tjsistemas.ristorante.security.TowFactorAuthenticantionFilter;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
    private MyAuthenticationProvider authProvider;	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/layout/**")
           .antMatchers("/images/**")
           .antMatchers( HttpMethod.GET , "/empresas");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http 
		.addFilterBefore(authenticantionFilter(), UsernamePasswordAuthenticationFilter.class) // adicionar um campo a mais no login 
		.authorizeRequests()
		.antMatchers("/camareiro/**").hasRole("CADASTRO_CLIENTE")    // acresceta ROLE_ no banco. .hasAuthority() do jeito que esta no banco
			.anyRequest().authenticated()                         // libera tudo que nao foi informado acima
			.and()
			.formLogin()
		    .loginPage("/login")                                   
		    .permitAll()
		    .and()
		  .logout()
		  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   // crfs liberado para logout
			.and()
		  .exceptionHandling().accessDeniedPage("/403")                // erro acesso negado
		  .and()
//		  .sessionManagement().maximumSessions(1).expiredUrl("/login"); // maximo de usuarios por conta    
		  .sessionManagement()
		  .invalidSessionUrl("/login")
		  ;    // Redireciona quando sessao expira e tenta fazer um POST
	}
	

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	// adicionar um campo a mais no login
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public TowFactorAuthenticantionFilter authenticantionFilter() throws Exception{
        TowFactorAuthenticantionFilter authFilter = new TowFactorAuthenticantionFilter();
	    
        authFilter.setAuthenticationManager(authenticationManager());
       
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST")); 
        authFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/comanda/comanda"));
       
        authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
		authFilter.setUsernameParameter("username");
	    authFilter.setPasswordParameter("password");
        authFilter.setExtraParameter("empresa");
		
		return authFilter;
	}
}