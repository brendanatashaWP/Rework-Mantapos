package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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

@RestController
public class ManagerController {
    private static String UPLOAD_LOCATION=System.getProperty("user.dir") + "/src/main/resources/static/images/";

    //Untuk commandName di jsp discount
    @ModelAttribute("multipartFile")
    public UploadMenuImage getForm(){
        return new UploadMenuImage();
    }

    UserDaoImpl userDao = new UserDaoImpl();
    MenuDaoImpl menuDao = new MenuDaoImpl();
    private LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    private SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    private RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    int id_resto;

    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(){
        return new ModelAndView("manager-dashboard");
    }
    @GetMapping(value = "/menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView menuDashboardHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Menu> menuList = menuDao.getAllMenu(id_resto);
        return new ModelAndView("manager-menu", "menuList", menuList);
    }
    @GetMapping(value = "/outcome", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Ledger> outcomeList = ledgerDao.GetDailyKredit(id_resto);
        return new ModelAndView("manager-outcome", "outcomeList", outcomeList);
    }
    @GetMapping(value = "/employee", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierListHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<User> userList = userDao.getAllUser(id_resto);
        return new ModelAndView("manager-cashier", "userList", userList);
    }
    @GetMapping(value = "/range", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView ledgerChooseRangeHtml(Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        List<Ledger> monthAndYearList = ledgerDao.GetMonthAndYearList(id_resto);
        return new ModelAndView("manager-pilih-range-ledger", "monthAndYearList", monthAndYearList);
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
        mav.setViewName("manager-saldo-awal");
        return mav;
    }
    @GetMapping(value = "/restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView restaurantListHtml(){
        List<Restoran> restoranList = restoranDao.GetRestoranList();
        return new ModelAndView("admin-restaurant", "restoranList", restoranList);
    }

    @PostMapping(value = "/saldo-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addSaldoAwalHtml(Authentication authentication,
                                         @ModelAttribute("saldoAwal") SaldoAwal saldoAwal){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        saldoDao.AddSaldoAwal(id_resto, saldoAwal.getSaldo_awal());
        return new ModelAndView("redirect:/saldo");
    }
    @PostMapping(value = "/add-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addMenuJson(@ModelAttribute("menu")Menu menu,
                                           @ModelAttribute("uploadFile") @Valid UploadMenuImage uploadMenuImage,
                                           Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);

        try{
            MultipartFile multipartFile = uploadMenuImage.getMultipartFile();
//            String[] filenameSplit = multipartFile.getOriginalFilename().split(".");
//            System.out.println("Filename 1 : " + filenameSplit[0] + ", Filename 2 : " + filenameSplit[1]);
//            String filename = String.valueOf(menuDao.getLastId(restoran.getId_resto())) + "." + filenameSplit[1];
            String filename = String.valueOf(menuDao.getLastId(id_resto)) + ".jpg";
//            FileCopyUtils.copy(uploadMenuImage.getMultipartFile().getBytes(), new File(UPLOAD_LOCATION + filename));
            byte[] bytes = uploadMenuImage.getMultipartFile().getBytes();
            BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(
                    new File(UPLOAD_LOCATION + filename)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            menu.setLokasi_gambar_menu("/images/" + filename);
            menuDao.Insert(id_resto, menu);
        } catch (Exception ex){
            System.out.println("Error add menu : " + ex.toString());
        }
        return new ModelAndView("redirect:/menu");
    }
    @PostMapping(value = "/add-cashier", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addUserHtml(@ModelAttribute("user")User user,
                                    Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        user.setRole("cashier");
        user.setId_resto(id_resto);
        userDao.Insert(user);
        return new ModelAndView("redirect:/employee");
    }
    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("ledger") Ledger ledger,
                                        @RequestParam("quantity") String qty,
                                        Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        String[] dateSplit = ledger.getWaktu().split("-");
        int tanggal = Integer.parseInt(dateSplit[2]);
        int week = WeekGenerator.GetWeek(tanggal); ledger.setWeek(week);
        int month = Integer.parseInt(dateSplit[1]); ledger.setMonth(month);
        int year = Integer.parseInt(dateSplit[0]); ledger.setYear(year);
        ledger.setTipe("kredit");
        ledger.setKeperluan(ledger.getKeperluan() + "(" + qty + ")");
        ledgerDao.Insert(ledger, id_resto);
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
        } else if(skala.equals("bulanan")){ //bulanan
            total_kredit = ledgerDao.GetTotalKreditTahunan(id_resto, year);
            total_debit = ledgerDao.GetTotalDebitTahunan(id_resto, year);
            saldo_awal = saldoDao.getSaldoAwal(id_resto, month, year);
            saldo_akhir = saldo_awal+total_debit-total_kredit;
            mutasi = saldo_akhir-saldo_awal;
            ledgerList = ledgerDao.GetMonthlyLedger(id_resto, year);
            skala_ledger = "BULANAN";
        } else{ //tahunan

        }

        mav.addObject("saldo_awal", saldo_awal);
        mav.addObject("total_debit", total_debit);
        mav.addObject("total_kredit", total_kredit);
        mav.addObject("saldo_akhir", saldo_akhir);
        mav.addObject("mutasi", mutasi);
        mav.addObject("ledgerList", ledgerList);
        mav.addObject("skala_ledger", skala_ledger);
        mav.setViewName("manager-ledger");
        return mav;
    }
    @PostMapping(value = "/add-restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addRestaurantPost(@ModelAttribute("restoran") Restoran restoran,
                                          @ModelAttribute("user") User user){

        return new ModelAndView("redirect:/restaurant");
    }
    @GetMapping(value = "/delete/cashier/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView deleteCashier(@PathVariable("id") int id){
        userDao.DeleteCashier(id);
        return new ModelAndView("redirect:/employee");
    }
    @GetMapping(value = "/active/cashier/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView activeCashier(@PathVariable("id") int id){
        userDao.ActivateCashier(id);
        return new ModelAndView("redirect:/employee");
    }
}
