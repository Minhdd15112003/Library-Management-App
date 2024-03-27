package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLoaiSach;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.viewholder> {

    private final Context context;
    private final ArrayList<Sach> list;
    DaoSach daoSach;
    DaoLoaiSach daoLoaiSach;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        daoSach = new DaoSach(context);
    }

    @NonNull
    @Override
    public SachAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sach, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.viewholder holder, int position) {
        daoLoaiSach = new DaoLoaiSach(context);
        holder.txtMasv.setText(list.get(position).getId() + "");
        holder.txtTenSach.setText(list.get(position).getTensach());
        holder.txtgiaSach.setText(list.get(position).getGiaSach());
        LoaiSach loaiSach = daoLoaiSach.getLoaiSachById(list.get(position).getLoaiSachId());
        holder.txtLoaiSach.setText(loaiSach.getTenloai());

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate();
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoSach.delete(list.get(position).getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    private void showDialogUpdate(LoaiSach loaiSach) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach, null);
        EditText txtAddTenSachDialog;
        EditText txtAddgiaThueDialog;
        Spinner spnLoaiSach;
        Button btnthemA;
        txtAddTenSachDialog = view.findViewById(R.id.txtAddTenSachDialog);
        txtAddgiaThueDialog = view.findViewById(R.id.txtAddgiaThueDialog);
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        btnthemA = view.findViewById(R.id.btnthemA);

    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtMasv;
        ImageView imgUpdate;
        ImageView imgDelete;
        TextView txtTenSach;
        TextView txtgiaSach;
        TextView txtLoaiSach;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMasv = itemView.findViewById(R.id.txtMasv);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtgiaSach = itemView.findViewById(R.id.txtgiaSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
        }
    }
}
