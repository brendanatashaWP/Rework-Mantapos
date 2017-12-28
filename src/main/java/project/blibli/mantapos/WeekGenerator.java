package project.blibli.mantapos;

public class WeekGenerator {

    //Class yang menangani untuk generate week ke berapa dari tanggal

    public static int GetWeek(int tanggal){
        int week=0;
        if (tanggal<=7) //Jika tanggalnya 1-7, maka itu week ke-1.
            week=1;
        else if(tanggal>7 && tanggal<=14) //Jika tanggalnya adalah 8-14, maka itu week ke-2
            week=2;
        else if(tanggal>14 && tanggal<=21) //Jika tanggalnya adalah 15-21, maka itu week ke-3
            week=3;
        else //Jika tanggalnya >21, maka itu week ke-4
            week=4;
        return week;
    }
}
