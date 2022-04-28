package edu.ucdenver.zacharykelly.iou;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DebtDao {
    @Query("SELECT * FROM debt")
    List<Debt> getAll();

    @Query("Select * FROM debt WHERE debtId IN (:debtIds)")
    List<Debt> loadAllByIds(int[] debtIds);

    @Insert
    void insertAll(Debt... debt);

    @Update
    void Update(Debt debt);

    @Delete
    void delete(Debt debt);
}
