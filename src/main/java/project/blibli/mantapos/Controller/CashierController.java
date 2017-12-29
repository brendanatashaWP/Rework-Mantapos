package project.blibli.mantapos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import project.blibli.mantapos.Config.Mail;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.Model.Menu;
import project.blibli.mantapos.Model.OrderedMenu;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.NewImplementationDao.LedgerDaoImpl;
import project.blibli.mantapos.NewImplementationDao.MenuDaoImpl;
import project.blibli.mantapos.NewImplementationDao.MenuYangDipesanDaoImpl;
import project.blibli.mantapos.NewImplementationDao.RestoranDaoImpl;
import project.blibli.mantapos.WeekGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
//Controller untuk cashier (akses URL /cashier, penerimaan order baru, dan pengiriman receipt melalui email)
public class CashierController {

    MenuDaoImpl menuDao = new MenuDaoImpl();
    LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    MenuYangDipesanDaoImpl orderedMenuDao = new MenuYangDipesanDaoImpl();
    RestoranDaoImpl restaurantDao = new RestoranDaoImpl();
    Restoran restoran;

    Mail mail = new Mail();

    //Melakukan AutoWired untuk templateEngine, ini digunakan untuk mengirim email dalam bentuk HTML
    @Autowired
    TemplateEngine templateEngine;

    //Mapping untuk akses URL /cashier
    @GetMapping(value = "/cashier", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierHtml(Authentication authentication){
        ModelAndView mav = new ModelAndView();

        String loggedInUsername = authentication.getName(); //Ambil username yang sedang login
        mav.addObject("loggedInUsername", loggedInUsername);

        restoran = restaurantDao.readOne(restaurantDao.readIdResto(loggedInUsername)); //Mengambil informasi restoran berdasarkan nama username yang login (username itu belong ke restoran mana)
        mav.addObject("restoran", restoran); //object restoran ini gunanya nanti untuk di receipt

        List<Menu> menuList = menuDao.readAll(restoran.getId(), 0, 0); //Mengambil semua menu yang ada di database. itemPerPage dan page dibuat 0 karena tidak akan memakai pagination
        mav.setViewName("cashier");
        mav.addObject("menuList", menuList);
        return mav;
    }

    //Mapping untuk menambah order dari cashier ke database
    @PostMapping(value = "/add-order", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addOrderHtml(@ModelAttribute("order") Ledger ledger,
                                     @RequestParam(value = "array_id_order", required = false) String[] array_id_order,
                                     @RequestParam(value = "array_qty", required = false) String[] array_qty,
                                     @RequestParam(value = "is_kirim_email_receipt", required = false) String is_kirim_email_receipt,
                                     @RequestParam(value = "email_kirim_receipt", required = false) String email_kirim_receipt,
                                     @RequestParam("namaResto") String nama_resto,
                                     @RequestParam("customer_name") String nama_customer,
                                     Authentication authentication){
        ModelAndView mav = new ModelAndView();

        String loggedInUsername = authentication.getName(); //Ambil username yang sedang login
        int id_resto = restaurantDao.readIdResto(loggedInUsername);
        System.out.println("id resto : " + id_resto);
//        restoran = restaurantDao.GetRestaurantInfo(loggedInUsername); //Mengambil informasi restoran berdasarkan username yg login (username itu belong ke restoran mana)
//        int id_resto = restoran.getId(); //Mengambil id restoran yang didapat dari hasil di atas

        ledger.setTipe("debit"); ledger.setKeperluan("penjualan menu"); //Karena disini adalah pemasukkan, makannya tipe ledger adalah debit dan keperluannya adalah penjualan menu
        ledger.setWaktu(LocalDate.now().toString()); //setWaktu untuk di-insert ke database adalah waktu sekarang (yyyy-mm-dd)
        ledger.setTanggal(LocalDate.now().getDayOfMonth()); //setTanggal
        ledger.setWeek(WeekGenerator.GetWeek(LocalDateTime.now().getDayOfMonth())); //setWeek, value week diambil dari tanggalnya. Detailnya ada di class WeekGenerator
        ledger.setMonth(LocalDateTime.now().getMonthValue()); //setMonth
        ledger.setYear(LocalDateTime.now().getYear()); //setYear
        ledgerDao.insert(ledger, restoran.getId()); //insert informasi pemesanan ke database (tabel ledger_harian)
        int lastOrderId = ledgerDao.getLastId(id_resto); //Mengambil id dari order yang barusan dimasukkan (record terakhir). ID nya ini untuk dijadikan foreign key di table OrderedMenu
        //Melakukan for dari 0 hingga panjang dari Array array_id_order. Array ini isinya adalah id dari menu2 yang dipesan. Memasukkan id menu2 itu ada di order.js
        for(int i=0; i<array_id_order.length; i++){
            orderedMenuDao.insertMenuYangDipesan(lastOrderId, Integer.parseInt(array_id_order[i]),
                    Integer.parseInt(array_qty[i]));
            //Melakukan insert ke table OrderedMenu. Dimana foreign key nya adalah id dari last Order (record terakhir tadi).
            //lalu id dari tiap2 menu yang dipesan itu diinsert ke table OrderedMenu, bersamaan dengan quantity-nya dari Array array_qty. Memasukkan quantity-nya ada di order.js
        }

        //Melakukan pengecekan apakah customer ingin receipt dikirim via email atau tidak. Di-pass dari HTML kesini dalam variabel is_kirim_email_receipt
        if(is_kirim_email_receipt.equals("yes")){ //jika YES
            MimeMessage maill = mail.send().createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(maill, true);
                Context context = new Context();
                helper.setFrom("mantapos@axella.online");
                helper.setTo(email_kirim_receipt); //Alamat tujuan pengiriman email, di-pass dari HTML kesini dalam variabel email_kirim_receipt
                helper.setSubject("RECEIPT PEMBELIAN DI " + nama_resto); //Subject email
                context.setVariable("nama_customer", nama_customer); //Melempar nama customer ke HTML receipt (email.html)
                context.setVariable("namaResto", nama_resto); //Melempar nama restoran ke HTML receipt (email.html)
                context.setVariable("tanggal", LocalDate.now().toString()); //Melempar tanggal sekarang ke HTML receipt (email.html)
                context.setVariable("total_harga", ledger.getBiaya()); //Melempar total biaya ke HTML receipt (email.html). Total biaya didapat dari object ledger yang dilempar dari kasir kesini.
                List<OrderedMenu> ordered_menu_list = new ArrayList<>(); //List yang berisi detail menu (nama menu, harga menu, qty, dan total harga menu)
                for (int i=0; i<array_id_order.length; i++){
                    Menu menuObject = menuDao.readOne(Integer.parseInt(array_id_order[i])); //Mengambil detail dari masing-masing menu. Cara mengambil detailnya adalah berdasarkan id menu yang ada di Array array_id_order
                    ordered_menu_list.add(new OrderedMenu(
                            menuObject.getNama_menu(),
                            (menuObject.getHarga_menu() * Integer.parseInt(array_qty[i])),
                            Integer.parseInt(array_qty[i])));
                }
                context.setVariable("ordered_menu_list", ordered_menu_list); //Melempar list ordered_menu_list yang berisi detail menu yang di-order ke HTML receipt (email.html)
                String body = templateEngine.process("email", context); //Menspecify body dari email adalah email.html dengan context (yaitu variabel2 yang dilempar tadi)
                helper.setText(body, true);
                mail.send().send(maill); //Melakukan send email. Harap cek spam untuk melihat email yang dikirim. Tidak tahu kenapa masuk spam.
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        mav.setViewName("redirect:/cashier");
        return mav;
    }
}
