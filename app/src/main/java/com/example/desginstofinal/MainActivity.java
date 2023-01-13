package com.example.desginstofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button worker,student;
    Intent t;
    @SuppressLint("MissingInflatedId")
    @Override
    /**
     * @author		Ahmad mashal
     * @version	    V1.0
     * @since		6/12/2022
     * user will   Choose who he is student/manegmant person.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student=(Button) findViewById(R.id.student);
        worker=(Button)findViewById(R.id.worker);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * moveing to the next student activity.
                 * <p>
                 *next:LOGIN
                 * @param	view Button	on click operate the action.
                 */

                t=new Intent(MainActivity.this,StudentLogIN.class);
                t.putExtra("user",false);
                startActivity(t);
                finish();
            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                t=new Intent(MainActivity.this,addproducts.class);
//                startActivity(t);
//                finish();

                t=new Intent(MainActivity.this,StudentLogIN.class);
                t.putExtra("user",true);
                startActivity(t);
                finish();
            }
        });
    }



}