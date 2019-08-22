package com.example.todo.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todo.R;
import com.example.todo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ログイン用Activity
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        mLoginPresenter = new LoginPresenter(loginFragment);
    }
}