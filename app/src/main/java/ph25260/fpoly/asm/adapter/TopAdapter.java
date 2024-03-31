package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.model.Top;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    ArrayList<Top> list;
    Context context;

    public TopAdapter(ArrayList<Top> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top_sach, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Top top = list.get(position);
        holder.txtTen.setText(top.getTenSach());
        holder.txtSoLuong.setText(String.valueOf(top.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtSoLuong;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenSach);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
        }
    }
}
