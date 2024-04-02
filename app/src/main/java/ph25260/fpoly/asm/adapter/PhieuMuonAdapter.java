package ph25260.fpoly.asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.PhieuMuon;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.model.User;
import ph25260.fpoly.asm.utils.CustomDialog;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.viewholder> {

    private final Context context;
    private final ArrayList<PhieuMuon> list;
    DaoPhieuMuon daoPhieuMuon;
    DaoSach daoSach;
    DaoLogin daoLogin;
    ArrayList<PhieuMuon> phieuMuonArrayList;
    ArrayList<Sach> sachArrayList;
    ArrayList<User> userArrayList;
    PhieuMuonAdapter phieuMuonAdapter;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        this.daoLogin = new DaoLogin(context);
        this.daoSach = new DaoSach(context);
        this.daoPhieuMuon = new DaoPhieuMuon(context);
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, null);
        return new PhieuMuonAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

        PhieuMuon phieuMuon = list.get(position);
        Sach sach = daoSach.getSachByID(phieuMuon.getIdSach() + "");
        LoaiSach loaiSach = daoSach.layThongTinById(sach.getLoaiSachId()+ "");
        User user = daoLogin.getUserById(phieuMuon.getIdUser());


        holder.txtMaphieu.setText(list.get(position).getId() + "");

        if (user != null) {
            holder.txtNguoiMuon.setText(user.getUsername());
        } else {
            holder.txtNguoiMuon.setText("Không tồn tại");
        }

        if (sach != null) {
            holder.txtMasach.setText(sach.getId() + "");
            holder.txtTenSach.setText(sach.getTensach());
            holder.txtLoaiSach.setText(loaiSach.getTenloai());
            holder.txtGiaThue.setText(sach.getGiaSach() + "");
        } else {
            holder.txtMasach.setText("Không tồn tại");
            holder.txtTenSach.setText("Không tồn tại");
            holder.txtLoaiSach.setText("Không tồn tại");
            holder.txtGiaThue.setText("Không tồn tại");
        }
//
//        if (loaiSach != null) {
//            holder.txtLoaiSach.setText(loaiSach.getTenloai());
//        } else {
//            holder.txtLoaiSach.setText("Không tồn tại");
//        }
        holder.txtNgayThue.setText(list.get(position).getNgayThue());

        if (list.get(position).getTrangThai() == 1) {
            holder.txtTrangThai.setText("Đã trả");
            holder.txtTrangThai.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.txtTrangThai.setText("Chưa trả");
            holder.txtTrangThai.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdate(list.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(list.get(position));
            }
        });
    }

    private void dialogUpdate(PhieuMuon phieuMuon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_phieumuon);
        Spinner spinnerTenNguoiMuon;
        Spinner spinnerTenSach;
        Switch switchTrangThai;
        TextView txtTrangThai;
        Button btnthemA;
        spinnerTenNguoiMuon = dialog.findViewById(R.id.spinnerTenNguoiMuon);
        spinnerTenSach = dialog.findViewById(R.id.spinnerTenSach);
        switchTrangThai = dialog.findViewById(R.id.switchTrangThai);
        txtTrangThai = dialog.findViewById(R.id.txtTrangThai);
        btnthemA = dialog.findViewById(R.id.btnthemA);
        loadSpinnerSach(spinnerTenSach);
        loadSpinnerUser(spinnerTenNguoiMuon);

        switchTrangThai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchTrangThai.isChecked()) {
                    txtTrangThai.setText("Đã trả");
                    txtTrangThai.setTextColor(context.getResources().getColor(R.color.green));
                } else {
                    txtTrangThai.setTextColor(context.getResources().getColor(R.color.red));
                    txtTrangThai.setText("Chưa trả");
                }
            }
        });
        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                String ngaymuon = format.format(date);

                User selectedUser = userArrayList.get(spinnerTenNguoiMuon.getSelectedItemPosition());
                Sach selectedSach = sachArrayList.get(spinnerTenSach.getSelectedItemPosition());

                // Create a PhieuMuon object
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setIdUser(Integer.parseInt(selectedUser.getUsername()));
                phieuMuon.setIdSach(selectedSach.getId());
                phieuMuon.setLoaiSachId(Integer.parseInt(String.valueOf(selectedSach.getLoaiSachId())));
                phieuMuon.setNgayThue(ngaymuon);
                phieuMuon.setTrangThai(switchTrangThai.isChecked() ? 1 : 0);

                boolean check = daoPhieuMuon.update(phieuMuon);
                if (check) {
                    loadData();
                    dialog.dismiss();
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        CustomDialog customDialog = new CustomDialog();
        customDialog.decorBackground(dialog);
        dialog.show();
    }

    private void dialogDelete(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu mượn này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = daoPhieuMuon.delete(phieuMuon.getId());
                if (check) {
                    loadData();
                    dialog.dismiss();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void loadSpinnerUser(Spinner spinnerTenNguoiMuon) {
        userArrayList = (ArrayList<User>) daoLogin.getAllUsers();
        ArrayList<String> list = new ArrayList<>();
        for (User user : userArrayList) {
            list.add(user.getUsername());
        }
        spinnerTenNguoiMuon.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, list));
    }

    private void loadSpinnerSach(Spinner spinnerSach) {
        sachArrayList = (ArrayList<Sach>) daoSach.getAll();
        ArrayList<String> list = new ArrayList<>();
        for (Sach sach : sachArrayList) {
            list.add(sach.getTensach());
        }
        spinnerSach.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, list));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtMaphieu;
        TextView txtMasach;
        ImageView imgDelete;
        ImageView imgUpdate;
        TextView txtNguoiMuon;
        TextView txtTenSach;
        TextView txtLoaiSach;
        TextView txtGiaThue;
        TextView txtNgayThue;
        Switch switchTrangThai;
        TextView txtTrangThai;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMaphieu = itemView.findViewById(R.id.txtMaphieu);
            txtMasach = itemView.findViewById(R.id.txtMaSach);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            txtNguoiMuon = itemView.findViewById(R.id.txtNguoiMuon);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtNgayThue = itemView.findViewById(R.id.txtNgayThue);
            switchTrangThai = itemView.findViewById(R.id.switchTrangThai);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
        }
    }

    public void loadData() {
        list.clear();
        list.addAll(daoPhieuMuon.getAll());
        notifyDataSetChanged();
    }
}
