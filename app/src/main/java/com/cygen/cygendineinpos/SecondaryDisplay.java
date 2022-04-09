package com.cygen.cygendineinpos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cygen.cygendineinpos.Retrofit.RetrofitBuilder;
import com.cygen.cygendineinpos.Retrofit.RetrofitInterface;
import com.cygen.cygendineinpos.RetrofitRequest.CouponsRequest;
import com.cygen.cygendineinpos.RetrofitResponse.BannersResponse;
import com.cygen.cygendineinpos.database.AddOns;
import com.cygen.cygendineinpos.database.DataBaseHandler;
import com.cygen.cygendineinpos.database.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.cygen.cygendineinpos.BaseActivity.round;

public class SecondaryDisplay extends Presentation {

    RecyclerView cutsomercartRecyclerView;

    private static final String TAG_ID = "productId";
    private static final String TAG_NAME = "productName";
    private static final String TAG_QUANTITY = "productQty";
    private static final String TAG_NOTE = "productNote";
    private static final String TAG_PRICE = "productPrice";
    private static final String TAG_ADDON_PRICE = "productAddOnPrice";
    private static final String TAG_ADDON_NAME = "productAddOnName";
    private static final String TAG_ADDON_ID = "productAddOnId";
    private static final String TAG_ADDON_QTY = "productAddOnQty";
    List<Product> products;

    List<String> productIdList;

    DataBaseHandler db;
    int count = 0;
    ArrayList<HashMap<String, String>> productsWithAddOnsList;
    CartListAdapter cartListAdapter;

    Double subTotal = null;
    Double totalAmount = 0.00;
    String tableNumber = null;
    String orderNo = null;
    String orderNumber = null;
    Double orderTotal = 0.00;
    AppCompatTextView quantityTextView, subTotalTextView, discountTextView, taxTextView, grandTotalTextView;

    Context context;

    String discountType = null;

    Double disc = 0.00;
    Double selectedDiscount = 0.00;

    public SecondaryDisplay(Context outerContext, Display display) {
        super(outerContext, display);
    }

    TextView scrollingText;

    AppCompatImageView bannerImageView;

    List<AddOns> addOns;
    List<String> addOnsPrimaryIdList, addOnGroupIdList, addOnKotIdList,addOnNameList, addOnIdList, addOnPriceList, addOnQtyList;
    String addOnId = null, addOnName = null, addOnPrice = null, addOnQty = null, addOnsNotes = null,addOnGroupId = null, addOnKot = null, addOnPrimary = null;;

    SessionManager sessionManager;

    String bannerImage = null;
    String userId = null;
    String outlet_id = null;

    public RetrofitInterface apiService;
    List<BannersResponse.UserListResponse> bannersList;
    Double taxAmount, newtaxAmount;
    String newDiscountForSecond = "0.00";
    String eventName = "Welcome to Spicy Bean Cafe";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        db = new DataBaseHandler(getContext());
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user1 = sessionManager.getUserDetails();
        bannerImage = user1.get(sessionManager.KEY_BANNER_IMG);
        userId = user1.get(sessionManager.KEY_USER_ID);
        outlet_id = user1.get(sessionManager.KEY_OUTLET_ID);
        newDiscountForSecond = user1.get(sessionManager.KEY_DISCOUNT);

        addOnIdList = new ArrayList<>();
        addOnNameList = new ArrayList<>();
        addOnNameList = new ArrayList<>();
        addOnPriceList = new ArrayList<>();
        addOnQtyList = new ArrayList<>();
        productIdList = new ArrayList<>();
        addOnsPrimaryIdList = new ArrayList<>();
        addOnGroupIdList = new ArrayList<>();
        addOnKotIdList = new ArrayList<>();

        productsWithAddOnsList = new ArrayList<>();

        cutsomercartRecyclerView = findViewById(R.id.customerCartRecyclerView);

//        scrollingText.setSelected(true);

        quantityTextView = findViewById(R.id.quantityTextView);
        subTotalTextView = findViewById(R.id.subTotalTextView);
        discountTextView = findViewById(R.id.discountTextView);
        taxTextView = findViewById(R.id.taxTextView);
        grandTotalTextView = findViewById(R.id.grandTotalTextView);

        apiService = RetrofitBuilder.getApiBuilder().create(RetrofitInterface.class);

        bannerImageView = findViewById(R.id.bannerImageView);

