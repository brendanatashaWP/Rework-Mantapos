package project.blibli.mantapos.Beans_Model;

public class Outcome {
    private int outcome_amount;
    private String item_name;
    private int qty;
    private String supp_name, supp_location;
    private String outcome_date;
    private int week, month, year;

    public String getOutcome_date() {
        return outcome_date;
    }

    public void setOutcome_date(String outcome_date) {
        this.outcome_date = outcome_date;
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

    public int getOutcome_amount() {
        return outcome_amount;
    }

    public void setOutcome_amount(int outcome_amount) {
        this.outcome_amount = outcome_amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSupp_name() {
        return supp_name;
    }

    public void setSupp_name(String supp_name) {
        this.supp_name = supp_name;
    }

    public String getSupp_location() {
        return supp_location;
    }

    public void setSupp_location(String supp_location) {
        this.supp_location = supp_location;
    }
}
