package edu.ucdenver.zacharykelly.iou;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.text.NumberFormat;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DebtAdapter extends RecyclerView.Adapter <DebtAdapter.ListItemHolder> {
    private MainActivity mainActivity;
    private List<Debt> list;

    public DebtAdapter(MainActivity mainActivity, List<Debt> list) {
        this.mainActivity = mainActivity;
        this.list = list;
    }

    public DebtAdapter.ListItemHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from (parent.getContext()).inflate(R.layout.debt_row, parent, false);
        return new ListItemHolder(listItem);
    }

    public int getItemCount() {
        return list.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(DebtAdapter.ListItemHolder holder, int position) {
        Debt debt = list.get(position);

        // Currency formatting
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);

        holder.tvName.setText(debt.getContactName());
        if (debt.isMonetary()) {
            if (debt.getDebtPaid() != 0) {
                holder.tvDebt.setText(format.format(debt.getDebtAmount()) + " - " + format.format(debt.getDebtPaid()));
            } else {
                holder.tvDebt.setText(format.format(debt.getDebtAmount()));
            }
        } else {
            holder.tvDebt.setText(debt.getDebtItem().toString());
        }


        if (debt.owesMe()) {
            holder.tvDebt.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_green));
        } else {
            holder.tvDebt.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_red));
        }
        if (debt.getDueDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(debt.getDueDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            holder.tvDate.setText("Due by " + year + "-" + month + "-" + day);
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDebt;
        private TextView tvName;
        private TextView tvDate;

        public ListItemHolder(View view) {
            super(view);

            tvDebt = view.findViewById(R.id.tvDebt);
            tvName = view.findViewById(R.id.tvName);
            tvDate = view.findViewById(R.id.tvDate);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        public void onClick (View view) {
            // Onclick
            Log.i("info", "Inside onClick method");
            mainActivity.viewDebt(getBindingAdapterPosition());
        }
    }
}