package com.example.todo.save;

import com.example.todo.BasePresenter;
import com.example.todo.BaseView;

public interface SaveContract {
    interface View extends BaseView<SaveContract.Presenter> {
        /**
         * Todo一覧画面表示
         */
        void showTodoListUi();

        /**
         * Todoタイトル設置
         */
        void setTitle(String title);

        /**
         * Todo内容設置
         */
        void setContent(String content);

        /**
         * Fragmentをアタッチ完了したかどうか
         */
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        /**
         * Todoを保存
         * @param title
         * @param content
         */
        void saveTodo(String title, String content);
    }
}
