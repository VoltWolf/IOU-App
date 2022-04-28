package edu.ucdenver.zacharykelly.iou;

import android.app.AlertDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.ucdenver.zacharykelly.iou.databinding.FragmentViewDebtBinding;


public class ViewDebtFragment extends DialogFragment {
    private FragmentViewDebtBinding binding;
    private Debt debt;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = FragmentViewDebtBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        // Currency formatting
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);

        // Set the text views
        if (debt.owesMe()) {
            binding.tvName.setText(debt.getContactName() + " owes me:");
        } else {
            binding.tvName.setText("You owe " + debt.getContactName());
        }
        if (debt.isMonetary()) {
            binding.tvDebt.setText(format.format(debt.getDebtAmount()));
        } else {
            binding.tvDebt.setText(debt.getDebtItem());
            binding.btnPay.setVisibility(View.INVISIBLE);
        }
        if (debt.getDueDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(debt.getDueDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            binding.tvDate .setText("Due by " + year + "-" + month + "-" + day);
        } else {
            binding.tvDate.setVisibility(View.GONE);
        }

        if (debt.isRecurring()) {
            binding.tvDescription.setText(debt.getDescription() + " (Recurring)");
        } else {
            binding.tvDescription.setText(debt.getDescription());
        }

        // Delete the debt
        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.removeDebt(debt);
                dismiss();
            }
        });

        // Close the debt
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    public void setDebt (Debt debt) {
        this.debt = debt;
    }
}