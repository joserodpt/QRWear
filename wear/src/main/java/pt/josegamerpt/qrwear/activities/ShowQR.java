package pt.josegamerpt.qrwear.activities;

import static pt.josegamerpt.qrwear.utils.QRUtils.trimLeave5Percent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidmads.library.qrgenearator.QRGEncoder;
import pt.josegamerpt.qrwear.databinding.ActivityShowQrBinding;
import pt.josegamerpt.qrwear.utils.QRCodeClass;

public class ShowQR extends Activity {

    private TextView mTextView;
    private ActivityShowQrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Object qrc = getIntent().getSerializableExtra("qrc");

            binding.imageView.setImageBitmap(trimLeave5Percent(((QRCodeClass) qrc).getEncoder(true).getBitmap(), Color.WHITE));
        }

        binding.bkg.setBackgroundColor(Color.WHITE);

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
    }

}