package com.cygen.cygendineinpos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref, lpref;

    Editor editor, leditor, restaurantEditor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CapitalShowPref";
    private static final String PREF_LOGIN = "CapitalLoginPref";

    private static final String IS_LOGIN = "IsLogged";
    public static final String KEY_CAT_ID = "catId";
    public static final String KEY_SUB_CAT_ID = "subCatId";
    public static final String KEY_BRAND_ID = "brandId";
    public static final String KEY_TAG_ID = "tagId";
    public static final String KEY_TAG_NAME = "tagName";
    public static final String KEY_CAT_NAME = "catName";
    public static final String KEY_SUB_CAT_NAME = "subCatName";
    public static final String KEY_BRAND_NAME = "brandName";
    public static final String KEY_FROM = "from";
    public static final String KEY_FILTER_FROM = "filterfrom";
    public static final String KEY_SEARCH_WORD = "word";

    public static final String KEY_FILTER_PRICE = "filterPrice";
    public static final String KEY_FILTER_BRAND = "filterBrand";
    public static final String KEY_FILTER_CAT = "filterCat";

    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_MOBILE = "mobile";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_SELECTED_LAST_LATITUDE = "latitude";
    public static final String KEY_SELECTED_LAST_LONGITUDE = "longitude";
    public static final String KEY_SELECTED_LAST_LOCALITY = "locality";

    public static final String KEY_USER_LOCALITY = "userlocality";
    public static final String KEY_USER_LAT = "userlatitude";
    public static final String KEY_USER_LONG = "userloongitude";

    public static final String KEY_ADDRESS = "address";
    public static final String KEY_GROCERY_SORT_POS = "positom";
    public static final String KEY_ORDER_TYPE = "orderType";
    public static final String KEY_PAYMENT_TYPE = "paymentType";

    public static final String KEY_OUTLET_ID = "outletId";
    public static final String KEY_BASE_URL = "baseUrl";
    public static final String KEY_CONTACT_PHONE = "contactPhone";
    public static final String KEY_CONTACT_EMAIL = "contactEmail";

    public static final String KEY_BANNER_IMG = "bannerImage";
    public static final String KEY_DISCOUNT = "setDiscount";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        lpref = _context.getSharedPreferences(PREF_LOGIN, PRIVATE_MODE);
        leditor = lpref.edit();
    }

    public void storeLoginDetails(String name, String userId) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
    }

    public void storeOutletDetails(String outletId, String baseUrl, String phone, String email) {
        leditor.putBoolean(IS_LOGIN, true);
        leditor.putString(KEY_OUTLET_ID, outletId);
        leditor.putString(KEY_BASE_URL, baseUrl);
        leditor.putString(KEY_CONTACT_PHONE, phone);
        leditor.putString(KEY_CONTACT_EMAIL, email);
        leditor.commit();
    }

    public void storeRegistrationDetails(String name, String mobile, String email, String password) {
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_MOBILE, mobile);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void storeCategoryDetails(String catId, String catName) {
        editor.putString(KEY_CAT_ID, catId);
        editor.putString(KEY_CAT_NAME, catName);
        editor.commit();
    }

    public void storeSubCategoryDetails(String catId, String catName) {
        editor.putString(KEY_SUB_CAT_ID, catId);
        editor.putString(KEY_SUB_CAT_NAME, catName);
        editor.commit();
    }

    public void storeFilterSortByCategory(String sortPosition) {

        editor.putString(KEY_GROCERY_SORT_POS, sortPosition);
        editor.commit();
    }

    public void setDiscount(String discount) {

        editor.putString(KEY_DISCOUNT, discount);
        editor.commit();
    }

    public void storeBannerImage(String sortPosition) {

        editor.putString(KEY_BANNER_IMG, sortPosition);
        editor.commit();
    }

    public void storeBrandDetails(String brandId, String brandName) {
        editor.putString(KEY_BRAND_ID, brandId);
        editor.putString(KEY_BRAND_NAME, brandName);
        editor.commit();
    }

    public void storeTagIdName(String tagId, String tagName) {
        editor.putString(KEY_TAG_ID, tagId);
        editor.putString(KEY_TAG_NAME, tagName);
        editor.commit();
    }

    public void storeFrom(String from) {
        editor.putString(KEY_FROM, from);
        editor.commit();
    }

    public void storeOrderType(String from) {
        editor.putString(KEY_ORDER_TYPE, from);
        editor.commit();
    }

    public void storePaymentType(String from) {
        editor.putString(KEY_PAYMENT_TYPE, from);
        editor.commit();
    }

    public void storeFilterFrom(String from) {
        editor.putString(KEY_FILTER_FROM, from);
        editor.commit();
    }

    public void storeFilterData(String from, String to) {
        editor.putString(KEY_FILTER_BRAND, from);
        editor.putString(KEY_FILTER_PRICE, to);
        editor.commit();
    }

    public void storeAddress(String address) {
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }

    public void storeSearchWord(String word) {
        editor.putString(KEY_SEARCH_WORD, word);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_CAT_ID, pref.getString(KEY_CAT_ID, null));
        user.put(KEY_CAT_NAME, pref.getString(KEY_CAT_NAME, null));
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        user.put(KEY_BRAND_ID, pref.getString(KEY_BRAND_ID, null));
        user.put(KEY_BRAND_NAME, pref.getString(KEY_BRAND_NAME, null));
        user.put(KEY_SEARCH_WORD, pref.getString(KEY_SEARCH_WORD, null));
        user.put(KEY_FROM, pref.getString(KEY_FROM, null));
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        user.put(KEY_USER_MOBILE, pref.getString(KEY_USER_MOBILE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TAG_ID, pref.getString(KEY_TAG_ID, null));
        user.put(KEY_TAG_NAME, pref.getString(KEY_TAG_NAME, null));
        user.put(KEY_GROCERY_SORT_POS, pref.getString(KEY_GROCERY_SORT_POS, null));

        user.put(KEY_SELECTED_LAST_LATITUDE, pref.getString(KEY_SELECTED_LAST_LATITUDE, null));
        user.put(KEY_SELECTED_LAST_LONGITUDE, pref.getString(KEY_SELECTED_LAST_LONGITUDE, null));
        user.put(KEY_SELECTED_LAST_LOCALITY, pref.getString(KEY_SELECTED_LAST_LOCALITY, null));

        user.put(KEY_USER_LOCALITY, pref.getString(KEY_USER_LOCALITY, null));
        user.put(KEY_USER_LAT, pref.getString(KEY_USER_LAT, null));
        user.put(KEY_USER_LONG, pref.getString(KEY_USER_LONG, null));

        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));

        user.put(KEY_FILTER_PRICE, pref.getString(KEY_FILTER_PRICE, null));
        user.put(KEY_FILTER_BRAND, pref.getString(KEY_FILTER_BRAND, null));

        user.put(KEY_FILTER_FROM, pref.getString(KEY_FILTER_FROM, null));

        user.put(KEY_ORDER_TYPE, pref.getString(KEY_ORDER_TYPE, null));
        user.put(KEY_PAYMENT_TYPE, pref.getString(KEY_PAYMENT_TYPE, null));

        user.put(KEY_OUTLET_ID, lpref.getString(KEY_OUTLET_ID, null));
        user.put(KEY_BASE_URL, lpref.getString(KEY_BASE_URL, null));
        user.put(KEY_CONTACT_PHONE, lpref.getString(KEY_CONTACT_PHONE, null));
        user.put(KEY_CONTACT_EMAIL, lpref.getString(KEY_CONTACT_EMAIL, null));

        user.put(KEY_BANNER_IMG, lpref.getString(KEY_BANNER_IMG, null));
        user.put(KEY_DISCOUNT, pref.getString(KEY_DISCOUNT, null));

        return user;

    }

    public void storeCurrentLocation(String selectedPlaceName, String selectedLatitudes, String selectedLongitudes) {

        editor.putString(KEY_USER_LOCALITY, selectedPlaceName);
        editor.putString(KEY_USER_LAT, selectedLatitudes);
        editor.putString(KEY_USER_LONG, selectedLongitudes);
        editor.commit();
    }

    public void storeLastSelectedLocation(String selectedLatitude, String selectedLongitude, String Locality) {
        editor.putString(KEY_SELECTED_LAST_LATITUDE, selectedLatitude);
        editor.putString(KEY_SELECTED_LAST_LONGITUDE, selectedLongitude);
        editor.putString(KEY_SELECTED_LAST_LOCALITY, Locality);

        editor.commit();
    }

    public void storeSearchData(String cat_id, String sub_cat_name) {

        leditor.putString(KEY_CAT_ID, cat_id);
        leditor.putString(KEY_CAT_NAME, sub_cat_name);
        leditor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLogged() {

        return pref.getBoolean(IS_LOGIN, false);
    }

}
