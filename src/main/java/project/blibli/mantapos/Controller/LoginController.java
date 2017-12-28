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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RestController
//Controller untuk menangani jika akses ke URL /login,
//Akses ke URL "/" (URL "/" adalah URL jika user sukses login) (maka di redirect sesuai role-nya)
//Akses ke URL /privilege, yaitu jika user mengakses page yang tidak seharusnya (role-nya tidak sesuai)
public class LoginController {

    //Jika user mengakses URL /login
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView loginHtml(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        //jika dalam URL itu ada parameter error (/login?error), maka ada error, maka tambahkan object error ke mav. Nantinya akan di-check di login.html
        if(error!=null)
            mav.addObject("error", "Invalid username and/or password");
        //jika dalam URL itu ada parameter logout (/login?logout), maka user berhasil logout, maka tambahkan object logout ke mav. Nantinya akan di-check di login.html
        else if(logout!=null)
            mav.addObject("logout", "You've been logged out successfully!");
        return mav;
    }

    //Melakukan assigning default page untuk user yang login berdasarkan role-nya
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        if(authentication.getAuthorities().toString().equals("[cashier]"))
            mav.setViewName("redirect:/cashier");
        else if(authentication.getAuthorities().toString().equals("[manager]") || authentication.getAuthorities().toString().equals("[owner]"))
            mav.setViewName("redirect:/dashboard");
        else
            mav.setViewName("redirect:/restaurant/1");
        return mav;
    }

    //Jika user mencoba mengakses ke page yang tidak seharusnya (role tidak sesuai) (misal cashier ingin mengakses dashboard)
    @GetMapping(value = "/privilege", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView privilegePage(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("privilege");
        mav.addObject("privilege", "You don't have permission to access this page");
        String username = authentication.getName();
        mav.addObject("username", username);
        String role = String.valueOf(authentication.getAuthorities());
        mav.addObject("role", role);
        return mav;
    }

}
