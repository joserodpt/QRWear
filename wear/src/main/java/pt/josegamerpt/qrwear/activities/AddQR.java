package pt.josegamerpt.qrwear.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;

import androidmads.library.qrgenearator.QRGContents;
import pt.josegamerpt.qrwear.R;
import pt.josegamerpt.qrwear.databinding.ActivityAddQrBinding;
import pt.josegamerpt.qrwear.utils.QRCodeClass;

public class AddQR extends Activity {

    private TextView mTextView;
    private ActivityAddQrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.save.setOnClickListener(v -> {
            if (Strings.isEmptyOrWhitespace(binding.qrname.getText().toString()) ||
                    Strings.isEmptyOrWhitespace(binding.qrval.getText().toString()) ||
                    Strings.isEmptyOrWhitespace(binding.emoji.getText().toString())) {
                Toast toast = Toast.makeText(this, getString(R.string.fields_empty), Toast.LENGTH_SHORT);
                toast.show();
            } else {
            Intent intent = new Intent();
            intent.putExtra("refresh", 1);
            intent.putExtra("qrcc", new QRCodeClass(binding.qrname.getText().toString(),
                    binding.qrval.getText().toString(),
                    binding.spinner.getSelectedItem().toString(),
                    binding.emoji.getText().toString(),
                    binding.switch1.isChecked()));
            setResult(RESULT_OK, intent);

            finish();
        }
        });

        binding.main.requestFocus();
    }
}