package com.cpetsolut.com.inceptiveassignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpetsolut.com.inceptiveassignment.DBHelper.Controllerdb;
import com.cpetsolut.com.inceptiveassignment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView details;
    private Button askQuestion,patientlist;
    private Dialog QuestionDialog;
    private View questionView;
    private ImageView cancelDialog;
    private Button submit_area;
    private EditText patientname,uhidnumber,dateofbirth,et_age,mobilenumber,address;
    private Spinner gender;
    String[] gender1 = {"Male","Female","Others"};
    Controllerdb db =new Controllerdb(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        patientlist=(Button)findViewById(R.id.patientlist);
        patientlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayData();
            }
        });

        details=(ListView) findViewById(R.id.details);
        askQuestion=(Button) findViewById(R.id.askQuestion);
        askQuestion.setOnClickListener(v->{
            AddDetails();
        });
    }

    private void displayData() {
       /* db = new Controllerdb(this);
        ArrayList<HashMap<String, String>> userList = db.GetUsers();
        ListAdapter adapter = new SimpleAdapter(NavigationActivity.this, userList, R.layout.result_layout,new String[]{"pname","pUHIDnumber","PAge","paddress"},
                new int[]{R.id.pname, R.id.puhid, R.id.page,R.id.paddress});
        details.setAdapter(adapter);*/
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.setMessage("please wait...");
            pDialog.show();
            new AsyncTask<Object, Integer, ArrayList<HashMap<String, String>>>() {
                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Object... params) {
                    ArrayList<HashMap<String, String>> rumy = db.GetUsers();
                    return rumy;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
                    ListAdapter data=new SimpleAdapter(NavigationActivity.this, hashMaps, R.layout.result_layout,new String[]{"pname","pUHIDnumber","PAge","paddress"},
                            new int[]{R.id.pname, R.id.puhid, R.id.page,R.id.paddress});
                    details.setAdapter(data);
                    super.onPostExecute(hashMaps);
                    if (pDialog.isShowing()){
                        pDialog.dismiss();
                    }
                }
            }.execute();
    }

    private void AddDetails() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.setMessage("please wait...");
        pDialog.show();
        questionView= View.inflate(NavigationActivity.this,R.layout.add_details,null);
        questionView.startAnimation(AnimationUtils.loadAnimation(NavigationActivity.this,R.anim.zoom_in_enter));
        this.QuestionDialog=new Dialog(NavigationActivity.this,R.style.NewDialog);
        this.QuestionDialog.setContentView(questionView);
        this.QuestionDialog.setCancelable(true);
        this.QuestionDialog.show();

        Window window = this.QuestionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        patientname=(EditText)questionView.findViewById(R.id.patientname);
        uhidnumber=(EditText)questionView.findViewById(R.id.uhidnumber);
        dateofbirth=(EditText)questionView.findViewById(R.id.dateofbirth);
        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDatePicker();
            }
        });
        et_age=(EditText)questionView.findViewById(R.id.et_age);
        mobilenumber=(EditText)questionView.findViewById(R.id.mobilenumber);
        address=(EditText)questionView.findViewById(R.id.address);
        address=(EditText)questionView.findViewById(R.id.address);
        gender=(Spinner) questionView.findViewById(R.id.gender);
        gender.setOnItemSelectedListener(this);
        ArrayAdapter aa12 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender1);
        aa12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa12);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit_area=(Button) questionView.findViewById(R.id.submit_area);
        submit_area.setOnClickListener(view -> {
            if (patientname.getText().toString().isEmpty()){
                patientname.setError("Patient Name");
            }else {
                if (uhidnumber.getText().toString().isEmpty()){
                    uhidnumber.setError("UHID number");
                }else {
                    if (dateofbirth.getText().toString().isEmpty()){
                        dateofbirth.setError("Date of Birth");
                    }else {
                        if (mobilenumber.getText().toString().isEmpty()){
                            mobilenumber.setError("Mobile Number");
                        }else {
                            db = new Controllerdb(NavigationActivity.this);
                            db.insertUserDetails(patientname.getText().toString(),uhidnumber.getText().toString(),dateofbirth.getText().toString(),et_age.getText().toString(),
                                    gender.getSelectedItem().toString(),mobilenumber.getText().toString(),address.getText().toString());
                            displayData();
                            QuestionDialog.dismiss();
                        }
                    }
                }
            }
        });
        cancelDialog=(ImageView) questionView.findViewById(R.id.closeDialog);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionDialog.dismiss();
            }
        });
        pDialog.dismiss();
    }
    private void bookDatePicker() {
        final Calendar newDate = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog currDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                dateofbirth.setText(dateFormatter.format(newDate.getTime()));
                dateofbirth.setError(null);
                et_age.setText(Integer.toString(calculateAge(newDate.getTimeInMillis())));
            }
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        // currDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //newDate.add(Calendar.DATE, -0);
        //currDatePickerDialog.getDatePicker().setMaxDate(newDate.getTimeInMillis() + (1000 * 60 * 60 * 24 * 24));
        currDatePickerDialog.show();
    }
    public static int calculateAge(long birthdate) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(birthdate);
        Calendar today=Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
