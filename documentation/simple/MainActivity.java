package facebooklogindemo.subbupampana.com.facebooklogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private LoginButton fbDefaultButton;
    private Button fbLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
    }
}
