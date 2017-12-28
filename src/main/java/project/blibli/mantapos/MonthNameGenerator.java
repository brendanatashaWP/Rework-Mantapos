package project.blibli.mantapos;

public class MonthNameGenerator {

    //Class untuk men-generate nama bulan berdasarkan angka bulan ke berapa.

    public static String MonthNameGenerator(int month){
        String monthName="";
        switch (month){
            case 1: //Jika bulan ke-1, maka itu bulan Januari, dan seterusnya bisa dilihat di bawah.
                monthName="Januari";
                break;
            case 2:
                monthName="Februari";
                break;
            case 3:
                monthName="Maret";
                break;
            case 4:
                monthName="April";
                break;
            case 5:
                monthName="Mei";
                break;
            case 6:
                monthName="Juni";
                break;
            case 7:
                monthName="Juli";
                break;
            case 8:
                monthName="Agustus";
                break;
            case 9:
                monthName="September";
                break;
            case 10:
                monthName="Oktober";
                break;
            case 11:
                monthName="November";
                break;
            case 12:
                monthName="Desember";
                break;
        }
        return monthName;
    }
}
