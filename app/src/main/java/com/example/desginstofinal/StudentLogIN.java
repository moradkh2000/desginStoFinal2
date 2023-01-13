package com.example.desginstofinal;

import static com.example.desginstofinal.FBref.refAuth;
import static com.example.desginstofinal.FBref.refstudent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class StudentLogIN extends AppCompatActivity {
    TextView tVtitle, tVregister;
    Intent t;
    EditText eTemail, eTpass,eTphone,eTid,eTname;
    Button btn,btnFP;
    LinearLayout LL;
    Boolean stayConnect, registered, firstrun,inorder=false;
    CheckBox cBstayconnect;
    String id,name, phone, email, password, uid;
    Student student;

    Bundle bundle;
    boolean isUser=false;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log_in);
        tVtitle=(TextView)findViewById(R.id.tVtitle);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        eTphone=(EditText)findViewById(R.id.eTphone);
        eTname=(EditText)findViewById(R.id.eTname);
        btn = (Button) findViewById(R.id.btn);
        eTid=(EditText) findViewById(R.id.eTid);
        LL = findViewById(R.id.LL);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btnFP=(Button) findViewById(R.id.btnFG);

        intent=getIntent();
        bundle=intent.getExtras();
        if(bundle!=null){

            isUser=(boolean) bundle.getBoolean("user");
            if(isUser){
                tVtitle.setText("Login For User");
                btnFP.setVisibility(View.GONE);
            }

          //  Toast.makeText(getApplicationContext(),String.valueOf(isUser),Toast.LENGTH_SHORT).show();
        }
