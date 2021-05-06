package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter blueloothAdapter = BluetoothAdapter.getDefaultAdapter();
    Button btn_activation;
    Button btn_admin_activ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_activation = findViewById(R.id.btn_activation);
        btn_admin_activ = findViewById(R.id.btn_admin_activ);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (blueloothAdapter.isEnabled())
        {
            btn_admin_activ.setBackgroundColor(Color.parseColor("#0F9D58"));
            btn_activation.setBackgroundColor(Color.parseColor("#0F9D58"));
        }
    }

    public void verifier(View view) {
        if (blueloothAdapter == null) {
            Toast.makeText(this, "pas de bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " bluetooth adapter exist", Toast.LENGTH_SHORT).show();

        }
    }

    public void Activer(View view) {
        if (!blueloothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        } else {
            blueloothAdapter.disable();
            btn_activation.setBackgroundColor(Color.parseColor("#6200ea"));
            btn_admin_activ.setBackgroundColor(Color.parseColor("#6200ea"));
        }
    }

    // Called Back when the started activity returns a result
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == REQUEST_ENABLE_BT) {  // Match the request code
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth Turned on", Toast.LENGTH_LONG).show();
                btn_activation.setText("Bluetooth Is Active ");
                btn_admin_activ.setText("Bluetooth Is Active ");
                btn_admin_activ.setBackgroundColor(Color.parseColor("#0F9D58"));
                btn_activation.setBackgroundColor(Color.parseColor("#0F9D58"));

            } else {   // RESULT_CANCELED
                Toast.makeText(this, "error: turning on bluetooth", Toast.LENGTH_LONG).show();
                btn_activation.setText(" Bluetooth DISABLED ");
                btn_activation.setBackgroundColor(Color.parseColor("#6200ea"));
                btn_admin_activ.setBackgroundColor(Color.parseColor("#6200ea"));

            }

        }
    }

    public void ActivationAdmin(View view) {
        if (blueloothAdapter.isEnabled()) {
            blueloothAdapter.disable();
            btn_admin_activ.setText("Bluetooth DISABLED");
            btn_activation.setText(" Bluetooth DISABLED ");
            btn_activation.setBackgroundColor(Color.parseColor("#6200ea"));
            btn_admin_activ.setBackgroundColor(Color.parseColor("#6200ea"));
        } else {
            blueloothAdapter.enable();
            btn_activation.setText("Bluetooth Is Active ");
            btn_admin_activ.setText("Bluetooth Is Active ");
            btn_admin_activ.setBackgroundColor(Color.parseColor("#0F9D58"));
            btn_activation.setBackgroundColor(Color.parseColor("#0F9D58"));

        }
    }

    public void ListDeviceClick(View view) {
        Set<BluetoothDevice> devices = blueloothAdapter.getBondedDevices();
        String ch = "";
        for (BluetoothDevice d : devices) {
            ch += d.getName() + "/n";
        }
        Toast.makeText(this, ch, Toast.LENGTH_SHORT).show();

    }

    public void DiscovryClick(View view) {
        blueloothAdapter.startDiscovery();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(context, device.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };
}