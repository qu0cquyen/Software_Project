package com.example.emergensui.automotive_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.anastr.speedviewlib.SpeedView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class paramedic_screen extends AppCompatActivity {
    private static final Random RAMDOM = new Random();
    private int lastX = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramedic_screen);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        GraphView graph = (GraphView) findViewById(R.id.graph);

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

}


