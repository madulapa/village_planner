package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginTabFragment extends Fragment {
    EditText email;
    EditText pass;
    Button login;
    TextView error;
    float v = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.password);
        login = root.findViewById(R.id.login_btn);
        error = root.findViewById(R.id.error);

        email.setAlpha(v);
        pass.setAlpha(v);
//        login.setAlpha(v);
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailStr = email.getText().toString().trim();
                String passStr = pass.getText().toString().trim();
                Log.d("a","Logging in");
                //if signup success, go to home
                signIn(view,emailStr,passStr);
            }
        });
        return root;
    }

    public void handleAuth(View view){
        Intent intent = new Intent(this.getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    public void signIn(View view,String emailStr,String passStr) {
        //database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //auth object
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(emailStr==null || emailStr.isEmpty() || passStr==null || passStr.isEmpty()){
            error.setText("Fill out all fields!");
        }
        else{
            mAuth.signInWithEmailAndPassword(emailStr,passStr)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("login","Login success!");
                                handleAuth(view);
                            }
                            else{
                                Log.d("login","Login failure");
                                error.setText("Login failed - make sure your credentials are correct," +
                                        "or create an account.");
                            }
                        }
                    });
        }
    }
}
