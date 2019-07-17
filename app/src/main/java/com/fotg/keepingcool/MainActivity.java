package com.fotg.keepingcool;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.auth.IdpResponse;
import com.fotg.keepingcool.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 1;
    ImageView backgroundImage, fishImage;
    TextView exploreText;
    LinearLayout textSplash, emailLayout;
    Animation frombottom, emailAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        emailAnim = AnimationUtils.loadAnimation(this, R.anim.email_anim);

        backgroundImage = findViewById(R.id.backgroundImage);
        fishImage = findViewById(R.id.fishImage);
        textSplash = (LinearLayout) findViewById(R.id.textSplash);
        exploreText = findViewById(R.id.exploreText);
        emailLayout = (LinearLayout) findViewById(R.id.emailLayout);

        backgroundImage.animate().translationY(-1830).setDuration(800).setStartDelay(800);
        fishImage.animate().translationX(1330).setDuration(800).setStartDelay(500);
        textSplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);
        exploreText.startAnimation(frombottom);
        emailLayout.startAnimation(emailAnim);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });

}

    private void requestLogin() {
        startActivityForResult(Authentication.requestLoginIntent(), RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                User userDB = new User(user.getDisplayName());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference userRef = database.getReference("/users");

                Map<String, Object> userMap = new HashMap<>();
                userMap.put(user.getUid(), userDB);

                userRef.updateChildren(userMap);

                Intent intent = new Intent(this, ListPostsActivity.class);
                startActivity(intent);
            }
        }
    }
}