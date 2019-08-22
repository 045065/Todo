package com.example.todo.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {
    @Mock
    private LoginContract.View mLoginView;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setupLoginPresenter() {
        MockitoAnnotations.initMocks(this);

        mLoginPresenter = givenTasksPresenter();

        when(mLoginView.isActive()).thenReturn(true);
    }

    private LoginPresenter givenTasksPresenter() {
        return new LoginPresenter(mLoginView);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        mLoginPresenter = givenTasksPresenter();

        verify(mLoginView).setPresenter(mLoginPresenter);
    }
}