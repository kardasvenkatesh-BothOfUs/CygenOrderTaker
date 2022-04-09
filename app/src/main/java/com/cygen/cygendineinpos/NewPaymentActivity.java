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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.printservice.PrintService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cygen.cygendineinpos.RetrofitRequest.GetTermicalCheckoutRequest;
import com.cygen.cygendineinpos.RetrofitRequest.OrderRequest;
import com.cygen.cygendineinpos.RetrofitRequest.PrintKotRequest;
import com.cygen.cygendineinpos.RetrofitRequest.SquareUpPaymentRequest;
import com.cygen.cygendineinpos.RetrofitResponse.LoginResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintBillResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintKotResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SaveOrderResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SquareUpPaymentResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TermicalCancelCheckoutResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TermicalCheckoutResponse;
import com.cygen.cygendineinpos.database.AddOns;
import com.cygen.cygendineinpos.database.DataBaseHandler;
import com.cygen.cygendineinpos.database.Product;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.EscPosPrinterCommands;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.cygen.cygendineinpos.Printer.SunmiPrintHelper;

public class NewPaymentActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, Runnable {

    private AppCompatButton zeroButton;
    private AppCompatButton oneButton;
    private AppCompatButton twoButton;
    private AppCompatButton threeButton;
    private AppCompatButton fourButton;
    private AppCompatButton fiveButton;
    private AppCompatButton sixButton;
    private AppCompatButton sevenButton;
    private AppCompatButton eightButton;
    private AppCompatButton nineButton;
    private AppCompatButton tenButton;
    private AppCompatButton plusButton;
    private AppCompatButton minusButton;
    private AppCompatButton multiplyButton;
    private AppCompatButton divideButton;
    private AppCompatButton dotButton;
    private AppCompatButton equalButton;

    private AppCompatTextView fiveCentButton;
    private AppCompatTextView tenCentButton;
    private AppCompatTextView twentyCentButton;
    private AppCompatTextView fiftyCentButton;

    private AppCompatTextView oneDollarButton;
    private AppCompatTextView twoDollarButton;
    private AppCompatTextView fiveDollarButton;
    private AppCompatTextView tenDollarButton;
    private AppCompatTextView twentyDollarButton;
    private AppCompatTextView fiftyDollarButton;
    private AppCompatTextView hundredDollarButton;

    private AppCompatTextView selectPrinterTv;
    private AppCompatTextView paymentsEnterInvoiceTv;
    private AppCompatTextView backPaymentTv;
    private AppCompatTextView tipAmountTv;

    private AppCompatButton clearButton;

    private AppCompatEditText calculationEditText;

    private String calc;
    private String result;
    Double mValue1 = 0.0;
    Double mValue2 = 0.0;
    Double tipAmount = 0.0;
    Double finalResult = 0.0;
    Double add = 0.0, sub = 0.0, mul = 0.0, div = 0.0;
    Double totalPayable, totalPayings = 0.00, changeReturn = 0.00, discount = 0.00;
    Double totalAmount = 0.00, balance = 0.00;
    Double amount = 0.00;
    String mAmount = null;
    int qty = 0;
    private boolean addition, subtraction, multiplication, division;

    String BILL = "";
    String ITEM_BILL = "";
    String ITEM_BILL_1 = "";

    Double subTotal = 0.00, taxAmount = 0.00, paidAmount = 0.00, refundAmount = 0.00, dueAmount = 0.00;
    String totalAmt, paidAmt;

    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    AlertDialog alertDialog;

    String companyName, companyAddress, orderNo, billingName, date, kotNumber, tableNumber;

    List<PrintBillResponse.ProductListResponse> productsList;
    List<PrintKotResponse.ProductListResponse> printKotList;
    List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> addOnsproductsList;
    List<PrintKotResponse.ProductListResponse.ProductAddOnsListResponse> kotAddOnsList;
    AppCompatTextView totalItemsTv;
    AppCompatTextView totalAmountTv;
    AppCompatTextView totalDiscountTv;
    AppCompatTextView totalPayableTv;
    AppCompatTextView totalPayingsTv;
    AppCompatTextView totalBalanceTv;
    AppCompatTextView changeReturnTv;

    AppCompatTextView qrGenerateTv;

    AppCompatTextView cardPaymentTextView;
    AppCompatTextView cashPaymentTextView;

    RelativeLayout fiveCentLayout;
    RelativeLayout tenCentLayout;
    RelativeLayout twentyCentLayout;
    RelativeLayout fiftyCentLayout;
    RelativeLayout oneDollarLayout;
    RelativeLayout twoDollarLayout;
    RelativeLayout fiveDollarLayout;
    RelativeLayout tenDollarLayout;
    RelativeLayout twentyDollarLayout;
    RelativeLayout fiftyDollarLayout;
    RelativeLayout hundredDollarLayout;
    RelativeLayout clearDollarLayout;
    String userId = null;
    SessionManager sessionManager;

    DataBaseHandler db;

    List<String> addOnNameList, addOnIdList, addOnPriceList, productIdList, addOnQtyList;
    String addOnId = null, addOnName = null, addOnPrice = null, addOnQty = null, addOnsNotes = null;

    Bitmap image;
    private String URL;

    AlertDialog qrPaymentDialog;

    Boolean upiPaymentPopup = false;

    List<Product> products;
    List<AddOns> addOns;
    ArrayList<HashMap<String, String>> productsWithAddOnsList;

    JSONArray productsArray;
    String outlet_id;

    String orderNumber;

    String updateData = "0";
    String paymentType = "0";
    String orderType = "-1";
    String priceId = "0";
    String checkoutId = "1234";

    AlertDialog progressDialog;

    Double selectedDiscount = 0.00;
    Double deliveryCharges = 0.00;

    LinearLayoutCompat cashCardLayout, otherOrders;
    AppCompatTextView addDeliveryChargesTextView, uberTextView, menulogTextView, doordashTextView, othersTextView;
    AppCompatEditText addDeliveryChargesEditText;
    AppCompatTextView deliverChargesTv;

    List<PrintBillResponse.ProductListResponse> printList;
    PrintBillResponse.UserListResponse taxList;

    String userDetails = null;
    String printText = null;
    String textName = null;
    String customerId = null;
    String customerName, mobileNo, tableNo;

    //    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    String tableNewNo;
    String orderNewNo;
//    String tax;

    Double tax = 0.00;
    private static final String ACTION_USB_PERMISSION = "com.cygen.cygendineinpos.USB_PERMISSION";
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    HashMap<String, UsbDevice> mDeviceList;
    Iterator<UsbDevice> mDeviceIterator;

    String addNote;
    private SunmiPrintHelper sunmiPrinterService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> user1 = sessionManager.getUserDetails();
        userId = user1.get(sessionManager.KEY_USER_ID);
        outlet_id = user1.get(sessionManager.KEY_OUTLET_ID);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        mDeviceList = mUsbManager.getDeviceList();

        mDeviceIterator = mDeviceList.values().iterator();
        UsbManager usbManager = (UsbManager) getSystemService(USB_SERVICE);
        if (usbManager != null) {
            HashMap<String, UsbDevice> devices = usbManager.getDeviceList();
            for (String key : devices.keySet()) {
//                Log.d("TAG", "Device Name: " + devices.get(key).getDeviceName());
//                Log.d("TAG", "Manufacturer Name: " + devices.get(key).getManufacturerName());
//                Log.d("TAG", "Product Name: " + devices.get(key).getProductName());

                Toast.makeText(this, "Device List Sizes: " + devices.get(key).getProductId() + ",  " + devices.get(key).getVendorId(), Toast.LENGTH_LONG).show();

            }
        }


