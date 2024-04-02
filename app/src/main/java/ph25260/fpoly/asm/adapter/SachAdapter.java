package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLoaiSach;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.utils.CustomDialog;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.viewholder> {
    private ArrayList<LoaiSach> loaiSachList;
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
        holder.txtgiaSach.setText(list.get(position).getGiaSach() + "");
        LoaiSach loaiSach = daoLoaiSach.getID(String.valueOf(list.get(position).getLoaiSachId()));
        if (loaiSach != null) {
            holder.txtLoaiSach.setText(loaiSach.getTenloai());
        } else {
            holder.txtLoaiSach.setText("Không tồn tại");
        }

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialogDelete(list.get(position));
            }
        });

    }

    private void dialogDelete(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            if (daoSach.delete(sach.getId())) {
                list.remove(sach);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
    }

    private void showDialogUpdate(Sach sach) {
        EditText txtAddTenSachDialog;
        EditText txtAddgiaThueDialog;
        Spinner spnLoaiSach;
        Button btnthemA;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_sach);
        spnLoaiSach = dialog.findViewById(R.id.spnLoaiSach);
        txtAddTenSachDialog = dialog.findViewById(R.id.txtAddTenSachDialog);
        txtAddgiaThueDialog = dialog.findViewById(R.id.txtAddgiaThueDialog);
        txtAddTenSachDialog.setText(sach.getTensach());
        txtAddgiaThueDialog.setText(sach.getGiaSach() + "");
        btnthemA = dialog.findViewById(R.id.btnthemA);
        loadSpinnerData(spnLoaiSach);
        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tensach = txtAddTenSachDialog.getText().toString();
                String giathue = txtAddgiaThueDialog.getText().toString();
                String choosedTypeName = spnLoaiSach.getSelectedItem().toString();
                int selectedId = 0;
                for (LoaiSach x : daoLoaiSach.getAll()) {
                    if (x.getTenloai().equals(choosedTypeName)) {
                        selectedId = x.getId();
                    }
                }
                if (tensach.isEmpty() || giathue.isEmpty()) {
                    txtAddTenSachDialog.setError("Không được để trống");
                    txtAddTenSachDialog.setError("Không được để trống");
                    return;
                }

                sach.setTensach(tensach);
                sach.setGiaSach(Integer.parseInt(giathue));

                if (daoSach.update(sach)) {
                    list.addAll(daoSach.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        new CustomDialog().decorBackground(dialog);
        dialog.show();
    }

    private void loadSpinnerData(Spinner spinner) {

        // get all and fill to the drop down
        loaiSachList = daoLoaiSach.getAll();
        if (loaiSachList.size() <= 0) {
            Toast.makeText(context, "Please create book category first!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> simpleName = new ArrayList<>();
        for (LoaiSach x : loaiSachList) {
            simpleName.add(x.getTenloai());
        }

        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, simpleName));

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
