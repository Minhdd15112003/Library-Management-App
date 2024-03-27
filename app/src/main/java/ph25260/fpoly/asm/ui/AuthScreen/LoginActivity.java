package ph25260.fpoly.asm.ui.AuthScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ph25260.fpoly.asm.MainActivity;
import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.model.User;


public class LoginActivity extends AppCompatActivity {


    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin , btnCancel;
    private TextView tvRegister;

    DaoLogin daoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        btnCancel = findViewById(R.id.btnCancel);
        daoLogin = new DaoLogin(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (email.isEmpty() && pass.isEmpty()) {
                    edtEmail.setError("Email không được để trống");
                    edtPass.setError("Mật khẩu không được để trống");
                } else {
                    User user = daoLogin.checkLogin(email, pass);
                    if (user != null && user.getEmail().equals(email)  && user.getPassword().equals(pass)) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }


    private void Register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}