        calculationEditText = findViewById(R.id.calculationEditText);

        cashPaymentTextView = findViewById(R.id.cashPaymentTextView);
        cardPaymentTextView = findViewById(R.id.cardPaymentTextView);

        uberTextView = findViewById(R.id.uberTv);
        menulogTextView = findViewById(R.id.menulogTv);
        doordashTextView = findViewById(R.id.doordashTv);
        othersTextView = findViewById(R.id.othersTv);

        cashCardLayout = findViewById(R.id.cashCardLayout);
        otherOrders = findViewById(R.id.otherOrders);

        addDeliveryChargesEditText = findViewById(R.id.addDeliveryChargesEditText);
        addDeliveryChargesTextView = findViewById(R.id.addDeliveryChargesTextView);
        deliverChargesTv = findViewById(R.id.deliverChargesTv);

        db = new DataBaseHandler(getApplicationContext());

        addOnIdList = new ArrayList<>();
        addOnNameList = new ArrayList<>();
        addOnPriceList = new ArrayList<>();
        addOnQtyList = new ArrayList<>();

        retrofitService();

        priceId = "0";

        totalPayable = getIntent().getExtras().getDouble("totalPayable");
        totalAmount = getIntent().getExtras().getDouble("total");
        qty = getIntent().getExtras().getInt("qty");
        discount = getIntent().getExtras().getDouble("discount");
        orderNo = getIntent().getExtras().getString("orderId");
        updateData = getIntent().getExtras().getString("updateData");
        priceId = getIntent().getExtras().getString("priceId");
        customerName = getIntent().getExtras().getString("customerName");
        mobileNo = getIntent().getExtras().getString("mobileNo");
        tableNo = getIntent().getExtras().getString("tableNo");
        orderType = getIntent().getExtras().getString("oredrType");
//        addNote = getIntent().getExtras().getString("addNote");

//        Toast.makeText(getApplicationContext(), tableNo + ",," + orderType, Toast.LENGTH_SHORT).show();
//        Log.d("tableNooooo", tableNo + "," + orderType + "," + discount + "," + totalPayable + "," + totalAmount + "," + orderNo);

        if (priceId.equalsIgnoreCase("6")) {
            uberTextView.setAlpha(1);
            uberTextView.setBackgroundResource(R.color.colorBlack);
            uberTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            menulogTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            doordashTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            othersTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            menulogTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            doordashTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            othersTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            cashCardLayout.setVisibility(View.GONE);
            otherOrders.setVisibility(View.VISIBLE);
        } else if (priceId.equalsIgnoreCase("7")) {
            menulogTextView.setAlpha(1);
            menulogTextView.setBackgroundResource(R.color.addOnOrange);
            menulogTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            uberTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            doordashTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            othersTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            uberTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            doordashTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            othersTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            cashCardLayout.setVisibility(View.GONE);
            otherOrders.setVisibility(View.VISIBLE);
        } else if (priceId.equalsIgnoreCase("8")) {
            doordashTextView.setAlpha(1);
            doordashTextView.setBackgroundResource(R.color.redColor);
            doordashTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            menulogTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            uberTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            othersTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            menulogTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            uberTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            othersTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            cashCardLayout.setVisibility(View.GONE);
            otherOrders.setVisibility(View.VISIBLE);
        } else if (priceId.equalsIgnoreCase("9")) {
            othersTextView.setAlpha(1);
            othersTextView.setBackgroundResource(R.color.addOnBlue);
            othersTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            menulogTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            doordashTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            uberTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
            menulogTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            doordashTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            uberTextView.setTextColor(getResources().getColor(R.color.addOnSelectedColor));
            cashCardLayout.setVisibility(View.GONE);
            otherOrders.setVisibility(View.VISIBLE);
        } else {
            cashCardLayout.setVisibility(View.VISIBLE);
            otherOrders.setVisibility(View.GONE);
        }


        balance = totalPayable;

        oneButton = findViewById(R.id.oneTv);
        twoButton = findViewById(R.id.twoTv);
        threeButton = findViewById(R.id.threeTv);
        fourButton = findViewById(R.id.fourTv);
        fiveButton = findViewById(R.id.fiveTv);
        sixButton = findViewById(R.id.sixTV);
        sevenButton = findViewById(R.id.sevenTv);
        eightButton = findViewById(R.id.eightTv);
        nineButton = findViewById(R.id.nineTv);
        zeroButton = findViewById(R.id.zeroTv);
        plusButton = findViewById(R.id.additionTv);
        minusButton = findViewById(R.id.minusTv);
        multiplyButton = findViewById(R.id.multiplyTv);
        divideButton = findViewById(R.id.divideTv);
        equalButton = findViewById(R.id.equalsTv);
        dotButton = findViewById(R.id.dotTv);

        fiveCentButton = findViewById(R.id.fiveCentTv);
        tenCentButton = findViewById(R.id.tenCentTv);
        twentyCentButton = findViewById(R.id.twentyCentTv);
        fiftyCentButton = findViewById(R.id.fiftyCentTv);

        oneDollarButton = findViewById(R.id.oneDollarTv);
        twoDollarButton = findViewById(R.id.twoDollarTv);
        fiveDollarButton = findViewById(R.id.fiveDollarTv);
        tenDollarButton = findViewById(R.id.tenDollarTv);
        twentyDollarButton = findViewById(R.id.twentyDollarTv);
        fiftyDollarButton = findViewById(R.id.fiftyDollarTv);
        hundredDollarButton = findViewById(R.id.hundredDollarTv);

        fiveCentLayout = findViewById(R.id.fiveCentLayout);
        tenCentLayout = findViewById(R.id.tenCentLayout);
        twentyCentLayout = findViewById(R.id.twentyCentLayout);
        fiftyCentLayout = findViewById(R.id.fiftyCentLayout);
        oneDollarLayout = findViewById(R.id.oneDollarLayout);
        twoDollarLayout = findViewById(R.id.twoDollarLayout);
        fiveDollarLayout = findViewById(R.id.fiveDollarLayout);
        tenDollarLayout = findViewById(R.id.tenDollarLayout);
        twentyDollarLayout = findViewById(R.id.twentyDollarLayout);
        fiftyDollarLayout = findViewById(R.id.fiftyDollarLayout);
        hundredDollarLayout = findViewById(R.id.hundredDollarLayout);
        clearDollarLayout = findViewById(R.id.clearDollarLayout);

        paymentsEnterInvoiceTv = findViewById(R.id.paymentsEnterInvoiceTv);
        backPaymentTv = findViewById(R.id.backPaymentTv);
        selectPrinterTv = findViewById(R.id.selectPrinterTv);
        qrGenerateTv = findViewById(R.id.qrGenerateTv);
        tipAmountTv = findViewById(R.id.tipAmountTv);

        clearButton = findViewById(R.id.clearTv);

