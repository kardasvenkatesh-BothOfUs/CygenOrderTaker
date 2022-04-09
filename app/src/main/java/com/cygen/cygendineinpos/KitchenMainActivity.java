package com.cygen.cygendineinpos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cygen.cygendineinpos.RetrofitRequest.PrintKotRequest;
import com.cygen.cygendineinpos.RetrofitResponse.KotItemStatusResponse;
import com.cygen.cygendineinpos.RetrofitResponse.KotListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OrdersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintBillResponse;
import com.cygen.cygendineinpos.RetrofitResponse.RunningOrdersResponse;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenMainActivity extends BaseActivity {

    RecyclerView kitchenDeliveryOrdersRecyclerview, kitchenDeliveryOrdersRecyclerview1, kitchenDeliveryOrdersRecyclerview2, kitchenDeliveryOrdersRecyclerview3;
    AppCompatTextView noDataTextView, noDataTextView1, noDataTextView2, noDataTextView3;
    String userId = null;
    String outlet_id = null;
    String kitchen_id = null;
    List<OrdersResponse.OrdesListResponse> ordersList;
    List<RunningOrdersResponse.ProductListResponse> dineInList;

    SessionManager sessionManager;
    String name, orderNo;
    List<PrintBillResponse.ProductListResponse> orderList;
    RecyclerView ordersProductsItemsRecyclerView;
    RecyclerView ordersProductsItemsRecyclerView1;
    RecyclerView ordersProductsItemsRecyclerView2;
    RecyclerView ordersProductsItemsRecyclerView3;
    RecyclerView kitchenaddOnsRecyclerView;
    RecyclerView kitchenaddOnsRecyclerView1;
    RecyclerView kitchenaddOnsRecyclerView2;
    RecyclerView kitchenaddOnsRecyclerView3;
    //    ModifyProductAddonsAdapter modifyProductAddonsAdapter;
    KotAddonsEditRecyclerAdapter kotAddonsEditRecyclerAdapter;
    KotAddonsEditRecyclerAdapter1 kotAddonsEditRecyclerAdapter1;
    KotAddonsEditRecyclerAdapter2 kotAddonsEditRecyclerAdapter2;
    KotAddonsEditRecyclerAdapter3 kotAddonsEditRecyclerAdapter3;
    LinearLayout kitchenProductsItemsLayout;
    LinearLayout kitchenProductsItemsLayout1;
    LinearLayout kitchenProductsItemsLayout2;
    LinearLayout kitchenProductsItemsLayout3;

    AppCompatTextView refreshScreenTv;

    String orderId, itemId;

    String sendOrderNo;
    String ITEM_BILL = "";
    String BILL = "";

    //    OrdersRecyclerAdapter LoansListAdapter;
    DineInOrdersRecyclerAdapter dineInOrdersRecyclerAdapter;
    //    ModifyOrdersEditRecyclerAdapter itemsListAdapter;
    private Paint p = new Paint();

    RecyclerView.ViewHolder finalViewHolder = null;

    private BluetoothSocket mBluetoothSocket;
    protected static final String TAG = "TAG";
    BluetoothAdapter mBluetoothAdapter;
    private ProgressDialog mBluetoothConnectProgressDialog;
    BluetoothDevice mBluetoothDevice;

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    AlertDialog alertDialog;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    List<KotListResponse.KotListResponseListResponse> kotListForModify;
    List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> kotItemListForModify;

    KotOrdersEditRecyclerAdapter LoansListAdapter;
    KotOrdersEditRecyclerAdapter1 LoansListAdapter1;
    KotOrdersEditRecyclerAdapter2 LoansListAdapter2;
    KotOrdersEditRecyclerAdapter3 LoansListAdapter3;

    AppCompatTextView deliveryCountTv, takeawayCountTv, tableTopCountTv, dineInCountTv;

    int totalCount = 0;
    int count = 0;

    private final Locale locale = new Locale("id", "ID");
    private final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", locale);
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    public static final int PERMISSION_BLUETOOTH = 1;

    List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> addOnsproductsList;

    List<PrintBillResponse.ProductListResponse> printList;

    String printOrderNo, sessionPrintOrderNo;

    String userDetails = null;
    String printText = null;
    String textName = null;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_kitchen_main);

        sessionManager = new SessionManager(getApplicationContext());

        retrofitService();

        HashMap<String, String> user1 = sessionManager.getUserDetails();
        name = user1.get(sessionManager.KEY_USER_NAME);
        userId = user1.get(sessionManager.KEY_USER_ID);
        outlet_id = user1.get(sessionManager.KEY_OUTLET_ID);

        refreshScreenTv = findViewById(R.id.refreshScreenTv);

        refreshScreenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KitchenMainActivity.this, KitchenMainActivity.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        dineInCountTv = findViewById(R.id.dineInCountTv);
        tableTopCountTv = findViewById(R.id.tableTopCountTv);
        takeawayCountTv = findViewById(R.id.takeawayCountTv);
        deliveryCountTv = findViewById(R.id.deliveryCountTv);

        kitchenProductsItemsLayout = findViewById(R.id.kitchenProductsItemsLayout);

        kitchenDeliveryOrdersRecyclerview = findViewById(R.id.kitchenDeliveryOrdersRecyclerview);
        noDataTextView = findViewById(R.id.noDataTextView);

        kitchenDeliveryOrdersRecyclerview1 = findViewById(R.id.kitchenDeliveryOrdersRecyclerview1);
        noDataTextView1 = findViewById(R.id.noDataTextView1);

        kitchenDeliveryOrdersRecyclerview2 = findViewById(R.id.kitchenDeliveryOrdersRecyclerview2);
        noDataTextView2 = findViewById(R.id.noDataTextView2);

        kitchenDeliveryOrdersRecyclerview3 = findViewById(R.id.kitchenDeliveryOrdersRecyclerview3);
        noDataTextView3 = findViewById(R.id.noDataTextView3);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        kitchenDeliveryOrdersRecyclerview.setLayoutManager(mLayoutManager);

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        kitchenDeliveryOrdersRecyclerview1.setLayoutManager(mLayoutManager1);

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        kitchenDeliveryOrdersRecyclerview2.setLayoutManager(mLayoutManager2);

        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        kitchenDeliveryOrdersRecyclerview3.setLayoutManager(mLayoutManager3);


//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        kitchenDeliveryOrdersRecyclerview.setLayoutManager(mLayoutManager);
        if (CommonUtilities.checkConn(getApplicationContext())) {

            getOrderDetailsDineIn();
            getOrderDetailsTableTop();
            getOrderDetailsTakeAway();
            getOrderDetailsDelivery();

        } else {

        }

        findViewById(R.id.backScreenTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        handlerCall();

    }

    private void getOrderDetailsDineIn() {

        //showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_type("4");

        Call<KotListResponse> call = apiService.allKotList("1234", request);

        Log.d("modfyrequestzzz", new Gson().toJson(request));
        call.enqueue(new Callback<KotListResponse>() {
            @Override
            public void onResponse(Call<KotListResponse> call, Response<KotListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final KotListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("orderResp222", "response 1112: " + new Gson().toJson(response.body()));
                        kotListForModify = user.kotResults;
                        Log.d("lotlisttsized", "" + kotListForModify.size());

                        dineInCountTv.setText("" + kotListForModify.size());

                        int finalSize = kotListForModify.size() - 1;
//
//                        Log.d("finalSize", "" + finalSize);
//
//                        printOrderNo = kotListForModify.get(finalSize).getSales_id();
//
//                        Log.d("sesssss", "" + sessionPrintOrderNo + "," + printOrderNo);
//
//                        if (sessionPrintOrderNo.equalsIgnoreCase(printOrderNo)) {
//
//                        } else {
//                            getPrintBillDetails1();
//                        }


                        if (kotListForModify.size() > 0) {
                            kotItemListForModify = kotListForModify.get(0).itemsResults;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    DineInOrdersRecyclerAdapter LoansListAdapter = new DineInOrdersRecyclerAdapter(KitchenMainActivity.this, kotListForModify);
                                    kitchenDeliveryOrdersRecyclerview.setAdapter(LoansListAdapter);
                                    kitchenDeliveryOrdersRecyclerview.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();


                                }
                            });
                        }


                    } else {
                        showLongSnackBar(message);
                        noDataTextView.setVisibility(View.VISIBLE);
                        kitchenDeliveryOrdersRecyclerview.setVisibility(View.GONE);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class DineInOrdersRecyclerAdapter extends RecyclerView.Adapter<DineInOrdersRecyclerAdapter.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse masterData2;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data3;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData3;
        Boolean selected;
        int row_index;

        public DineInOrdersRecyclerAdapter(Activity activity2,
                                           List<KotListResponse.KotListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText(masterData2.getOrder_numbers());
            holder.categoryTypeTv.setText(" Dine In");
            holder.customerTv.setText(masterData2.customer);
            holder.tableNoTv.setText(masterData2.getTable_number());
            holder.orderedTimeTv.setText(masterData2.getAdd_time());

            data3 = masterData2.getitemsResults();
            final ViewHolder finalHolder = holder;


            finalHolder.viewBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    totalCount = 0;

                    masterData2 = data2.get(position);
                    data3 = masterData2.getitemsResults();


                    Log.d("sizeeee", "" + masterData2.getitemsResults().size());

                    finalHolder.viewBtn1.setVisibility(View.GONE);

                    finalHolder.kitchenProductsItemsLayout.setVisibility(View.VISIBLE);

                    LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                    holder.ordersProductsItemsRecyclerView.setLayoutManager(mLayoutManager2);

                    LoansListAdapter = new KotOrdersEditRecyclerAdapter(KitchenMainActivity.this, data3);
                    holder.ordersProductsItemsRecyclerView.setAdapter(LoansListAdapter);
                    holder.ordersProductsItemsRecyclerView.setNestedScrollingEnabled(false);
                    LoansListAdapter.notifyDataSetChanged();

                    for (int i = 0; i < masterData2.getitemsResults().size(); i++) {
                        totalCount = totalCount + Integer.parseInt(data3.get(i).getFood_status());
                    }

                    if (totalCount == masterData2.getitemsResults().size()) {
                        holder.clearItemBtn.setVisibility(View.VISIBLE);
                    }

                    if (masterData2.getitemsResults() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                masterData2 = data2.get(position);
                                data3 = masterData2.getitemsResults();

                                Log.d("kotnumber", masterData2.getKot_number());


//                                getOrderDetailsDineIn();


                            }
                        });
                    }

                }
            });


            holder.closeBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            holder.kitchenProductsItemsLayout.setVisibility(View.GONE);
                            holder.viewBtn1.setVisibility(View.VISIBLE);
                        }
                    });

            holder.printBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            Log.d("ckicling", "clining");
                            sendOrderNo = masterData2.getSales_id();
                            Log.d("sendordeee", sendOrderNo);

                            getPrintKitchenBillDetails1();

                        }
                    });


            holder.clearItemBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);

                            getKOTItemRemoveResponse(masterData2.getSales_id(), masterData2.getKot_number());

                            getOrderDetailsDineIn();

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;

            AppCompatTextView viewBtn1, categoryTypeTv, customerTv, closeBtn, printBtn, tableNoTv, clearItemBtn;
            AppCompatTextView billNowButton;
            LinearLayout kitchenProductsItemsLayout;

            RecyclerView ordersProductsItemsRecyclerView;
            AppCompatTextView orderedTimeTv;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.ordersProductsItemsRecyclerView = itemView.findViewById(R.id.ordersProductsItemsRecyclerView);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.viewBtn1 = itemView.findViewById(R.id.viewBtn1);
                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
                this.customerTv = itemView.findViewById(R.id.customerTv);
                this.kitchenProductsItemsLayout = itemView.findViewById(R.id.kitchenProductsItemsLayout);
                this.closeBtn = itemView.findViewById(R.id.closeBtn);
                this.printBtn = itemView.findViewById(R.id.printBtn);
                this.clearItemBtn = itemView.findViewById(R.id.clearItemBtn);
                this.tableNoTv = itemView.findViewById(R.id.tableNoTv);
                this.orderedTimeTv = itemView.findViewById(R.id.orderedTimeTv);

            }

        }

    }

    public class KotOrdersEditRecyclerAdapter extends RecyclerView.Adapter<KotOrdersEditRecyclerAdapter.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data;
        KotListResponse.KotListResponseListResponse masterData;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data1;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData1;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotOrdersEditRecyclerAdapter(Activity activity2,
                                            List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_products_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());

            if (masterData1.getFood_status().equalsIgnoreCase("1")) {

                holder.productItemCheckBox.setChecked(true);
                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }
