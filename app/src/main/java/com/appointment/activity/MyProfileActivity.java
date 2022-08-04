package com.appointment.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.appointment.R;
import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.ProfileModel;
import com.appointment.apiViews.model.StateModel;
import com.appointment.apiViews.presenter.ProfilePresenter;
import com.appointment.apiViews.view.IProfileView;
import com.appointment.databinding.ActivityMyProfileBinding;
import com.appointment.preferences.AppPreferenceManager;

import java.util.ArrayList;

public class MyProfileActivity extends BaseActivity implements IProfileView {

    ActivityMyProfileBinding binding;
    ProfilePresenter presenter;

    ArrayList stateName, stateId;
    ArrayList cityName, cityId;

    String stateslectedID = "" , stateSelectedName;
    String citySelectedID = "" , citySelectedName;

    ProfileModel category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);

        stateName = new ArrayList();
        stateId = new ArrayList();

        cityName = new ArrayList();
        cityId = new ArrayList();


        presenter = new ProfilePresenter();
        presenter.setView(this);

        presenter.getProfile(this, AppPreferenceManager.getUserId(this));




        cityName.add(0 , "Please Select District");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cityName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvcity.setAdapter(dataAdapter);
        //attach the listener to the spinner

        //attach the listener to the spinner
        binding.tvstate .setOnItemSelectedListener(new MyOnItemSelectedListener());


    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            stateSelectedName = (String) stateName.get(stateName.indexOf(selectedItem));
            stateslectedID = (String) stateId.get(stateName.indexOf(selectedItem));
            presenter.getCity(MyProfileActivity.this , stateslectedID);


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backarraow:
                finish();
                break;


            case R.id.tv_submit:
                presenter.getUpdate(this, AppPreferenceManager.getUserId(this),
                        binding.tvUser.getText().toString(),
                        binding.emailid.getText().toString(),
                        binding.addMobile.getText().toString(),
                        binding.tvbusinessName.getText().toString(),
                        stateslectedID,
                        citySelectedID,
                        binding.pincode.getText().toString());

                break;


           /* case R.id.add_medical_store:
                Intent intent = new Intent(getApplicationContext(), MyMedicalList.class);
                startActivity(intent);
                break;
            case R.id.add_whole_seller:
                intent = new Intent(getApplicationContext(), MyWholesellList.class);
                startActivity(intent);
                break;
            case R.id.add_doctor:
                intent = new Intent(getApplicationContext(), MyDoctorList.class);
                startActivity(intent);
                break;*/





        }
    }

    @Override
    public void onSuccess(ProfileModel category) {

        snackBar(binding.container, category.getMessage());

        this.category = category;
        if (category.getSuccess() && category.getData() != null) {

            binding.tvUser.setText(category.getData().getName());
            binding.emailid.setText(category.getData().getEmail());
            binding.addMobile.setText(category.getData().getMobile());

            binding.tvbusinessName.setText(category.getData().getBusinessName());

           // binding.state.setText(category.getData().getState());


            //  binding.city.setText(category.getData().getCity());


            binding.pincode.setText(category.getData().getPincode());

            AppPreferenceManager.setUserName(this, category.getData().getName());
            AppPreferenceManager.setUserEmail(this, category.getData().getEmail());
            AppPreferenceManager.setUserPhone(this, category.getData().getMobile());


            presenter.getState(MyProfileActivity.this);

        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStateSuccess(StateModel item) {

        if (stateName != null && stateName.size() > 0) {
            stateName.clear();
            stateId.clear();

        }

        for (int i = 0; i <item.getData().size() ; i++) {
            stateName.add(item.getData().get(i).getName());
            stateId.add(item.getData().get(i).getId());
        }

       // stateName.add(0 , "Please Select State");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stateName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvstate.setAdapter(dataAdapter);

        stateslectedID   = String.valueOf(stateId.indexOf(category.getData().getState()));

        Log.e("stateslectedID - " , stateslectedID);
        binding.tvstate.setSelection(Integer.parseInt(stateslectedID));

      //  presenter.getCity(MyProfileActivity.this , stateslectedID);


    }

    @Override
    public void onCitySuccess(CityModel item) {

        if (cityName != null && cityName.size() > 0) {
            cityName.clear();
            cityId.clear();

        }
        for (int i = 0; i <item.getData().size() ; i++) {
            cityName.add(item.getData().get(i).getCity());
            cityId.add(item.getData().get(i).getId());
        }

       // cityName.add(0 , "Please Select District");

        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cityName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvcity.setAdapter(dataAdapter);

        //attach the listener to the spinner
        binding.tvcity .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

                String selectedItem = parent.getItemAtPosition(pos).toString();
                citySelectedID = (String) cityId.get(cityName.indexOf(selectedItem));
                citySelectedName = (String) cityName.get(cityName.indexOf(selectedItem));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvcity.setSelection(cityId.indexOf(category.getData().getCity()));

    }

}
