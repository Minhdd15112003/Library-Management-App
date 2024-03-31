package ph25260.fpoly.asm.ui.topSach;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.adapter.TopAdapter;
import ph25260.fpoly.asm.dao.DaoPhieuMuon;
import ph25260.fpoly.asm.dao.DaoSach;
import ph25260.fpoly.asm.model.Top;

public class TopSachFragment extends Fragment {

    private RecyclerView recyclerViewTopSach;
    ArrayList<Top> list;
    TopAdapter topAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_sach, container, false);
        recyclerViewTopSach = view.findViewById(R.id.recyclerViewTopSach);
        DaoPhieuMuon phieuMuonDAO = new DaoPhieuMuon(getContext());
        list = (ArrayList<Top>) phieuMuonDAO.getTop10();
        topAdapter = new TopAdapter(list, getContext());
        recyclerViewTopSach.setAdapter(topAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTopSach.setLayoutManager(layoutManager);

        return view;
    }
}
