package project.blibli.mantapos.Beans_Model;

public class Income {
    private int income_amount;
    private int week, month, year;
    private int scale;

    public Income(){ }
    public Income(int income_amount, int week, int month, int year) {
        this.income_amount = income_amount;
        this.week = week;
        this.month = month;
        this.year = year;
    }

    public int getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(int income_amount) {
        this.income_amount = income_amount;
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
