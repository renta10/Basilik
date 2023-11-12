package com.basiliskSB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//@Order(2)
//public class MvcSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	//@Autowired
//	//private DataSource securityDataSource;
//
//	@Autowired
//	UserDetailsService userDetailsService;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
//		/* Cara JDBC Classic
//		authentication.jdbcAuthentication()
//			.dataSource(securityDataSource)
//			.authoritiesByUsernameQuery("Select Username, concat('ROLE_', Role) from Accounts where Username = ?")
//			.usersByUsernameQuery("select Username, Password, Enabled from Accounts where Username = ?");
//		*/
//		//Cara JPA
//		authentication.userDetailsService(userDetailsService);
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		//hasRole diganti hasAuthority, hasAnyRole diganti hasAnyAuthority
//		http.authorizeRequests()
//				.antMatchers("/resources/**", "/account/**").permitAll()
//				.antMatchers("/delivery/**",
//					"/category/upsertForm",
//					"/category/delete",
//					"/region/upsertForm",
//					"/region/delete",
//					"/region/assignDetailForm",
//					"/region/deleteDetail",
//					"/salesman/upsertForm",
//					"/salesman/delete",
//					"/salesman/assignDetailForm",
//					"/salesman/deleteDetail").hasAuthority("Administrator")
//				.antMatchers("/customer/**",
//					"/product/upsertForm",
//					"/product/delete").hasAnyAuthority("Administrator", "Salesman")
//				.antMatchers("/order/**").hasAnyAuthority("Administrator", "Finance")
//				.anyRequest().authenticated()
//			.and().formLogin()
//				.loginPage("/account/loginForm")
//				.loginProcessingUrl("/authenticating") // get this for free from Spring Security
//			.and().logout()
//			.and().exceptionHandling().accessDeniedPage("/account/accessDenied");
//	}
//
//	@Bean
//	public static PasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}


@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {

	@Order(2)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//hasRole diganti hasAuthority, hasAnyRole diganti hasAnyAuthority
		http.authorizeRequests()
				.antMatchers("/resources/**", "/account/**").permitAll()
				.antMatchers("/delivery/**",
						"/category/upsertForm",
						"/category/delete",
						"/region/upsertForm",
						"/region/delete",
						"/region/assignDetailForm",
						"/region/deleteDetail",
						"/salesman/upsertForm",
						"/salesman/delete",
						"/salesman/assignDetailForm",
						"/salesman/deleteDetail").hasAuthority("Administrator")
				.antMatchers("/customer/**",
						"/product/upsertForm",
						"/product/delete").hasAnyAuthority("Administrator", "Salesman")
				.antMatchers("/order/**").hasAnyAuthority("Administrator", "Finance")
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/account/loginForm")
				.loginProcessingUrl("/authenticating") // get this for free from Spring Security
				.and().logout()
				.and().exceptionHandling().accessDeniedPage("/account/accessDenied");

		return http.build();
	}

	@Bean
	public AuthenticationManager authManager(
			HttpSecurity http,
			PasswordEncoder getPasswordEncoder,
			UserDetailsService userDetailsService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(getPasswordEncoder)
				.and()
				.build();
	}
}


