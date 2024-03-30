package ph25260.fpoly.asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.PhieuMuon;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.model.User;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.viewholder> {

    private final Context context;
    private final ArrayList<PhieuMuon> list;
    DaoPhieuMuon daoPhieuMuon;
    DaoSach daoSach;
    DaoLogin daoLogin;

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
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.viewholder holder, int position) {
        User user = daoLogin.getID(String.valueOf(list.get(position).getId()));
        Sach sach = daoSach.getID(String.valueOf(list.get(position).getId()));

        holder.txtMaphieu.setText(list.get(position).getId() + "");

        holder.txtNguoiMuon.setText(user.getUsername());

        holder.txtMasach.setText(sach.getId() + "");

        holder.txtTenSach.setText(sach.getTensach());

        holder.txtLoaiSach.setText(sach.getLoaiSachId() + "");
        holder.txtNgayThue.setText(list.get(position).getNgayThue());
        holder.txtGiaThue.setText(sach.getGiaSach() + "");
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
        ImageView imgUpdate;
        ImageView imgDelete;
        TextView txtNguoiMuon;
        TextView txtTenSach;
        TextView txtLoaiSach;
        TextView txtGiaThue;
        TextView txtNgayThue;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMaphieu = itemView.findViewById(R.id.txtMaphieu);
            txtMasach = itemView.findViewById(R.id.txtMaSach);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txtNguoiMuon = itemView.findViewById(R.id.txtNguoiMuon);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtNgayThue = itemView.findViewById(R.id.txtNgayThue);
        }
    }
}
