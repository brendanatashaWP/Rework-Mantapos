package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.*;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.WeekGenerator;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
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
//        List<Outcome> outcomeList = outcomeDao.getOutcome(restoran.getId());
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
    public ModelAndView ledgerChooseRangeHtml(){
        return new ModelAndView("manager-pilih-range-ledger");
    }

//    @PostMapping(value = "/add-saldo-awal", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String, String> addSaldoAwalJson(@RequestParam("saldo_awal") int saldo_awal,
//                                                Authentication authentication){
//        Map<String, String> param = new HashMap<>();
//        //Ambil username yang sedang login, untuk nantinya diambil ID restoran-nya
//        String loggedInUsername = authentication.getName();
//
//        Restoran restoran = restoranDao.GetRestaurantInfo(loggedInUsername);
//        int statusAddSaldoAwal = saldoDao.AddSaldoAwal(restoran.getId_resto(), saldo_awal);
//        if(statusAddSaldoAwal==1)
//            param.put("status", "success");
//        else
//            param.put("status", "failed");
//        return param;
//    }

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

//    @PostMapping(value = "/add-cashier", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String, Object> addUserJson(@ModelAttribute("user")User user){
//        Map<String, Object> param = new HashMap<>();
//        int status = 0;
//        String errorMsg="Error";
//        userDao.Insert(user);
//        return param;
//    }

    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("ledger") Ledger ledger,
                                        Authentication authentication){
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);
        String[] dateSplit = ledger.getWaktu().split("-");
        int tanggal = Integer.parseInt(dateSplit[2]);
        int week = WeekGenerator.GetWeek(tanggal); ledger.setWeek(week);
        int month = Integer.parseInt(dateSplit[1]); ledger.setMonth(month);
        int year = Integer.parseInt(dateSplit[0]); ledger.setYear(year);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        String jam = dtf.format(now);
        ledger.setTipe("kredit");
        ledgerDao.Insert(ledger, id_resto);
//        outcome.setOutcome_date(outcome.getOutcome_date() + "," + jam);
//        System.out.println("Week : " + week + ", Month : " + month + ", Year : " + year);
//        int status = outcomeDao.Insert(restoran.getId(), outcome);
//        boolean isIncomeExists = incomeDao.isIncomeExists(restoran.getId());
//        if(!isIncomeExists){
//            debitKreditDao.Insert(1, restoran.getId(), LocalDate.now().getMonthValue(), LocalDateTime.now().getYear(), outcome.getOutcome_amount());
//        } else{
//            debitKreditDao.Update(1, restoran.getId(), LocalDate.now().getMonthValue(), LocalDateTime.now().getYear(), outcome.getOutcome_amount());
//            saldoDao.Update(1, restoran.getId(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), outcome.getOutcome_amount());
//        }
        return new ModelAndView("redirect:/outcome");
    }

    @PostMapping(value = "/daily")
    //get bulan yang dikehendaki di tahun LocalDate.now().getYear()
    public ModelAndView LedgerHarian(@RequestParam("month") int month,
                                     Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager-ledger-daily");
        String username = authentication.getName();
        id_resto = restoranDao.GetRestoranId(username);

        int saldo_awal = saldoDao.getSaldoAwal(id_resto, month, LocalDate.now().getYear());
        mav.addObject("saldo_awal", saldo_awal);

//        int saldo_akhir = saldoDao.getSaldoAkhir(restoran.getId(), income.getMonth(), LocalDate.now().getYear());
//        List<DebitKredit> debitKreditList = debitKreditDao.getDebitKreditAmount(restoran.getId(), income.getMonth(), LocalDate.now().getYear());
//        int total_debit=0, total_kredit=0;
//        for (DebitKredit item:debitKreditList
//             ) {
//            total_debit = item.getDebit();
//            total_kredit = item.getKredit();
//        }
        int total_kredit_bulanan = ledgerDao.GetTotalKreditBulanan(id_resto, month, LocalDate.now().getYear());
        int total_debit_bulanan = ledgerDao.GetTotalDebitBulanan(id_resto, month, LocalDate.now().getYear());
        mav.addObject("total_debit", total_debit_bulanan);
        mav.addObject("total_kredit", total_kredit_bulanan);
        int saldo_akhir = saldo_awal+total_debit_bulanan-total_kredit_bulanan;
        mav.addObject("saldo_akhir", saldo_akhir);
        int mutasi = saldo_awal-saldo_akhir;
        mav.addObject("mutasi", mutasi);
        List<Ledger> ledgerList = ledgerDao.GetDailyLedger(id_resto, month, LocalDate.now().getYear());
        mav.addObject("ledgerList", ledgerList);
        return mav;
    }
}
