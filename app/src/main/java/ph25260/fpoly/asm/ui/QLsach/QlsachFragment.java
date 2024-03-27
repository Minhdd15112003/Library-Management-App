package ph25260.fpoly.asm.ui.QLsach;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.adapter.LoaiSachAdapter;
import ph25260.fpoly.asm.adapter.SachAdapter;
import ph25260.fpoly.asm.adapter.UserAdapter;
import ph25260.fpoly.asm.dao.DaoLoaiSach;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.LoaiSach;
import ph25260.fpoly.asm.model.Sach;
import ph25260.fpoly.asm.model.User;


public class QlsachFragment extends Fragment {
    private ArrayList<LoaiSach> loaiSachList;
    SachAdapter sachAdapter;
    LoaiSachAdapter loaiSachAdapter;
    DaoSach daoSach;
    private DaoLoaiSach daoLoaiSach;

    FloatingActionButton fab;
    RecyclerView rcView;
    private ArrayList<Sach> list = new ArrayList<>();
    private EditText txtAddTenSachDialog;
    private EditText txtAddgiaThueDialog;
    private Spinner spnLoaiSach;
    private Button btnthemA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);
        rcView = view.findViewById(R.id.rcvListSach);
        fab = getActivity().findViewById(R.id.fab);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        daoSach = new DaoSach(getActivity());
        daoLoaiSach = new DaoLoaiSach(getActivity()); // Khởi tạo  ngay trong onCreate
        loadData();
        if (rcView != null) ;
        rcView.setLayoutManager(layoutManager);
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
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_sach);

        Spinner spinner = dialog.findViewById(R.id.spnLoaiSach);
        loadSpinnerData(spinner); // Load dữ liệu cho Spinner

        txtAddTenSachDialog = dialog.findViewById(R.id.txtAddTenSachDialog);
        txtAddgiaThueDialog = dialog.findViewById(R.id.txtAddgiaThueDialog);
        spnLoaiSach = dialog.findViewById(R.id.spnLoaiSach);
        btnthemA = dialog.findViewById(R.id.btnthemA);
        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tensach = txtAddTenSachDialog.getText().toString();
                String giathue = txtAddgiaThueDialog.getText().toString();

                String choosedTypeName = spinner.getSelectedItem().toString();
                int selectedId = 0;
                for (LoaiSach x: loaiSachList) {
                    if (x.getTenloai().equals(choosedTypeName)) {
                        selectedId = x.getId();
                    }
                }

                // Populate Spinner (có thể đặt trong onCreate hoặc sau khi có dữ liệu)
                if (tensach.isEmpty() || giathue.isEmpty()) {
                    txtAddTenSachDialog.setError("Không được để trống");
                    txtAddgiaThueDialog.setError("Không được để trống");
                } else {

                    boolean isAdded = daoSach.insert(tensach, giathue, selectedId);
                    if (isAdded) {
                        loadData();
                        Toast.makeText(getContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.editcustom);
        decorDialogBackground(dialog);
        dialog.show();
    }

    public void decorDialogBackground(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void loadData() {
        list = daoSach.getAll();
        sachAdapter = new SachAdapter(getActivity(), list);
        rcView.setAdapter(sachAdapter);
    }
    private void loadSpinnerData(Spinner spinner) {

        // get all and fill to the drop down
        loaiSachList = daoLoaiSach.getAll();
        if (loaiSachList.size() <= 0) {
            Toast.makeText(getActivity(), "Please create book category first!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> simpleName = new ArrayList<>();
        for (LoaiSach x: loaiSachList) {
            simpleName.add(x.getTenloai());
        }

        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, simpleName));

    }
}