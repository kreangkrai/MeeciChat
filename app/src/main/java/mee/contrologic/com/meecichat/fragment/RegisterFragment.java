package mee.contrologic.com.meecichat.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

import mee.contrologic.com.meecichat.MainActivity;
import mee.contrologic.com.meecichat.R;
import mee.contrologic.com.meecichat.utility.MyAlert;
import mee.contrologic.com.meecichat.utility.RegisterModel;

/**
 * Created by SAMSUNG on 27/1/2561.
 */

public class RegisterFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Toolbar Controller

        toolbarController();

    }// Main Method

    private void toolbarController() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
//        setup title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register_th));

//        setup Navigator
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

//        setup menu
        setHasOptionsMenu(true);

    } //Toolbar

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.itemRegister)
        {
            uploadValuetoFirebase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadValuetoFirebase() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait few minute...");
        progressDialog.setMessage("Try to save your value to firebase");
        progressDialog.show();

//        Initial View
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        final String nameString = nameEditText.getText().toString().trim();
        String emailString = emailEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();

        if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty())
        {
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Have Space","Please fill all every blank");
        }
        else
        {
//            no space
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(emailString,passwordString)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getActivity(),"Register Success",Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();

                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String userUIDstring = firebaseUser.getUid();
                                Log.d("28JanV1","userUID==> "+userUIDstring);

//                                Setup Model
                                final RegisterModel registerModel = new RegisterModel(userUIDstring,nameString);
//                                setup display
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                                        .Builder().setDisplayName(nameString).build();
                                firebaseUser.updateProfile(userProfileChangeRequest)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        DatabaseReference databaseReference = FirebaseDatabase
                                                .getInstance()
                                                .getReference()
                                                .child("DetailUser");

                                        databaseReference.child(nameString).setValue(registerModel);
                                    } //onSuccess
                                });
//                                Back to  main Fragment
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                            else
                            {
                                MyAlert myAlert = new MyAlert(getActivity());
                                myAlert.normalDialog("Cannot Register",task.getException().getMessage());
                            }

                        } //onCompleted
                    });

        } //if
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        return  view;
    }
}// Main Class
