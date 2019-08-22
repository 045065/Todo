package com.example.todo.login;

import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todo.application.TodoApplication;
import com.example.todo.util.SPrefUtils;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Charsets;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

import static com.example.todo.util.SPrefUtils.LOGIN_MAIL;
import static com.example.todo.util.SPrefUtils.LOGIN_PASS;

/**
 * ログイン用Presenter
 */
public class LoginPresenter implements LoginContract.Presenter {
    private static final String LOG_TAG = "LoginPresenter";

    private static final String KEY_ALGORITHM = "RSA";
    private static final String KEY_PROVIDER = "AndroidKeyStore";
    private static final String KEY_ALIAS = "TodoAppKeyAlias";
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    private final LoginContract.View mLoginView;
    private FirebaseAuth mAuth;
    private KeyStore mKeyStore = null;

    public LoginPresenter(@NonNull final LoginContract.View loginView) {
        mLoginView = loginView;

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
        mAuth = FirebaseAuth.getInstance();
        loadKeyStore();
        createNewKey(mKeyStore, KEY_ALIAS);
        Log.d(LOG_TAG, "login pass:" + SPrefUtils.getSPref().getString(LOGIN_PASS, null));

        String password = SPrefUtils.getSPref().getString(LOGIN_PASS, null);
        if (StringUtils.isNotEmpty(password)) {
            password = decryptString(mKeyStore, KEY_ALIAS, password);
        }
        mLoginView.showSavedAccount(SPrefUtils.getSPref().getString(LOGIN_MAIL, null), password);
    }

    @Override
    public void loginAccount(final String mail, final String password) {
        Log.d(LOG_TAG, "login mail:" + mail);

        if (!checkEmptyMailAndPassword(mail, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(task -> loginAndRegisterComplete(mail, password, task));
    }

    @Override
    public void registerAccount(final String mail, final String password) {
        if (!checkEmptyMailAndPassword(mail, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(task -> loginAndRegisterComplete(mail, password, task));
    }

    /**
     * ログイン、新規登録完了時の処理
     *
     * @param mail
     * @param password
     * @param task
     */
    private void loginAndRegisterComplete(final String mail, final String password, final Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.d(LOG_TAG, "signInWithEmail: success");
            mLoginView.showTodoListUi();
            SPrefUtils.getEditor().putString(LOGIN_MAIL, mail).apply();
            SPrefUtils.getEditor().putString(LOGIN_PASS, encryptString(mKeyStore, KEY_ALIAS, password)).apply();
        } else {
            Log.w(LOG_TAG, "signInWithEmail: failure", task.getException());
            mLoginView.showLoginAndRegisterFailed();
        }
    }

    /**
     * メールアドレス、パスワードの入力チェック
     *
     * @param mail
     * @param password
     * @return
     */
    private boolean checkEmptyMailAndPassword(final String mail, final String password) {
        if (TextUtils.isEmpty(mail)) {
            mLoginView.showEmptyMail();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            mLoginView.showEmptyPassword();
            return false;
        }
        return true;
    }

    /**
     * KeyStoreの読込
     */
    private void loadKeyStore() {
        try {
            mKeyStore = KeyStore.getInstance(KEY_PROVIDER);
            mKeyStore.load(null);
            Log.d(LOG_TAG, "success create keystore");
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    /**
     * Keyが無い際に新規作成
     *
     * @param keyStore
     * @param alias
     */
    private void createNewKey(final KeyStore keyStore, final String alias) {
        try {
            Log.d(LOG_TAG, "alias:" + alias);
            if (!keyStore.containsAlias(alias)) {
                Calendar start = new GregorianCalendar();
                Calendar end = new GregorianCalendar();
                end.add(Calendar.YEAR, 1);

                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, KEY_PROVIDER);

                AlgorithmParameterSpec spec;

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    spec = new KeyPairGeneratorSpec.Builder(TodoApplication.getContext())
                            .setAlias(alias)
                            .setSubject(new X500Principal("CN=" + alias))
                            .setSerialNumber(BigInteger.ONE)
                            .setStartDate(start.getTime())
                            .setEndDate(end.getTime())
                            .build();
                } else {
                    spec = new KeyGenParameterSpec.Builder(
                            alias,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setCertificateSubject(new X500Principal("CN=" + alias))
                            .setCertificateSerialNumber(BigInteger.ONE)
                            .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                            .build();
                }
                keyPairGenerator.initialize(spec);
                keyPairGenerator.generateKeyPair();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    /**
     * 暗号化処理
     *
     * @param keyStore
     * @param alias
     * @param plainText
     * @return
     */
    private String encryptString(final KeyStore keyStore, final String alias, final String plainText) {
        String encryptedText = null;
        try {
            PublicKey publicKey = (PublicKey) keyStore.getCertificate(alias).getPublicKey();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(plainText.getBytes(Charsets.UTF_8));
            encryptedText = Base64.encodeToString(bytes, Base64.DEFAULT);
            Log.d(LOG_TAG, "encryptedText: " + encryptedText);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return encryptedText;
    }

    /**
     * 複合化処理
     *
     * @param keyStore
     * @param alias
     * @param encryptedText
     * @return
     */
    private String decryptString(final KeyStore keyStore, final String alias, final String encryptedText) {
        String decryptText = null;
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptText = new String(cipher.doFinal(Base64.decode(encryptedText, Base64.DEFAULT)), StandardCharsets.UTF_8);
            Log.d(LOG_TAG, "decryptText: " + decryptText);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return decryptText;
    }
}
