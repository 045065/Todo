package com.example.todo.todo;

import com.example.todo.BasePresenter;
import com.example.todo.BaseView;
import com.example.todo.data.TodoData;

public interface TodoListContract {
    interface View extends BaseView<Presenter> {
        /**
         * Todoを追加
         * @param toDoData
         */
        void addTodo(TodoData toDoData);

        /**
         * Todoを削除
         * @param firebaseKey
         */
        void removeTodo(String firebaseKey);

        /**
         * Todoを編集
         * @param toDoData
         */
        void changeTodo(TodoData toDoData);

        /**
         * ログイン画面表示
         */
        void showLoginUi();

        /**
         * Fragmentをアタッチ完了したかどうか
         */
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        /**
         * ログアウト
         */
        void logoutAccount();

        /**
         * Todo完了
         * @param todoData
         */
        void completeTodo(TodoData todoData);

        /**
         * FirebaseDatabaseの設定
         */
        void initFirebaseDB();
    }
}
