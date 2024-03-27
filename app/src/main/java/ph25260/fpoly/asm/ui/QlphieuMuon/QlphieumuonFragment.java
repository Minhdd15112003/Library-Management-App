package ph25260.fpoly.asm.ui.QlphieuMuon;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ph25260.fpoly.asm.R;

public class QlphieumuonFragment extends Fragment {



    public static QlphieumuonFragment newInstance() {
        return new QlphieumuonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlphieumuon, container, false);
    }



}