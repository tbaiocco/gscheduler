package com.nuovonet.gscheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public class Oauth2ResourceServerConfiguration {

    private static final String RESOURCE_SERVER_RESOURCE_ID = "mjs-poc-resource-server"; //de onde vem esse nome???!!! //"rsdata-resource-server";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        
        
        @Value("${oauth.clientid:}")
        private String oauthClientId;

        @Value("${oauth.clientsecret:}")
        private String oauthClientSecret;

        @Value("${oauth.check.token.endpoint:}")
        private String oauthClientTokenEndpoint;
        
        @Value("${oauth.userinfo.endpoint:}")
        private String oauthUserinfoEndpoint;
        
        @Bean
        public ResourceServerTokenServices myUserInfoTokenServices() {
//        	MyUserInfoTokenServices tokenServices = new MyUserInfoTokenServices(oauthUserinfoEndpoint, oauthClientId);
//        	return tokenServices;
        	return new ResourceServerTokenServices() {
				
				@Override
				public OAuth2AccessToken readAccessToken(String accessToken) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public OAuth2Authentication loadAuthentication(String accessToken)
						throws AuthenticationException, InvalidTokenException {
					// TODO Auto-generated method stub
					return null;
				}
			};
        }
        
        
        /**
         * Use the remote token services in the Authorisation Server.
         * @return
         */
//        @Bean
//        public ResourceServerTokenServices tokenService() {
//           RemoteTokenServices tokenServices = new RemoteTokenServices();
//           tokenServices.setClientId(oauthClientId);
//           tokenServices.setClientSecret(oauthClientSecret);
//           tokenServices.setCheckTokenEndpointUrl(oauthClientTokenEndpoint);
//           
//           return tokenServices;
//        }        
        
        
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // @formatter:off
            resources
                .resourceId(RESOURCE_SERVER_RESOURCE_ID)
//                .tokenServices(tokenService())
                .tokenServices(myUserInfoTokenServices())
                ;
            // @formatter:on
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .authorizeRequests()
                	.antMatchers("/swagger-ui**").permitAll()
                	.antMatchers("/mappings**").permitAll()
                	.antMatchers("/beans**").permitAll()
                	.antMatchers("/dump**").permitAll()
                	.antMatchers("/trace**").permitAll()
                	.antMatchers("/env**").permitAll()
                	.antMatchers("/env/**").permitAll()
                	.antMatchers("/metrics/**").permitAll()
                	.antMatchers("/metrics**").permitAll() 
                	.antMatchers("/health**").permitAll()
                	.antMatchers("/info**").permitAll()
                	.antMatchers("/configprops**").permitAll()
                	.antMatchers("/autoconfig **").permitAll()
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/teste").authenticated()
            		.antMatchers("/epi").authenticated()
            		.antMatchers("/parser").authenticated()
            		.antMatchers("/**").authenticated();
            // @formatter:on
        }

    }    
    

}
