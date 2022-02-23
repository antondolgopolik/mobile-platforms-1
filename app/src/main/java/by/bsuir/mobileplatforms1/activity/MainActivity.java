package by.bsuir.mobileplatforms1.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;

import by.bsuir.mobileplatforms1.ApplicationState;
import by.bsuir.mobileplatforms1.R;
import by.bsuir.mobileplatforms1.datasource.dao.UserDao;
import by.bsuir.mobileplatforms1.service.UserService;


public class MainActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST_CODE = 1;

    private AppBarConfiguration appBarConfiguration;

    private SignInClient signInClient;
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Потребовать логин
        login();
        // Настройка навигации
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void login() {
        signInClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.google_server_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();
        signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(
                                result.getPendingIntent().getIntentSender(), LOGIN_REQUEST_CODE,
                                null, 0, 0, 0
                        );
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        finishAndRemoveTask();
                    }
                })
                .addOnFailureListener(this, e -> {
                    e.printStackTrace();
                    finishAndRemoveTask();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            try {
                SignInCredential credential = signInClient.getSignInCredentialFromIntent(data);
                String username = credential.getId();
                // Создать пользователя
                UserService userService = new UserService(this);
                userService.createUser(username);
                // Сохранить username
                ApplicationState.setUsername(username);
            } catch (ApiException e) {
                e.printStackTrace();
                finishAndRemoveTask();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about_us) {
            startActivity(new Intent(this, AboutUsActivity.class));
            return true;
        }
        if (itemId == R.id.action_about_app) {
            startActivity(new Intent(this, AboutAppActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}