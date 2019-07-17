package com.fotg.keepingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fotg.keepingcool.models.Profile;
import com.fotg.keepingcool.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = db.getReference("/users");
        final DatabaseReference profileRef = db.getReference("/profile");

        EditText firstName = findViewById(R.id.editFirstName);
        EditText lastName = findViewById(R.id.editLastName);
        EditText displayName = findViewById(R.id.editDisplayName);
        EditText aboutMe = findViewById(R.id.editAboutMe);

        Button saveButton = findViewById(R.id.saveProfile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news_feed:
                        Intent feed_intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                        startActivity(feed_intent);
                        break;
//                    case R.id.useful_links:
//                        Intent links_intent = new Intent(getApplicationContext(), UsefulLinksActivity.class);
//                        startActivity(links_intent);
//                        break;
                    case R.id.tips:
                        Intent tips_intent = new Intent(getApplicationContext(), DavidsTipsActivity.class);
                        startActivity(tips_intent);
                        break;
                    case R.id.bindr:
                        Intent bindr_intent = new Intent(getApplicationContext(), BindrActivity.class);
                        startActivity(bindr_intent);
                        break;
                }
                return true;
            }
        });

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user;
                String name;
                String fName;
                String lName;
                String aMe;

                user = dataSnapshot.child(uid).getValue(User.class);

                name = user.getName();

                displayName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile;
                String fName;
                String lName;
                String aMe;

                profile = dataSnapshot.child(uid).getValue(Profile.class);

                if (profile != null) {
                    fName = dataSnapshot.child(uid).child("First Name").getValue().toString();
                    lName = dataSnapshot.child(uid).child("Last Name").getValue().toString();
                    aMe = dataSnapshot.child(uid).child("About Me").getValue().toString();

                    firstName.setText(fName);
                    lastName.setText(lName);
                    aboutMe.setText(aMe);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileRef.child(uid).child("name").setValue(displayName.getText().toString());
                profileRef.child(uid).child("First Name").setValue(firstName.getText().toString());
                profileRef.child(uid).child("Last Name").setValue(lastName.getText().toString());
                profileRef.child(uid).child("About Me").setValue(aboutMe.getText().toString());
            }
        });

    }
}
