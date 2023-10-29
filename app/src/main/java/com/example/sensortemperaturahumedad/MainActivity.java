package com.example.sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView temp;
    private TextView hum;
    private SensorManager sensorManager;
    private Sensor TemperaturaSensor;
    private Sensor HumedadSensor;
    private Boolean TemperaturaDisponible;
    private Boolean HumedadDisponible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp=findViewById(R.id.Temperatura);
        hum = findViewById(R.id.Humedad);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TemperaturaDisponible = false;
        HumedadDisponible = false;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            TemperaturaSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            TemperaturaDisponible = true;
        }else {
            temp.setText("El sensor de temperatura no esta disponible");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TemperaturaDisponible){
            sensorManager.registerListener(this,TemperaturaSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (HumedadDisponible){
            sensorManager.registerListener(this,HumedadSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            temp.setText(sensorEvent.values[0] + " °C");
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            hum.setText(sensorEvent.values[0] + " %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}