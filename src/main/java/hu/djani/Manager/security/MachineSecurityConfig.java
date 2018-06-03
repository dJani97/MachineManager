package hu.djani.Manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import hu.djani.Manager.service.entity.UserService;

@Configuration
@EnableWebSecurity
public class MachineSecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
 	private static final String[] AUTH_WHITELIST = {
			"/",
			"/machine/list",
			"/api/**"
	};

 	private static final String[] AUTH_ANONYMOUS = {
 			"/login/**",
 			"/register/**",
 			"/confirmAccount/**"
	};
	// @formatter:on

	@Autowired
	private UserService userService;

	@Autowired
	private MachineSessionRegistryImpl sessionRegistryImpl;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers(AUTH_ANONYMOUS).anonymous()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.usernameParameter("email")
				.permitAll()
				.and()
			.logout()
				.addLogoutHandler(new CustomLogoutHandler(this.sessionRegistryImpl))
//				.clearAuthentication(true)
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(this.sessionRegistryImpl);

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

	// @Bean("machineSessionRegistryImpl")
	// public SessionRegistry sessionRegistry() {
	// return new MachineSessionRegistryImpl();
	// }
}
