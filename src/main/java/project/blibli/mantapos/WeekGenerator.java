package project.blibli.mantapos;

public class WeekGenerator {
    public static int GetWeek(int tanggal){
        int week=0;
        if (tanggal<=7)
            week=1;
        else if(tanggal>7 && tanggal<=14)
            week=2;
        else if(tanggal>14 && tanggal<=21)
            week=3;
        else
            week=4;
        return week;
    }
}
