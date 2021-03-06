package edu.ucdenver.zacharykelly.iou;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Debt {
    @PrimaryKey(autoGenerate = true)
    public int debtId;

    @ColumnInfo(name = "contact_name")
    public String contactName;

    @ColumnInfo(name = "owes_me")
    public boolean owesMe;

    @ColumnInfo(name = "is_monetary")
    public boolean isMonetary;

    @ColumnInfo(name = "debt_amount")
    public Double debtAmount;

    @ColumnInfo(name = "debt_paid")
    public double debtPaid;

    @ColumnInfo(name = "debt_item")
    public String debtItem;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "due_date")
    public Long dueDate; // IN UTC MILLIS

    @ColumnInfo(name = "recurring")
    public boolean recurring;

    // Constructor
    public Debt(String contactName, boolean owesMe, boolean isMonetary, Double debtAmount, String debtItem, String description, Long dueDate, boolean recurring) {
        this.contactName = contactName;
        this.owesMe = owesMe;
        this.isMonetary = isMonetary;
        this.debtAmount = debtAmount;
        this.debtPaid = 0;
        this.debtItem = debtItem;
        this.description = description;
        this.dueDate = dueDate;
        this.recurring = recurring;
    }

    // Debt ID
    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    // Contact Name
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    // Owes Me
    public boolean owesMe() {
        return owesMe;
    }

    public void setOwesMe(boolean owesMe) {
        this.owesMe = owesMe;
    }

    // Is Monetary
    public boolean isMonetary() {
        return isMonetary;
    }

    public void setMonetary(boolean monetary) {
        isMonetary = monetary;
    }

    // Debt Amount
    public Double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    // Debt Paid (Monetary)
    public double getDebtPaid() {
        return debtPaid;
    }

    public void setDebtPaid(double debtPaid) {
        this.debtPaid = debtPaid;
    }

    // Debt Item (Non-Monetary)
    public String getDebtItem() {
        return debtItem;
    }

    public void setDebtItem(String debtItem) {
        this.debtItem = debtItem;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Due Date (in UTC Millis)
    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    // Recurring
    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}
