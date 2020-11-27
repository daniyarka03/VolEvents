package com.example.volunteerproject;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    /**Declaring variables */



    private long backPressedTime;
    private Toast backToast;
    Animation topAnim, bottomAnim;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        bottomNavigationView.setAnimation(bottomAnim);

}
    @Override
        public void onBackPressed () {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                /** When you press the back button 2 times, the device exits the application */

                backToast.cancel();
                super.onBackPressed();
                moveTaskToBack(true);
                System.runFinalizersOnExit(true);
                System.exit(0);
                return;
            } else {
                /** If the user pressed the back button 1 time, the message "Press again to exit!" */

                backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти!", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();



        }
    }
