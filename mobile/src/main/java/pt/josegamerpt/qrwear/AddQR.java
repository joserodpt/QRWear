package pt.josegamerpt.qrwear;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class AddQR extends AppCompatActivity {

    private static final String DATA_KEY = "pt.josegamerpt.qrwear.datakey";
    private DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qr);

        setTitle(getString(R.string.app_name) + " > " + getString(R.string.add_button));
        dataClient = Wearable.getDataClient(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarCódigoQR();
            }
        });
    }

    private void adicionarCódigoQR() {

        EditText name = findViewById(R.id.nameText);
        EditText value = findViewById(R.id.dataText);
        EditText emoij = findViewById(R.id.emojiText);
        Spinner type = findViewById(R.id.spinner);
        Boolean useEmoji = ((Switch) findViewById(R.id.switch1)).isChecked();
        String sep = "/~";

        if (Strings.isEmptyOrWhitespace(name.getText().toString()) ||
                Strings.isEmptyOrWhitespace(value.getText().toString())||
                Strings.isEmptyOrWhitespace(emoij.getText().toString()))
        {
            Toast toast = Toast.makeText(this, getString(R.string.fields_empty), Toast.LENGTH_SHORT);
            toast.show();
        } else {

            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/qr");
            putDataMapReq.getDataMap().putString(DATA_KEY, name.getText().toString() + sep + value.getText().toString() + sep + type.getSelectedItem().toString() + sep + emoij.getText().toString() + sep + useEmoji);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
        }
    }

}