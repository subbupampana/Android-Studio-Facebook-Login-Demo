package com.subbupampana.facebooklogindemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import android.util.Base64;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager; //CallbackManager object
    private LoginButton fbLoginButton; // LoginButton button object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Facebook Login section starts*/
        //In activity_main.xml we added a facebook button.In this line R.id.login_button points to  facebook button
        //Now  we are assigning that Button UI to the fbLoginButton Button object
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        //setReadPermissions() will takes permission to fetch email id of the user
        fbLoginButton.setReadPermissions(Arrays.asList("email"));
        //Call Back Manager   handles the  login responses
        callbackManager = CallbackManager.Factory.create();

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Facebook Login Success.Do what ever you want
                Toast.makeText(MainActivity.this, "Facebook Login Success",
                        Toast.LENGTH_LONG).show(); //Success Toast
                //You can send this Accees Token to server
                String accessToken = loginResult.getAccessToken().getToken();
                Log.w("Facebook Access Token",accessToken.toString());

            }
            @Override
            public void onCancel() {
                //User cancled the Facebook Login
                Toast.makeText(MainActivity.this, "Facebook Login Canceled",
                        Toast.LENGTH_LONG).show();//Cancel Toast
            }
            @Override
            public void onError(FacebookException exception) {
                //Facebook Login Failed
                Log.w("Facebook LOgin Failed",exception.toString());//Error message will be printed on console
                Toast.makeText(MainActivity.this, "Facebook Login Failed",
                        Toast.LENGTH_LONG).show(); //Error Toast
            }
        });
        /*Facebook Login section ends*/
        this.printHashKey(this); //Call this metod to print Key Hashes on console.Paste the keyhash  facebook developers website
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //This method will listen to Activity Result of  facebook callbackManager.
        // It pass the login results to the LoginManager via callbackManager.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static void printHashKey(Context pContext) {
        //This methos will print the Key Hashes
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Printed Hash Key", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("NoSuchAlgorith", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Failed To Print", "printHashKey()", e);
        }
    }

    private void fbAccountSignOut() {
        //Call this method to facebook logout
        AccessToken token;
        token = AccessToken.getCurrentAccessToken();
        //First,It will check for Access Token.If it is not null then LoginManager will do logout
        if (token != null) {
            /*Means user is not logged in*/
            LoginManager.getInstance().logOut();
        }
    }

    private boolean getLoginStatus(){
        //This method will return the login status
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }
}
