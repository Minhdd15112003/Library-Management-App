package ph25260.fpoly.asm.ui.QLthanhVien;

import androidx.cardview.widget.CardView;
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
import ph25260.fpoly.asm.adapter.UserAdapter;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.databinding.ActivityMainBinding;
import ph25260.fpoly.asm.model.User;

public class QLthanhVienFragment extends Fragment {
    UserAdapter userAdapter;
    DaoLogin daoLoginl;
    FloatingActionButton fab;
    RecyclerView rcvthanhvien;
    private ArrayList<User> list = new ArrayList<>();

    public static QLthanhVienFragment newInstance() {
        return new QLthanhVienFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_lthanh_vien, container, false);
        rcvthanhvien = view.findViewById(R.id.rcvthanhvien);
        fab = getActivity().findViewById(R.id.fab);
        daoLoginl = new DaoLogin(getActivity());
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);
        loadData();
        if (rcvthanhvien != null) ;
        rcvthanhvien.setLayoutManager(layout);
        rcvthanhvien.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_user, null);
        builder.setView(view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setContentView(R.layout.dialog_add_user);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.editcustom);
        EditText edtEmail = view.findViewById(R.id.txtAddEmailDialog);
        EditText edtName = view.findViewById(R.id.txtAddUsernameDialog);
        EditText edtPass = view.findViewById(R.id.txtAddPasswordDialog);
        EditText edtNumberPhone = view.findViewById(R.id.txtAddNumberPhoneDialog);
        Button btnAdd = view.findViewById(R.id.btnthemA);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String username = edtName.getText().toString();
                String password = edtPass.getText().toString();
                String numberphone = edtNumberPhone.getText().toString();

                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || numberphone.isEmpty()) {
                    edtEmail.setError("Không được để trống");
                    edtName.setError("Không được để trống");
                    edtPass.setError("Không được để trống");
                    edtNumberPhone.setError("Không được để trống");

                } else {
                    if (daoLoginl.addUser(email, username, password, numberphone) > 0) {
                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
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
        list = (ArrayList<User>) daoLoginl.getAllUsers();
        userAdapter = new UserAdapter(getActivity(), list);
        rcvthanhvien.setAdapter(userAdapter);
    }


}