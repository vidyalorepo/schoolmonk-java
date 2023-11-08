package com.dcc.schoolmonk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.cors().and().csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests()
				.antMatchers("/authenticate", "/register", "/payments/**", "/users/login", "/users" +
								"/sendResetPasswordMailLink",
						"/users/validateToken", "/users/resetPassword", "/users/signup", "/fe/attachment",
						"/users/register", "/users/validate",
						"/users/allUser", "/users/updatePwd", "/schooluser/getByToken/**",
						"/fe/downloadMedia/**", "/schooluser/getSchoolBySearch", "/schooluser/getAdmissionOpenSchool",
						"/schooluser/getFeaturedSchool",
						"/schooluser/schoolBySearch/**", "/schooluser/getClassListOfSchool",
						"/commonMaster/getBoardInSearch", "/commonMaster/getMediumInSearch",
						"/schooluser/getSchoolByCustomSearch", "/commonMaster/getSchoolLevelsInSearch","/commonMaster/fetchAllBlogCategory",
						"/commonMaster/getSchoolTypesInSearch", "/commonMaster/getDistrictListByStateMulti",
						"/commonMaster/getStateListInSearch", "/helpdesk/raiseTicket", "/helpdesk/uploadFiles",
						"/schooluser/findBySlug/**", "/paymentCollectionController/schoolPaymentDetailsReport/**",
						"/advanced-search/**","/studentcontroller/checkForApply",
						"/noticeboard/getPublicNoticeList", "/noticeboard/getPublicNoticeDetails",
						"/events/getPublicEventDetails", "/commonMaster/saveSchoolDtl", "/testimonials/getAllTestimonials","/testimonials/gettestimonials/**",
						"/schooluser/getAllPublishedNotice", "/schooluser/getPublishedNoticeDtl/**", "/commonMaster/getFaqs", "/commonMaster/saveFeedback","/newsarticlescontroller/findNewsArticalBySlug/**","/newsarticlescontroller/getallnewsartical","/locationlistcontroller/getalllocationlist","/commonMaster/getMediums","/commonMaster/getSchoolTypes","/commonMaster/getBoards","/commonMaster/getDistrictListByState","/reviewcontroller/addreview","/reviewcontroller/getallreview","/adsvertisement/getallads", "/customerdetails/savecustomerdetails","/adsvertisement/fetchByAdd/{id}","/adsvertisement/uploadSingleFileInFolder","/adsvertisement/attachment/**","/adsvertisement/getallorder","/adsvertisement/saveorder",
						"/blog/fetchallblog","/blog/findbySlug","/headertagcontroller/fetchBytag","/blog/fetchallblog","/blog/findbySlug","/headertagcontroller/fetchBytag","/adsvertisement/fetchattachmentbyzone","/parentenquiry","/schoolMstBulkController/checkduplicateSchool")
				.permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}