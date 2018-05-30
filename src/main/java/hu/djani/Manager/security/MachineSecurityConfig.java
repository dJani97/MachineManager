package hu.djani.Manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import hu.djani.Manager.service.UserService;

@Configuration
@EnableWebSecurity
public class MachineSecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
 	private static final String[] AUTH_WHITELIST = {
			"/hello/**",
	};
	// @formatter:on

	@Autowired
	private UserService userService;

	// @Bean
	// @Override
	// public AuthenticationManager authenticationManagerBean() throws Exception {
	// return super.authenticationManagerBean();
	// }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// erdekessegek / lehetosegek:
		//
		// auth.ldapAuthentication()
		// auth.jdbcAuthentication()
		auth.inMemoryAuthentication().withUser("asd").password("{noop}asd").roles("ADMIN").and().withUser("sdf")
				.password("{noop}sdf").roles("USER");

		// auth.userDetailsService(this.userService).passwordEncoder(new
		// BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers("/login/**").anonymous()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
//				.loginPage("/login")
//				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.permitAll();
//				.and()
//			.logout()
//				.logoutSuccessUrl("/login?logout")
//				.permitAll();



		// hiba eseten iranyitson vissza /login -ra:
		http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

		// mit csinaljon akkor, ha olyan jelentkezik be aki mar be volt jelentkezve:
		// session fixation -> atmasolja az elozo session adatait az ujba
		http.sessionManagement().sessionAuthenticationStrategy(new SessionFixationProtectionStrategy());

		// @formatter:on
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**").antMatchers("/css/**");
	}

}
