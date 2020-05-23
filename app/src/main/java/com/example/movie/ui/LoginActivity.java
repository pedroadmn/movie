package com.example.movie.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.R;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.saripaar_required)
    @Email(messageResId = R.string.valid_email_required)
    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, messageResId = R.string.valid_password_required)
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.imgGoogle)
    ImageView imgGoogle;

    @BindView(R.id.imgFacebook)
    ImageView imgFacebook;

    @BindView(R.id.imgTwitter)
    ImageView imgTwitter;

    @BindView(R.id.txtForgotPassword)
    TextView txtForgotPassword;

    @BindView(R.id.txtRegister)
    TextView txtRegister;

    @BindView(R.id.loginProgressBar)
    ProgressBar loginProgressBar;

    private Validator validator;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> validator.validate());

        txtRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        txtForgotPassword.setOnClickListener(v -> {
            EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle(getString(R.string.reset_password));
            passwordResetDialog.setMessage(getString(R.string.reset_password_link_message));
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                mFirebaseAuth.sendPasswordResetEmail(resetMail.getText().toString().trim())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(LoginActivity.this, getString(R.string.link_sent_message), Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(LoginActivity.this, getString(R.string.link_not_sent_message) + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            });

            passwordResetDialog.setNegativeButton(getString(R.string.no), (dialog, which) -> {

            });

            passwordResetDialog.create().show();
        });
    }

    @Override
    public void onValidationSucceeded() {
        loginProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.sucess_login, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                    }
                    loginProgressBar.setVisibility(View.INVISIBLE);
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
