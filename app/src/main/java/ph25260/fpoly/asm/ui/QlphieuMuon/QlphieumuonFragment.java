package ph25260.fpoly.asm.ui.QlphieuMuon;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.adapter.PhieuMuonAdapter;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.LoaiSach;
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
    int selectedPosition = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        fab = getActivity().findViewById(R.id.fab);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rcvPhieuMuon.setLayoutManager(layoutManager);
        daoPhieuMuon = new DaoPhieuMuon(getActivity());
        loadData();
        if (rcvPhieuMuon != null) ;
        rcvPhieuMuon.setLayoutManager(layoutManager);
        rcvPhieuMuon.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        return view;
    }

    private void showDialogAdd() {
        PhieuMuon phieuMuon = new PhieuMuon();
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_phieumuon);
        Spinner spinnerTenNguoiMuon;
        Spinner spinnerTenSach;
        Button btnthemA;
        Switch switchTrangThai;
        TextView txtTrangThai;

        switchTrangThai = dialog.findViewById(R.id.switchTrangThai);
        txtTrangThai = dialog.findViewById(R.id.txtTrangThai);
        spinnerTenNguoiMuon = dialog.findViewById(R.id.spinnerTenNguoiMuon);
        spinnerTenSach = dialog.findViewById(R.id.spinnerTenSach);
        btnthemA = dialog.findViewById(R.id.btnthemA);
        daoLogin = new DaoLogin(getActivity());
        daoSach = new DaoSach(getActivity());

        userArrayList = (ArrayList<User>) daoLogin.getAllUsers();
        ArrayList<String> listUser = new ArrayList<>();
        for (User user : userArrayList) {
            listUser.add(user.getUsername());
        }
        spinnerTenNguoiMuon.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listUser));

        sachArrayList = (ArrayList<Sach>) daoSach.getAll();
        ArrayList<String> listSach = new ArrayList<>();
        for (Sach sach : sachArrayList) {
            listSach.add(sach.getTensach());
        }
        spinnerTenSach.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listSach));


        spinnerTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (spinnerTenSach.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 sách !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerTenNguoiMuon.getCount() <= 0) {
            Toast.makeText(getContext(), "Vui lòng thêm ít nhất 1 thành viên !", Toast.LENGTH_SHORT).show();
            return;
        }

        switchTrangThai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchTrangThai.isChecked()) {
                    txtTrangThai.setText("Đã trả");
                    txtTrangThai.setTextColor(getResources().getColor(R.color.green));
                } else {
                    txtTrangThai.setTextColor(getResources().getColor(R.color.red));
                    phieuMuon.setTrangThai(0);
                    txtTrangThai.setText("Chưa trả");
                }
            }
        });

        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                User user = userArrayList.get(spinnerTenNguoiMuon.getSelectedItemPosition());

                Sach sach = sachArrayList.get(spinnerTenSach.getSelectedItemPosition());
                // Create a PhieuMuon object
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setIdUser(user.getId());
                phieuMuon.setIdSach(sach.getId());
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                String ngaymuon = format.format(date);
                phieuMuon.setNgayThue(ngaymuon);
                phieuMuon.setTrangThai(switchTrangThai.isChecked() ? 1 : 0);

                boolean check = daoPhieuMuon.insert(phieuMuon);

                if (check) {
                    loadData();
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                selectedPosition = 0;
            }
        });
        new CustomDialog().decorBackground(dialog);
        dialog.show();


    }


    private void loadData() {
        daoPhieuMuon = new DaoPhieuMuon(getContext());
        phieuMuonArrayList = daoPhieuMuon.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), phieuMuonArrayList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rcvPhieuMuon.setLayoutManager(layoutManager);
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);
    }
}