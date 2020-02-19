package com.example.finalassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalassignment.R;
import com.example.finalassignment.database.PersonServiceImpl;
import com.example.finalassignment.helper.Helper;
import com.example.finalassignment.models.Person;

public class AddPersonActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtPhone, txtAddress;
    private Button saveButton;
    private Person person;
    private TextView titleTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        initViews();
        fetchPerson();
    }

    private void fetchPerson() {
        Intent intent = getIntent();
        person = intent.getParcelableExtra("person");
        if (person != null) {
            setUpForEdit();
            titleTxtView.setText("Edit Person Info");
        }
    }

    private void setUpForEdit() {
        txtFirstName.setText(person.getFirstName());
        txtLastName.setText(person.getLastName());
        txtPhone.setText(person.getPhoneNumber());
        txtAddress.setText(person.getAddress());
    }

    private void initViews() {
        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtPhone = findViewById(R.id.txt_phn);
        txtAddress = findViewById(R.id.txt_address);
        saveButton = findViewById(R.id.btn_save);
        titleTxtView = findViewById(R.id.txtView_total);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked();
            }
        });
    }

    private void saveButtonClicked() {
        if(txtFirstName.getText().toString().isEmpty()) {
            Helper.showAlertCommon(this, "Please enter first name");
        } else if(txtLastName.getText().toString().isEmpty()) {
            Helper.showAlertCommon(this, "Please enter last name");
        } else if(txtPhone.getText().toString().isEmpty()) {
            Helper.showAlertCommon(this, "Please enter phone number");
        }  else if(txtPhone.getText().toString().length() != 10) {
            Helper.showAlertCommon(this, "Phone number should be of 10 digits");
        } else if(txtAddress.getText().toString().isEmpty()) {
            Helper.showAlertCommon(this, "Please enter address");
        } else {
            PersonServiceImpl personService = new PersonServiceImpl(getApplicationContext());
            String fName = txtFirstName.getText().toString();
            String lName = txtLastName.getText().toString();
            String phone = txtPhone.getText().toString();
            String address = txtAddress.getText().toString();
            if (person == null) {
                Person person = new Person(fName, lName, phone, address);
                personService.insert(person);
                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
            } else {
                person.setFirstName(fName);
                person.setLastName(lName);
                person.setPhoneNumber(phone);
                person.setAddress(address);
                personService.update(person);
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