//        try {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        } catch (Exception e) {
//
//            Log.w("error",e.getMessage());
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//
//        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    logorreg();
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FGM(view);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //FBref.refAuth.


        stayConnect=false;
        registered=true;

        isStayConnected();
        if(isUser==false) {
            regoption();
        }


    }



    private  void isStayConnected(){


        try {
            SharedPreferences settings = isUser?getSharedPreferences("USER",MODE_PRIVATE): getSharedPreferences("PREFS_NAME", MODE_PRIVATE);

            boolean isStay =isUser?settings.getBoolean("stayuser",false):  settings.getBoolean("stayConnect", false);
            String email =isUser?settings.getString("emailuser", null): settings.getString("email", null);
            String password =isUser?settings.getString("passworduser", null): settings.getString("password", null);
           // Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

            if (isStay && isUser==false) {

             //   if(email.contains("user")==false) {
                    cBstayconnect.setChecked(true);
                    eTemail.setText(email);
                    eTpass.setText(password);
              //  }
//                else
//                {
//                    cBstayconnect.setChecked(false);
//                    eTemail.setText("");
//                    eTpass.setText("");
//                }
            }
            else
            {
                if(isStay){

                    if(email.equals("user") && password.equals("1234")){
                        cBstayconnect.setChecked(true);
                        eTemail.setText(email);
                        eTpass.setText(password);
                    }
                }
            }

        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());

            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart() {
        /**
         * Checks if the user already checked the "Stay Connected" button.
         * <p>
         *
         */
        super.onStart();
//        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
//        Boolean isChecked=settings.getBoolean("stayConnect",false);
//        Intent si = new Intent(StudentLogIN.this,StudentMain_Activity.class);
//        if (refAuth.getCurrentUser()!=null && isChecked) {
//            stayConnect=true;
//            startActivity(si);
//        }



    }

    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    private void regoption() {
        /**
         * Switches the screen from Login to Register.
         * <p>
         */
        isStayConnected();
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                eTphone.setVisibility(View.VISIBLE);
                eTid.setVisibility(View.VISIBLE);
                LL.setVisibility(View.VISIBLE);
                btnFP.setVisibility(View.INVISIBLE);
                btn.setText("Register");

                registered=false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        eTemail.setText("");
        eTpass.setText("");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                eTphone.setVisibility(View.INVISIBLE);
                eTid.setVisibility(View.INVISIBLE);
                LL.setVisibility(View.INVISIBLE);
                btnFP.setVisibility(View.VISIBLE);


                btn.setText("Login");
                registered=true;
                regoption();
            }
        };

        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }
     Student sstudent=null;



    public void logorreg() {


        if(isUser){


            email = eTemail.getText().toString();
            password = eTpass.getText().toString();

            if(email.isEmpty() || password.isEmpty()){

                Toast.makeText(getApplicationContext(),"row/(s) is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(email.equals("user") && password.equals("1234")){


                SharedPreferences settings = getSharedPreferences("USER", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("stayuser", cBstayconnect.isChecked());
                editor.putString("emailuser",email);
                editor.putString("passworduser",password);
                editor.commit();
                // }


//               Intent t=new Intent(StudentLogIN.this,addproducts.class);
//               // t.putExtra("user",true);
//                startActivity(t);
//                finish();
                return;
            }
            else {

                Toast.makeText(getApplicationContext(),"Your permission not exists",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        /**
         * Checks if the user is registered and logging in, else it will register.
         * <p>
         *
         * @param    view Button	on click operate the action.
         */


        if (registered) {
       //     Toast.makeText(getApplicationContext(),"d",Toast.LENGTH_SHORT).show();
            email = eTemail.getText().toString();
            password = eTpass.getText().toString();

            if(email.isEmpty() || password.isEmpty()){

                Toast.makeText(getApplicationContext(),"Email/Password is Empty!!",Toast.LENGTH_SHORT).show();
                return;
            }
            final ProgressDialog pd = ProgressDialog.show(this, "Login", "Connecting...", true);

            //refAuth.fetchSignInMethodsForEmail()
            refAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();

                           // Toast.makeText(getApplicationContext(),String.valueOf(task.isSuccessful()),Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {


                              //  Toast.makeText(getApplicationContext(),"dpme",Toast.LENGTH_SHORT).show();
                                firebase firebase;
                                firebase=new firebase() {

                                    @Override
                                    public void onstart(String email) {
                                     //   Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
                                        FBref.query= refstudent.orderByChild("email").equalTo(email);
                                        FBref.query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {



                                                for (DataSnapshot d : snapshot.getChildren()){

                                                    sstudent =(Student) d.getValue(Student.class);
                                                }
                                                String email= refAuth.getCurrentUser().getEmail();
                                                sstudent.setEmail(email);
                                                try {
                                                    Toast.makeText(getApplicationContext(), sstudent.getEmail(), Toast.LENGTH_SHORT).show();

                                                }
                                                catch (Exception ex){
                                                 //   Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                    if (sstudent != null) {

                                                       // Toast.makeText(getApplicationContext(),sstudent.getName(),Toast.LENGTH_SHORT).show();
                                                        oncomplete(sstudent);
                                                    } else
                                                    {
                                                        Toast.makeText(getApplicationContext(),"User Not found",Toast.LENGTH_SHORT).show();
                                                    }



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void oncomplete(Student ssx) {
                                        //Check if Stay Connected is checked
                                        //   if(cBstayconnect.isChecked()) {

                                        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                        editor.putString("email",email);
                                        editor.putString("password",password);
                                        editor.commit();
                                        // }
                                        Log.d("MainActivity", "signinUserWithEmail:success");
                                        Toast.makeText(StudentLogIN.this, "Login Success", Toast.LENGTH_LONG).show();
                                        Intent si =null;// new Intent(StudentLogIN.this, StudentMain_Activity.class);

                                    //    if(ss!=null)
                                        if(ssx!=null) {
                                         //   Toast.makeText(getApplicationContext(), ssx.getName(), Toast.LENGTH_SHORT).show();
                                        //    si.putExtra("student", ssx);

//                                            Bundle bundle = new Bundle();
//                                            bundle.putSerializable("student", ssx);
//                                            bundle.putString("Email","email");
//                                            si.putExtras(bundle);
//                                            startActivity(si);
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();

                                    }
                                };
                                firebase.onstart(email);






                            } else {
                                Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(StudentLogIN.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            if(e.getMessage()!=null){
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            name = eTname.getText().toString();
            phone = eTphone.getText().toString();
            email = eTemail.getText().toString();
            password = eTpass.getText().toString();
            id = eTid.getText().toString();

            StringBuilder builder=new StringBuilder();
            if(name.isEmpty())builder.append("IsEmpty");
            if(phone.isEmpty() && builder.toString().length()==0)builder.append("IsEmpty");
            if(email.isEmpty() && builder.toString().length()==0)builder.append("IsEmpty");
            if(password.isEmpty() && builder.toString().length()==0)builder.append("IsEmpty");
            if(id.isEmpty() && builder.toString().length()==0)builder.append("IsEmpty");
            if(builder.length()>0){
                builder.delete(0,builder.length());
                builder.append("row/(s) is empty");
                Toast.makeText(getApplicationContext(), builder.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            if(password.length()<6){
                builder.delete(0,builder.length());
                builder.append("the password must 6 character");
                Toast.makeText(getApplicationContext(), builder.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            student = new Student(name, phone, email, uid, id);
/*
            try {
                FirebaseDatabase FBDB = FirebaseDatabase.getInstance("https://alfaversion-3fc76-default-rtdb.firebaseio.com/");

              //  FBDB.setPersistenceEnabled(true);
                DatabaseReference refstudent = FBDB.getReference("students");

                //   Toast.makeText(getApplicationContext(),String.valueOf(refstudent.get().getResult().getChildrenCount()),Toast.LENGTH_SHORT).show();
              //  if(refstudent!=null)
              //  {
                    refstudent.child("Mohamed Muassi").setValue(student)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                          //  Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                          //  Log.d("COMPLETE",String.valueOf(task.isSuccessful()));
                                        }
                                    }).

                addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.d("ERROR",e.getMessage());
                            System.out.println(e.getMessage());
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
               // }
            }
            catch (Exception ex){

                //eTname.setText(ex.getMessage());
               Toast.makeText(getApplicationContext(),"error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
*/
            final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
            refAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();

                            if (task.isSuccessful()) {

                            //    if(task.getException()!=null)
                              //  Toast.makeText(getApplicationContext(),String.valueOf(task.getException().getMessage()),Toast.LENGTH_SHORT).show();
                                SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                editor.putString("email",email);
                                editor.putString("password",password);
                                editor.commit();

                                Log.d("MainActivity", "createUserWithEmail:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                uid = user.getUid();
                                //UUID.randomUUID();
                                student = new Student(name, phone, email, uid, id);
                                refstudent.child(uid).setValue(student);
                                Toast.makeText(StudentLogIN.this, "Successful registration", Toast.LENGTH_LONG).show();

//                                Intent si = new Intent(StudentLogIN.this, StudentMain_Activity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("student", student);
//                                si.putExtras(bundle);
//                                startActivity(si);

                                finish();


                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(StudentLogIN.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                else {
                                    Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(StudentLogIN.this, "User create failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(StudentLogIN.this,MainActivity.class);
        startActivity(i);
    }

    public  interface  firebase{
        public  void onstart(String email);
        public  void oncomplete(Student student);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        uid = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), uid, Toast.LENGTH_SHORT).show();
    }




    public void FGM(View view) {
        t=new Intent(StudentLogIN.this,ForGotMyPass.class);
        startActivity(t);
    }
}