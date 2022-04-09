package com.cygen.cygendineinpos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.cygen.cygendineinpos.RetrofitRequest.AddCompanyRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddSiteRequest;
import com.cygen.cygendineinpos.RetrofitRequest.CategoryRequest;
import com.cygen.cygendineinpos.RetrofitRequest.HoldRequest;
import com.cygen.cygendineinpos.RetrofitRequest.LoginRequest;
import com.cygen.cygendineinpos.RetrofitRequest.StateListRequest;
import com.cygen.cygendineinpos.RetrofitResponse.CompanyResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CountryListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CurrencyListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SiteSettingsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.StateListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TaxResponse;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCompanyDetailsActivity extends BaseActivity {

    AppCompatEditText companyNameEt, mobileEt, emailEt, companyPhoneEt, companyWebsiteEt, cityEt, postcodeEt, addressEt, bankDetailsEt, siteNameEt, siteUrlEt, sitecopyRightEt, sitedomainet;
    AppCompatAutoCompleteTextView countrySpinnerEt, stateSpinnerEt;
    AppCompatImageView companyLogoImageView, siteLogoImageView;
    AppCompatButton updateCompanyBtn, closeCompanyBtn;
    String companyName, mobile, phone, email, website, country, state, city, postcode, address, bankDetails;

    SessionManager sessionManager;
    HashMap<String, String> user1;
    String userId, outlet_id, customer_id;
    List<String> countryNameList, stateNameList;
    List<CountryListResponse.ProductListResponse> countryList;
    List<StateListResponse.ProductListResponse> stateList;
    String countryId, stateId;
    SiteSettingsResponse.UserListResponse siteSettingsList;
    SiteSettingsResponse.UserListResponse1 siteSettingsList1;
    AppCompatAutoCompleteTextView currencyspinner;

    AppCompatTextView clickIvUpload;
    String encodedImage = "null";
    AppCompatImageView SiteSettingimageView;
    AppCompatTextView chooseFileTv;
    String site_name, currency_id, site_url, copyright, domain, product_img;
    String currencyName, currencyId;
    List<String> currencyNameList, currencyIdList;
    List<CurrencyListResponse.ProductListResponse> currencyList;
    AppCompatButton updateSiteBtn;
    ImageView closeIcon;
    AppCompatTextView clicksiteIvUpload;

    private static final int Image_Capture_Code = 2;
    private static final int Image_Capture_Code_1 = 3;
    ArrayAdapter aa;
    String companyupdateId, siteUpdateId;
    String currencySymbol;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_profile);

        inflateId();
        retrofitService();

        sessionManager = new SessionManager(getApplicationContext());

        currencyNameList = new ArrayList<>();
        currencyNameList.add("Select Currency");
        stateNameList = new ArrayList<>();
        currencyIdList = new ArrayList<>();

        user1 = sessionManager.getUserDetails();
        userId = user1.get(sessionManager.KEY_USER_ID);
        outlet_id = user1.get(sessionManager.KEY_OUTLET_ID);
