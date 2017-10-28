package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController{

    @GetMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView error(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        int errorCode = getErrorCode(request);
        mav.addObject("errorCode", errorCode);
        return mav;
    }

    private int getErrorCode(HttpServletRequest request){
        return (int) request.getAttribute("javax.servlet.error.status_code");
    }
}
