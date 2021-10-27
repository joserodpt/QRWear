package pt.josegamerpt.qrwear;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import pt.josegamerpt.qrwear.utils.QRCodeClass;
import pt.josegamerpt.qrwear.utils.QRUtils;

public class MenuItem {

    private String emoji = null;
    private QRCodeClass qrcc = null;
    private String text;
    private int image, image2;
    private Bitmap bitmap1;
    private ClickType ct;

    @Override
    public String toString() {
        return "MenuItem{" +
                "qrcc=" + qrcc +
                ", text='" + text + '\'' +
                ", image=" + image +
                ", image2=" + image2 +
                ", ct=" + ct +
                '}';
    }

    public MenuItem(QRCodeClass qrcc) {
        this.qrcc = qrcc;
        this.ct = ClickType.SHOW_QR;
        this.image = 1;

        if (this.qrcc.usesEmoji())
        {
            this.emoji = this.qrcc.getEmoji();
        }

        this.bitmap1 = QRUtils.trimLeave5Percent(this.qrcc.getEncoder(false).getBitmap(), Color.WHITE);
        this.text = qrcc.getName();
    }

    public MenuItem() {
        //menu
        this.ct = ClickType.LOGO_ADDQR;
        this.image = R.drawable.ic_qr;
        this.image2 = R.drawable.add;
        this.text = "QR Wear";
    }

    public String getText() {
        return this.text;
    }

    public int getImage() {
        return this.image;
    }

    public int getImage2() {
        return this.image2;
    }

    public QRCodeClass getQRCC() {
        return this.qrcc;
    }

    public Bitmap getBitmap1() {
        return this.bitmap1;
    }

    public String getEmoji() {
        return this.emoji;
    }

    public enum ClickType { SHOW_QR, LOGO_ADDQR, ADD_QR }

    public ClickType getClickType() {
        return this.ct;
    }
}