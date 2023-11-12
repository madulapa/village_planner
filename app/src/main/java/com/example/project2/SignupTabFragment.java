package com.example.project2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SignupTabFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 1; //for getting image from gallery
    EditText email;
    EditText phone;
    EditText pass;
    EditText confirmPass;
    TextView error;
    Button signUp;
    EditText profilePic;
    EditText name;
    float v = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);
        pass = root.findViewById(R.id.password);
        confirmPass = root.findViewById(R.id.confirm_password);
        signUp = root.findViewById(R.id.signup_btn);
        error = root.findViewById(R.id.error);
        name = root.findViewById(R.id.name);
        profilePic = root.findViewById(R.id.profile_pic);

        email.setAlpha(v);
        phone.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signUp.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        signUp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //TODO: let user upload their profile picture
                Log.i("SignupTabFragment.java", "pfp clicked");
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String nameStr = name.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passStr = pass.getText().toString().trim();
                String confirmStr = confirmPass.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String pfpStr = profilePic.getText().toString().trim();

                Log.d("a","Signing up");
                //if signup success, go to home
                signUp(view,nameStr,emailStr,passStr,confirmStr,phoneStr,pfpStr);
            }
        });
        return root;
    }
    public void handleAuth(View view){
        Intent intent = new Intent(this.getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    public boolean signUp(View view,String nameStr,String emailStr,String passStr,String confirmStr
            ,String phoneStr,String pfpStr){
        //database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //auth object
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(emailStr.isEmpty() || passStr.isEmpty()|| phoneStr.isEmpty() || nameStr.isEmpty()){
            error.setText("Fill out all fields!");
        }
        else if(!confirmStr.equals(passStr)){
            error.setText("Passwords not equal.");
        }
        else if(passStr.length()<6){
            error.setText("Password must be longer than 6 chars.");
        }
        else{
            Log.d("cat","Getting to creating user");
            mAuth.createUserWithEmailAndPassword(emailStr,passStr)
                    .addOnCompleteListener(requireActivity(),new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //if register success, add data to other parts of db
                                Log.d("cat","Register success!");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(Uri.parse(pfpStr)).build();
                                DatabaseReference userRef = db.getReference("users/"+user.getUid());
                                User userObj = new User(nameStr,emailStr,phoneStr,passStr);
                                userRef.setValue(userObj)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Log.d("user","user fields added to db!");
                                                }
                                                else{
                                                    Log.d("user","user fields FAILED");
                                                }
                                            }
                                        });
                                handleAuth(view);
                            }
                            else{
                                Log.d("a","Register failure.");
                            }
                        }
                    });
        }
        return false;
    }
}
