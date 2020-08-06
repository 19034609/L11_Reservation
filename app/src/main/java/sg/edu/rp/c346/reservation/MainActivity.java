package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etTelephone, etSize, etDay, etTime;
    CheckBox checkBox;
    Button btReserve, btReset;
    int theYear, theMonth, theDay, theHour, theMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        Calendar now = Calendar.getInstance();
        theHour = now.get(Calendar.HOUR);
        theMin = now.get(Calendar.MINUTE);
        theYear = now.get(Calendar.YEAR);
        theMonth = now.get(Calendar.MONTH);
        theDay = now.get(Calendar.DAY_OF_MONTH);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        theYear = year;
                        theMin = monthOfYear;
                        theDay = dayOfMonth;
                        etDay.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, theYear, theMonth, theDay);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int theHour = hourOfDay;
                        int theMin = minute;
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, theHour, theMin, true);
                myTimeDialog.show();
            }
        });

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }

                String message = "New Reservation " + "\n" +
                        "Name: " + etName.getText().toString() + "\n" +
                        "Smoking: " + isSmoke + "\n" +
                        "Size: " + etSize.getText().toString() + "\n" +
                        "Date: " + etDay.getText() + "\n" +
                        "Time: " + etTime.getText();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(message);
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM",null);
                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                etDay.setText(null);
                etTime.setText(null);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", etName.getText().toString());
        edit.putString("tel", etTelephone.getText().toString());
        edit.putString("size", etSize.getText().toString());
        edit.putBoolean("smoking", checkBox.isChecked());
        edit.putString("day", etDay.getText().toString());
        edit.putString("time", etTime.getText().toString());
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", ""));
        etTelephone.setText(prefs.getString("tel", ""));
        etSize.setText(prefs.getString("size", ""));
        checkBox.setChecked(prefs.getBoolean("smoking", false));
        etDay.setText(prefs.getString("day", ""));
        etTime.setText(prefs.getString("time", ""));
    }
}