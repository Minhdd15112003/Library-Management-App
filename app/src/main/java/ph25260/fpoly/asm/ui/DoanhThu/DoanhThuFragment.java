package ph25260.fpoly.asm.ui.DoanhThu;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;

public class DoanhThuFragment extends Fragment {

    private EditText edFromDate;
    private EditText edToDate;
    private TextView tvDoanhThu;
    private Button btnGetDoanhThu,btnTuNgay,btnDenNgay;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnGetDoanhThu = view.findViewById(R.id.btnGetDoanhThu);

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        String thang = "";
                        if (i2 < 10){
                            ngay = "0" + i2;
                        }else {
                            ngay = String.valueOf(i2);
                        }

                        if ((i1 + 1) < 10){
                            thang = "0" + (i1 + 1);
                        }else {
                            thang = String.valueOf((i1 + 1));
                        }
                        edFromDate.setText(i + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        String thang = "";
                        if (i2 < 10){
                            ngay = "0" + i2;
                        }else {
                            ngay = String.valueOf(i2);
                        }

                        if ((i1 + 1) < 10){
                            thang = "0" + (i1 + 1);
                        }else {
                            thang = String.valueOf((i1 + 1));
                        }
                        edToDate.setText(i + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnGetDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = edFromDate.getText().toString();
                String toDate = edToDate.getText().toString();
                DaoPhieuMuon phieuMuonDAO = new DaoPhieuMuon(getContext());
                int doanhThu = phieuMuonDAO.getDoanhThu(fromDate, toDate);
                tvDoanhThu.setText(String.valueOf(doanhThu));
            }
        });

        return view;
    }
}