        try {
            if (bannerImage.isEmpty() && bannerImage != null) {

            }
        } catch (NullPointerException e) {

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cutsomercartRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        updateList();

        getBannersResponse();

        handlerCall();

        scrollingText = (TextView) this.findViewById(R.id.scrollingtext);
//        scrollingText.setSelected(true);
//        scrollingText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        scrollingText.setText("\t \t \t \t \t \t" + eventName + "\t \t \t \t \t \t");
//        scrollingText.setSingleLine(true);

//        scrollingText.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right));
//
//        TranslateAnimation animation = new TranslateAnimation(0.0f, 1500.0f, 0.0f, 0.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
//
//        animation.setDuration(1500); // animation duration
//        animation.setRepeatCount(1); // animation repeat count
//        animation.setFillAfter(false);
//        scrollingText.startAnimation(animation);//your_view for mine is imageView

        final ScrollViewText scrollTextView = (ScrollViewText) findViewById(R.id.txtPoemProgramatically);
        scrollTextView.startScroll();

        //Pause ACTION_DOWN, resume scroll otherwise.
        scrollTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollTextView.pauseScroll();
                    Log.i(TAG, "Round duration: " + scrollTextView.getRoundDuration());
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    scrollTextView.resumeScroll();
                    return true;
                }
                return false;
            }
        });

    }

    private void getBannersResponse() {

        CouponsRequest request = new CouponsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        retrofit2.Call<BannersResponse> call = apiService.bannersData("1234", request);
        call.enqueue(new Callback<BannersResponse>() {
            @Override
            public void onResponse(Call<BannersResponse> call, Response<BannersResponse> response) {
                if (response.isSuccessful()) {

                    final BannersResponse user = response.body();
                    bannersList = user.bannerResults;

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
                        Log.d("SSbannersResp", "response 112: " + new Gson().toJson(response.body()));

                        Log.d("jhdfjkdf", "called");
//                        Glide.with(getContext()).load(bannersList.get(0).banner_image)
//                                .thumbnail(0.5f)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(bannerImageView);
//                        scrollingText.setText(bannersList.get(0).title_banner);

                    } else {
//                        showLongSnackBar(message);
                    }
                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<BannersResponse> call, Throwable t) {
//                hideProgressDialog();
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
            }
        });
    }

    public void updateList() {

        products = db.getAllCategories();

        productsWithAddOnsList.clear();

        for (Product cn : products) {

            String id = "" + cn.get_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();
            String name = cn.get_product_name();
            String note = cn.get_note();
            String kot = cn.get_kot();

            addOns = new ArrayList<>();
            addOns.clear();
            addOns = db.getAllAddOns(id);

//            Log.d("secondary", id + "," + price + "," + name);

            addOnIdList.clear();
            addOnsPrimaryIdList.clear();
            addOnNameList.clear();
            addOnPriceList.clear();
            addOnQtyList.clear();
            addOnGroupIdList.clear();
            addOnKotIdList.clear();

            if (addOns.size() > 0) {

                for (AddOns addOn : addOns) {
                    String addOnId1 = addOn.get_addOnsId();
                    String addOnName = addOn.get_addOnsName();
                    String addOnPrice = addOn.get_addOnsPrice();
                    String addOnQty = addOn.get_addOnsQty();

//                    Log.d("secondary", addOnId1 + ", name : " + addOnName + ", price : " + addOnPrice + ", qty : " + addOnQty);

                    addOnIdList.add(addOnId1);
                    addOnNameList.add(addOnName);
                    addOnPriceList.add(addOnPrice);
                    addOnQtyList.add(addOnQty);
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < addOnIdList.size(); i++) {
                    if (i == addOnIdList.size() - 1)
                        sb.append(addOnIdList.get(i));
                    else {
                        sb.append(addOnIdList.get(i));
                        sb.append(",");
                    }
                }
                addOnId = sb.toString();

                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < addOnNameList.size(); i++) {
                    if (i == addOnNameList.size() - 1)
                        sb2.append(addOnNameList.get(i));
                    else {
                        sb2.append(addOnNameList.get(i));
                        sb2.append(",");
                    }
                }
                addOnName = sb2.toString();

                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i < addOnPriceList.size(); i++) {
                    if (i == addOnPriceList.size() - 1)
                        sb1.append(addOnPriceList.get(i));
                    else {
                        sb1.append(addOnPriceList.get(i));
                        sb1.append(",");
                    }
                }
                addOnPrice = sb1.toString();

                StringBuilder sb3 = new StringBuilder();
                for (int i = 0; i < addOnQtyList.size(); i++) {
                    if (i == addOnQtyList.size() - 1)
                        sb3.append(addOnQtyList.get(i));
                    else {
                        sb3.append(addOnQtyList.get(i));
                        sb3.append(",");
                    }
                }
                addOnQty = sb3.toString();
            } else {
                addOnId = "";
                addOnName = "";
                addOnPrice = "";
                addOnQty = "";
                addOnGroupId = "";
                addOnKot = "";
                addOnPrimary = "";
            }

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TAG_ID, id);
            map.put(TAG_QUANTITY, productQty);
            map.put(TAG_PRICE, price);
            map.put(TAG_NAME, name);
            map.put(TAG_NOTE, note);
            map.put(TAG_ADDON_ID, addOnId);
            map.put(TAG_ADDON_NAME, addOnName);
            map.put(TAG_ADDON_PRICE, addOnPrice);
            map.put(TAG_ADDON_QTY, addOnQty);

            productsWithAddOnsList.add(map);

        }

        if (!productsWithAddOnsList.isEmpty()) {
            cartListAdapter = new CartListAdapter((Activity) context, productsWithAddOnsList);
            cutsomercartRecyclerView.setAdapter(cartListAdapter);
            Log.d("dsfsdjk", productsWithAddOnsList.size() + "");
        } else {
            productsWithAddOnsList.clear();
            cartListAdapter = new CartListAdapter((Activity) context, productsWithAddOnsList);
            cutsomercartRecyclerView.setAdapter(cartListAdapter);
        }

        subTotal = db.getTotalPrice();

        totalAmount = db.getTotalPrice();
        totalAmount = Double.parseDouble(String.valueOf(round(totalAmount, 2)));
        subTotalTextView.setText("$" + String.format("%.2f", totalAmount));

        taxAmount = 0.00;
        Double discountAmount = 0.00;

        try {
            if (discountType.equalsIgnoreCase("Percentage")) {
                disc = (subTotal * selectedDiscount) / 100;
            } else {
                disc = selectedDiscount;
            }
        } catch (NullPointerException e) {
            disc = 0.00;
        }

        newtaxAmount = subTotal / 10.0;

        taxTextView.setText("$" + String.format("%.2f", newtaxAmount));
        discountTextView.setText("$" + newDiscountForSecond);

        totalAmount = totalAmount - disc + taxAmount;

        totalAmount = Double.parseDouble(String.valueOf(round(totalAmount, 2)));

