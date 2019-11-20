package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.anastr.speedviewlib.SpeedView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class paramedic_screen extends AppCompatActivity {
    private static final Random RAMDOM = new Random();
    private int lastX = 1;
    private Button btnBack;
    private TextView speed_value;
    private GraphView graph;
    private TextView heart_value;
    private TextView blood_value;
    private TextView oxygen_value;
    private TextView respiration_value;


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
        Button btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(paramedic_screen.this, MapActivity.class);
                startActivity(mapIntent);
            }
        });

        testWithRandom();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(lastX, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),
                new DataPoint(lastX++, RAMDOM.nextDouble() * 100d),

        });
        graph.addSeries(series);

    }
    private void getId(){
        btnBack = (Button) findViewById(R.id.btnBack);
        speed_value = (TextView)findViewById(R.id.speed_value);
        graph = (GraphView) findViewById(R.id.graph);
        heart_value = (TextView)findViewById(R.id.heart_value);
        blood_value = (TextView)findViewById(R.id.blood_value);
        oxygen_value = (TextView)findViewById(R.id.oxygen_value);
        respiration_value = (TextView)findViewById(R.id.respiration_value);


    }
    private void testWithRandom(){
        String speedV = RAMDOM.nextInt(200)+ " km/h";
        speed_value.setText(speedV);
        String heartV = (RAMDOM.nextInt(40)+80)+ " bps";
        heart_value.setText(heartV);
        String bloodV = (RAMDOM.nextInt(100)+60)+ "/" + (RAMDOM.nextInt(40)+20)+ " mmHg";
        blood_value.setText(bloodV);
        String oxygenV = (RAMDOM.nextInt(10)+ 90)+ " %";
        oxygen_value.setText(oxygenV);
        String respirationV = (RAMDOM.nextInt(40))+ " rps";
        respiration_value.setText(respirationV);
    }


}
