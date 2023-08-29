package com.example.cgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class sgpaCalculation extends AppCompatActivity {
    Button submit,add;
    LinearLayout ly;
    TextView sgpa_res;
    SQLiteDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpa_calculation);
        submit=findViewById(R.id.sgpa_submit);
        add=findViewById(R.id.sgpa_add);
        ly=findViewById(R.id.sgpa_ly);
        sgpa_res=findViewById(R.id.sgpa_res);
        db=openOrCreateDatabase("CreditDB",MODE_PRIVATE,null);
        /*db.execSQL("create table if not exists CREDIT(sub_name varchar(10) primary key,sub_credit int)");
        db.execSQL("insert into CREDIT values('mces',4)");
        db.execSQL("insert into CREDIT values('daa',4)");
        db.execSQL("insert into CREDIT values('fafl',3)");*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }

            private void addView(){
                View myView=getLayoutInflater().inflate(R.layout.sgpa_row_add,null,false);
                ImageView iv=myView.findViewById(R.id.sgpa_iv);
                ly.addView(myView);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ly.removeView(myView);
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double total_credits=0;
                double total_marks=0;
                double grade=0;
                int flag=0;
                for(int i=0;i<ly.getChildCount();i++)
                {
                    View myView=ly.getChildAt(i);
                    EditText sub_tv=myView.findViewById(R.id.subject);
                    EditText cie_tv=myView.findViewById(R.id.cie);
                    EditText see_tv=myView.findViewById(R.id.see);
                    EditText credit_tv=myView.findViewById(R.id.credit);
                    if(credit_tv.getText().toString().equals("") && !sub_tv.getText().toString().equals(""))
                    {
                        String sub_name=sub_tv.getText().toString().toLowerCase();
                        Cursor c=db.rawQuery("select * from CREDIT where sub_name=?",new String[]{sub_name+""});
                        if(c.getCount()>0){
                            c.moveToFirst();
                            int set_credit=c.getInt(1);
                            credit_tv.setText(set_credit+"");
                        }
                    }
                    if(cie_tv.getText().toString().equals("") && see_tv.getText().toString().equals("") && credit_tv.getText().toString().equals(""))
                        ;
                    else if(cie_tv.getText().toString().equals("") || see_tv.getText().toString().equals("") || credit_tv.getText().toString().equals(""))
                    {
                        Toast.makeText(sgpaCalculation.this, "Incomplete details in row: "+(i+1), Toast.LENGTH_SHORT).show();
                        flag=1;
                    }
                    else
                    {
                        double cie=Double.parseDouble(cie_tv.getText().toString().trim());
                        double see=Double.parseDouble(see_tv.getText().toString().trim());
                        total_credits+=Double.parseDouble(credit_tv.getText().toString().trim());
                        double credit=Double.parseDouble(credit_tv.getText().toString().trim());
                        total_marks=cie+see;
                        if(cie<20 || see<20)
                            grade+=0;
                        else{
                            if(total_marks<=100 && total_marks>=90)
                                grade+=(10*credit);
                            if(total_marks<=89 && total_marks>=80)
                                grade+=(9*credit);
                            if(total_marks<=79 && total_marks>=70)
                                grade+=(8*credit);
                            if(total_marks<=69 && total_marks>=60)
                                grade+=(7*credit);
                            if(total_marks<=59 && total_marks>=50)
                                grade+=(6*credit);
                            if(total_marks<50)
                                grade+=(4*credit);
                        }
                    }
                }
                double ans=(grade/total_credits);
                if(flag==1)
                    sgpa_res.setText("");
                else
                    sgpa_res.setText("YOUR SGPA IS: "+String.format("%.2f",ans));
            }
        });
    }
}