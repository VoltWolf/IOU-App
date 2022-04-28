package edu.ucdenver.zacharykelly.iou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.zacharykelly.iou.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Variables
    BottomAppBar bottomAppBar;
    FloatingActionButton fabAdd;

    // Binding
    private ActivityMainBinding binding;

    // Room DB
    private DebtDatabase db;
    private DebtDao debtDao;

    // Recycler View
    ArrayList<Debt> debtList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DebtAdapter debtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard Stuff
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main); No need thanks to binding

        // View binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create the database object
        db = Room.databaseBuilder(getApplicationContext(), DebtDatabase.class, "database-name").allowMainThreadQueries().build();
        debtDao = db.debtDao();

        // Create the appbar object and buttons
        fabAdd = findViewById(R.id.fabAdd);
        bottomAppBar = findViewById(R.id.bottomAppBar);

        // Setup the RecyclerView
        recyclerView = findViewById(R.id.rvDebt);
        debtAdapter = new DebtAdapter(this, debtList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(debtAdapter);

        // Onclick Events:
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDebtFragment addDebtFragment = new AddDebtFragment();
                addDebtFragment.show(getSupportFragmentManager(), "");
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.abHome:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.abSettings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    // This is where we load our dat
    @Override
    public void onResume() {
        super.onResume();
        loadDebts();
    }

    // Load all items in the list
    private void loadDebts() {
        Log.i("info", "Reloading debts");
        debtList.clear();
        List<Debt> tempList = debtDao.getAll();
        // If there are elements to display
        if (tempList.size() != 0) {
            // Add all of the elements into the list
            Log.i("info", "Loading " + tempList.size() + " debts");
            debtList.addAll(tempList);
        }
    }

    public void addDebt (Debt debt) {
        Log.i("info", "Adding debt");
        debtDao.insertAll(debt);
        // Reload all of the items in the list
        loadDebts();
    }
}