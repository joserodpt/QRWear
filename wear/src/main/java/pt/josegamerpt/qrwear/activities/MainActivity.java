package pt.josegamerpt.qrwear.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import pt.josegamerpt.qrwear.DataManager;
import pt.josegamerpt.qrwear.MenuItem;
import pt.josegamerpt.qrwear.R;
import pt.josegamerpt.qrwear.databinding.ActivityMainBinding;
import pt.josegamerpt.qrwear.recycleview.CustomScrollingLayoutCallback;
import pt.josegamerpt.qrwear.recycleview.DragAndSwipeRecycleView;
import pt.josegamerpt.qrwear.recycleview.QRCodesAdapter;
import pt.josegamerpt.qrwear.utils.QRCodeClass;

public class MainActivity extends Activity implements DataClient.OnDataChangedListener {

    private static final String DATA_KEY = "pt.josegamerpt.qrwear.datakey";
    public static DataManager dataManager;
    public QRCodesAdapter adapter;
    private ActivityMainBinding binding;
    private DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataClient = Wearable.getDataClient(this);

        dataManager = new DataManager(this);

        WearableRecyclerView wrv = findViewById(R.id.rcv);
        wrv.setEdgeItemsCenteringEnabled(true);
        wrv.setLayoutManager(new WearableLinearLayoutManager(this, new CustomScrollingLayoutCallback()));

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem());
        dataManager.loadData().forEach(qrCodeClass -> menuItems.add(new MenuItem(qrCodeClass)));

        adapter = new QRCodesAdapter(this, menuItems, (position, mi) -> {
            Intent i = null;

            if (mi != null) {
                switch (mi.getClickType()) {
                    case SHOW_QR:
                        i = new Intent(MainActivity.this, ShowQR.class);
                        i.putExtra("qrc", mi.getQRCC());
                        break;
                    case LOGO_ADDQR:
                        i = new Intent(MainActivity.this, AddQR.class);
                        break;
                }
            }

            if (i != null) {
                startActivityForResult(i, 20212022);
            }
        });
        wrv.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new DragAndSwipeRecycleView(adapter));
        touchHelper.attachToRecyclerView(wrv);

        wrv.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20212022 && resultCode == Activity.RESULT_OK) {
            final int result = data.getIntExtra("refresh", 0);
            QRCodeClass o = (QRCodeClass) data.getSerializableExtra("qrcc");
            if (result == 1) {
                adapter.addQRCC(o);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/qr") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    addQR(dataMap.getString(DATA_KEY));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
            }
        }
    }

    private void addQR(String s) {
        QRCodeClass qqc = new QRCodeClass(s);
        adapter.addQRCC(qqc);

        Toast.makeText(MainActivity.this, qqc.getName() + getString(R.string.qr_added), Toast.LENGTH_SHORT).show();
    }
}