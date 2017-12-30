package project.blibli.mantapos.Service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import project.blibli.mantapos.Config.Mail;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.Model.*;
import project.blibli.mantapos.Helper.WeekGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CashierService {

    LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    MenuDaoImpl menuDao = new MenuDaoImpl();
    MenuYangDipesanDaoImpl menuYangDipesanDao = new MenuYangDipesanDaoImpl();

    int tanggal = LocalDate.now().getDayOfMonth();
    int bulan = LocalDate.now().getMonthValue();
    int tahun = LocalDate.now().getYear();

    public ModelAndView getMappingCashier(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cashier");
        String loggedInUsername = getLoggedInUsername(authentication);
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(loggedInUsername);
        mav.addObject("loggedInUsername", loggedInUsername);
        mav.addObject("restoran", getInfoRestoran(idResto));
        mav.addObject("menuList", getAllMenu(idResto));
        return mav;
    }

    public ModelAndView postMappingCashier(Ledger ledger,
                                           String[] arrayIdMenu,
                                           String[] arrayQtyMenu,
                                           String apakahMauKirimReceiptMelaluiEmail,
                                           String emailTujuan,
                                           String namaResto,
                                           String namaCustomer,
                                           Authentication authentication,
                                           TemplateEngine templateEngine,
                                           Mail mail){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/cashier");
        String loggedInUsername = authentication.getName();
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(loggedInUsername);
        insertPemasukkan(ledger, idResto);
        updateSaldoAkhir(idResto);
        insertDetailMenuYangDipesan(getLastIdOrder(idResto), arrayIdMenu, arrayQtyMenu);
        if(apakahMauKirimReceiptMelaluiEmail.equals("yes")){
            kirimReceiptMelaluiEmail(emailTujuan, namaResto, namaCustomer, templateEngine, mail, ledger.getBiaya(), arrayIdMenu, arrayQtyMenu);
        }
        return mav;
    }

    public List<Menu> getAllMenu(int idResto){
        List<Menu> menuList = menuDao.readAll(idResto, 0, 0);
        return menuList;
    }

    public Restoran getInfoRestoran(int idResto){
        Restoran restoran = restoranDao.readOne(idResto);
        return restoran;
    }

    public String getLoggedInUsername(Authentication authentication){
        String username = authentication.getName();
        return username;
    }

    public int getLastIdOrder(int idResto){
        int lastIdOrder = ledgerDao.getLastId(idResto);
        return lastIdOrder;
    }

    public void insertDetailMenuYangDipesan(int idOrder, String[] arrayIdMenu, String[] arrayQtyMenu){
        for (int i=0; i<arrayIdMenu.length; i++){
            menuYangDipesanDao.insertMenuYangDipesan(idOrder, Integer.parseInt(arrayIdMenu[i]), Integer.parseInt(arrayQtyMenu[i]));
        }
    }

    public void insertPemasukkan(Ledger ledger, int idResto){
        ledger.setTipe("debit");
        ledger.setKeperluan("penjualan menu"); //Karena disini adalah pemasukkan, makannya tipe ledger adalah debit dan keperluannya adalah penjualan menu
        ledger.setWaktu(LocalDate.now().toString()); //setWaktu untuk di-insert ke database adalah waktu sekarang (yyyy-mm-dd)
        ledger.setTanggal(tanggal); //setTanggal
        ledger.setWeek(WeekGenerator.GetWeek(LocalDateTime.now().getDayOfMonth())); //setWeek, value week diambil dari tanggalnya. Detailnya ada di class WeekGenerator
        ledger.setMonth(bulan); //setMonth
        ledger.setYear(tahun); //setYear
        ledgerDao.insert(ledger, idResto);
    }

    public void updateSaldoAkhir(int idResto){
        Saldo saldo = new Saldo();
        saldo.setId_resto(idResto);
        saldo.setTipe_saldo("akhir");
        saldo.setMonth(bulan);
        saldo.setYear(tahun);
        saldo.setSaldo(saldoDao.getSaldoAwal(idResto) + ledgerDao.getTotalDebitDalamSebulan(idResto, bulan, tahun) - ledgerDao.getTotalKreditDalamSebulan(idResto, bulan, tahun)); //saldo akhir = saldo awal + debit - kredit
    }

    public void kirimReceiptMelaluiEmail(String alamatEmailTujuan,
                                         String namaResto,
                                         String namaCustomer,
                                         TemplateEngine templateEngine,
                                         Mail mail,
                                         int totalBiaya,
                                         String[] arrayIdMenu,
                                         String[] arrayQtyMenu){
        MimeMessage maill = mail.send().createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(maill, true);
            Context context = new Context();
            helper.setFrom("mantapos@axella.online");
            helper.setTo(alamatEmailTujuan); //Alamat tujuan pengiriman email, di-pass dari HTML kesini dalam variabel email_kirim_receipt
            helper.setSubject("RECEIPT PEMBELIAN DI " + namaResto); //Subject email
            context.setVariable("nama_customer", namaCustomer); //Melempar nama customer ke HTML receipt (email.html)
            context.setVariable("namaResto", namaResto); //Melempar nama restoran ke HTML receipt (email.html)
            context.setVariable("tanggal", LocalDate.now().toString()); //Melempar tanggal sekarang ke HTML receipt (email.html)
            context.setVariable("total_harga", totalBiaya); //Melempar total biaya ke HTML receipt (email.html). Total biaya didapat dari object ledger yang dilempar dari kasir kesini.
            List<OrderedMenu> ordered_menu_list = new ArrayList<>(); //List yang berisi detail menu (nama menu, harga menu, qty, dan total harga menu)
            for (int i=0; i<arrayIdMenu.length; i++){
                Menu menuObject = menuDao.readOne(Integer.parseInt(arrayIdMenu[i])); //Mengambil detail dari masing-masing menu. Cara mengambil detailnya adalah berdasarkan id menu yang ada di Array array_id_order
                ordered_menu_list.add(new OrderedMenu(
                        menuObject.getNama_menu(),
                        (menuObject.getHarga_menu() * Integer.parseInt(arrayQtyMenu[i])),
                        Integer.parseInt(arrayQtyMenu[i])));
            }
            context.setVariable("ordered_menu_list", ordered_menu_list); //Melempar list ordered_menu_list yang berisi detail menu yang di-order ke HTML receipt (email.html)
            String body = templateEngine.process("email", context); //Menspecify body dari email adalah email.html dengan context (yaitu variabel2 yang dilempar tadi)
            helper.setText(body, true);
            mail.send().send(maill); //Melakukan send email. Harap cek spam untuk melihat email yang dikirim. Tidak tahu kenapa masuk spam.
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