//        customer_id = user1.get(sessionManager.KEY_CUSTOMER_ID);

        clickIvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

            }
        });

        clicksiteIvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 2);//one can be replaced with any action code

            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                startActivity(intent);
            }
        });

        getSiteSettingResponse();
        getCountryDetails();
        getStateDetails();
        AdmingetCurrencyListForSiteSettins();
    }

    public void inflateId() {

        countryNameList = new ArrayList<>();

        companyNameEt = findViewById(R.id.companyNameEt);
        mobileEt = findViewById(R.id.mobileEt);
        emailEt = findViewById(R.id.emailEt);
        companyPhoneEt = findViewById(R.id.companyPhoneEt);
        companyWebsiteEt = findViewById(R.id.companyWebsiteEt);
        postcodeEt = findViewById(R.id.postcodeEt);
        cityEt = findViewById(R.id.cityEt);
        addressEt = findViewById(R.id.addressEt);
        bankDetailsEt = findViewById(R.id.bankDetailsEt);
        clickIvUpload = findViewById(R.id.clickIvUpload);

        countrySpinnerEt = findViewById(R.id.countrySpinnerEt);
        stateSpinnerEt = findViewById(R.id.stateSpinnerEt);
        companyLogoImageView = findViewById(R.id.companyLogoImageView);
        siteLogoImageView = findViewById(R.id.companyLogoImageView);
        updateSiteBtn = findViewById(R.id.updateSiteBtn);
        closeIcon = findViewById(R.id.closeIcon);

        closeCompanyBtn = findViewById(R.id.closeCompanyBtn);
        updateCompanyBtn = findViewById(R.id.updateCompanyBtn);
        clicksiteIvUpload = findViewById(R.id.clicksiteIvUpload);


        siteNameEt = findViewById(R.id.siteNameEt);
        currencyspinner = findViewById(R.id.currencySpinner);
        siteUrlEt = findViewById(R.id.siteUrlEt);
        sitecopyRightEt = findViewById(R.id.sitecopyRightEt);
        sitedomainet = findViewById(R.id.sitedomainet);
//        SiteSettingimageView = findViewById(R.id.SiteSettingimageView);
        chooseFileTv = findViewById(R.id.chooseFileTv);


        findViewById(R.id.closeSiteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                startActivity(intent);
            }
        });

        updateSiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                companyName = companyNameEt.getText().toString();
                mobile = mobileEt.getText().toString();
                email = emailEt.getText().toString();
                website = companyWebsiteEt.getText().toString();
                city = cityEt.getText().toString();
                postcode = postcodeEt.getText().toString();
                address = addressEt.getText().toString();
                bankDetails = bankDetailsEt.getText().toString();

                site_name = siteNameEt.getText().toString();
                site_url = siteUrlEt.getText().toString();
                copyright = sitecopyRightEt.getText().toString();
                domain = sitedomainet.getText().toString();

                if (companyNameEt.getText().toString().length() != 0) {
                    if (mobileEt.getText().toString().length() != 0) {
                        if (emailEt.getText().toString().length() != 0) {
                            if (companyWebsiteEt.getText().toString().length() == 0) {
                                if (cityEt.getText().toString().length() != 0) {
                                    if (addressEt.getText().toString().length() != 0) {
                                        if (currencyspinner.getText().toString().length() != 0) {
                                            if (siteNameEt.getText().toString().length() != 0) {
                                                if (siteUrlEt.getText().toString().length() != 0) {
                                                    if (sitecopyRightEt.getText().toString().length() == 0) {
                                                        if (sitedomainet.getText().toString().length() == 0) {
                                                            AdminAddSiteSettingsResponse();
                                                            addCustomerResponse();

                                                        } else {
                                                            showShortSnackBar("Enter Domain Name");
                                                        }

                                                    } else {
                                                        showShortSnackBar("Enter Site Copyright");
                                                    }

                                                } else {
                                                    showShortSnackBar("Enter Site URL");
                                                }
                                            } else {
                                                showShortSnackBar("Enter Site Domain");
                                            }
                                        } else {
                                            showShortSnackBar("Enter Your Currency");
                                        }

                                    } else {
                                        showShortSnackBar("Enter Address");
                                    }
                                } else {
                                    showShortSnackBar("Enter City");
                                }
                            } else {
                                showShortSnackBar("Enter Website");
                            }
                        } else {
                            showShortSnackBar("Enter Email");
                        }
                    } else {
                        showShortSnackBar("Enter Mobile");
                    }

                } else {
                    showShortSnackBar("Enter Company Name");
                }

            }
        });


    }

    private void getSiteSettingResponse() {

        LoginRequest request = new LoginRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        showProgressDialog();
        Log.d("sitereqyest", new Gson().toJson(request));

        Call<SiteSettingsResponse> call = apiService.siteSettingsData("1234", request);

        call.enqueue(new Callback<SiteSettingsResponse>() {
            @Override
            public void onResponse(Call<SiteSettingsResponse> call, Response<SiteSettingsResponse> response) {

                Log.d("siterespons", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    SiteSettingsResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

                        siteSettingsList = user.productResults;
                        siteSettingsList1 = user.productResults1;

                        Log.d("siresettngsrsespo", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                companyupdateId = siteSettingsList1.getId();
                                companyName = siteSettingsList1.getCompany_name();
                                mobile = siteSettingsList1.getMobile();
                                email = siteSettingsList1.getEmail();
                                phone = siteSettingsList1.getPhone();
                                website = siteSettingsList1.getCompany_website();
                                country = siteSettingsList1.getCountry();
                                state = siteSettingsList1.getState();
                                city = siteSettingsList1.getCity();
                                postcode = siteSettingsList1.getPostcode();
                                address = siteSettingsList1.getAddress();
                                bankDetails = siteSettingsList1.getBank_details();
                                countryId = siteSettingsList1.getCountry_id();
                                stateId = siteSettingsList1.getState_id();

                                siteUpdateId = siteSettingsList.getId();
                                site_name = siteSettingsList.getSite_name();
                                currencyName = siteSettingsList.getCurrency();
                                site_url = siteSettingsList.getSite_url();
                                copyright = siteSettingsList.getCopyright();
                                domain = siteSettingsList.getDomain();
                                product_img = siteSettingsList.getLogo();
                                currencyId = siteSettingsList.getCurrency_id();


                                if (companyName.equalsIgnoreCase("0")) {
                                    companyName = "";
                                }
                                if (mobile.equalsIgnoreCase("0")) {
                                    mobile = "";
                                }
                                if (email.equalsIgnoreCase("0")) {
                                    email = "";
                                }
                                if (phone.equalsIgnoreCase("0")) {
                                    phone = "";
                                }
                                if (website.equalsIgnoreCase("0")) {
                                    website = "";
                                }
                                if (country.equalsIgnoreCase("0")) {
                                    country = "";
                                }
                                if (state.equalsIgnoreCase("0")) {
                                    state = "";
                                }
                                if (city.equalsIgnoreCase("0")) {
                                    city = "";
                                }
                                if (postcode.equalsIgnoreCase("0")) {
                                    postcode = "";
                                }
                                if (address.equalsIgnoreCase("0")) {
                                    address = "";
                                }
                                if (bankDetails.equalsIgnoreCase("0")) {
                                    bankDetails = "";
                                }
//
//                                if(site_name.equalsIgnoreCase("0")){
//                                    site_name = "";
//                                }
//                                if(currencyName.equalsIgnoreCase("0")){
//                                    currencyName = "";
//                                }
//                                if(site_url.equalsIgnoreCase("0")){
//                                    site_url = "";
//                                }
//                                if(copyright.equalsIgnoreCase("0")){
//                                    copyright = "";
//                                }
//                                if(domain.equalsIgnoreCase("0")){
//                                    domain = "";
//                                }
                                companyNameEt.setText(companyName);
                                mobileEt.setText(mobile);
                                emailEt.setText(email);
                                companyPhoneEt.setText(mobile);
                                companyWebsiteEt.setText(website);
                                countrySpinnerEt.setText(country);
                                stateSpinnerEt.setText(state);
                                cityEt.setText(city);
                                postcodeEt.setText(postcode);
                                addressEt.setText(address);
                                bankDetailsEt.setText(bankDetails);

                                siteNameEt.setText(site_name);
                                siteUrlEt.setText(site_url);
                                currencyspinner.setText(currencyName);
                                currencyNameList.add(currencyName);
                                sitecopyRightEt.setText(copyright);
                                sitedomainet.setText(domain);




                            }
                        });
                    } else {

                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SiteSettingsResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getCountryDetails() {

        showProgressDialog();

        HoldRequest holdRequest = new HoldRequest();

        Call<CountryListResponse> call = apiService.countryList("1234", holdRequest);

        call.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {

                Log.d("countryresp", "response 12: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CountryListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {
                        countryNameList.clear();

                        Log.d("customres", "response 12: " + new Gson().toJson(response.body()));
                        countryList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CountryAdapter countryAdapter = new CountryAdapter(getApplicationContext(), R.layout.activity_simple_spinner, countryList);
                                countrySpinnerEt.setAdapter(countryAdapter);
                            }
                        });

                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<CountryListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class CountryAdapter extends ArrayAdapter<CountryListResponse.ProductListResponse> {

        private final Context mContext;
        private final List<CountryListResponse.ProductListResponse> countries;
        private final List<CountryListResponse.ProductListResponse> countriesall;

        private final int mLayoutResourceId;

        public CountryAdapter(Context context, int resource, List<CountryListResponse.ProductListResponse> departments) {
            super(context, resource, departments);
            this.mContext = context;
            this.mLayoutResourceId = resource;
            this.countries = new ArrayList<>(departments);
            this.countriesall = new ArrayList<>(departments);

        }

        public int getCount() {
            return countries.size();
        }

        public CountryListResponse.ProductListResponse getItem(int position) {
            return countries.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.activity_autocomplete_tv_item, parent, false);
                }
                final CountryListResponse.ProductListResponse countryDetails = getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.customerNameTv);
                name.setText(countryDetails.country);


                name.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            //do stuff here
                        }
                        Log.d("idddddd", countryDetails.id);
                        Log.i("click text", "kakak");

                        countryId = countryDetails.id;
                        countrySpinnerEt.setText(countryDetails.country);
                        stateSpinnerEt.setText("");

                        Log.d("countryidd", countryId);
                        getStateDetails();

                        hideKeyBoard();

                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                public String convertResultToString(Object resultValue) {
                    return ((CountryListResponse.ProductListResponse) resultValue).country;
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    List<CountryListResponse.ProductListResponse> departmentsSuggestion = new ArrayList<>();
                    if (constraint != null) {
                        Log.d("dsdsddsddsd", "sdsdsdsd");
                        for (CountryListResponse.ProductListResponse country : countriesall) {
                            Log.d("dsdsddsddsd", "" + country);
                            if (country.country.toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                                departmentsSuggestion.add(country);
                            }
                        }
                        filterResults.values = departmentsSuggestion;
                        filterResults.count = departmentsSuggestion.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    countries.clear();
                    if (results != null && results.count > 0) {
                        // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                        for (Object object : (List<?>) results.values) {
                            if (object instanceof CountryListResponse.ProductListResponse) {
                                countries.add((CountryListResponse.ProductListResponse) object);
                            }
                        }
                        notifyDataSetChanged();
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        countries.addAll(countriesall);
                        notifyDataSetInvalidated();
                    }
                }
            };
        }
    }

    private void getStateDetails() {

        showProgressDialog();
        StateListRequest request = new StateListRequest();
        request.setCountry(countryId);
        request.setOutlet_id(outlet_id);

        Log.d("countryidd2", new Gson().toJson(request));

        Call<StateListResponse> call = apiService.stateList("1234", request);

        call.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {

                Log.d("stateRespo", "response 12: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    StateListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {
                        stateNameList.clear();

                        Log.d("customres", "response 12: " + new Gson().toJson(response.body()));
                        stateList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StateAdapter stateAdapter = new StateAdapter(getApplicationContext(), R.layout.activity_simple_spinner, stateList);
                                stateSpinnerEt.setAdapter(stateAdapter);
                            }
                        });

                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<StateListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class StateAdapter extends ArrayAdapter<StateListResponse.ProductListResponse> {

        private final Context mContext;
        private final List<StateListResponse.ProductListResponse> states;
        private final List<StateListResponse.ProductListResponse> statesAll;

        private final int mLayoutResourceId;

        public StateAdapter(Context context, int resource, List<StateListResponse.ProductListResponse> departments) {
            super(context, resource, departments);
            this.mContext = context;
            this.mLayoutResourceId = resource;
            this.states = new ArrayList<>(departments);
            this.statesAll = new ArrayList<>(departments);

        }

        public int getCount() {
            Log.d("testtt", "" + states.size());
            return states.size();

        }

        public StateListResponse.ProductListResponse getItem(int position) {
            return statesAll.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.activity_autocomplete_tv_item, parent, false);
                }
                final StateListResponse.ProductListResponse countryDetails = getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.customerNameTv);
                name.setText(countryDetails.state);


                name.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            //do stuff here
                        }
                        Log.d("idddddd", countryDetails.id);
                        Log.i("click text", "kakak");
                        stateId = countryDetails.id;

                        hideKeyBoard();

                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                public String convertResultToString(Object resultValue) {
                    return ((StateListResponse.ProductListResponse) resultValue).state;
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    List<StateListResponse.ProductListResponse> departmentsSuggestion = new ArrayList<>();
                    if (constraint != null) {
                        Log.d("dsdsddsddsd", "sdsdsdsd");
                        for (StateListResponse.ProductListResponse customer : statesAll) {
                            if (customer.state.toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                                departmentsSuggestion.add(customer);
                            }
                        }
                        filterResults.values = departmentsSuggestion;
                        filterResults.count = departmentsSuggestion.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    states.clear();
                    if (results != null && results.count > 0) {
                        // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                        for (Object object : (List<?>) results.values) {
                            if (object instanceof StateListResponse.ProductListResponse) {
                                states.add((StateListResponse.ProductListResponse) object);
                            }
                        }
                        notifyDataSetChanged();
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        states.addAll(statesAll);
                        notifyDataSetInvalidated();
                    }
                }
            };
        }
    }


    private void addCustomerResponse() {
        showProgressDialog();
        AddCompanyRequest request = new AddCompanyRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setCompany_website(website);
        request.setCompany_name(companyName);
        request.setMobile(mobile);
        request.setPhone(phone);
        request.setEmail(email);
        request.setCountry(countryId);
        request.setState(stateId);
        request.setCity(city);
        request.setAddress(address);
        request.setPostcode(postcode);
        request.setBank_details(bankDetails);
        request.setProduct_img("image");


        request.setId(companyupdateId);


        Call<CompanyResponse> call = apiService.addCompanyData("1234", request);
        Log.d("AddedCustomerrequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CompanyResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
                        Log.d("savedInvoice", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLongSnackBar(message);
                                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        showLongSnackBar(message);
                    }
                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    private void AdmingetCurrencyListForSiteSettins() {

        CategoryRequest request = new CategoryRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        Log.d("CurreSubRespoReq", new Gson().toJson(request));

        Call<CurrencyListResponse> call = apiService.getCurrencyData("1234", request);

        call.enqueue(new Callback<CurrencyListResponse>() {
            @Override
            public void onResponse(Call<CurrencyListResponse> call, Response<CurrencyListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CurrencyListResponse user = response.body();
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        currencyNameList.clear();
                        currencyIdList.clear();
                        Log.d("SuppliSubRespo", "response 12: " + new Gson().toJson(response.body()));
                        currencyList = user.productResults;
//                        for (int i = 0; i < currencyList.size(); i++) {
//                            currencyNameList.add(currencyList.get(i).currency_name);
//                            currencyIdList.add(currencyList.get(i).id);
//                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


//                                addProductcategorySpinnerData();
                                CurrencyAdapter currencyAdapter = new CurrencyAdapter(getApplicationContext(), R.layout.activity_simple_spinner, currencyList);
                                currencyspinner.setAdapter(currencyAdapter);
                            }
                        });
                    } else {
                        showLongSnackBar(message);
                    }


                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<CurrencyListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public void addProductcategorySpinnerData() {

        hideKeyBoard();

        aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, currencyNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        currencyspinner.setAdapter(aa);
        currencyspinner.setSelection(aa.getPosition(String.valueOf(currencyList)));
        currencyspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                return false;
            }
        });

        currencyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyName = parent.getItemAtPosition(position).toString();
                currencyId = currencyIdList.get(position);