        totalPayingsTv = findViewById(R.id.totalPayingsTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        totalAmountTv = findViewById(R.id.totalAmountTv);
        totalDiscountTv = findViewById(R.id.totalDiscountTv);
        totalPayableTv = findViewById(R.id.totalPayableTv);
        totalBalanceTv = findViewById(R.id.totalBalanceTv);
        changeReturnTv = findViewById(R.id.changeReturnTv);

        totalPayingsTv.setText("$" + String.valueOf(round(totalPayings, 2)));
        totalPayableTv.setText("$" + String.valueOf(round(totalPayable, 2)));
        changeReturnTv.setText("$" + String.valueOf(round(changeReturn, 2)));
        totalAmountTv.setText("$" + String.valueOf(round(totalAmount, 2)));
        totalItemsTv.setText("" + qty);
        totalDiscountTv.setText("$" + String.valueOf(round(discount, 2)));
        totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));

        addDeliveryChargesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addDeliveryChargesEditText.getText().toString().isEmpty()) {
                    showLongSnackBar("Please add delivery charges");
                } else {
                    deliveryCharges = Double.parseDouble(addDeliveryChargesEditText.getText().toString());
                    totalPayable = totalPayable + deliveryCharges;
                    deliverChargesTv.setText("$ " + deliveryCharges);
                    totalPayableTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                    totalBalanceTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                }
            }
        });

        cashPaymentTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                paymentType = "1";
                cashPaymentTextView.setBackgroundResource(R.color.colorPrimaryDark);
                cashPaymentTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                cardPaymentTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
                cardPaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));

//                tipPopup();

                String s = String.valueOf(String.valueOf(round(totalPayable, 2)));
                String[] parts = s.split("\\."); // escape .
                String part1 = parts[0];
                String part2 = parts[1];

//                Log.d("jhgsad", part2);

                if (part2.length() > 1) {
                    String value2 = part2.substring(0, 1);
//                    Log.d("lksjdkl", value2);
                    String value = part2.substring(part2.length() - 1);
//                    Log.d("akjshjk", value);
                    char first = part2.charAt(0);
//                    Log.d("dfdgfd", String.valueOf(first));
                    if (value2.equalsIgnoreCase("9")) {
                        if (value.equalsIgnoreCase("6") || value.equalsIgnoreCase("7") ||
                                value.equalsIgnoreCase("8") || value.equalsIgnoreCase("9")) {
                            part1 = String.valueOf(Integer.parseInt(part1) + 1);
//                            Log.d("kjashdj", part1);
                            part2 = "0";
                        }
                    } else {

                        if (value.equalsIgnoreCase("0") || value.equalsIgnoreCase("5")) {

                        } else {
                            if (value.equalsIgnoreCase("1") || value.equalsIgnoreCase("2") ||
                                    value.equalsIgnoreCase("3") || value.equalsIgnoreCase("4")) {
                                value = "5";
                                StringBuilder sb = new StringBuilder();
                                sb.append(value2);
                                sb.append(value);
                                part2 = sb.toString();
//                                Log.d("kjashdj", part2);
                            } else if (value.equalsIgnoreCase("6") || value.equalsIgnoreCase("7") ||
                                    value.equalsIgnoreCase("8") || value.equalsIgnoreCase("9")) {
                                part2 = String.valueOf(Integer.parseInt(part2.substring(0, 1)) + 1);
//                                Log.d("kjashdj", part2);
                            }
                        }
                    }

                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(part1);
                    sb2.append(".");
                    sb2.append(part2);

                    totalPayable = Double.parseDouble(sb2.toString());

                    totalPayableTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                    totalBalanceTv.setText("$" + String.valueOf(round(totalPayable, 2)));

                    calculationEditText.setText("" + totalPayings);
                    onEqual();

                }

            }
        });

        findViewById(R.id.addTipTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipPopup();
            }
        });

        cardPaymentTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                paymentType = "2";
                cardPaymentTextView.setBackgroundResource(R.color.colorPrimaryDark);
                cardPaymentTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                cashPaymentTextView.setBackgroundResource(R.drawable.rectangle_ash_line);
                cashPaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));

//                tipAmount = 0.0;
//                tipAmountTv.setText("$ " + tipAmount);
                totalPayable = getIntent().getExtras().getDouble("totalPayable");
                totalPayable = totalPayable + tipAmount;
                totalPayableTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                totalBalanceTv.setText("$" + String.valueOf(round(totalPayable, 2)));

                calculationEditText.setText("0");
                onEqual();
                sendCardCartData();
//                paymentTypePopup();

            }
        });

        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "1");
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "2");
            }
        });

        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "3");
            }
        });

        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "4");
            }
        });

        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "5");
            }
        });

        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "6");
            }
        });

        sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "7");
            }
        });

        eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "8");
            }
        });

        nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "9");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + "0");
            }
        });

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText(calculationEditText.getText() + ".");
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText("");
                onEqual();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
                    calculationEditText.setText("");
                    addition = true;
                } catch (NumberFormatException e) {
                    showLongSnackBar("Invalid expression");
                }
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
                    calculationEditText.setText("");
                    subtraction = true;
                } catch (NumberFormatException e) {
                    showLongSnackBar("Invalid expression");
                }
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
                    calculationEditText.setText("");
                    multiplication = true;
                } catch (NumberFormatException e) {
                    showLongSnackBar("Invalid expression");
                }
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
                    calculationEditText.setText("");
                    division = true;
                } catch (NumberFormatException e) {
                    showLongSnackBar("Invalid expression");
                }
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mValue2 = Double.parseDouble(calculationEditText.getText().toString());
                    if (addition == true) {
                        add = mValue1 + mValue2;
                        calculationEditText.setText(String.valueOf(add));
                    }
                    if (subtraction == true) {
                        sub = mValue1 - mValue2;
                        calculationEditText.setText(String.valueOf(sub));
                    }
                    if (multiplication == true) {
                        mul = mValue1 * mValue2;
                        calculationEditText.setText(String.valueOf(mul));
                    }
                    if (division == true) {
                        div = mValue1 / mValue2;
                        calculationEditText.setText(String.valueOf(div));
                    }

                    onEqual();

                } catch (NumberFormatException e) {
                    showLongSnackBar("Invalid Expression");
                }

            }
        });

        oneDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 1;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        twoDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 2;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        fiveDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 5;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        tenDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 10;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        twentyDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 20;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        fiftyDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 50;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        hundredDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                float result = Float.parseFloat(calculationEditText.getText().toString()) + 100;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        fiveCentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                double result = Float.parseFloat(calculationEditText.getText().toString()) + 0.05;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        tenCentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                double result = Float.parseFloat(calculationEditText.getText().toString()) + 0.10;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        twentyCentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                double result = Float.parseFloat(calculationEditText.getText().toString()) + 0.20;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        fiftyCentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculationEditText.getText().toString().isEmpty()) {
                    calculationEditText.setText("0");
                }
                double result = Float.parseFloat(calculationEditText.getText().toString()) + 0.50;
                calculationEditText.setText(String.valueOf(result));
                onEqual();
            }
        });

        clearDollarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationEditText.setText("0");
                onEqual();
            }
        });

        paymentsEnterInvoiceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("dsjhfkjd", priceId);
                if (priceId.equalsIgnoreCase("0")) {

                    if (paymentType.equalsIgnoreCase("0")) {
                        showLongSnackBar("Please choose payment type Cash or Card");
                    } else {
                        if (balance <= 0) {
                            if (orderNo == null) {
                                sendCartData();
                            } else {
                                getSaveOrderCheckoutResponse();
                            }
                        } else {
                            showLongSnackBar("Please pay complete amount");
                        }
                    }

                } else {
                    if (orderNo == null) {
                        sendCartData();
                    } else {
                        getSaveOrderCheckoutResponse();
                    }
                }
            }
        });

        selectPrinterTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(NewPaymentActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
//                        Log.d("fdfsd", "msg.toString()");
                        ListPairedDevices();
                        selectBluetoothDialog();
                    }
                }
            }
        });

        backPaymentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

