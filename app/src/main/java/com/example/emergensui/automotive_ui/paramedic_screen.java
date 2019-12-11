package com.example.emergensui.automotive_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class paramedic_screen extends AppCompatActivity {
    private static final Random RAMDOM = new Random();
    private int lastX = 1;
    private Button btnBack;
    private int speedv;
    private GraphView graph;
    private TextView heart_value;
    private TextView blood_value;
    private TextView oxygen_value;
    private TextView respiration_value;
    private SpeedView speedometer;

    private FirebaseDatabase database = null;
    private DatabaseReference ref = null;
    speedometer s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramedic_screen);

        getId();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //getSpeedometerData();
        test();
    }

    private void test(){
        testWithRandom();

        testGraph();

    }
    private void getId(){
        btnBack = (Button) findViewById(R.id.btnBack);
        speedometer = findViewById(R.id.speedView);
        graph = (GraphView) findViewById(R.id.graph);
        heart_value = (TextView)findViewById(R.id.heart_value);
        blood_value = (TextView)findViewById(R.id.blood_value);
        oxygen_value = (TextView)findViewById(R.id.oxygen_value);
        respiration_value = (TextView)findViewById(R.id.respiration_value);
    }

    private void testSpeedometer(){
        speedometer.setMinSpeed(0);
        speedometer.setMaxSpeed(200);

        speedometer.speedTo(speedv);

    }
    private void getSpeedometerData(){
        Bundle extras = getIntent().getExtras();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Speedometer/");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsh : dataSnapshot.getChildren()) {
                    final String speedometerPath = "speedometer/" + dsh.getKey();
                    System.out.println(speedometerPath);
                    System.out.println(dsh.getKey());

                    ref = database.getReference(speedometerPath);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            s = dataSnapshot.getValue(speedometer.class);



                            speedometer.speedTo(Integer.valueOf(s.getSpeed()));
                            heart_value.setText(s.getHeart_rate());
                            blood_value.setText(s.getBlood_pressure());
                            oxygen_value.setText(s.getOxygen());
                            respiration_value.setText(s.getRespiration());

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void testWithRandom(){
        speedv=RAMDOM.nextInt(200);
        testSpeedometer();
        String heartV = (RAMDOM.nextInt(40)+80)+ " bps";
        heart_value.setText(heartV);
        String bloodV = (RAMDOM.nextInt(100)+60)+ "/" + (RAMDOM.nextInt(40)+20)+ " mmHg";
        blood_value.setText(bloodV);
        String oxygenV = (RAMDOM.nextInt(10)+ 90)+ " %";
        oxygen_value.setText(oxygenV);
        String respirationV = (RAMDOM.nextInt(40))+ " rps";
        respiration_value.setText(respirationV);
    }
    private void testGraph(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(lastX, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
        });
        graph.addSeries(series);
    }
}