//
                Log.d("catidddd", currencyId);
//
//                retrofitService();
//                getSubCategories();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });

    }

    public class CurrencyAdapter extends ArrayAdapter<CurrencyListResponse.ProductListResponse> {

        private final Context mContext;
        private final List<CurrencyListResponse.ProductListResponse> currencys;
        private final List<CurrencyListResponse.ProductListResponse> currencyAll;

        private final int mLayoutResourceId;

        public CurrencyAdapter(Context context, int resource, List<CurrencyListResponse.ProductListResponse> departments) {
            super(context, resource, departments);
            this.mContext = context;
            this.mLayoutResourceId = resource;
            this.currencys = new ArrayList<>(departments);
            this.currencyAll = new ArrayList<>(departments);

        }

        public int getCount() {

            return currencys.size();

        }

        public CurrencyListResponse.ProductListResponse getItem(int position) {
            return currencys.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.activity_autocomplete_tv_item, parent, false);
                }
                final CurrencyListResponse.ProductListResponse currencyDetails = getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.customerNameTv);
                name.setText(currencyDetails.currency_name);


                name.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            //do stuff here
                        }
                        Log.d("idddddd", currencyDetails.id);
                        Log.i("click text", "kakak");
//                        getAllCustomerDetails(customerDetails.id);
                        currency_id = currencyDetails.id;
                        currencySymbol = currencyDetails.currency;

                        Log.d("newcurrenct", currency_id + "" + currencySymbol);

