package project.blibli.mantapos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.*;
import project.blibli.mantapos.MonthNameGenerator;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.Service.OutcomeService;
import project.blibli.mantapos.Service.OwnerManager.DashboardService;
import project.blibli.mantapos.Service.OwnerManager.EmployeeService;
import project.blibli.mantapos.Service.OwnerManager.MenuService;
import project.blibli.mantapos.Service.OwnerManager.SaldoService;
import project.blibli.mantapos.WeekGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO : DISCOUNT belum dibuat sama sekali

@RestController
//Controller yang mengatur segala hal yang berkaitan dengan dashboard manager/owner
public class OwnerManagerController {
    private static String UPLOAD_LOCATION=System.getProperty("user.dir") + "/src/main/resources/static/images/";

    UserDaoImpl userDao = new UserDaoImpl();
    MenuDaoImpl menuDao = new MenuDaoImpl();
    private LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    private SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    private RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    int id_resto, itemPerPage=5; //itemPerPage dibuat 5 item saja.

    DashboardService dashboardService;
    MenuService menuService;
    EmployeeService employeeService;
    SaldoService saldoService;
    OutcomeService outcomeService;

    @Autowired
    public OwnerManagerController (DashboardService dashboardService,
                                   MenuService menuService,
                                   EmployeeService employeeService,
                                   SaldoService saldoService,
                                   OutcomeService outcomeService){
        this.dashboardService = dashboardService;
        this.menuService = menuService;
        this.employeeService = employeeService;
        this.saldoService = saldoService;
        this.outcomeService = outcomeService;
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
        String username = authentication.getName();
        id_resto = restoranDao.readIdRestoBasedOnUsernameRestoTerkait(username);
        List<Ledger> monthAndYearList = ledgerDao.getListBulanDanTahun(id_resto); //Mengambil semua month dan year yang ada di database. Ini di pass ke pilih-range-ledger.html, supaya dropdown di html itu nanti sesuai dengan month dan year yang tersedia di database saja
        return new ModelAndView("owner-manager/pilih-range-ledger", "monthAndYearList", monthAndYearList);
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
        ModelAndView mav = new ModelAndView();
        String username = authentication.getName();
        id_resto = restoranDao.readIdRestoBasedOnUsernameRestoTerkait(username);
        List<Ledger> ledgerList = new ArrayList<>();
        int saldo_awal=0, total_debit=0, total_kredit=0, saldo_akhir=0, mutasi=0;
        String skala_ledger=""; //skala ledger bisa harian, mingguan, bulanan, atau tahunan
        if(skala.equals("harian") || skala.equals("mingguan")){ //jika skala yang di-pass dari ledger.html adalah harian atau mingguan. Yang di-pick user adalah bulan dan tahunnya. Misal user ingin melihat ledger harian di bulan Oktober tahun 2017. Atau mingguan di bulan Desember tahun 2017
            total_kredit = ledgerDao.getTotalKreditBulanan(id_resto, month, year); //Mengambil total kredit bulanan di bulan dan tahun yang di pick user
            total_debit = ledgerDao.getTotalDebitBulanan(id_resto, month, year); //Mengambil total debit bulanan dari bulan dan tahun yang di pick user
            if (month==1){
                saldo_awal = saldoDao.getSaldoAkhir(id_resto, 12, year-1);
            } else{
                saldo_awal = saldoDao.getSaldoAkhir(id_resto, (month-1), (year));
            }
            if(saldo_awal==0){ //artinya bulan kemarin tidak ada
                saldo_awal = saldoDao.getSaldoAwal(id_resto);
            }
            saldo_akhir = saldo_awal+total_debit-total_kredit; //Menghitung saldo akhir (dari saldo awal ditambah pemasukkan (total debit) dikurangi pengeluaran (total kredit)
            mutasi = saldo_akhir-saldo_awal; //mutasi, masih belum tahu maksudnya mutasi ini apa. //TODO : Cari definisi dari mutasi itu apa
            if(skala.equals("harian")){
                //jika skalanya adalah harian
                ledgerList = ledgerDao.getLedgerHarian(id_resto, month, year); //mengambil ledger secara harian di bulan dan tahun yang di pick user
                skala_ledger = "HARIAN";
            } else if(skala.equals("mingguan")){
                //jika skalanya adalah mingguan
                ledgerList = ledgerDao.getLedgerMingguan(id_resto, month, year); //mengambil ledger secara mingguan di bulan dan tahun yang di pick user
                skala_ledger = "MINGGUAN";
            }
        } else if(skala.equals("bulanan")){
            //jika skalanya adalah bulanan
            //karena bulanan, maka user memilih tahunnya. misal user ingin melihat ledger secara bulanan di tahun 2017
            total_kredit = ledgerDao.getTotalKreditTahunan(id_resto, year); //mengambil total kredit
            total_debit = ledgerDao.getTotalDebitTahunan(id_resto, year); //mengambil total debit
            int monthTerkecil = saldoDao.getMinMonthInYear(id_resto, year);
            saldo_awal = saldoDao.getSaldoAkhir(id_resto, monthTerkecil, (year));
            if(saldo_awal==0){ //artinya bulan kemarin tidak ada
                saldo_awal = saldoDao.getSaldoAwal(id_resto);
            }
            saldo_akhir = saldo_awal+total_debit-total_kredit;
            mutasi = saldo_akhir-saldo_awal;
            ledgerList = ledgerDao.getLedgerBulanan(id_resto, year); //mengambil ledger bulanan di tahun yang di pick user
            skala_ledger = "BULANAN";
        } else if(skala.equals("tahunan")){ //tahunan //TODO : Ledger tahunan

        } else {
            //custom ledger range

            //inti dari 4 baris kode di bawah ini adalah untuk mengambil tanggal, bulan, dan tahun dari waktu awal yang dikehendaki user untuk melihat ledger
            String[] dateAwalSplit = ledger_custom_awal.split("-");
            int tanggalAwal = Integer.parseInt(dateAwalSplit[2]);
            int monthAwal = Integer.parseInt(dateAwalSplit[1]);
            int yearAwal = Integer.parseInt(dateAwalSplit[0]);

            //inti dari 4 baris kode di bawah ini adalah untuk mengambil tanggal, bulan, dan tahun dari waktu akhir yang dikehendaki user unutuk melihat ledger
            String[] dateAkhirSplit = ledger_custom_akhir.split("-");
            int tanggalAkhir = Integer.parseInt(dateAkhirSplit[2]);
            int monthAkhir = Integer.parseInt(dateAkhirSplit[1]);
            int yearAkhir = Integer.parseInt(dateAkhirSplit[0]);

            total_kredit = ledgerDao.getTotalKreditCustom(id_resto, tanggalAwal, monthAwal, yearAwal, tanggalAkhir,  monthAkhir, yearAkhir); //Total kredit dari waktu awal hingga akhir yang dikehendaki user
            total_debit = ledgerDao.getTotalDebitCustom(id_resto, tanggalAwal, monthAwal, yearAwal, tanggalAkhir,  monthAkhir, yearAkhir); //Total debit dari waktu awal hingga akhir yang dikehendaki user
            if (month==1){
                saldo_awal = saldoDao.getSaldoAwalCustom(id_resto, 12, yearAwal-1); //Mengambil saldo awal dari waktu awal yang di pick user.
            } else{
                saldo_awal = saldoDao.getSaldoAwalCustom(id_resto, monthAwal-1, yearAwal); //Mengambil saldo awal dari waktu awal yang di pick user.
            }
            if(saldo_awal==0){ //artinya bulan kemarin tidak ada
                saldo_awal = saldoDao.getSaldoAwal(id_resto);
            }
            saldo_akhir = saldo_awal+total_debit-total_kredit;
            mutasi = saldo_akhir-saldo_awal;
            ledgerList = ledgerDao.getLedgerCustom(id_resto, tanggalAwal, monthAwal, yearAwal, tanggalAkhir,  monthAkhir, yearAkhir); //Mengambil ledger berdasarkan waktu awal dan waktu akhir yang di pick user
            skala_ledger = "CUSTOM";
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
