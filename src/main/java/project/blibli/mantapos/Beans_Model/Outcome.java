package project.blibli.mantapos.Beans_Model;

public class Outcome {
    private int outcomeAmount;
    private String itemName;
    private int qty;
    private String suppName, suppLocation, suppContact;

    public int getOutcomeAmount() {
        return outcomeAmount;
    }

    public void setOutcomeAmount(int outcomeAmount) {
        this.outcomeAmount = outcomeAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }

    public String getSuppLocation() {
        return suppLocation;
    }

    public void setSuppLocation(String suppLocation) {
        this.suppLocation = suppLocation;
    }

    public String getSuppContact() {
        return suppContact;
    }

    public void setSuppContact(String suppContact) {
        this.suppContact = suppContact;
    }
}
