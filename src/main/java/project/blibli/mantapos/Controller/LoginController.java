package project.blibli.mantapos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import project.blibli.mantapos.Config.Mail;
import project.blibli.mantapos.Service.LoginService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RestController
//Controller untuk menangani jika akses ke URL /login,
//Akses ke URL "/" (URL "/" adalah URL jika user sukses login) (maka di redirect sesuai role-nya)
//Akses ke URL /privilege, yaitu jika user mengakses page yang tidak seharusnya (role-nya tidak sesuai)
public class LoginController {

    LoginService loginService;

    @Autowired
    public LoginController (LoginService loginService){
        this.loginService = loginService;
    }

    //Jika user mengakses URL /login
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView loginHtml(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout){
        return loginService.getMappingLogin(error, logout);
    }

    //Melakukan assigning default page untuk user yang login berdasarkan role-nya
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Authentication authentication){
        return loginService.getMappingIndexPageDefaultSuccessLogin(authentication);
    }

    //Jika user mencoba mengakses ke page yang tidak seharusnya (role tidak sesuai) (misal cashier ingin mengakses dashboard)
    @GetMapping(value = "/privilege", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView privilegePage(Authentication authentication){
        return loginService.getMappingAccessDeniedPage(authentication);
    }

}