//            else {
//                holder.productItemCheckBox.setChecked(false);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | ~Paint.STRIKE_THRU_TEXT_FLAG);
//
//            }


            data2 = masterData1.addonsResults;

            Log.d("daaaaaas", masterData1.getFood_status());
//
//
////            nameLists.clear();
////            qtysList.clear();
//            priceList.clear();
//
//            for (int i = 0; i < data2.size(); i++) {
//                masterData1 = data1.get(i);
//                priceList.add(masterData2.getPrice());
////                nameLists.add(masterData2.getAddon_name());
////                qtysList.add(masterData2.getQty());
//            }

            if (masterData1.getaddonsResults() != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        kitchenaddOnsRecyclerView.setLayoutManager(mLayoutManager2);

                        kotAddonsEditRecyclerAdapter = new KotAddonsEditRecyclerAdapter(KitchenMainActivity.this, data2);
                        kitchenaddOnsRecyclerView.setAdapter(kotAddonsEditRecyclerAdapter);
                        kitchenaddOnsRecyclerView.setNestedScrollingEnabled(false);
                        kotAddonsEditRecyclerAdapter.notifyDataSetChanged();


                    }
                });
            }

            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    masterData1 = data1.get(position);
                    if (holder.productItemCheckBox.isChecked()) {
                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "1";

                        sendOrderNo = masterData1.getSales_id();


                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);

                        getOrderDetailsDineIn();

                    } else {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "0";

                        sendOrderNo = masterData1.getSales_id();

                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);

                        getOrderDetailsDineIn();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenOrdersProductsItemsTV, txt_prod_qty, txt_prod_price, txt_sub_total, txt_prod_weight;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus;
            AppCompatTextView modifystatusTv;
            TextView cart_list_item_addOns, cart_list_item_addOns_price;

            AppCompatCheckBox productItemCheckBox;


            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);

                kitchenaddOnsRecyclerView = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView);
                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);

            }
        }

    }

    public class KotAddonsEditRecyclerAdapter extends RecyclerView.Adapter<KotAddonsEditRecyclerAdapter.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotAddonsEditRecyclerAdapter(Activity activity2, List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub

            activity = activity2;
            data2 = masterList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ViewHolder finalHolder = holder;

            masterData2 = data2.get(position);

//            Log.d("masterdddddd", masterData2.getAddon_name());

            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getQty() + " X " + masterData2.getAddon_name());


        }


        @Override
        public int getItemCount() {

            return data2.size();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenAddOnsItemTv, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
            ImageView minusImageView, plusImageView, deleteImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);


            }

        }
    }

    //////table toppp
    private void getOrderDetailsTableTop() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_type("3");

        Call<KotListResponse> call = apiService.allKotList("1234", request);

        Log.d("modfyrequestzzz", new Gson().toJson(request));
        call.enqueue(new Callback<KotListResponse>() {
            @Override
            public void onResponse(Call<KotListResponse> call, Response<KotListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final KotListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("orderResp222", "response 1112: " + new Gson().toJson(response.body()));
                        kotListForModify = user.kotResults;
                        Log.d("lotlisttsize", "" + kotListForModify.size());

                        tableTopCountTv.setText("" + kotListForModify.size());
                        if (kotListForModify.size() > 0) {

                            kotItemListForModify = kotListForModify.get(0).itemsResults;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    DineInOrdersRecyclerAdapter1 LoansListAdapter = new DineInOrdersRecyclerAdapter1(KitchenMainActivity.this, kotListForModify);
                                    kitchenDeliveryOrdersRecyclerview1.setAdapter(LoansListAdapter);
                                    kitchenDeliveryOrdersRecyclerview1.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();


                                }
                            });
                        }


                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class DineInOrdersRecyclerAdapter1 extends RecyclerView.Adapter<DineInOrdersRecyclerAdapter1.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse masterData2;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data3;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData3;
        Boolean selected;
        int row_index;

        public DineInOrdersRecyclerAdapter1(Activity activity2,
                                            List<KotListResponse.KotListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_list_item1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText(masterData2.getOrder_numbers());
            holder.categoryTypeTv.setText(" TableTop");
            holder.customerTv.setText(masterData2.customer);
//            holder.tableNoTv.setText(masterData2.getTable_number());

            data3 = masterData2.getitemsResults();

            final ViewHolder finalHolder = holder;

            finalHolder.viewBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    masterData2 = data2.get(position);
                    data3 = masterData2.getitemsResults();

                    Log.d("kotnumber", masterData2.getKot_number());

                    finalHolder.viewBtn2.setVisibility(View.GONE);

                    finalHolder.kitchenProductsItemsLayout1.setVisibility(View.VISIBLE);

                    LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                    holder.ordersProductsItemsRecyclerView1.setLayoutManager(mLayoutManager2);

                    LoansListAdapter1 = new KotOrdersEditRecyclerAdapter1(KitchenMainActivity.this, data3);
                    holder.ordersProductsItemsRecyclerView1.setAdapter(LoansListAdapter1);
                    holder.ordersProductsItemsRecyclerView1.setNestedScrollingEnabled(false);
                    LoansListAdapter1.notifyDataSetChanged();

                    for (int i = 0; i < masterData2.getitemsResults().size(); i++) {
                        totalCount = totalCount + Integer.parseInt(data3.get(i).getFood_status());
                    }


                    if (totalCount == masterData2.getitemsResults().size()) {
                        holder.clearItemBtn.setVisibility(View.VISIBLE);
                    }

                    if (masterData2.getitemsResults() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                masterData2 = data2.get(position);
                                data3 = masterData2.getitemsResults();

                                Log.d("kotnumber", masterData2.getKot_number());


                            }
                        });
                    }

                }
            });


            holder.closeBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            holder.kitchenProductsItemsLayout1.setVisibility(View.GONE);
                            holder.viewBtn2.setVisibility(View.VISIBLE);

                        }
                    });

            holder.printBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            Log.d("ckicling", "clining");
                            sendOrderNo = masterData2.getSales_id();
                            Log.d("sendordeee", sendOrderNo);

                            getPrintKitchenBillDetails1();

                        }
                    });

            holder.clearItemBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);

                            getKOTItemRemoveResponse(masterData2.getSales_id(), masterData2.getKot_number());


                        }
                    });


        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;

            AppCompatTextView viewBtn2, categoryTypeTv, customerTv, closeBtn, printBtn, tableNoTv, clearItemBtn;
            AppCompatTextView billNowButton;
            LinearLayout kitchenProductsItemsLayout1;

            RecyclerView ordersProductsItemsRecyclerView1;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.ordersProductsItemsRecyclerView1 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView1);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.viewBtn2 = itemView.findViewById(R.id.viewBtn2);
                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
                this.customerTv = itemView.findViewById(R.id.customerTv);
                this.kitchenProductsItemsLayout1 = itemView.findViewById(R.id.kitchenProductsItemsLayout1);
                this.closeBtn = itemView.findViewById(R.id.closeBtn);
                this.printBtn = itemView.findViewById(R.id.printBtn);
                this.clearItemBtn = itemView.findViewById(R.id.clearItemBtn);

            }

        }

    }

    public class KotOrdersEditRecyclerAdapter1 extends RecyclerView.Adapter<KotOrdersEditRecyclerAdapter1.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data;
        KotListResponse.KotListResponseListResponse masterData;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data1;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData1;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotOrdersEditRecyclerAdapter1(Activity activity2,
                                             List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_products_item1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());


            if (masterData1.getFood_status().equalsIgnoreCase("1")) {

                holder.productItemCheckBox.setChecked(true);
                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }


            data2 = masterData1.addonsResults;

            Log.d("daaaaaa", "" + data2);
//
//
////            nameLists.clear();
////            qtysList.clear();
//            priceList.clear();
//
//            for (int i = 0; i < data2.size(); i++) {
//                masterData1 = data1.get(i);
//                priceList.add(masterData2.getPrice());
////                nameLists.add(masterData2.getAddon_name());
////                qtysList.add(masterData2.getQty());
//            }

            if (masterData1.getaddonsResults() != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        kitchenaddOnsRecyclerView1.setLayoutManager(mLayoutManager2);

                        kotAddonsEditRecyclerAdapter1 = new KotAddonsEditRecyclerAdapter1(KitchenMainActivity.this, data2);
                        kitchenaddOnsRecyclerView1.setAdapter(kotAddonsEditRecyclerAdapter1);
                        kitchenaddOnsRecyclerView1.setNestedScrollingEnabled(false);
                        kotAddonsEditRecyclerAdapter1.notifyDataSetChanged();


                    }
                });
            }

            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    masterData1 = data1.get(position);
                    if (holder.productItemCheckBox.isChecked()) {
                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "1";

                        sendOrderNo = masterData1.getSales_id();


                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);

                        getOrderDetailsTableTop();

                    } else {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "0";

                        sendOrderNo = masterData1.getSales_id();

                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
                        getOrderDetailsTableTop();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenOrdersProductsItemsTV, txt_prod_qty, txt_prod_price, txt_sub_total, txt_prod_weight;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus;
            AppCompatTextView modifystatusTv;
            TextView cart_list_item_addOns, cart_list_item_addOns_price;

            AppCompatCheckBox productItemCheckBox;


            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);

                kitchenaddOnsRecyclerView1 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView1);
                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);

            }
        }

    }

    public class KotAddonsEditRecyclerAdapter1 extends RecyclerView.Adapter<KotAddonsEditRecyclerAdapter1.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotAddonsEditRecyclerAdapter1(Activity activity2, List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub

            activity = activity2;
            data2 = masterList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ViewHolder finalHolder = holder;

            masterData2 = data2.get(position);

//            Log.d("masterdddddd", masterData2.getAddon_name());

            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getQty() + " X " + masterData2.getAddon_name());


        }


        @Override
        public int getItemCount() {

            return data2.size();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenAddOnsItemTv, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
            ImageView minusImageView, plusImageView, deleteImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);


            }

        }
    }

    ///// takeaway
    private void getOrderDetailsTakeAway() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_type("1");

        Call<KotListResponse> call = apiService.allKotList("1234", request);

        Log.d("modfyrequestzzz", new Gson().toJson(request));
        call.enqueue(new Callback<KotListResponse>() {
            @Override
            public void onResponse(Call<KotListResponse> call, Response<KotListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final KotListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("orderResp222", "response 1112: " + new Gson().toJson(response.body()));
                        kotListForModify = user.kotResults;
                        Log.d("lotlisttsize", "" + kotListForModify.size());

                        takeawayCountTv.setText("" + kotListForModify.size());
                        if (kotListForModify.size() > 0) {
                            kotItemListForModify = kotListForModify.get(0).itemsResults;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    DineInOrdersRecyclerAdapter2 LoansListAdapter = new DineInOrdersRecyclerAdapter2(KitchenMainActivity.this, kotListForModify);
                                    kitchenDeliveryOrdersRecyclerview2.setAdapter(LoansListAdapter);
                                    kitchenDeliveryOrdersRecyclerview2.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();


                                }
                            });
                        }


                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class DineInOrdersRecyclerAdapter2 extends RecyclerView.Adapter<DineInOrdersRecyclerAdapter2.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse masterData2;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data3;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData3;
        Boolean selected;
        int row_index;

        public DineInOrdersRecyclerAdapter2(Activity activity2,
                                            List<KotListResponse.KotListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_list_item2, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText(masterData2.getOrder_numbers());
            holder.categoryTypeTv.setText(" TakeAway");
            holder.customerTv.setText(masterData2.customer);
//            holder.tableNoTv.setText(masterData2.getTable_number());

            data3 = masterData2.getitemsResults();

            final ViewHolder finalHolder = holder;

            finalHolder.viewBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    masterData2 = data2.get(position);
                    data3 = masterData2.getitemsResults();

                    Log.d("kotnumber", "" + masterData2.getitemsResults().size());

                    finalHolder.viewBtn3.setVisibility(View.GONE);

                    finalHolder.kitchenProductsItemsLayout2.setVisibility(View.VISIBLE);

                    LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                    holder.ordersProductsItemsRecyclerView2.setLayoutManager(mLayoutManager2);

                    LoansListAdapter2 = new KotOrdersEditRecyclerAdapter2(KitchenMainActivity.this, data3);
                    holder.ordersProductsItemsRecyclerView2.setAdapter(LoansListAdapter2);
                    holder.ordersProductsItemsRecyclerView2.setNestedScrollingEnabled(false);
                    LoansListAdapter2.notifyDataSetChanged();

                    for (int i = 0; i < masterData2.getitemsResults().size(); i++) {
                        data3 = masterData2.getitemsResults();
                        totalCount = totalCount + Integer.parseInt(data3.get(i).getFood_status());

                        Log.d("totalcount", "" + totalCount + ",,,," + masterData2.getitemsResults().size());
                    }


                    if (totalCount == masterData2.getitemsResults().size()) {
                        Log.d("totalcount1", "" + totalCount + ",,," + masterData2.getitemsResults().size());
                        holder.clearItemBtn.setVisibility(View.VISIBLE);
                    }

                    if (masterData2.getitemsResults() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                masterData2 = data2.get(position);
                                data3 = masterData2.getitemsResults();

                                Log.d("kotnumber", masterData2.getKot_number());


                            }
                        });
                    }