//                        sessionManager.storeSiteDetails(currencySymbol, currency_id);

                        hideKeyBoard();

                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                public String convertResultToString(Object resultValue) {
                    return ((CurrencyListResponse.ProductListResponse) resultValue).currency_name;
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    List<CurrencyListResponse.ProductListResponse> departmentsSuggestion = new ArrayList<>();
                    if (constraint != null) {
                        Log.d("dsdsddsddsd", "sdsdsdsd");
                        for (CurrencyListResponse.ProductListResponse currency : currencyAll) {
                            if (currency.getCurrency_name().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                                departmentsSuggestion.add(currency);
                            }
                        }
                        filterResults.values = departmentsSuggestion;
                        filterResults.count = departmentsSuggestion.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    currencys.clear();
                    if (results != null && results.count > 0) {
                        // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                        for (Object object : (List<?>) results.values) {
                            if (object instanceof CurrencyListResponse.ProductListResponse) {
                                currencys.add((CurrencyListResponse.ProductListResponse) object);
                            }
                        }
                        notifyDataSetChanged();
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        currencys.addAll(currencyAll);
                        notifyDataSetInvalidated();
                    }
                }
            };
        }
    }

//    public void adminCurrencySpinnerDataForSiteSettings() {
//
//        hideKeyBoard();
//
//
//        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, currencyNameList);
//        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
//        currencyspinner.setAdapter(aa);
//        currencyspinner.setSelection(aa.getPosition(String.valueOf(currencyList)));
//        currencyspinner.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                hideKeyBoard();
//                return false;
//            }
//        });
//
//        currencyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                currencyName = parent.getItemAtPosition(position).toString();
//                currencyId = currencyIdList.get(position);
//
//
//                retrofitService();
////                getSubCategories();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                hideKeyBoard();
//            }
//        });
//
//    }

    private void AdminAddSiteSettingsResponse() {
        showProgressDialog();
        AddSiteRequest request = new AddSiteRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setSite_name(site_name);
        request.setCurrency_id(currencyId);
        request.setSite_url(site_url);
        request.setCopyright(copyright);
        request.setDomain(domain);
        request.setProduct_img("data:image/png;base64," + encodedImage);
        request.setId(siteUpdateId);

        Call<TaxResponse> call = apiService.addSiteData("1234", request);
        Log.d("siterequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<TaxResponse>() {
            @Override
            public void onResponse(Call<TaxResponse> call, Response<TaxResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    TaxResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
                        Log.d("savedInvoice", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                showLongSnackBar(message);

                            }
                        });
                    } else {
                        showLongSnackBar(message);
                    }
                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<TaxResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Log.d("resultcoce", "" + "," + requestCode);
        switch (requestCode) {


            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Log.d("imageeebit", "" + imageBitmap);
                    companyLogoImageView.setImageBitmap(imageBitmap);

                    Bitmap immagex = imageBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    Log.d("rrrrr", encodedImage);
                    Log.d("resultcoce1", "" + "," + requestCode);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    companyLogoImageView.setImageURI(selectedImage);
                    Log.d("resultcoce2", "" + "," + requestCode);
                }
                break;

        }


    }


}
