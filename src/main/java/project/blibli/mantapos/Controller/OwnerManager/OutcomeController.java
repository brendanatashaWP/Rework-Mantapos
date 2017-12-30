package project.blibli.mantapos.Controller.OwnerManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.Service.OwnerManager.OutcomeService;

@RestController
public class OutcomeController {

    OutcomeService outcomeService;

    @Autowired
    public OutcomeController (OutcomeService outcomeService){
        this.outcomeService = outcomeService;
    }

    //Jika user mengakses laman outcome. Kurang lebih sama dengan menu/{page} di atas.
    @GetMapping(value = "/outcome/{page}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtml(@PathVariable("page") int page,
                                    Authentication authentication){
        return outcomeService.getMappingOutcome(authentication, page);
    }

    //Jika user menambahkan outcome (pengeluaran baru)
    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("ledger") Ledger ledger,
                                        @RequestParam("quantity") String qty,
                                        Authentication authentication){
        return outcomeService.postMappingOutcome(authentication, ledger, qty);
    }

}
