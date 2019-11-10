package com.example.emergensui.automotive_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class paramedic_screen extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    private static final Random RAMDOM = new Random();
    private int lastX = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramedic_screen);

        Button btnBack= (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        //Create real time graph
        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setScrollable(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=0;;i++){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }
    // add random data to graph
    private void addEntry(){
        series.appendData(new DataPoint(lastX++, RAMDOM.nextDouble()*10d),true,10);
    }
}