//


                }
            });


            holder.closeBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            holder.kitchenProductsItemsLayout2.setVisibility(View.GONE);
                            holder.viewBtn3.setVisibility(View.VISIBLE);

                        }
                    });

            holder.printBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            Log.d("ckicling", "clining");
                            sendOrderNo = masterData2.getSales_id();
                            Log.d("sendordeee", sendOrderNo);

//                            getKitchenPrintBillDetails(sendOrderNo);

                            getPrintKitchenBillDetails1();

                        }
                    });

            holder.clearItemBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);

                            getKOTItemRemoveResponse(masterData2.getSales_id(), masterData2.getKot_number());


                        }
                    });


        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;

            AppCompatTextView viewBtn3, categoryTypeTv, customerTv, closeBtn, printBtn, tableNoTv, clearItemBtn;
            AppCompatTextView billNowButton;
            LinearLayout kitchenProductsItemsLayout2;

            RecyclerView ordersProductsItemsRecyclerView2;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.ordersProductsItemsRecyclerView2 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView2);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.viewBtn3 = itemView.findViewById(R.id.viewBtn3);
                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
                this.customerTv = itemView.findViewById(R.id.customerTv);
                this.kitchenProductsItemsLayout2 = itemView.findViewById(R.id.kitchenProductsItemsLayout2);
                this.closeBtn = itemView.findViewById(R.id.closeBtn);
                this.printBtn = itemView.findViewById(R.id.printBtn);
                this.clearItemBtn = itemView.findViewById(R.id.clearItemBtn);

            }

        }

    }

    public class KotOrdersEditRecyclerAdapter2 extends RecyclerView.Adapter<KotOrdersEditRecyclerAdapter2.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data;
        KotListResponse.KotListResponseListResponse masterData;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data1;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData1;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotOrdersEditRecyclerAdapter2(Activity activity2,
                                             List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_products_item2, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());


            if (masterData1.getFood_status().equalsIgnoreCase("1")) {

                holder.productItemCheckBox.setChecked(true);
                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }

            data2 = masterData1.addonsResults;

            Log.d("daaaaaa", "" + data2);
//
//
////            nameLists.clear();
////            qtysList.clear();
//            priceList.clear();
//
//            for (int i = 0; i < data2.size(); i++) {
//                masterData1 = data1.get(i);
//                priceList.add(masterData2.getPrice());
////                nameLists.add(masterData2.getAddon_name());
////                qtysList.add(masterData2.getQty());
//            }

            if (masterData1.getaddonsResults() != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        kitchenaddOnsRecyclerView2.setLayoutManager(mLayoutManager2);

                        kotAddonsEditRecyclerAdapter2 = new KotAddonsEditRecyclerAdapter2(KitchenMainActivity.this, data2);
                        kitchenaddOnsRecyclerView2.setAdapter(kotAddonsEditRecyclerAdapter2);
                        kitchenaddOnsRecyclerView2.setNestedScrollingEnabled(false);
                        kotAddonsEditRecyclerAdapter2.notifyDataSetChanged();


                    }
                });
            }

            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    masterData1 = data1.get(position);
                    if (holder.productItemCheckBox.isChecked()) {
                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "1";

                        sendOrderNo = masterData1.getSales_id();

                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);

                        getOrderDetailsTakeAway();

                    } else {

                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "0";

                        sendOrderNo = masterData1.getSales_id();

                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
                        getOrderDetailsTakeAway();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenOrdersProductsItemsTV, txt_prod_qty, txt_prod_price, txt_sub_total, txt_prod_weight;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus;
            AppCompatTextView modifystatusTv;
            TextView cart_list_item_addOns, cart_list_item_addOns_price;

            AppCompatCheckBox productItemCheckBox;


            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);

                kitchenaddOnsRecyclerView2 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView2);
                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);

            }
        }

    }

    public class KotAddonsEditRecyclerAdapter2 extends RecyclerView.Adapter<KotAddonsEditRecyclerAdapter2.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotAddonsEditRecyclerAdapter2(Activity activity2, List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub

            activity = activity2;
            data2 = masterList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items2, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ViewHolder finalHolder = holder;

            masterData2 = data2.get(position);

//            Log.d("masterdddddd", masterData2.getAddon_name());

            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getQty() + " X " + masterData2.getAddon_name());


        }


        @Override
        public int getItemCount() {

            return data2.size();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenAddOnsItemTv, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
            ImageView minusImageView, plusImageView, deleteImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);


            }

        }
    }

    private void getOrderDetailsDelivery() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_type("2");

        Call<KotListResponse> call = apiService.allKotList("1234", request);

        Log.d("modfyrequestzzz", new Gson().toJson(request));
        call.enqueue(new Callback<KotListResponse>() {
            @Override
            public void onResponse(Call<KotListResponse> call, Response<KotListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final KotListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("orderResp222", "response 1112: " + new Gson().toJson(response.body()));
                        kotListForModify = user.kotResults;
                        Log.d("lotlisttsize", "" + kotListForModify.size());

                        deliveryCountTv.setText("" + kotListForModify.size());

                        if (kotListForModify.size() != 0) {
                            kotItemListForModify = kotListForModify.get(0).itemsResults;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    DineInOrdersRecyclerAdapter3 LoansListAdapter = new DineInOrdersRecyclerAdapter3(KitchenMainActivity.this, kotListForModify);
                                    kitchenDeliveryOrdersRecyclerview3.setAdapter(LoansListAdapter);
                                    kitchenDeliveryOrdersRecyclerview3.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();


                                }
                            });
                        }


                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class DineInOrdersRecyclerAdapter3 extends RecyclerView.Adapter<DineInOrdersRecyclerAdapter3.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse masterData2;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data3;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData3;
        Boolean selected;
        int row_index;

        public DineInOrdersRecyclerAdapter3(Activity activity2,
                                            List<KotListResponse.KotListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_list_item3, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText(masterData2.getOrder_numbers());
            holder.categoryTypeTv.setText(" Delivery");
            holder.customerTv.setText(masterData2.customer);
//            holder.tableNoTv.setText(masterData2.getTable_number());

            data3 = masterData2.getitemsResults();

            final ViewHolder finalHolder = holder;

            finalHolder.viewBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    masterData2 = data2.get(position);
                    data3 = masterData2.getitemsResults();

                    Log.d("kotnumber", masterData2.getKot_number());

                    finalHolder.viewBtn4.setVisibility(View.GONE);

                    finalHolder.kitchenProductsItemsLayout3.setVisibility(View.VISIBLE);

                    LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                    holder.ordersProductsItemsRecyclerView3.setLayoutManager(mLayoutManager2);

                    LoansListAdapter3 = new KotOrdersEditRecyclerAdapter3(KitchenMainActivity.this, data3);
                    holder.ordersProductsItemsRecyclerView3.setAdapter(LoansListAdapter3);
                    holder.ordersProductsItemsRecyclerView3.setNestedScrollingEnabled(false);
                    LoansListAdapter3.notifyDataSetChanged();

                    for (int i = 0; i < masterData2.getitemsResults().size(); i++) {
                        totalCount = totalCount + Integer.parseInt(data3.get(i).getFood_status());
                    }


                    if (totalCount == masterData2.getitemsResults().size()) {
                        holder.clearItemBtn.setVisibility(View.VISIBLE);
                    }


                    if (masterData2.getitemsResults() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                masterData2 = data2.get(position);
                                data3 = masterData2.getitemsResults();

                                Log.d("kotnumber", masterData2.getKot_number());


                            }
                        });
                    }

//


                }
            });


            holder.closeBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            holder.kitchenProductsItemsLayout3.setVisibility(View.GONE);
                            holder.viewBtn4.setVisibility(View.VISIBLE);

                        }
                    });

            holder.printBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            Log.d("ckicling", "clining");
                            sendOrderNo = masterData2.getSales_id();
                            Log.d("sendordeee", sendOrderNo);

                            getPrintKitchenBillDetails1();

                        }
                    });

            holder.clearItemBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);

                            getKOTItemRemoveResponse(masterData2.getSales_id(), masterData2.getKot_number());


                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;

            AppCompatTextView viewBtn4, categoryTypeTv, customerTv, closeBtn, printBtn, tableNoTv, clearItemBtn;
            AppCompatTextView billNowButton;
            LinearLayout kitchenProductsItemsLayout3;

            RecyclerView ordersProductsItemsRecyclerView3;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.ordersProductsItemsRecyclerView3 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView3);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.viewBtn4 = itemView.findViewById(R.id.viewBtn4);
                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
                this.customerTv = itemView.findViewById(R.id.customerTv);
                this.kitchenProductsItemsLayout3 = itemView.findViewById(R.id.kitchenProductsItemsLayout3);
                this.closeBtn = itemView.findViewById(R.id.closeBtn);
                this.printBtn = itemView.findViewById(R.id.printBtn);
                this.clearItemBtn = itemView.findViewById(R.id.clearItemBtn);

            }

        }

    }

    public class KotOrdersEditRecyclerAdapter3 extends RecyclerView.Adapter<KotOrdersEditRecyclerAdapter3.ViewHolder> {

        Context activity;

        List<KotListResponse.KotListResponseListResponse> data;
        KotListResponse.KotListResponseListResponse masterData;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data1;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData1;

        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotOrdersEditRecyclerAdapter3(Activity activity2,
                                             List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_kitchen_orders_products_item3, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());

            if (masterData1.getFood_status().equalsIgnoreCase("1")) {

                holder.productItemCheckBox.setChecked(true);
                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }


            data2 = masterData1.addonsResults;

            Log.d("daaaaaa", "" + data2);
//
//
////            nameLists.clear();
////            qtysList.clear();
//            priceList.clear();
//
//            for (int i = 0; i < data2.size(); i++) {
//                masterData1 = data1.get(i);
//                priceList.add(masterData2.getPrice());
////                nameLists.add(masterData2.getAddon_name());
////                qtysList.add(masterData2.getQty());
//            }

            if (masterData1.getaddonsResults() != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        kitchenaddOnsRecyclerView3.setLayoutManager(mLayoutManager2);

                        kotAddonsEditRecyclerAdapter = new KotAddonsEditRecyclerAdapter(KitchenMainActivity.this, data2);
                        kitchenaddOnsRecyclerView3.setAdapter(kotAddonsEditRecyclerAdapter);
                        kitchenaddOnsRecyclerView3.setNestedScrollingEnabled(false);
                        kotAddonsEditRecyclerAdapter.notifyDataSetChanged();


                    }
                });
            }

            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    masterData1 = data1.get(position);
                    if (holder.productItemCheckBox.isChecked()) {
                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "1";

                        sendOrderNo = masterData1.getSales_id();


                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
                        getOrderDetailsDelivery();

                    } else {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        orderId = masterData1.getItem_id();
                        itemId = masterData1.getId();
                        String checkedStatus = "0";

                        sendOrderNo = masterData1.getSales_id();

                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);

                        getOrderDetailsDelivery();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenOrdersProductsItemsTV, txt_prod_qty, txt_prod_price, txt_sub_total, txt_prod_weight;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus;
            AppCompatTextView modifystatusTv;
            TextView cart_list_item_addOns, cart_list_item_addOns_price;

            AppCompatCheckBox productItemCheckBox;


            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);

                kitchenaddOnsRecyclerView3 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView3);
                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);

            }
        }

    }

    public class KotAddonsEditRecyclerAdapter3 extends RecyclerView.Adapter<KotAddonsEditRecyclerAdapter3.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;

        public KotAddonsEditRecyclerAdapter3(Activity activity2, List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub

            activity = activity2;
            data2 = masterList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ViewHolder finalHolder = holder;

            masterData2 = data2.get(position);

//            Log.d("masterdddddd", masterData2.getAddon_name());

            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getQty() + " X " + masterData2.getAddon_name());


        }


        @Override
        public int getItemCount() {

            return data2.size();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView kitchenAddOnsItemTv, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
            ImageView minusImageView, plusImageView, deleteImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);


            }

        }
    }

