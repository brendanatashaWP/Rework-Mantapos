package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.*;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.MonthNameGenerator;
import project.blibli.mantapos.WeekGenerator;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO : DISCOUNT belum dibuat sama sekali

@RestController
public class OwnerManagerController {
    private static String UPLOAD_LOCATION=System.getProperty("user.dir") + "/src/main/resources/static/images/";

    UserDaoImpl userDao = new UserDaoImpl();
    MenuDaoImpl menuDao = new MenuDaoImpl();
    private LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    private SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    private RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    int id_resto;

    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(){
        return new ModelAndView("owner-manager/dashboard");
    }
    @GetMapping(value = "/menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView menuDashboardHtml(Menu menu,
            Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Menu> menuList = menuDao.getAllMenu(id_resto);
        return new ModelAndView("owner-manager/menu", "menuList", menuList);
    }
    @GetMapping(value = "/outcome", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Ledger> outcomeList = ledgerDao.GetDailyKredit(id_resto);
        return new ModelAndView("owner-manager/outcome", "outcomeList", outcomeList);
    }
    @GetMapping(value = "/employee", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierListHtml(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/employee");
        List<User> userList = new ArrayList<>();
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        if(authentication.getAuthorities().toString().equals("[manager]")){
            userList = userDao.getAllUser(id_resto, "cashier");
        } else if(authentication.getAuthorities().toString().equals("[owner]")){
            userList = userDao.getAllUser(id_resto, "manager&cashier");
        }
        String role = authentication.getAuthorities().toString();
        mav.addObject("userList", userList);
        mav.addObject("role", role);
        return mav;
//        return new ModelAndView("manager-cashier", "userList", userList);
    }
    @GetMapping(value = "/range", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView ledgerChooseRangeHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Ledger> monthAndYearList = ledgerDao.GetMonthAndYearList(id_resto);
        return new ModelAndView("owner-manager/pilih-range-ledger", "monthAndYearList", monthAndYearList);
    }
    @GetMapping(value = "/saldo")
    public ModelAndView addSaldoAwalHtml(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        int intBulan = LocalDate.now().getMonthValue();
        String bulan = MonthNameGenerator.MonthNameGenerator(intBulan);
        List<SaldoAwal> saldoAwalList = saldoDao.getSaldoAwalTiapBulan(id_resto);
        mav.addObject("saldoAwalList", saldoAwalList);
        mav.addObject("bulan", bulan);
        mav.setViewName("owner-manager/saldo-awal");
        return mav;
    }

    @PostMapping(value = "/saldo-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addSaldoAwalHtml(Authentication authentication,
                                         @ModelAttribute("saldoAwal") SaldoAwal saldoAwal){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        int user_id = userDao.GetUserIdBerdasarkanUsername(username);
        saldoDao.AddSaldoAwal(id_resto, saldoAwal.getSaldo_awal(), user_id);
        return new ModelAndView("redirect:/saldo");
    }
    @PostMapping(value = "/menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addMenuJson(@ModelAttribute("menu") @Valid Menu menu,
                                    Authentication authentication,
                                    BindingResult bindingResult){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        int user_id = userDao.GetUserIdBerdasarkanUsername(username);
        if(bindingResult.hasErrors()){
            return new ModelAndView("owner-manager/menu");
        } else{
            try{
                MultipartFile multipartFile = menu.getMultipartFile();
                String filename = multipartFile.getOriginalFilename();
                FileCopyUtils.copy(menu.getMultipartFile().getBytes(), new File(UPLOAD_LOCATION + filename));
                menu.setLokasi_gambar_menu("/images/" + filename);
                menuDao.Insert(id_resto, menu, user_id);
            } catch (Exception ex){
                System.out.println("Error add menu : " + ex.toString());
            }
            return new ModelAndView("redirect:/menu");
        }
    }
    @PostMapping(value = "/add-user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addUserHtml(@ModelAttribute("user")User user,
                                    Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        int user_id = userDao.GetUserIdBerdasarkanUsername(username);
        if(authentication.getAuthorities().toString().equals("[manager]")){
            user.setRole("cashier");
        } else if(authentication.getAuthorities().toString().equals("[owner]")){
            user.setRole(user.getRole());
        }
        user.setId_resto(id_resto);
        userDao.Insert(user, user_id);
        return new ModelAndView("redirect:/employee");
    }
    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("ledger") Ledger ledger,
                                        @RequestParam("quantity") String qty,
                                        Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        int user_id = userDao.GetUserIdBerdasarkanUsername(username);
        String[] dateSplit = ledger.getWaktu().split("-");
        int tanggal = Integer.parseInt(dateSplit[2]);
        int week = WeekGenerator.GetWeek(tanggal); ledger.setWeek(week);
        int month = Integer.parseInt(dateSplit[1]); ledger.setMonth(month);
        int year = Integer.parseInt(dateSplit[0]); ledger.setYear(year);
        ledger.setTipe("kredit");
        ledger.setKeperluan(ledger.getKeperluan() + "(" + qty + ")");
        ledgerDao.Insert(ledger, id_resto, user_id);
        return new ModelAndView("redirect:/outcome");
    }
    @PostMapping(value = "/ledger")
    //get bulan yang dikehendaki di tahun LocalDate.now().getYear()
    public ModelAndView Ledger(@RequestParam(value = "Skala", required = false) String skala,
                               @RequestParam(value = "month", required = false) Integer month,
                               @RequestParam(value = "year", required = false) Integer year,
                               Authentication authentication){
        ModelAndView mav = new ModelAndView();
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Ledger> ledgerList = new ArrayList<>();
        int saldo_awal=0, total_debit=0, total_kredit=0, saldo_akhir=0, mutasi=0;
        String skala_ledger=""; //skala ledger bisa harian, mingguan, bulanan, atau tahunan
        if(skala.equals("harian") || skala.equals("mingguan")){ //harian atau mingguan
            total_kredit = ledgerDao.GetTotalKreditBulanan(id_resto, month, year);
            total_debit = ledgerDao.GetTotalDebitBulanan(id_resto, month, year);
            saldo_awal = saldoDao.getSaldoAwal(id_resto, month, year);
            saldo_akhir = saldo_awal+total_debit-total_kredit;
            mutasi = saldo_akhir-saldo_awal;
            if(skala.equals("harian")){
                ledgerList = ledgerDao.GetDailyLedger(id_resto, month, year);
                skala_ledger = "HARIAN";
            } else if(skala.equals("mingguan")){ //mingguan
                ledgerList = ledgerDao.GetWeeklyLedger(id_resto, month, year);
                skala_ledger = "MINGGUAN";
            }
        } else if(skala.equals("bulanan")){ //bulanan //TODO : saldo awal enaknya gimana? saldo awal total dalam tahun yang dipilih? atau saldo awal di awal bulannya tiap tahun(kayaknya bagus yg ini)
            total_kredit = ledgerDao.GetTotalKreditTahunan(id_resto, year);
            total_debit = ledgerDao.GetTotalDebitTahunan(id_resto, year);
            //jadi saldo awalnya bagusnya dilihatkan jumlah saldo awalnya dalam setahun itu
            saldo_awal = saldoDao.getSaldoAwal(id_resto, month, year);
            saldo_akhir = saldo_awal+total_debit-total_kredit;
            mutasi = saldo_akhir-saldo_awal;
            ledgerList = ledgerDao.GetMonthlyLedger(id_resto, year);
            skala_ledger = "BULANAN";
        } else{ //tahunan //TODO : Ledger tahunan

        }

        mav.addObject("saldo_awal", saldo_awal);
        mav.addObject("total_debit", total_debit);
        mav.addObject("total_kredit", total_kredit);
        mav.addObject("saldo_akhir", saldo_akhir);
        mav.addObject("mutasi", mutasi);
        mav.addObject("ledgerList", ledgerList);
        mav.addObject("skala_ledger", skala_ledger);
        mav.setViewName("owner-manager/ledger");
        return mav;
    }
    @GetMapping(value = "/delete/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView deleteCashier(@PathVariable("id") int id,
                                      Authentication authentication){
        if(authentication.getAuthorities().toString().equals("[admin]")){
            userDao.DeleteUserAndDependencies(id);
            return new ModelAndView("redirect:/restaurant");
        } else{
            userDao.DeleteUser(id);
            return new ModelAndView("redirect:/employee");
        }
    }
    @GetMapping(value = "/active/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView activeCashier(@PathVariable("id") int id,
                                      Authentication authentication){
        userDao.ActivateUser(id);
        if(authentication.getAuthorities().toString().equals("[admin]"))
            return new ModelAndView("redirect:/restaurant");
        else
            return new ModelAndView("redirect:/employee");
    }
    @GetMapping(value = "/delete/menu/{id}")
    public ModelAndView deleteMenu(@PathVariable("id") int id){
        menuDao.DeleteMenu(id);
        return new ModelAndView("redirect:/menu");
    }
    @GetMapping(value = "/edit/menu/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuHtml(@PathVariable("id") int id,
                                     Authentication authentication){
        ModelAndView mav = new ModelAndView();
        String username = authentication.getName();
        int id_resto = restoranDao.GetRestoranId(username);
        List<Menu> menuList = menuDao.getMenuById(id_resto, id);
        mav.addObject("menuList", menuList);
        mav.setViewName("owner-manager/edit-menu");
        return mav;
    }
    @PostMapping(value = "/edit-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuPostHtml(@ModelAttribute("menu") Menu menu){
        ModelAndView mav = new ModelAndView();
        //UPDATE KE DATABASE DISINI
        mav.setViewName("redirect:/menu");
        return mav;
    }
}
