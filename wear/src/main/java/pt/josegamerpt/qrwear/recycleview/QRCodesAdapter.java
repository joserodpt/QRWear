package pt.josegamerpt.qrwear.recycleview;

import static pt.josegamerpt.qrwear.activities.MainActivity.dataManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;
import java.util.Collections;

import pt.josegamerpt.qrwear.MenuItem;
import pt.josegamerpt.qrwear.R;
import pt.josegamerpt.qrwear.utils.QRCodeClass;

public class QRCodesAdapter extends RecyclerView.Adapter<QRCodesAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<MenuItem> dataSource;

    public ArrayList<MenuItem> getItems() {
        return dataSource;
    }

    public void addQRCC(QRCodeClass qrcc) {
        dataSource.add(new MenuItem(qrcc));
        dataManager.saveData();
        int from = this.getItemCount();
        this.notifyItemInserted(dataSource.size() - 1);
        this.notifyItemRangeChanged(from, dataSource.size());
    }

    public interface AdapterCallback {
        void onItemClicked(Integer position, MenuItem mi);
    }

    private AdapterCallback callback;

    public QRCodesAdapter(Context context, ArrayList<MenuItem> dataArgs, AdapterCallback callback) {
        this.context = context;
        this.dataSource = dataArgs;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout menuContainer;
        TextView menuText;
        ImageView menuIcon;
        ImageView menuIcon2;
        ImageView menuIcon3;
        TextView emojiViewer;

        public RecyclerViewHolder(View view) {
            super(view);
            menuContainer = view.findViewById(R.id.menu_container);
            menuText = view.findViewById(R.id.menu_item);
            menuIcon = view.findViewById(R.id.menu_icon);
            menuIcon2 = view.findViewById(R.id.menu_icon2);
            emojiViewer = view.findViewById(R.id.emojiViewer);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        MenuItem data_provider = dataSource.get(position);

        holder.menuText.setSelected(true);

        if (!Strings.isEmptyOrWhitespace(data_provider.getText()))
        {
            holder.menuText.setText(data_provider.getText());
            if (data_provider.getImage() != 1) {
                holder.menuIcon.setImageResource(data_provider.getImage());
            } else  {
                holder.menuIcon.setImageBitmap(data_provider.getBitmap1());
            }
        } else {
            holder.menuIcon3.setVisibility(View.VISIBLE);
            holder.menuText.setVisibility(View.GONE);
            holder.menuIcon3.setImageResource(data_provider.getImage());
        }
        if (data_provider.getImage2() != 0) {
            holder.menuIcon2.setImageResource(data_provider.getImage2());
            holder.menuIcon2.setVisibility(View.VISIBLE);
        }

        if (data_provider.getEmoji() != null)
        {
            holder.menuIcon.setVisibility(View.GONE);
            holder.emojiViewer.setText(data_provider.getEmoji());
            holder.emojiViewer.setVisibility(View.VISIBLE);
        }

        holder.menuContainer.setOnClickListener(v -> {
            if (callback != null) {
                callback.onItemClicked(position, data_provider);
            }
        });

    }

    public void removeQR(int position) {
        if (position != 0 && getItemCount() != position) {
            MenuItem mi = dataSource.get(position);
            if (mi != null && mi.getClickType() == MenuItem.ClickType.SHOW_QR) {
                dataSource.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataSource.size());

                dataManager.saveData();
                Toast toast = Toast.makeText(context, mi.getQRCC().getName() + context.getString(R.string.qr_removed), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void swapItems(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Collections.swap(this.dataSource, viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());

        Log.v("tag", "INICIO");

        int a = 0;
        for (MenuItem menuItem : this.dataSource) {
            Log.v("tag", a + " > " + menuItem.toString());
            a++;
        }

        Log.v("tag", "FIM");


        notifyItemMoved(viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());
        dataManager.saveData();
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}