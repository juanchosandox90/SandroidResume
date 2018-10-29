package jcsandoval.sandroidresume.com.sandroidresume.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jcsandoval.sandroidresume.com.sandroidresume.R;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.buttonLogOut)
    Button buttonLogut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        //Do something
    }

    @OnClick(R.id.buttonLogOut)
    public void logout() {
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
