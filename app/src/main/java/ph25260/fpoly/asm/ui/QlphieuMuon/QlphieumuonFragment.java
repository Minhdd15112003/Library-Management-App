package ph25260.fpoly.asm.ui.QlphieuMuon;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.adapter.PhieuMuonAdapter;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.PhieuMuon;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.model.User;
import ph25260.fpoly.asm.utils.CustomDialog;

public class QlphieumuonFragment extends Fragment {

    private RecyclerView rcvPhieuMuon;
    DaoPhieuMuon daoPhieuMuon;
    DaoSach daoSach;
    DaoLogin daoLogin;
    ArrayList<PhieuMuon> phieuMuonArrayList;
    ArrayList<Sach> sachArrayList;
    ArrayList<User> userArrayList;
    PhieuMuonAdapter phieuMuonAdapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        fab = getActivity().findViewById(R.id.fab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);
        daoPhieuMuon = new DaoPhieuMuon(getActivity());
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        return view;
    }

    private void showDialogAdd() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_phieumuon);
        Spinner spinnerTenNguoiMuon;
        Spinner spinnerTenSach;
        TextView txtNgayMuon;
        TextView txtTienThue;
        Button btnthemA;

        spinnerTenNguoiMuon = dialog.findViewById(R.id.spinnerTenNguoiMuon);
        spinnerTenSach = dialog.findViewById(R.id.spinnerTenSach);
        txtNgayMuon = dialog.findViewById(R.id.txtNgayMuon);
        txtTienThue = dialog.findViewById(R.id.txtTienThue);
        btnthemA = dialog.findViewById(R.id.btnthemA);
        daoLogin = new DaoLogin(getActivity());
        daoSach = new DaoSach(getActivity());
        loadSpinnerUser(spinnerTenNguoiMuon);
        loadSpinnerSach(spinnerTenSach);
        if (spinnerTenSach.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 sách !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerTenNguoiMuon.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 thành viên !", Toast.LENGTH_SHORT).show();
            return;
        }

        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                String ngaymuon = format.format(date);

                // Get selected user and book
                User selectedUser = userArrayList.get(spinnerTenNguoiMuon.getSelectedItemPosition());
                Sach selectedSach = sachArrayList.get(spinnerTenSach.getSelectedItemPosition());

                // Get values from selected user and book
                String nguoiMuon = selectedUser.getUsername();
                int masach = selectedSach.getId();
                String tensach = selectedSach.getTensach();
                String loaisach = String.valueOf(selectedSach.getLoaiSachId());
                int giaThue = Integer.parseInt(selectedSach.getGiaSach());

                // Check if ngaymuon is null
                if (ngaymuon == null) {
                    Toast.makeText(getContext(), "Ngày mượn không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = daoPhieuMuon.insert(nguoiMuon, masach, tensach, loaisach, giaThue, ngaymuon, 0);

                if (check) {
                    loadData();

                    dialog.dismiss();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new CustomDialog().decorBackground(dialog);
        dialog.show();
    }

    private void loadSpinnerUser(Spinner spinnerTenNguoiMuon) {
        userArrayList = (ArrayList<User>) daoLogin.getAllUsers();
        ArrayList<String> list = new ArrayList<>();
        for (User user : userArrayList) {
            list.add(user.getUsername());
        }
        spinnerTenNguoiMuon.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, list));
    }

    private void loadSpinnerSach(Spinner spinnerSach) {
        sachArrayList = (ArrayList<Sach>) daoSach.getAll();
        ArrayList<String> list = new ArrayList<>();
        for (Sach sach : sachArrayList) {
            list.add(sach.getTensach());
        }
        spinnerSach.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, list));
    }

    private void loadData() {
        daoPhieuMuon = new DaoPhieuMuon(getContext());
        phieuMuonArrayList = daoPhieuMuon.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), phieuMuonArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcvPhieuMuon.setLayoutManager(manager);
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);
    }
}