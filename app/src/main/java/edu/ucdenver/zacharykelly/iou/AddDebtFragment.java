package edu.ucdenver.zacharykelly.iou;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.TimeZone;

import edu.ucdenver.zacharykelly.iou.Debt;
import edu.ucdenver.zacharykelly.iou.MainActivity;
import edu.ucdenver.zacharykelly.iou.databinding.FragmentAddDebtBinding;

import edu.ucdenver.zacharykelly.iou.R;

public class AddDebtFragment extends DialogFragment {
    private FragmentAddDebtBinding binding;

    // Variables
    String contactName;
    boolean owesMe;
    boolean isMonetary;
    double debtAmount;
    double debtPaid;
    String debtItem;
    String description;
    Long dueDate = null; // IN UTC MILLIS
    boolean useDate = false;
    boolean recurring;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = FragmentAddDebtBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        // Setup Date Picker
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select A Date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        // When the owes me switch is checked
        binding.swOwesMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (binding.swOwesMe.isChecked()) {
                    binding.swOwesMe.setText("Owes Me");
                } else {
                    binding.swOwesMe.setText("Owe Them");
                }
            }
        });

        // When the monetary switch is checked
        binding.swMonetary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (binding.swMonetary.isChecked()) {
                    // If checked...
                    binding.etDebtAmount.setVisibility(View.VISIBLE); // Show debt amount
                    binding.etDebtItem.setVisibility(View.GONE); // Hide debt item
                } else {
                    // If not checked...
                    binding.etDebtAmount.setVisibility(View.GONE); // Hide debt amount
                    binding.etDebtItem.setVisibility(View.VISIBLE); // Show debt item
                }
            }
        });

        // When user presses set date button
        binding.btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        // When user is done selecting their date
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                useDate = true;
                binding.tvDate.setText(materialDatePicker.getHeaderText());
                dueDate = selection;
                Log.i("info", "Date of " + dueDate + " picked.");
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First, clear any previous errors
                binding.etName.setError(null);
                binding.etDebtAmount.setError(null);
                binding.etDebtItem.setError(null);
                // Create debt object
                Debt debt;

                // int debtId; Not needed, automatically created
                contactName = binding.etName.getEditText().getText().toString();
                owesMe = binding.swOwesMe.isChecked();
                isMonetary = binding.swMonetary.isChecked();
                description = binding.etDescription.getEditText().getText().toString();
                recurring = binding.swRecurring.isChecked();

                // Check if name is empty
                if (TextUtils.isEmpty(contactName)) {
                    // Name is empty, display error
                    binding.etName.setError("Name cannot be blank");
                    return;
                }

                // Whether monetary or not, we will create two different debts
                if (isMonetary) {
                    // Try to pull a decimal value
                    try {
                        // We are good, create the debt
                        debtAmount = Double.parseDouble(binding.etDebtAmount.getEditText().getText().toString());
                    } catch (Exception e) {
                        // We are not good, report to user, let them try again
                        binding.etDebtAmount.setError("Debt amount cannot be blank");
                        return;
                    }
                    debt = new Debt(contactName, owesMe, true, debtAmount, null, description, dueDate, recurring);
                } else {
                    // Try to find an item as a debt
                    debtItem = binding.etDebtItem.getEditText().getText().toString();
                    // If no item entered
                    if (TextUtils.isEmpty(debtItem)) {
                        // Report to user, let them try again
                        binding.etDebtItem.setError("Item cannot be blank");
                        return;
                    }
                    // We are good, create the debt
                    debt = new Debt(contactName, owesMe, false, null, debtItem, description, dueDate, recurring);
                }

                // Actually insert debt
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.addDebt(debt);

                // Close dialog
                dismiss();
            }
        });

        return builder.create();
    }
}