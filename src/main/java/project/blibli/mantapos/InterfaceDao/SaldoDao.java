package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Saldo;

public interface SaldoDao extends BasicDao<Saldo, Integer, Integer, Integer, Integer> {
    int getSaldoAwal(int idResto);
    int isSaldoAkhirExists(int idResto, int month, int year);
    int getSaldoAkhir(int idResto, int month, int year);
    int getMinMonthInYear(int idResto, int year);
}
