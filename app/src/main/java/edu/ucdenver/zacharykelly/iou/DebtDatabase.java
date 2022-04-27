package edu.ucdenver.zacharykelly.iou;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Debt.class}, version = 1)
public abstract class DebtDatabase extends RoomDatabase {
    public abstract DebtDao debtDao();
}