//    private void getOrderDetails1() {
//
//        OrdersRequest request = new OrdersRequest();
//        request.setUser_id(userId);
//        request.setOutlet_id(outlet_id);
//        request.setOrder_type("3");
//        request.setKitchen_id("1");
//
//        Log.d("ordersRequest33", request + new Gson().toJson(request));
//        retrofit2.Call<OrdersResponse> call = apiService.orderDetails("1234", request);
//
//        call.enqueue(new Callback<OrdersResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<OrdersResponse> call, Response<OrdersResponse> response) {
//
//                if (response.isSuccessful()) {
//
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    OrdersResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
//                    if (success.equalsIgnoreCase("0")) {
//
////                        noDataTextView1.setVisibility(View.GONE);
////                        kitchenDeliveryOrdersRecyclerview1.setVisibility(View.VISIBLE);
//
//                        Log.d("ordersResp334", "response 1113: " + new Gson().toJson(response.body()));
//                        ordersList = user.ordesListResponses;
//
//                        Log.d("userrrdata", "" + user.ordesListResponses.size());
//
//                        if (user.ordesListResponses != null && user.ordesListResponses.size() > 0) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    OrdersRecyclerAdapter1 LoansListAdapter = new OrdersRecyclerAdapter1(MainActivityNew.this, ordersList);
//                                    kitchenDeliveryOrdersRecyclerview1.setAdapter(LoansListAdapter);
//                                    kitchenDeliveryOrdersRecyclerview1.setNestedScrollingEnabled(false);
//                                    LoansListAdapter.notifyDataSetChanged();
//                                }
//                            });
//                        } else {
//                            noDataTextView1.setVisibility(View.VISIBLE);
//                            kitchenDeliveryOrdersRecyclerview1.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        noDataTextView1.setVisibility(View.VISIBLE);
//                        kitchenDeliveryOrdersRecyclerview1.setVisibility(View.GONE);
//                    }
//
//                } else {
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrdersResponse> call, Throwable t) {
////                progressBar.setVisibility(View.GONE);
//                hideProgressDialog();
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//            }
//        });
//
//    }
//
//    public class OrdersRecyclerAdapter1 extends RecyclerView.Adapter<OrdersRecyclerAdapter1.ViewHolder> {
//
//        Context activity;
//
//        List<OrdersResponse.OrdesListResponse> data2;
//        OrdersResponse.OrdesListResponse masterData2;
//
//        Boolean selected;
//        int row_index;
//
//        public OrdersRecyclerAdapter1(Activity activity2,
//                                      List<OrdersResponse.OrdesListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data2 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_list_item1, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final OrdersRecyclerAdapter1.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData2 = data2.get(position);
//
//            holder.orderIdTextView.setText(masterData2.getSales_code());
//            holder.categoryTypeTv.setText(" Table Top");
//            holder.customerTv.setText("Cust Id : " + masterData2.getCustomer_id() + " ");
//
//            data3 = masterData2.getitemsResults();
//
//            final ViewHolder finalHolder = holder;
//
//            finalHolder.viewBtn2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    masterData2 = data2.get(position);
//                    data3 = masterData2.getitemsResults();
//
//                    Log.d("kotnumber", masterData2.getKot_number());
//
////                    finalHolder.viewBtn1.setVisibility(View.GONE);
//
//                    finalHolder.kitchenProductsItemsLayout.setVisibility(View.VISIBLE);
//
//                    LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
//                    holder.ordersProductsItemsRecyclerView.setLayoutManager(mLayoutManager2);
//
//                    LoansListAdapter = new KotOrdersEditRecyclerAdapter(MainActivityNew.this, data3);
//                    holder.ordersProductsItemsRecyclerView.setAdapter(LoansListAdapter);
//                    holder.ordersProductsItemsRecyclerView.setNestedScrollingEnabled(false);
//                    LoansListAdapter.notifyDataSetChanged();
//
//                    if (masterData2.getitemsResults() != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                masterData2 = data2.get(position);
//                                data3 = masterData2.getitemsResults();
//
//                                Log.d("kotnumber", masterData2.getKot_number());
//
//
//                            }
//                        });
//                    }
//
////
//
//
//                }
//            });
//
//            holder.closeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    masterData2 = data2.get(position);
//
//                    kitchenProductsItemsLayout1.setVisibility(View.GONE);
//
////                            modifyItemsPopup(sendOrderNo);
//
//
//                }
//            });
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data2.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView orderIdTextView;
//
//            AppCompatTextView viewBtn2, categoryTypeTv, customerTv, closeBtn, printBtn;
//            AppCompatTextView billNowButton;
//
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
//                ordersProductsItemsRecyclerView1 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView1);
//                this.viewBtn2 = itemView.findViewById(R.id.viewBtn2);
//                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
//                this.customerTv = itemView.findViewById(R.id.customerTv);
//                kitchenProductsItemsLayout1 = itemView.findViewById(R.id.kitchenProductsItemsLayout1);
//                this.closeBtn = itemView.findViewById(R.id.closeBtn);
//                this.printBtn = itemView.findViewById(R.id.printBtn);
//
//            }
//
//        }
//
//    }
//
//    public class KotOrdersEditRecyclerAdapter1 extends RecyclerView.Adapter<KotOrdersEditRecyclerAdapter1.ViewHolder> {
//
//        Context activity;
//
//        List<KotListResponse.KotListResponseListResponse> data;
//        KotListResponse.KotListResponseListResponse masterData;
//
//        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> data1;
//        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse masterData1;
//
//        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
//        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;
//
//        public KotOrdersEditRecyclerAdapter(Activity activity2,
//                                            List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data1 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_products_item, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData1 = data1.get(position);
//
//            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());
//
//
//            if (masterData1.getFood_status().equalsIgnoreCase("0")) {
//
//                holder.productItemCheckBox.setChecked(false);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | ~Paint.STRIKE_THRU_TEXT_FLAG);
//
//            } else {
//                holder.productItemCheckBox.setChecked(true);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            }
//
//
//            data2 = masterData1.addonsResults;
//
//            Log.d("daaaaaa", "" + data2);
////
////
//////            nameLists.clear();
//////            qtysList.clear();
////            priceList.clear();
////
////            for (int i = 0; i < data2.size(); i++) {
////                masterData1 = data1.get(i);
////                priceList.add(masterData2.getPrice());
//////                nameLists.add(masterData2.getAddon_name());
//////                qtysList.add(masterData2.getQty());
////            }
//
//            if (masterData1.getaddonsResults() != null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
//                        kitchenaddOnsRecyclerView.setLayoutManager(mLayoutManager2);
//
//                        kotAddonsEditRecyclerAdapter = new KotAddonsEditRecyclerAdapter(MainActivityNew.this, data2);
//                        kitchenaddOnsRecyclerView.setAdapter(kotAddonsEditRecyclerAdapter);
//                        kitchenaddOnsRecyclerView.setNestedScrollingEnabled(false);
//                        kotAddonsEditRecyclerAdapter.notifyDataSetChanged();
//
//
//                    }
//                });
//            }
//
//            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    masterData1 = data1.get(position);
//                    if (holder.productItemCheckBox.isChecked()) {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        orderId = masterData1.getItem_id();
//                        itemId = masterData1.getId();
//                        String checkedStatus = "1";
//
//                        sendOrderNo = masterData1.getSales_id();
//
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//
//                    } else {
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        orderId = masterData1.getItem_id();
//                        itemId = masterData1.getId();
//                        String checkedStatus = "0";
//
//                        sendOrderNo = masterData1.getSales_id();
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//                    }
//                }
//            });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data1.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenOrdersProductsItemsTV, txt_prod_qty, txt_prod_price, txt_sub_total, txt_prod_weight;
//            ImageView imageview, cartListItemDeleteIv;
//            ImageView img_plus, img_minus;
//            AppCompatTextView modifystatusTv;
//            TextView cart_list_item_addOns, cart_list_item_addOns_price;
//
//            AppCompatCheckBox productItemCheckBox;
//
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);
//
//                kitchenaddOnsRecyclerView = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView);
//                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);
//
//            }
//        }
//
//    }
//
//    public class KotAddonsEditRecyclerAdapter1 extends RecyclerView.Adapter<KotAddonsEditRecyclerAdapter1.ViewHolder> {
//
//        Context activity;
//        LayoutInflater inflater;
//        List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> data2;
//        KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse masterData2;
//
//        public KotAddonsEditRecyclerAdapter(Activity activity2, List<KotListResponse.KotListResponseListResponse.ItemsListResponseListResponse.AddonsListResponseListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//
//            activity = activity2;
//            data2 = masterList;
//
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            final ViewHolder finalHolder = holder;
//
//            masterData2 = data2.get(position);
//
////            Log.d("masterdddddd", masterData2.getAddon_name());
//
//            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getQty() + " X " + masterData2.getAddon_name());
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//
//            return data2.size();
//
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenAddOnsItemTv, addOnPriceTextView, addOnQtyTextView, addOnSubTotalTextView;
//            ImageView minusImageView, plusImageView, deleteImageView;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);
//
//
//            }
//
//        }
//    }
//
////    private void getKitchenOrdersListItemsApi1() {
////
////        PrintKotRequest request = new PrintKotRequest();
////        request.setOrder_id(sendOrderNo);
////
////        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);
////
////        Log.d("modfyrequest111222ss", new Gson().toJson(request));
////        call.enqueue(new Callback<PrintBillResponse>() {
////            @Override
////            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {
////
////                if (response.isSuccessful()) {
////                    hideProgressDialog();
////                    final PrintBillResponse user = response.body();
////
////                    String message = user.message;
////                    String success = user.responseCode;
//////                    orderNo = user.order_number;
////
////                    if (success.equalsIgnoreCase("0")) {
////
////                        Log.d("orderResp222aaa", "response 1112: " + new Gson().toJson(response.body()));
////                        orderList = user.productResults;
////
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////
////                                kitchenProductsItemsLayout1.setVisibility(View.VISIBLE);
////
////
////                                ModifyOrdersEditRecyclerAdapter1 LoansListAdapter = new ModifyOrdersEditRecyclerAdapter1(MainActivityNew.this, orderList);
////                                ordersProductsItemsRecyclerView1.setAdapter(LoansListAdapter);
////                                ordersProductsItemsRecyclerView1.setNestedScrollingEnabled(false);
////                                LoansListAdapter.notifyDataSetChanged();
////
////
////                            }
////                        });
////
////                    } else {
////                        showLongSnackBar(message);
////                    }
////
////                } else {
////                    hideProgressDialog();
////                    showLongSnackBar("Server Error. Please try again later.");
////                }
////            }
////
////            @Override
////            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
////                hideProgressDialog();
////
////                message = "Oops! something went wrong please try again";
////                showShortSnackBar(message);
////
////            }
////        });
////
////    }
////
////    public class ModifyOrdersEditRecyclerAdapter1 extends RecyclerView.Adapter<ModifyOrdersEditRecyclerAdapter1.ViewHolder> {
////
////        Context activity;
////
////        List<PrintBillResponse.ProductListResponse> data;
////        PrintBillResponse.ProductListResponse masterData;
////
////        List<PrintBillResponse.ProductListResponse> data1;
////        PrintBillResponse.ProductListResponse masterData1;
////
////        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
////        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
////
////        public ModifyOrdersEditRecyclerAdapter1(Activity activity2,
////                                                List<PrintBillResponse.ProductListResponse> masterList) {
////            // TODO Auto-generated constructor stub
////            activity = activity2;
////            data1 = masterList;
////        }
////
////        @Override
////        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
////            View v = LayoutInflater.from(activity)
////                    .inflate(R.layout.activity_kitchen_orders_products_item1, viewGroup, false);
////            ViewHolder viewHolder = new ViewHolder(v);
////            return viewHolder;
////        }
////
////        @Override
////        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
////
////            masterData1 = data1.get(position);
////
////            data2 = masterData1.productAddOnsResults;
////
////            Log.d("daaaaaa", "" + data2.size());
////
////            holder.kitchenOrdersProductsItemsTV.setText(masterData1.sales_qty + " X " + masterData1.item_name);
////
////            if (masterData1.getFood_status().equalsIgnoreCase("1")) {
////                Log.d("eeeeee", masterData1.getFood_status());
////
////                holder.productItemCheckBox.setChecked(true);
////                holder.productItemCheckBox.setEnabled(false);
////                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
////
////            }
////
////
////            LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
////            kitchenaddOnsRecyclerView1.setLayoutManager(mLayoutManager2);
////
////            modifyProductAddonsAdapter1 = new ModifyProductAddonsAdapter1(MainActivityNew.this, data2);
////            kitchenaddOnsRecyclerView1.setAdapter(modifyProductAddonsAdapter1);
////            kitchenaddOnsRecyclerView1.setNestedScrollingEnabled(false);
////            modifyProductAddonsAdapter1.notifyDataSetChanged();
////
////            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////
////                @Override
////                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////
////                    masterData1 = data1.get(position);
////                    if (holder.productItemCheckBox.isChecked()) {
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
////
////                        orderId = masterData1.getItem_id();
////                        itemId = masterData1.getId();
////                        String checkedStatus = "1";
////
////                        sendOrderNo = masterData1.getId();
////
////
////                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
////
////                    } else {
//////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
////
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
////                        orderId = masterData1.getItem_id();
////                        itemId = masterData1.getId();
////                        String checkedStatus = "0";
////
////                        sendOrderNo = masterData1.getId();
////
////                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
////                    }
////                }
////            });
////
////
////            if (masterData1.getProductAddOnsGroupResults() != null) {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////
////
////                    }
////                });
////            }
////
////
////        }
////
////        @Override
////        public int getItemCount() {
////            return data1.size();
////        }
////
////        public class ViewHolder extends RecyclerView.ViewHolder {
////
////            AppCompatTextView kitchenOrdersProductsItemsTV;
////            AppCompatCheckBox productItemCheckBox;
////
////            public ViewHolder(final View itemView) {
////                super(itemView);
////
////                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);
////                kitchenaddOnsRecyclerView1 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView1);
////                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);
////
////            }
////        }
////
////    }
////
////    public class ModifyProductAddonsAdapter1 extends RecyclerView.Adapter<ModifyProductAddonsAdapter1.ViewHolder> {
////
////        Context activity;
////        LayoutInflater inflater;
////        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
////        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
////
////        public ModifyProductAddonsAdapter1(Activity activity2, List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> masterList) {
////            // TODO Auto-generated constructor stub
////
////            activity = activity2;
////            data2 = masterList;
////
////        }
////
////        @Override
////        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
////            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items1, viewGroup, false);
////            ViewHolder viewHolder = new ViewHolder(v);
////            return viewHolder;
////        }
////
////        @Override
////        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
////
////            final ViewHolder finalHolder = holder;
////
////            masterData2 = data2.get(position);
////
////            Log.d("masterddddddccc", masterData2.getAddon_name());
////
////            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getAddon_name());
////
////
////        }
////
////
////        @Override
////        public int getItemCount() {
////
////            return data2.size();
////
////        }
////
////
////        public class ViewHolder extends RecyclerView.ViewHolder {
////
////            TextView kitchenAddOnsItemTv;
////
////            public ViewHolder(final View itemView) {
////                super(itemView);
////
////                kitchenAddOnsItemTv = (TextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);
////
////
////            }
////
////        }
////    }
//
//
//    private void getKitchenOrdersListItemsApi() {
//
//        PrintKotRequest request = new PrintKotRequest();
//        request.setOrder_id(sendOrderNo);
//
//        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);
//
//        Log.d("modfyrequest111", new Gson().toJson(request));
//        call.enqueue(new Callback<PrintBillResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {
//
//                if (response.isSuccessful()) {
//                    hideProgressDialog();
//                    final PrintBillResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
////                    orderNo = user.order_number;
//
//                    if (success.equalsIgnoreCase("0")) {
//
//                        Log.d("orderResp222aaa", "response 1112: " + new Gson().toJson(response.body()));
//                        orderList = user.productResults;
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                ModifyOrdersEditRecyclerAdapter itemsListAdapter = new ModifyOrdersEditRecyclerAdapter(MainActivityNew.this, orderList);
//                                ordersProductsItemsRecyclerView.setAdapter(itemsListAdapter);
//                                ordersProductsItemsRecyclerView.setNestedScrollingEnabled(false);
//                                itemsListAdapter.notifyDataSetChanged();
//
//                            }
//                        });
//
//                    } else {
//                        showLongSnackBar(message);
//                    }
//
//                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
//                hideProgressDialog();
//
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//
//            }
//        });
//
//    }
//
//    public class ModifyOrdersEditRecyclerAdapter extends RecyclerView.Adapter<ModifyOrdersEditRecyclerAdapter.ViewHolder> {
//
//        Context activity;
//
//        List<PrintBillResponse.ProductListResponse> data;
//        PrintBillResponse.ProductListResponse masterData;
//
//        List<PrintBillResponse.ProductListResponse> data1;
//        PrintBillResponse.ProductListResponse masterData1;
//
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyOrdersEditRecyclerAdapter(MainActivityNew activity2,
//                                               List<PrintBillResponse.ProductListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data1 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_products_item, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData1 = data1.get(position);
////            masterData2 = data2.get(position);
//
//            data2 = masterData1.getProductAddOnsGroupResults();
//
//            Log.d("daaaaaa", "" + data2.size() + ", " + masterData1.getProductAddOnsGroupResults());
//
//            holder.kitchenOrdersProductsItemsTV.setText(masterData1.getSales_qty() + " X " + masterData1.getItem_name());
//
//            Log.d("ddddddd", masterData1.getFood_status());
//
//            if (masterData1.getFood_status().equalsIgnoreCase("1")) {
//                Log.d("eeeeee", masterData1.getFood_status());
//
//                holder.productItemCheckBox.setChecked(true);
//                holder.productItemCheckBox.setEnabled(false);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            }
//
//            if (masterData1.getProductAddOnsGroupResults() != null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
//                        kitchenaddOnsRecyclerView.setLayoutManager(mLayoutManager2);
//
//                        modifyProductAddonsAdapter = new ModifyProductAddonsAdapter(MainActivityNew.this, data2);
//                        kitchenaddOnsRecyclerView.setAdapter(modifyProductAddonsAdapter);
//                        kitchenaddOnsRecyclerView.setNestedScrollingEnabled(false);
//                        modifyProductAddonsAdapter.notifyDataSetChanged();
//
//
//                    }
//                });
//            }
//
//            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    masterData1 = data1.get(position);
//                    if (holder.productItemCheckBox.isChecked()) {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "1";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//
//                    } else {
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "0";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//                    }
//                }
//            });
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data1.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenOrdersProductsItemsTV;
//            AppCompatCheckBox productItemCheckBox;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenaddOnsRecyclerView = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView);
//                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);
//                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);
//
//
//            }
//        }
//
//    }
//
//    public class ModifyProductAddonsAdapter extends RecyclerView.Adapter<ModifyProductAddonsAdapter.ViewHolder> {
//
//        Context activity;
//        LayoutInflater inflater;
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyProductAddonsAdapter(Activity activity2, List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//
//            activity = activity2;
//            data2 = masterList;
//
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            final ViewHolder finalHolder = holder;
//
//            masterData2 = data2.get(position);
//
//            Log.d("masterddddddcccdd", masterData2.getAddon_name());
//
//            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getAddon_name());
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//
//            return data2.size();
//
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenAddOnsItemTv;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenAddOnsItemTv = (AppCompatTextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);
//
//
//            }
//
//        }
//    }
//
//
//    private void getOrderDetails2() {
//
//        OrdersRequest request = new OrdersRequest();
//        request.setUser_id(userId);
//        request.setOutlet_id(outlet_id);
//        request.setOrder_type("1");
//        request.setKitchen_id("1");
//
//        Log.d("ordersRequest33tww", request + new Gson().toJson(request));
//        retrofit2.Call<OrdersResponse> call = apiService.orderDetails("1234", request);
//
//        call.enqueue(new Callback<OrdersResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<OrdersResponse> call, Response<OrdersResponse> response) {
//
//                if (response.isSuccessful()) {
//
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    OrdersResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
//                    if (success.equalsIgnoreCase("0")) {
//
//                        noDataTextView2.setVisibility(View.GONE);
//                        kitchenDeliveryOrdersRecyclerview2.setVisibility(View.VISIBLE);
//
//                        Log.d("ordersResp33tww", "response 1113: " + new Gson().toJson(response.body()));
//                        ordersList = user.ordesListResponses;
//
//                        Log.d("userrrdata", "" + user.ordesListResponses.size());
//
//                        if (user.ordesListResponses != null && user.ordesListResponses.size() > 0) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    OrdersRecyclerAdapter2 LoansListAdapter = new OrdersRecyclerAdapter2(MainActivityNew.this, ordersList);
//                                    kitchenDeliveryOrdersRecyclerview2.setAdapter(LoansListAdapter);
//                                    kitchenDeliveryOrdersRecyclerview2.setNestedScrollingEnabled(false);
//                                    LoansListAdapter.notifyDataSetChanged();
//                                }
//                            });
//                        } else {
//                            noDataTextView2.setVisibility(View.VISIBLE);
//                            kitchenDeliveryOrdersRecyclerview2.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        noDataTextView2.setVisibility(View.VISIBLE);
//                        kitchenDeliveryOrdersRecyclerview2.setVisibility(View.GONE);
//                    }
//
//                } else {
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrdersResponse> call, Throwable t) {
////                progressBar.setVisibility(View.GONE);
//                hideProgressDialog();
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//            }
//        });
//
//    }
//
//    public class OrdersRecyclerAdapter2 extends RecyclerView.Adapter<OrdersRecyclerAdapter2.ViewHolder> {
//
//        Context activity;
//
//        List<OrdersResponse.OrdesListResponse> data2;
//        OrdersResponse.OrdesListResponse masterData2;
//        Boolean selected;
//        int row_index;
//
//        public OrdersRecyclerAdapter2(Activity activity2,
//                                      List<OrdersResponse.OrdesListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data2 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_list_item2, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData2 = data2.get(position);
//
//            holder.orderIdTextView.setText(masterData2.getSales_code());
//            holder.categoryTypeTv.setText(" TakeAway");
//            holder.customerTv.setText("Cust Id : " + masterData2.getCustomer_id() + " ");
//
//            final ViewHolder finalViewHolder = holder;
//
//            finalViewHolder.viewBtn3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    masterData2 = data2.get(position);
//                    sendOrderNo = masterData2.getId();
//
//                    final ViewHolder finalViewHolder = holder;
//
//                    finalViewHolder.viewBtn3.setVisibility(View.GONE);
//
//                    Log.d("gettttt", masterData2.getId());
//                    finalViewHolder.kitchenProductsItemsLayout2.setVisibility(View.VISIBLE);
//                    LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                    finalViewHolder.ordersProductsItemsRecyclerView2.setLayoutManager(mLayoutManager2);
//                    getKitchenOrdersListItemsApi2(sendOrderNo);
//
////                            modifyItemsPopup(sendOrderNo);
//
//
//                }
//            });
//
//            holder.closeBtn.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("getttttaa", masterData2.getId());
//                            finalViewHolder.kitchenProductsItemsLayout2.setVisibility(View.GONE);
//                            finalViewHolder.viewBtn3.setVisibility(View.VISIBLE);
//
//                        }
//                    });
//
//            holder.printBtn.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            getPrintBillDetails(sendOrderNo);
//
//                        }
//                    });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data2.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView orderIdTextView;
//
//            AppCompatTextView viewBtn3, categoryTypeTv, customerTv, closeBtn, printBtn;
//            AppCompatTextView billNowButton;
//            RecyclerView ordersProductsItemsRecyclerView2;
//            LinearLayout kitchenProductsItemsLayout2;
//
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
//                this.ordersProductsItemsRecyclerView2 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView2);
//                this.viewBtn3 = itemView.findViewById(R.id.viewBtn3);
//                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
//                this.customerTv = itemView.findViewById(R.id.customerTv);
//                this.kitchenProductsItemsLayout2 = itemView.findViewById(R.id.kitchenProductsItemsLayout2);
//                this.closeBtn = itemView.findViewById(R.id.closeBtn);
//                this.printBtn = itemView.findViewById(R.id.printBtn);
//
//
//            }
//
//        }
//
//    }
//
//    private void getKitchenOrdersListItemsApi2(String orderNo) {
//
//        PrintKotRequest request = new PrintKotRequest();
//        request.setOrder_id(orderNo);
//
//        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);
//
//        Log.d("modfyrequest111", new Gson().toJson(request));
//        call.enqueue(new Callback<PrintBillResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {
//
//                if (response.isSuccessful()) {
//                    hideProgressDialog();
//                    final PrintBillResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
////                    orderNo = user.order_number;
//
//                    if (success.equalsIgnoreCase("0")) {
//
//                        Log.d("orderResp222aaa9900", "response 1112: " + new Gson().toJson(response.body()));
//                        orderList = user.productResults;
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                ordersProductsItemsRecyclerView2 = findViewById(R.id.ordersProductsItemsRecyclerView2);
//
//                                ModifyOrdersEditRecyclerAdapter2 LoansListAdapter = new ModifyOrdersEditRecyclerAdapter2(MainActivityNew.this, orderList);
//                                ordersProductsItemsRecyclerView2.setAdapter(LoansListAdapter);
//                                ordersProductsItemsRecyclerView2.setNestedScrollingEnabled(false);
//                                LoansListAdapter.notifyDataSetChanged();
//
//
//                            }
//                        });
//
//                    } else {
//                        showLongSnackBar(message);
//                    }
//
//                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
//                hideProgressDialog();
//
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//
//            }
//        });
//
//    }
//
//    public class ModifyOrdersEditRecyclerAdapter2 extends RecyclerView.Adapter<ModifyOrdersEditRecyclerAdapter2.ViewHolder> {
//
//        Context activity;
//
//        List<PrintBillResponse.ProductListResponse> data;
//        PrintBillResponse.ProductListResponse masterData;
//
//        List<PrintBillResponse.ProductListResponse> data1;
//        PrintBillResponse.ProductListResponse masterData1;
//
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyOrdersEditRecyclerAdapter2(Activity activity2,
//                                                List<PrintBillResponse.ProductListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data1 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_products_item2, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData1 = data1.get(position);
//
//            data2 = masterData1.productAddOnsResults;
//
//            Log.d("daaaaaa", "" + data2.size());
//
//            holder.kitchenOrdersProductsItemsTV.setText(masterData1.sales_qty + " X " + masterData1.item_name);
//
//            if (masterData1.getFood_status().equalsIgnoreCase("1")) {
//                Log.d("eeeeee", masterData1.getFood_status());
//
//                holder.productItemCheckBox.setChecked(true);
//                holder.productItemCheckBox.setEnabled(false);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            }
//
//            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    masterData1 = data1.get(position);
//                    if (holder.productItemCheckBox.isChecked()) {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "1";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//
//                    } else {
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "0";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//                    }
//                }
//            });
//
//            if (masterData1.getProductAddOnsGroupResults() != null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
//                        kitchenaddOnsRecyclerView2.setLayoutManager(mLayoutManager2);
//
//                        modifyProductAddonsAdapter2 = new ModifyProductAddonsAdapter2(MainActivityNew.this, data2);
//                        kitchenaddOnsRecyclerView2.setAdapter(modifyProductAddonsAdapter);
//                        kitchenaddOnsRecyclerView2.setNestedScrollingEnabled(false);
//                        modifyProductAddonsAdapter2.notifyDataSetChanged();
//
//
//                    }
//                });
//            }
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data1.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenOrdersProductsItemsTV;
//            AppCompatCheckBox productItemCheckBox;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);
//                kitchenaddOnsRecyclerView2 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView2);
//                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);
//
//            }
//        }
//
//    }
//
//    public class ModifyProductAddonsAdapter2 extends RecyclerView.Adapter<ModifyProductAddonsAdapter2.ViewHolder> {
//
//        Context activity;
//        LayoutInflater inflater;
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyProductAddonsAdapter2(Activity activity2, List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//
//            activity = activity2;
//            data2 = masterList;
//
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items2, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            final ViewHolder finalHolder = holder;
//
//            masterData2 = data2.get(position);
//
//            Log.d("masterddddddccc", masterData2.getAddon_name());
//
//            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getAddon_name());
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//
//            return data2.size();
//
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            TextView kitchenAddOnsItemTv;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenAddOnsItemTv = (TextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);
//
//
//            }
//
//        }
//    }
//
//
//    private void getOrderDetails3() {
//
//        OrdersRequest request = new OrdersRequest();
//        request.setUser_id(userId);
//        request.setOutlet_id(outlet_id);
//        request.setOrder_type("2");
//        request.setKitchen_id("1");
//
//        Log.d("ordersRequest33", request + new Gson().toJson(request));
//        retrofit2.Call<OrdersResponse> call = apiService.orderDetails("1234", request);
//
//        call.enqueue(new Callback<OrdersResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<OrdersResponse> call, Response<OrdersResponse> response) {
//
//                if (response.isSuccessful()) {
//
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    OrdersResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
//                    if (success.equalsIgnoreCase("0")) {
//
//                        noDataTextView2.setVisibility(View.GONE);
//                        kitchenDeliveryOrdersRecyclerview2.setVisibility(View.VISIBLE);
//
//                        Log.d("ordersResp33", "response 1113: " + new Gson().toJson(response.body()));
//                        ordersList = user.ordesListResponses;
//
//                        Log.d("userrrdata", "" + user.ordesListResponses.size());
//
//                        if (user.ordesListResponses != null && user.ordesListResponses.size() > 0) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    OrdersRecyclerAdapter3 LoansListAdapter = new OrdersRecyclerAdapter3(MainActivityNew.this, ordersList);
//                                    kitchenDeliveryOrdersRecyclerview3.setAdapter(LoansListAdapter);
//                                    kitchenDeliveryOrdersRecyclerview3.setNestedScrollingEnabled(false);
//                                    LoansListAdapter.notifyDataSetChanged();
//                                }
//                            });
//                        } else {
//                            noDataTextView3.setVisibility(View.VISIBLE);
//                            kitchenDeliveryOrdersRecyclerview3.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        noDataTextView3.setVisibility(View.VISIBLE);
//                        kitchenDeliveryOrdersRecyclerview3.setVisibility(View.GONE);
//                    }
//
//                } else {
////                    progressBar.setVisibility(View.GONE);
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrdersResponse> call, Throwable t) {
////                progressBar.setVisibility(View.GONE);
//                hideProgressDialog();
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//            }
//        });
//
//    }
//
//    public class OrdersRecyclerAdapter3 extends RecyclerView.Adapter<OrdersRecyclerAdapter3.ViewHolder> {
//
//        Context activity;
//
//        List<OrdersResponse.OrdesListResponse> data2;
//        OrdersResponse.OrdesListResponse masterData2;
//        Boolean selected;
//        int row_index;
//
//        public OrdersRecyclerAdapter3(Activity activity2,
//                                      List<OrdersResponse.OrdesListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data2 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_list_item3, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData2 = data2.get(position);
//
//            final ViewHolder finalViewHolder1 = holder;
//
//            holder.orderIdTextView.setText(masterData2.getSales_code());
//            holder.categoryTypeTv.setText(" Delivery");
//            holder.customerTv.setText("Cust Id : " + masterData2.getCustomer_id() + " ");
//
//
//            holder.viewBtn4.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            masterData2 = data2.get(position);
//                            sendOrderNo = masterData2.getId();
//
//                            final ViewHolder finalViewHolder1 = holder;
//
//                            finalViewHolder1.viewBtn4.setVisibility(View.GONE);
//
//                            Log.d("gettttt", masterData2.getId());
//
//                            finalViewHolder1.kitchenProductsItemsLayout3.setVisibility(View.VISIBLE);
//                            finalViewHolder1.ordersProductsItemsRecyclerView3.setVisibility(View.VISIBLE);
//
//
//                            LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                            finalViewHolder1.ordersProductsItemsRecyclerView3.setLayoutManager(mLayoutManager3);
//
//                            getKitchenOrdersListItemsApi3(sendOrderNo);
//
//
////                            modifyItemsPopup(sendOrderNo);
//
//                        }
//                    });
//
//            holder.closeBtn.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            holder.kitchenProductsItemsLayout3.setVisibility(View.GONE);
//                            holder.viewBtn4.setVisibility(View.VISIBLE);
//
//                        }
//                    });
//
//            holder.printBtn.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            getPrintBillDetails(sendOrderNo);
//
//                        }
//                    });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data2.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView orderIdTextView;
//
//            AppCompatTextView viewBtn4, categoryTypeTv, customerTv, closeBtn, printBtn;
//            AppCompatTextView billNowButton;
//
//            RecyclerView ordersProductsItemsRecyclerView3;
//            LinearLayout kitchenProductsItemsLayout3;
//
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
//                ordersProductsItemsRecyclerView3 = itemView.findViewById(R.id.ordersProductsItemsRecyclerView3);
//                this.viewBtn4 = itemView.findViewById(R.id.viewBtn4);
//                this.categoryTypeTv = itemView.findViewById(R.id.categoryTypeTv);
//                this.customerTv = itemView.findViewById(R.id.customerTv);
//                kitchenProductsItemsLayout3 = itemView.findViewById(R.id.kitchenProductsItemsLayout3);
//                this.closeBtn = itemView.findViewById(R.id.closeBtn);
//                this.printBtn = itemView.findViewById(R.id.printBtn);
//
//
//            }
//
//        }
//
//    }
//
//    private void getKitchenOrdersListItemsApi3(String sendOrderNo) {
//
//        PrintKotRequest request = new PrintKotRequest();
//        request.setOrder_id(sendOrderNo);
//
//        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);
//
//        Log.d("modfyrequest111888del", new Gson().toJson(request));
//        call.enqueue(new Callback<PrintBillResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {
//
//                if (response.isSuccessful()) {
//                    hideProgressDialog();
//                    final PrintBillResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
////                    orderNo = user.order_number;
//
//                    if (success.equalsIgnoreCase("0")) {
//
//                        Log.d("orderResp222aaadel", "response 1112: " + new Gson().toJson(response.body()));
//                        orderList = user.productResults;
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//
//                                ordersProductsItemsRecyclerView3 = findViewById(R.id.ordersProductsItemsRecyclerView3);
//
//                                ModifyOrdersEditRecyclerAdapter3 LoansListAdapter = new ModifyOrdersEditRecyclerAdapter3(MainActivityNew.this, orderList);
//                                ordersProductsItemsRecyclerView3.setAdapter(LoansListAdapter);
//                                ordersProductsItemsRecyclerView3.setNestedScrollingEnabled(false);
//                                LoansListAdapter.notifyDataSetChanged();
//
//                            }
//                        });
//
//                    } else {
//                        showLongSnackBar(message);
//                    }
//
//                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
//                hideProgressDialog();
//
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//
//            }
//        });
//
//    }
//
//    public class ModifyOrdersEditRecyclerAdapter3 extends RecyclerView.Adapter<ModifyOrdersEditRecyclerAdapter3.ViewHolder> {
//
//        Context activity;
//
//        List<PrintBillResponse.ProductListResponse> data;
//        PrintBillResponse.ProductListResponse masterData;
//
//        List<PrintBillResponse.ProductListResponse> data1;
//        PrintBillResponse.ProductListResponse masterData1;
//
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyOrdersEditRecyclerAdapter3(Activity activity2,
//                                                List<PrintBillResponse.ProductListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data1 = masterList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.activity_kitchen_orders_products_item3, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData1 = data1.get(position);
//
//            data2 = masterData1.productAddOnsResults;
//
//            Log.d("daaaaaa", "" + data2.size());
//
//            holder.kitchenOrdersProductsItemsTV.setText(masterData1.sales_qty + " X " + masterData1.item_name);
//
//            if (masterData1.getFood_status().equalsIgnoreCase("1")) {
//                Log.d("eeeeee", masterData1.getFood_status());
//
//                holder.productItemCheckBox.setChecked(true);
//                holder.productItemCheckBox.setEnabled(false);
//                holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            }
//
//            LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
//            holder.kitchenaddOnsRecyclerView3.setLayoutManager(mLayoutManager2);
//
//            modifyProductAddonsAdapter3 = new ModifyProductAddonsAdapter3(MainActivityNew.this, data2);
//            holder.kitchenaddOnsRecyclerView3.setAdapter(modifyProductAddonsAdapter);
//            holder.kitchenaddOnsRecyclerView3.setNestedScrollingEnabled(false);
//            modifyProductAddonsAdapter3.notifyDataSetChanged();
//
//
//            holder.productItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    masterData1 = data1.get(position);
//                    if (holder.productItemCheckBox.isChecked()) {
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "1";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//
//                    } else {
////                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        holder.kitchenOrdersProductsItemsTV.setPaintFlags(holder.kitchenOrdersProductsItemsTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        orderId = masterData1.item_id;
//                        itemId = masterData1.getId();
//                        String checkedStatus = "0";
//
//                        Log.d("sndinggg", sendOrderNo);
//
//                        getKOTItemStatusResponse(sendOrderNo, itemId, checkedStatus);
//                    }
//                }
//            });
//
//            if (masterData1.getProductAddOnsGroupResults() != null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                });
//            }
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data1.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            AppCompatTextView kitchenOrdersProductsItemsTV;
//            AppCompatCheckBox productItemCheckBox;
//            RecyclerView kitchenaddOnsRecyclerView3;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenOrdersProductsItemsTV = (AppCompatTextView) itemView.findViewById(R.id.kitchenOrdersProductsItemsTV);
//                this.kitchenaddOnsRecyclerView3 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView3);
//                productItemCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.productItemCheckBox);
//                kitchenaddOnsRecyclerView3 = (RecyclerView) itemView.findViewById(R.id.kitchenaddOnsRecyclerView3);
//
//            }
//        }
//
//    }
//
//    public class ModifyProductAddonsAdapter3 extends RecyclerView.Adapter<ModifyProductAddonsAdapter3.ViewHolder> {
//
//        Context activity;
//        LayoutInflater inflater;
//        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data2;
//        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData2;
//
//        public ModifyProductAddonsAdapter3(Activity activity2, List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//
//            activity = activity2;
//            data2 = masterList;
//
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity).inflate(R.layout.activity_kitchen_addons_items3, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            final ViewHolder finalHolder = holder;
//
//            masterData2 = data2.get(position);
//
//            Log.d("masterddddddccc", masterData2.getAddon_name());
//
//            finalHolder.kitchenAddOnsItemTv.setText(masterData2.getAddon_name());
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//
//            return data2.size();
//
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            TextView kitchenAddOnsItemTv;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                kitchenAddOnsItemTv = (TextView) itemView.findViewById(R.id.kitchenAddOnsItemTv);
//
//
//            }
//
//        }
//    }

    private void getKOTItemStatusResponse(String orderId, String itemId, String checkedStatus) {

        PrintKotRequest request = new PrintKotRequest();
        request.setUser_id(userId);
        request.setOrder_id(orderId);
        request.setOutlet_id(outlet_id);
        request.setStatus(checkedStatus);
        request.setId(itemId);

        Log.d("statusrequest", request + new Gson().toJson(request));
        Call<KotItemStatusResponse> call = apiService.kotItemStatusUpdateData("1234", request);

        call.enqueue(new Callback<KotItemStatusResponse>() {
            @Override
            public void onResponse(Call<KotItemStatusResponse> call, Response<KotItemStatusResponse> response) {

                if (response.isSuccessful()) {

//                    progressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    KotItemStatusResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        showShortSnackBar(message);


                    } else {
                        noDataTextView1.setVisibility(View.VISIBLE);
                        kitchenDeliveryOrdersRecyclerview1.setVisibility(View.GONE);
                    }

                } else {
//                    progressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotItemStatusResponse> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getPrintBillDetails(String orderNo) {

//        showProgressDialog();
        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);

        Call<PrintBillResponse> call = apiService.printbill("1234", request);

        Log.d("request", new Gson().toJson(request));
        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        showLongSnackBar("data found");

                        orderList = user.productResults;

                        if (orderList.size() > 0) {
//                            orderNo = user.order_number;

//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String a = "Copy - Receipt of \n Purchase(Inc Tax)";

                                    ITEM_BILL = "           " + user.company_name + " \n" +
                                            "  " + user.company_address + "\n" +

                                            "-----------------------------------------------\n";

                                    ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s %3$8s", "#", "PRODUCT              ", "QTY");
                                    ITEM_BILL = ITEM_BILL + "\n";
                                    ITEM_BILL = ITEM_BILL
                                            + "-----------------------------------------------";
                                    for (int i = 0; i < orderList.size(); i++) {

                                        String item = orderList.get(i).item_name;
                                        String qty = orderList.get(i).sales_qty;
                                        String price = orderList.get(i).price_per_unit;
                                        String total = orderList.get(i).unit_total_cost;

                                        if (item.length() < 20) {
                                            for (int k = 0; k < 18; k++) {
                                                item = item + " ";
                                            }
                                        }

                                        if (item.length() >= 21) {
                                            item = item.substring(0, 20) + "..";
                                        }

                                        ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s", "" + (i + 1), item, qty);

                                    }
                                    ITEM_BILL = ITEM_BILL
                                            + "\n-----------------------------------------------\n";
                                    ITEM_BILL = ITEM_BILL;

                                    Log.d("jfdhj", ITEM_BILL);

                                }
                            });
                        } else {
                            hideProgressDialog();
                            showShortSnackBar(message);
                        }

                    } else {
                        hideProgressDialog();
                        showLongSnackBar(message);
                    }


                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public void printFooter() {
        BILL = ITEM_BILL;
        BILL = BILL + "\n ";
        byte[] arrayOfByte1 = {27, 33, 0};
        Log.d("final bill", BILL);
        byte[] format = {27, 33, 0};

        printIt(BILL);
    }

    private void printIt(String thisData) {
        BluetoothSocket socket = null;
        byte[] data = thisData.getBytes();

        Log.d("gettttt", "" + data);

        //Get BluetoothAdapter
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Open Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get sunmi InnerPrinter BluetoothDevice
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);
        if (device == null) {
            Toast.makeText(getBaseContext(), "Make Sure Bluetooth have InnterPrinter", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            socket = BluetoothUtil.getSocket(device);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BluetoothUtil.sendData(data, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        void openDrawer(in ICallback callback);
//        int getOpenDrawerTimes();

    }

    AlertDialog modifyItemsDialog;

    private void getKOTItemRemoveResponse(String orderId, String kotnumber) {

        PrintKotRequest request = new PrintKotRequest();
        request.setUser_id(userId);
        request.setOrder_id(orderId);
        request.setOutlet_id(outlet_id);
        request.setKot_number(kotnumber);

        Log.d("statusrequestRemove", request + new Gson().toJson(request));
        Call<KotItemStatusResponse> call = apiService.kotItemClearOrder("1234", request);

        call.enqueue(new Callback<KotItemStatusResponse>() {
            @Override
            public void onResponse(Call<KotItemStatusResponse> call, Response<KotItemStatusResponse> response) {

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    KotItemStatusResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        showShortSnackBar(message);
                        getOrderDetailsDineIn();

                    } else {
//                        noDataTextView1.setVisibility(View.VISIBLE);
//                        kitchenDeliveryOrdersRecyclerview1.setVisibility(View.GONE);
                        showShortSnackBar(message);
                    }

                } else {
//                    progressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<KotItemStatusResponse> call, Throwable t) {

                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getPrintBillDetails1() {

//        showProgressDialog();
        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        Call<PrintBillResponse> call = apiService.kitchenprintbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;
//                        billingName = user.customer;
//                        companyName = user.company_name;
//                        date = user.time;
//                        totalAmt = user.total_amount;
//                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    String a = "   Copy - Receipt";

                                    ITEM_BILL = "             " + user.company_name + " \n" +
                                            "    " + user.company_address + "\n" +
                                            "-----------------------------------------------\n" +
                                            a + "\n";

                                    ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s", "#", " Item       ", "Qty");
                                    ITEM_BILL = ITEM_BILL + "\n";
                                    ITEM_BILL = ITEM_BILL
                                            + "-----------------------------------------------";
                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String price = user.productResults.get(i).price_per_unit;
                                        String total = user.productResults.get(i).unit_total_cost;

                                        if (item.length() < 20) {
                                            for (int k = 0; k < 18; k++) {
                                                item = item + " ";
                                            }
                                        }

                                        if (item.length() >= 21) {
                                            item = item.substring(0, 21) + "..";
                                        }

                                        ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s", "" + (i + 1), item, qty);

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    String a_qty = addOnsproductsList.get(j).qty;
                                                    String a_price = addOnsproductsList.get(j).price;
                                                    String a_total = addOnsproductsList.get(j).total_price;

                                                    if (a_item.length() < 20) {
                                                        for (int k = 0; k < 18; k++) {
                                                            a_item = a_item + " ";
                                                        }
                                                    }

                                                    if (a_item.length() >= 21)
                                                        a_item = a_item.substring(0, 21) + "..";
                                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s", "", a_item, a_qty);
                                                }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                    }
                                    ITEM_BILL = ITEM_BILL
                                            + "\n-----------------------------------------------\n" +
                                            "     Thank You Visit Again";
                                    Log.d("jfdhj", ITEM_BILL);

                                    printData(ITEM_BILL);
                                }

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
            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getKitchenPrintBillDetails(String orderNum) {

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_id(orderNum);

        Call<PrintBillResponse> call = apiService.printbill("1234", request);
        Log.d("kitchenprintreq", "response 1112: " + new Gson().toJson(request));

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    Log.d("kitchenprint", "response 1112: " + new Gson().toJson(response.body()));

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("kitchenprint1", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        Log.d("printsize", "" + printList.size());

                        orderNo = user.order_number;
                        printOrderNo = user.order_id;
//                        billingName = user.customer;
//                        companyName = user.company_name;
//                        date = user.time;
//                        totalAmt = user.total_amount;
//                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    String a = "Kitchen Receipt";

                                    String company_name = "<b>" + user.company_name + "</b> ";
                                    Html.fromHtml(company_name);

                                    String company_address = "<b>" + user.company_address + "</b> ";
                                    Html.fromHtml(company_address);


                                    ITEM_BILL =
                                            "       " + company_name + "\n" +
                                                    "         " + company_address + "\n" +
                                                    "-----------------------------------------\n";

                                    ITEM_BILL = ITEM_BILL + "Table : " + user.table_number + "   " + "Order No :  " + user.getOrder_number() +
                                            "\n";

                                    ITEM_BILL = ITEM_BILL + "Order Type :   " + user.getOrder_type() +
                                            "\n";

                                    ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-3s %3$-4s", "#", " Item        ", "         Qty");
                                    ITEM_BILL = ITEM_BILL + "\n";
                                    ITEM_BILL = ITEM_BILL
                                            + "----------------------------------------";
                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String price = user.productResults.get(i).price_per_unit;
                                        String total = user.productResults.get(i).unit_total_cost;

                                        if (item.length() < 20) {
                                            for (int k = 0; k < 18; k++) {
                                                item = item + " ";
                                            }
                                        }

                                        if (item.length() >= 21) {
                                            item = item.substring(0, 21) + "..";
                                        }

                                        ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-3s %3$4s", "" + (i + 1), item, qty);

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    String a_qty = addOnsproductsList.get(j).qty;
                                                    String a_price = addOnsproductsList.get(j).price;
                                                    String a_total = addOnsproductsList.get(j).total_price;

                                                    if (a_item.length() < 20) {
                                                        for (int k = 0; k < 18; k++) {
                                                            a_item = a_item + " ";
                                                        }
                                                    }

                                                    if (a_item.length() >= 21)
                                                        a_item = a_item.substring(0, 21) + "..";
                                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s", "", a_item, a_qty);
                                                }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                    }
                                    ITEM_BILL = ITEM_BILL
                                            + "\n-----------------------------------------------\n" +
                                            "           Thank You!  ";
                                    Log.d("jfdhj", ITEM_BILL);

                                    printData(ITEM_BILL);

                                } else {

                                }

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
            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

//    private void getKitchenPrintBillDetails() {
//
////        showProgressDialog();
//
//        PrintKotRequest request = new PrintKotRequest();
//
//        request.setUser_id(userId);
//        request.setOutlet_id(outlet_id);
//
//        Call<PrintBillResponse> call = apiService.kitchenprintbill("1234", request);
//        Log.d("kitchenprintreq", "response 1112: " + new Gson().toJson(request));
//
//
//        call.enqueue(new Callback<PrintBillResponse>() {
//            @Override
//            public void onResponse(Call<PrintBillResponse> call, Response<PrintBillResponse> response) {
//
//                if (response.isSuccessful()) {
//                    hideProgressDialog();
//                    final PrintBillResponse user = response.body();
//
//                    String message = user.message;
//                    String success = user.responseCode;
//                    Log.d("kitchenprint", "response 1112: " + new Gson().toJson(response.body()));
//
//
//                    if (success.equalsIgnoreCase("0")) {
//
//                        Log.d("kitchenprint1", "response 1112: " + new Gson().toJson(response.body()));
//                        printList = user.productResults;
//
//                        orderNo = user.order_number;
////                        billingName = user.customer;
////                        companyName = user.company_name;
////                        date = user.time;
////                        totalAmt = user.total_amount;
////                        paidAmt = user.paid_amount;
////                        Log.d("kfdjgkl", totalAmt);
////                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                String a = "Kitchen Receipt";
//
//
//                                ITEM_BILL = " <b>            " + user.company_name + "</b> \n" +
//                                        "    " + user.company_address + "\n" +
//                                        "-----------------------------------------------\n";
//
//
//                                ITEM_BILL = "Table : " + user.getTable_number() + "   " + "Order : " + user.getOrder_number() +
//                                        "\n";
//
//                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-3s %3$-1s", "#", " Item        ", "Qty");
//                                ITEM_BILL = ITEM_BILL + "\n";
//                                ITEM_BILL = ITEM_BILL
//                                        + "-----------------------------------------------";
//                                for (int i = 0; i < printList.size(); i++) {
//                                    addOnsproductsList = new ArrayList<>();
//                                    addOnsproductsList = user.productResults.get(i).productAddOnsResults;
//
//                                    String item = user.productResults.get(i).item_name;
//                                    String qty = user.productResults.get(i).sales_qty;
//                                    String price = user.productResults.get(i).price_per_unit;
//                                    String total = user.productResults.get(i).unit_total_cost;
//
//                                    if (item.length() < 20) {
//                                        for (int k = 0; k < 18; k++) {
//                                            item = item + " ";
//                                        }
//                                    }
//
//                                    if (item.length() >= 21) {
//                                        item = item.substring(0, 21) + "..";
//                                    }
//
//                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-3s %3$5s", "" + (i + 1), item, qty);
//
//                                    try {
//                                        if (addOnsproductsList.size() > 0) {
//
//                                            for (int j = 0; j < addOnsproductsList.size(); j++) {
//                                                String a_item = addOnsproductsList.get(j).addon_name;
//                                                String a_qty = addOnsproductsList.get(j).qty;
//                                                String a_price = addOnsproductsList.get(j).price;
//                                                String a_total = addOnsproductsList.get(j).total_price;
//
//                                                if (a_item.length() < 20) {
//                                                    for (int k = 0; k < 18; k++) {
//                                                        a_item = a_item + " ";
//                                                    }
//                                                }
//
//                                                if (a_item.length() >= 21)
//                                                    a_item = a_item.substring(0, 21) + "..";
//                                                ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s", "", a_item, a_qty);
//                                            }
////                                    ITEM_BILL = ITEM_BILL + "\n";
//                                        }
//                                    } catch (NullPointerException e) {
//
//                                    }
//                                }
//                                ITEM_BILL = ITEM_BILL
//                                        + "\n-----------------------------------------------\n" +
//                                        "     ";
//                                Log.d("jfdhj", ITEM_BILL);
//
//                                doPrint(ITEM_BILL);
//
////                                sessionManager.storeOrderNo(printOrderNo);
//                            }
//                        });
//
//                    } else {
//                        showLongSnackBar(message);
//                    }
//
//                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
//                hideProgressDialog();
//
//                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
//
//            }
//        });
//
//    }


    public void doPrint(String itemBill) {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, PrintMainActivity.PERMISSION_BLUETOOTH);
            } else {
//                getPrinter();

                BluetoothConnection[] bluetoothDevicesList = new BluetoothConnections().getList();

                if (bluetoothDevicesList == null) {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }

                int i = 0;
                BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
                for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
                    BluetoothDevice device = bluetoothConnection.getDevice();


                    int majDeviceCl = device.getBluetoothClass().getMajorDeviceClass(),
                            deviceCl = device.getBluetoothClass().getDeviceClass();

//                    showLongSnackBar(device.getAddress() + "");

                    if ((majDeviceCl == BluetoothClass.Device.Major.IMAGING && (deviceCl == 1664 || deviceCl == BluetoothClass.Device.Major.IMAGING)) || device.getName().equals("InnerPrinter")) {
                        printersTmp[i++] = new BluetoothConnection(device);
                    }
                }


//                BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
//                for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
//                    BluetoothDevice device = bluetoothConnection.getDevice();
//                    if (device.getName().equals("TSP100-C0260")) {
//                        printersTmp[i++] = new BluetoothConnection(device);
//                    }else if (device.getName().equals("InnerPrinter")) {
//                        printersTmp[i++] = new BluetoothConnection(device);
//                    }{
//
//                    }
//                }
                BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
                System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);

//                showLongSnackBar(new Gson().toJson(bluetoothPrinters));
//                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                BluetoothConnection connection = bluetoothPrinters[0];
                Log.d("kldj", connection + "");

                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 65f, 40);
//                    final String text = "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
//                            this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.tia_logo,
//                                    DisplayMetrics.DENSITY_HIGH, getTheme())) + "</img>\n" +
//                            itemBill;

                    final String text = itemBill;

                    printer.printFormattedText(text);
                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    public void printData(final String ItemBill) {
        Thread t = new Thread() {
            public void run() {
                try {

                    BILL = ItemBill;
                    BILL = BILL + "\n ";
                    byte[] arrayOfByte1 = {27, 33, 0};
                    Log.d("final bill", BILL);
                    byte[] format = {27, 33, 0};

                    printIt(BILL);

                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    final Handler handler = new Handler();

    public void handlerCall() {
        final Runnable r = new Runnable() {
            public void run() {

//                getPrintBillDetails1();
                handler.postDelayed(this, 30000);
            }
        };

        handler.postDelayed(r, 10000);
    }

    private void getPrintKitchenBillDetails1() {

//        showProgressDialog();
        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setOrder_id(sendOrderNo);

        Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("kotPrintResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;
//                        billingName = user.customer;
//                        companyName = user.company_name;
//                        date = user.time;
//                        totalAmt = user.total_amount;
//                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    userDetails = "";

                                    String orderType = user.order_type;
                                    if (orderType.equalsIgnoreCase("Take away")) {
                                        userDetails = "[L]<b>Customer Name: <font size='big'>" + user.customer + "\"</font>\n" +
                                                "[L]Customer Mobile: " + user.customer_mobile + "</b>\n";
                                        textName = "Pickup Time : ";
                                    } else if (orderType.equalsIgnoreCase("Online Delivery")) {
                                        userDetails = "[L]<b>Customer Name: <font size='big'>" + user.del_customer_name + "</font>\n" +
                                                "[L]Customer Mobile: " + user.del_customer_mobile + "\n" +
                                                "[L]Customer Address: " + user.del_customer_address + "</b>\n";
                                        textName = "Delivery Time : ";
                                    }

                                    ITEM_BILL = "";

                                    ITEM_BILL = ITEM_BILL + "[L]\n";

                                    ITEM_BILL = ITEM_BILL + "[C]<b><font size='big'>" + user.getOrder_type() + "</font></b>\n";

                                    ITEM_BILL = ITEM_BILL + "[L]<b><font size='big'>#" + user.getOrder_number() + "</font></b>\n";
                                    ITEM_BILL = ITEM_BILL + "<b>Date: " + user.getSales_date() + "\n";
                                    if (user.table_number.equalsIgnoreCase("0")) {

                                    } else {
                                        ITEM_BILL = ITEM_BILL + "[L]<b>Table No : <font size='big'>#" + user.table_number + "</font></b>\n";
                                    }

                                    if (user.delivery_time.equalsIgnoreCase("0")) {

                                    } else {
                                        if (user.order_type.equalsIgnoreCase("Take away")) {

                                            ITEM_BILL = ITEM_BILL + "PickUp Time: " + user.delivery_time + "\n";

                                        } else if (orderType.equalsIgnoreCase("Online Delivery")) {

                                            ITEM_BILL = ITEM_BILL + "Delivery Time: " + user.delivery_time + "\n";

                                        }

                                    }


                                    ITEM_BILL = ITEM_BILL + textName + user.delivery_time + "\n";
                                    ITEM_BILL = ITEM_BILL + "Payment Status: <font size='big'>" + user.getPayment_status() + "</font></b>\n";
                                    ITEM_BILL = ITEM_BILL
                                            +
                                            userDetails +
                                            "[L]\n";

                                    ITEM_BILL = ITEM_BILL
                                            + "[C]<b><font size='big'>" + "ORDER DETAILS" + "</font></b>" + "\n"
                                            + "[C]-------------------------------------\n";
                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;

                                        ITEM_BILL = ITEM_BILL + "[L]<b>x " + qty + "  " + item + "[R]" + "\n";

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;

                                                    ITEM_BILL = ITEM_BILL + "[L]    " + a_item + "[R]" + "</b>\n+";

                                                }

                                            }
                                        } catch (NullPointerException e) {

                                        }

                                        ITEM_BILL = ITEM_BILL + "[C]\n";

                                    }

                                    Log.d("dinejfdhj", ITEM_BILL);

                                    printText = null;

                                    ITEM_BILL = ITEM_BILL + "[L]\n" +
                                            "[C]\n";
                                    printText = ITEM_BILL;

                                    doPrint(printText);

                                    //doPrint(printText);
//                                    printTcp();
                                }

                            }
                        });

                    } else {
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (KitchenMainActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {
                            // printIt(new UsbConnection(usbManager, usbDevice));
                            new AsyncUsbEscPosPrint(context).execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));
                        }
                    }
                }
            }
        }
    };

    public void printUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);

        if (usbConnection == null || usbManager == null) {
//            showLongSnackBar("No USB printer found");
            return;
        }

        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(KitchenMainActivity.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(KitchenMainActivity.ACTION_USB_PERMISSION);
        registerReceiver(this.usbReceiver, filter);
        usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 303, 62f, 40);
        return printer.setTextToPrint("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.tia_logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" + printText);
    }

    public void printTcp() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    EscPosPrinter printer = new EscPosPrinter(new TcpConnection("192.168.0.234", 9100), 203, 65f, 42);
                    printer.printFormattedTextAndCut(
                            "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, getApplicationContext().getResources().getDrawableForDensity(R.mipmap.tia_logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                                    printText);
                    printer.disconnectPrinter();
                } catch (EscPosConnectionException e) {
                    e.printStackTrace();
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Broken connection")
//                            .setMessage(e.getMessage())
//                            .show();
                } catch (EscPosParserException e) {
                    e.printStackTrace();
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Invalid formatted text")
//                            .setMessage(e.getMessage())
//                            .show();
                } catch (EscPosEncodingException e) {
                    e.printStackTrace();
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Bad selected encoding")rree
//                            .setMessage(e.getMessage())
//                            .show();
                } catch (EscPosBarcodeException e) {
                    e.printStackTrace();
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Invalid barcode")
//                            .setMessage(e.getMessage())
//                            .show();
                }
            }
        }).start();

    }

}
