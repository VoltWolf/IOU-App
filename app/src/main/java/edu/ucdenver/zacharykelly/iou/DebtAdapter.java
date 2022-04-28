package edu.ucdenver.zacharykelly.iou;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

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

    public void onBindViewHolder(DebtAdapter.ListItemHolder holder, int position) {
        Debt debt = list.get(position);

        holder.tvName.setText(debt.getContactName());
        if (debt.isMonetary()) {
            holder.tvDebt.setText(Double.toString(debt.getDebtAmount()));
        } else {
            holder.tvDebt.setText(debt.getDebtItem().toString());
        }
        // holder.tvDate.setText(Long.toString(debt.getDueDate()));
        holder.tvDate.setText("TEST DATE");
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
            // mainActivity.showDebt(getAdapterPosition());
        }
    }
}