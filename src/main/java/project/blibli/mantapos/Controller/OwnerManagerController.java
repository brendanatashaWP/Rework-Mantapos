package project.blibli.mantapos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.*;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.Service.OwnerManager.*;

import java.util.ArrayList;
import java.util.List;

//TODO : DISCOUNT belum dibuat sama sekali

@RestController
//Controller yang mengatur segala hal yang berkaitan dengan dashboard manager/owner
public class OwnerManagerController {

    DashboardService dashboardService;
    MenuService menuService;
    EmployeeService employeeService;
    SaldoService saldoService;
    OutcomeService outcomeService;
    LedgerService ledgerService;

    @Autowired
    public OwnerManagerController (DashboardService dashboardService,
                                   MenuService menuService,
                                   EmployeeService employeeService,
                                   SaldoService saldoService,
                                   OutcomeService outcomeService,
                                   LedgerService ledgerService){
        this.dashboardService = dashboardService;
        this.menuService = menuService;
        this.employeeService = employeeService;
        this.saldoService = saldoService;
        this.outcomeService = outcomeService;
        this.ledgerService = ledgerService;
    }

    //Jika user mengakses /dashboard
    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(Authentication authentication){
        return dashboardService.getMappingDashboard(authentication);
    }
    //Jika user mengakses menu/{page}, dimana {page} ini adalah page keberapa laman menu itu, misal menu/1 berarti laman menu page 1 di pagination-nya
    @GetMapping(value = "/menu/{page}")
    public ModelAndView menuPaginated(Menu menu, @PathVariable("page") int page, Authentication authentication){
        return menuService.getMappingMenu(authentication, page);
    }
    //Jika user mengakses laman outcome. Kurang lebih sama dengan menu/{page} di atas.
    @GetMapping(value = "/outcome/{page}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtml(@PathVariable("page") int page,
            Authentication authentication){
        return outcomeService.getMappingOutcome(authentication, page);
    }
    //Jika user akses /employee, kurang lebih sama dengan menu/page dan outcome/page
    @GetMapping(value = "/employee/{page}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierListHtml(@PathVariable("page") int page,
            Authentication authentication){
        return employeeService.getMappingEmployee(authentication, page);
    }
    //Jika user mengakses link /range, yaitu link untuk memilih jangka waktu melihat ledger (buku besar)
    @GetMapping(value = "/range", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView ledgerChooseRangeHtml(Authentication authentication){
        return ledgerService.getMappingChooseRangeLedger(authentication);
    }
    //Jika user akses /saldo/page, sama dengan menu, outcome, dan employee untuk paging-nya
    @GetMapping(value = "/saldo/{page}")
    public ModelAndView addSaldoAwalHtml(@PathVariable("page") int page,
            Authentication authentication){
        return saldoService.getMappingSaldoAwal(authentication, page);
    }

    //Jika user menambahkan saldo baru
    @PostMapping(value = "/saldo-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addSaldoAwalHtml(Authentication authentication,
                                         @ModelAttribute("saldoAwal") Saldo saldo){
        return saldoService.postMappingAddSaldoAwal(authentication, saldo);
    }
    //Jika user menambahkan menu baru
    @PostMapping(value = "/menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addMenuJson(@ModelAttribute("menu") Menu menu,
                                    Authentication authentication){
        return menuService.postMappingAddNewMenu(authentication, menu);
    }
    //Jika user menambahkan user baru
    @PostMapping(value = "/add-user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addUserHtml(@ModelAttribute("user")User user,
                                    Authentication authentication){
        return employeeService.postMappingAddNewEmployee(authentication, user);
    }
    //Jika user menambahkan outcome (pengeluaran baru)
    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("ledger") Ledger ledger,
                                        @RequestParam("quantity") String qty,
                                        Authentication authentication){
        return outcomeService.postMappingOutcome(authentication, ledger, qty);
    }
    //Setelah pilih jangka waktu untuk melihat ledger, maka page ini diakses
    @PostMapping(value = "/ledger")
    public ModelAndView Ledger(@RequestParam(value = "Skala", required = false) String skala,
                               @RequestParam(value = "month", required = false) Integer month,
                               @RequestParam(value = "year", required = false) Integer year,
                               @RequestParam(value = "ledger_custom_awal", required = false)  String ledger_custom_awal,
                               @RequestParam(value = "ledger_custom_akhir", required = false)  String ledger_custom_akhir,
                               Authentication authentication){
        return ledgerService.postMappingLihatLedger(authentication, skala, month, year, ledger_custom_awal, ledger_custom_akhir);
    }
    //Jika user menghapus user
    @GetMapping(value = "/delete/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView deleteCashier(@PathVariable("id") int id,
                                      Authentication authentication){
        return employeeService.postMappingDeleteEmployee(authentication, id);
    }
    //Jika user mengaktifkan lagi user
    @GetMapping(value = "/active/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView activeCashier(@PathVariable("id") int id,
                                      Authentication authentication){
        return employeeService.postMappingActivateEmployee(authentication, id);
    }
    //jika user menghapus menu
    @GetMapping(value = "/delete/menu/{id}")
    public ModelAndView deleteMenu(@PathVariable("id") int id){
        return menuService.getMappingDeleteMenu(id);
    }
    //jika user mengakses halaman untuk mengedit menu (keluar form dengan value dari menu yg bersesuaian di set ke input2 yang ada
    @GetMapping(value = "/edit/menu/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuHtml(@PathVariable("id") int id){
        return menuService.getMappingEditMenu(id);
    }
    //jika user posting menu yang sudah di edit
    @PostMapping(value = "/edit-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuPostHtml(@ModelAttribute("menu") Menu menu,
                                         Authentication authentication){
        return menuService.postMappingEditMenu(authentication, menu);
    }
    //Jika user mengakses laman edit user (show form) beserta value value yang bersesuaian di input2 yang ada
    @GetMapping(value = "/edit/user/{id}")
    public ModelAndView editUserHtml(@PathVariable("id") int id,
                                     Authentication authentication){
        return employeeService.getMappingEditEmployee(authentication, id);
    }
    //Memposting data user yang baru
    @PostMapping(value = "/edit-user")
    public ModelAndView editUserPostHtml(@ModelAttribute("user") User user,
                                         Authentication authentication){
        return employeeService.postMappingEditEmployee(authentication, user);
    }
}
