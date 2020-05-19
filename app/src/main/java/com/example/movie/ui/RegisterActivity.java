package com.example.movie.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.R;
import com.example.movie.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @NotEmpty
    @Email
    @BindView(R.id.edtRegisterEmail)
    EditText edtRegisterEmail;

    @NotEmpty
    @Email
    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    @BindView(R.id.edtRegisterPassword)
    EditText edtRegisterPassword;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.createUserWithEmailAndPassword(edtRegisterEmail.getText().toString(), edtRegisterPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    User user = new User(edtFullName.getText().toString(), edtRegisterEmail.getText().toString());
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(mFirebaseAuth.getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
}