//                Thread t = new Thread() {
//                    public void run() {
//                        try {
//                            OutputStream os = mBluetoothSocket
//                                    .getOutputStream();
//
//                            BILL = ITEM_BILL_1;
//
//                            byte[] arrayOfByte1 = {27, 33, 0};
//                            Log.d("final bill", BILL);
//                            byte[] format = {27, 33, 0};
//                            // Bold
//                            format[2] = ((byte) (0x8 | arrayOfByte1[2]));
//                            os.write(format);
//                            os.write(BILL.getBytes(), 0, BILL.getBytes().length);
//                            //This is printer specific code you can comment ==== > Start
//
//                            // Setting height
//                            int gs = 29;
//                            os.write(intToByteArray(gs));
//                            int h = 104;
//                            os.write(intToByteArray(h));
//                            int n = 162;
//                            os.write(intToByteArray(n));
//
//                            // Setting Width
//                            int gs_width = 19;
//                            os.write(intToByteArray(gs_width));
//                            int w = 58;
//                            os.write(intToByteArray(w));
//                            int n_width = 2;
//                            os.write(intToByteArray(n_width));
//
//                        } catch (Exception e) {
//                            Log.e("MainActivity", "Exe ", e);
//                        }
//                    }
//                };
//                t.start();
            }
        });

        qrGenerateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upiPaymentPopup();
            }
        });

        handlerCall();

