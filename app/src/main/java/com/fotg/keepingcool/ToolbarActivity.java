package com.fotg.keepingcool;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_messenger) {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
            try {
                startActivity(intent);
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.action_logout) {
            Authentication.requestLogout(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
