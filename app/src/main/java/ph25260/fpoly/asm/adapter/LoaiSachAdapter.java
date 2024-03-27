package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLoaiSach;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.User;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.viewholder> {

    private final Context context;
    private final ArrayList<LoaiSach> list;
    DaoLoaiSach daoLoaiSach;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        daoLoaiSach = new DaoLoaiSach(context);
    }


    @NonNull
    @Override
    public LoaiSachAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, null);
        return new LoaiSachAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.viewholder holder, int position) {
        holder.txtMasv.setText(list.get(position).getId() + "");
        holder.txtLoaiSach.setText(list.get(position).getTenloai());
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(list.get(position));
            }
        });
    }
    private void showDialogUpdate(LoaiSach loaiSach) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach, null);
        TextView txtLoaiSach = view.findViewById(R.id.txtAddLoaiSachlDialog);
        Button btnThem = view.findViewById(R.id.btnthemA);
        txtLoaiSach.setText(loaiSach.getTenloai());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloaiSach = txtLoaiSach.getText().toString();
                if (tenloaiSach.isEmpty()) {
                    txtLoaiSach.setError("Loại sách không được để trống");
                    return;
                }

                // Update the user object with the new values
                loaiSach.setTenloai(tenloaiSach);

                if (daoLoaiSach.update(loaiSach)) {
                    list.clear();
                    list.addAll(daoLoaiSach.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showDialogDelete(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete this user?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (daoLoaiSach.delete(loaiSach.getId())) {
                    list.clear();
                    list.addAll(daoLoaiSach.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    @Override
    public int getItemCount() {
       if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class
    viewholder extends RecyclerView.ViewHolder {
         TextView txtMasv;
         ImageView imgUpdate;
         ImageView imgDelete;
         TextView txtLoaiSach;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMasv = itemView.findViewById(R.id.txtMasv);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);

        }
    }
    public void reloadData() {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
