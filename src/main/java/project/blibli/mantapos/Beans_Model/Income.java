package project.blibli.mantapos.Beans_Model;

public class Income {
    private int incomeAmount;
    private int week, month, year;
    private int scale;

    public Income(){ }
    public Income(int incomeAmount, int week, int month, int year) {
        this.incomeAmount = incomeAmount;
        this.week = week;
        this.month = month;
        this.year = year;
    }

    public int getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(int incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
