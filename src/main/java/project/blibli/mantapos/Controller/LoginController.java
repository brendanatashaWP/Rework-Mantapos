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
public class LoginController {



    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView loginHtml(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        if(error!=null)
            mav.addObject("error", "Invalid username and/or password");
        else if(logout!=null)
            mav.addObject("logout", "You've been logged out successfully!");
        return mav;
    }

    //DEFAULT PAGE FOR OWNER, MANAGER, CASHIER
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
