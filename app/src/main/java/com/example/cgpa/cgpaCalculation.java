package com.example.cgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class cgpaCalculation extends AppCompatActivity {

    Button submit,add;
    LinearLayout ly;
    TextView cgpa_res;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa_calculation);
        submit=findViewById(R.id.cgpa_submit);
        add=findViewById(R.id.cgpa_add);
        ly=findViewById(R.id.cgpa_ly);
        cgpa_res=findViewById(R.id.cgpa_res);

        for(int i=0;i<8;i++){
            View myView=getLayoutInflater().inflate(R.layout.cgpa_row_add,null,false);
            ImageView iv=myView.findViewById(R.id.cgpa_iv);
            ly.addView(myView);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ly.removeView(myView);
                }
            });
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }

            private void addView(){
                View myView=getLayoutInflater().inflate(R.layout.cgpa_row_add,null,false);
                ImageView iv=myView.findViewById(R.id.cgpa_iv);
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
                int j=0;
                int flag=0;
                double total_credit=0;
                double ans=0.0;
                for(int i=0;i<ly.getChildCount();i++)
                {
                    View myView=ly.getChildAt(i);
                    EditText credit_tv=myView.findViewById(R.id.cgpa_total_tv);
                    if(credit_tv.getText().toString().equals(""))
                        total_credit+=0;
                    else{
                        double credit=Double.parseDouble(credit_tv.getText().toString().trim());
                        total_credit+=credit;
                    }
                }

                for(j=0;j<ly.getChildCount();j++)
                {
                    View myView=ly.getChildAt(j);
                    EditText credit_tv=myView.findViewById(R.id.cgpa_tv);
                    EditText sgpa_tv=myView.findViewById(R.id.cgpa_total_tv);
                    if(credit_tv.getText().toString().equals("") && sgpa_tv.getText().toString().equals(""))
                        ;
                    else if(credit_tv.getText().toString().equals("") || sgpa_tv.getText().toString().equals("")){
                        Toast.makeText(cgpaCalculation.this, "Incomplete details in row: "+(j+1), Toast.LENGTH_SHORT).show();
                        flag=1;
                    }
                    else{
                        double sgpa=Double.parseDouble(credit_tv.getText().toString().trim());
                        double credit=Double.parseDouble(sgpa_tv.getText().toString().trim());
                        double x=(credit*100/total_credit);
                        ans+=(sgpa*x/100);
                    }
                }
                if(flag==1)
                    cgpa_res.setText("");
                else
                    cgpa_res.setText("YOUR CGPA IS: "+String.format("%.2f",ans));

            }

        });
    }

}
