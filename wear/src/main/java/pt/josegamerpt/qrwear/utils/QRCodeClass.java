package pt.josegamerpt.qrwear.utils;

import android.util.Log;

import java.io.Serializable;

import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeClass implements Serializable {

    private final String name;
    private final String data;
    private final String type;
    private final String sep = "/~";
    private final String emoji;
    private final boolean useEmoji;

    public QRCodeClass(String name, String data, String type, String emoji, Boolean useEmoji)
    {
        this.name = name;
        this.data = data;
        this.type = type;
        this.emoji = emoji;
        this.useEmoji = useEmoji;
        Log.d("tag", toString());
    }

    @Override
    public String toString() {
        return "QRCodeClass{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                ", type='" + type + '\'' +
                ", sep='" + sep + '\'' +
                ", emoji='" + emoji + '\'' +
                ", useEmoji=" + useEmoji +
                '}';
    }

    public QRCodeClass(String serialData)
    {
        String[] split = serialData.split(sep);
        this.name = split[0];
        this.data = split[1];
        this.type = split[2];
        this.emoji = split[3];
        this.useEmoji = Boolean.parseBoolean(split[4]);
    }

    public String getDataSerialized() {
        return this.name + sep + this.data + sep + this.type + sep + this.emoji + sep + this.useEmoji;
    }

    public String getName() {
        return this.name;
    }

    public String getDataQR() {
        return this.data;
    }

    public QRGEncoder getEncoder(boolean big)
    { return new QRGEncoder(this.getDataQR(), null, this.getContentType(), big ? 250 : 50); }

    public String getContentType() {
        return this.type + "_TYPE";
    }

    public boolean usesEmoji() {
        return this.useEmoji;
    }

    public String getEmoji() {
        return this.emoji;
    }
}
