package com.example.todo.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo.R;
import com.example.todo.todo.TodoListActivity;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ログイン用Fragment
 */
public class LoginFragment extends Fragment implements LoginContract.View {
    @BindView(R.id.email_log_in_edit_text)
    EditText mMailEditText;

    @BindView(R.id.password_log_in_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.register_button)
    Button mRegisterButton;

    private LoginContract.Presenter mPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull final LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.login_button)
    void onClickLogin(final View view) {
        mPresenter.loginAccount(mMailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.register_button)
    void onClickRegister(final View view) {
        mPresenter.registerAccount(mMailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void showSavedAccount(final String mail, final String password) {
        mMailEditText.setText(mail);
        mPasswordEditText.setText(password);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showLoginAndRegisterFailed() {
        Snackbar.make(getView(), getContext().getString(R.string.login_register_faild), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyMail() {
        Snackbar.make(getView(), getContext().getString(R.string.empty_mail), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyPassword() {
        Snackbar.make(getView(), getContext().getString(R.string.empty_password), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTodoListUi() {
        Intent intent = new Intent(getContext(), TodoListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
