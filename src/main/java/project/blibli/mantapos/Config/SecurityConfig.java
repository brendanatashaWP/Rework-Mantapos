package project.blibli.mantapos.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("cashier");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?")
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                //pada access disini pakai hasAuthority (instead of hasRole) karena
                //kalau pake hasRole, di database harus ada prefix ROLE_ pada record-nya, contoh ROLE_CASHIER
                .antMatchers("/cashier/**").access("hasAnyAuthority('cashier', 'manager', 'owner')")
                .antMatchers("/dashboard/**", "/menu/**", "/outcome/**", "/employee/**", "/range/**", "/add-menu/**", "/edit/menu/**", "/add-cashier/**", "/delete/user/**", "/active/user/**", "/outcome-post/**", "/daily/**", "/saldo/**").access("hasAnyAuthority('manager', 'owner')")
                .antMatchers("/restaurant/**").access("hasAuthority('admin')")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/privilege")
                .and()
                .csrf().disable();
    }
}