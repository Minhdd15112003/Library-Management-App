package ph25260.fpoly.asm.ui.AuthScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import ph25260.fpoly.asm.R;
import ph25260.fpoly.asm.dao.DaoLogin;
import ph25260.fpoly.asm.model.User;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imageView3;
    private TextInputEditText edREmail;
    private TextInputEditText edRUsername;
    private TextInputEditText edRPassword;
    private TextInputEditText edRNumberPhone;
    private CheckBox chkRememberPass;
    private Button btnRSignup;
    private Button btnCancel;
    private TextInputEditText edRPasswordCheck;

    DaoLogin daoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageView3 = findViewById(R.id.imageView3);
        edREmail = findViewById(R.id.edREmail);
        edRUsername = findViewById(R.id.edRUsername);
        edRPassword = findViewById(R.id.edRPassword);
        edRNumberPhone = findViewById(R.id.edRNumberPhone);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        edRPasswordCheck = findViewById(R.id.edRPasswordCheck);
        btnRSignup = findViewById(R.id.btnRSignup);
        btnCancel = findViewById(R.id.btnCancel);
        daoLogin = new DaoLogin(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnRSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edREmail.getText().toString().trim();
                String username = edRUsername.getText().toString().trim();
                String pass = edRPassword.getText().toString().trim();
                String numberphone = edRNumberPhone.getText().toString().trim();
                String passCheck = edRPasswordCheck.getText().toString().trim();
                if (email.isEmpty() && pass.isEmpty() && username.isEmpty() && numberphone.isEmpty() && passCheck.isEmpty() && pass.equals(passCheck)) {
                    edREmail.setError("Email không được để trống");
                    edRUsername.setError("Username không được để trống");
                    edRPassword.setError("Password không được để trống");
                    edRNumberPhone.setError("Số điện thoại không được để trống");
                    edRPasswordCheck.setError("Password không được để trống");
                    if (pass.equals(passCheck)) {
                        edRPasswordCheck.setError("Mật khẩu không trùng khớp");
                    }
                } else {

                    long dangki = daoLogin.addUser(email, username, pass, numberphone);
                    if (dangki != -1) {
                        edREmail.setText("");
                        edRUsername.setText("");
                        edRPassword.setText("");
                        edRNumberPhone.setText("");
                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
              startActivity(intent);
            }
        });
    }
}