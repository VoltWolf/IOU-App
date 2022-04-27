package edu.ucdenver.zacharykelly.iou;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Debt {
    @PrimaryKey
    public int debtId;

    @ColumnInfo(name = "contact_name")
    public String contactName;

    @ColumnInfo(name = "debt_amount")
    public double debtAmount;

    @ColumnInfo(name = "debt_paid")
    public double debtPaid;

    @ColumnInfo(name = "recurring")
    public boolean recurring;
}
