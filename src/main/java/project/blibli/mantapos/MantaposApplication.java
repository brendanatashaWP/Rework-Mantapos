package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.Model.*;
import project.blibli.mantapos.NewImplementationDao.*;
import java.util.List;

@SpringBootApplication
public class MantaposApplication {

	static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		RestoranDaoImpl restoranDao = new RestoranDaoImpl();
		restoranDao.createTable();

		UserDaoImpl userDao = new UserDaoImpl();
//		userDao.createRoleUsers();
		userDao.createTable();
		userDao.createTableUsersRole();

		Restoran restoran = new Restoran("ADMIN", "ADMIN");
//		restoranDao.insert(restoran);
		restoran = new Restoran(
				"Warung Biru", "Yogyakarta"
		);
//		restoranDao.insert(restoran);

		User user = new User(
				"axell", bCryptPasswordEncoder.encode("axell123"), "admin", "Axellageraldinc",
				"123", "456", "jogja", "L", GetIdResto.getIdRestoBasedOnNamaResto("ADMIN"), true
		);
//		userDao.insert(user);
//		userDao.insertTableUsersRole(user);

		user = new User(
				"damas", bCryptPasswordEncoder.encode("damas123"), "owner", "Sulistyo Damas",
				"123", "456", "jogja", "L", GetIdResto.getIdRestoBasedOnNamaResto("Warung Biru"), true
		);
//		userDao.insert(user);
//		userDao.insertTableUsersRole(user);
		List<User> userList = userDao.getAll("id_resto=" + GetIdResto.getIdRestoBasedOnUsernameTerkait("damas"));
		System.out.println("*** LIST USERS ***");
		for (User item:userList
			 ) {
			System.out.println(item.getId() + ", " + item.getIdResto() + ", " + item.getNamaLengkap());
		}
		List<Restoran> restoranList = restoranDao.getAll("users_roles.role='owner'");
		System.out.println("*** LIST RESTORAN ***");
		for (Restoran item:restoranList
				) {
			System.out.println(item.getId() + ", " + item.getNamaResto());
		}

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.createTable();
//		Random random = new Random();
//		String kategori;
//		for (int i=0; i<12; i++){
//			int rand = random.nextInt();
//			if(rand%2==0){
//				kategori="food";
//			} else{
//				kategori="drink";
//			}
//			Menu menu = new Menu(7, "Menu " + String.valueOf(i+1), "test.jpg", kategori, (i+1)*1000);
//			menuDao.insert(menu);
//		}
		List<Menu> menuList = menuDao.getAll("id_resto=7");
		System.out.println("*** List Menu ***");
		for (Menu item:menuList
			 ) {
			System.out.println(item.getId() + ", " + item.getIdResto() + ", " + item.getNama_menu());
		}

		SaldoAwalDaoImpl saldoAwalDao = new SaldoAwalDaoImpl();
		saldoAwalDao.createTable();
		SaldoAkhirDaoImpl saldoAkhirDao = new SaldoAkhirDaoImpl();
		saldoAkhirDao.createTable();
		Saldo saldo = new Saldo(7, 50000, "awal");
//		saldoAwalDao.insert(saldo);
		List<Saldo> saldoAwalList = saldoAwalDao.getAll("id_resto=7");
		System.out.println("*** LIST SALDO AWAL ***");
		for (Saldo item:saldoAwalList
			 ) {
			System.out.println(item.getId_resto() + ", " + item.getSaldo());
		}

		PemasukkanDaoImpl pemasukkanDao = new PemasukkanDaoImpl();
		pemasukkanDao.createTable();

//		LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
//		ledgerDao.createTipeLedger();
//		ledgerDao.createTable();
//		Ledger ledger = new Ledger();
//		for (int i=0; i<12; i++) {
//			for (int j=0; j<5; j++){
//				ledger.setTipe("debit");
//				ledger.setKeperluan("penjualan menu"); //Karena disini adalah pemasukkan, makannya tipe ledger adalah debit dan keperluannya adalah penjualan menu
//				ledger.setWaktu(LocalDate.now().toString()); //setWaktu untuk di-insert ke database adalah waktu sekarang (yyyy-mm-dd)
//				ledger.setTanggal(j+1); //setTanggal
//				ledger.setWeek(WeekGenerator.GetWeek(LocalDateTime.now().getDayOfMonth())); //setWeek, value week diambil dari tanggalnya. Detailnya ada di class WeekGenerator
//				ledger.setMonth(i+1); //setMonth
//				ledger.setYear(2017); //setYear
//				ledger.setBiaya((j+1)*2000);
//				ledgerDao.insert(ledger, 16); //insert informasi pemesanan ke database (tabel ledger_harian)
//				saldo.setId_resto(16);
//				saldo.setTipe_saldo("akhir");
//				saldo.setTanggal(j+1);
//				saldo.setMonth(i+1);
//				saldo.setYear(2017);
//				int saldo_awal = saldoDao.getSaldoAkhir(16, i, 2017);
//				if(saldo_awal==0){ //artinya bulan kemarin tidak ada
//					saldo_awal = saldoDao.getSaldoAwal(16);
////                saldo_awal = saldoDao.getSaldoAkhir(id_resto, month, year);
//				}
//				saldo.setSaldo(saldo_awal + ledgerDao.getTotalDebitDalamSebulan(16, i+1, 2017) - ledgerDao.getTotalKreditDalamSebulan(16, i+1, 2017)); //saldo akhir = saldo awal + debit - kredit
//				saldoDao.insert(saldo, 16);
//			}
//		}
//		for (int i=0; i<12; i++){
//			for (int j=0; j<5; j++){
//				ledger.setTipe("kredit");
//				ledger.setKeperluan("outcome test"); //Karena disini adalah pemasukkan, makannya tipe ledger adalah debit dan keperluannya adalah penjualan menu
//				ledger.setWaktu(LocalDate.now().toString()); //setWaktu untuk di-insert ke database adalah waktu sekarang (yyyy-mm-dd)
//				ledger.setTanggal(i+1); //setTanggal
//				ledger.setWeek(WeekGenerator.GetWeek(LocalDateTime.now().getDayOfMonth())); //setWeek, value week diambil dari tanggalnya. Detailnya ada di class WeekGenerator
//				ledger.setMonth(i+1); //setMonth
//				ledger.setYear(2017); //setYear
//				ledger.setBiaya((j+1)*1000);
//				ledgerDao.insert(ledger, 16); //insert informasi pemesanan ke database (tabel ledger_harian)
//				saldo.setId_resto(16);
//				saldo.setTipe_saldo("akhir");
//				saldo.setTanggal(j+1);
//				saldo.setMonth(i+1);
//				saldo.setYear(2017);
//				int saldo_awal = saldoDao.getSaldoAkhir(16, i, 2017);
//				if(saldo_awal==0){ //artinya bulan kemarin tidak ada
//					saldo_awal = saldoDao.getSaldoAwal(16);
////                saldo_awal = saldoDao.getSaldoAkhir(id_resto, month, year);
//				}
//				saldo.setSaldo(saldo_awal + ledgerDao.getTotalDebitDalamSebulan(16, i+1, 2017) - ledgerDao.getTotalKreditDalamSebulan(16, i+1, 2017)); //saldo akhir = saldo awal + debit - kredit
//				saldoDao.insert(saldo, 16);
//			}
//		}

		MenuYangDipesanDaoImpl menuYangDipesanDao = new MenuYangDipesanDaoImpl();
		menuYangDipesanDao.createTable();
	}

}
