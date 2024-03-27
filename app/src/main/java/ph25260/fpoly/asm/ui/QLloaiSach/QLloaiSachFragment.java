package ph25260.fpoly.asm.ui.QLloaiSach;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.adapter.LoaiSachAdapter;
import ph25260.fpoly.asm.adapter.UserAdapter;
import ph25260.fpoly.asm.dao.DaoLoaiSach;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.User;

public class QLloaiSachFragment extends Fragment {

    LoaiSachAdapter loaiSachAdapter;
    DaoLoaiSach daoLoaiSach;
    FloatingActionButton fab;
    RecyclerView rcView;
    private ArrayList<LoaiSach> list = new ArrayList<>();
    public static QLloaiSachFragment newInstance() {
        return new QLloaiSachFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_lloai_sach, container, false);
        rcView = view.findViewById(R.id.rcView);
        fab = getActivity().findViewById(R.id.fab);
        daoLoaiSach = new DaoLoaiSach(getActivity());
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);
        loadData();
        if (rcView != null) ;
        rcView.setLayoutManager(layout);
        rcView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });
        return view;
    }
    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_loaisach, null);
        builder.setView(view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setContentView(R.layout.dialog_add_loaisach);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.editcustom);
        EditText edtLoaiSach = view.findViewById(R.id.txtAddLoaiSachlDialog);
        Button btnAdd = view.findViewById(R.id.btnthemA);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loaiSach = edtLoaiSach.getText().toString();
                if (loaiSach.isEmpty()) {
                    edtLoaiSach.setError("Không được để trống");

                } else {
                    if (daoLoaiSach.insert(loaiSach) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        dialog.show();
    }
    private void loadData() {
        list = (ArrayList<LoaiSach>) daoLoaiSach.getAll();
        loaiSachAdapter = new LoaiSachAdapter(getActivity(), list);
        rcView.setAdapter(loaiSachAdapter);
    }
}