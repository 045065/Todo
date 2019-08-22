package com.example.todo.login;

import com.example.todo.BaseView;
import com.example.todo.BasePresenter;

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        /**
         * ログイン・新規登録失敗時に表示
         */
        void showLoginAndRegisterFailed();

        /**
         * メールアドレスが空の時に表示
         */
        void showEmptyMail();

        /**
         * パスワードが空の時に表示
         */
        void showEmptyPassword();

        /**
         * Todo一覧画面を表示
         */
        void showTodoListUi();

        /**
         * 保存されたアカウント情報を表示
         *
         * @param string
         * @param decryptString
         */
        void showSavedAccount(String string, String decryptString);

        /**
         * Fragmentをアタッチ完了したかどうか
         */
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        /**
         * ログイン
         *
         * @param mail
         * @param password
         */
        void loginAccount(String mail, String password);

        /**
         * 新規登録
         *
         * @param mail
         * @param password
         */
        void registerAccount(String mail, String password);
    }
}
