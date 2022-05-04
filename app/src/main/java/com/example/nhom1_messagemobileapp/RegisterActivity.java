package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button btnToLoginPage,btnRegister;
    EditText edtName, edtEmail, edtPassword, edtRePassword;
    private Context context;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();

        btnToLoginPage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = onClickRegister();
            }
        });


    }
    private void initUi() {
        context = this;

        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.regis_edtUserHandleName);
        edtEmail = findViewById(R.id.regis_edtEmailInput);
        edtPassword = findViewById(R.id.regis_edtPassInput);
        edtRePassword = findViewById(R.id.regis_edtConfirmPassInput);

        btnToLoginPage = findViewById(R.id.btn_to_login_page);
        btnRegister = findViewById(R.id.btn_register);
    }
    private boolean onClickRegister() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String rePassword = edtRePassword.getText().toString().trim();
        if (name.isEmpty() || name.length() <= 0) {
            Toast.makeText(context, "Name không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if (email.isEmpty() || email.length() <= 0) {
            Toast.makeText(context, "Email không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty() || password.length() <= 0) {
            Toast.makeText(context, "Password không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if (rePassword.isEmpty() || rePassword.length() <= 0) {
            Toast.makeText(context, "Password nhập lại không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(context, "Password không giống nhau", Toast.LENGTH_LONG).show();
            return false;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String uid = mAuth.getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("user");
                            User user = new User(name, email, password);
                            myRef.child(uid).setValue(user);
                            Toast.makeText(context, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Đăng ký tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return true;
    }
}