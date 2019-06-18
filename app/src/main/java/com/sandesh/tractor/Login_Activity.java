package com.sandesh.tractor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login_Activity extends AppCompatActivity {

    private AppCompatTextView signin, signup, signin_signup_txt, forgot_password;
    private CircleImageView circleImageView;
    private AppCompatButton signin_signup_btn;
    private LinearLayoutCompat linearLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        signin_signup_txt = findViewById(R.id.signin_signup_txt);
        forgot_password = findViewById(R.id.forgot_password);
        circleImageView = findViewById(R.id.circleImageView);
        signin_signup_btn = findViewById(R.id.signin_signup_btn);
        signin_signup_btn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        linearLayout3 = findViewById(R.id.linearLayout3);
        signin.setOnClickListener(view -> {
            signin.setTextColor(Color.parseColor("#FFFFFF"));
            signin.setBackgroundColor(Color.parseColor("#D81B60"));
            signup.setTextColor(Color.parseColor("#FF2729C3"));
            signup.setBackgroundResource(R.drawable.bordershape);
            circleImageView.setImageResource(R.drawable.sigin_boy_img);
            signin_signup_txt.setText(getString(R.string.sign_in));
            signin_signup_btn.setText(getString(R.string.sign_in));
            forgot_password.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.GONE);

        });
        signup.setOnClickListener(view -> {
            signup.setTextColor(Color.parseColor("#FFFFFF"));
            signup.setBackgroundColor(Color.parseColor("#FF2729C3"));
            signin.setTextColor(Color.parseColor("#FF2729C3"));
            signin.setBackgroundResource(R.drawable.bordershape);
            circleImageView.setImageResource(R.drawable.sigup_boy_img);
            signin_signup_txt.setText(getString(R.string.sign_up));
            signin_signup_btn.setText(getString(R.string.sign_up));
            forgot_password.setVisibility(View.INVISIBLE);
            linearLayout3.setVisibility(View.VISIBLE);

        });
    }
}

