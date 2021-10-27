package pt.josegamerpt.qrwear;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.josegamerpt.qrwear.activities.MainActivity;
import pt.josegamerpt.qrwear.utils.QRCodeClass;

public class DataManager {

    private MainActivity a;

    public DataManager(MainActivity a)
    {
        this.a = a;
    }

    public List<String> transformData() {
        List<String> data = new ArrayList<>();

        for (MenuItem item : a.adapter.getItems()) {
            if (item.getQRCC() != null)
            {
                data.add(item.getQRCC().getDataSerialized());
            }
        }

        return data;
    }

    public void saveData()
    {
        Set<String> usersSet= new HashSet<>(transformData());
        SharedPreferences prefs = a.getSharedPreferences("qrwear", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("qrdata", usersSet);
        editor.apply();
    }


    public List<QRCodeClass> loadData()
    {
        List<QRCodeClass> codes = new ArrayList<>();
        SharedPreferences prefs = a.getSharedPreferences("qrwear", 0);
        Set<String> usersSet= prefs.getStringSet("qrdata", new HashSet<>());
        usersSet.forEach(s -> codes.add(new QRCodeClass(s)));
        return codes;
    }

    public ArrayList<String> loadDataString() {
        ArrayList<String> codes = new ArrayList<>();
        loadData().forEach(qrCodeClass -> codes.add(qrCodeClass.getDataSerialized()));
        return codes;
    }
}