//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    EscPosPrinter printer = new EscPosPrinter(new TcpConnection(ip, port), 203, 65f, 42);
////                    printer.openCashBox();
//                    getCashBoxOpen();
//                    printer.disconnectPrinter();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

    final Handler handler = new Handler();

    public void handlerCall() {
        final Runnable r = new Runnable() {
            public void run() {

//                Log.d("checkotuid", checkoutId);

                if (checkoutId.equalsIgnoreCase("1234")) {

//                    showShortSnackBar("Wait");

                } else {
                    if (CommonUtilities.checkConn(getApplicationContext())) {
                        try {
                            if (progressDialog.isShowing() && progressDialog != null) {

                            } else {
                                progressPopup();
                            }
                        } catch (NullPointerException e) {
                            progressPopup();
                        }
                        getTerminalCheckoutResponse();
                    } else {

                    }
                }
                handler.postDelayed(this, 3000);
            }
        };

        handler.postDelayed(r, 5000);
    }

    public void progressPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progress_popup, null);
        builder.setView(dialogView);
        progressDialog = builder.create();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(progressDialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        progressDialog.getWindow().setAttributes(layoutParams);

        AppCompatButton closeButton = (AppCompatButton) progressDialog.findViewById(R.id.yesButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkoutId.equalsIgnoreCase("1234")) {
                    cancelTerminalCheckoutResponse();
                }
            }
        });

    }

    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.

        if (calculationEditText.getText().toString().isEmpty()) {
            amount = Double.parseDouble("0");
        } else {
            amount = Double.parseDouble(calculationEditText.getText().toString());
        }

        // Create an Expression (A class from exp4j library)
        totalPayingsTv.setText("$" + amount);
        totalPayings = amount;

        balance = totalPayable;
        balance = balance - amount;

        if (balance > 0) {
            totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));
        } else {
            balance = 0.00;
            totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));
        }
        changeReturn = amount - totalPayable;
        if (changeReturn > 0) {
            changeReturnTv.setText("$" + String.valueOf(round(changeReturn, 2)));
        } else {
            changeReturn = 0.00;
            changeReturnTv.setText("$" + String.valueOf(round(changeReturn, 2)));
        }

    }

    public void tipPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.tip_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        final AppCompatEditText tipAmountInput = dialogView.findViewById(R.id.customerTipEt);

        dialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipAmount = 0.0;
                tipAmountTv.setText("$ " + tipAmount);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipAmountInput.getText().toString().isEmpty()) {
                    showLongSnackBar("Please enter amount");
                } else {
                    tipAmount = Double.parseDouble(tipAmountInput.getText().toString());
                    totalPayable = totalPayable + tipAmount;
                    tipAmountTv.setText("$ " + tipAmount);
                    totalPayableTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                    totalBalanceTv.setText("$" + String.valueOf(round(totalPayable, 2)));
                }

                dialog.dismiss();
            }
        });

    }

    public void paymentTypePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_type_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.findViewById(R.id.othersButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendCardCartData();
                othersPaymentTypePopup();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.squareupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCardCartData();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.closeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void othersPaymentTypePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.others_payment_type_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        TextView amountTextView = dialog.findViewById(R.id.bill_amount_text_view);
        amountTextView.setText("Order Amount  :  $" + String.valueOf(round(totalAmount, 2)));

        dialog.findViewById(R.id.rejectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendCardCartData();
//                sendDataOtherPaymentOrder();
                successFailurePopup("Failure");
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.acceptButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataOtherPaymentOrder();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.closeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void sendCardCartData() {

        products = db.getAllCategories();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

            String catid = cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();
            String note = cn.get_note();

            addOns = new ArrayList<>();
            addOns.clear();
            addOns = db.getAllAddOns(catid);

            addOnIdList.clear();
            addOnNameList.clear();
            addOnPriceList.clear();
            addOnQtyList.clear();

            if (addOns.size() > 0) {

                for (AddOns addOn : addOns) {
                    String addOnId1 = addOn.get_addOnsId();
                    String addOnName = addOn.get_addOnsName();
                    String addOnPrice = addOn.get_addOnsPrice();
                    String addOnQty = addOn.get_addOnsQty();

//                    Log.d("lkjlgk", addOnId1 + ", name : " + addOnName + ", price : " + addOnPrice + ", qty : " + addOnQty);

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
                        sb2.append("test");
                    else {
                        sb2.append("test");
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
            }
            JsonObject productsObj = new JsonObject();

            productsObj.addProperty("item_id", catid);
            productsObj.addProperty("item_qty", productQty);
            productsObj.addProperty("item_price", price);
            productsObj.addProperty("item_note", note);
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        if (orderNo == null)
            getSaveOrderCardResponse();
        else
            getSquareUpResponse();

    }

    public void sendDataOtherPaymentOrder() {

        products = db.getAllCategories();

//        productsList.clear();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

            String catid = "" + cn.get_id();
            String productid = "" + cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();
            String note = cn.get_note();

            addOns = new ArrayList<>();
            addOns.clear();
            addOns = db.getAllAddOns(catid);

            addOnIdList.clear();
            addOnNameList.clear();
            addOnPriceList.clear();
            addOnQtyList.clear();


            if (addOns.size() > 0) {

                for (AddOns addOn : addOns) {
                    String addOnId1 = addOn.get_addOnsId();
                    String addOnName = addOn.get_addOnsName();
                    String addOnPrice = addOn.get_addOnsPrice();
                    String addOnQty = addOn.get_addOnsQty();

//                    Log.d("lkjlgk", addOnId1 + ", name : " + addOnName + ", price : " + addOnPrice + ", qty : " + addOnQty);

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
                        sb2.append("test");
                    else {
                        sb2.append("test");
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
            }
            JsonObject productsObj = new JsonObject();

            productsObj.addProperty("item_id", productid);
            productsObj.addProperty("item_qty", productQty);
            productsObj.addProperty("item_price", price);
            productsObj.addProperty("item_note", note);
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        getSaveOrderOtherPaymentResponse();

    }

    public void sendCartData() {

        products = db.getAllCategories();

//        productsList.clear();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

//            String catid = cn.get_product_id();
//            String productQty = cn.get_qty();
//            String price = cn.get_price();
//            String note = cn.get_note();

            String catid = "" + cn.get_id();
            String productid = "" + cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();
            String note = cn.get_note();

            addOns = new ArrayList<>();
            addOns.clear();
            addOns = db.getAllAddOns(catid);

            addOnIdList.clear();
            addOnNameList.clear();
            addOnPriceList.clear();
            addOnQtyList.clear();


            if (addOns.size() > 0) {

                for (AddOns addOn : addOns) {
                    String addOnId1 = addOn.get_addOnsId();
                    String addOnName = addOn.get_addOnsName();
                    String addOnPrice = addOn.get_addOnsPrice();
                    String addOnQty = addOn.get_addOnsQty();

//                    Log.d("lkjlgk", addOnId1 + ", name : " + addOnName + ", price : " + addOnPrice + ", qty : " + addOnQty);

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

//                Log.d("addosnid", addOnId);

                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < addOnNameList.size(); i++) {
                    if (i == addOnNameList.size() - 1)
                        sb2.append("test");
                    else {
                        sb2.append("test");
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
            }
            JsonObject productsObj = new JsonObject();

            productsObj.addProperty("item_id", productid);
            productsObj.addProperty("item_qty", productQty);
            productsObj.addProperty("item_price", price);
            productsObj.addProperty("item_note", note);
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        if (priceId.equalsIgnoreCase("0")) {
            if (paymentType.equalsIgnoreCase("1")) {
                getSaveOrderResponse();
            } else if (paymentType.equalsIgnoreCase("2")) {
                getSaveOrderCardResponse();
            }
        } else {
            getSaveOrderResponse();
        }

    }

    private void getSaveOrderResponse() {

        showProgressDialog();

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNo);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Cash");
        request.setChange_return("" + changeReturn);
        request.setItem_list(productsArray);
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");
        request.setOrder_type(orderType);
        request.setPaid_amt("" + totalPayable);
        request.setTot_discount_to_all_amt("" + discount);

        request.setProduct_notes(addNote);
        request.setCustomer_name(customerName);
        request.setMobile(mobileNo);

//        Log.d("saveOrderRequestcash", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.saveOrdersList("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
//                Log.d("saveOrderResponsecash", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {

                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

                        db.deleteAll();

//                        printConfirmationPopup();
//                        getBillDetails();

                        successFailurePopup("success");

                    } else {
                        hideProgressDialog();
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getSaveOrderCheckoutResponse() {

        showProgressDialog();

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Cash");
        request.setOrder_id(orderNo);
        request.setPaid_amt(totalPayable + "");
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");
        request.setOutlet_id(outlet_id);
        request.setChange_return(changeReturn + "");

        request.setTot_discount_to_all_amt("" + discount);

        request.setCustomer_name(customerName);
        request.setMobile(mobileNo);



//        Log.d("saveRequest", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.checkout("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
//                Log.d("saveResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
                        orderNo = user.getOrder_id();
                        db.deleteAll();

//                        getBillDetails();
                        successFailurePopup("success");

                    } else {
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getSaveOrderOtherPaymentResponse() {

        showProgressDialog();

//        totalAmount = totalAmount + orderTotal;
        OrderRequest request = new OrderRequest();
        request.setCustomer_id(customerId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setPaid_amt("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Others");
        request.setChange_return("0");
        request.setItem_list(productsArray);
        request.setTips("" + tipAmount);
        request.setPrice_id("100");
        request.setOrder_id(orderNo);


//        Log.d("saveOrderRequest", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.checkout("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
//                Log.d("saveOrderResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        addKotDialog.dismiss();
                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

                        db.deleteAll();

                        successFailurePopup("success");

                    } else {
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getSaveOrderCardResponse() {

        showProgressDialog();

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNo);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Card");
        request.setChange_return(changeReturn + "");
        request.setPaid_amt("" + totalPayable);
        request.setItem_list(productsArray);
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");
        request.setOrder_type(orderType + "");
        request.setTot_discount_to_all_amt(discount + "");
        request.setProduct_notes(addNote);

        request.setCustomer_name(customerName);
        request.setMobile(mobileNo);

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_id(orderNo);
        }


//        Log.d("saveOrderRequestcard", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.saveOrdersList("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
//                Log.d("saveOrderResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        addKotDialog.dismiss();
                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

//                        db.deleteAll();
//                        selectedDiscount = 0.00;
//                        updateList();

//                        sessionManager.storeOrderTrackId(orderId);

                        getSquareUpResponse();

                    } else {
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getSquareUpResponse() {

        showProgressDialog();
        SquareUpPaymentRequest request = new SquareUpPaymentRequest();
        request.setOrder_id(orderNo);
        request.setAmount("" + totalPayable);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
//        Log.d("reqqqqqq", new Gson().toJson(request));
        retrofit2.Call<SquareUpPaymentResponse> call = apiService.squareupPaymentData("1234", request);

        call.enqueue(new Callback<SquareUpPaymentResponse>() {
            @Override
            public void onResponse(retrofit2.Call<SquareUpPaymentResponse> call, Response<SquareUpPaymentResponse> response) {

                if (response.isSuccessful()) {

//                    Log.d("responseeeSque", new Gson().toJson(response));
                    hideProgressDialog();
                    SquareUpPaymentResponse user = response.body();

                    String message = user.getMessage();
                    String status = user.getStatus();
                    String success = user.getResponseCode();
                    if (status.equalsIgnoreCase("SUCCESS")) {

                        try {
                            checkoutId = user.getCheckout_id();
//                            Log.d("jshdfsd", checkoutId);

                            getTerminalCheckoutResponse();
                        } catch (NullPointerException e) {
                            showLongSnackBar("INVALID REQUEST ERROR");
                        }

                    } else {
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<SquareUpPaymentResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getTerminalCheckoutResponse() {

        GetTermicalCheckoutRequest request = new GetTermicalCheckoutRequest();
        request.setOrder_id(orderNo);
        request.setCheckout_id(checkoutId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TermicalCheckoutResponse> call = apiService.termicalCheckoutData("1234", request);

        Log.e("reqyuestTerm", new Gson().toJson(request));

        call.enqueue(new Callback<TermicalCheckoutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TermicalCheckoutResponse> call, Response<TermicalCheckoutResponse> response) {

                if (response.isSuccessful()) {
                    Log.e("responseee989", new Gson().toJson(response));
//                    hideProgressDialog();
                    final TermicalCheckoutResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();
                    String status = user.getStatus();
                    if (status.equalsIgnoreCase("SUCCESS")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String status = user.getPayment_status();
//                                Log.d("dasdagd", status);

                                if (status.equalsIgnoreCase("COMPLETED")) {
                                    db.deleteAll();
//                                    String invoiceId = user1.get(SessionManager.KEY_HOLD_ID);
//                                    deleteHoldInvoiceAfterPayment(invoiceId);
//                                    sessionManager.storeLastHoldInvoice("0");
                                    selectedDiscount = 0.00;
                                    checkoutId = "1234";
                                    progressDialog.dismiss();
//                                    getPrintBillDetails();
//                                    printConfirmationPopup();
                                    successFailurePopup("success");

                                } else if (status.equalsIgnoreCase("PENDING")) {

                                } else if (status.equalsIgnoreCase("IN_PROGRESS")) {

                                } else if (status.equalsIgnoreCase("CANCELED")) {
                                    progressDialog.dismiss();
                                    checkoutId = "1234";
                                    successFailurePopup("Failure");
                                }

                            }
                        });

                    } else {
                        checkoutId = "1234";
//                        progressDialog.dismiss();
                        successFailurePopup("Failure");
//                        showLongSnackBar(message);
                    }

                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<TermicalCheckoutResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void cancelTerminalCheckoutResponse() {

        GetTermicalCheckoutRequest request = new GetTermicalCheckoutRequest();
        request.setOrder_id(orderNo);
        request.setCheckout_id(checkoutId);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TermicalCancelCheckoutResponse> call = apiService.cancelTerminalCheckoutData("1234", request);

//        Log.d("cancelRequs", new Gson().toJson(request));

        call.enqueue(new Callback<TermicalCancelCheckoutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TermicalCancelCheckoutResponse> call, Response<TermicalCancelCheckoutResponse> response) {

                if (response.isSuccessful()) {
//                    Log.d("responseee989", new Gson().toJson(response));
//                    hideProgressDialog();
                    TermicalCancelCheckoutResponse user = response.body();
                    progressDialog.dismiss();
                    String message = user.getMessage();
                    String status = user.getStatus();
                    String success = user.getResponseCode();
                    if (status.equalsIgnoreCase("SUCCESS")) {
                        checkoutId = "1234";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });

                    } else {
                        checkoutId = "1234";
                        progressDialog.dismiss();
                        successFailurePopup("failure");
//                        showLongSnackBar(message);
                    }

                } else {
//                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<TermicalCancelCheckoutResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }


    private void getBillDetails() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        Log.e("printnreee", new Gson().toJson(request));

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, final Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {

//                    hideProgressDialog();
//                    getCashBoxOpen();

                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;
                        taxList = user.userResults;

                        orderNo = user.order_number;
                        String billingName = user.customer;
                        String companyName = user.company_name;
                        String date = user.time;
                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;

                        orderNewNo = "";
                        tableNewNo = "";

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    tax = Double.valueOf(taxList.getTax());
                                } catch (NullPointerException e) {
                                    tax = 0.00;
                                }

//                                int count = Object.keys(taxList).length;

                                if (printList.size() > 0) {
                                    userDetails = "";

                                    String orderType = user.order_type;
                                    if (orderType.equalsIgnoreCase("Take away")) {
                                        userDetails = "[L]<b>Customer Name:" + user.customer + "</font>\n" +
                                                "[L]Customer Mobile: " + user.customer_mobile + "</b>\n";
                                        textName = "Pickup Time : ";
                                    } else if (orderType.equalsIgnoreCase("Online Delivery")) {
                                        userDetails = "[L]<b>Customer Name:" + user.del_customer_name + "</font>\n" +
                                                "[L]Customer Mobile: " + user.del_customer_mobile + "\n" +
                                                "[L]Customer Address: " + user.del_customer_address + "</b>\n";
                                        textName = "Delivery Time : ";
                                    }


                                    if (Integer.parseInt(user.price_id) == 0) {

                                        orderNewNo = "[L]<b><font size='normal'>" + user.order_type + "  " + "[C]" + user.order_id + "</font></b>\n";
                                        if (orderType.equalsIgnoreCase("Dine In")) {
                                            tableNewNo = "[L]<b><font size='big'>Table No:" + user.table_number + "</font></b>";
                                        } else {
                                            tableNewNo = "";
                                        }

                                    } else {

                                        orderNewNo = "[L]<b><font size='big'>" + user.order_type + "</font></b>\n" +
                                                "[C]<b>" + user.del_customer_name + "</b>\n" +
                                                "[C]<b>" + user.del_customer_mobile + "</b>\n";
                                        tableNewNo = "";
                                    }

                                    printText = "";
                                    printText =
                                            "[L]\n" +
                                                    "[C]<b>" + user.getCompany_address() + "\n" +
                                                    "[C]<b>Tax Invoice" + "\n" +
                                                    "[C]<b>Invoice No:" + user.order_id + "\n" +
                                                    "[C]<b>ABN:" + user.abn + "\n" +
                                                    "[C]----------------------------------------" +
//                                                    "[C]" + user.company_address + "\n" +
//                                                    "[C]Ph:" + user.mobile + "\n" +
                                                    "[L]\n" +
                                                    "[L]\n";

//                                    Log.d("aahgdfg", orderNewNo + "," + tableNewNo);

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String total = user.productResults.get(i).unit_total_cost;
                                        String item_note = user.productResults.get(i).item_note;

                                        if (item.length() < 18) {
                                            for (int k = 0; k < 16; k++) {
                                                item = item + " ";
                                            }
                                        }

                                        if (item.length() >= 17) {
                                            item = item.substring(0, 17) + "..";
                                        }

                                        Double newtotal;

                                        newtotal = Double.parseDouble(total) * Double.parseDouble(qty);

                                        if (item_note.equalsIgnoreCase("0")) {
                                            printText = printText + "[C]<b><font size='normal'>" + qty + " x  " + item + "</b>[R]" + newtotal + "\n";
                                        } else {
                                            printText = printText + "[C]<b><font size='normal'> " + qty + " x  " + item + "[R]" + newtotal + "\n" +
                                                    "[C]" + item_note + "\n";
                                        }

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    String a_price = addOnsproductsList.get(j).price;
                                                    String a_qty = addOnsproductsList.get(j).qty;

                                                    Double itemCost = Integer.parseInt(a_qty) * Double.parseDouble(a_price);

                                                    printText = printText + "[C]" + a_qty + " x " + a_item + "[R]" + itemCost + "</font></b>\n";

                                                }
//
                                            }

                                        } catch (NullPointerException e) {

                                        }
                                        printText = printText + "[L]\n";
                                    }

//                                    if (orderType.equalsIgnoreCase("Online Delivery") || orderType.equalsIgnoreCase("Take away")) {
//                                        printText = printText + userDetails
//                                                +
//                                                "[C]--------------------------------\n";
//                                    }

                                    printText = printText +
                                            "[C]-----------------------------------------\n" +
                                            "[L]<b>SUB TOTAL [R]$" + user.grand_total + "</b>\n" +
                                            "[L]<b>DISCOUNT [R]$" + user.total_discount + "</b>\n" +
                                            "[L]<b>TAX [R]$" + tax + "</b>\n";
                                    printText = printText + "[L]<b>PAID [R]$" + user.paid_amount + "</b>\n" +
                                            "[L]<b>BALANCE[R]$" + user.balance_amount + "</b>\n" +
                                            "[L]<b>CHANGE RETURN[R]$" + user.change_return + "</b>\n" +
                                            "[C]-----------------------------------------\n" +
                                            "[L]<b>Customer Name:" + user.del_customer_name + "</b>\n" +
                                            "[L]<b>Customer Mobile: " + user.del_customer_mobile + "</b>\n" +
                                            "[L]<b>Date : " + user.sales_date + "-" + user.time + "</b>\n";
//                                            "[L]<b>Time Taken : " + user.time + "</ b>\n";
                                    if (orderType.equalsIgnoreCase("Online Delivery") || orderType.equalsIgnoreCase("Take away")) {
//                                        printText = printText + "[L]" + textName + user.delivery_time + "\n";
                                    }

                                    if (orderType.equalsIgnoreCase("Dine In")) {
                                        tableNewNo = "[L]<b><font size='normal'>" + "Table No : " + "  " + "[C]" + user.table_number + "</font></b>\n";
                                    } else {
                                        tableNewNo = "";

                                    }

                                    printText = printText +
                                            "[L]Clerk : " + user.getUser_name() + "\n" +
                                            "[L]Payment : <font size='normal'>" + user.payment_status + "</font></b>\n" +
                                            "[L]Order Type: <font size='normal'>" + user.order_type + "</font></b>\n" +
                                            "[L]<font size='normal'>" + tableNewNo + "</font></b>\n";
//                                            "[L]Payment Type: <font size='normal'>" + user.payment_status + "</font></b>\n";

                                    printText = printText + "[L]\n" +
//                                            "[C]<qrcode>" + user.getCompany_website() + "</qrcode>\n" +
                                            "[L]\n" +
                                            "[L]\n";


                                    doPrint();
//                                    printUsb();

//                                    autoPrintUsb();
//                                    printTcp();
                                }

                            }
                        });

                    } else {
//                        hideProgressDialog();
                        showLongSnackBar(message);
                    }

                } else {
//                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<PrintBillResponse> call, Throwable t) {
//                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }


    private void getKotBillDetails() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintKotResponse> call = apiService.printkot("1234", request);

        call.enqueue(new Callback<PrintKotResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintKotResponse> call, Response<PrintKotResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintKotResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    billingName = user.customer;
                    kotNumber = user.kot;
                    date = user.time;
                    tableNumber = user.table;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printKotList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ITEM_BILL_1 = " \n KOT    :  #" + kotNumber + "\n" +
                                        " Table  :  #" + tableNumber + "\n" +
                                        " Name   :  " + billingName + "\n" +
                                        " Time   :  " + date;
                                ITEM_BILL_1 = ITEM_BILL_1
                                        + "\n------------------------------\n";

                                ITEM_BILL_1 = ITEM_BILL_1 + String.format("%1$-1s %2$-1s ", " Qty", "  Description   ");
                                ITEM_BILL_1 = ITEM_BILL_1
                                        + "\n------------------------------";
                                for (int i = 0; i < printKotList.size(); i++) {
                                    kotAddOnsList = new ArrayList<>();
                                    kotAddOnsList = user.productResults.get(i).productAddOnsResults;

                                    String item = user.productResults.get(i).item_name;
                                    String qty = user.productResults.get(i).sales_qty;

                                    if (item.length() < 20) {
                                        for (int k = 0; k < 18; k++) {
                                            item = item + " ";
                                        }
                                    }

                                    if (item.length() >= 21) {
                                        item = item.substring(0, 21) + "..";
                                    }

                                    ITEM_BILL_1 = ITEM_BILL_1 + "\n " + String.format("%1$-1s %2$-1s", qty + " X ", item);
                                    for (int j = 0; j < kotAddOnsList.size(); j++) {
                                        String a_item = kotAddOnsList.get(j).addon_name;
                                        String a_qty = kotAddOnsList.get(j).qty;

                                        if (a_item.length() < 20) {
                                            for (int k = 0; k < 18; k++) {
                                                a_item = a_item + " ";
                                            }
                                        }

                                        if (a_item.length() >= 21)
                                            a_item = a_item.substring(0, 21) + "..";
                                        ITEM_BILL_1 = ITEM_BILL_1 + "\n " + String.format("%1$-1s %2$-1s", "+" + a_qty + " X ", a_item);
                                    }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                }
                                ITEM_BILL_1 = ITEM_BILL_1
                                        + "\n-------------------------------";
//                                Log.d("jfdhj", ITEM_BILL_1);

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
            public void onFailure(Call<PrintKotResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    @SuppressLint("MissingPermission")
    private void ListPairedDevices() {
        @SuppressLint("MissingPermission") Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void selectBluetoothDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewPaymentActivity.this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bluetooth_saved_popup, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        setResult(Activity.RESULT_CANCELED);
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        ListView mPairedListView = (ListView) dialogView.findViewById(R.id.paired_devices);
        mPairedListView.setAdapter(mPairedDevicesArrayAdapter);
        mPairedListView.setOnItemClickListener(mDeviceClickListener);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();

        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                mPairedDevicesArrayAdapter.add(mDevice.getName() + "\n" + mDevice.getAddress());


            }
        } else {
            String mNoDevices = "None Paired";//getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(mNoDevices);
        }
        alertDialog.show();
    }

    public void upiPaymentPopup() {

        if (upiPaymentPopup == true) {

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.activity_qr_payment, null);
            builder.setView(dialogView);
            qrPaymentDialog = builder.create();
            qrPaymentDialog.show();
            qrPaymentDialog.setCancelable(false);
            qrPaymentDialog.setCanceledOnTouchOutside(false);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int displayWidth = displayMetrics.widthPixels;
            int displayHeight = displayMetrics.heightPixels;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(qrPaymentDialog.getWindow().getAttributes());
            int dialogWindowWidth = (int) (displayWidth * 0.80f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
            int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.width = dialogWindowWidth;
            layoutParams.height = dialogWindowHeight;
            qrPaymentDialog.getWindow().setAttributes(layoutParams);

            upiPaymentPopup = true;

            final AppCompatImageView closeTextView = qrPaymentDialog.findViewById(R.id.closeIcon);
            final AppCompatTextView amountTextView = qrPaymentDialog.findViewById(R.id.amountTextView);

            amountTextView.setText("To Pay \n" + "$" + totalAmount);

            ImageView qrDisplayImageView = (ImageView) qrPaymentDialog.findViewById(R.id.qrDisplayImageView);

            final QRCodeWriter writer = new QRCodeWriter();

            URL = getUPIString("9030636373@ybl", "payeename", orderNo, "test", totalAmount + "", "INR");

//            Log.d("urlllllll", URL);
            BitMatrix bitMatrix = null;
            try {

                bitMatrix = writer.encode(URL, BarcodeFormat.QR_CODE, 512, 512);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrDisplayImageView.setImageBitmap(bmp);
            image = bmp;

            closeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    qrPaymentDialog.dismiss();
                    upiPaymentPopup = false;
                }
            });
        }

    }

    private String getUPIString(String payeeAddress, String payeeName, String trxnRefId,
                                String trxnNote, String payeeAmount, String currencyCode) {
        String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName
                + "&tr=" + trxnRefId
                + "&tn=" + trxnNote + "&am=" + payeeAmount + "&cu=" + currencyCode;

//        Log.d("upiiiirrr", UPI);
        return UPI.replace(" ", "+");

    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @SuppressLint("MissingPermission")
        public void onItemClick(AdapterView<?> mAdapterView, View mView, int mPosition, long mLong) {
            try {

                mBluetoothAdapter.cancelDiscovery();
                String mDeviceInfo = ((TextView) mView).getText().toString();
                String mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length() - 17);
                Log.v(TAG, "Device_Address " + mDeviceAddress);
                mBluetoothDevice = mBluetoothAdapter
                        .getRemoteDevice(mDeviceAddress);
                mBluetoothConnectProgressDialog = ProgressDialog.show(NewPaymentActivity.this,
                        "Connecting...", mBluetoothDevice.getName() + " : "
                                + mBluetoothDevice.getAddress(), true, false);
                Thread mBlutoothConnectThread = new Thread(NewPaymentActivity.this);
                mBlutoothConnectThread.start();
                alertDialog.dismiss();

            } catch (Exception ex) {

            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();

            try {
                mBluetoothSocket.connect();
                Log.e("", "Connected");
            } catch (IOException e) {
                Log.e("", e.getMessage());
                try {
                    Log.e("", "trying fallback...");

                    mBluetoothSocket = (BluetoothSocket) mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mBluetoothDevice, 1);
                    mBluetoothSocket.connect();

                    Log.e("", "Connected");
                } catch (Exception e2) {
                    Log.e("", "Couldn't establish Bluetooth connection!");
                }
            }
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(NewPaymentActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Log.d("dskjflkj", "Called");
        upiPaymentPopup = false;
    }

    public void successFailurePopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.success_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView logo = dialog.findViewById(R.id.logosuccess);
        final TextView success = dialog.findViewById(R.id.success);
        final TextView orderIdTextView = dialog.findViewById(R.id.orderIdTextView);
        final TextView orderTotalTextView = dialog.findViewById(R.id.orderTotalTextView);
        final TextView printBillButton = dialog.findViewById(R.id.printBillButton);

        orderIdTextView.setText("Order Number : " + orderNo);
        orderIdTextView.setVisibility(View.GONE);
        orderTotalTextView.setText("Change Return : $" + String.valueOf(round(changeReturn, 2)));
        orderTotalTextView.setVisibility(View.VISIBLE);

        if (message.equalsIgnoreCase("success")) {
            success.setText("Payment Successful");
            logo.setBackgroundResource(R.mipmap.success_icon);
            success.setTextColor(Color.parseColor("#5c9b45"));
            printBillButton.setVisibility(View.VISIBLE);
        } else if (message.equalsIgnoreCase("Failure")) {
            success.setText("Sorry! Order Failed");
            logo.setBackgroundResource(R.mipmap.failure);
            success.setTextColor(Color.parseColor("#fe1a1a"));
            printBillButton.setVisibility(View.GONE);
        }

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.findViewById(R.id.shopAgainBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.findViewById(R.id.printBillButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBillDetails();
                dialog.dismiss();
                finish();
            }
        });

    }

    private void printIt(String thisData) {
        BluetoothSocket socket = null;
        byte[] data = thisData.getBytes();


//        Log.d("gettttt", "" + data);

        //Get BluetoothAdapter
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Open Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get sunmi InnerPrinter BluetoothDevice
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);
        if (device == null) {

            NewPaymentActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Make Sure Bluetooth have InnterPrinter", Toast.LENGTH_LONG).show();
                }
            });

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

    public void printData() {
        Thread t = new Thread() {
            public void run() {
                try {

                    BILL = ITEM_BILL;
                    BILL = BILL + "\n ";
                    byte[] arrayOfByte1 = {27, 33, 0};
//                    Log.d("final bill", BILL);
                    byte[] format = {27, 33, 0};

                    printIt(BILL);

                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    public void printConfirmationPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPaymentActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.print_confirmation_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBillDetails();
                dialog.dismiss();
            }
        });

    }

//    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (NewPaymentActivity.ACTION_USB_PERMISSION.equals(action)) {
//                synchronized (this) {
//                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (usbManager != null && usbDevice != null) {
//                            // printIt(new UsbConnection(usbManager, usbDevice));
//                            new AsyncUsbEscPosPrint(context).execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));
//                        }
//                    }
//                }
//            }
//        }
//    };

    public void doPrint() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, PrintMainActivity.PERMISSION_BLUETOOTH);
            } else {

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

                    if ((majDeviceCl == BluetoothClass.Device.Major.IMAGING && (deviceCl == 1664 || deviceCl == BluetoothClass.Device.Major.IMAGING)) || device.getName().equals("InnerPrinter")) {
                        printersTmp[i++] = new BluetoothConnection(device);
                    }
                }

                BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
                System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);

                BluetoothConnection connection = bluetoothPrinters[0];
//                Log.d("kldj", connection + "");

                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 65f, 32);

                    final String text1 = "[L]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                            this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_white_one_line,
                                    DisplayMetrics.DENSITY_HIGH, getTheme())) + "</img>\n" + printText;

