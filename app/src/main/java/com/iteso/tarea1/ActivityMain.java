package com.iteso.tarea1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    //Alumno
    Alumno alumno;

    //Actions
    Button button_clear;

    //Form inputs
    EditText editText_name;
    EditText editText_phone;
    Spinner spinner_scholarship;
    RadioGroup radioGroup_gender;
    AutoCompleteTextView autoCompleteTextView_favbook;
    CheckBox checkBox_doesSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set form inputs
        editText_name = findViewById(R.id.activity_main_edit_text_name);
        editText_phone = findViewById(R.id.activity_main_edit_text_phone);
        radioGroup_gender = findViewById(R.id.activity_main_radio_group_gender);
        checkBox_doesSports = findViewById(R.id.activity_main_check_box_sport);

        //Fill Spinner View
        spinner_scholarship = findViewById(R.id.activity_main_spinner_scolarship);

        ArrayAdapter<CharSequence> spinnerItemsAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.activity_main_spinner_scholarship_entries,
                        android.R.layout.simple_spinner_dropdown_item);

        spinnerItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_scholarship.setAdapter(spinnerItemsAdapter);

        //Fill AutoCompleteTextView
        autoCompleteTextView_favbook = findViewById(R.id.activity_main_auto_complete_text_view_favbook);

        ArrayAdapter<CharSequence> autoCompleteTextViewItemsAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.activity_main_auto_complete_favbook_entries,
                        android.R.layout.simple_list_item_1);

        autoCompleteTextView_favbook.setAdapter(autoCompleteTextViewItemsAdapter);

        //Set button actions
        button_clear = findViewById(R.id.activity_main_button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder clearWarningDialog = new AlertDialog.Builder(view.getContext());

                clearWarningDialog
                        .setMessage(getString(R.string.activity_main_warning_dialog_text))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.activity_main_warning_dialog_op_yes),
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                clearFields();
                            }
                        })
                        .setNegativeButton(getString(R.string.activity_main_warning_dialog_op_no),
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = clearWarningDialog.create();

                // show it
                alertDialog.show();

            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //restore fields
        restoreFields(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //save in alumno instance
        storeFields();

        //save in bunlde
        outState.putString("NAME", alumno.getName());
        outState.putString("PHONE", alumno.getPhone());
        outState.putString("SCHOLARSHIP", alumno.getScholarship());
        outState.putString("GENDER", alumno.getGender());
        outState.putString("FAVBOOK", alumno.getFavbook());
        outState.putString("DOESSPORT", Boolean.toString(alumno.getDoesSports()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //check if item_save element was pressed, then show Toast
        int id = item.getItemId();

        if (id == R.id.activity_menu_item_save) {
            storeFields();
            Toast.makeText(this, alumno.toString(), Toast.LENGTH_LONG).show();
            clearFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Use MenuInflater to load the activity_menu.xml resource
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    public void storeFields(){

        //Store in alumno instance
        int selectedId = radioGroup_gender.getCheckedRadioButtonId();
        String radButton_string = (selectedId == -1 ? "" : ((RadioButton)findViewById(selectedId)).getText().toString());

        alumno = new Alumno(
                editText_name.getText().toString(),
                editText_phone.getText().toString(),
                spinner_scholarship.getSelectedItem().toString(),
                radButton_string,
                autoCompleteTextView_favbook.getText().toString(),
                checkBox_doesSports.isChecked()
        );
    }

    public void restoreFields(Bundle savedInstanceState){

        //Restore data to form
        editText_name.setText(savedInstanceState.getString("NAME"));
        if(savedInstanceState.getString("NAME") == ""){
            editText_name.setHint(getString(R.string.activity_main_edit_text_name_hint));
        }

        editText_phone.setText(savedInstanceState.getString("PHONE"));
        if(savedInstanceState.getString("PHONE") == ""){
            editText_phone.setHint(getString(R.string.activity_main_edit_text_phone_hint));
        }

        spinner_scholarship
                .setSelection(((ArrayAdapter<String>)spinner_scholarship.getAdapter())
                        .getPosition(savedInstanceState.getString("SCHOLARSHIP")));

        if(savedInstanceState.getString("GENDER") == getString(R.string.activity_main_radio_button_gender_fem)){
            radioGroup_gender.check(R.id.activity_main_radio_button_fem);
        } else {
            radioGroup_gender.check(R.id.activity_main_radio_button_mas);
        }

        autoCompleteTextView_favbook.setText(savedInstanceState.getString("FAVBOOK"));
        checkBox_doesSports.setChecked(Boolean.valueOf(savedInstanceState.getString("DOESSPORT")));
    }

    public void clearFields(){

        //Set fields to default
        editText_name.setText("");
        editText_name.setHint(getString(R.string.activity_main_edit_text_name_hint));

        editText_phone.setText("");
        editText_phone.setHint(getString(R.string.activity_main_edit_text_phone_hint));

        spinner_scholarship.setSelection(0, true);
        ((RadioButton)findViewById(R.id.activity_main_radio_button_fem)).setChecked(true);
        autoCompleteTextView_favbook.setText("");
        checkBox_doesSports.setChecked(false);
    }
}
