package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.ImplementationDao.BasicDao;

public interface SaldoDao extends BasicDao<Saldo, Integer, Integer, Integer, Integer> {
    int getSaldoAwal(int idResto);
    int getSaldoAwalCustom(int idResto, int month, int year);
    int isSaldoAkhirExists(int idResto, int month, int year);
    int getSaldoAkhir(int idResto, int month, int year);
    int getMinMonthInYear(int idResto, int year);
}
