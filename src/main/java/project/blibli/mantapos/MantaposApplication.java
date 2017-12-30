package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.Model.Menu;
import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.ImplementationDao.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		RestoranDaoImpl restoranDao = new RestoranDaoImpl();
		restoranDao.createTable();

		UserDaoImpl userDao = new UserDaoImpl();
		userDao.createRoleUser();
		userDao.createTable();

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.createTable();
		Menu menu = new Menu();
//		for (int i=0; i<12; i++){
//			menu.setNama_menu("Menu " + String.valueOf(i+1));
//			menu.setHarga_menu((i+1)*1000);
//			menu.setKategori_menu("food");
//			menu.setLokasi_gambar_menu("test.jpg");
//			menuDao.insert(menu, 16);
//		}

		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
		saldoDao.createTable();
//		Saldo saldo = new Saldo();
//		saldo.setTipe_saldo("awal");
//		saldo.setId_resto(16);
//		saldo.setSaldo(5000);
//		saldo.setTanggal(1);
//		saldo.setMonth(1);
//		saldo.setYear(2017);
//		saldoDao.insert(saldo, 16);

		LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
		ledgerDao.createTipeLedger();
		ledgerDao.createTable();
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