//        Log.d("totalamt", "" + totalAmount);
        grandTotalTextView.setText("$" + String.format("%.2f", totalAmount));

        quantityTextView.setText("" + db.getAllCategories().size());

    }

    public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

        Context activity;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> resultp = new HashMap<String, String>();

        public CartListAdapter(Activity activity2,
                               ArrayList<HashMap<String, String>> productsList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = productsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext())
                    .inflate(R.layout.cart_recycler_view, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            resultp = data.get(position);

            String quty = resultp.get(SecondaryDisplay.TAG_QUANTITY);
            String pricee = resultp.get(SecondaryDisplay.TAG_PRICE);
            String aPrice = resultp.get(SecondaryDisplay.TAG_ADDON_PRICE);

            int finalQty = Integer.parseInt(quty);
            Double finalPrice = Double.parseDouble(pricee);

            Double result = finalQty * finalPrice;
            result = Double.parseDouble(String.valueOf(round(result, 2)));

            holder.txt_prod_name.setText(resultp.get(SecondaryDisplay.TAG_NAME));
            holder.txt_prod_price.setText(resultp.get(SecondaryDisplay.TAG_PRICE));
            holder.txt_sub_total.setText(String.format("%.2f", result));
            holder.txt_prod_qty.setText(resultp.get(SecondaryDisplay.TAG_QUANTITY));
            holder.txt_prod_note.setText(resultp.get(SecondaryDisplay.TAG_NOTE));

            String addOns = resultp.get(SecondaryDisplay.TAG_ADDON_NAME);
            String addOnsPrice = resultp.get(SecondaryDisplay.TAG_ADDON_PRICE);
            String addOnsId = resultp.get(SecondaryDisplay.TAG_ADDON_ID);
            String addOnsQty = resultp.get(SecondaryDisplay.TAG_ADDON_QTY);

            if (!addOns.isEmpty()) {

                List<String> addOnsList = Arrays.asList(addOns.split(","));
                List<String> addOnsPriceList = Arrays.asList(addOnsPrice.split(","));
                List<String> addOnsIdList = Arrays.asList(addOnsId.split(","));
                List<String> addOnsQtyList = Arrays.asList(addOnsQty.split(","));

                if (addOnsQtyList.size() > 0) {

                    LinearLayoutManager mLayoutManager2 = new LinearLayoutManager((Activity) activity, LinearLayoutManager.VERTICAL, false);
                    holder.cart_addons_recyclerview.setLayoutManager(mLayoutManager2);

                    ProductAdapter cartListAdapter = new ProductAdapter((Activity) activity, addOnsList, addOnsPriceList, addOnsQtyList, addOnsIdList);
                    holder.cart_addons_recyclerview.setAdapter(cartListAdapter);
                    holder.cart_addons_recyclerview.setHasFixedSize(true);
                    cartListAdapter.notifyDataSetChanged();

                }
            }

        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_prod_name, txt_prod_qty, txt_prod_price,
                    txt_sub_total, txt_prod_weight, txt_prod_note;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus, img_delete;
            TextView cart_list_item_addOns, cart_list_item_addOns_price;

            RecyclerView cart_addons_recyclerview;

            public ViewHolder(final View itemView) {
                super(itemView);

                txt_prod_name = (TextView) itemView.findViewById(R.id.itemNameTv);
                txt_prod_price = (TextView) itemView.findViewById(R.id.priceInvoiceTv);
                txt_prod_qty = (TextView) itemView.findViewById(R.id.qtyTv);
                txt_sub_total = (TextView) itemView.findViewById(R.id.subTotalTv);

                img_plus = (ImageView) itemView.findViewById(R.id.cart_plus_img);
                img_minus = (ImageView) itemView.findViewById(R.id.cart_minus_img);
                img_delete = (ImageView) itemView.findViewById(R.id.statusTv);
                txt_prod_note = (TextView) itemView.findViewById(R.id.itemNoteTv);

                cart_addons_recyclerview = (RecyclerView) itemView.findViewById(R.id.cart_addons_recyclerview);

            }

        }

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<String> addOnNamesList, addOnQtysList, addOnPricesList, addOnIdsList;
        RadioButton selected = null;

        public ProductAdapter(Context activity, List<String> namesList, List<String> priceList, List<String> qtyList, List<String> idList) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.addOnNamesList = namesList;
            this.addOnQtysList = qtyList;
            this.addOnPricesList = priceList;
            this.addOnIdsList = idList;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.cart_add_ons_list_item, viewGroup, false);
            ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ProductAdapter.ViewHolder finalHolder = holder;

            finalHolder.addOnNameTextView.setText(addOnNamesList.get(position));
            finalHolder.addOnPriceTextView.setText(addOnPricesList.get(position));
            finalHolder.addOnQtyTextView.setText(addOnQtysList.get(position));

            Log.d("jcvdf", addOnNamesList.get(position));

            int finalQty = Integer.parseInt(addOnQtysList.get(position));
            Double finalPrice = Double.parseDouble(addOnPricesList.get(position));

            Double sum = 0.00;

            sum = finalQty * finalPrice;

            finalHolder.addOnSubTotalTextView.setText("" + String.format("%.2f", sum));

        }

        @Override
        public int getItemCount() {

            return addOnPricesList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView addOnNameTextView, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
            ImageView minusImageView, plusImageView, deleteImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                addOnNameTextView = (TextView) itemView.findViewById(R.id.addOnNameTv);
                addOnPriceTextView = (TextView) itemView.findViewById(R.id.addOnPriceTv);
                addOnQtyTextView = (TextView) itemView.findViewById(R.id.addOnQtyTv);
                addOnSubTotalTextView = (TextView) itemView.findViewById(R.id.addOnSubTotalTv);
                minusImageView = (ImageView) itemView.findViewById(R.id.add_on_cart_minus_img);
                plusImageView = (ImageView) itemView.findViewById(R.id.add_on_cart_plus_img);
                deleteImageView = (ImageView) itemView.findViewById(R.id.addOnDeleteTv);

            }

        }
    }

    final Handler handler = new Handler();

    public void handlerCall() {
        final Runnable r = new Runnable() {
            public void run() {

                sessionManager = new SessionManager(getContext());
                HashMap<String, String> user1 = sessionManager.getUserDetails();
                newDiscountForSecond = user1.get(sessionManager.KEY_DISCOUNT);
                discountTextView.setText("$" + (newDiscountForSecond));

//                totalAmount = totalAmount - Double.parseDouble(newDiscountForSecond) + taxAmount;
//
//                totalAmount = Double.parseDouble(String.valueOf(round(totalAmount, 2)));

                grandTotalTextView.setText("$" + String.format("%.2f", totalAmount - Double.parseDouble(newDiscountForSecond)));
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 10000);
    }


}