//                    Log.d("printtttt", printText);

//                    getCashBoxOpen();

                    if (!priceId.equalsIgnoreCase("0")) {

                        printer.printFormattedTextAndCut(text1, 5);

                    } else {

                        printer.printFormattedTextAndCut(text1, 5);
                        SunmiPrintHelper.getInstance().openCashBox();

//                        printer.printFormattedText(text1);
//                        printer.printFormattedTextAndOpenCashBox(text1, 40);
                    }

                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }


    public void openCashBox() {

//        Log.d("coming", "coming");
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        sunmiPrinterService.openCashBox();
    }

    private void handleRemoteException(RemoteException e) {
        //TODO process when get one exception
    }

    public void printUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
        if (usbConnection != null && usbManager != null) {
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(NewPaymentActivity.ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(NewPaymentActivity.ACTION_USB_PERMISSION);
            registerReceiver(this.usbReceiver, filter);
            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
        }
    }

    public void autoPrintUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
        if (usbConnection != null && usbManager != null) {
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(NewPaymentActivity.ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(NewPaymentActivity.ACTION_USB_PERMISSION);
            registerReceiver(this.usbReceiver, filter);
            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
        }
    }

    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (NewPaymentActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    mConnection = mUsbManager.openDevice(usbDevice);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {

                            //  printIt(new UsbConnection(usbManager, usbDevice));
                            new AsyncUsbEscPosPrint(context).execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));

                            try {

                            } catch (RuntimeException e) {


                            }
                        }
                    }
                }
            }
        }
    };

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 60f, 42);
//        Log.d("printtext", printText);
//        getCashBoxOpen();
        openCashBox();

        return printer.setTextToPrint("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_white, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" + printText);

//        return printer.setTextToPrint("[C]" + printText);

    }

    public void printTcp() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    EscPosPrinter printer = new EscPosPrinter(new TcpConnection("192.168.1.1", 9100), 203, 65f, 42);
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
//                            .setTitle("Bad selected encoding")
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

    private void getCashBoxOpen() {
        EscPosPrinter printer = null;
        try {

            printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
//            UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
//            UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
            printer.printFormattedTextAndOpenCashBox(
//                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_bw, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                    "[L]\n" +
                            "[L]\n", 5
            );

//            autoPrintUsb();
//            doPrint();
//            Log.d("openinggg", "openingg");
            printer.disconnectPrinter();
        } catch (EscPosConnectionException | EscPosParserException | EscPosEncodingException | EscPosBarcodeException e) {
            e.printStackTrace();
        }
    }


}
