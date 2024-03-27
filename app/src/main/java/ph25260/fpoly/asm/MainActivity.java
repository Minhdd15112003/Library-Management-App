package ph25260.fpoly.asm;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import ph25260.fpoly.asm.databinding.ActivityMainBinding;
import ph25260.fpoly.asm.ui.DoanhThu.DoanhThuFragment;
import ph25260.fpoly.asm.ui.QLloaiSach.QLloaiSachFragment;
import ph25260.fpoly.asm.ui.QLsach.QlsachFragment;
import ph25260.fpoly.asm.ui.QLthanhVien.QLthanhVienFragment;
import ph25260.fpoly.asm.ui.QlphieuMuon.QlphieumuonFragment;
import ph25260.fpoly.asm.ui.settings.SettingsFragment;
import ph25260.fpoly.asm.ui.topSach.TopSachFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.appBarMain.toolbar;
        frameLayout = binding.appBarMain.fragmentContainer;

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QlphieumuonFragment()).commit();
            toolbar.setTitle("Quản lý phiếu mượn");
            navigationView.setCheckedItem(R.id.nav_qlphieu);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        // Xử lý sự kiện khi người dùng chọn các mục trong Navigation Drawer
        if (itemId == R.id.nav_qlphieu) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QlphieumuonFragment()).commit();
            toolbar.setTitle("Quản lý phiếu mượn");

        } else if (itemId == R.id.nav_qlLoaiSach) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QLloaiSachFragment()).commit();
            toolbar.setTitle("Quản lý loại sách");

        } else if (itemId == R.id.nav_sach) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QlsachFragment()).commit();
            toolbar.setTitle("Quản lý sách");

        } else if (itemId == R.id.nav_qlThanhVien) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QLthanhVienFragment()).commit();
            toolbar.setTitle("Quản lý thành viên");

        } else if (itemId == R.id.nav_topSach) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TopSachFragment()).commit();
            toolbar.setTitle("Top sách");

        } else if (itemId == R.id.nav_doanhthu) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DoanhThuFragment()).commit();
            toolbar.setTitle("Doanh thu");

        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment()).commit();
            toolbar.setTitle("Settings");

        } else if (itemId == R.id.nav_dangxuat) {
            // Khi người dùng chọn mục Logout, đóng ứng dụng bằng finishAffinity()
            finishAffinity();
            //   startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Toast.makeText(this, "đã thoát", Toast.LENGTH_SHORT).show();
        }
        // Sau khi xử lý sự kiện, đóng Navigation Drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Nếu Navigation Drawer đang mở, đóng nó khi người dùng bấm nút Back

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Nếu không, thực hiện hành động mặc định của nút Back (thoát hoặc quay lại)
            super.onBackPressed();
        }
    }
}