package pt.josegamerpt.qrwear.recycleview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import pt.josegamerpt.qrwear.activities.MainActivity;

public class DragAndSwipeRecycleView extends ItemTouchHelper.Callback {

    private final QRCodesAdapter mAdapter;

    public DragAndSwipeRecycleView(QRCodesAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = createDragFlags(viewHolder.getBindingAdapterPosition());
        int swipeFlags = createSwipeFlags(viewHolder.getBindingAdapterPosition());
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        if (target.getBindingAdapterPosition() != 0) {
            mAdapter.swapItems(viewHolder, target);
            return true;
        }
        return false;
    }

    private int createDragFlags(int position) {
        return position == 0 ? 0 : ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    }

    private int createSwipeFlags(int position) {
        return position == 0 ? 0 : ItemTouchHelper.START | ItemTouchHelper.END;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.removeQR(viewHolder.getBindingAdapterPosition());
    }
}