package com.cjs.example.config;

import com.cjs.example.util.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * @author ChengJianSheng
 * @date 2019-03-03
 *
 * security config的配置代码
 * 说明；
 *     Spring Security是用于安全访问的，这里我们我们用来做访问权限控制
 *
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EnvironmentUtils environmentUtils;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if ("local".equals(environmentUtils.getActiveProfile())) {
            http.authorizeRequests().anyRequest().permitAll();
        }else {
            http.logout().logoutSuccessUrl("http://localhost:8080/logout")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable();
        }
    }
}

