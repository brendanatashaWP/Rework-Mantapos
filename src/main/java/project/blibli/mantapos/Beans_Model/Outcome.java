package project.blibli.mantapos.Beans_Model;

public class Outcome {
    private int outcome_amount;
    private String item_name;
    private int qty;
    private String supp_name, supp_location, supp_contact;

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

    public String getSupp_contact() {
        return supp_contact;
    }

    public void setSupp_contact(String supp_contact) {
        this.supp_contact = supp_contact;
    }
}
