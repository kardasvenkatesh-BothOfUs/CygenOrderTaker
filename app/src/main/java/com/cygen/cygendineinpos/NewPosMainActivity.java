package com.cygen.cygendineinpos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.display.DisplayManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cygen.cygendineinpos.Printer.SunmiPrintHelper;
import com.cygen.cygendineinpos.Retrofit.RetrofitBuilder;
import com.cygen.cygendineinpos.Retrofit.RetrofitInterface;
import com.cygen.cygendineinpos.RetrofitRequest.AddCategoryRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddCustomerRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddProductRequest;
import com.cygen.cygendineinpos.RetrofitRequest.CashInOutRequest;
import com.cygen.cygendineinpos.RetrofitRequest.CashInRequest;
import com.cygen.cygendineinpos.RetrofitRequest.CategoryRequest;
import com.cygen.cygendineinpos.RetrofitRequest.ChangeStatusRequest;
import com.cygen.cygendineinpos.RetrofitRequest.CouponsRequest;
import com.cygen.cygendineinpos.RetrofitRequest.GetTermicalCheckoutRequest;
import com.cygen.cygendineinpos.RetrofitRequest.HoldRequest;
import com.cygen.cygendineinpos.RetrofitRequest.LoginRequest;
import com.cygen.cygendineinpos.RetrofitRequest.OrderRequest;
import com.cygen.cygendineinpos.RetrofitRequest.OrdersRequest;
import com.cygen.cygendineinpos.RetrofitRequest.PrintKotRequest;
import com.cygen.cygendineinpos.RetrofitRequest.ProductsRequest;
import com.cygen.cygendineinpos.RetrofitRequest.ReservationStatusRequest;
import com.cygen.cygendineinpos.RetrofitRequest.SquareUpPaymentRequest;
import com.cygen.cygendineinpos.RetrofitRequest.StateListRequest;
import com.cygen.cygendineinpos.RetrofitRequest.TableShiftRequest;
import com.cygen.cygendineinpos.RetrofitResponse.AddCategoryResponse;
import com.cygen.cygendineinpos.RetrofitResponse.AddCustomerResponse;
import com.cygen.cygendineinpos.RetrofitResponse.AddOnsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.AddProductResponse;
import com.cygen.cygendineinpos.RetrofitResponse.BannersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CashInOutResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CashInResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CategoryResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CountryListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CouponsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CustomerResponse;
import com.cygen.cygendineinpos.RetrofitResponse.DayEndReportResponse;
import com.cygen.cygendineinpos.RetrofitResponse.GroupListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LastInvoiceListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LoginResponse;
import com.cygen.cygendineinpos.RetrofitResponse.NewTableListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OrdersCountResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OrdersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintBillResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintKotResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ProductsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.RefundReasonsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReservationResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReservationStatusResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReturnListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.RunningOrdersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SaveOrderResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SquareUpPaymentResponse;
import com.cygen.cygendineinpos.RetrofitResponse.StateListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.StoreUsersListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TableLayoutResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TableListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TableResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TableShiftResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TaxResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TermicalCancelCheckoutResponse;
import com.cygen.cygendineinpos.RetrofitResponse.TermicalCheckoutResponse;
import com.cygen.cygendineinpos.database.AddOns;
import com.cygen.cygendineinpos.database.DataBaseHandler;
import com.cygen.cygendineinpos.database.Product;
import com.dantsu.escposprinter.EscPosPrinter;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPosMainActivity extends BaseActivity {

    androidx.appcompat.widget.LinearLayoutCompat cashLayoutCompat;

    RecyclerView productsRecyclerView, categoryListRecyclerView, subCategoryListRecyclerView, cartRecyclerView, tablesRecyclerView, couponsRecyclerView, ordersRecyclerView, runningOrdersListView;

    RecyclerView areaRecyclerView;
    RecyclerView areaTablesRecyclerView;
    RecyclerView couponsListRecyclerView;

    RelativeLayout roomOneLayout;
    RelativeLayout roomTwoLayout;
    RelativeLayout roomThreeLayout;

    String catId = "101", name = null, userId = null;
    String subCatId = null;
    String layout_id = null;

    List<CategoryResponse.ProductListResponse> categoryList;
    List<CategoryResponse.ProductListResponse> subCategoryList;
    List<TableResponse.ProductListResponse> tablesList;
    List<ProductsResponse.ProductListResponse> productsList;
    List<AddOnsResponse.ProductAddOnsGroupListResponse> addOnsList;
    List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> addOnsOnlyList;
    List<RunningOrdersResponse.ProductListResponse> runningOrdersList;
    List<ReservationResponse.ReservationsListResponse> reservationsList;
    List<OrdersResponse.OrdesListResponse> ordersList;
    List<TableListResponse.AreaListResponse> areaTablesList;
    List<NewTableListResponse.AreaListResponse> newareaTablesList;
    List<TableLayoutResponse.AreaListResponse> areasList;
    List<GroupListResponse.AreaListResponse> groupListData;
    List<StoreUsersListResponse.UsersListResponse> storeUsersListData;

    SessionManager sessionManager;

    productsRecyclerAdapter LoansListAdapter;
    AddOnsRecyclerAdapter addOnsListAdapter;
    AddOnsTwoRecyclerAdapter addOnsTwoListAdapter;
    AddOnsThreeRecyclerAdapter addOnsThreeListAdapter;
    AddOnsFourRecyclerAdapter addOnsFourListAdapter;
    AddOnsFiveRecyclerAdapter addOnsFiveListAdapter;
    AddOnsSixRecyclerAdapter addOnsSixListAdapter;

    String productName = null, productPrice = null, productId = null;

    List<String> addOnsPrimaryIdList, addOnGroupIdList, addOnKotIdList, addOnNameList, addOnIdList, maxAddOnIdList, addOnPriceList, productIdList, addOnQtyList;
    List<String> addOnGroupIdListOne, addOnKotIdListOne, addOnNameListOne, addOnIdListOne, addOnPriceListOne, productIdListOne, addOnQtyListOne;
    String addOnId = null, addOnName = null, addOnPrice = null, addOnQty = null, addOnGroupId = null, addOnKot = null, addOnsNotes = null, addOnPrimary = null;

    DataBaseHandler db;

    int count = 0;

    private static final String ID = "id";
    private static final String TAG_ID = "productId";
    private static final String TAG_NAME = "productName";
    private static final String TAG_QUANTITY = "productQty";
    private static final String TAG_NOTE = "productNote";
    private static final String TAG_PRICE = "productPrice";
    private static final String TAG_ADDON_PRICE = "productAddOnPrice";
    private static final String TAG_ADDON_NAME = "productAddOnName";
    private static final String TAG_ADDON_ID = "productAddOnId";
    private static final String TAG_ADDON_QTY = "productAddOnQty";
    private static final String TAG_ADDON_GROUP_ID = "productAddOnGroupId";
    private static final String TAG_KOT = "productKot";
    private static final String TAG_PRIMARY = "productPrimary";

    List<Product> products;
    List<AddOns> addOns;
    ArrayList<HashMap<String, String>> productsWithAddOnsList;

    CartListAdapter cartListAdapter;
    homeCatRecyclerAdapter categoryAdapter;
    tableRecyclerAdapter tableAdapter;
    runningTableRecyclerAdapter runningTablesAdapter;
    AppCompatTextView quantityTextView, subTotalTextView, discountTextView, taxTextView, grandTotalTextView;

    Double subTotal = 0.00;
    Double totalAmount = 0.00;
    String tableNumber = "0";
    String priceId = "0";
    String orderNo = null;
    String orderNumber = null;

    Double orderTotal = 0.00;

    AppCompatImageView refundImageView;

    AppCompatSpinner customerSpinner;
    AppCompatSpinner categorySpinner;

    LinearLayoutCompat discountLayout;
    LinearLayoutCompat updateItemLayout;
    LinearLayoutCompat runningOrdersLayout;
    LinearLayoutCompat signOutLayout;
    LinearLayoutCompat voidLayout;
    LinearLayoutCompat placeOrderLayout;
    LinearLayoutCompat tableLayout;
    LinearLayoutCompat tableTopOrdersLayout;
    LinearLayoutCompat takeAwayOrdersLayout;
    LinearLayoutCompat deliveryOrdersLayout;
    LinearLayoutCompat reservationLayout;

    List<String> customerNameList;
    List<String> categoriesList;
    List<String> customerIdList;
    List<CustomerResponse.ProductListResponse> customerList;
    String customerId = null, customerName = null;
    String discount = null;
    AppCompatButton searchButton;
    AppCompatEditText searchEditText;
    String searchWord = null, productStatus = "-1";

    private SpinnerDialog spinnerDialog;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> catIdList = new ArrayList<>();

    String discountType = null;
    String saveOrderType = "0";

    homeSubCatRecyclerAdapter subCategoryAdapter;

    JSONArray productsArray;
    List<PrintBillResponse.ProductListResponse> orderList;
    List<PrintKotResponse.ProductListResponse> printKotList;
    List<CouponsResponse.ProductListResponse> couponsList;
    List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> addOnsOrderList;
    List<PrintKotResponse.ProductListResponse.ProductAddOnsListResponse> kotAddOnsList;

    Double disc = 0.00;
    Double grandTotal = 0.00;
    Double tax = 0.00;
    String updateData = "0";

    ProgressBar mProgressBar;
    AppCompatTextView noRecordsTextView;
    int index = 1;
    String addOnDbId = null;

    List<String> HeadingNameList, HeadingIdList, HeadingPriceList, HeadingQtyList;
    HashMap<String, List<String>> listDataChild, listDataQtyChild, listDataIdChild, listDataPriceChild;
    List<List<String>> dataList, dataIdList, dataPriceList, dataQtyList;
    List<String> list, idList, priceList, qtyList;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;

    String cashInOutAmount = null, cashInOutDescription = null, shiftInOutAmount = null, shiftInOutDesc = null;
    LinearLayoutCompat cashInOutLayout, shiftInOutLayout;
    AlertDialog cashInOutalertDialog;
    AreaWiseTablesRecyclerAdapter areaTablesRecyclerAdapter;
    AreaTableLayoutRecyclerAdapter areasRecyclerAdapter;

    String amount = null;
    String cashType = null;
    String orderType = "-1";
    String newOrderType = "-1";

    ArrayList<String> subCatIdList = new ArrayList<>();

    String outlet_id = null;

    String checkoutId = "1234";

    AppCompatTextView noDataTextView;
    AppCompatTextView backTextView;
    AppCompatTextView areaNoDataTextView;
    ProgressBar progressBar;
    ProgressBar areaProgressBar;

    List<LastInvoiceListResponse.ProductListResponse> lastInvoiceList;
    List<DayEndReportResponse.HourlyReportResponse> hourlyReportList;
    List<DayEndReportResponse.CategoryReportResponse> categoryReportList;
    List<DayEndReportResponse.PaymentReportResponse> paymentsReportList;

    List<BannersResponse.UserListResponse> bannersList;

    AppCompatTextView tableTopOrdersCountTextView;
    AppCompatTextView takeAwayOrdersCountTextView;
    AppCompatTextView deliveryOrdersCountTextView;

    Double selectedDiscount = 0.00;
    int printValue = 0;

    String ITEM_BILL = "";
    String BILL = "";

    String totalAmt, paidAmt;

    public DisplayManager displayManager = null;
    public Display[] presentationDisplays = null;

    List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> addOnsproductsList;

    List<PrintBillResponse.ProductListResponse> printList;

    List<String> countryNameList;
    List<String> countryIdList;
    List<String> stateNameList;
    List<String> stateIdList;

    TextView paymentsBackTv;
    Spinner countrySpinner, stateSpinner;
    String mobile, email, phone, city, postCode, address;
    String countryId;
    String stateId;
    String countryName;
    String stateName;

    String product_name;
    String product_price;

    List<CountryListResponse.ProductListResponse> countryList;
    List<StateListResponse.ProductListResponse> stateList;

    String addProductName, addProductPrice;

    AppCompatEditText productNameEt, productPriceEt;
    AppCompatButton submitAddProductBtn, addProductBtn;

    RecyclerView miscProductsRecyclerview;
    LinearLayout miscProductsLayout, addProductLayout;

    RecyclerView updateProductRecyclerview;
    updateProductsRecyclerAdapter updateProductsRecyclerAdapter;

    String customer_name;
    String customer_mobile;
    String customer_address;
    String customer_email;
    String customer_post_code;

    RelativeLayout addOnsDataLayout;
    RelativeLayout categoriesLayout;
    RelativeLayout paymentLayout;
    LinearLayout customerDetailsLayout;
    int kotId = 100;
    String product_id;
    String selectedProductId;

    AppCompatTextView productNameTextView;

    AppCompatTextView addOnHeaderNameFirst;
    AppCompatTextView productPriceTextView;

    AppCompatTextView addTwoHeaderName;
    AppCompatTextView addOnHeaderNameThird;

    RecyclerView addOnsRecyclerView;
    RecyclerView choiceBreadRecylerView;
    RecyclerView exclusionRecyclerView;

    RelativeLayout addOnsLayout;
    RelativeLayout choiceBreadLayout;
    RelativeLayout exclusionLayout;

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
    Double totalPayable, totalPayings = 0.00, changeReturn = 0.00;
    Double balance = 0.00;
    String mAmount = null;
    int qty = 0;
    private boolean addition, subtraction, multiplication, division;

    String ITEM_BILL_1 = "";

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
    AlertDialog orderDetailsPopup;

    String companyName, companyAddress, billingName, date, kotNumber;

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

    Bitmap image;
    private String URL;

    AlertDialog qrPaymentDialog;

    Boolean upiPaymentPopup = false;

    String paymentType = "0";

    AlertDialog progressDialog;

    LinearLayoutCompat cashCardLayout, otherOrders;
    AppCompatTextView uberTextView, menulogTextView, doordashTextView, othersTextView;

    List<PrintBillResponse.ProductListResponse> paymentproductsList;

    Double paymentDiscount = 0.00, paymentAmount = 0.00;

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

    private AppCompatTextView reservationCountTextView;

    RelativeLayout placeKotLayout;
    RelativeLayout addMiscProductLayout;
    RelativeLayout tablesLayout;
    RelativeLayout addCustomerDataLayout;

    AppCompatButton closeAddProductBtn;

    String selectedOrderNumber = "0";

    public static final int PERMISSION_BLUETOOTH = 1;

    private final Locale locale = new Locale("id", "ID");
    private final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", locale);
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

    private static final String ACTION_USB_PERMISSION = "com.cygen.cygendineinpos.USB_PERMISSION";

    String userDetails = null;
    String printText = null;
    String textName = null;

    RecyclerView orderDetailsItemsRecyclerView;

    AppCompatTextView orderIdTextViewNew, orderTypeTextView, orderDateTv, customerNameTextViewNew, customerMobileTextView,
            paymnentStatusTv, tableNoTextViewNew, amountTextView;
    AppCompatImageView closeIcon;

    OrderDetailsAddOnsRecyclerAdapter orderDetailsAddOnsRecyclerAdapter;

    //adding admins details

    AppCompatTextView chooseFileTv;
    LinearLayoutCompat categoryListRecyclerViewLayout, addNewCategoryLayout;
    RecyclerView AddedcategoryListRecyclerView, addedProductListRecyclerView;
    AppCompatTextView errorText;
    AppCompatImageView backArrowIv;
    AppCompatImageView categoryimageView;
    AppCompatEditText categoryNameEt, descriptionEt;
    String admincategoryName = "Select Category";
    AddedhomeCatRecyclerAdapter addedhomeCatRecyclerAdapter;
    String editId = "0";
    String changeStatus, changeTable, changeStatusId;
    String encodedImage = "null";
    AppCompatEditText itemCodeEt, itemNameEt, stockEt, purchasePriceEt, taxEt, priceWithTaxEt, salesPriceEt;
    AppCompatSpinner addcategorySpinner, taxTypeSpinner;
    AppCompatButton addMainProductBtn, clearMainProductBtn;
    String itemCode, itemName, unitId, expDate, purchasePrice, salePrice, supplierPrice, stock, categoryName, categoryId, unitName, supplierName, supplierId, taxType = "Select TaxType", supplierTax;
    String id = "0";
    String addProductCatName, addProductCatId;
    List<String> categoriesNameList, categoryIdList;
    ArrayAdapter aa;
    AdminProductRecyclerAdapter adminProductRecyclerAdapter;

    String[] taxTypeArray = {"Select TaxType", "Exclusive", "Inclusive"};
    String mergeSourceTableId, mergeDestTableId, mergeTableOrderNo;
    RelativeLayout mergeTablesLayoutLayout;
    LinearLayout runningOrdersHeaderLayout;

    String tableNo = "0", mobileNo;
    PrintBillResponse.UserListResponse taxList;
    String tableNewNo;
    String orderNewNo;

    LatestTablesRecyclerAdapter latestTablesRecyclerAdapter;
    RecyclerView newtablesRecyclerView, userRecyclerView;

    List<String> groupListNew;

    String minAddons, maxAddons;
    int minAddonsInteger, maxAddonsInteger;
    String loginUserName;
    UsersDetailsAdapterRecyclerAdapter usersDetailsAdapterRecyclerAdapter;
    AppCompatTextView userNameTv;

    AppCompatButton logoutBtn, loginBtn;
    HashMap<String, String> user1;
    LoginResponse.UserListResponse loginList;
    String roleId, roleName, username, password;

    RelativeLayout oneLayout, twoLayout, threeLayout, fourLayout, fiveLayout, sixLayout, sevenLayout, eightLayout, nineLayout, zeroLayout, clearTextLayout;
    AppCompatEditText userNameEt;
    AppCompatImageView innerLoginCloseIv;
    AppCompatTextView loggedUserNameTv;
    String addNote;
    Double newtaxAmount;

    AppCompatEditText customerNameEt;
    AppCompatEditText customerMobileEt;
    AppCompatEditText addNoteEt;

    //refund Code

    AutoCompleteTextView returnOrderIdEt;
    AppCompatButton returnOrderIdSubmitBtnTv, returnSubmit;
    RecyclerView refundRecyclerView, refundReasonsRecyclerview;
    List<RefundReasonsResponse.RefundListResponse> refundReasonsList;
    RefundReasonsListAdapter refundReasonsListAdapter;
    String returnType, returnorderNo;
    RecyclerView returnListRecyclerView;
    List<ReturnListResponse.ReturnResponse> returnsList;
    ReturnOrderProductsRecyclerAdapter returnOrderProductsRecyclerAdapter;
    long rowid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pos_main_screen);

        reduceChoreographerSkippedFramesWarningThreshold();

        customerNameList = new ArrayList<>();
        customerIdList = new ArrayList<>();

        categoriesNameList = new ArrayList<>();
        categoryIdList = new ArrayList<>();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        sessionManager = new SessionManager(getApplicationContext());

        user1 = sessionManager.getUserDetails();
        name = user1.get(sessionManager.KEY_USER_NAME);
        userId = user1.get(sessionManager.KEY_USER_ID);
        outlet_id = user1.get(sessionManager.KEY_OUTLET_ID);

        inflateIds();

        loggedUserNameTv.setText(name);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 10);
        categoryListRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
        cartRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        subCategoryListRecyclerView.setLayoutManager(linearLayoutManager3); // set LayoutManager to RecyclerView

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        productsRecyclerView.setLayoutManager(linearLayoutManager1);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 250);
//        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        productsRecyclerView.setLayoutManager(layoutManager);
        // Limiting the size
        productsRecyclerView.setHasFixedSize(true);
        sessionManager.setDiscount("" + disc);
        updateList();

        initializeSSLContext(getApplicationContext());

        getHomeCategory();

        getOrdersCountDetails();

        getCustomerDetails();

        cashLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productsRecyclerView.setVisibility(View.GONE);

                if (products.size() > 0) {

                    if (orderNo != null) {
                        Intent intent = new Intent(getApplicationContext(), NewPaymentActivity.class);
                        intent.putExtra("total", db.getTotalPrice());
                        intent.putExtra("totalPayable", totalAmount);
                        intent.putExtra("qty", products.size());
                        intent.putExtra("discount", disc);
                        intent.putExtra("orderId", orderNo);
                        intent.putExtra("updateData", updateData);
                        intent.putExtra("priceId", priceId);

//                        Log.d("priceiddd", priceId);
                        startActivity(intent);
                        orderNo = null;
                    } else {
                        addMiscProductLayout.setVisibility(View.GONE);
                        placeKotLayout.setVisibility(View.GONE);
                        mergeTablesLayoutLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        customerDetailsLayout.setVisibility(View.VISIBLE);
                        categoriesLayout.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.GONE);
                        customerDetailPopup();
//                        infoPopup("Please place an order to make the payment");
                    }

                } else {
                    infoPopup("Please add item(s) to Cart");
                }

            }
        });

        refundImageView = findViewById(R.id.refundImageView);
        refundImageView.setColorFilter(Color.parseColor("#FFB61E"), PorterDuff.Mode.SRC_IN);

        placeOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0) {

//                    Log.d("dbsiezee", "dbseizee");
//                    addKotPopup();
                    categoriesLayout.setVisibility(View.GONE);
                    productsRecyclerView.setVisibility(View.GONE);
                    customerDetailsLayout.setVisibility(View.GONE);
                    addKotData();
                    tablesLayout.setVisibility(View.GONE);
                    placeKotLayout.setVisibility(View.VISIBLE);
                    paymentLayout.setVisibility(View.GONE);
                    addMiscProductLayout.setVisibility(View.GONE);
                    addOnsDataLayout.setVisibility(View.GONE);
                } else {
                    infoPopup("Please add item(s) to Cart");
                }
            }
        });

        runningOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                runningOrdersPopup();
                getRunningOrderDetails();
            }
        });

//        signOutLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sessionManager.logoutUser();
//            }
//        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWord = searchEditText.getText().toString();
                if (searchWord.length() > 0) {
                    hideKeyBoard();
                    getSearchProductDetails();
                    searchEditText.setText("");
                }
            }
        });

        discountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0) {
                    setDiscountPopup();
                } else {
                    showLongSnackBar("Add items to Cart");
                }
            }
        });

        voidLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0)

                    voidPopup();
            }
        });

        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tableLayoutPopup();
                mergeTablesLayoutLayout.setVisibility(View.VISIBLE);
                customerDetailsLayout.setVisibility(View.GONE);
                mergeTablesData();
                placeKotLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.GONE);
                addMiscProductLayout.setVisibility(View.GONE);
                addOnsDataLayout.setVisibility(View.GONE);
                addCustomerDataLayout.setVisibility(View.GONE);
                tableLayoutData();
            }
        });

        findViewById(R.id.tableTopOrdersLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchPopup();
//                orderType = "3";
//                getOrderDetails(orderType);
            }
        });

        findViewById(R.id.takeAwayOrdersLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderType = "1";
                getOrderDetails(orderType);
            }
        });

        findViewById(R.id.deliveryOrdersLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                orderType = "2";
//                getOrderDetails(orderType);
            }
        });

        findViewById(R.id.reservationLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReservationDetails();
            }
        });

        findViewById(R.id.signoutLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

//        shiftInOutLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shiftInOutPopUp();
//            }
//        });

        findViewById(R.id.couponsLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponsPopUp();
            }
        });

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryListRecyclerView.setVisibility(View.VISIBLE);
                productsRecyclerView.setVisibility(View.GONE);
                backTextView.setVisibility(View.GONE);
            }
        });

//        findViewById(R.id.newsaleLayout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.deleteAll();
//                orderNo = null;
//                updateList();
//                selectedDiscount = 0.00;
//            }
//        });

        findViewById(R.id.cardPaymentLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0)
                    paymentTypePopup();
                else
                    infoPopup("Add item(s) to cart");
            }
        });

//        findViewById(R.id.dayEndReportLayout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dayEndReportPopup();
//            }
//        });
//
        findViewById(R.id.lastInvoiceLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LastInvoiceOrdersPopup();
                categoriesLayout.setVisibility(View.VISIBLE);
                tablesLayout.setVisibility(View.GONE);
                placeKotLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.GONE);
                addMiscProductLayout.setVisibility(View.GONE);
                addOnsDataLayout.setVisibility(View.GONE);
                addCustomerDataLayout.setVisibility(View.GONE);
                customerDetailsLayout.setVisibility(View.GONE);
                runningOrdersHeaderLayout.setVisibility(View.GONE);
                getLastInvoiceOrderDetails();
            }
        });

        findViewById(R.id.MiscLinearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addProductPopup();
                addMiscProductData();
//                getCashBoxOpen2();
            }
        });

        findViewById(R.id.addCustomerLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addCustomerDialog();
                addCustomerData();
            }
        });

        findViewById(R.id.kitchenLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerDetailsLayout.setVisibility(View.GONE);
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
//                addCustomerDialog();
//                Intent intent = new Intent(getApplicationContext(), KitchenMainActivity.class);
//                startActivity(intent);
            }
        });


        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager != null) {
            presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if (presentationDisplays.length > 0) {
                SecondaryDisplay secondaryDisplay = new SecondaryDisplay(NewPosMainActivity.this, presentationDisplays[0]);
                secondaryDisplay.show();
            }
        }

        findViewById(R.id.moreLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreLayoutPopup();
            }
        });

        findViewById(R.id.printLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0) {
                }
//                    printConfirmationPopup();
            }
        });

        findViewById(R.id.refundLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllCategories().size() > 0)
                    refundReasonsPopup();
                else
                    infoPopup("Add item(s) to Cart");
            }
        });

        findViewById(R.id.homeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sessionManager.logoutUser();

                userDetailPopup();

//                mergeTablesLayoutLayout.setVisibility(View.VISIBLE);
//                categoriesLayout.setVisibility(View.VISIBLE);
//                tablesLayout.setVisibility(View.GONE);
//                placeKotLayout.setVisibility(View.GONE);
//                paymentLayout.setVisibility(View.GONE);
//                addMiscProductLayout.setVisibility(View.GONE);
//                addOnsDataLayout.setVisibility(View.GONE);
//                addCustomerDataLayout.setVisibility(View.GONE);
//                mergeTablesData();
//
//                if (!priceId.equalsIgnoreCase("0")) {
//                    priceId = "0";
//                    db.deleteAll();
//                    orderNo = null;
//                    updateList();
//                    selectedDiscount = 0.00;
//                    getProductDetails();
//
//                    Log.d("mergevsi", "visisble");
//
//                }

            }
        });

        handlerCall();

        selectedOrderNumber = "0";

        findViewById(R.id.uberEatsLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                priceId = "6";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
            }
        });

        findViewById(R.id.menulogLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                priceId = "7";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
            }
        });

        findViewById(R.id.posLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                priceId = "0";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
            }
        });

        findViewById(R.id.returnLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getReturnListPopup();
            }
        });

        findViewById(R.id.tillLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCashBoxOpen2();
            }
        });


    }

    public void inflateIds() {

        addOnGroupIdList = new ArrayList<>();
        addOnKotIdList = new ArrayList<>();
        addOnIdList = new ArrayList<>();
        addOnsPrimaryIdList = new ArrayList<>();
        maxAddOnIdList = new ArrayList<>();
        addOnNameList = new ArrayList<>();
        addOnPriceList = new ArrayList<>();
        addOnQtyList = new ArrayList<>();
        productIdList = new ArrayList<>();

        addOnGroupIdListOne = new ArrayList<>();
        addOnKotIdListOne = new ArrayList<>();
        addOnIdListOne = new ArrayList<>();
        addOnNameListOne = new ArrayList<>();
        addOnPriceListOne = new ArrayList<>();
        addOnQtyListOne = new ArrayList<>();
        productIdListOne = new ArrayList<>();

        HeadingNameList = new ArrayList<String>();
        HeadingIdList = new ArrayList<String>();
        HeadingPriceList = new ArrayList<String>();
        HeadingQtyList = new ArrayList<String>();

        listDataChild = new HashMap<String, List<String>>();
        listDataQtyChild = new HashMap<String, List<String>>();
        listDataIdChild = new HashMap<String, List<String>>();
        listDataPriceChild = new HashMap<String, List<String>>();

        dataIdList = new ArrayList<>();
        dataList = new ArrayList<List<String>>();
        dataPriceList = new ArrayList<List<String>>();
        dataQtyList = new ArrayList<List<String>>();

        productsWithAddOnsList = new ArrayList<>();

        db = new DataBaseHandler(getApplicationContext());
//        newDb = new NewDataBaseHandler(getApplicationContext());
        db.deleteAll();
        orderNo = null;
        selectedDiscount = 0.00;
//        newDb.deleteAll();

        paymentLayout = findViewById(R.id.paymentLayout);
        customerDetailsLayout = findViewById(R.id.customerDetailsLayout);
        addOnsDataLayout = findViewById(R.id.addOnsDataLayout);
        categoriesLayout = findViewById(R.id.categoriesLayout);

        cashLayoutCompat = findViewById(R.id.cashLayout);
        voidLayout = findViewById(R.id.voidLinearLayout);
        placeOrderLayout = findViewById(R.id.placeOrderLayout);
        placeKotLayout = findViewById(R.id.placeKotLayout);
        addMiscProductLayout = findViewById(R.id.addMiscProductLayout);
        tablesLayout = findViewById(R.id.tablesLayout);
        addCustomerDataLayout = findViewById(R.id.addCustomerDataLayout);
        reservationLayout = findViewById(R.id.reservationLayout);
        mergeTablesLayoutLayout = findViewById(R.id.mergeTablesLayoutLayout);
        runningOrdersHeaderLayout = findViewById(R.id.runningOrdersHeaderLayout);

        tableLayout = findViewById(R.id.tableLayout);
        tableTopOrdersLayout = findViewById(R.id.tableTopOrdersLayout);
        takeAwayOrdersLayout = findViewById(R.id.takeAwayOrdersLayout);
        deliveryOrdersLayout = findViewById(R.id.deliveryOrdersLayout);

        cashInOutLayout = findViewById(R.id.cashInOutLayout);
        shiftInOutLayout = findViewById(R.id.shiftInOutLayout);

        quantityTextView = findViewById(R.id.quantityTextView);
        subTotalTextView = findViewById(R.id.subTotalTextView);
        discountTextView = findViewById(R.id.discountTextView);
        taxTextView = findViewById(R.id.taxTextView);
        grandTotalTextView = findViewById(R.id.grandTotalTextView);
        loggedUserNameTv = findViewById(R.id.loggedUserNameTv);

        reservationCountTextView = findViewById(R.id.reservationCountTextView);

        retrofitService();

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        categoryListRecyclerView = (RecyclerView) findViewById(R.id.categoryRecyclerView);

        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        subCategoryListRecyclerView = (RecyclerView) findViewById(R.id.subCategoriesRecyclerView);

        expandableListView = (ExpandableListView) findViewById(R.id.expListView);

        mProgressBar = findViewById(R.id.progressBar);
        noRecordsTextView = findViewById(R.id.noRecordsTextView);

        customerSpinner = findViewById(R.id.customerSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);

        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);

        discountLayout = findViewById(R.id.discountLayout);
        updateItemLayout = findViewById(R.id.updateItemLayout);
        runningOrdersLayout = findViewById(R.id.runningOrdersLayout);
        signOutLayout = findViewById(R.id.signoutLayout);

        backTextView = findViewById(R.id.backTextView);

        tableTopOrdersCountTextView = findViewById(R.id.tableTopOrdersCountTextView);
        takeAwayOrdersCountTextView = findViewById(R.id.takeAwayOrdersCountTextView);
        deliveryOrdersCountTextView = findViewById(R.id.deliveryOrdersCountTextView);

    }

    private void getReservationCount() {

        ReservationStatusRequest request = new ReservationStatusRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

//        Log.d("printstatus", new Gson().toJson(request));

        Call<ReservationStatusResponse> call = apiService.changeReservationStatus("1234", request);

        call.enqueue(new Callback<ReservationStatusResponse>() {
            @Override
            public void onResponse(Call<ReservationStatusResponse> call, Response<ReservationStatusResponse> response) {

//                Log.d("printstatus", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    final ReservationStatusResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                reservationCountTextView.setText(user.booking_count);

                            }
                        });
                    } else {

//                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ReservationStatusResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public class AutoFitGridLayoutManager extends GridLayoutManager {

        private int columnWidth;
        private boolean columnWidthChanged = true;

        public AutoFitGridLayoutManager(Context context, int columnWidth) {
            super(context, 1);

            setColumnWidth(columnWidth);
        }


        public void setColumnWidth(int newColumnWidth) {
            if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
                columnWidth = newColumnWidth;
                columnWidthChanged = true;
            }
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (columnWidthChanged && columnWidth > 0) {
                int totalSpace;
                if (getOrientation() == VERTICAL) {
                    totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
                } else {
                    totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
                }
                int spanCount = Math.max(1, totalSpace / columnWidth);
                setSpanCount(spanCount);
                columnWidthChanged = false;
            }
            super.onLayoutChildren(recycler, state);
        }
    }

    public void addProductPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product_popup, null);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        dialog.getWindow().setAttributes(layoutParams);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.60f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        AppCompatImageView closeIcon = findViewById(R.id.closeIcon);

        productNameEt = dialogView.findViewById(R.id.productNameEt);
        productPriceEt = dialogView.findViewById(R.id.productPriceEt);
        submitAddProductBtn = dialogView.findViewById(R.id.submitAddProductBtn);
        closeIcon = dialogView.findViewById(R.id.closeIcon);

        miscProductsRecyclerview = dialogView.findViewById(R.id.miscProductsRecyclerview);
        miscProductsLayout = dialogView.findViewById(R.id.miscProductsLayout);
        addProductLayout = dialogView.findViewById(R.id.addProductLayout);
        addProductBtn = dialogView.findViewById(R.id.addProductBtn);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        miscProductsRecyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        getMiscProductDetails();

        submitAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productNameEt.getText().toString().isEmpty()) {

                }
                addProductName = productNameEt.getText().toString();
                addProductPrice = productPriceEt.getText().toString();

                addProductResponse(addProductName, addProductPrice);

            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductLayout.setVisibility(View.VISIBLE);
                miscProductsLayout.setVisibility(View.GONE);
            }
        });

        dialogView.findViewById(R.id.closeAddProductBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductLayout.setVisibility(View.GONE);
                miscProductsLayout.setVisibility(View.VISIBLE);
            }
        });


        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void addMiscProductData() {

        addMiscProductLayout.setVisibility(View.VISIBLE);
        categoriesLayout.setVisibility(View.GONE);
        paymentLayout.setVisibility(View.GONE);
        placeKotLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.GONE);
        customerDetailsLayout.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);

        productNameEt = findViewById(R.id.productNameEt);
        productPriceEt = findViewById(R.id.productPriceEt);
        submitAddProductBtn = findViewById(R.id.submitAddProductBtn);

        miscProductsRecyclerview = findViewById(R.id.miscProductsRecyclerview);
        miscProductsLayout = findViewById(R.id.miscProductsLayout);
        addProductLayout = findViewById(R.id.addProductLayout);
        addProductBtn = findViewById(R.id.addProductBtn);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 6);
        miscProductsRecyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        getMiscProductDetails();

        submitAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productNameEt.getText().toString().isEmpty()) {

                }
                addProductName = productNameEt.getText().toString();
                addProductPrice = productPriceEt.getText().toString();

                addProductResponse(addProductName, addProductPrice);

            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductLayout.setVisibility(View.VISIBLE);
                miscProductsLayout.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.closeAddProductBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductLayout.setVisibility(View.GONE);
                miscProductsLayout.setVisibility(View.VISIBLE);
            }
        });


    }

    private void addProductResponse(String productName, String productPrice) {

        showProgressDialog();
        AddProductRequest request = new AddProductRequest();

        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setProduct_name(productName);
        request.setSale_price(productPrice);

        retrofit2.Call<AddProductResponse> call = apiService.addProductData("1234", request);
//        Log.d("Miscrequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddProductResponse> call, Response<AddProductResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    AddProductResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("MiscsavedInvoice", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                showLongSnackBar(message);
//                                dialog.dismiss();

                                productNameEt.setText("");
                                productPriceEt.setText("");

                                getMiscProductDetails();

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
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    private void getMiscProductDetails() {

        miscProductsRecyclerview.setVisibility(View.GONE);
        noRecordsTextView.setVisibility(View.GONE);
        ProductsRequest request = new ProductsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setCategory_id("");
        request.setQuick_product("1");

//        Log.d("requtesmisc", new Gson().toJson(request));

        retrofit2.Call<ProductsResponse> call = apiService.productList("1234", request);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ProductsResponse> call, Response<ProductsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    ProductsResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("requtesmiscres", new Gson().toJson(response));
//
//                        Log.d("prodResp", "response 111211: " + new Gson().toJson(response.body()));
                        productsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productsList.size() > 0) {
                                    addProductLayout.setVisibility(View.GONE);
//                                    miscProductsRecyclerview.setVisibility(View.VISIBLE);
                                    miscProductsLayout.setVisibility(View.VISIBLE);
                                    miscProductsRecyclerview.setVisibility(View.VISIBLE);
                                    miscProductsRecyclerAdapter LoansListAdapter = new miscProductsRecyclerAdapter(NewPosMainActivity.this, productsList);
                                    miscProductsRecyclerview.setAdapter(LoansListAdapter);
                                    miscProductsRecyclerview.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                } else {

                                    miscProductsLayout.setVisibility(View.GONE);
                                }
                            }
                        });

                    } else {
                        miscProductsRecyclerview.setVisibility(View.GONE);
                        noRecordsTextView.setVisibility(View.VISIBLE);
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    AlertDialog updateproductDialog;

    public void updateProductPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_update_product_popup, null);
        builder.setView(dialogView);
        updateproductDialog = builder.create();
        updateproductDialog.setCanceledOnTouchOutside(false);
        updateproductDialog.setCancelable(false);
        updateproductDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(updateproductDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.80f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        updateproductDialog.getWindow().setAttributes(layoutParams);

        final AppCompatImageView closeIcon = updateproductDialog.findViewById(R.id.closeIcon);
        final AppCompatEditText productSearchet = updateproductDialog.findViewById(R.id.productSearchet);
        updateProductRecyclerview = updateproductDialog.findViewById(R.id.updateProductRecyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
        updateProductRecyclerview.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        getAllProductDetails();

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateproductDialog.dismiss();
            }
        });

        productSearchet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateProductsRecyclerAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public class updateProductsRecyclerAdapter extends RecyclerView.Adapter<updateProductsRecyclerAdapter.ViewHolder> implements Filterable {

        Context activity;
        List<ProductsResponse.ProductListResponse> data;
        ProductsResponse.ProductListResponse masterData;
        List<ProductsResponse.ProductListResponse> filterData;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse> data1;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse masterData1;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;

        public updateProductsRecyclerAdapter(Activity activity2,
                                             List<ProductsResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
            filterData = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.update_product_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            if (!filterData.get(position).getItem_name().isEmpty())
                holder.updateProductNameTv.setText(filterData.get(position).getItem_name());
            if (!filterData.get(position).getSales_price().isEmpty())
                holder.updateProductPriceTv.setText("$" + filterData.get(position).getSales_price());

            final ViewHolder finalHolder = holder;

            finalHolder.clickProductTv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final ViewHolder finalHolder = holder;

                            masterData = data.get(position);

                            if (holder.clickProductTv.getText() == "Click") {
                                holder.clickProductTv.setText("Close");
                                holder.priceUpdateLayout.setVisibility(View.VISIBLE);
                            } else {
                                holder.clickProductTv.setText("Click");
                                holder.priceUpdateLayout.setVisibility(View.GONE);
                            }

                        }
                    });

            finalHolder.updateProductBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    masterData = data.get(position);

                    String updatedPrice = finalHolder.updateProductPriceEt.getText().toString();

                    updateProductResponse(masterData.getId(), updatedPrice);

                }
            });

        }

        @Override
        public int getItemCount() {
            return filterData.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString().toLowerCase();
//                    Log.d("charstring", charString);

                    if (charString.isEmpty()) {
                        filterData = data;
                    } else {
                        ArrayList<ProductsResponse.ProductListResponse> filteredList = new ArrayList<>();

//                        filterData.clear();
                        for (ProductsResponse.ProductListResponse androidVersion : data) {

                            if (androidVersion.getItem_name().toLowerCase().contains(charString)) {

                                filteredList.add(androidVersion);
                            }
                        }

                        filterData = filteredList;
//                        Log.d("ksjkj", filterData.size() + "");
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterData;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filterData = (ArrayList<ProductsResponse.ProductListResponse>) filterResults.values;

                    if (filterData.size() > 0) {
                        LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                    } else {
                        if (charSequence.length() == 0) {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, data);
                        } else {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                        }
                    }
                    productsRecyclerView.setAdapter(LoansListAdapter);
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView updateProductNameTv, clickProductTv, updateProductBtn;
            TextView updateProductPriceTv;
            TextView productPriceTextView;
            AppCompatEditText updateProductPriceEt;
            RelativeLayout priceUpdateLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.updateProductNameTv = (TextView) itemView.findViewById(R.id.updateProductNameTv);
                this.updateProductPriceTv = (TextView) itemView.findViewById(R.id.updateProductPriceTv);
                this.clickProductTv = (TextView) itemView.findViewById(R.id.clickProductTv);
                this.updateProductBtn = (TextView) itemView.findViewById(R.id.updateProductBtn);
                this.priceUpdateLayout = (RelativeLayout) itemView.findViewById(R.id.priceUpdateLayout);
                this.updateProductPriceEt = (AppCompatEditText) itemView.findViewById(R.id.updateProductPriceEt);

            }

        }

    }

    private void updateProductResponse(String updatedproductId, String updatedproductPrice) {

        showProgressDialog();
        AddProductRequest request = new AddProductRequest();

        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setProduct_id(updatedproductId);
        request.setSale_price(updatedproductPrice);

        retrofit2.Call<AddProductResponse> call = apiService.updateProductData("1234", request);
//        Log.d("updaterequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddProductResponse> call, Response<AddProductResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    AddProductResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("updateRespon", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLongSnackBar(message);
//                                dialog.dismiss();
                                getAllProductDetails();
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
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public void addCustomerDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_customer_popup, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();

        groupListNew = new ArrayList<>();
        countryNameList = new ArrayList<>();
        countryIdList = new ArrayList<>();
        stateNameList = new ArrayList<>();
        stateIdList = new ArrayList<>();

        groupListNew.clear();
        stateNameList.clear();
        stateIdList.clear();
        stateNameList.add("Select State");
        stateIdList.add("Select State");

        countryNameList.clear();
        countryIdList.clear();
        countryNameList.add("Select Country");
        countryIdList.add("Select Country");

        paymentsBackTv = (TextView) dialogView.findViewById(R.id.paymentsBackTv);
        countrySpinner = (Spinner) dialogView.findViewById(R.id.countrySpinner);
        stateSpinner = (Spinner) dialogView.findViewById(R.id.stateSpinner);
        final TextView customerNameEt = (EditText) dialogView.findViewById(R.id.customerNameEt);
        final TextView mobileEt = (EditText) dialogView.findViewById(R.id.mobileEt);
        final TextView phoneEt = (EditText) dialogView.findViewById(R.id.phoneEt);
        final TextView emailEt = (EditText) dialogView.findViewById(R.id.emailEt);
        final TextView openingBalanceEt = (EditText) dialogView.findViewById(R.id.openingBalanceEt);
        final TextView gstNumberEt = (EditText) dialogView.findViewById(R.id.gstNumberEt);
        final TextView taxNumberEt = (EditText) dialogView.findViewById(R.id.taxNumberEt);
        final TextView cityEt = (EditText) dialogView.findViewById(R.id.cityEt);
        final TextView postCodeEt = (EditText) dialogView.findViewById(R.id.postCodeEt);
        final TextView addressEt = (EditText) dialogView.findViewById(R.id.addressEt);
        final TextView addCustomerTV = (TextView) dialogView.findViewById(R.id.addCustomerTV);
        final TextView errorTextView = (TextView) dialogView.findViewById(R.id.errorTextView);
        final TextView paymentsBackTv = (TextView) dialogView.findViewById(R.id.paymentsBackTv);
        final ImageView closeIcon = (ImageView) dialogView.findViewById(R.id.closeIcon);

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, countryNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        countrySpinner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.activity_simple_spinner, stateNameList);
        aa1.setDropDownViewResource(R.layout.activity_simple_spinner);
        stateSpinner.setAdapter(aa1);

        getCountryDetails();

//        Rect displayRectangle = new Rect();
//        dialogView.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
//        dialogView.setMinimumHeight((int) (displayRectangle.height() * 0.8f));
//        dialogBuilder.setView(dialogView);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);

        addCustomerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerName = customerNameEt.getText().toString();
                mobile = mobileEt.getText().toString();
                phone = phoneEt.getText().toString();
                email = emailEt.getText().toString();
                city = cityEt.getText().toString();
                postCode = postCodeEt.getText().toString();
                address = addressEt.getText().toString();

                if (customerNameEt.getText().toString().length() != 0) {
                    errorTextView.setText("");

                    if (mobileEt.getText().toString().length() != 0) {
                        errorTextView.setText("");

                        if (phoneEt.getText().toString().length() != 0) {
                            errorTextView.setText("");

                            if (emailEt.getText().toString().length() != 0) {
                                errorTextView.setText("");

                                if (countryId != null && !countryId.equalsIgnoreCase("Select")) {
                                    errorTextView.setText("");

//                                    Log.d("countr", stateId);

                                    if (stateId != null && !stateId.equalsIgnoreCase("Select")) {
                                        errorTextView.setText("");

                                        if (cityEt.getText().toString().length() != 0) {
                                            errorTextView.setText("");

                                            if (postCodeEt.getText().toString().length() != 0) {
                                                errorTextView.setText("");

                                                if (addressEt.getText().toString().length() != 0) {
                                                    errorTextView.setText("");

                                                    addCustomerResponse();
                                                    alertDialog.dismiss();
                                                } else {
                                                    errorTextView.setText("Enter Address");

                                                }

                                            } else {
                                                errorTextView.setText("Enter PostCode");
                                            }

                                        } else {
                                            errorTextView.setText("Enter City");
                                        }


                                    } else {
                                        errorTextView.setText("Select State");
                                    }
                                } else {
                                    errorTextView.setText("Select Country");
                                }

                            } else {
                                errorTextView.setText("Enter Email");
                            }

                        } else {
                            errorTextView.setText("Enter Phone");
                        }
                    } else {
                        errorTextView.setText("Enter Mobile");
                    }

                } else {
                    errorTextView.setText("Enter Customer Name");
                }
            }
        });

        paymentsBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    public void addCustomerData() {

        addMiscProductLayout.setVisibility(View.GONE);
        categoriesLayout.setVisibility(View.GONE);
        paymentLayout.setVisibility(View.GONE);
        placeKotLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.VISIBLE);
        customerDetailsLayout.setVisibility(View.GONE);

        countryNameList = new ArrayList<>();
        countryIdList = new ArrayList<>();
        stateNameList = new ArrayList<>();
        stateIdList = new ArrayList<>();

        stateNameList.clear();
        stateIdList.clear();
        stateNameList.add("Select State");
        stateIdList.add("Select State");

        countryNameList.clear();
        countryIdList.clear();
        countryNameList.add("Select Country");
        countryIdList.add("Select Country");

        paymentsBackTv = findViewById(R.id.paymentsBackTv);
        countrySpinner = findViewById(R.id.countrySpinner);
        stateSpinner = findViewById(R.id.stateSpinner);

        final TextView customerNameEt = findViewById(R.id.customerNameEditText);
        final TextView mobileEt = findViewById(R.id.mobileEt);
        final TextView phoneEt = findViewById(R.id.phoneEt);
        final TextView emailEt = findViewById(R.id.emailEt);
        final TextView cityEt = findViewById(R.id.cityEt);
        final TextView postCodeEt = findViewById(R.id.postCodeEt);
        final TextView addressEt = findViewById(R.id.addressEt);
        final TextView addCustomerTV = findViewById(R.id.addCustomerTV);
        final TextView errorTextView = findViewById(R.id.errorTextView);
        final TextView paymentsBackTv = findViewById(R.id.paymentsBackTv);

        customerNameEt.setText("");
        mobileEt.setText("");
        phoneEt.setText("");
        emailEt.setText("");
        cityEt.setText("");
        postCodeEt.setText("");
        addressEt.setText("");

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, countryNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        countrySpinner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.activity_simple_spinner, stateNameList);
        aa1.setDropDownViewResource(R.layout.activity_simple_spinner);
        stateSpinner.setAdapter(aa1);

        getCountryDetails();

        addCustomerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerName = customerNameEt.getText().toString();
                mobile = mobileEt.getText().toString();
                phone = phoneEt.getText().toString();
                email = emailEt.getText().toString();
                city = cityEt.getText().toString();
                postCode = postCodeEt.getText().toString();
                address = addressEt.getText().toString();

                if (customerNameEt.getText().toString().length() != 0) {
                    errorTextView.setText("");

                    if (mobileEt.getText().toString().length() != 0) {
                        errorTextView.setText("");

                        if (phoneEt.getText().toString().length() != 0) {
                            errorTextView.setText("");

                            if (emailEt.getText().toString().length() != 0) {
                                errorTextView.setText("");

                                if (countryId != null && !countryId.equalsIgnoreCase("Select")) {
                                    errorTextView.setText("");

//                                    Log.d("countr", stateId);

                                    if (stateId != null && !stateId.equalsIgnoreCase("Select")) {
                                        errorTextView.setText("");

                                        if (cityEt.getText().toString().length() != 0) {
                                            errorTextView.setText("");

                                            if (postCodeEt.getText().toString().length() != 0) {
                                                errorTextView.setText("");

                                                if (addressEt.getText().toString().length() != 0) {
                                                    errorTextView.setText("");

                                                    addCustomerResponse();
                                                    alertDialog.dismiss();
                                                } else {
                                                    errorTextView.setText("Enter Address");

                                                }

                                            } else {
                                                errorTextView.setText("Enter PostCode");
                                            }

                                        } else {
                                            errorTextView.setText("Enter City");
                                        }


                                    } else {
                                        errorTextView.setText("Select State");
                                    }
                                } else {
                                    errorTextView.setText("Select Country");
                                }

                            } else {
                                errorTextView.setText("Enter Email");
                            }

                        } else {
                            errorTextView.setText("Enter Phone");
                        }
                    } else {
                        errorTextView.setText("Enter Mobile");
                    }

                } else {
                    errorTextView.setText("Enter Customer Name");
                }
            }
        });

        paymentsBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomerDataLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getCountryDetails() {
        showProgressDialog();
        HoldRequest holdRequest = new HoldRequest();
        retrofit2.Call<CountryListResponse> call = apiService.countryList("1234", holdRequest);
        call.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CountryListResponse> call, Response<CountryListResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CountryListResponse user = response.body();
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
                        countryNameList.clear();
                        countryIdList.clear();
                        countryNameList.add("Select Country");
                        countryIdList.add("Select Country");
//                        Log.d("cateResp", "response 12: " + new Gson().toJson(response.body()));
                        countryList = user.productResults;
                        for (int i = 0; i < countryList.size(); i++) {
                            countryNameList.add(countryList.get(i).country);
                            countryIdList.add(countryList.get(i).id);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countrySpinnerData();
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
            public void onFailure(Call<CountryListResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public void countrySpinnerData() {
        hideKeyBoard();
        List<CountryListResponse.ProductListResponse> data;
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, countryNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        countrySpinner.setAdapter(aa);
        countrySpinner.setSelection(aa.getPosition(String.valueOf(countryName)));
        countrySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                return false;
            }
        });
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryName = parent.getItemAtPosition(position).toString();
                countryId = countryIdList.get(position);
                getStateDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });
    }

    private void getStateDetails() {
        showProgressDialog();
        StateListRequest request = new StateListRequest();
        request.setCountry(countryName);
        retrofit2.Call<StateListResponse> call = apiService.stateList("1234", request);
        call.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<StateListResponse> call, Response<StateListResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    StateListResponse user = response.body();
                    String message = user.message;
                    String success = user.responseCode;
//                    Log.d("statesss", new Gson().toJson(response));

                    if (success.equalsIgnoreCase("0")) {

                        stateNameList.clear();
                        stateIdList.clear();
                        stateNameList.add("Select State");
                        stateIdList.add("Select State");

//                        Log.d("cateResp", "response 12: " + new Gson().toJson(response.body()));
                        stateList = user.productResults;
                        for (int i = 0; i < stateList.size(); i++) {
                            stateNameList.add(stateList.get(i).state);
                            stateIdList.add(stateList.get(i).id);

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stateSpinnerData();
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
            public void onFailure(Call<StateListResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public void stateSpinnerData() {

        hideKeyBoard();

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, stateNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        stateSpinner.setAdapter(aa);
        stateSpinner.setSelection(aa.getPosition(String.valueOf(countryName)));
        stateSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                return false;
            }
        });

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateName = parent.getItemAtPosition(position).toString();
                stateId = stateIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });

    }

    private void addCustomerResponse() {
        showProgressDialog();
        AddCustomerRequest request = new AddCustomerRequest();
        request.setCustomer_name(customerName);
        request.setMobile(mobile);
        request.setPhone(phone);
        request.setEmail(email);
        request.setCountry("" + countryId);
        request.setState(stateId);
        request.setCity(city);
        request.setAddress("" + address);
        request.setPostcode("" + postCode);
        request.setAdded_by("" + userId);
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        retrofit2.Call<AddCustomerResponse> call = apiService.addCustomerData("1234", request);
//        Log.d("request", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<AddCustomerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddCustomerResponse> call, Response<AddCustomerResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    AddCustomerResponse user = response.body();
                    final String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("savedInvoice", "response 12: " + new Gson().toJson(response.body()));
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
            public void onFailure(Call<AddCustomerResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
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
//            Log.d("dfgdf", addOns.size() + "");
//            Log.d("kjdfg", id + "," + price + "," + name);

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
                    String addOnGroup = addOn.get_addOn_group_id();
                    String addOnKot = addOn.get_kot_id();
                    String addOnPrimary = addOn.get_product_primary_id();

//                    Log.d("lkjlgk", addOnId1 + ", name : " + addOnName + ", price : " + addOnPrice + ", qty : " + addOnQty + ", kot" + addOnKot);
                    addOnsPrimaryIdList.add(id);
                    addOnIdList.add(addOnId1);
                    addOnNameList.add(addOnName);
                    addOnPriceList.add(addOnPrice);
                    addOnQtyList.add(addOnQty);
                    addOnGroupIdList.add(addOnGroup);
                    addOnKotIdList.add(addOnKot);
                    addOnsPrimaryIdList.add(addOnPrimary);
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

                StringBuilder sb4 = new StringBuilder();
                for (int i = 0; i < addOnGroupIdList.size(); i++) {
                    if (i == addOnGroupIdList.size() - 1)
                        sb4.append(addOnGroupIdList.get(i));
                    else {
                        sb4.append(addOnGroupIdList.get(i));
                        sb4.append(",");
                    }
                }
                addOnGroupId = sb4.toString();

                StringBuilder sb5 = new StringBuilder();
                for (int i = 0; i < addOnKotIdList.size(); i++) {
                    if (i == addOnKotIdList.size() - 1)
                        sb5.append(addOnKotIdList.get(i));
                    else {
                        sb5.append(addOnKotIdList.get(i));
                        sb5.append(",");
                    }
                }
                addOnKot = sb5.toString();

                StringBuilder sb6 = new StringBuilder();
                for (int i = 0; i < addOnsPrimaryIdList.size(); i++) {
                    if (i == addOnsPrimaryIdList.size() - 1)
                        sb6.append(addOnsPrimaryIdList.get(i));
                    else {
                        sb6.append(addOnsPrimaryIdList.get(i));
                        sb6.append(",");
                    }
                }
                addOnPrimary = sb6.toString();

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
            map.put(ID, "" + cn.get_id());
            map.put(TAG_ID, cn.get_product_id());
            map.put(TAG_QUANTITY, productQty);
            map.put(TAG_PRICE, price);
            map.put(TAG_NAME, name);
            map.put(TAG_NOTE, note);
            map.put(TAG_ADDON_ID, addOnId);
            map.put(TAG_ADDON_NAME, addOnName);
            map.put(TAG_ADDON_PRICE, addOnPrice);
            map.put(TAG_ADDON_QTY, addOnQty);
            map.put(TAG_ADDON_GROUP_ID, addOnGroupId);
            map.put(TAG_KOT, addOnKot);
            map.put(TAG_PRIMARY, addOnPrimary);

//            Log.d("notesssss", "" + note);

            productsWithAddOnsList.add(map);

        }


        subTotal = db.getTotalPrice();

        totalAmount = db.getTotalPrice();
        totalAmount = Double.parseDouble(String.valueOf(round(totalAmount, 2)));
        subTotalTextView.setText("$" + String.format("%.2f", totalAmount));

        Double taxAmount = 0.00;
        Double discountAmount = 0.00;

        try {
            if (discountType.equalsIgnoreCase("Percentage")) {
                disc = (subTotal * selectedDiscount) / 100;
            } else {
                disc = selectedDiscount;
            }
        } catch (NullPointerException e) {
            disc = 0.00;
//            Log.d("kjsdhfksj", "called");
        }

        newtaxAmount = subTotal / 10.0;

        taxTextView.setText("$" + String.format("%.2f", newtaxAmount));
        discountTextView.setText("$" + String.format("%.2f", disc));

//        Log.d("discamountt", "" + disc);

        totalAmount = totalAmount - disc + taxAmount;

        totalAmount = Double.parseDouble(String.valueOf(round(totalAmount, 2)));

//        Log.d("totalamt", "" + totalAmount);
        grandTotalTextView.setText("$" + String.format("%.2f", totalAmount));

        quantityTextView.setText("" + db.getAllCategories().size());

        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager != null) {
            presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if (presentationDisplays.length > 0) {
                SecondaryDisplay secondaryDisplay = new SecondaryDisplay(NewPosMainActivity.this, presentationDisplays[0]);
                secondaryDisplay.show();
            }
        }

        if (!productsWithAddOnsList.isEmpty()) {
            cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
            cartListAdapter = new CartListAdapter(NewPosMainActivity.this, productsWithAddOnsList);
            cartRecyclerView.setAdapter(cartListAdapter);
//            Log.d("dsfsdjk", productsWithAddOnsList.size() + "");
        } else {
            disc = 0.00;
            grandTotal = 0.00;
            selectedDiscount = 0.00;
            sessionManager.setDiscount("" + disc);
//            Log.d("disccccccc", "" + disc);
            discountTextView.setText("$" + String.format("%.2f", disc));
            grandTotalTextView.setText("$" + String.format("%.2f", grandTotal));

            getHomeCategory();
            productsWithAddOnsList.clear();
            cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
            cartListAdapter = new CartListAdapter(NewPosMainActivity.this, productsWithAddOnsList);
            cartRecyclerView.setAdapter(cartListAdapter);
        }
    }

    final Handler handler = new Handler();

    public void handlerCall() {
        final Runnable r = new Runnable() {
            public void run() {

                if (checkoutId.equalsIgnoreCase("1234")) {

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

                getOrdersCountDetails();
                getReservationCount();
                getTakeAwayTestBillDetails();

                handler.postDelayed(this, 15000);

            }
        };

        handler.postDelayed(r, 10000);
    }

    public void sendCartData() {

        products = db.getAllCategories();

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

//                Log.d("addonsid", addOnId);

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


        getSaveOrderResponse();

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

        getSaveOrderCardResponse();

    }

    public void sendDataWithoutOrder() {

        products = db.getAllCategories();

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

//                Log.d("addonsid", addOnId);

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

//        Log.d("productarray", "" + productsArray);

        putSaveOrderResponse();

    }

    public void sendDataOtherPaymentOrder() {

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

        getSaveOrderOtherPaymentResponse();

    }

    private void putSaveOrderResponse() {

        OrderRequest request = new OrderRequest();

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalAmount);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("");
        request.setChange_return("0");
        request.setPaid_amt("" + totalAmount);
        request.setTax_type("Exclusive");
        request.setDecice_type("2");
        request.setItem_list(productsArray);
        request.setOrder_type(saveOrderType);
        request.setCustomer_name(customer_name);
        request.setEmail(customer_email);
        request.setMobile(customer_mobile);
        request.setAddress(customer_address);
        request.setPostcode(customer_post_code);
        request.setOutlet_id(outlet_id);

        showProgressDialog();
        Log.d("tablechargesaveRequest", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.saveOrdersList("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
                Log.d("tablechargsaveResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {

                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

                        db.deleteAll();
                        updateList();
                        orderNo = null;
                        selectedDiscount = 0.00;

                        tableNumber = "0";

                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        categoriesLayout.setVisibility(View.VISIBLE);

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

        OrderRequest request = new OrderRequest();
        request.setCustomer_id(customerId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalAmount);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Card");
        request.setChange_return("0");
        request.setPaid_amt("" + totalAmount);
        request.setItem_list(productsArray);

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }

        showProgressDialog();
//        Log.d("saveOrderRequest", new Gson().toJson(request));

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

                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        categoriesLayout.setVisibility(View.VISIBLE);

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

    private void getSaveOrderOtherPaymentResponse() {

//        totalAmount = totalAmount + orderTotal;
        OrderRequest request = new OrderRequest();
        request.setCustomer_id(customerId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalAmount);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Card");
        request.setChange_return("0");
        request.setPaid_amt("" + totalAmount);
        request.setItem_list(productsArray);

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }

        showProgressDialog();
//        Log.d("saveOrderRequest", new Gson().toJson(request));

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

                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        categoriesLayout.setVisibility(View.VISIBLE);

//                        sessionManager.storeOrderTrackId(orderId);
                        succsessfailerpopup("success", totalAmount);

//                        getSquareUpResponse();

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

    private void getSaveOrderResponse() {

//        totalAmount = totalAmount + orderTotal;
        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalAmount);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("");
        request.setChange_return("0");
        request.setPaid_amt("" + totalAmount);
        request.setItem_list(productsArray);
        request.setCustomer_name(customer_name);
        request.setEmail(customer_email);
        request.setMobile(customer_mobile);
        request.setAddress(customer_address);
        request.setPostcode(customer_post_code);

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }

        showProgressDialog();
        Log.d("saveOrderRespoRepet", new Gson().toJson(request));


        Call<SaveOrderResponse> call = apiService.saveOrdersList("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
                Log.d("saveOrderResponse", new Gson().toJson(response.body()));
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
                        selectedDiscount = 0.00;
                        updateList();
                        updateData = "0";
                        tableNumber = "0";
//                        sessionManager.storeOrderTrackId(orderId);

//                        getSquareUpResponse();
                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        categoriesLayout.setVisibility(View.VISIBLE);


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
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.cart_recycler_view, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            resultp = data.get(position);

            final ViewHolder finalHolder = holder;
            holder.img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!selectedOrderNumber.equalsIgnoreCase("1")) {

                        resultp = data.get(position);
                        rowid = Integer.parseInt(resultp.get(NewPosMainActivity.ID));
                        String addOnsId = resultp.get(NewPosMainActivity.TAG_ADDON_ID);
                        if (addOnsId.equalsIgnoreCase("")) {
                            String product_id = resultp.get(NewPosMainActivity.TAG_ID);
                            String qty = db.getQtyWithSno(product_id, "" + rowid);
//                            Log.d("qty", qty);
                            count = Integer.parseInt(qty);
                            count = count + 1;
                            db.updateQuantity("" + rowid, product_id, String.valueOf(count));
                            finalHolder.txt_prod_qty.setText("" + count);

                            if (!addOnsId.isEmpty())
//                                Log.d("lkfdjglkfdj", addOnsId);

                                if (!addOnsId.isEmpty()) {
                                    List<String> addOnIdsList = Arrays.asList(addOnsId.split(","));
                                    String id = addOnIdsList.get(0);
                                    count = Integer.parseInt(db.getAddOnsQty("" + rowid, id));
                                    count = count + 1;
                                    db.updateAddOnQuantity(id, "" + rowid, count + "");
                                }

                            updateList();
                        } else {
                            addOnsRepeatPopup(resultp.get(NewPosMainActivity.TAG_ID), resultp.get(NewPosMainActivity.TAG_NAME), resultp.get(NewPosMainActivity.TAG_PRICE), resultp.get(NewPosMainActivity.TAG_ADDON_NAME), addOnsId);
                        }
                    }

                }
            });

            final ViewHolder finalHolder1 = holder;
            holder.img_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!selectedOrderNumber.equalsIgnoreCase("1")) {
                        resultp = data.get(position);
                        rowid = Integer.parseInt(resultp.get(NewPosMainActivity.ID));
                        String product_id = resultp.get(NewPosMainActivity.TAG_ID);
                        String price = resultp.get(NewPosMainActivity.TAG_PRICE);
                        String addOnId = resultp.get(NewPosMainActivity.TAG_ADDON_ID);
                        String qty = db.getQty("" + rowid);
                        count = Integer.parseInt(qty);
                        if (count > 0) {
                            count = count - 1;
                            if (count == 0) {
                                db.deleteCategory(new Product((int) rowid));
                                db.deleteAddOnWithProductId("" + rowid);
                            } else {
                                db.updateQuantity("" + rowid, product_id, String.valueOf(count));
                                finalHolder1.txt_prod_qty.setText("" + count);
//                                Log.d("countttt", "" + count);

                                String addOnsId = resultp.get(NewPosMainActivity.TAG_ADDON_ID);
                                if (!addOnsId.isEmpty()) {
                                    List<String> addOnIdsList = Arrays.asList(addOnsId.split(","));
                                    for (int i = 0; i < addOnIdsList.size(); i++) {
                                        String id = addOnIdsList.get(i);
                                        count = Integer.parseInt(db.getAddOnsQty("" + rowid, id));
                                        count = count - 1;
                                        db.updateAddOnQuantity(id, "" + rowid, count + "");
                                    }
                                }
                            }

                            updateList();
                        }
                    }
                }
            });

            String quty = resultp.get(NewPosMainActivity.TAG_QUANTITY);
            String pricee = resultp.get(NewPosMainActivity.TAG_PRICE);
            String aPrice = resultp.get(NewPosMainActivity.TAG_ADDON_PRICE);

            int finalQty = Integer.parseInt(quty);
            Double finalPrice = Double.parseDouble(pricee);

//            List<String> priceList = Arrays.asList(aPrice.split(","));
//            Double sum = 0.00;
//            for (int i = 0; i < priceList.size(); i++) {
//                sum = sum + Double.parseDouble(priceList.get(i));
//            }
//            Double subTotal = 0.00;
//            subTotal = Double.parseDouble(pricee) + sum;

            Double result = finalQty * finalPrice;
            result = Double.parseDouble(String.valueOf(round(result, 2)));

            holder.txt_prod_name.setText(resultp.get(NewPosMainActivity.TAG_NAME));
            holder.txt_prod_price.setText(resultp.get(NewPosMainActivity.TAG_PRICE));
            holder.txt_sub_total.setText(String.format("%.2f", result));
            holder.txt_prod_qty.setText(resultp.get(NewPosMainActivity.TAG_QUANTITY));
            holder.itemNoteTv.setText(resultp.get(NewPosMainActivity.TAG_NOTE));

//            Log.d("notesss", NewPosMainActivity.TAG_NOTE);


            holder.cartItemNameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultp = data.get(position);
                    rowid = Integer.parseInt(resultp.get(NewPosMainActivity.ID));
                    product_id = resultp.get(NewPosMainActivity.TAG_ID);
                    product_name = resultp.get(NewPosMainActivity.TAG_NAME);
                    product_price = resultp.get(NewPosMainActivity.TAG_PRICE);
//                    getProductAddOnsDetails();

                    addNotePopup();
                }
            });

            String addOns = resultp.get(NewPosMainActivity.TAG_ADDON_NAME);
            String addOnsPrice = resultp.get(NewPosMainActivity.TAG_ADDON_PRICE);
            String addOnsId = resultp.get(NewPosMainActivity.TAG_ADDON_ID);
            String addOnsPrimaryId = resultp.get(NewPosMainActivity.TAG_PRIMARY);
            String addOnsQty = resultp.get(NewPosMainActivity.TAG_ADDON_QTY);
            String addOnsGroup = resultp.get(NewPosMainActivity.TAG_ADDON_GROUP_ID);
            String addOnsKot = resultp.get(NewPosMainActivity.TAG_KOT);

            if (!addOns.isEmpty()) {

                List<String> addOnsList = Arrays.asList(addOns.split(","));
                List<String> addOnsPriceList = Arrays.asList(addOnsPrice.split(","));
                List<String> addOnsIdList = Arrays.asList(addOnsId.split(","));
                List<String> addOnsPrimaryIdList = Arrays.asList(addOnsPrimaryId.split(","));
                List<String> addOnsQtyList = Arrays.asList(addOnsQty.split(","));
                List<String> addOnsGroupList = Arrays.asList(addOnsGroup.split(","));
                List<String> addOnsKotList = Arrays.asList(addOnsKot.split(","));

                if (addOnsQtyList.size() > 0) {

                    LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    holder.cart_addons_recyclerview.setLayoutManager(mLayoutManager2);

                    ProductAdapter cartListAdapter = new ProductAdapter(NewPosMainActivity.this, addOnsList, addOnsPriceList, addOnsQtyList, addOnsIdList, addOnsGroupList, addOnsKotList, addOnsPrimaryIdList);
                    holder.cart_addons_recyclerview.setAdapter(cartListAdapter);
                    holder.cart_addons_recyclerview.setHasFixedSize(true);
                    cartListAdapter.notifyDataSetChanged();

                }
            }

            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!selectedOrderNumber.equalsIgnoreCase("1")) {
                        resultp = data.get(position);
                        rowid = Integer.parseInt(resultp.get(NewPosMainActivity.ID));
                        String pId = resultp.get(NewPosMainActivity.TAG_ID);
//                        Log.d("hjhhj", pId);
                        String addOnIds = resultp.get(NewPosMainActivity.TAG_ADDON_ID);
                        List<String> addOnsIdList = Arrays.asList(addOnIds.split(","));
//                        Log.d("fwertweklj", "" + addOnsIdList);

                        db.deleteCategory(new Product((int) rowid));
                        db.deleteAddOnWithProductId("" + rowid);

                        updateList();
                    }

                }
            });

        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_prod_name, txt_prod_qty, txt_prod_price,
                    txt_sub_total, txt_prod_weight;
            ImageView imageview, cartListItemDeleteIv;
            ImageView img_plus, img_minus, img_delete;
            TextView cart_list_item_addOns, cart_list_item_addOns_price, itemNoteTv;

            RecyclerView cart_addons_recyclerview;
            RelativeLayout cartItemNameLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                txt_prod_name = (TextView) itemView.findViewById(R.id.itemNameTv);
                txt_prod_price = (TextView) itemView.findViewById(R.id.priceInvoiceTv);
                txt_prod_qty = (TextView) itemView.findViewById(R.id.qtyTv);
                txt_sub_total = (TextView) itemView.findViewById(R.id.subTotalTv);

                img_plus = (ImageView) itemView.findViewById(R.id.cart_plus_img);
                img_minus = (ImageView) itemView.findViewById(R.id.cart_minus_img);
                img_delete = (ImageView) itemView.findViewById(R.id.statusTv);
                itemNoteTv = (TextView) itemView.findViewById(R.id.itemNoteTv);
                cartItemNameLayout = (RelativeLayout) itemView.findViewById(R.id.cartItemNameLayout);

                cart_addons_recyclerview = (RecyclerView) itemView.findViewById(R.id.cart_addons_recyclerview);

            }

        }

    }

    private void getProductAddOnsDetails() {

//        categoriesLayout.setVisibility(View.VISIBLE);
        paymentLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.GONE);
        addMiscProductLayout.setVisibility(View.GONE);
        placeKotLayout.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);

        ProductsRequest request = new ProductsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setProduct_id(product_id);
        request.setPrice_id(priceId);
        Log.d("addonreqq", "response: " + new Gson().toJson(request));
        retrofit2.Call<AddOnsResponse> call = apiService.singleProductList("1234", request);

        call.enqueue(new Callback<AddOnsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddOnsResponse> call, Response<AddOnsResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("AddonsDetailResp", "response: " + new Gson().toJson(response));
                    hideProgressDialog();
                    AddOnsResponse user = response.body();
                    addOnIdList.clear();
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        addOnsList = user.productAddOnsGroupResults;
                        if (addOnsList.size() > 0) {
//                            categoriesLayout.setVisibility(View.VISIBLE);
//                            productsRecyclerView.setVisibility(View.VISIBLE);
                            addOnsDataLayout.setVisibility(View.VISIBLE);
                            addOnsOnlyList = user.productAddOnsGroupResults.get(0).productAddOnsResults;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        if (addOnsOnlyList.size() > 0) {

                                            addOnIdList.clear();
                                            addOnsPrimaryIdList.clear();

                                            addOnsDataLayout.setVisibility(View.VISIBLE);
                                            categoriesLayout.setVisibility(View.GONE);
//                                        productsRecyclerView.setVisibility(View.GONE);
                                            paymentLayout.setVisibility(View.GONE);

                                            addOnGroupIdList.clear();
                                            addOnKotIdList.clear();
                                            addOnIdList.clear();
                                            addOnPriceList.clear();
                                            addOnNameList.clear();
                                            addOnQtyList.clear();

                                            addOnIdListOne.clear();
                                            addOnNameListOne.clear();
                                            addOnPriceListOne.clear();
                                            addOnQtyListOne.clear();
                                            addOnGroupIdListOne.clear();
                                            addOnKotIdListOne.clear();

                                            paymentLayout.setVisibility(View.GONE);
                                            addCustomerDataLayout.setVisibility(View.GONE);
                                            addMiscProductLayout.setVisibility(View.GONE);
                                            placeKotLayout.setVisibility(View.GONE);
                                            addOnsDataLayout.setVisibility(View.VISIBLE);

                                            TextView productNameTextView = findViewById(R.id.productNameTextView);
                                            TextView productPriceTextView = findViewById(R.id.productPriceTextView);

                                            productNameTextView.setVisibility(View.GONE);

                                            productNameTextView.setText(product_name);
                                            productPriceTextView.setText("$" + product_price);

                                            TextView addOnHeaderOneTextView = findViewById(R.id.addOnHeaderNameFirst);
                                            TextView addOnHeaderTwoTextView = findViewById(R.id.addTwoHeaderName);
                                            TextView addOnHeaderFourTextView = findViewById(R.id.addFourHeaderName);
                                            TextView addOnHeaderFiveTextView = findViewById(R.id.addFiveHeaderName);
                                            TextView addOnHeaderThreeTextView = findViewById(R.id.addOnHeaderNameThird);

                                            RelativeLayout addOnOneLayout = findViewById(R.id.addOnsLayout);
                                            RelativeLayout addOnTwoLayout = findViewById(R.id.choiceBreadLayout);
                                            RelativeLayout addOnThreeLayout = findViewById(R.id.exclusionLayout);
                                            RelativeLayout addOnFourLayout = findViewById(R.id.containsLayout);
                                            RelativeLayout addOnFiveLayout = findViewById(R.id.makeAMealLayout);

                                            addOnOneLayout.setVisibility(View.GONE);
                                            addOnTwoLayout.setVisibility(View.GONE);
                                            addOnThreeLayout.setVisibility(View.GONE);
                                            addOnFourLayout.setVisibility(View.GONE);
                                            addOnFiveLayout.setVisibility(View.GONE);

                                            final RecyclerView addOnHeaderOneRecyclerView = findViewById(R.id.addOnsRecyclerView);
                                            final RecyclerView addOnHeaderTwoRecyclerView = findViewById(R.id.choiceBreadRecylerView);
                                            final RecyclerView addOnHeaderThreeRecyclerView = findViewById(R.id.exclusionRecyclerView);
                                            final RecyclerView addOnHeaderFourRecyclerView = findViewById(R.id.containsRecylerView);
                                            final RecyclerView addOnHeaderFiveRecyclerView = findViewById(R.id.makeAMealRecylerView);

                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 8);
                                            addOnHeaderOneRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

                                            GridLayoutManager layoutManager = new GridLayoutManager(NewPosMainActivity.this, 8);
                                            addOnHeaderTwoRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

                                            GridLayoutManager layoutManager3 = new GridLayoutManager(NewPosMainActivity.this, 8);
                                            addOnHeaderThreeRecyclerView.setLayoutManager(layoutManager3); // set LayoutManager to RecyclerView

                                            GridLayoutManager layoutManager4 = new GridLayoutManager(NewPosMainActivity.this, 8);
                                            addOnHeaderFourRecyclerView.setLayoutManager(layoutManager4); // set LayoutManager to RecyclerView

                                            GridLayoutManager layoutManager5 = new GridLayoutManager(NewPosMainActivity.this, 8);
                                            addOnHeaderFiveRecyclerView.setLayoutManager(layoutManager5); // set LayoutManager to RecyclerView

                                            for (int i = 0; i < addOnsList.size(); i++) {

                                                final String groupId = addOnsList.get(i).getModifiergroup_id();
                                                maxAddons = addOnsList.get(i).getMaximum_addon();
                                                minAddons = addOnsList.get(i).getMinimum_addon();

                                                maxAddonsInteger = Integer.parseInt(maxAddons);
                                                minAddonsInteger = Integer.parseInt(minAddons);

//                                                Log.d("minmaxAddons", maxAddons + "," + minAddons + "," + maxAddonsInteger + "," + minAddonsInteger);
//
//                                                Log.d("addonsListssize", addOnsList.size() + "," + groupId);

                                                if (groupId.equalsIgnoreCase("21") || groupId.equalsIgnoreCase("22") || groupId.equalsIgnoreCase("24") || groupId.equalsIgnoreCase("25") || groupId.equalsIgnoreCase("27") || groupId.equalsIgnoreCase("28") || groupId.equalsIgnoreCase("29")) {
                                                    addOnOneLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderOneTextView.setText(addOnsList.get(i).getModifiergroupName());
                                                    addOnHeaderOneTextView.setVisibility(View.GONE);

                                                    addOnsListAdapter = new AddOnsRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId);
                                                    addOnHeaderOneRecyclerView.setAdapter(addOnsListAdapter);
                                                    addOnHeaderOneRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsListAdapter.notifyDataSetChanged();
                                                }

                                                if (groupId.equalsIgnoreCase("20") || groupId.equalsIgnoreCase("23") || groupId.equalsIgnoreCase("26") || groupId.equalsIgnoreCase("30")) {
                                                    addOnTwoLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderTwoTextView.setText(addOnsList.get(i).getModifiergroupName());

                                                    addOnHeaderTwoTextView.setVisibility(View.VISIBLE);

                                                    maxAddOnIdList.clear();

                                                    addOnsTwoListAdapter = new AddOnsTwoRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId, addOnsList.get(i).maximum_addon, addOnsList.get(i).minimum_addon);
                                                    addOnHeaderTwoRecyclerView.setAdapter(addOnsTwoListAdapter);
                                                    addOnHeaderTwoRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsTwoListAdapter.notifyDataSetChanged();
                                                }

                                                if (groupId.equalsIgnoreCase("13")) {
                                                    addOnFourLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderFourTextView.setText(addOnsList.get(i).getModifiergroupName());
                                                    addOnHeaderFourTextView.setVisibility(View.GONE);

                                                    addOnsFiveListAdapter = new AddOnsFiveRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId);
                                                    addOnHeaderFourRecyclerView.setAdapter(addOnsFiveListAdapter);
                                                    addOnHeaderFourRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsFiveListAdapter.notifyDataSetChanged();
                                                }

                                                if (groupId.equalsIgnoreCase("14")) {
                                                    addOnFiveLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderFiveTextView.setText(addOnsList.get(i).getModifiergroupName());
                                                    addOnHeaderFiveTextView.setVisibility(View.GONE);

                                                    addOnsSixListAdapter = new AddOnsSixRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId);
                                                    addOnHeaderFiveRecyclerView.setAdapter(addOnsSixListAdapter);
                                                    addOnHeaderFiveRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsSixListAdapter.notifyDataSetChanged();
                                                }

                                                if (groupId.equalsIgnoreCase("10") || groupId.equalsIgnoreCase("31")) {
                                                    addOnThreeLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderThreeTextView.setText(addOnsList.get(i).getModifiergroupName());
                                                    addOnHeaderThreeTextView.setVisibility(View.VISIBLE);


                                                    addOnsThreeListAdapter = new AddOnsThreeRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId, addOnsList.get(i).maximum_addon, addOnsList.get(i).minimum_addon);
                                                    addOnHeaderThreeRecyclerView.setAdapter(addOnsThreeListAdapter);
                                                    addOnHeaderThreeRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsThreeListAdapter.notifyDataSetChanged();
                                                }


                                                if (groupId.equalsIgnoreCase("11")) {
                                                    addOnOneLayout.setVisibility(View.VISIBLE);
                                                    addOnHeaderOneTextView.setText(addOnsList.get(i).getModifiergroupName());
                                                    addOnHeaderOneTextView.setVisibility(View.GONE);

                                                    addOnsListAdapter = new AddOnsRecyclerAdapter(NewPosMainActivity.this, addOnsList.get(i).productAddOnsResults, groupId, "" + kotId);
                                                    addOnHeaderOneRecyclerView.setAdapter(addOnsListAdapter);
                                                    addOnHeaderOneRecyclerView.setNestedScrollingEnabled(false);
                                                    addOnsListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        } else {
//                                        getProductDetails();
                                            addOnsDataLayout.setVisibility(View.GONE);
                                            showLongSnackBar("There are no Addons for this product");
                                            categoriesLayout.setVisibility(View.VISIBLE);
                                            productsRecyclerView.setVisibility(View.VISIBLE);
                                        }
                                    } catch (NullPointerException e) {
                                        categoriesLayout.setVisibility(View.VISIBLE);
                                        productsRecyclerView.setVisibility(View.VISIBLE);
//                                    getProductDetails();
                                    }
                                }
                            });

                        } else {
                            categoriesLayout.setVisibility(View.VISIBLE);
                            productsRecyclerView.setVisibility(View.VISIBLE);
                            addOnsDataLayout.setVisibility(View.GONE);
//                            getProductDetails();
                        }


                    } else {

                    }
                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<AddOnsResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        Context activity;
        LayoutInflater inflater;
        List<String> primaryIDList, addOnNamesList, addOnQtysList, addOnPricesList, addOnIdsList, addOnGroupList, addOnKotList;
        RadioButton selected = null;

        public ProductAdapter(Activity activity, List<String> namesList, List<String> priceList, List<String> qtyList, List<String> idList, List<String> groupList, List<String> kotList, List<String> primaryIDList) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.addOnNamesList = namesList;
            this.addOnQtysList = qtyList;
            this.addOnPricesList = priceList;
            this.addOnIdsList = idList;
            this.addOnGroupList = groupList;
            this.addOnKotList = kotList;
            this.primaryIDList = primaryIDList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity).inflate(R.layout.cart_add_ons_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            final ViewHolder finalHolder = holder;

            if (addOnGroupList.get(position).equalsIgnoreCase("10")) {
                finalHolder.addOnNameTextView.setText("-  " + addOnNamesList.get(position));
            } else {
                finalHolder.addOnNameTextView.setText("+  " + addOnNamesList.get(position));
            }
            finalHolder.addOnPriceTextView.setText(addOnPricesList.get(position));
            finalHolder.addOnQtyTextView.setText(addOnQtysList.get(position));

            int finalQty = Integer.parseInt(addOnQtysList.get(position));
            Double finalPrice = Double.parseDouble(addOnPricesList.get(position));

            Double sum = 0.00;

            sum = finalQty * finalPrice;

            finalHolder.addOnSubTotalTextView.setText("" + String.format("%.2f", sum));

            finalHolder.plusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = addOnIdsList.get(position);
                    String name = addOnNamesList.get(position);
                    String price = addOnPricesList.get(position);
                    String qty = addOnQtysList.get(position);
                    count = Integer.parseInt(db.getAddOnsQty(primaryIDList.get(position), id));
                    count = count + 1;
                    db.updateAddOnQuantity(id, primaryIDList.get(position), count + "");
                    updateList();
                }
            });

            finalHolder.minusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addOnIdsList.size() > 1) {
                        String id = addOnIdsList.get(position);
                        count = Integer.parseInt(db.getAddOnsQty(primaryIDList.get(position), id));
                        count = count - 1;
                        if (count == 0) {
                            db.deleteAddOn(primaryIDList.get(position), id);
                        } else {
                            db.updateAddOnQuantity(id, primaryIDList.get(position), count + "");
                        }
                        updateList();
                    } else if (addOnIdsList.size() == 1) {
                        String id = addOnIdsList.get(position);
                        count = Integer.parseInt(db.getAddOnsQty(primaryIDList.get(position), id));
                        count = count - 1;
                        if (count == 0) {
                            db.deleteAddOn(primaryIDList.get(position), id);
                        } else {
                            db.updateAddOnQuantity(id, primaryIDList.get(position), count + "");
                        }
                        updateList();
                    } else {
                        String id = addOnIdsList.get(position);
                        count = Integer.parseInt(db.getAddOnsQty(primaryIDList.get(position), id));
                        count = count - 1;
                        if (count == 0) {
                            db.deleteAddOn(primaryIDList.get(position), id);
                        } else {
                            db.updateAddOnQuantity(id, primaryIDList.get(position), count + "");
                        }
                        updateList();

                    }
                }
            });

            finalHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addOnIdsList.size() > 1) {
                        String id = addOnIdsList.get(position);
                        db.deleteAddOn(primaryIDList.get(position), id);
                        updateList();
                    } else {
                        infoPopup("Product should have atleast one addon");
                    }
                }
            });

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

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        Context _context;
        private List<String> _listDataHeader;
        private List<String> _listDataIdHeader;
        private List<String> _listDataPriceHeader;
        private List<String> _listDataQtyHeader;

        private HashMap<String, List<String>> _listDataChild, _listDataIdChild, _listDataPriceChild, _listDataQtyChild;

        public ExpandableListAdapter(Context context,
                                     List<String> listDataIdHeader, List<String> listDataHeader, List<String> listDataPriceHeader, List<String> listDataQtyHeader,
                                     HashMap<String, List<String>> listChildData, HashMap<String, List<String>> listChildIdData,
                                     HashMap<String, List<String>> listDataPriceChild, HashMap<String, List<String>> listDataQtyChild) {
            this._context = context;
            this._listDataIdHeader = listDataIdHeader;
            this._listDataHeader = listDataHeader;
            this._listDataPriceHeader = listDataPriceHeader;
            this._listDataQtyHeader = listDataQtyHeader;
            this._listDataChild = listChildData;
            this._listDataIdChild = listChildIdData;
            this._listDataPriceChild = listDataPriceChild;
            this._listDataQtyChild = listDataQtyChild;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(
                    this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        public Object getIdChild(int groupPosition, int childPosititon) {
            return this._listDataIdChild.get(
                    this._listDataIdHeader.get(groupPosition))
                    .get(childPosititon);
        }

        public Object getPriceChild(int groupPosition, int childPosition) {
            return this._listDataPriceChild.get(
                    this._listDataPriceHeader.get(groupPosition))
                    .get(childPosition);
        }

//        public Object getQtyChild(int groupPosition, int childPosition) {
//            return this._listDataQtyChild.get(
//                    this._listDataQtyHeader.get(groupPosition))
//                    .get(childPosition);
//        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition,
                                 final int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {

            final String childText = (String) getChild(groupPosition,
                    childPosition);
            final String price = (String) getPriceChild(groupPosition,
                    childPosition);
//            final String qty = (String) getQtyChild(groupPosition,
//                    childPosition);

            final String id = (String) getIdChild(groupPosition,
                    childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.cart_add_ons_list_item, null);
            }

            TextView addOnNameTextView = (TextView) convertView.findViewById(R.id.addOnNameTv);
            TextView addOnPriceTextView = (TextView) convertView.findViewById(R.id.addOnPriceTv);
            TextView addOnQtyTextView = (TextView) convertView.findViewById(R.id.addOnQtyTv);
            TextView addOnSubTotalTextView = (TextView) convertView.findViewById(R.id.addOnSubTotalTv);
            ImageView minusImageView = (ImageView) convertView.findViewById(R.id.add_on_cart_minus_img);
            ImageView plusImageView = (ImageView) convertView.findViewById(R.id.add_on_cart_plus_img);
            ImageView deleteImageView = (ImageView) convertView.findViewById(R.id.addOnDeleteTv);

            addOnNameTextView.setText(childText);
            addOnPriceTextView.setText("$" + price);
            addOnQtyTextView.setText("1");

            int finalQty = Integer.parseInt("1");
            Double finalPrice = Double.parseDouble(price);

            Double sum = 0.00;
            sum = finalQty * finalPrice;

            addOnSubTotalTextView.setText("" + String.format("%.2f", sum));


            plusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String position = String.valueOf(groupPosition + 1);

//                    Log.d("jkdfhkjd", id + "," + position);

//                    String qty = newDb.getAddOnsQty(id, position);
//                    int count = Integer.parseInt(qty);
//                    count = count + 1;
//                    newDb.updateQuantityWithAddOn(id, position, count + "");
//                    newUpdateList();
                }
            });

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(
                    this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        public Object getIdGroup(int groupPosition) {
            return this._listDataIdHeader.get(groupPosition);
        }

        public Object getPriceGroup(int groupPosition) {
            return this._listDataPriceHeader.get(groupPosition);
        }

        public Object getQtyGroup(int groupPosition) {
            return this._listDataQtyHeader.get(groupPosition);
        }


        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            String headerTitle = (String) getGroup(groupPosition);
            final String groupId = (String) getIdGroup(groupPosition);
            String price = (String) getPriceGroup(groupPosition);
            String qty = (String) getQtyGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.cart_recycler_view, null);
            }

            TextView txt_prod_name = (TextView) convertView.findViewById(R.id.itemNameTv);
            TextView txt_prod_price = (TextView) convertView.findViewById(R.id.priceInvoiceTv);
            TextView txt_prod_qty = (TextView) convertView.findViewById(R.id.qtyTv);
            TextView txt_sub_total = (TextView) convertView.findViewById(R.id.subTotalTv);

            ImageView img_plus = (ImageView) convertView.findViewById(R.id.cart_plus_img);
            ImageView img_minus = (ImageView) convertView.findViewById(R.id.cart_minus_img);
            ImageView img_delete = (ImageView) convertView.findViewById(R.id.statusTv);

            txt_prod_name.setText(headerTitle);
            txt_prod_price.setText(price);
            txt_prod_qty.setText(qty);

            int finalQty = Integer.parseInt(qty);
            Double finalPrice = Double.parseDouble(price);

            Double sum = 0.00;
            sum = finalQty * finalPrice;

            txt_sub_total.setText("" + String.format("%.2f", sum));

            final String position = String.valueOf(groupPosition + 1);
            img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String qty = newDb.getQtyWithSno(groupId, position);
//                    int count = Integer.parseInt(qty);
//                    count = count + 1;
//                    newDb.updateQuantityWithSno(groupId, position, count + "");
//                    newUpdateList();
                }
            });

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    public static void justifyListViewHeightBasedOnChildrenExpandable(ExpandableListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private void getHomeCategory() {

        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<CategoryResponse> call = apiService.categoryList("1234", request);

        Log.e("categoriesss", new Gson().toJson(request));

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, Response<CategoryResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CategoryResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        Log.e("categoriesssres", new Gson().toJson(response));

                        categoryListRecyclerView.setVisibility(View.VISIBLE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        backTextView.setVisibility(View.GONE);

                        categoryList = user.productResults;

                        items = new ArrayList<String>();

                        for (int i = 0; i < categoryList.size(); i++) {
                            items.add(categoryList.get(i).category_name);
                            catIdList.add(categoryList.get(i).id);
                            if (i == 0) {
                                catId = categoryList.get(i).id;
//                                Log.d("kjdfkd", catId);
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                categoryAdapter = new homeCatRecyclerAdapter(NewPosMainActivity.this, categoryList);
                                categoryListRecyclerView.setAdapter(categoryAdapter);
                                categoryListRecyclerView.setNestedScrollingEnabled(false);

                                getProductDetails();
//                                categorySpinnerData();
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
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getHomeSubCategory() {

        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        retrofit2.Call<CategoryResponse> call = apiService.subCategoryList("1234", request);

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, Response<CategoryResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CategoryResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        subCategoryListRecyclerView.setVisibility(View.VISIBLE);

                        subCategoryList = user.productResults;

                        items = new ArrayList<String>();

                        catId = subCategoryList.get(0).id;

//                        Log.d("kljdf", subCatId);
                        for (int i = 0; i < subCategoryList.size(); i++) {
                            items.add(subCategoryList.get(i).getCategory_name());
                            subCatIdList.add(subCategoryList.get(i).getId());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                subCategoryAdapter = new homeSubCatRecyclerAdapter(NewPosMainActivity.this, subCategoryList);
                                subCategoryListRecyclerView.setAdapter(subCategoryAdapter);
                                subCategoryListRecyclerView.setNestedScrollingEnabled(false);
                                getProductDetails();
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
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public class homeSubCatRecyclerAdapter extends RecyclerView.Adapter<homeSubCatRecyclerAdapter.ViewHolder> {

        Context activity;
        List<CategoryResponse.ProductListResponse> data;
        CategoryResponse.ProductListResponse masterData;
        int row_index = 0;

        public homeSubCatRecyclerAdapter(Activity activity2,
                                         List<CategoryResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public homeSubCatRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.sub_categories_item, viewGroup, false);
            homeSubCatRecyclerAdapter.ViewHolder viewHolder = new homeSubCatRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(homeSubCatRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            holder.categoryTextView.setText(masterData.getCategory_name());

//            Log.d("posssssss", "" + position);

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);
                            catId = masterData.getId();
                            String subCatName = masterData.getCategory_name();
                            sessionManager.storeSubCategoryDetails(catId, subCatName);
                            getProductDetails();
                            row_index = position;
                            subCategoryAdapter.notifyDataSetChanged();
                        }
                    });

            if (row_index == position) {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#DC143C"));
            } else {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#0f1a29"));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView categoryTextView;
            LinearLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.categoryTextView = (TextView) itemView.findViewById(R.id.productNameTv);
                this.bgLayout = itemView.findViewById(R.id.categoryItemBgLayout);

            }

        }

    }

//    public void newtableLayoutPopup() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.activity_table_layout_popup_new, null);
//        builder.setView(dialogView);
//        final AlertDialog runningOrdersDialog = builder.create();
//        runningOrdersDialog.show();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int displayWidth = displayMetrics.widthPixels;
//        int displayHeight = displayMetrics.heightPixels;
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
//        int dialogWindowWidth = (int) (displayWidth * 0.70f);
////        int dialogWindowHeight = (int) (displayHeight * 0.40f);
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;
//        runningOrdersDialog.getWindow().setAttributes(layoutParams);
//        runningOrdersDialog.setCanceledOnTouchOutside(false);
//
//        final AppCompatImageView closeTextView = runningOrdersDialog.findViewById(R.id.closeIcon);
//
//        areaRecyclerView = (RecyclerView) runningOrdersDialog.findViewById(R.id.areaRecyclerView);
//        areaRecyclerView.setVisibility(View.GONE);
//
//        areaProgressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.areaProgressBar);
//
////        noDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.noDataTextView);
////        noDataTextView.setVisibility(View.GONE);
//
//        areaNoDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.areaNoDataTextView);
//        areaNoDataTextView.setVisibility(View.GONE);
//
//        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
//        areaRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView
//
////        getAreaTablesDetails();
//
//        closeTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                runningOrdersDialog.dismiss();
//            }
//        });
//
//    }

    private void getTablesList() {

//        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TableResponse> call = apiService.tableList("1234", request);

        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TableResponse> call, Response<TableResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    TableResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        tablesList.clear();

//                        Log.d("cateRespTable", "response 12: " + new Gson().toJson(response.body()));
                        tablesList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tableAdapter = new tableRecyclerAdapter(NewPosMainActivity.this, tablesList);
                                tablesRecyclerView.setAdapter(tableAdapter);
                                tablesRecyclerView.setNestedScrollingEnabled(false);
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
            public void onFailure(Call<TableResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getRunningTablesList() {

        showProgressDialog();

        ProductsRequest request = new ProductsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_type("1");

        retrofit2.Call<TableResponse> call = apiService.runningOrderTablesList("1234", request);

        Log.e("rerere", new Gson().toJson(request));

        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TableResponse> call, Response<TableResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    TableResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        tablesList.clear();
//                        Log.d("tablecateResp", "response 12: " + new Gson().toJson(response.body()));
                        tablesList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                runningTablesAdapter = new runningTableRecyclerAdapter(NewPosMainActivity.this, tablesList);
                                tablesRecyclerView.setAdapter(runningTablesAdapter);
                                tablesRecyclerView.setNestedScrollingEnabled(false);
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
            public void onFailure(Call<TableResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getBillDetails() {

        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        Log.e("lastinvoice", new Gson().toJson(request));

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;
                        taxList = user.userResults;

                        final String orderNo = user.order_number;
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

                                //orderNo = null;

                                try {
                                    tax = Double.valueOf(taxList.getTax());
                                } catch (NullPointerException e) {
                                    tax = Double.valueOf(taxList.getFree());

                                }

                                //    Log.d("taxssss", "" + tax);

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
                                                    "[C]----------------------------------" +
//                                                    "[C]" + user.company_address + "\n" +
//                                                    "[C]Ph:" + user.mobile + "\n" +
                                                    "[L]\n" +
                                                    "[L]\n";

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String total = user.productResults.get(i).unit_total_cost;
                                        String item_note = user.productResults.get(i).item_note;

                                        if (item_note.equalsIgnoreCase("0")) {
                                            printText = printText + "[C]<b><font size='normal'>" + qty + " x " + item + "</b>[R]" + total + "\n";
                                        } else {
                                            printText = printText + "[C]<b><font size='normal'> " + qty + " x " + item + "</b>[R]" + total + "\n" +
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
                                            "[C]------------------------------------\n" +
                                            "[L]<b>SUB TOTAL [R]$" + user.grand_total + "</b>\n" +
                                            "[L]<b>DISCOUNT [R]$" + user.total_discount + "</b>\n" +
                                            "[L]<b>TAX [R]$" + tax + "</b>\n";
                                    printText = printText + "[L]<b>PAID [R]$" + user.paid_amount + "</b>\n" +
                                            "[L]<b>BALANCE[R]$" + user.balance_amount + "</b>\n" +
                                            "[L]<b>CHANGE RETURN[R]$" + user.change_return + "</b>\n" +
                                            "[C]-----------------------------------\n" +
                                            "[L]<b>Customer Name:" + user.del_customer_name + "</b>\n" +
                                            "[L]<b>Customer Mobile: " + user.del_customer_mobile + "</b>\n" +
                                            "[L]<b>Date : " + user.sales_date + "-" + user.time + "</b>\n";
//                                            "[L]<b>Time Taken : " + user.time + "</ b>\n";
                                    if (orderType.equalsIgnoreCase("Online Delivery") || orderType.equalsIgnoreCase("Take away")) {
//                                        printText = printText + "[L]" + textName + user.delivery_time + "\n";
                                    }

                                    if (orderType.equalsIgnoreCase("Dine In")) {
                                        tableNewNo = "[L]Table No : <font size='normal'>" + user.table_number + "</font></b>\n";
                                    } else {
                                        tableNewNo = "";

                                    }

                                    printText = printText +
                                            "[L]Clerk : " + user.getUser_name() + "\n" +
                                            "[L]Payment : <font size='normal'>" + user.payment_status + "</font></b>\n" +
                                            "[L]Order Type: <font size='normal'>" + user.order_type + "</font></b>\n" +
                                            tableNewNo;

                                    printText = printText + "[L]\n";
//                                            "[C]<qrcode>" + user.getCompany_website() + "</qrcode>\n" +


                                    doPrint();
//                                    printUsb();
//                                    printTcp();
                                }

                            }
                        });

                    } else {
                        hideProgressDialog();
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


    private void sendPrintStatus(String orderNo) {

        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setOrder_id(orderNo);

        showProgressDialog();
//        Log.d("printstatus", new Gson().toJson(request));

        Call<LoginResponse> call = apiService.changeprintstatus_data("1234", request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

//                Log.d("logresponseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    LoginResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                    } else {

//                        showShortSnackBar(message);

                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    private void sendKitchenPrintStatus(String orderNo) {

        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setOrder_id(orderNo);

        showProgressDialog();
//        Log.d("printstatus", new Gson().toJson(request));

        Call<LoginResponse> call = apiService.changeprintstatus_kichenData("1234", request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

//                Log.d("logresponseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    LoginResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                    } else {

//                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public void paymentTypePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
        amountTextView.setText("Order Amount  :  $" + totalAmount);

        dialog.findViewById(R.id.rejectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendCardCartData();
//                sendDataOtherPaymentOrder();
                succsessfailerpopup("Failure", totalAmount);
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

    public void voidPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.void_popup, null);
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
                customerDetailsLayout.setVisibility(View.GONE);
                db.deleteAll();
                orderNo = null;
                updateList();
                dialog.dismiss();
                selectedDiscount = 0.00;
                selectedOrderNumber = "0";
                disc = 0.00;
                grandTotal = 0.00;

                getHomeCategory();
            }
        });

    }

//    AlertDialog addKotDialog;

//    public void addKotPopup() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.activity_add_kot_layout_popup, null);
//        builder.setView(dialogView);
//        addKotDialog = builder.create();
//        addKotDialog.setCanceledOnTouchOutside(false);
//        addKotDialog.setCancelable(false);
//        addKotDialog.show();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int displayWidth = displayMetrics.widthPixels;
//        int displayHeight = displayMetrics.heightPixels;
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(addKotDialog.getWindow().getAttributes());
//        int dialogWindowWidth = (int) (displayWidth * 0.70f);
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;
//        addKotDialog.getWindow().setAttributes(layoutParams);
//
//        final AppCompatButton newOrderButton = addKotDialog.findViewById(R.id.newOrderButton);
//        final AppCompatButton runningOrderButton = addKotDialog.findViewById(R.id.runningOrderButton);
//        final AppCompatImageView closeImageView = addKotDialog.findViewById(R.id.closeIcon);
//        final AppCompatTextView backButton = addKotDialog.findViewById(R.id.addKotBackTv);
//        final AppCompatTextView saveButton = addKotDialog.findViewById(R.id.addKotSaveTv);
//
//        final RelativeLayout tableTypeLayout = addKotDialog.findViewById(R.id.tableTypeLayout);
//        final RelativeLayout formLayout = addKotDialog.findViewById(R.id.customerFormLayout);
//
//        tablesRecyclerView = (RecyclerView) addKotDialog.findViewById(R.id.tablesRecyclerView);
//        tablesRecyclerView.setVisibility(View.VISIBLE);
//
//        final AppCompatButton takeAwayButton = addKotDialog.findViewById(R.id.takeAwayOrderButton);
//        final AppCompatButton deliveryButton = addKotDialog.findViewById(R.id.deliveryOrderButton);
//
//        final AppCompatEditText customerMobileEt = addKotDialog.findViewById(R.id.customerMobileEt);
//        final AppCompatEditText customerNameEt = addKotDialog.findViewById(R.id.customerNameEt);
//        final AppCompatEditText customerAddressEt = addKotDialog.findViewById(R.id.customerAddressEt);
//        final AppCompatEditText customerEmailEt = addKotDialog.findViewById(R.id.customerEmailEt);
//        final AppCompatEditText customerPostCodeEt = addKotDialog.findViewById(R.id.customerPostCodeEt);
//
//        GridLayoutManager addKotLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 4);
//        tablesRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView
//
//        getTablesList();
//        newOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
//
//        newOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getTablesList();
//                newOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
//                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
//                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
//                tablesRecyclerView.setVisibility(View.VISIBLE);
//                formLayout.setVisibility(View.GONE);
//            }
//        });
//
//        runningOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getRunningTablesList();
//                runningOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
//                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
//                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
//                tablesRecyclerView.setVisibility(View.VISIBLE);
//                formLayout.setVisibility(View.GONE);
//            }
//        });
//
//        takeAwayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getTablesList();
//                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                takeAwayButton.setBackgroundColor(Color.parseColor("#DC143C"));
//                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
//                tablesRecyclerView.setVisibility(View.GONE);
//                customerAddressEt.setVisibility(View.GONE);
//                customerPostCodeEt.setVisibility(View.GONE);
//                formLayout.setVisibility(View.VISIBLE);
//                saveOrderType = "1";
//            }
//        });
//
//        deliveryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getTablesList();
//                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
//                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
//                deliveryButton.setBackgroundColor(Color.parseColor("#DC143C"));
//                tablesRecyclerView.setVisibility(View.GONE);
//                customerAddressEt.setVisibility(View.VISIBLE);
//                customerPostCodeEt.setVisibility(View.VISIBLE);
//                formLayout.setVisibility(View.VISIBLE);
//                saveOrderType = "2";
//            }
//        });
//
//        closeImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addKotDialog.dismiss();
//            }
//        });
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addKotDialog.dismiss();
//            }
//        });
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                customer_name = customerNameEt.getText().toString();
//                customer_mobile = customerMobileEt.getText().toString();
//                customer_email = customerEmailEt.getText().toString();
//                customer_address = customerAddressEt.getText().toString();
//                customer_post_code = customerPostCodeEt.getText().toString();
//
//                if (saveOrderType.equalsIgnoreCase("1") || saveOrderType.equalsIgnoreCase("2")) {
//                    if (updateData.equalsIgnoreCase("1"))
//                        sendCartData();
//                    else
//                        sendDataWithoutOrder();
//                    addKotDialog.dismiss();
//                } else {
//                    if (!tableNumber.equalsIgnoreCase("0")) {
//                        if (updateData.equalsIgnoreCase("1"))
//                            sendCartData();
//                        else
//                            sendDataWithoutOrder();
//                        addKotDialog.dismiss();
//                    } else {
//                        showLongSnackBar("Please choose one table");
//                    }
//                }
//            }
//        });
//
//    }

    public void addKotData() {


        final AppCompatButton newOrderButton = findViewById(R.id.newOrderButton);
        final AppCompatButton runningOrderButton = findViewById(R.id.runningOrderButton);
        final AppCompatTextView backButton = findViewById(R.id.addKotBackTv);
        final AppCompatTextView saveButton = findViewById(R.id.addKotSaveTv);

        final RelativeLayout tableTypeLayout = findViewById(R.id.tableTypeLayout);
        final RelativeLayout formLayout = findViewById(R.id.customerFormLayout);

        tablesRecyclerView = (RecyclerView) findViewById(R.id.tablesRecyclerView);
        tablesRecyclerView.setVisibility(View.VISIBLE);

        final AppCompatButton takeAwayButton = findViewById(R.id.takeAwayOrderButton);
        final AppCompatButton deliveryButton = findViewById(R.id.deliveryOrderButton);

        final AppCompatEditText customerMobileEt = findViewById(R.id.customerMobileEt);
        final AppCompatEditText customerNameEt = findViewById(R.id.customerNameEt);
        final AppCompatEditText customerAddressEt = findViewById(R.id.customerAddressEt);
        final AppCompatEditText customerEmailEt = findViewById(R.id.customerEmailEt);
        final AppCompatEditText customerPostCodeEt = findViewById(R.id.customerPostCodeEt);

        GridLayoutManager addKotLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 10);
        tablesRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getTablesList();
        newOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
        runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
        takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
        deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
        tablesRecyclerView.setVisibility(View.VISIBLE);
        formLayout.setVisibility(View.GONE);

        customerMobileEt.setText("");
        customerNameEt.setText("");
        customerAddressEt.setText("");
        customerEmailEt.setText("");
        customerPostCodeEt.setText("");
        saveOrderType = "0";
        tableNumber = "0";

        newOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTablesList();
                newOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
                tablesRecyclerView.setVisibility(View.VISIBLE);
                productsRecyclerView.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
                saveOrderType = "0";
            }
        });

        runningOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRunningTablesList();
                runningOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
                tablesRecyclerView.setVisibility(View.VISIBLE);
                productsRecyclerView.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
                saveOrderType = "0";
            }
        });

        takeAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTablesList();
                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                takeAwayButton.setBackgroundColor(Color.parseColor("#DC143C"));
                deliveryButton.setBackgroundColor(Color.parseColor("#09478f"));
                tablesRecyclerView.setVisibility(View.GONE);
                customerAddressEt.setVisibility(View.GONE);
                customerPostCodeEt.setVisibility(View.GONE);
                formLayout.setVisibility(View.VISIBLE);
                saveOrderType = "1";
            }
        });

        deliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTablesList();
                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                takeAwayButton.setBackgroundColor(Color.parseColor("#09478f"));
                deliveryButton.setBackgroundColor(Color.parseColor("#DC143C"));
                tablesRecyclerView.setVisibility(View.GONE);
                customerAddressEt.setVisibility(View.VISIBLE);
                customerPostCodeEt.setVisibility(View.VISIBLE);
                formLayout.setVisibility(View.VISIBLE);
                saveOrderType = "2";
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeKotLayout.setVisibility(View.GONE);
                addMiscProductLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.GONE);
                placeKotLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.VISIBLE);
                productsRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (saveOrderType.equalsIgnoreCase("1")) {

                    if (customerMobileEt.getText().toString().trim().length() > 8) {
                        if (customerNameEt.getText().toString().trim().length() > 1) {

                            customer_name = customerNameEt.getText().toString();
                            customer_mobile = customerMobileEt.getText().toString();
                            customer_email = customerEmailEt.getText().toString();
                            customer_address = customerAddressEt.getText().toString();
                            customer_post_code = customerPostCodeEt.getText().toString();

                            if (updateData.equalsIgnoreCase("1"))
                                sendCartData();
                            else
                                sendDataWithoutOrder();

                        } else {
                            showLongSnackBar("Please enter name");
                        }

                    } else {
                        showLongSnackBar("Please enter mobile number");
                    }

                } else if (saveOrderType.equalsIgnoreCase("2")) {

                    if (customerMobileEt.getText().toString().trim().length() > 8) {
                        if (customerNameEt.getText().toString().trim().length() > 1) {
                            if (customerAddressEt.getText().toString().trim().length() > 6) {
                                if (customerPostCodeEt.getText().toString().trim().length() == 4) {

                                    customer_name = customerNameEt.getText().toString();
                                    customer_mobile = customerMobileEt.getText().toString();
                                    customer_email = customerEmailEt.getText().toString();
                                    customer_address = customerAddressEt.getText().toString();
                                    customer_post_code = customerPostCodeEt.getText().toString();

                                    if (updateData.equalsIgnoreCase("1"))
                                        sendCartData();
                                    else
                                        sendDataWithoutOrder();

                                } else {
                                    showLongSnackBar("Please enter valid postcode");
                                }
                            } else {
                                showLongSnackBar("Please enter address");
                            }

                        } else {
                            showLongSnackBar("Please enter name");
                        }

                    } else {
                        showLongSnackBar("Please enter mobile number");
                    }

                } else {
                    if (!tableNumber.equalsIgnoreCase("0")) {
                        if (updateData.equalsIgnoreCase("1"))
                            sendCartData();
                        else
                            sendDataWithoutOrder();
                    } else {
                        showLongSnackBar("Please choose one table");
                    }
                }
            }
        });

    }

    public void mergeTablesData() {

        categoriesLayout.setVisibility(View.GONE);

        final AppCompatButton newOrderButton = findViewById(R.id.mergenewOrderButton);
        final AppCompatButton runningOrderButton = findViewById(R.id.mergerunningOrderButton);

        final AppCompatTextView backButton = findViewById(R.id.mergeBackTv);
        final AppCompatTextView saveButton = findViewById(R.id.mergeSaveTv);

        final RelativeLayout tableTypeLayout = findViewById(R.id.tableTypeLayout);
        final RelativeLayout formLayout = findViewById(R.id.customerFormLayout);

        tablesRecyclerView = (RecyclerView) findViewById(R.id.mergetablesRecyclerView);
        tablesRecyclerView.setVisibility(View.VISIBLE);

        GridLayoutManager addKotLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 4);
        tablesRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getRunningTablesList();
        newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
        runningOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
        tablesRecyclerView.setVisibility(View.VISIBLE);
        formLayout.setVisibility(View.GONE);

        saveOrderType = "0";
        tableNumber = "0";

        newOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTablesList();
                newOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
                runningOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                tablesRecyclerView.setVisibility(View.VISIBLE);
                formLayout.setVisibility(View.GONE);
                saveOrderType = "0";
            }
        });

        runningOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRunningTablesList();
                runningOrderButton.setBackgroundColor(Color.parseColor("#DC143C"));
                newOrderButton.setBackgroundColor(Color.parseColor("#09478f"));
                tablesRecyclerView.setVisibility(View.VISIBLE);
                formLayout.setVisibility(View.GONE);
                saveOrderType = "0";
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeKotLayout.setVisibility(View.GONE);
                mergeTablesLayoutLayout.setVisibility(View.GONE);
                addMiscProductLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.GONE);
                placeKotLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTableMergeResponse();

            }
        });

    }

    private void getTableMergeResponse() {

        TableShiftRequest request = new TableShiftRequest();

        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setTable_source(mergeSourceTableId);
        request.setTable_destination(mergeDestTableId);
        request.setOrder_id(mergeTableOrderNo);

        showProgressDialog();
//        Log.d("mergesaveRequest", new Gson().toJson(request));

        Call<TableShiftResponse> call = apiService.tableShiftData("1234", request);

        call.enqueue(new Callback<TableShiftResponse>() {
            @Override
            public void onResponse(Call<TableShiftResponse> call, retrofit2.Response<TableShiftResponse> response) {
//                Log.d("saveResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    TableShiftResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {

                        infoPopup(user.getMessage());

                        getRunningOrderDetails();

                        categoriesLayout.setVisibility(View.VISIBLE);
                        mergeTablesLayoutLayout.setVisibility(View.GONE);

                    } else {
                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<TableShiftResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    public void addOnsRepeatPopup(final String productId, final String productName, final String productPrice, String addOnName, final String addOnsId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.repeat_or_choose_item_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        final AppCompatButton chooseButton = dialog.findViewById(R.id.iWillChooseTextView);
        final AppCompatButton repeatButton = dialog.findViewById(R.id.repeatTextView);
        final AppCompatImageView closeImageView = dialog.findViewById(R.id.closeIcon);
        final AppCompatTextView itemNameTextView = dialog.findViewById(R.id.itemNameTv);
        final AppCompatTextView itemPriceTextView = dialog.findViewById(R.id.itemPriceTv);
        final AppCompatTextView addOnsTextView = dialog.findViewById(R.id.addonsTextView);

        itemNameTextView.setText(productName);
        itemPriceTextView.setText("$" + productPrice);
        addOnName.replaceAll(",", "\\n");
        addOnsTextView.setText(addOnName);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                String qty = db.getQty("" + rowid);
                count = Integer.parseInt(qty) + 1;
                db.updateQuantity("" + rowid, productId, count + "");
                updateList();


                if (!addOnsId.isEmpty()) {
                    List<String> addOnIdsList = Arrays.asList(addOnsId.split(","));
                    for (int i = 0; i < addOnIdsList.size(); i++) {
                        String id = addOnIdsList.get(i);
                        count = Integer.parseInt(db.getAddOnsQty("" + rowid, id));
                        count = count + 1;
                        db.updateAddOnQuantity(id, "" + rowid, count + "");
                    }
                }

                updateList();

                dialog.dismiss();
            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productStatus = "No";
                count = 0;
                count = count + 1;
                addNote = "";
                rowid = db.addCategory(new Product(productId, "" + kotId, productName, "" + "1", addNote, productPrice));
                kotId = kotId + 1;

                getProductAddOnsDetails();

                updateList();
                dialog.dismiss();

            }
        });


    }

    public void setDiscountPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_set_discount_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.60f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        final AppCompatButton percentageButton = dialog.findViewById(R.id.percentageButton);
        final AppCompatButton fixedButton = dialog.findViewById(R.id.fixedButton);
        final AppCompatImageView closeImageView = dialog.findViewById(R.id.closeIcon);
        final AppCompatTextView backButton = dialog.findViewById(R.id.discountCloseTv);
        final AppCompatTextView saveButton = dialog.findViewById(R.id.discountSaveTv);
        final AppCompatEditText discountEditText = dialog.findViewById(R.id.discountEt);
        final AppCompatTextView errorTextView = dialog.findViewById(R.id.errorTextView);

        discountEditText.setText("");

        discountType = "Percentage";
        percentageButton.setBackgroundColor(Color.parseColor("#1C60AE"));
        percentageButton.setTextColor(Color.parseColor("#ffffff"));
        fixedButton.setBackgroundColor(Color.parseColor("#F0FAFF"));
        fixedButton.setTextColor(Color.parseColor("#555555"));

        percentageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentageButton.setBackgroundColor(Color.parseColor("#1C60AE"));
                percentageButton.setTextColor(Color.parseColor("#ffffff"));
                fixedButton.setBackgroundColor(Color.parseColor("#F0FAFF"));
                fixedButton.setTextColor(Color.parseColor("#555555"));
                discountType = "Percentage";
            }
        });

        fixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fixedButton.setBackgroundColor(Color.parseColor("#1C60AE"));
                fixedButton.setTextColor(Color.parseColor("#ffffff"));
                percentageButton.setBackgroundColor(Color.parseColor("#F0FAFF"));
                percentageButton.setTextColor(Color.parseColor("#555555"));
                discountType = "Fixed";
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (discountEditText.getText().toString().isEmpty()) {
                    discount = "0";
                } else {
                    discount = discountEditText.getText().toString();
                }

                subTotal = Double.parseDouble(String.valueOf(round(subTotal, 2)));

                try {
                    Double discountInput = Double.parseDouble(discount);

                    if (discountType.equalsIgnoreCase("Percentage")) {
//                        Log.d("Percentage", discountInput + "");
                        if (discountInput <= 100.00) {

//                            Log.d("kljlk", discountInput + "");

                            selectedDiscount = Double.parseDouble(discount);
                            disc = (subTotal * selectedDiscount) / 100;
                            discountTextView.setText("$" + String.format("%.2f", disc));
                            sessionManager.setDiscount("" + disc);
                            grandTotal = subTotal + tax - disc;
                            grandTotalTextView.setText("$" + String.format("%.2f", grandTotal));

                            totalAmount = grandTotal;
                            errorTextView.setVisibility(View.GONE);
                            dialog.dismiss();
                        } else {
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.setText("Please enter valid Percentage. It should be less than or equal to 100");
                        }
                    } else if (discountType.equalsIgnoreCase("Fixed")) {
//                        Log.d("Fixed", discountInput + ", " + subTotal);

                        int retval = Double.compare(discountInput, subTotal);
//                        Log.d("Fixed", retval + ", ");
                        if (retval <= 0) {
//                            Log.d("kljlk", discountInput + ", " + subTotal);
                            selectedDiscount = Double.parseDouble(discount);
                            disc = Double.parseDouble(discount);
                            discountTextView.setText("$" + String.format("%.2f", disc));
                            sessionManager.setDiscount("" + disc);
                            grandTotal = subTotal + tax - disc;
                            grandTotalTextView.setText("$" + String.format("%.2f", grandTotal));

                            totalAmount = grandTotal;
                            errorTextView.setVisibility(View.GONE);
                            dialog.dismiss();
                        } else {
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.setText("Please enter less than or equal to cart amount i.e.," + subTotal);
                        }
                    }
                } catch (NumberFormatException e) {
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("Invalid expression");
                }

            }
        });

    }

    AlertDialog morerunningOrdersDialog;

    public void moreLayoutPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_more_layout_popup, null);
        builder.setView(dialogView);
        morerunningOrdersDialog = builder.create();
        morerunningOrdersDialog.show();
        morerunningOrdersDialog.setCanceledOnTouchOutside(false);
        morerunningOrdersDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(morerunningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.60f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        morerunningOrdersDialog.getWindow().setAttributes(layoutParams);
        morerunningOrdersDialog.setCanceledOnTouchOutside(false);

        final AppCompatImageView closeTextView = morerunningOrdersDialog.findViewById(R.id.closeIcon);
//        final LinearLayoutCompat moreHomeLayout = morerunningOrdersDialog.findViewById(R.id.moreHomeLayout);

        morerunningOrdersDialog.findViewById(R.id.searchLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchPopup();
                Intent intent = new Intent(getApplicationContext(), KitchenMainActivity.class);
                startActivity(intent);
            }
        });

        morerunningOrdersDialog.findViewById(R.id.moreHomeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchPopup();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.uberEatsLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                morerunningOrdersDialog.dismiss();
                orderType = "1";
                getOrderDetails(orderType);

                categoriesLayout.setVisibility(View.VISIBLE);
                productsRecyclerView.setVisibility(View.VISIBLE);

            }
        });

        morerunningOrdersDialog.findViewById(R.id.dayEndReportLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayEndReportPopup();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.updateItemLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProductPopup();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.cashInOutLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashInOutPopUp();
                morerunningOrdersDialog.dismiss();
            }
        });


        morerunningOrdersDialog.findViewById(R.id.shiftInOutLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftInOutPopUp();
                morerunningOrdersDialog.dismiss();
            }
        });


        morerunningOrdersDialog.findViewById(R.id.newsaleLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.returnsLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReturnListPopup();
//                sessionManager.logoutUser();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.addCategoryLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategoryPopup();
                morerunningOrdersDialog.dismiss();

            }
        });

        morerunningOrdersDialog.findViewById(R.id.addProductLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAdminProductPopup();
                morerunningOrdersDialog.dismiss();
            }
        });


        morerunningOrdersDialog.findViewById(R.id.siteSettingsLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCompanyDetailsActivity.class);
                startActivity(intent);
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.uberEatsLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceId = "6";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.menulogLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceId = "7";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.doordashLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceId = "8";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.othersLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                priceId = "9";
//                db.deleteAll();
//                orderNo = null;
//                updateList();
//                selectedDiscount = 0.00;
////                getProductDetails();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.tillLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCashBoxOpen2();
                morerunningOrdersDialog.dismiss();
            }
        });

        morerunningOrdersDialog.findViewById(R.id.posLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceId = "0";
                db.deleteAll();
                orderNo = null;
                updateList();
                selectedDiscount = 0.00;
                getProductDetails();
                morerunningOrdersDialog.dismiss();
            }
        });


        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morerunningOrdersDialog.dismiss();
            }
        });

    }

    public void tableLayoutPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_table_layout_popup, null);
        builder.setView(dialogView);
        final AlertDialog runningOrdersDialog = builder.create();
        runningOrdersDialog.show();
        runningOrdersDialog.setCanceledOnTouchOutside(false);
        runningOrdersDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.70f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        runningOrdersDialog.getWindow().setAttributes(layoutParams);
        runningOrdersDialog.setCanceledOnTouchOutside(false);

        final AppCompatImageView closeTextView = runningOrdersDialog.findViewById(R.id.closeIcon);

        areaRecyclerView = (RecyclerView) runningOrdersDialog.findViewById(R.id.areaRecyclerView);
        areaRecyclerView.setVisibility(View.GONE);

        areaTablesRecyclerView = (RecyclerView) runningOrdersDialog.findViewById(R.id.areaTablesRecyclerView);
        areaTablesRecyclerView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        areaNoDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.areaNoDataTextView);
        areaNoDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        areaProgressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.areaProgressBar);

//
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        areaTablesRecyclerView.setLayoutManager(mLayoutManager);

        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        areaRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getAreaDetails();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runningOrdersDialog.dismiss();
            }
        });

    }

    public void OrderDetailsPopup(String orderNo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.order_detail_layout_popup, null);
        builder.setView(dialogView);
        orderDetailsPopup = builder.create();
        orderDetailsPopup.show();
        orderDetailsPopup.setCancelable(false);
        orderDetailsPopup.setCanceledOnTouchOutside(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(orderDetailsPopup.getWindow().getAttributes());


//        int dialogWindowWidth = (int) (displayWidth * 0.60f);
////        int dialogWindowHeight = (int) (displayHeight * 0.40f);
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;

        orderDetailsPopup.getWindow().setAttributes(layoutParams);

        layoutParams.copyFrom(orderDetailsPopup.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.57f);
//        int dialogWindowHeight = (int) (displayHeight * 0.80f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        orderDetailsPopup.getWindow().setAttributes(layoutParams);

        orderDetailsPopup.getWindow().setGravity(Gravity.RIGHT);

        closeIcon = orderDetailsPopup.findViewById(R.id.closeIcon);

        orderDetailsItemsRecyclerView = orderDetailsPopup.findViewById(R.id.orderDetailsItemsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
        orderDetailsItemsRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        orderIdTextViewNew = orderDetailsPopup.findViewById(R.id.orderIdTextViewNew);
        orderTypeTextView = orderDetailsPopup.findViewById(R.id.orderTypeTextView);
        orderDateTv = orderDetailsPopup.findViewById(R.id.orderDateTv);

        customerNameTextViewNew = orderDetailsPopup.findViewById(R.id.customerNameTextViewNew);
        customerMobileTextView = orderDetailsPopup.findViewById(R.id.customerMobileTextView);
        paymnentStatusTv = orderDetailsPopup.findViewById(R.id.paymnentStatusTv);

        tableNoTextViewNew = orderDetailsPopup.findViewById(R.id.tableNoTextViewNew);
        amountTextView = orderDetailsPopup.findViewById(R.id.amountTextView);

        getOrderDetailsData(orderNo);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDetailsPopup.dismiss();
            }
        });


    }

    private void getOrderDetailsData(String newOrderNo) {

        PrintKotRequest request = new PrintKotRequest();

        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_id(newOrderNo);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        Log.d("Orderdemodfyr", new Gson().toJson(request));
        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                       // Log.d("kotdetailResp", "response 1112: " + new Gson().toJson(response.body()));
                        orderList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                orderIdTextViewNew.setText("Order No: " + user.getOrder_id());
                                orderTypeTextView.setText("Type: " + user.getOrder_type());
                                orderDateTv.setText("Date: " + user.getSales_date());
                                customerNameTextViewNew.setText("Name: " + user.getCustomer());
                                customerMobileTextView.setText("Mobile: " + user.getMobile());
                                paymnentStatusTv.setText("Status: " + user.getPayment_status());
//                                tableNoTextViewNew.setText(user.);
                                amountTextView.setText("Total: " + user.getTotal_amount());

                                OrderDetailsRecyclerAdapter LoansListAdapter = new OrderDetailsRecyclerAdapter(NewPosMainActivity.this, orderList);
                                orderDetailsItemsRecyclerView.setAdapter(LoansListAdapter);
                                orderDetailsItemsRecyclerView.setNestedScrollingEnabled(false);
                                LoansListAdapter.notifyDataSetChanged();


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

    public class OrderDetailsRecyclerAdapter extends RecyclerView.Adapter<OrderDetailsRecyclerAdapter.ViewHolder> {

        Context activity;

        List<PrintBillResponse.ProductListResponse> data2;
        PrintBillResponse.ProductListResponse masterData2;

        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data3;
        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData3;
        Boolean selected;
        int row_index;

        public OrderDetailsRecyclerAdapter(Activity activity2,
                                           List<PrintBillResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.order_details_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            data3 = masterData2.productAddOnsResults;

            holder.orderDetailItemTv.setText(masterData2.sales_qty + " X " + masterData2.item_name);
            if (masterData2.item_note.equalsIgnoreCase("0")) {
                holder.orderDetailItemNoteTv.setVisibility(View.GONE);
            } else {
                holder.orderDetailItemNoteTv.setText(masterData2.item_note);
            }


            final ViewHolder finalHolder = holder;

            LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
            holder.orderDetailAddonsRecyclerView.setLayoutManager(mLayoutManager2);

            orderDetailsAddOnsRecyclerAdapter = new OrderDetailsAddOnsRecyclerAdapter(NewPosMainActivity.this, data3);
            holder.orderDetailAddonsRecyclerView.setAdapter(orderDetailsAddOnsRecyclerAdapter);
            holder.orderDetailAddonsRecyclerView.setNestedScrollingEnabled(false);
            orderDetailsAddOnsRecyclerAdapter.notifyDataSetChanged();


        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderDetailItemTv, orderDetailItemNoteTv;
            RecyclerView orderDetailAddonsRecyclerView;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.orderDetailItemTv = itemView.findViewById(R.id.orderDetailItemTv);
                this.orderDetailItemNoteTv = itemView.findViewById(R.id.orderDetailItemNoteTv);
                this.orderDetailAddonsRecyclerView = itemView.findViewById(R.id.orderDetailAddonsRecyclerView);


            }

        }

    }

    public class OrderDetailsAddOnsRecyclerAdapter extends RecyclerView.Adapter<OrderDetailsAddOnsRecyclerAdapter.ViewHolder> {

        Context activity;

        List<PrintBillResponse.ProductListResponse> data;
        PrintBillResponse.ProductListResponse masterData;

        List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> data1;
        PrintBillResponse.ProductListResponse.ProductAddOnsListResponse masterData1;


        public OrderDetailsAddOnsRecyclerAdapter(Activity activity2, List<PrintBillResponse.ProductListResponse.ProductAddOnsListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.order_detail_addons_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.orderDetailaddOnsItemTv.setText(masterData1.getQty() + " X " + masterData1.getAddon_name());

//            holder.modifystatusTv.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView orderDetailaddOnsItemTv;

            public ViewHolder(final View itemView) {
                super(itemView);

                orderDetailaddOnsItemTv = (TextView) itemView.findViewById(R.id.orderDetailaddOnsItemTv);

            }
        }

    }

    public void tableLayoutData() {

        areaRecyclerView = (RecyclerView) findViewById(R.id.areaRecyclerView);
        areaRecyclerView.setVisibility(View.GONE);

        areaTablesRecyclerView = (RecyclerView) findViewById(R.id.areaTablesRecyclerView);
        areaTablesRecyclerView.setVisibility(View.GONE);

//        roomOneLayout = findViewById(R.id.roomOneLayout);
//        roomTwoLayout = findViewById(R.id.roomTwoLayout);
//        roomThreeLayout = findViewById(R.id.roomThreeLayout);

        noDataTextView = (AppCompatTextView) findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        areaNoDataTextView = (AppCompatTextView) findViewById(R.id.areaNoDataTextView);
        areaNoDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        progressBar.setVisibility(View.GONE);

        areaProgressBar = (ProgressBar) findViewById(R.id.areaProgressBar);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        areaTablesRecyclerView.setLayoutManager(mLayoutManager);

        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        areaRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getAreaDetails();

    }

    AlertDialog runningOrdersDialog;

    public void runningOrdersPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.running_orders_popup, null);
        builder.setView(dialogView);
        runningOrdersDialog = builder.create();
        runningOrdersDialog.show();
        runningOrdersDialog.setCanceledOnTouchOutside(false);
        runningOrdersDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.90f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        runningOrdersDialog.getWindow().setAttributes(layoutParams);

        final AppCompatImageView closeTextView = runningOrdersDialog.findViewById(R.id.closeIcon);

        runningOrdersListView = (RecyclerView) runningOrdersDialog.findViewById(R.id.runningOrdersListView);
        runningOrdersListView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.progressBar);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        runningOrdersListView.setLayoutManager(mLayoutManager);

//        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        runningOrdersListView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getRunningOrderDetails();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runningOrdersDialog.dismiss();
            }
        });

    }

    public void ordersPopup(String order_type) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orders_list_popup, null);
        builder.setView(dialogView);
        runningOrdersDialog = builder.create();
        runningOrdersDialog.show();
        runningOrdersDialog.setCanceledOnTouchOutside(false);
        runningOrdersDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.60f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        runningOrdersDialog.getWindow().setAttributes(layoutParams);

        final AppCompatTextView closeTextView = runningOrdersDialog.findViewById(R.id.closeTextView);

        ordersRecyclerView = (RecyclerView) runningOrdersDialog.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ordersRecyclerView.setLayoutManager(mLayoutManager);

//        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        runningOrdersListView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView
        orderType = order_type;
//        getOrderDetails();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runningOrdersDialog.dismiss();
            }
        });

    }

    Dialog couponsDialog;

    public void couponsPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.coupons_list_popup, null);
        builder.setView(dialogView);
        couponsDialog = builder.create();
        couponsDialog.show();
        couponsDialog.setCanceledOnTouchOutside(false);
        couponsDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(couponsDialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = (int) (displayHeight * 0.60f);
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        couponsDialog.getWindow().setAttributes(layoutParams);

        final AppCompatTextView closeTextView = couponsDialog.findViewById(R.id.closeTextView);

        couponsRecyclerView = (RecyclerView) couponsDialog.findViewById(R.id.couponsRecyclerView);
        couponsRecyclerView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) couponsDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) couponsDialog.findViewById(R.id.progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        couponsRecyclerView.setLayoutManager(mLayoutManager);

//        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        runningOrdersListView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getCouponsResponse();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponsDialog.dismiss();
            }
        });

    }

    private void getCouponsResponse() {
//        showProgressDialog();
        CouponsRequest request = new CouponsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        retrofit2.Call<CouponsResponse> call = apiService.couponsData("1234", request);
        call.enqueue(new Callback<CouponsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CouponsResponse> call, Response<CouponsResponse> response) {
                if (response.isSuccessful()) {
//                    hideProgressDialog();
                    CouponsResponse user = response.body();
                    String message = user.message;
                    String success = user.responseCode;
                    progressBar.setVisibility(View.GONE);
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        couponsList = user.productResults;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (couponsList.size() > 0) {
                                    couponsRecyclerView.setVisibility(View.VISIBLE);
                                    noDataTextView.setVisibility(View.GONE);
                                    couponsAdapter couponsAdapter = new couponsAdapter(NewPosMainActivity.this, couponsList);
                                    couponsRecyclerView.setAdapter(couponsAdapter);
                                    couponsRecyclerView.setNestedScrollingEnabled(false);
                                    couponsAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        noDataTextView.setVisibility(View.VISIBLE);
                        couponsRecyclerView.setVisibility(View.GONE);
                        showLongSnackBar(message);
                    }
                } else {
//                    hideProgressDialog();
                    progressBar.setVisibility(View.GONE);
                    couponsRecyclerView.setVisibility(View.GONE);
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<CouponsResponse> call, Throwable t) {
//                hideProgressDialog();
                progressBar.setVisibility(View.GONE);
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public class couponsAdapter extends RecyclerView.Adapter<couponsAdapter.ViewHolder> {
        Context activity;
        List<CouponsResponse.ProductListResponse> data;
        CouponsResponse.ProductListResponse masterData;
        int row_index = 0;

        public couponsAdapter(Activity activity2,
                              List<CouponsResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.coupons_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            masterData = data.get(position);

            if (!masterData.getCoupon_name().isEmpty())
                holder.couponNameTv.setText(masterData.getCoupon_name());

            if (!masterData.getCoupon_offertext().isEmpty())
                holder.couponOfferTv.setText(masterData.getCoupon_offertext() + "% off");

            holder.couponApplyBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);
                            discount = masterData.getCoupon_offertext();
                            if (db.getAllCategories().size() > 0) {

                                subTotal = Double.parseDouble(String.valueOf(round(subTotal, 2)));

                                int discnt = Integer.parseInt(discount);
                                disc = (subTotal * discnt) / 100;

                                discountTextView.setText("$" + String.format("%.2f", disc));
                                grandTotal = subTotal + tax - disc;
                                grandTotalTextView.setText("$" + String.format("%.2f", grandTotal));

                                totalAmount = grandTotal;

                                selectedDiscount = Double.parseDouble(discount);

                                couponsDialog.dismiss();

                            } else {

                            }

                        }
                    });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView couponIdTv, couponNameTv, couponOfferTv, couponApplyBtn;

            public ViewHolder(final View itemView) {
                super(itemView);
//                this.couponIdTv = (TextView) itemView.findViewById(R.id.couponIdTv);
                this.couponNameTv = (TextView) itemView.findViewById(R.id.couponNameTv);
                this.couponOfferTv = (TextView) itemView.findViewById(R.id.couponDiscountTv);
                this.couponApplyBtn = (TextView) itemView.findViewById(R.id.applyButton);
            }
        }
    }

    public class homeCatRecyclerAdapter extends RecyclerView.Adapter<homeCatRecyclerAdapter.ViewHolder> {

        Context activity;
        List<CategoryResponse.ProductListResponse> data;
        CategoryResponse.ProductListResponse masterData;
        int row_index = 0;

        public homeCatRecyclerAdapter(Activity activity2,
                                      List<CategoryResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public homeCatRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.categories_item, viewGroup, false);
            homeCatRecyclerAdapter.ViewHolder viewHolder = new homeCatRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(homeCatRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            holder.categoryTextView.setText(masterData.getCategory_name());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);
                            catId = masterData.getId();
                            String catName = masterData.getCategory_name();
                            sessionManager.storeCategoryDetails(catId, catName);
                            getProductDetails();
                            row_index = position;
                            categoryAdapter.notifyDataSetChanged();
                            customerDetailsLayout.setVisibility(View.GONE);
                        }
                    });

            if (row_index == position) {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#DC143C"));
            } else {
                holder.bgLayout.setBackgroundColor(Color.parseColor(masterData.color_code));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView categoryTextView;
            LinearLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);
                this.categoryTextView = (TextView) itemView.findViewById(R.id.productNameTv);
                this.bgLayout = itemView.findViewById(R.id.categoryItemBgLayout);
            }

        }

    }

    public class tableRecyclerAdapter extends RecyclerView.Adapter<tableRecyclerAdapter.ViewHolder> {

        Context activity;
        List<TableResponse.ProductListResponse> data;
        TableResponse.ProductListResponse masterData;
        int column_index = 100;

        public tableRecyclerAdapter(Activity activity2,
                                    List<TableResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public tableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.table_item, viewGroup, false);
            tableRecyclerAdapter.ViewHolder viewHolder = new tableRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(tableRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            holder.categoryTextView.setText(masterData.getTable_name());
            holder.nameTextView.setText(masterData.area);
            holder.tablePersonsTextView.setText(masterData.table_capacity);

//            Log.d("posssssss", "" + position);

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);
                            catId = masterData.getId();
                            tableNumber = masterData.getId();
//                            Log.d("rererere", tableNumber);
                            updateData = "0";
                            orderNo = null;
                            String catName = masterData.getTable_name();
                            sessionManager.storeCategoryDetails(catId, catName);
                            column_index = position;
                            tableAdapter.notifyDataSetChanged();

                            mergeDestTableId = masterData.getId();

//                            Log.d("tableNooo", tableNumber + "," + catName);
                        }
                    });

            if (column_index == position) {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#1C60AE"));
                holder.categoryTextView.setTextColor(Color.parseColor("#ffffff"));
                holder.nameTextView.setTextColor(Color.parseColor("#ffffff"));
                holder.tablePersonsTextView.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.categoryTextView.setTextColor(Color.parseColor("#1C60AE"));
                holder.nameTextView.setTextColor(Color.parseColor("#555555"));
                holder.nameTextView.setTextColor(Color.parseColor("#1C60AE"));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView categoryTextView;
            TextView nameTextView;
            TextView tablePersonsTextView;
            RelativeLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.categoryTextView = (TextView) itemView.findViewById(R.id.tableNumberTv);
                this.nameTextView = (TextView) itemView.findViewById(R.id.tableNameTv);
                this.tablePersonsTextView = (TextView) itemView.findViewById(R.id.tablePersonsTextView);
                this.bgLayout = itemView.findViewById(R.id.tableItemBgLayout);

            }

        }

    }

    public class runningTableRecyclerAdapter extends RecyclerView.Adapter<runningTableRecyclerAdapter.ViewHolder> {

        Context activity;
        List<TableResponse.ProductListResponse> data;
        TableResponse.ProductListResponse masterData;
        int column_index1 = 101;

        public runningTableRecyclerAdapter(Activity activity2,
                                           List<TableResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public runningTableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.table_item, viewGroup, false);
            runningTableRecyclerAdapter.ViewHolder viewHolder = new runningTableRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(runningTableRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            holder.categoryTextView.setText(masterData.getTable_name());
            holder.tablePersonsTextView.setText(masterData.table_capacity);

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);
                            catId = masterData.getId();
                            orderNo = masterData.getOrder_id();
                            tableNumber = masterData.getId();
//                            orderTotal = Double.parseDouble(masterData.getOrder_total());
                            String catName = masterData.getTable_name();
                            sessionManager.storeCategoryDetails(catId, catName);
                            column_index1 = position;
                            runningTablesAdapter.notifyDataSetChanged();
                            updateData = "1";

                            mergeSourceTableId = masterData.getId();
                            mergeTableOrderNo = masterData.getOrder_id();
//                            Log.d("orderNooo", mergeTableOrderNo);
                        }
                    });

            if (column_index1 == position) {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#1C60AE"));
                holder.categoryTextView.setTextColor(Color.parseColor("#ffffff"));
                holder.nameTextView.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.bgLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.categoryTextView.setTextColor(Color.parseColor("#1C60AE"));
                holder.nameTextView.setTextColor(Color.parseColor("#555555"));
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView categoryTextView;
            AppCompatTextView tablePersonsTextView;
            TextView nameTextView;
            RelativeLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.categoryTextView = (TextView) itemView.findViewById(R.id.tableNumberTv);
                this.nameTextView = (TextView) itemView.findViewById(R.id.tableNameTv);
                this.bgLayout = itemView.findViewById(R.id.tableItemBgLayout);
                this.tablePersonsTextView = itemView.findViewById(R.id.tablePersonsTextView);

            }

        }

    }

    private void getBillOrderDetails() {

//        showProgressDialog()getBillOrderDetails;

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

//        Log.d("newupdatecart", new Gson().toJson(request));

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("updaterunningorderResp", "response 1112: " + new Gson().toJson(response.body()));
                        orderList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                db.deleteAll();

                                for (int i = 0; i < orderList.size(); i++) {
                                    addOnsOrderList = new ArrayList<>();
                                    addOnsOrderList = user.productResults.get(i).productAddOnsResults;

                                    String item = user.productResults.get(i).item_name;
                                    String item_id = user.productResults.get(i).item_id;
                                    String qty = user.productResults.get(i).sales_qty;
                                    String price = user.productResults.get(i).price_per_unit;
                                    String total = user.productResults.get(i).unit_total_cost;

                                    StringBuilder sb = new StringBuilder();
                                    StringBuilder sb1 = new StringBuilder();
                                    StringBuilder sb2 = new StringBuilder();
                                    StringBuilder sb3 = new StringBuilder();
                                    StringBuilder sb4 = new StringBuilder();
                                    StringBuilder sb5 = new StringBuilder();

                                    db.addCategory(new Product(item_id, item, price, qty));

                                    try {
                                        if (addOnsOrderList.size() > 0) {
                                            for (int j = 0; j < addOnsOrderList.size(); j++) {

                                                sb.append(addOnsOrderList.get(j).addon_name);
                                                sb1.append(addOnsOrderList.get(j).qty);
                                                sb2.append(addOnsOrderList.get(j).price);
                                                sb3.append(addOnsOrderList.get(j).addon_id);
                                                sb4.append(addOnsOrderList.get(j).id);
                                                sb5.append(addOnsOrderList.get(j).kot_number);

//                                                db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));

//                                                Log.d("addonsssss", item_id + "," + addOnsOrderList.get(j).id + "," + addOnsOrderList.get(j).kot_number + "," + "1" + "," + addOnsOrderList.get(j).addon_id + "," + addOnsOrderList.get(j).addon_name + "," + addOnsOrderList.get(j).price + "," + addOnsOrderList.get(j).qty + "," + rowid);
                                                db.addAddOns(new AddOns(item_id, "" + addOnsOrderList.get(j).kot_number, addOnsOrderList.get(j).id, addOnsOrderList.get(j).addon_id, addOnsOrderList.get(j).addon_name, addOnsOrderList.get(j).price, addOnsOrderList.get(j).qty, "" + "1"));
//                                                db.addAddOns(new AddOns(item_id, addOnsOrderList.get(j).addon_id, addOnsOrderList.get(j).addon_name, addOnsOrderList.get(j).price, addOnsOrderList.get(j).qty));

                                            }
                                        }
                                    } catch (NullPointerException e) {

                                    }

                                }

                                selectedOrderNumber = "0";

                                updateList();

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

    private void getUnpaidBillOrderDetails() {

//        showProgressDialog()getBillOrderDetails;

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
//                    orderNo = user.order_number;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("orderResp", "response 1112: " + new Gson().toJson(response.body()));
                        orderList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                db.deleteAll();

                                for (int i = 0; i < orderList.size(); i++) {
                                    addOnsOrderList = new ArrayList<>();
                                    addOnsOrderList = user.productResults.get(i).productAddOnsResults;

                                    String item = user.productResults.get(i).item_name;
                                    String item_id = user.productResults.get(i).item_id;
                                    String qty = user.productResults.get(i).sales_qty;
                                    String price = user.productResults.get(i).price_per_unit;
                                    String total = user.productResults.get(i).unit_total_cost;

                                    StringBuilder sb = new StringBuilder();
                                    StringBuilder sb1 = new StringBuilder();
                                    StringBuilder sb2 = new StringBuilder();
                                    StringBuilder sb3 = new StringBuilder();

                                    db.addCategory(new Product(item_id, item, price, qty));

                                    try {
                                        if (addOnsOrderList.size() > 0) {
                                            for (int j = 0; j < addOnsOrderList.size(); j++) {

                                                sb.append(addOnsOrderList.get(j).addon_name);
                                                sb1.append(addOnsOrderList.get(j).qty);
                                                sb2.append(addOnsOrderList.get(j).price);
                                                sb3.append(addOnsOrderList.get(j).addon_id);

                                                db.addAddOns(new AddOns(item_id, addOnsOrderList.get(j).addon_id, addOnsOrderList.get(j).addon_name, addOnsOrderList.get(j).price, addOnsOrderList.get(j).qty));

                                            }
                                        }
                                    } catch (NullPointerException e) {

                                    }

                                }

                                selectedOrderNumber = "0";

                                updateList();

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

    private void getAllProductDetails() {

        mProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);
        noRecordsTextView.setVisibility(View.GONE);
        ProductsRequest request = new ProductsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        retrofit2.Call<ProductsResponse> call = apiService.allProductList("1234", request);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ProductsResponse> call, Response<ProductsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    ProductsResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        productsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productsList.size() > 0) {
                                    mProgressBar.setVisibility(View.GONE);
                                    updateProductRecyclerview.setVisibility(View.VISIBLE);
                                    noRecordsTextView.setVisibility(View.GONE);
                                    updateProductsRecyclerAdapter = new updateProductsRecyclerAdapter(NewPosMainActivity.this, productsList);
                                    updateProductRecyclerview.setAdapter(updateProductsRecyclerAdapter);
                                    updateProductRecyclerview.setNestedScrollingEnabled(false);
                                    updateProductsRecyclerAdapter.notifyDataSetChanged();
                                } else {
                                    mProgressBar.setVisibility(View.GONE);
                                    updateProductRecyclerview.setVisibility(View.GONE);
                                    noRecordsTextView.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.GONE);
                        noRecordsTextView.setVisibility(View.VISIBLE);
//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getPrintBillDetails() {

        showProgressDialog();
        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNumber);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        showLongSnackBar("data found");

                        orderList = user.productResults;

                        if (orderList.size() > 0) {
                            orderNo = user.order_number;

                            totalAmt = user.total_amount;
                            paidAmt = user.paid_amount;
//                            Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String a = "Copy - Receipt of \n Purchase(Inc Tax)";

                                    ITEM_BILL = "       " + user.company_name + " \n" +
                                            "          " + user.company_address + "\n" +
                                            "                    " + user.city + "\n" +
                                            "                  " + user.state + "\n" +
                                            "                    " + user.postcode + "\n" +
                                            "         " + user.company_website + "\n" +
                                            "                 " + user.phone + "\n" +
                                            "          " + user.email + "\n" +
                                            "-----------------------------------------------\n" +
                                            a + "           " + user.sales_date + "  " + user.time + "\n" +
                                            "Staff                                    " + user.staff + "\n" +
                                            "Device                                   " + user.device + "\n" +
                                            "-----------------------------------------------\n";

                                    ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s %3$8s %4$6s %5$6s", "#", "PRODUCT              ", "PRICE", "QTY", "TOTAL");
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
                                            item = item.substring(0, 21) + "..";
                                        }

                                        ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s %4$6s %5$6s", "" + (i + 1), item, price, qty, total);

                                    }
                                    ITEM_BILL = ITEM_BILL
                                            + "\n-----------------------------------------------\n";
                                    ITEM_BILL = ITEM_BILL
                                            + "Sub Total                                " + user.total_amount + "\n"
                                            + "Total                                    " + user.total_amount + "\n" +
                                            "-----------------------------------------------\n"
                                            + "Payment by Tender                        Amount \n"
                                            + user.payment_type + "                                     " + user.total_amount + " \n" +
                                            "-----------------------------------------------\n"
                                            + "Payment by Tender       Percentage       Amount \n"
                                            + user.tax_rate + "                     " + user.tax_percentage + "              " + user.total_amount + "\n" +
                                            "               ABN :  " + user.abn + "\n" +
                                            "-----------------------------------------------\n" +
                                            "         " + user.reg_number + ", Account : " + user.account_number + "\n" +
                                            "                PH " + user.phone + "\n" +
                                            "       Thank you for shopping with us";

//                                    Log.d("jfdhj", ITEM_BILL);

                                    printFooter();

                                }
                            });
                        } else {
                            hideProgressDialog();
                            infoPopup("No Data found");
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

    private void getProductDetails() {

        mergeTablesLayoutLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);
        noRecordsTextView.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);
        placeKotLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);
        addMiscProductLayout.setVisibility(View.GONE);
        categoriesLayout.setVisibility(View.VISIBLE);
        ProductsRequest request = new ProductsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setCategory_id(catId);
        request.setPrice_id(priceId);
        Log.d("prodReq", "response: " + new Gson().toJson(request));
        retrofit2.Call<ProductsResponse> call = apiService.productList("1234", request);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ProductsResponse> call, Response<ProductsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    ProductsResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        runningOrdersHeaderLayout.setVisibility(View.GONE);
                        categoryListRecyclerView.setVisibility(View.VISIBLE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        subCategoryListRecyclerView.setVisibility(View.GONE);
                        subCategoryAdapter = new homeSubCatRecyclerAdapter(NewPosMainActivity.this, categoryList);
                        subCategoryListRecyclerView.setAdapter(subCategoryAdapter);
                        subCategoryAdapter.notifyDataSetChanged();
                        backTextView.setVisibility(View.GONE);

//                        Log.d("prodResp", "response 111211: " + new Gson().toJson(response.body()));
                        productsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productsList.size() > 0) {
                                    mProgressBar.setVisibility(View.GONE);
                                    categoriesLayout.setVisibility(View.VISIBLE);
                                    noRecordsTextView.setVisibility(View.GONE);
//                                    AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(NewPosMainActivity.this, 250);
                                    GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 10);
                                    productsRecyclerView.setLayoutManager(mLayoutManager);
                                    LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, productsList);
                                    productsRecyclerView.setAdapter(LoansListAdapter);
                                    productsRecyclerView.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                } else {
                                    mProgressBar.setVisibility(View.GONE);
                                    productsRecyclerView.setVisibility(View.GONE);
                                    noRecordsTextView.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.GONE);
                        noRecordsTextView.setVisibility(View.VISIBLE);
                    }

                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getSearchProductDetails() {

        ProductsRequest request = new ProductsRequest();
        request.setSearch_text(searchWord);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<ProductsResponse> call = apiService.searchProductList("1234", request);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ProductsResponse> call, Response<ProductsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    ProductsResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        productsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, productsList);
                                productsRecyclerView.setAdapter(LoansListAdapter);
                                productsRecyclerView.setNestedScrollingEnabled(false);
                                LoansListAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getRunningOrderDetails() {

        mergeTablesLayoutLayout.setVisibility(View.GONE);
        placeKotLayout.setVisibility(View.GONE);
        paymentLayout.setVisibility(View.GONE);
        addMiscProductLayout.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.GONE);
        customerDetailsLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);
        categoryListRecyclerView.setVisibility(View.VISIBLE);
        categoriesLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);

        OrdersRequest request = new OrdersRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

//        Log.d("runningOrdersRequest", "response 111: " + new Gson().toJson(request));

        retrofit2.Call<RunningOrdersResponse> call = apiService.runningOrdersList("1234", request);

        call.enqueue(new Callback<RunningOrdersResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RunningOrdersResponse> call, Response<RunningOrdersResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    mProgressBar.setVisibility(View.GONE);

                    RunningOrdersResponse user = response.body();
//                    Log.d("runningOrdersResp", "response 1115: " + new Gson().toJson(response.body()));
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        noDataTextView.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        categoriesLayout.setVisibility(View.VISIBLE);
//                        Log.d("runningOrdersResp", "response 1112: " + new Gson().toJson(response.body()));
                        runningOrdersList = user.productResults;
                        if (user.productResults != null && user.productResults.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
                                    GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
                                    productsRecyclerView.setLayoutManager(layoutManager);
                                    noRecordsTextView.setVisibility(View.GONE);

//                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
//                                    cartRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
                                    runningOrdersHeaderLayout.setVisibility(View.VISIBLE);
                                    RunningOrdersRecyclerAdapter LoansListAdapter = new RunningOrdersRecyclerAdapter(NewPosMainActivity.this, runningOrdersList);
                                    productsRecyclerView.setAdapter(LoansListAdapter);
                                    productsRecyclerView.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            productsRecyclerView.setVisibility(View.GONE);
                            noDataTextView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        productsRecyclerView.setVisibility(View.GONE);
//                        noDataTextView.setVisibility(View.VISIBLE);
                        showLongSnackBar(message);
                    }

                } else {
                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<RunningOrdersResponse> call, Throwable t) {
                hideProgressDialog();
                mProgressBar.setVisibility(View.GONE);
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getReservationDetails() {

        placeKotLayout.setVisibility(View.GONE);
        paymentLayout.setVisibility(View.GONE);
        addMiscProductLayout.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);
        addCustomerDataLayout.setVisibility(View.GONE);
        tablesLayout.setVisibility(View.GONE);
        categoryListRecyclerView.setVisibility(View.VISIBLE);
        categoriesLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        customerDetailsLayout.setVisibility(View.GONE);

        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

//        Log.d("runningOrdersRequest", "response 111: " + new Gson().toJson(request));

        retrofit2.Call<ReservationResponse> call = apiService.getReservationList("1234", request);

        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ReservationResponse> call, Response<ReservationResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    mProgressBar.setVisibility(View.GONE);

                    ReservationResponse user = response.body();
//                    Log.d("reservationResp", "response 1115: " + new Gson().toJson(response.body()));
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        noDataTextView.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        categoriesLayout.setVisibility(View.VISIBLE);
//                        Log.d("runningOrdersResp", "response 1112: " + new Gson().toJson(response.body()));
                        reservationsList = user.reservationListResponses;
                        if (user.reservationListResponses != null && user.reservationListResponses.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    GridLayoutManager layoutManager = new GridLayoutManager(NewPosMainActivity.this, 4);
                                    GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
                                    productsRecyclerView.setLayoutManager(layoutManager);
                                    ReservationRecyclerAdapter LoansListAdapter = new ReservationRecyclerAdapter(NewPosMainActivity.this, reservationsList);
                                    productsRecyclerView.setAdapter(LoansListAdapter);
                                    productsRecyclerView.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            productsRecyclerView.setVisibility(View.GONE);
//                            noDataTextView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        productsRecyclerView.setVisibility(View.GONE);
//                        noDataTextView.setVisibility(View.VISIBLE);
                        showLongSnackBar(message);
                    }

                } else {
                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                hideProgressDialog();
                mProgressBar.setVisibility(View.GONE);
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getAreaDetails() {

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TableLayoutResponse> call = apiService.getAreaTablesData("1234", request);

        call.enqueue(new Callback<TableLayoutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TableLayoutResponse> call, Response<TableLayoutResponse> response) {

                if (response.isSuccessful()) {
                    areaProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    TableLayoutResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        noDataTextView.setVisibility(View.GONE);
                        areaRecyclerView.setVisibility(View.VISIBLE);

//                        Log.d("TableLayoutResp", "response 112: " + new Gson().toJson(response.body()));
                        areasList = user.areaResults;

                        layout_id = areasList.get(0).id;
//                        Log.d("jdfjkds", layout_id);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                areasRecyclerAdapter = new AreaTableLayoutRecyclerAdapter(NewPosMainActivity.this, areasList);
                                areaRecyclerView.setAdapter(areasRecyclerAdapter);
                                areaRecyclerView.setNestedScrollingEnabled(false);

                                getAreaWiseTablesDetails();

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
            public void onFailure(Call<TableLayoutResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getAreaWiseTablesDetails() {

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setLayout_id(layout_id);

//        Log.d("layouttt", new Gson().toJson(request));

        retrofit2.Call<TableListResponse> call = apiService.getAreaWiseTablesData("1234", request);

        call.enqueue(new Callback<TableListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TableListResponse> call, Response<TableListResponse> response) {

                if (response.isSuccessful()) {
                    areaProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    TableListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        noDataTextView.setVisibility(View.GONE);
                        areaTablesRecyclerView.setVisibility(View.VISIBLE);

//                        Log.d("TableLayoutResp", "response 1112: " + new Gson().toJson(response.body()));
                        areaTablesList = user.areaResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                areaTablesRecyclerAdapter = new AreaWiseTablesRecyclerAdapter(NewPosMainActivity.this, areaTablesList);
                                areaTablesRecyclerView.setAdapter(areaTablesRecyclerAdapter);
                                areaTablesRecyclerView.setNestedScrollingEnabled(false);
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
            public void onFailure(Call<TableListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getOrdersCountDetails() {

//        showProgressDialog();
        CouponsRequest request = new CouponsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        retrofit2.Call<OrdersCountResponse> call = apiService.getOnlineOrdersCountResponse("1234", request);

        call.enqueue(new Callback<OrdersCountResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OrdersCountResponse> call, Response<OrdersCountResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final OrdersCountResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tableTopOrdersCountTextView.setText(user.instore);
                                takeAwayOrdersCountTextView.setText(user.pickup);
                                deliveryOrdersCountTextView.setText(user.online);
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
            public void onFailure(Call<OrdersCountResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    private void getOrderDetails(String orderType) {

        OrdersRequest request = new OrdersRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        if (orderType.equalsIgnoreCase("1")) {
            request.setOrder_type("1");
        } else if (orderType.equalsIgnoreCase("2")) {
            request.setOrder_type("2");
        } else if (orderType.equalsIgnoreCase("3")) {
            request.setOrder_type("3");
        }

//        Log.d("ordersRequest", request + new Gson().toJson(request));
        retrofit2.Call<OrdersResponse> call = apiService.orderDetails("1234", request);

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OrdersResponse> call, Response<OrdersResponse> response) {

                if (response.isSuccessful()) {

                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    OrdersResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        noDataTextView.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        tablesLayout.setVisibility(View.GONE);
                        categoryListRecyclerView.setVisibility(View.VISIBLE);
                        categoriesLayout.setVisibility(View.VISIBLE);
                        runningOrdersHeaderLayout.setVisibility(View.GONE);
                        customerDetailsLayout.setVisibility(View.GONE);

//                        Log.d("ordersResp", "response 1112: " + new Gson().toJson(response.body()));
                        ordersList = user.ordesListResponses;

                        if (user.ordesListResponses != null && user.ordesListResponses.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    GridLayoutManager layoutManager = new GridLayoutManager(NewPosMainActivity.this, 4);
                                    GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
                                    productsRecyclerView.setLayoutManager(layoutManager);

                                    OrdersRecyclerAdapter LoansListAdapter = new OrdersRecyclerAdapter(NewPosMainActivity.this, ordersList);
                                    productsRecyclerView.setAdapter(LoansListAdapter);
                                    productsRecyclerView.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
//                            noDataTextView.setVisibility(View.VISIBLE);
                            productsRecyclerView.setVisibility(View.GONE);
                        }

                    } else {
//                        noDataTextView.setVisibility(View.VISIBLE);
                        productsRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    private void getCustomerDetails() {

        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<CustomerResponse> call = apiService.customerList("1234", request);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CustomerResponse> call, Response<CustomerResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CustomerResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {
                        customerNameList.clear();
                        customerIdList.clear();

//                        Log.d("cateResp", "response 12: " + new Gson().toJson(response.body()));
                        customerList = user.productResults;
                        for (int i = 0; i < customerList.size(); i++) {
                            customerNameList.add(customerList.get(i).customer_name);
                            customerIdList.add(customerList.get(i).id);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spinnerData();
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
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public void spinnerData() {

        hideKeyBoard();

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, customerNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);

        customerSpinner.setAdapter(aa);

//        customerSpinner.setSelection(aa.getPosition(String.valueOf(customerName)));

        customerSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                return false;
            }
        });

        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerName = parent.getItemAtPosition(position).toString();
                customerId = customerIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });

    }

    public void categorySpinnerData() {

        hideKeyBoard();

        spinnerDialog = new SpinnerDialog(NewPosMainActivity.this, items, "Select or Search City", "Close Button Text");// With No Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default


        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Toast.makeText(NewPosMainActivity.this, item + "  " + position + "", Toast.LENGTH_SHORT).show();
//                selectedItems.setText(item + " Position: " + position);
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, items);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);

        categorySpinner.setAdapter(aa);

//        customerSpinner.setSelection(aa.getPosition(String.valueOf(customerName)));

        categorySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
//                spinnerDialog.showSpinerDialog();
                return false;
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerName = parent.getItemAtPosition(position).toString();
                catId = catIdList.get(position);
                getProductDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });

    }

    public void progressPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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

    }

    private void getSquareUpResponse() {

        showProgressDialog();
        SquareUpPaymentRequest request = new SquareUpPaymentRequest();
        request.setOrder_id(orderNo);
        request.setAmount("" + totalAmount);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
//        Log.d("reqqqqqq", new Gson().toJson(request));
        retrofit2.Call<SquareUpPaymentResponse> call = apiService.squareupPaymentData("1234", request);

        call.enqueue(new Callback<SquareUpPaymentResponse>() {
            @Override
            public void onResponse(retrofit2.Call<SquareUpPaymentResponse> call, Response<SquareUpPaymentResponse> response) {

                if (response.isSuccessful()) {

//                    Log.d("responseee", new Gson().toJson(response));
                    hideProgressDialog();
                    SquareUpPaymentResponse user = response.body();

                    String message = user.getMessage();
                    String status = user.getStatus();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("SUCCESS")) {

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

        call.enqueue(new Callback<TermicalCheckoutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TermicalCheckoutResponse> call, Response<TermicalCheckoutResponse> response) {

                if (response.isSuccessful()) {
//                    Log.d("responseee989", new Gson().toJson(response));
//                    hideProgressDialog();
                    final TermicalCheckoutResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();
                    String status = user.getStatus();
                    if (status.equalsIgnoreCase("0")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String paymentStatus = user.getPayment_status();
//                                Log.d("dasdagd", paymentStatus);

                                if (paymentStatus.equalsIgnoreCase("COMPLETED")) {
                                    db.deleteAll();
//                                    String invoiceId = user1.get(SessionManager.KEY_HOLD_ID);
//                                    deleteHoldInvoiceAfterPayment(invoiceId);
//                                    sessionManager.storeLastHoldInvoice("0");
                                    selectedDiscount = 0.00;
                                    updateList();
                                    checkoutId = "1234";
                                    progressDialog.dismiss();
//                                    getPrintBillDetails();
//                                    printConfirmationPopup();
                                    succsessfailerpopup("success", totalAmount);

                                } else if (paymentStatus.equalsIgnoreCase("PENDING")) {

                                } else if (paymentStatus.equalsIgnoreCase("IN_PROGRESS")) {

                                } else if (paymentStatus.equalsIgnoreCase("CANCELED")) {
                                    progressDialog.dismiss();
                                    checkoutId = "1234";
                                    succsessfailerpopup("Failure", totalAmount);
                                }

                            }
                        });

                    } else {
                        checkoutId = "1234";
                        progressDialog.dismiss();
                        succsessfailerpopup("Failure", totalAmount);
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

    public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder> {

        Context activity;

        List<ReservationResponse.ReservationsListResponse> data2;
        ReservationResponse.ReservationsListResponse masterData2;

        public ReservationRecyclerAdapter(Activity activity2,
                                          List<ReservationResponse.ReservationsListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ReservationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_reservation_list_item, viewGroup, false);
            ReservationRecyclerAdapter.ViewHolder viewHolder = new ReservationRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ReservationRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            final ViewHolder finalHolder = holder;

            finalHolder.orderIdTextView.setText(masterData2.mobile);
            finalHolder.amountTextView.setText(masterData2.number_of_people);
            finalHolder.cNameTextView.setText(masterData2.name);
            finalHolder.dateTextView.setText(masterData2.reserve_date);
            finalHolder.timeTextView.setText(masterData2.reserve_time);
//            if (!masterData2.message.isEmpty())
//                holder.messageTextView.setText(masterData2.message);
//            else
//                holder.messageTextView.setVisibility(View.INVISIBLE);

            if (masterData2.booking_status.equalsIgnoreCase("2")) {
                finalHolder.billNowButton.setVisibility(View.GONE);
                finalHolder.ViewButton.setVisibility(View.GONE);
                finalHolder.ViewButton.setTextColor(getResources().getColor(R.color.redColor));
                finalHolder.ViewButton.setBackgroundDrawable(null);
                finalHolder.orderTypeHeaderLayout.setBackgroundResource(R.color.redColor);
                finalHolder.orderTypeHeaderLayout.setText("Rejected");

            } else if (masterData2.booking_status.equalsIgnoreCase("3")) {
                finalHolder.billNowButton.setVisibility(View.GONE);
                finalHolder.ViewButton.setVisibility(View.GONE);
                finalHolder.billNowButton.setTextColor(getResources().getColor(R.color.green));
                finalHolder.billNowButton.setBackgroundDrawable(null);
                finalHolder.orderTypeHeaderLayout.setBackgroundResource(R.color.green);
                finalHolder.orderTypeHeaderLayout.setText("Reserved");
            }


            finalHolder.billNowButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            orderNo = masterData2.getId();
                            sendReservationStatus(orderNo, "3");
                        }
                    });

            finalHolder.ViewButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            orderNo = masterData2.getId();
                            sendReservationStatus(orderNo, "2");
                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;
            AppCompatTextView amountTextView;
            AppCompatTextView dateTextView;
            AppCompatTextView orderTypeHeaderLayout;
            AppCompatTextView cNameTextView;
            AppCompatTextView timeTextView;
            AppCompatTextView messageTextView;
            AppCompatButton billNowButton;
            AppCompatButton ViewButton;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.amountTextView = itemView.findViewById(R.id.amountTextView);
                this.dateTextView = itemView.findViewById(R.id.dateTextView);
                this.billNowButton = itemView.findViewById(R.id.billNowButton);
                this.ViewButton = itemView.findViewById(R.id.ViewButton);
                this.orderTypeHeaderLayout = itemView.findViewById(R.id.orderTypeHeaderLayout);
                this.cNameTextView = itemView.findViewById(R.id.cNameTextView);
                this.timeTextView = itemView.findViewById(R.id.timeTextView);
                this.messageTextView = itemView.findViewById(R.id.messageTextView);

            }

        }

    }

    private void sendReservationStatus(String orderNo, String status) {

        ReservationStatusRequest request = new ReservationStatusRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setId(orderNo);
        request.setStatus(status);

        showProgressDialog();
//        Log.d("printstatus", new Gson().toJson(request));

        Call<ReservationStatusResponse> call = apiService.changeReservationStatus("1234", request);

        call.enqueue(new Callback<ReservationStatusResponse>() {
            @Override
            public void onResponse(Call<ReservationStatusResponse> call, Response<ReservationStatusResponse> response) {

//                Log.d("printstatus", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    ReservationStatusResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                getReservationDetails();

                            }
                        });
                    } else {

//                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ReservationStatusResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public class RunningOrdersRecyclerAdapter extends RecyclerView.Adapter<RunningOrdersRecyclerAdapter.ViewHolder> {

        Context activity;

        List<RunningOrdersResponse.ProductListResponse> data2;
        RunningOrdersResponse.ProductListResponse masterData2;
        Boolean selected;
        int row_index;

        public RunningOrdersRecyclerAdapter(Activity activity2,
                                            List<RunningOrdersResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.running_orders_main_listitem, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.runningOrdersOrderNoTv.setText(masterData2.getId());
            holder.runningOrdersTotalTv.setText("$" + masterData2.getGrand_total());
            holder.runningOrdersCustomerNameTv.setText(masterData2.getDel_customer_name());
            holder.runningOrdersCustomerMobileTv.setText(masterData2.getTable_number());
            holder.paymentStatusTv.setText(masterData2.getPayment_status());
            holder.runningOrdersTimeTv.setText(masterData2.time);

            if (masterData2.getPayment_status().equalsIgnoreCase("Unpaid")) {
                holder.actionTv.setText("Bill");
                holder.paymentStatusTv.setTextColor(getResources().getColor(R.color.redColor));
            } else if (masterData2.getPayment_status().equalsIgnoreCase("Paid")) {
                holder.viewActionTv.setText("View");
                holder.paymentStatusTv.setTextColor(getResources().getColor(R.color.green));
            } else {
                holder.actionTv.setText("Print");
                holder.paymentStatusTv.setTextColor(getResources().getColor(R.color.green));
            }

            if (masterData2.getOrder_type().equalsIgnoreCase("1")) {
                holder.runningOrdersTypeTv.setText("Take Away");
                holder.runningOrdersTypeTv.setTextColor(getResources().getColor(R.color.takeawayHeader));
                holder.tableHeaderLayout.setBackgroundResource(R.color.takeawayBg);

            } else if (masterData2.getOrder_type().equalsIgnoreCase("2")) {
                holder.runningOrdersTypeTv.setText("Delivery");
                holder.runningOrdersTypeTv.setTextColor(getResources().getColor(R.color.deliveryHeader));
                holder.tableHeaderLayout.setBackgroundResource(R.color.deliveryBg);

            } else {
                holder.runningOrdersTypeTv.setText("Dine In");
                holder.runningOrdersTypeTv.setTextColor(getResources().getColor(R.color.dineinHeader));
                holder.tableHeaderLayout.setBackgroundResource(R.color.dineinBg);
            }

//            if (masterData2.getId().equalsIgnoreCase(selectedRunningOrderNumber)) {
//                Log.d("hjkgjk", selectedRunningOrderNumber + "");
//                holder.tableHeaderLayout.setBackgroundResource(R.color.colorBlack);
//                holder.runningOrdersOrderNoTv.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersTimeTv.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersCustomerNameTv.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersCustomerMobileTv.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersTotalTv.setTextColor(getResources().getColor(R.color.colorWhite));
//            }

            holder.actionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    masterData2 = data2.get(position);
                    selectedDiscount = 0.00;

                    masterData2 = data2.get(position);
                    orderNo = masterData2.getId();
                    orderNumber = masterData2.getOrder_number();


                    Double totalAmount = Double.parseDouble(masterData2.getGrand_total());
                    customerName = masterData2.getDel_customer_name();
                    mobileNo = masterData2.getDel_customer_mobile();
                    orderNumber = masterData2.getOrder_number();
//                    Log.d("kjfkl", orderNo);

                    if (masterData2.getPayment_status().equalsIgnoreCase("Unpaid")) {
                        Intent intent = new Intent(getApplicationContext(), NewPaymentActivity.class);
                        intent.putExtra("total", totalAmount);
                        intent.putExtra("totalPayable", totalAmount);
                        intent.putExtra("qty", products.size());
                        intent.putExtra("discount", disc);
                        intent.putExtra("orderId", orderNo);
                        intent.putExtra("updateData", updateData);
                        intent.putExtra("priceId", priceId);
//                        Log.d("settingddd", totalAmount + "," + orderNo + "," + priceId + "," + updateData);
                        startActivity(intent);

//                        getBillOrderDetails();
                    } else {
                        getBillDetails();
                    }

//                            runningOrdersDialog.dismiss();
                }
            });

            holder.viewActionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    masterData2 = data2.get(position);
                    orderNo = masterData2.getId();
                    orderNumber = masterData2.getOrder_number();

                    String totalAmount = masterData2.getGrand_total();
                    customerName = masterData2.getDel_customer_name();
                    mobileNo = masterData2.getDel_customer_mobile();
                    orderNumber = masterData2.getOrder_number();

                    if (holder.viewActionTv.getText().toString().equalsIgnoreCase("View")) {
                        OrderDetailsPopup(orderNo);
                    } else {
//                        Intent intent = new Intent(getApplicationContext(), NewPaymentActivity.class);
//                        intent.putExtra("total", db.getTotalPrice());
//                        intent.putExtra("totalPayable", totalAmount);
//                        intent.putExtra("qty", products.size());
//                        intent.putExtra("discount", disc);
//                        intent.putExtra("orderId", orderNo);
//                        intent.putExtra("updateData", updateData);
//                        intent.putExtra("priceId", priceId);
//                        intent.putExtra("customerName", customerName);
//                        intent.putExtra("mobileNo", mobileNo);
//                        intent.putExtra("tableNo", tableNo);
//                        intent.putExtra("oredrType", orderType);
//                        startActivity(intent);
                        OrderDetailsPopup(orderNo);
//                        getBillOrderDetails();
                    }
//                    Log.d("kjfkl", orderNo);


                }
            });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView runningOrdersOrderNoTv;
            AppCompatTextView runningOrdersTypeTv;
            AppCompatTextView runningOrdersTotalTv;
            AppCompatTextView runningOrdersCustomerNameTv;
            AppCompatTextView runningOrdersCustomerMobileTv;
            AppCompatTextView runningOrdersTimeTv;
            AppCompatTextView paymentStatusTv;
            AppCompatTextView actionTv;
            AppCompatTextView viewActionTv;

            LinearLayout tableHeaderLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.runningOrdersOrderNoTv = itemView.findViewById(R.id.runningOrdersOrderNoTv);
                this.runningOrdersTypeTv = itemView.findViewById(R.id.runningOrdersTypeTv);
                this.runningOrdersTotalTv = itemView.findViewById(R.id.runningOrdersTotalTv);
                this.runningOrdersCustomerNameTv = itemView.findViewById(R.id.runningOrdersCustomerNameTv);
                this.runningOrdersCustomerMobileTv = itemView.findViewById(R.id.runningOrdersCustomerMobileTv);
                this.runningOrdersTimeTv = itemView.findViewById(R.id.runningOrdersTimeTv);
                this.paymentStatusTv = itemView.findViewById(R.id.paymentStatusTv);
                this.tableHeaderLayout = itemView.findViewById(R.id.tableHeaderLayout);
                this.actionTv = itemView.findViewById(R.id.actionTv);
                this.viewActionTv = itemView.findViewById(R.id.viewActionTv);

            }

        }

    }


//    public class RunningOrdersRecyclerAdapter extends RecyclerView.Adapter<RunningOrdersRecyclerAdapter.ViewHolder> {
//
//        Context activity;
//
//        List<RunningOrdersResponse.ProductListResponse> data2;
//        RunningOrdersResponse.ProductListResponse masterData2;
//        Boolean selected;
//        int row_index;
//
//        public RunningOrdersRecyclerAdapter(Activity activity2,
//                                            List<RunningOrdersResponse.ProductListResponse> masterList) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data2 = masterList;
//        }
//
//        @Override
//        public RunningOrdersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.running_order_list_item, viewGroup, false);
//            RunningOrdersRecyclerAdapter.ViewHolder viewHolder = new RunningOrdersRecyclerAdapter.ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final RunningOrdersRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData2 = data2.get(position);
//
//            holder.orderIdTextView.setText(masterData2.getId());
//            holder.amountTextView.setText("$" + masterData2.getGrand_total());
//            holder.tableNumberTextView.setText(masterData2.getTable_number());
//
//            if (masterData2.getOrder_type().equalsIgnoreCase("1")) {
//                holder.billNowButton.setBackgroundResource(R.color.takeawayHeader);
//                holder.orderTypeHeaderLayout.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersBgLayout.setBackgroundResource(R.color.takeawayBg);
//                holder.orderTypeHeaderLayout.setBackgroundResource(R.color.takeawayHeader);
//                holder.orderTypeHeaderLayout.setText("Take Away");
//            } else if (masterData2.getOrder_type().equalsIgnoreCase("2")) {
//                holder.billNowButton.setBackgroundResource(R.color.deliveryHeader);
//                holder.orderTypeHeaderLayout.setBackgroundResource(R.color.deliveryHeader);
//                holder.orderTypeHeaderLayout.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersBgLayout.setBackgroundResource(R.color.deliveryBg);
//                holder.orderTypeHeaderLayout.setText("Delivery");
//            } else {
//                holder.billNowButton.setBackgroundResource(R.color.dineinHeader);
//                holder.orderTypeHeaderLayout.setBackgroundResource(R.color.dineinHeader);
//                holder.orderTypeHeaderLayout.setTextColor(getResources().getColor(R.color.colorWhite));
//                holder.runningOrdersBgLayout.setBackgroundResource(R.color.dineinBg);
//                holder.orderTypeHeaderLayout.setText("Dine-in");
//            }
//
//            holder.billNowButton.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            masterData2 = data2.get(position);
//                            orderNo = masterData2.getId();
//                            orderNumber = masterData2.getOrder_number();
//                            Log.d("kjfkl", orderNo);
//
//                            getBillOrderDetails();
////                            runningOrdersDialog.dismiss();
//                        }
//                    });
//
//            holder.updateButton.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            masterData2 = data2.get(position);
//                            orderNo = masterData2.getId();
//                            orderNumber = masterData2.getOrder_number();
//                            Log.d("kjfkl", orderNo);
//
//                            getBillOrderDetails();
////                            runningOrdersDialog.dismiss();
//                        }
//                    });
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
//            AppCompatTextView amountTextView;
//            AppCompatTextView tableNumberTextView;
//            AppCompatTextView orderTypeHeaderLayout;
//            AppCompatButton billNowButton;
//            AppCompatButton updateButton;
//            Button cashButton;
//            Button cardButton;
//            RelativeLayout runningOrdersLayout;
//            RelativeLayout runningOrdersBgLayout;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                this.orderIdTextView = itemView.findViewById(R.id.orderTv);
//                this.amountTextView = itemView.findViewById(R.id.amountTv);
//                this.tableNumberTextView = itemView.findViewById(R.id.tableTv);
//                this.billNowButton = itemView.findViewById(R.id.runningOrdersBillButton);
//                this.updateButton = itemView.findViewById(R.id.runningOrdersUpdateButton);
//                this.runningOrdersLayout = itemView.findViewById(R.id.runningOrdersLayout);
//                this.orderTypeHeaderLayout = itemView.findViewById(R.id.orderTypeHeaderLayout);
//                this.runningOrdersBgLayout = itemView.findViewById(R.id.runningOrdersBgLayout);
////                this.cardButton = itemView.findViewById(R.id.runningOrdersCardButton);
////                this.cashButton = itemView.findViewById(R.id.runningOrdersCashButton);
//
//            }
//
//        }
//
//    }

    List<TableLayoutResponse.AreaListResponse.TablesListResponse> tablesData;

    public class AreaTableLayoutRecyclerAdapter extends RecyclerView.Adapter<AreaTableLayoutRecyclerAdapter.ViewHolder> {

        Context activity;

        List<TableLayoutResponse.AreaListResponse> data2;
        TableLayoutResponse.AreaListResponse masterData2;

        Boolean selected;
        int row_index = 0;

        public AreaTableLayoutRecyclerAdapter(Activity activity2,
                                              List<TableLayoutResponse.AreaListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public AreaTableLayoutRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_table_layout_area_item, viewGroup, false);
            AreaTableLayoutRecyclerAdapter.ViewHolder viewHolder = new AreaTableLayoutRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AreaTableLayoutRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.areaNameButton.setText(masterData2.getArea());

//            if (position == 0) {
//                roomOneLayout.setVisibility(View.VISIBLE);
//                roomTwoLayout.setVisibility(View.GONE);
//                roomThreeLayout.setVisibility(View.GONE);
//            }

            holder.areaNameButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            tablesData = masterData2.tableLayoutResults;
                            layout_id = masterData2.getId();
//                            Log.d("werwe", layout_id);
                            getAreaWiseTablesDetails();
                            row_index = position;
//                            Log.d("kjlklj", position + "");
                            areasRecyclerAdapter.notifyDataSetChanged();

//                            if (layout_id.equalsIgnoreCase("2")) {
//                                roomOneLayout.setVisibility(View.GONE);
//                                roomTwoLayout.setVisibility(View.VISIBLE);
//                                roomThreeLayout.setVisibility(View.GONE);
//                            } else if (layout_id.equalsIgnoreCase("3")) {
//                                roomOneLayout.setVisibility(View.GONE);
//                                roomTwoLayout.setVisibility(View.GONE);
//                                roomThreeLayout.setVisibility(View.VISIBLE);
//                            }

                        }
                    });

            if (row_index == position) {
                holder.areaNameButton.setBackgroundResource(R.drawable.rectangle_orange_bg);
                holder.areaNameButton.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.areaNameButton.setBackgroundResource(R.drawable.rectangle_white_bg_filled);
                holder.areaNameButton.setTextColor(Color.parseColor("#1C60AE"));
            }

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatButton areaNameButton;
            RelativeLayout tableBgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.areaNameButton = itemView.findViewById(R.id.areaNameButton);
                this.tableBgLayout = itemView.findViewById(R.id.tableBgLayout);

            }

        }

    }

    public class AreaWiseTablesRecyclerAdapter extends RecyclerView.Adapter<AreaWiseTablesRecyclerAdapter.ViewHolder> {

        Context activity;

        List<TableListResponse.AreaListResponse> data2;
        TableListResponse.AreaListResponse masterData2;
        Boolean selected;
        int row_index;

        public AreaWiseTablesRecyclerAdapter(Activity activity2,
                                             List<TableListResponse.AreaListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public AreaWiseTablesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_table_layout_table_item, viewGroup, false);
            AreaWiseTablesRecyclerAdapter.ViewHolder viewHolder = new AreaWiseTablesRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AreaWiseTablesRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.tableNameTextView.setText("Table");
            holder.tableNumberTextView.setText(masterData2.getTable_name());
            holder.tablePersonsTextView.setText(masterData2.getTable_capacity());
            holder.tableChairsTextView.setText(masterData2.getChair());

            holder.tableNumbesTextView.setText(masterData2.getTable_name());
            holder.tableChairs1TextView.setText(masterData2.getTable_name());

            if (masterData2.getTable_booked().equalsIgnoreCase("0")) {
                holder.tablesLayout.setBackgroundResource(R.drawable.rectangle_ash_fill_bg);
            } else {
                holder.tablesLayout.setBackgroundResource(R.drawable.rectangle_table_booked_bg);
            }

            if (masterData2.getChair().equalsIgnoreCase("2")) {
                Glide.with(activity).load(R.mipmap.empty_two_table)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.tableWithChairsIv);
            } else if (masterData2.getChair().equalsIgnoreCase("4")) {
                Glide.with(activity).load(R.mipmap.empty_four_table)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.tableWithChairsIv);
            } else if (masterData2.getChair().equalsIgnoreCase("6")) {
                Glide.with(activity).load(R.mipmap.empty_six_chairs)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.tableWithChairsIv);
            }

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView tableNameTextView;
            AppCompatTextView tableNumberTextView;
            AppCompatTextView tablePersonsTextView;
            AppCompatTextView tableChairsTextView;
            AppCompatTextView tableChairs1TextView;
            AppCompatTextView tableNumbesTextView;
            AppCompatImageView tableWithChairsIv;
            RelativeLayout tablesLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.tableNameTextView = itemView.findViewById(R.id.tableNameTextView);
                this.tableNumberTextView = itemView.findViewById(R.id.tableNumber1TextView);
                this.tablePersonsTextView = itemView.findViewById(R.id.tablePersonsTextView);
                this.tableChairsTextView = itemView.findViewById(R.id.tableChairsTextView);
                this.tableNumbesTextView = itemView.findViewById(R.id.tableNumbesTextView);
                this.tableChairs1TextView = itemView.findViewById(R.id.tableChairs1TextView);
                this.tableWithChairsIv = itemView.findViewById(R.id.tableWithChairsIv);

                this.tablesLayout = itemView.findViewById(R.id.tableItemLayout);

            }

        }

    }

    public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> {

        Context activity;

        List<OrdersResponse.OrdesListResponse> data2;
        OrdersResponse.OrdesListResponse masterData2;
        Boolean selected;
        int row_index;

        public OrdersRecyclerAdapter(Activity activity2,
                                     List<OrdersResponse.OrdesListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public OrdersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_orders_list_item, viewGroup, false);
            OrdersRecyclerAdapter.ViewHolder viewHolder = new OrdersRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final OrdersRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText(masterData2.getId());
            holder.amountTextView.setText("$" + masterData2.getGrand_total());

            if (masterData2.payment_status != null) {
                if (masterData2.payment_status.equalsIgnoreCase("Unpaid")) {
                    holder.dateTextView.setText(masterData2.payment_status);
                    holder.dateTextView.setTextColor(getResources().getColor(R.color.redColor));
                    holder.billNowButton.setText("Bill");
                } else {
                    holder.dateTextView.setText(masterData2.payment_status);
                    holder.dateTextView.setTextColor(getResources().getColor(R.color.green));
                    holder.billNowButton.setText("Print");
                }
            } else {
                holder.dateTextView.setText("Unpaid");
                holder.dateTextView.setTextColor(getResources().getColor(R.color.redColor));
                holder.billNowButton.setText("Bill");
            }

            holder.cNameTextView.setText(masterData2.customer_name);
            holder.timeTextView.setText(masterData2.created_time);

            if (masterData2.order_type.equalsIgnoreCase("1")) {
                holder.orderTypeHeaderLayout.setText("Take Away");
            } else if (masterData2.order_type.equalsIgnoreCase("2")) {
                holder.orderTypeHeaderLayout.setText("Delivery");
            } else if (masterData2.order_type.equalsIgnoreCase("0")) {
                holder.orderTypeHeaderLayout.setText("Table Top");
            } else {
                holder.orderTypeHeaderLayout.setText("Dine-In");
            }

            holder.billNowButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            orderNo = masterData2.getId();
                            orderNumber = masterData2.getSales_code();
                            selectedDiscount = Double.parseDouble(masterData2.getTot_discount_to_all_amt());
//                            Log.d("kjfkl", orderNo);

                            if (masterData2.payment_status != null) {

                                if (masterData2.payment_status.equalsIgnoreCase("Unpaid")) {
                                    discountType = "Fixed";
                                    updateData = "1";
                                    getUnpaidBillOrderDetails();
                                } else
                                    getBillDetails();
                            } else {
                                discountType = "Fixed";
                                updateData = "1";
                                getUnpaidBillOrderDetails();
                            }

//                            runningOrdersDialog.dismiss();
                        }
                    });


            holder.ViewButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            orderNo = masterData2.getId();
                            orderNumber = masterData2.getSales_code();
//                            Log.d("kjfkl", orderNo);
//                            getBillDetails();

//                            orderNo = null;

                            OrderDetailsPopup(orderNo);

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;
            AppCompatTextView amountTextView;
            AppCompatTextView dateTextView;
            AppCompatTextView orderTypeHeaderLayout;
            AppCompatTextView cNameTextView;
            AppCompatTextView timeTextView;
            AppCompatButton billNowButton;
            AppCompatButton ViewButton;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.amountTextView = itemView.findViewById(R.id.amountTextView);
                this.dateTextView = itemView.findViewById(R.id.dateTextView);
                this.billNowButton = itemView.findViewById(R.id.billNowButton);
                this.ViewButton = itemView.findViewById(R.id.ViewButton);
                this.orderTypeHeaderLayout = itemView.findViewById(R.id.orderTypeHeaderLayout);
                this.cNameTextView = itemView.findViewById(R.id.cNameTextView);
                this.timeTextView = itemView.findViewById(R.id.timeTextView);

            }

        }

    }

    public class miscProductsRecyclerAdapter extends RecyclerView.Adapter<miscProductsRecyclerAdapter.ViewHolder> implements Filterable {

        Context activity;
        List<ProductsResponse.ProductListResponse> data;
        ProductsResponse.ProductListResponse masterData;
        List<ProductsResponse.ProductListResponse> filterData;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse> data1;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse masterData1;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;

        public miscProductsRecyclerAdapter(Activity activity2,
                                           List<ProductsResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public miscProductsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.products_item, viewGroup, false);
            miscProductsRecyclerAdapter.ViewHolder viewHolder = new miscProductsRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(miscProductsRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            if (!masterData.getItem_name().isEmpty())
                holder.productTextView.setText(masterData.getItem_name());

//            Log.d("saldk", masterData.getItem_name());

            if (!masterData.getSales_price().isEmpty())
                holder.productPriceTextView.setText("$" + masterData.getSales_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData = data.get(position);
                            productId = masterData.getId();
                            productName = masterData.getItem_name();
                            productPrice = masterData.getSales_price();

                            productStatus = db.checkProductExists(productId);

                            if (productStatus.equalsIgnoreCase("Yes")) {
                                addOnsRepeatPopup(productId, productName, productPrice, "", "");
                               /* String qty = db.getQty(productId);
                                count = Integer.parseInt(qty);
                                count = count + 1;
                                db.updateQuantity(productId, count + "");*/
                            } else {
                                count = 0;
                                count = count + 1;
                                rowid = db.addCategory(new Product(productId, productName, productPrice, "" + count));
                            }

                            updateList();

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString().toLowerCase();
//                    Log.d("charstring", charString);

                    if (charString.isEmpty()) {
                        filterData = data;
                    } else {
                        ArrayList<ProductsResponse.ProductListResponse> filteredList = new ArrayList<>();

                        filterData.clear();
                        for (ProductsResponse.ProductListResponse androidVersion : data) {

                            if (androidVersion.getItem_name().toLowerCase().contains(charString)) {

                                filteredList.add(androidVersion);
                            }
                        }

                        filterData = filteredList;
//                        Log.d("ksjkj", filterData.size() + "");
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterData;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filterData = (ArrayList<ProductsResponse.ProductListResponse>) filterResults.values;

                    if (filterData.size() > 0) {
                        LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                    } else {
                        if (charSequence.length() == 0) {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, data);
                        } else {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                        }
                    }
                    productsRecyclerView.setAdapter(LoansListAdapter);
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView productTextView;
            TextView productIdTextView;
            TextView productPriceTextView;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.productTextView = (TextView) itemView.findViewById(R.id.productNameTv);
                this.productPriceTextView = (TextView) itemView.findViewById(R.id.productPriceTv);

            }

        }

    }

    public class productsRecyclerAdapter extends RecyclerView.Adapter<productsRecyclerAdapter.ViewHolder> implements Filterable {

        Context activity;
        List<ProductsResponse.ProductListResponse> data;
        ProductsResponse.ProductListResponse masterData;
        List<ProductsResponse.ProductListResponse> filterData;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse> data1;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse masterData1;

        List<ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        ProductsResponse.ProductListResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;

        String groupName;

        public productsRecyclerAdapter(Activity activity2,
                                       List<ProductsResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public productsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.products_item, viewGroup, false);
            productsRecyclerAdapter.ViewHolder viewHolder = new productsRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(productsRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

//            Log.d("djfkdj", masterData.veg);
//            if (masterData.getVeg().equalsIgnoreCase("1")) {
//                holder.itemTypeImageView.setVisibility(View.VISIBLE);
//                holder.itemTypeImageView.setBackgroundResource(R.mipmap.non_veg);
//            } else if (masterData.getVeg().equalsIgnoreCase("0")) {
//                holder.itemTypeImageView.setVisibility(View.VISIBLE);
//                holder.itemTypeImageView.setBackgroundResource(R.mipmap.veg);
//            } else if (masterData.getVeg().equalsIgnoreCase("2")) {
//                holder.itemTypeImageView.setVisibility(View.GONE);
//            }
            if (!masterData.getItem_name().isEmpty())
                holder.productTextView.setText(masterData.getItem_name());

//            Log.d("saldk", masterData.getItem_name());

            if (!masterData.getSales_price().isEmpty())
                holder.productPriceTextView.setText("$" + masterData.getSales_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Log.d("clickeddd", "clciking");

//                            if (selectedOrderNumber.equalsIgnoreCase("0")) {
                            masterData = data.get(position);
                            productId = masterData.getId();
                            productName = masterData.getItem_name();
                            String categoryId = masterData.getCategory_id();
                            productPrice = masterData.getSales_price();
                            product_id = masterData.getId();
                            productStatus = db.checkProductExists(productId);
                            if (productStatus.equalsIgnoreCase("Yes")) {
                                addOnsRepeatPopup(productId, productName, productPrice, "", "");

                               /* String qty = db.getQty(productId);
                                count = Integer.parseInt(qty);
                                count = count + 1;
                                db.updateQuantity(productId, count + "");*/
                            } else {
                                count = 0;
                                count = count + 1;
                                addNote = "";
                                rowid = db.addCategory(new Product(productId, "" + kotId, productName, "" + "1", addNote, productPrice));
                                kotId = kotId + 1;
                            }
                            getProductAddOnsDetails();
                            updateList();
                        }
                    });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString().toLowerCase();
//                    Log.d("charstring", charString);

                    if (charString.isEmpty()) {
                        filterData = data;
                    } else {
                        ArrayList<ProductsResponse.ProductListResponse> filteredList = new ArrayList<>();

                        filterData.clear();
                        for (ProductsResponse.ProductListResponse androidVersion : data) {

                            if (androidVersion.getItem_name().toLowerCase().contains(charString)) {

                                filteredList.add(androidVersion);
                            }
                        }

                        filterData = filteredList;
//                        Log.d("ksjkj", filterData.size() + "");
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterData;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filterData = (ArrayList<ProductsResponse.ProductListResponse>) filterResults.values;

                    if (filterData.size() > 0) {
                        LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                    } else {
                        if (charSequence.length() == 0) {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, data);
                        } else {
                            LoansListAdapter = new productsRecyclerAdapter(NewPosMainActivity.this, filterData);
                        }
                    }
                    productsRecyclerView.setAdapter(LoansListAdapter);
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView productTextView;
            TextView productIdTextView;
            TextView productPriceTextView;
            AppCompatImageView itemTypeImageView;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.productTextView = itemView.findViewById(R.id.productNameTv);
                this.productPriceTextView = itemView.findViewById(R.id.productPriceTv);
                this.itemTypeImageView = itemView.findViewById(R.id.itemTypeImageView);

            }

        }

    }

    public class AddOnsRecyclerAdapter extends RecyclerView.Adapter<AddOnsRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        Boolean selected;
        int row_index;
        String groupId;
        String kotId;

        public AddOnsRecyclerAdapter(Activity activity2,
                                     List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
        }

        @Override
        public AddOnsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_one, viewGroup, false);
            AddOnsRecyclerAdapter.ViewHolder viewHolder = new AddOnsRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            selected = false;

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);

                            if (addOnIdList.contains(masterData2.getModifier_id())) {
                                selected = true;
                            } else {
                                selected = false;
                            }

                            productId = masterData2.getModifier_id();
                            productStatus = db.checkAddOnExists(masterData2.getModifier_id(), "" + rowid);


                            if (productStatus.equalsIgnoreCase("Yes")) {
                                count = Integer.parseInt(db.getAddOnsQty("" + rowid, productId));
                                count = count + 1;
                                db.updateAddOnQuantity(productId, "" + rowid, count + "");
                                updateList();
                            } else {
                                addOnIdList.add(masterData2.getModifier_id());

                                Log.d("rowwwwwid", "" + rowid);
                                db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));
                                updateList();
                            }
//

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnOneLayout);

            }

        }

    }

    public class AddOnsTwoRecyclerAdapter extends RecyclerView.Adapter<AddOnsTwoRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        int row_index = -1;
        String groupId;
        String kotId;
        Boolean selected;
        String maxAddons;
        String minAddons;

        public AddOnsTwoRecyclerAdapter(Activity activity2,
                                        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id, String maxAddons, String minAddons) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
            this.maxAddons = maxAddons;
            this.minAddons = minAddons;
        }

        @Override
        public AddOnsTwoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_two, viewGroup, false);
            AddOnsTwoRecyclerAdapter.ViewHolder viewHolder = new AddOnsTwoRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsTwoRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Log.d("clicked", "clicked");

                            masterData2 = data2.get(position);
                            row_index = position;
                            addOnsTwoListAdapter.notifyDataSetChanged();

                            if (addOnIdList.contains(masterData2.getModifier_id())) {
                                selected = true;
                            } else {
                                selected = false;
                            }

                            productId = masterData2.getModifier_id();
                            productStatus = db.checkAddOnExists(masterData2.getModifier_id(), "" + rowid);

                            maxAddonsInteger = Integer.parseInt(maxAddons);
                            minAddonsInteger = Integer.parseInt(minAddons);

//                            Log.d("minmaxAddonsand", maxAddons + "," + minAddons + "," + maxAddonsInteger + "," + minAddonsInteger);


                            if (productStatus.equalsIgnoreCase("Yes")) {
                                count = Integer.parseInt(db.getAddOnsQty("" + rowid, productId));
                                count = count + 1;
                                // db.updateAddOnQuantity(productId, count + "");

                                addOns = new ArrayList<>();
                                addOns.clear();
                                addOns = db.getAllAddOns("" + rowid);

                                if (maxAddOnIdList.size() < maxAddonsInteger) {
                                    maxAddOnIdList.add(masterData2.getModifier_id());
                                    db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));

                                } else {

                                    showShortSnackBar("Reached the max addons limit");

                                }

                            } else {
                                if (maxAddOnIdList.size() < maxAddonsInteger) {
                                    maxAddOnIdList.add(masterData2.getModifier_id());
                                    db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));
                                } else {

                                    showShortSnackBar("Reached the max addons limit");

                                }
                            }
                            updateList();


                        }
                    });

            if (row_index == position) {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
            } else {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#96a7d7"));
            }
        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = (TextView) itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = (TextView) itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnTwoLayout);
            }

        }

    }

    public class AddOnsFourRecyclerAdapter extends RecyclerView.Adapter<AddOnsFourRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        Boolean selected;
        int row_index;
        String groupId;
        String kotId;

        public AddOnsFourRecyclerAdapter(Activity activity2,
                                         List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
        }

        @Override
        public AddOnsFourRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_one, viewGroup, false);
            AddOnsFourRecyclerAdapter.ViewHolder viewHolder = new AddOnsFourRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsFourRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            selected = false;

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);

                            if (addOnIdList.contains(masterData2.getModifier_id())) {
                                selected = true;
                            } else {
                                selected = false;
                            }

                            if (selected) {
                                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#ff8c00"));
                                db.deleteAddOn("" + rowid, masterData2.getModifier_id());
                                addOnIdList.remove(masterData2.getModifier_id());
                            } else {
                                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
                                addOnIdList.add(masterData2.getModifier_id());
                                db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));
                            }

                            updateList();

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnOneLayout);

            }

        }

    }

    public class AddOnsFiveRecyclerAdapter extends RecyclerView.Adapter<AddOnsFiveRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        int row_index = -1;
        String groupId;
        String kotId;

        public AddOnsFiveRecyclerAdapter(Activity activity2,
                                         List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
        }

        @Override
        public AddOnsFiveRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_two, viewGroup, false);
            AddOnsFiveRecyclerAdapter.ViewHolder viewHolder = new AddOnsFiveRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsFiveRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            row_index = position;
                            addOnsFiveListAdapter.notifyDataSetChanged();

                            db.deleteAddOnGroupId(groupId, product_id);
                            String qty = db.getQty(product_id);
                            int qt = Integer.parseInt(qty);
                            db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), qt + ""));

                            updateList();
                        }
                    });

            if (row_index == position) {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
            } else {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#96a7d7"));
            }
        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = (TextView) itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = (TextView) itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnTwoLayout);
            }

        }

    }

    public class AddOnsSixRecyclerAdapter extends RecyclerView.Adapter<AddOnsSixRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        int row_index = -1;
        String groupId;
        String kotId;

        public AddOnsSixRecyclerAdapter(Activity activity2,
                                        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
        }

        @Override
        public AddOnsSixRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_two, viewGroup, false);
            AddOnsSixRecyclerAdapter.ViewHolder viewHolder = new AddOnsSixRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsSixRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData2 = data2.get(position);
                            row_index = position;
                            addOnsSixListAdapter.notifyDataSetChanged();

                            db.deleteAddOnGroupId(groupId, product_id);
                            String qty = db.getQty(product_id);
                            int qt = Integer.parseInt(qty);
                            db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), qt + ""));

                            updateList();

                        }
                    });

            if (row_index == position) {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
            } else {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#96a7d7"));
            }
        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = (TextView) itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = (TextView) itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnTwoLayout);
            }

        }

    }

//    public class AddOnsThreeRecyclerAdapter extends RecyclerView.Adapter<AddOnsThreeRecyclerAdapter.ViewHolder> {
//
//        Context activity;
//
//        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
//        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
//        Boolean selected;
//        String groupId;
//        String kotId;
//
//        public AddOnsThreeRecyclerAdapter(Activity activity2,
//                                          List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id) {
//            // TODO Auto-generated constructor stub
//            activity = activity2;
//            data2 = masterList;
//            groupId = group_id;
//            kotId = kot_id;
//        }
//
//        @Override
//        public AddOnsThreeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(activity)
//                    .inflate(R.layout.addon_item_three, viewGroup, false);
//            AddOnsThreeRecyclerAdapter.ViewHolder viewHolder = new AddOnsThreeRecyclerAdapter.ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final AddOnsThreeRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//            masterData2 = data2.get(position);
//
//
//            holder.nameTextView.setText(masterData2.getModifier_name());
//            holder.priceTextView.setText("$" + masterData2.getModifier_price());
//
//            selected = false;
//
//            holder.itemView.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            masterData2 = data2.get(position);
//
//                            if (addOnIdList.contains(masterData2.getModifier_id())) {
//                                selected = true;
//                            } else {
//                                selected = false;
//                            }
//
//                            if (selected) {
//                                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#f1645c"));
//                                db.deleteAddOn("" + rowid, masterData2.getModifier_id());
//                                addOnIdList.remove(masterData2.getModifier_id());
//                            } else {
//                                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
//                                db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));
//                                addOnIdList.add(masterData2.getModifier_id());
//                            }
//
//                            updateList();
//
//                        }
//                    });
//        }
//
//        @Override
//        public int getItemCount() {
//            return data2.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            TextView nameTextView;
//            TextView priceTextView;
//            RelativeLayout addOnRelativeLayout;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//
//                this.nameTextView = (TextView) itemView.findViewById(R.id.addOnItemNameTextView);
//                this.priceTextView = (TextView) itemView.findViewById(R.id.addOnItemPriceTextView);
//                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnThreeLayout);
//
//            }
//
//        }
//
//    }

    public class AddOnsThreeRecyclerAdapter extends RecyclerView.Adapter<AddOnsThreeRecyclerAdapter.ViewHolder> {

        Context activity;

        List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> data2;
        AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse masterData2;
        int row_index = -1;
        String groupId;
        String kotId;
        Boolean selected;
        String maxAddons;
        String minAddons;

        public AddOnsThreeRecyclerAdapter(Activity activity2,
                                          List<AddOnsResponse.ProductAddOnsGroupListResponse.ProductAddOnsListResponse> masterList, String group_id, String kot_id, String maxAddons, String minAddons) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
            groupId = group_id;
            kotId = kot_id;
            this.maxAddons = maxAddons;
            this.minAddons = minAddons;
        }

        @Override
        public AddOnsThreeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.addon_item_three, viewGroup, false);
            AddOnsThreeRecyclerAdapter.ViewHolder viewHolder = new AddOnsThreeRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AddOnsThreeRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.nameTextView.setText(masterData2.getModifier_name());
            holder.priceTextView.setText("$" + masterData2.getModifier_price());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Log.d("clicked", "clicked");

                            masterData2 = data2.get(position);
                            row_index = position;
                            addOnsTwoListAdapter.notifyDataSetChanged();

                            if (addOnIdList.contains(masterData2.getModifier_id())) {
                                selected = true;
                            } else {
                                selected = false;
                            }

                            productId = masterData2.getModifier_id();
                            productStatus = db.checkAddOnExists(masterData2.getModifier_id(), "" + rowid);

                            maxAddonsInteger = Integer.parseInt(maxAddons);
                            minAddonsInteger = Integer.parseInt(minAddons);

//                            Log.d("minmaxAddonsand", maxAddons + "," + minAddons + "," + maxAddonsInteger + "," + minAddonsInteger);


                            if (productStatus.equalsIgnoreCase("Yes")) {
                                count = Integer.parseInt(db.getAddOnsQty("" + rowid, productId));
                                count = count + 1;
                                // db.updateAddOnQuantity(productId, count + "");

                                addOns = new ArrayList<>();
                                addOns.clear();
                                addOns = db.getAllAddOns("" + rowid);

                                if (maxAddOnIdList.size() < maxAddonsInteger) {
                                    maxAddOnIdList.add(masterData2.getModifier_id());
                                    db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));

                                } else {

                                    showShortSnackBar("Reached the max addons limit");

                                }

                            } else {
                                if (maxAddOnIdList.size() < maxAddonsInteger) {
                                    maxAddOnIdList.add(masterData2.getModifier_id());
                                    db.addAddOns(new AddOns(product_id, kotId, groupId, masterData2.getModifier_id(), masterData2.getModifier_name(), masterData2.getModifier_price(), "1", "" + rowid));
                                } else {

                                    showShortSnackBar("Reached the max addons limit");

                                }
                            }
                            updateList();


                        }
                    });

            if (row_index == position) {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#777777"));
            } else {
                holder.addOnRelativeLayout.setBackgroundColor(Color.parseColor("#96a7d7"));
            }
        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView priceTextView;
            RelativeLayout addOnRelativeLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.nameTextView = (TextView) itemView.findViewById(R.id.addOnItemNameTextView);
                this.priceTextView = (TextView) itemView.findViewById(R.id.addOnItemPriceTextView);
                this.addOnRelativeLayout = itemView.findViewById(R.id.addOnThreeLayout);

            }

        }

    }

    private void geCashInResponse() {

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setAmount(cashInOutAmount);
        showProgressDialog();
//        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<CashInResponse> call = apiService.cashInData("1234", request);

        call.enqueue(new Callback<CashInResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CashInResponse> call, Response<CashInResponse> response) {

//                Log.d("responseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    CashInResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

                        cashInOutalertDialog.dismiss();

                    } else {
                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    message = "Server Error. Please try again later";
                    showShortSnackBar(message);
                }
            }

            @Override
            public void onFailure(Call<CashInResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }

    public void cashInOutPopUp() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_cash_in_out_popup, null);
        dialogBuilder.setView(dialogView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        cashInOutalertDialog = dialogBuilder.create();
        cashInOutalertDialog.show();
        cashInOutalertDialog.setCanceledOnTouchOutside(false);
        cashInOutalertDialog.setCancelable(false);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(cashInOutalertDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.65f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        cashInOutalertDialog.getWindow().setAttributes(layoutParams);

        final EditText amountEt = (EditText) dialogView.findViewById(R.id.amountEt);
        final EditText disEt = (EditText) dialogView.findViewById(R.id.disEt);
        final AppCompatButton cashInButton = dialogView.findViewById(R.id.cashInButton);
        final AppCompatButton cashOutButton = dialogView.findViewById(R.id.cashOutButton);
        final AppCompatTextView CashInOutSaveTv = (AppCompatTextView) dialogView.findViewById(R.id.CashInOutSaveTv);
        final AppCompatTextView cashInOutCloseTv = (AppCompatTextView) dialogView.findViewById(R.id.cashInOutCloseTv);

        cashInButton.setBackgroundResource(R.drawable.rounded_button_blue_filled);
        cashInButton.setTextColor(Color.parseColor("#ffffff"));
        cashType = "1";

        cashInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cashInButton.setBackgroundResource(R.drawable.rounded_button_blue_filled);
                cashInButton.setTextColor(Color.parseColor("#ffffff"));
                cashOutButton.setBackgroundResource(R.drawable.rectangle_litesky_bg_filled);
                cashOutButton.setTextColor(Color.parseColor("#1C60AE"));
                cashType = "1";
            }
        });

        cashOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashOutButton.setBackgroundResource(R.drawable.rounded_button_blue_filled);
                cashOutButton.setTextColor(Color.parseColor("#ffffff"));
                cashInButton.setBackgroundResource(R.drawable.rectangle_litesky_bg_filled);
                cashInButton.setTextColor(Color.parseColor("#1C60AE"));
                cashType = "2";
            }
        });

        CashInOutSaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashInOutAmount = amountEt.getText().toString();
                cashInOutDescription = disEt.getText().toString();
                if (amountEt.getText().toString().length() != 0) {
                    try {
                        Double payableAmount = Double.parseDouble(cashInOutAmount);
                        if (cashType.equalsIgnoreCase("1")) {
                            geCashInResponse();
                        } else {
                            getCashInOutSaveDetails();
                        }
                    } catch (NumberFormatException e) {
                        showLongSnackBar("Invalid expression");
                    }
                } else {
                    showShortSnackBar("Please Enter Amount");
                }
            }
        });

        ImageView closeIcon = (ImageView) dialogView.findViewById(R.id.closeIcon);
        cashInOutalertDialog.setCanceledOnTouchOutside(false);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashInOutalertDialog.dismiss();
            }
        });

        cashInOutCloseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashInOutalertDialog.dismiss();
            }
        });

        cashInOutalertDialog.show();
        hideKeyBoard();

    }

    private void getCashInOutSaveDetails() {
        showProgressDialog();
        CashInOutRequest request = new CashInOutRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setAmount(cashInOutAmount);
        request.setDetail_desc(cashInOutDescription);
//        Log.d("requesstst", new Gson().toJson(request));
        retrofit2.Call<CashInOutResponse> call = apiService.cashInOutData("1234", request);
        call.enqueue(new Callback<CashInOutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CashInOutResponse> call, Response<CashInOutResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CashInOutResponse user = response.body();
                    final String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLongSnackBar(message);
                                cashInOutalertDialog.dismiss();
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
            public void onFailure(Call<CashInOutResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    AlertDialog shiftInOutDialog;

    public void shiftInOutPopUp() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_shift_in_out_popup, null);
        dialogBuilder.setView(dialogView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        shiftInOutDialog = dialogBuilder.create();
        shiftInOutDialog.show();
        shiftInOutDialog.setCanceledOnTouchOutside(false);
        shiftInOutDialog.setCancelable(false);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(shiftInOutDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.65f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        shiftInOutDialog.getWindow().setAttributes(layoutParams);

        final EditText amountEt = (EditText) dialogView.findViewById(R.id.amountEt);
        final EditText disEt = (EditText) dialogView.findViewById(R.id.disEt);
        final AppCompatTextView CashInOutSaveTv = (AppCompatTextView) dialogView.findViewById(R.id.CashInOutSaveTv);
        final AppCompatTextView cashInOutCloseTv = (AppCompatTextView) dialogView.findViewById(R.id.cashInOutCloseTv);

        CashInOutSaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftInOutAmount = amountEt.getText().toString();
                shiftInOutDesc = disEt.getText().toString();
                if (amountEt.getText().toString().length() != 0) {
                    getShiftInOutSaveDetails();
                } else {
                    showShortSnackBar("Please Enter Amount");
                }
            }
        });
        ImageView closeIcon = (ImageView) dialogView.findViewById(R.id.closeIcon);

        shiftInOutDialog.setCanceledOnTouchOutside(false);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftInOutDialog.dismiss();
            }
        });
        cashInOutCloseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftInOutDialog.dismiss();
            }
        });
        shiftInOutDialog.show();
        hideKeyBoard();
//        Window window = alertDialog.getWindow();
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void getShiftInOutSaveDetails() {
        showProgressDialog();
        CashInOutRequest request = new CashInOutRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setAmount(shiftInOutAmount);
        request.setDetail_desc(shiftInOutDesc);
//        Log.d("requesstst", new Gson().toJson(request));
        retrofit2.Call<CashInOutResponse> call = apiService.shiftInOutData("1234", request);
        call.enqueue(new Callback<CashInOutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CashInOutResponse> call, Response<CashInOutResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CashInOutResponse user = response.body();
                    final String message = user.message;
                    String success = user.responseCode;
//                    Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                    if (success.equalsIgnoreCase("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLongSnackBar(message);
                                shiftInOutDialog.dismiss();
                            }
                        });
                    } else {
                        shiftInOutDialog.dismiss();
                        showLongSnackBar(message);
                    }
                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<CashInOutResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    private class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        public SimpleDividerItemDecoration(Context applicationContext) {
        }
    }

    AppCompatTextView salesTextview;
    AppCompatTextView salesSummaryTextview;
    AppCompatTextView hourSalesTv;
    AppCompatTextView customerAvgSalesTv;
    AppCompatTextView orderAvgSalesTv;
    AppCompatTextView totalSalesTv;

    AppCompatTextView categoryTextView;
    AppCompatTextView qtyTextView;
    AppCompatTextView totalSalesTextView;

    AppCompatTextView paymentTypeTextView;
    AppCompatTextView paymentAmountTextView;
    AppCompatTextView companyNameTextView;

    public void dayEndReportPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.day_end_report, null);
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
        int dialogWindowWidth = (int) (displayWidth * 0.50f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        salesTextview = dialog.findViewById(R.id.totalNetSalesTv);

        hourSalesTv = dialog.findViewById(R.id.hourSalesTv);
        customerAvgSalesTv = dialog.findViewById(R.id.customerAvgSalesTv);
        orderAvgSalesTv = dialog.findViewById(R.id.orderAvgSalesTv);
        totalSalesTv = dialog.findViewById(R.id.totalSalesTv);

        categoryTextView = dialog.findViewById(R.id.categoryTextView);
        qtyTextView = dialog.findViewById(R.id.qtyTextView);
        totalSalesTextView = dialog.findViewById(R.id.totalSalesTextView);

        paymentAmountTextView = dialog.findViewById(R.id.paymentAmountTextView);
        paymentTypeTextView = dialog.findViewById(R.id.paymentTypeTextView);

        salesSummaryTextview = dialog.findViewById(R.id.totalNetSalesTextView);
        salesTextview.setText(" Order AVG \n Customer AVG \n Total Sales \n Number of Item(s) \n Total Discount \n Total Other Charges \n Order Average ");

        getDayEndResponse();

        dialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.printTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDayEndPrintResponse();
                dialog.dismiss();
            }
        });

    }

    private void getDayEndResponse() {
        showProgressDialog();
        CouponsRequest request = new CouponsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        retrofit2.Call<DayEndReportResponse> call = apiService.dayEndData("1234", request);
        call.enqueue(new Callback<DayEndReportResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DayEndReportResponse> call, Response<DayEndReportResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final DayEndReportResponse user = response.body();
                    hourlyReportList = user.hourlyReportResults;
                    categoryReportList = user.categoryReportResults;
                    paymentsReportList = user.paymentReportResults;
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("dayEndResp", "response 112: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (hourlyReportList.size() > 0) {
                                    salesSummaryTextview.setText(" " + user.Order_AVG + "\n " + user.Customer_AVG + "\n $" + user.total_sale + "\n " + user.number_items + "\n $" + user.total_disc + "\n $" +
                                            user.total_other_charge + "\n $" + user.avg_sale);

                                    StringBuilder sb3 = new StringBuilder();
                                    StringBuilder sb4 = new StringBuilder();
                                    StringBuilder sb5 = new StringBuilder();
                                    StringBuilder sb6 = new StringBuilder();
                                    for (int i = 0; i < hourlyReportList.size(); i++) {
                                        sb3.append(hourlyReportList.get(i).Hour + "\n");
                                        sb4.append(hourlyReportList.get(i).total_customer + "\n");
                                        sb5.append(hourlyReportList.get(i).total_order + "\n");
                                        sb6.append(hourlyReportList.get(i).total_sale + "\n");
                                    }
                                    hourSalesTv.setText("Hour \n " + sb3.toString());
                                    customerAvgSalesTv.setText("Customer Avg \n " + sb4.toString());
                                    orderAvgSalesTv.setText("Order Avg \n " + sb5.toString());
                                    totalSalesTv.setText("Total Sales \n " + "$" + sb6.toString());

//                                    Log.d("lksdjflkdsj", hourlyReportList.get(0).total_sale);

                                    StringBuilder sb = new StringBuilder();
                                    StringBuilder sb1 = new StringBuilder();
                                    StringBuilder sb2 = new StringBuilder();
                                    for (int i = 0; i < categoryReportList.size(); i++) {
                                        sb.append(categoryReportList.get(i).category + "\n");
                                        sb1.append(categoryReportList.get(i).total_qty + "\n");
                                        sb2.append("$" + categoryReportList.get(i).total_cost + "\n");
                                    }

                                    categoryTextView.setText("" + sb.toString());
                                    qtyTextView.setText("" + sb1.toString());
                                    totalSalesTextView.setText("" + sb2.toString());

                                    StringBuilder sb7 = new StringBuilder();
                                    StringBuilder sb8 = new StringBuilder();
                                    for (int j = 0; j < paymentsReportList.size(); j++) {
                                        sb7.append(paymentsReportList.get(j).payment_type + "\n");
                                        sb8.append("$" + paymentsReportList.get(j).payment + "\n");
                                    }
                                    paymentTypeTextView.setText("" + sb7.toString());
                                    paymentAmountTextView.setText(sb8.toString());
                                } else {
                                    infoPopup("No Data found");
                                }
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
            public void onFailure(Call<DayEndReportResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    private void getBannersResponse() {
        showProgressDialog();
        CouponsRequest request = new CouponsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        retrofit2.Call<BannersResponse> call = apiService.bannersData("1234", request);
        call.enqueue(new Callback<BannersResponse>() {
            @Override
            public void onResponse(retrofit2.Call<BannersResponse> call, Response<BannersResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final BannersResponse user = response.body();
                    bannersList = user.bannerResults;

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("bannersResp", "response 112: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sessionManager.storeBannerImage(bannersList.get(0).banner_image);
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
            public void onFailure(Call<BannersResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public void noDataPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.empty_popup, null);
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

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void succsessfailerpopup(String message, Double amount) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.success_popup, null);
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

        final ImageView logo = dialog.findViewById(R.id.logosuccess);
        final TextView success = dialog.findViewById(R.id.success);
        final TextView orderIdTextView = dialog.findViewById(R.id.orderIdTextView);
        final TextView orderTotalTextView = dialog.findViewById(R.id.orderTotalTextView);

        orderIdTextView.setText("Order Number : " + orderNumber);
        orderTotalTextView.setText("Order Total : $" + amount);

        if (message.equalsIgnoreCase("success")) {
            success.setText("Approved!");
            logo.setBackgroundResource(R.mipmap.success_icon);
            success.setTextColor(Color.parseColor("#5c9b45"));
        } else if (message.equalsIgnoreCase("Failure")) {
            success.setText("Declined!");
            logo.setBackgroundResource(R.mipmap.failure);
            success.setTextColor(Color.parseColor("#fe1a1a"));
        }

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.findViewById(R.id.shopAgainBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.printBillButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBillDetails();
                dialog.dismiss();
            }
        });

    }

    public void confirmationPopup(final String from, final String invoiceId) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirmation_popup, null);
        dialogBuilder.setView(dialogView);
        TextView error_text_textview = (TextView) dialogView.findViewById(R.id.error_text_textview);
        TextView yesTextView = (TextView) dialogView.findViewById(R.id.yesTextView);
        TextView noTextView = (TextView) dialogView.findViewById(R.id.noTextView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        yesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equalsIgnoreCase("delete")) {
//                    Log.d("dsfsd", "called");
//                    deleteHoldInvoice(invoiceId);
                } else if (from.equalsIgnoreCase("update")) {
//                    Log.d("jkhz", "called");
//                    Log.d("invoiceId", invoiceId);
//                    sessionManager.storeLastHoldInvoice(invoiceId);
//                    updateHoldInvoiceList(invoiceId);
                }
                alertDialog.dismiss();
            }
        });

        noTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        hideKeyBoard();
//        Window window = alertDialog.getWindow();
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();

        customerDetailsLayout.setVisibility(View.GONE);
        selectedOrderNumber = "0";
        getHomeCategory();

//        if (orderType != null)
//            getOrderDetails(orderType);

    }

    public void printConfirmationPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
//                getBillPrintDetails();
                getBillDetails();
                dialog.dismiss();
            }
        });

    }

    private void getBillPrintDetails() {

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;
                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String a = "Copy - Receipt of \n Purchase(Inc Tax)";

                                ITEM_BILL = "       " + user.company_name + " \n" +
                                        "          " + user.company_address + "\n" +
                                        "                    " + user.city + "\n" +
                                        "                  " + user.state + "\n" +
                                        "                    " + user.postcode + "\n" +
                                        "         " + user.company_website + "\n" +
                                        "                 " + user.phone + "\n" +
                                        "          " + user.email + "\n" +
                                        "-----------------------------------------------\n" +
                                        a + "           " + user.sales_date + "  " + user.time + "\n" +
                                        "Staff                                    " + user.staff + "\n" +
                                        "Device                                   " + user.device + "\n" +
                                        "-----------------------------------------------\n";

                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s %3$8s %4$6s %5$6s", "#", " Item                 ", "Qty", "Price", "Total");
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

                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s %4$6s %5$6s", "" + (i + 1), item, qty, price, total);

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
                                                ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s %4$6s %5$6s", "", a_item, a_qty, a_price, a_total);
                                            }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                        }
                                    } catch (NullPointerException e) {

                                    }
                                }
                                ITEM_BILL = ITEM_BILL
                                        + "\n-----------------------------------------------";
//                                Log.d("jfdhj", ITEM_BILL);

                                printData();
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

    public void printData() {
        Thread t = new Thread() {
            public void run() {
                try {

                    BILL = ITEM_BILL;
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

    public void LastInvoiceOrdersPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orders_list_popup, null);
        builder.setView(dialogView);
        runningOrdersDialog = builder.create();
        runningOrdersDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.60f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        runningOrdersDialog.getWindow().setAttributes(layoutParams);

        final AppCompatTextView closeTextView = runningOrdersDialog.findViewById(R.id.closeTextView);

        ordersRecyclerView = (RecyclerView) runningOrdersDialog.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) runningOrdersDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) runningOrdersDialog.findViewById(R.id.progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ordersRecyclerView.setLayoutManager(mLayoutManager);

//        LinearLayoutManager addKotLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        runningOrdersListView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

        getLastInvoiceOrderDetails();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runningOrdersDialog.dismiss();
            }
        });

    }

    private void getLastInvoiceOrderDetails() {

        OrdersRequest request = new OrdersRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

//        Log.d("ordersRequest", request + new Gson().toJson(request));
        retrofit2.Call<LastInvoiceListResponse> call = apiService.getLastInvoiceListResponse("1234", request);

        call.enqueue(new Callback<LastInvoiceListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LastInvoiceListResponse> call, Response<LastInvoiceListResponse> response) {

                if (response.isSuccessful()) {

                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    LastInvoiceListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        productsRecyclerView.setVisibility(View.VISIBLE);

//                        Log.d("ordersResp", "response 1112: " + new Gson().toJson(response.body()));
                        lastInvoiceList = user.productResults;

                        if (user.productResults != null && user.productResults.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    GridLayoutManager linearLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 4);
                                    productsRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                                    LastInvoiceOrdersRecyclerAdapter LoansListAdapter = new LastInvoiceOrdersRecyclerAdapter(NewPosMainActivity.this, lastInvoiceList);
                                    productsRecyclerView.setAdapter(LoansListAdapter);
                                    productsRecyclerView.setNestedScrollingEnabled(false);
                                    LoansListAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            productsRecyclerView.setVisibility(View.GONE);
                        }

                    } else {
                        productsRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<LastInvoiceListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    public class LastInvoiceOrdersRecyclerAdapter extends RecyclerView.Adapter<LastInvoiceOrdersRecyclerAdapter.ViewHolder> {

        Context activity;

        List<LastInvoiceListResponse.ProductListResponse> data2;
        LastInvoiceListResponse.ProductListResponse masterData2;
        Boolean selected;
        int row_index;

        public LastInvoiceOrdersRecyclerAdapter(Activity activity2,
                                                List<LastInvoiceListResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public LastInvoiceOrdersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.activity_last_invoice_orders_list_item, viewGroup, false);
            LastInvoiceOrdersRecyclerAdapter.ViewHolder viewHolder = new LastInvoiceOrdersRecyclerAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final LastInvoiceOrdersRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.orderIdTextView.setText("#" + masterData2.getId());
            holder.amountTextView.setText("$" + masterData2.getGrand_total());
            holder.dateTextView.setText(masterData2.created_time);

            if (masterData2.order_type.equalsIgnoreCase("1")) {
                holder.orderTypeTextView.setText("Take Away");
            } else if (masterData2.order_type.equalsIgnoreCase("2")) {
                holder.orderTypeTextView.setText("Delivery");
            } else {
                holder.orderTypeTextView.setText("Dine-In");
            }

            holder.customerNameTextView.setText(masterData2.getCustomer_name());

            holder.billNowButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
                            orderNo = masterData2.getId();
                            orderNumber = masterData2.getSales_code();
//                            Log.d("kjfkl", orderNo);
                            printValue = 1;
                            getBillDetails();
//                            printConfirmationPopup();
//                            getBillPrintDetails();
//                            runningOrdersDialog.dismiss();
                        }
                    });

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView orderIdTextView;
            AppCompatTextView amountTextView;
            AppCompatTextView dateTextView;
            AppCompatTextView customerNameTextView;
            AppCompatTextView orderTypeTextView;
            AppCompatButton billNowButton;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
                this.amountTextView = itemView.findViewById(R.id.amountTextView);
                this.dateTextView = itemView.findViewById(R.id.dateTextView);
                this.billNowButton = itemView.findViewById(R.id.runningOrdersBillButton);
                this.customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
                this.orderTypeTextView = itemView.findViewById(R.id.orderTypeTextView);

            }

        }

    }

    public void infoPopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_popup, null);
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

        TextView error_text_textview = dialogView.findViewById(R.id.infoTextView);
        error_text_textview.setText(message);

        dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void confirmationPopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.exit_confirmation_popup, null);
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

        TextView error_text_textview = dialogView.findViewById(R.id.infoTextView);
        error_text_textview.setText(message);

        dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void searchPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.search_popup, null);
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

        final AppCompatEditText searchEditText = dialogView.findViewById(R.id.searchEditText);

        dialog.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWord = searchEditText.getText().toString();
//                Log.d("kljdfkd", searchWord);
                if (searchWord.length() > 2) {
                    hideKeyBoard();
                    getSearchProductDetails();
                    searchEditText.setText("");
                    dialog.dismiss();
                }

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
            NewPosMainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Make Sure Bluetooth have InnerPrinter", Toast.LENGTH_LONG).show();
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

    public void printFooter() {
        BILL = ITEM_BILL;
        BILL = BILL + "\n ";
        byte[] arrayOfByte1 = {27, 33, 0};
//        Log.d("final bill", BILL);
        byte[] format = {27, 33, 0};

        printIt(BILL);
    }

    public void paymentsendDataOtherPaymentOrder() {

        products = db.getAllCategories();

//        productsList.clear();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

            String catid = cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();

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
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        paymentgetSaveOrderOtherPaymentResponse();

    }

    public void paymentsendCartData() {

        products = db.getAllCategories();

//        productsList.clear();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

            String catid = cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();

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
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        if (priceId.equalsIgnoreCase("0")) {
            if (paymentType.equalsIgnoreCase("1")) {
                paymentgetSaveOrderResponse();
            } else if (paymentType.equalsIgnoreCase("2")) {
                paymentgetSaveOrderResponse();
            }
        } else {
            paymentgetSaveOrderResponse();
        }

    }

    private void paymentgetSaveOrderResponse() {

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Cash");
        request.setChange_return("0");
        request.setItem_list(productsArray);
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");

        showProgressDialog();
//        Log.d("saveOrderRequest", new Gson().toJson(request));

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

                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

                        db.deleteAll();

//                        printConfirmationPopup();
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

    private void getSaveOrderCheckoutResponse() {

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Cash");
        request.setOrder_id(orderNo);
        request.setPaid_amt(amount + "");
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");
        request.setOutlet_id(outlet_id);

        showProgressDialog();
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

    private void paymentgetSaveOrderOtherPaymentResponse() {

//        totalAmount = totalAmount + orderTotal;
        OrderRequest request = new OrderRequest();
        request.setCustomer_id("");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Card");
        request.setChange_return("0");
        request.setItem_list(productsArray);
        request.setTips("" + tipAmount);
        request.setPrice_id(priceId);

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }

        showProgressDialog();
//        Log.d("saveOrderRequest", new Gson().toJson(request));

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
                        successFailurePopup("success");

//                        getSquareUpResponse();

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

    private void paymentgetSaveOrderCardResponse() {

        OrderRequest request = new OrderRequest();
        request.setCustomer_id("171");
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalPayable);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("Card");
        request.setChange_return("0");
        request.setItem_list(productsArray);
        request.setPrice_id(priceId);
        request.setTips(tipAmount + "");

        if (updateData.equalsIgnoreCase("1")) {
//            Log.d("kdtryjf", updateData);
            request.setOrder_number(orderNo);
        }

        showProgressDialog();
//        Log.d("saveOrderRequest", new Gson().toJson(request));

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


                        paymentgetSquareUpResponse();

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

    private void paymentgetSquareUpResponse() {

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

//                    Log.d("responseee", new Gson().toJson(response));
                    hideProgressDialog();
                    SquareUpPaymentResponse user = response.body();

                    String message = user.getMessage();
                    String status = user.getStatus();
                    String success = user.getResponseCode();
                    if (status.equalsIgnoreCase("SUCCESS")) {

                        try {
                            checkoutId = user.getCheckout_id();
//                            Log.d("jshdfsd", checkoutId);

                            paymentgetTerminalCheckoutResponse();
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

    private void paymentgetTerminalCheckoutResponse() {

        GetTermicalCheckoutRequest request = new GetTermicalCheckoutRequest();
        request.setOrder_id(orderNo);
        request.setCheckout_id(checkoutId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TermicalCheckoutResponse> call = apiService.termicalCheckoutData("1234", request);

        call.enqueue(new Callback<TermicalCheckoutResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TermicalCheckoutResponse> call, Response<TermicalCheckoutResponse> response) {

                if (response.isSuccessful()) {
//                    Log.d("responseee989", new Gson().toJson(response));
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

    private void paymentcancelTerminalCheckoutResponse() {

        GetTermicalCheckoutRequest request = new GetTermicalCheckoutRequest();
        request.setCheckout_id(checkoutId);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<TermicalCancelCheckoutResponse> call = apiService.cancelTerminalCheckoutData("1234", request);

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

    private void paymentgetBillDetails() {

//        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        paymentproductsList = user.productResults;

                        orderNo = user.order_number;
                        billingName = user.customer;
                        companyName = user.company_name;
                        date = user.time;
                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String a = "Copy - Receipt of \n Purchase(Inc Tax)";

                                ITEM_BILL = "       " + user.company_name + " \n" +
                                        "          " + user.company_address + "\n" +
                                        "                    " + user.city + "\n" +
                                        "                  " + user.state + "\n" +
                                        "                    " + user.postcode + "\n" +
                                        "         " + user.company_website + "\n" +
                                        "                 " + user.phone + "\n" +
                                        "          " + user.email + "\n" +
                                        "-----------------------------------------------\n" +
                                        a + "           " + user.sales_date + "  " + user.time + "\n" +
                                        "Staff                                    " + user.staff + "\n" +
                                        "Device                                   " + user.device + "\n" +
                                        "-----------------------------------------------\n";

                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s %3$8s %4$6s %5$6s", "#", " Item                 ", "Qty", "Price", "Total");
                                ITEM_BILL = ITEM_BILL + "\n";
                                ITEM_BILL = ITEM_BILL
                                        + "-----------------------------------------------";
                                for (int i = 0; i < paymentproductsList.size(); i++) {
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

                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s %4$6s %5$6s", "" + (i + 1), item, qty, price, total);

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
                                                ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$5s %4$6s %5$6s", "", a_item, a_qty, a_price, a_total);
                                            }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                        }
                                    } catch (NullPointerException e) {

                                    }
                                }
                                ITEM_BILL = ITEM_BILL
                                        + "\n-----------------------------------------------";
//                                Log.d("jfdhj", ITEM_BILL);

                                printData();
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

    private void paymentgetKotBillDetails() {

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

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
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
                                Log.d("jfdhj", ITEM_BILL_1);

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

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

//    public void selectBluetoothDialog() {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewPosMainActivity.this);
//// ...Irrelevant code for customizing the buttons and title
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.bluetooth_saved_popup, null);
//        dialogBuilder.setView(dialogView);
//        alertDialog = dialogBuilder.create();
//        setResult(Activity.RESULT_CANCELED);
//        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
//
//        ListView mPairedListView = (ListView) dialogView.findViewById(R.id.paired_devices);
//        mPairedListView.setAdapter(mPairedDevicesArrayAdapter);
//        mPairedListView.setOnItemClickListener(mDeviceClickListener);
//
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
//
//        if (mPairedDevices.size() > 0) {
//            for (BluetoothDevice mDevice : mPairedDevices) {
//                mPairedDevicesArrayAdapter.add(mDevice.getName() + "\n" + mDevice.getAddress());
//            }
//        } else {
//            String mNoDevices = "None Paired";//getResources().getText(R.string.none_paired).toString();
//            mPairedDevicesArrayAdapter.add(mNoDevices);
//        }
//        alertDialog.show();
//    }

    public void upiPaymentPopup() {

        if (upiPaymentPopup == true) {

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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

        Log.d("upiiiirrr", UPI);
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

    Boolean doubleBackToExitPressedOnce = false;

    public void successFailurePopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
        orderTotalTextView.setText("Order Total : $" + totalPayable);
        orderTotalTextView.setVisibility(View.GONE);

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

    private void paymentprintIt(String thisData) {
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

            NewPosMainActivity.this.runOnUiThread(new Runnable() {
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

    public void paymentprintData() {
        Thread t = new Thread() {
            public void run() {
                try {

                    BILL = ITEM_BILL;
                    BILL = BILL + "\n ";
                    byte[] arrayOfByte1 = {27, 33, 0};
                    Log.d("final bill", BILL);
                    byte[] format = {27, 33, 0};

                    paymentprintIt(BILL);

                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    public void paymentLayout() {

        totalPayable = totalAmount;

        totalPayings = paymentAmount;
        qty = products.size();

        paymentLayout.setVisibility(View.VISIBLE);
        categoryListRecyclerView.setVisibility(View.GONE);
        categoriesLayout.setVisibility(View.GONE);
        calculationEditText = findViewById(R.id.calculationEditText);

        cashPaymentTextView = findViewById(R.id.cashPaymentTextView);
        cardPaymentTextView = findViewById(R.id.cardPaymentTextView);

        uberTextView = findViewById(R.id.uberTv);
        menulogTextView = findViewById(R.id.menulogTv);
        doordashTextView = findViewById(R.id.doordashTv);
        othersTextView = findViewById(R.id.othersTv);

        cashCardLayout = findViewById(R.id.cashCardLayout);
        otherOrders = findViewById(R.id.otherOrders);

        db = new DataBaseHandler(getApplicationContext());

        addOnIdList = new ArrayList<>();
        addOnNameList = new ArrayList<>();
        addOnPriceList = new ArrayList<>();
        addOnQtyList = new ArrayList<>();

        retrofitService();

        priceId = "0";

//        totalPayable = getIntent().getExtras().getDouble("totalPayable");
//        totalAmount = getIntent().getExtras().getDouble("total");
//        qty = getIntent().getExtras().getInt("qty");
//        String discount = getIntent().getExtras().getDouble("discount");
//        orderNo = getIntent().getExtras().getString("orderId");
//        updateData = getIntent().getExtras().getString("updateData");
//        priceId = getIntent().getExtras().getString("priceId");


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

//        oneButton = findViewById(R.id.oneTv);
//        twoButton = findViewById(R.id.twoTv);
//        threeButton = findViewById(R.id.threeTv);
//        fourButton = findViewById(R.id.fourTv);
//        fiveButton = findViewById(R.id.fiveTv);
//        sixButton = findViewById(R.id.sixTV);
//        sevenButton = findViewById(R.id.sevenTv);
//        eightButton = findViewById(R.id.eightTv);
//        nineButton = findViewById(R.id.nineTv);
//        zeroButton = findViewById(R.id.zeroTv);
//        plusButton = findViewById(R.id.additionTv);
//        minusButton = findViewById(R.id.minusTv);
//        multiplyButton = findViewById(R.id.multiplyTv);
//        divideButton = findViewById(R.id.divideTv);
//        equalButton = findViewById(R.id.equalsTv);
//        dotButton = findViewById(R.id.dotTv);

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
        totalDiscountTv.setText("$" + String.valueOf(round(paymentDiscount, 2)));
        totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));

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
                    Log.d("lksjdkl", value2);
                    String value = part2.substring(part2.length() - 1);
                    Log.d("akjshjk", value);
                    char first = part2.charAt(0);
                    Log.d("dfdgfd", String.valueOf(first));
                    if (value2.equalsIgnoreCase("9")) {
                        if (value.equalsIgnoreCase("6") || value.equalsIgnoreCase("7") ||
                                value.equalsIgnoreCase("8") || value.equalsIgnoreCase("9")) {
                            part1 = String.valueOf(Integer.parseInt(part1) + 1);
                            Log.d("kjashdj", part1);
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
                                Log.d("kjashdj", part2);
                            } else if (value.equalsIgnoreCase("6") || value.equalsIgnoreCase("7") ||
                                    value.equalsIgnoreCase("8") || value.equalsIgnoreCase("9")) {
                                part2 = String.valueOf(Integer.parseInt(part2.substring(0, 1)) + 1);
                                Log.d("kjashdj", part2);
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
                newpaymentTypePopup();

            }
        });

//        oneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "1");
//            }
//        });
//
//        twoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "2");
//            }
//        });
//
//        threeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "3");
//            }
//        });
//
//        fourButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "4");
//            }
//        });
//
//        fiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "5");
//            }
//        });
//
//        sixButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "6");
//            }
//        });
//
//        sevenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "7");
//            }
//        });
//
//        eightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "8");
//            }
//        });
//
//        nineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "9");
//            }
//        });
//
//        zeroButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + "0");
//            }
//        });
//
//        dotButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText(calculationEditText.getText() + ".");
//            }
//        });
//
//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculationEditText.setText("");
//                onEqual();
//            }
//        });
//
//        plusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
//                    calculationEditText.setText("");
//                    addition = true;
//                } catch (NumberFormatException e) {
//                    showLongSnackBar("Invalid expression");
//                }
//            }
//        });
//
//        minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
//                    calculationEditText.setText("");
//                    subtraction = true;
//                } catch (NumberFormatException e) {
//                    showLongSnackBar("Invalid expression");
//                }
//            }
//        });
//
//        multiplyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
//                    calculationEditText.setText("");
//                    multiplication = true;
//                } catch (NumberFormatException e) {
//                    showLongSnackBar("Invalid expression");
//                }
//            }
//        });
//
//        divideButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mValue1 = Double.parseDouble(calculationEditText.getText().toString());
//                    calculationEditText.setText("");
//                    division = true;
//                } catch (NumberFormatException e) {
//                    showLongSnackBar("Invalid expression");
//                }
//            }
//        });
//
//        equalButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    mValue2 = Double.parseDouble(calculationEditText.getText().toString());
//                    if (addition == true) {
//                        add = mValue1 + mValue2;
//                        calculationEditText.setText(String.valueOf(add));
//                    }
//                    if (subtraction == true) {
//                        sub = mValue1 - mValue2;
//                        calculationEditText.setText(String.valueOf(sub));
//                    }
//                    if (multiplication == true) {
//                        mul = mValue1 * mValue2;
//                        calculationEditText.setText(String.valueOf(mul));
//                    }
//                    if (division == true) {
//                        div = mValue1 / mValue2;
//                        calculationEditText.setText(String.valueOf(div));
//                    }
//
//                    onEqual();
//
//                } catch (NumberFormatException e) {
//                    showLongSnackBar("Invalid Expression");
//                }
//
//            }
//        });

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
                                paymentsendCartData();
                            } else {
                                getSaveOrderCheckoutResponse();
                            }
                        } else {
                            showLongSnackBar("Please pay complete amount");
                        }
                    }

                } else {
                    if (orderNo == null) {
                        paymentsendCartData();
                    } else {
                        getSaveOrderCheckoutResponse();
                    }
                }
            }
        });

        selectPrinterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(NewPosMainActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
//                        Log.d("fdfsd", "msg.toString()");
                        ListPairedDevices();
                        // selectBluetoothDialog();
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

        paymenthandlerCall();

    }

    public void paymenthandlerCall() {
        final Runnable r = new Runnable() {
            public void run() {

                if (checkoutId.equalsIgnoreCase("1234")) {

                } else {
                    if (CommonUtilities.checkConn(getApplicationContext())) {
                        try {
                            if (progressDialog.isShowing() && progressDialog != null) {

                            } else {
                                paymentprogressPopup();
                            }
                        } catch (NullPointerException e) {
                            paymentprogressPopup();
                        }
                        paymentgetTerminalCheckoutResponse();
                    } else {

                    }
                }
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 10000);
    }

    public void paymentprogressPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
                    paymentcancelTerminalCheckoutResponse();
                }
            }
        });

    }

    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.

        if (calculationEditText.getText().toString().isEmpty()) {
            paymentAmount = Double.parseDouble("0");
        } else {
            paymentAmount = Double.parseDouble(calculationEditText.getText().toString());
        }

        // Create an Expression (A class from exp4j library)

        Double totalPayings = paymentAmount;

        totalPayingsTv.setText("$" + totalPayings);

        balance = totalPayable;
        balance = balance - paymentAmount;

        if (balance > 0) {
            totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));
        } else {
            balance = 0.00;
            totalBalanceTv.setText("$" + String.valueOf(round(balance, 2)));
        }
        changeReturn = paymentAmount - totalPayable;
        if (changeReturn > 0) {
            changeReturnTv.setText("$" + String.valueOf(round(changeReturn, 2)));
        } else {
            changeReturn = 0.00;
            changeReturnTv.setText("$" + String.valueOf(round(changeReturn, 2)));
        }

    }

    public void tipPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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

    public void newpaymentTypePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
                paymentothersPaymentTypePopup();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.squareupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentsendCardCartData();
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

    public void paymentothersPaymentTypePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
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
        amountTextView.setText("Order Amount  :  $" + totalAmount);

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
                paymentsendDataOtherPaymentOrder();
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

    public void paymentsendCardCartData() {

        products = db.getAllCategories();

        productsArray = new JSONArray();
        List<JsonObject> myProdObjects = new ArrayList<JsonObject>(products.size());

        for (Product cn : products) {

            String catid = cn.get_product_id();
            String productQty = cn.get_qty();
            String price = cn.get_price();

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
            productsObj.addProperty("addon_id", addOnId);
            productsObj.addProperty("addon_qty", addOnQty);
            productsObj.addProperty("addon_price", addOnPrice);
            productsObj.addProperty("addon_note", addOnsNotes);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        getSaveOrderCardResponse();

    }

    private void getDayEndPrintResponse() {
        showProgressDialog();
        CouponsRequest request = new CouponsRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        retrofit2.Call<DayEndReportResponse> call = apiService.dayEndData("1234", request);
        call.enqueue(new Callback<DayEndReportResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DayEndReportResponse> call, Response<DayEndReportResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final DayEndReportResponse user = response.body();
                    hourlyReportList = user.hourlyReportResults;
                    categoryReportList = user.categoryReportResults;
                    paymentsReportList = user.paymentReportResults;
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("dayEndResp", "response 112: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//

                                //// printing ///////


                                String a = "   SALES SUMMARY";

                                ITEM_BILL = "             " + "ORDER AVG     " + user.Order_AVG + "\n " + "CUSTOMER AVG  " + user.Customer_AVG
                                        + "\n TOTAL SALES   $" + user.total_sale + "\n NO. OF ITEMS  +" + user.number_items + "\n TOTAL DISCOUNT +$"
                                        + user.total_disc + "\n OTHER CHARGES +$" + user.total_other_charge + "\n ORDER AVG    $ " + user.avg_sale + "\n"
                                        + "              HOURLY SALES" + "\n" +
                                        "-------------------------------------------\n";

                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-1s %3$-1s %4$-4s %5$-4s", "#", "  Hour", "Avg", "OrderAvg  ", "TotalSales  ");
                                ITEM_BILL = ITEM_BILL + "\n";

                                for (int i = 0; i < hourlyReportList.size(); i++) {

                                    String hour = user.hourlyReportResults.get(i).getHour();
                                    String customerAvg = user.hourlyReportResults.get(i).getTotal_customer();
                                    String orderAvg = user.hourlyReportResults.get(i).getTotal_order();
                                    String totalSale = user.hourlyReportResults.get(i).getTotal_sale();

                                    if (hour.length() < 3) {
                                        for (int k = 0; k < 2; k++) {
                                            hour = hour + "";
                                        }
                                    }

                                    if (hour.length() >= 3) {
                                        hour = hour.substring(0, 3) + "";
                                    }

                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %3$-1s %4$-4s %5$-4s", "" + (i + 1), "  " + hour, "  " + customerAvg, "     " + orderAvg, "     " + totalSale);

                                    ITEM_BILL = ITEM_BILL +

                                            "           \n \n CATEGORY SALES \n" +
                                            "-----------------------------------------------\n";
                                }


                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-20s %3$-5s %4$-4s", "#", " Category", "Qty", "Total");
                                ITEM_BILL = ITEM_BILL + "\n";

                                for (int i = 0; i < categoryReportList.size(); i++) {

                                    String category = user.categoryReportResults.get(i).getCategory();
                                    String categorySales = user.categoryReportResults.get(i).getTotal_qty();
                                    String totalSales = user.categoryReportResults.get(i).getTotal_cost();

                                    if (category.length() < 15) {
                                        for (int k = 0; k < 13; k++) {
                                            category = category + " ";
                                        }
                                    }

                                    if (category.length() >= 15) {
                                        category = category.substring(0, 15) + "..";
                                    }

                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-20s %3$-5s %4$-4s", "" + (i + 1), category, categorySales, totalSales);


                                }

                                ITEM_BILL = ITEM_BILL +
                                        "                   \n \n PAYMENT DETAILS \n" +
                                        "-----------------------------------------------\n";

                                ITEM_BILL = ITEM_BILL + String.format("%1$-1s %2$-15s %3$-4s", "#", " PAYMENT", "AMOUNT");
                                ITEM_BILL = ITEM_BILL + "\n";

                                for (int i = 0; i < paymentsReportList.size(); i++) {

                                    String paymentType = user.paymentReportResults.get(i).getPayment_type();
                                    String payment = user.paymentReportResults.get(i).getPayment();

                                    if (paymentType.length() < 10) {
                                        for (int k = 0; k < 8; k++) {
                                            paymentType = paymentType + "";
                                        }
                                    }

                                    if (paymentType.length() >= 10) {
                                        paymentType = paymentType.substring(0, 10) + "";
                                    }

                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-15s %3$-4s", "" + (i + 1), paymentType, payment);

                                }


                                ITEM_BILL = ITEM_BILL
                                        + "\n-----------------------------------------------\n" + "";
//                                Log.d("jfdhj", ITEM_BILL);

                                printData();

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
            public void onFailure(Call<DayEndReportResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
//                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void getOnlineBillDetails() {

        PrintKotRequest request = new PrintKotRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printOnlineBill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;

                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    String orderType = user.order_type;
                                    if (orderType.equalsIgnoreCase("Take away")) {
                                        userDetails = "Customer Name: " + user.customer + "\n" +
                                                "Customer Mobile: " + user.customer_mobile;
                                    } else if (orderType.equalsIgnoreCase("Online Delivery")) {
                                        userDetails = "Customer Name: " + user.del_customer_name + "\n" +
                                                "Customer Mobile: " + user.del_customer_mobile + "\n" +
                                                "Customer Address: " + user.del_customer_address;
                                    }

                                    ITEM_BILL = "         " + user.company_name + "\n" +
                                            "      ABN:" + user.company_address + "\n" +
                                            "      " + user.company_address + "\n" +
                                            "              Ph:" + user.phone + "\n" +
                                            " \n" +
                                            "            " + user.order_type + "\n" +
                                            "\n" +
                                            " #" + user.order_number + "\n" +
                                            " Date: " + user.sales_date + "\n" +
                                            " Time: " + user.time + "\n" +
                                            " Taken/By:  " + user.staff + "\n" +
                                            "-----------------------------------------------\n" +
                                            userDetails + "\n" +
                                            "-----------------------------------------------\n" +
                                            "             ORDER DETAILS \n";

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String price = user.productResults.get(i).price_per_unit;
                                        String total = user.productResults.get(i).unit_total_cost;

                                        ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$-1s %2$-1s %5$6s", "x " + qty, item, total);

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    String a_qty = addOnsproductsList.get(j).qty;
                                                    String a_price = addOnsproductsList.get(j).price;
                                                    String a_total = addOnsproductsList.get(j).total_price;

                                                    ITEM_BILL = ITEM_BILL + "\n " + String.format("%1$5s ", a_item);
                                                }
//                                    ITEM_BILL = ITEM_BILL + "\n";
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                    }
                                    ITEM_BILL = ITEM_BILL
                                            + "\n-----------------------------------------------";

                                    ITEM_BILL = ITEM_BILL +
                                            "TOTAL                    $" + user.total_amount + "\n" +
                                            "TIPS                   $" + "0.00" + "\n" +
                                            "PAID                   $" + user.total_amount + "\n" +
                                            "BALANCE                    $" + "0.00" + "\n";

                                    Log.d("jfdhj", ITEM_BILL);

                                    printData();

                                    sendPrintStatus(user.order_id);

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

    private void getPrintKitchenBillDetails1() {

//        showProgressDialog();
        PrintKotRequest request = new PrintKotRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        Log.d("kitchenAutoPrint", " " + new Gson().toJson(request));

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

                                    if (user.table_number.equalsIgnoreCase("0")) {
                                        ITEM_BILL = ITEM_BILL + "<b>Date: " + user.getSales_date() + "\n";
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

//                                    printUsb();

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

    private void getOnlineTestBillDetails() {

        PrintKotRequest request = new PrintKotRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setOrder_id("11");

        retrofit2.Call<PrintBillResponse> call = apiService.printbill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;

                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    printText =
                                            "[C]<b>" + user.company_name + "</b>\n" +
                                                    "[C]ABN:" + user.abn + "\n" +
                                                    "[C]" + user.company_address + "\n" +
                                                    "[C] Ph:" + user.mobile + "\n" +
                                                    "[L]\n" +
                                                    "[C]<b> <font size='tall'>" + user.order_type + "</font></b>\n" +
                                                    "[L]\n" +
                                                    "[L] <b><font size='tall'>#" + user.order_number + "</font></b>\n" +
                                                    "[L]\n" +
                                                    "[L] Date : " + user.sales_date + "\n" +
                                                    "[L] Time : " + user.time + "\n" +
                                                    "[L] Taken/By : " + user.staff + "\n" +
                                                    "[L] Payment : " + user.payment_status + "\n" +
                                                    "[C]<b>--------------------------------</b>\n" +
                                                    "[L] <b> No: " + user.table_number + "</b>\n" +
                                                    "[C]--------------------------------\n " +
                                                    "[L]\n" +
                                                    "[C]ORDER DETAILS" + "\n" +
                                                    "[L]\n";

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String total = user.productResults.get(i).unit_total_cost;

                                        printText = printText + "[L]x " + qty + "  " + item + "[R]" + total + "\n";

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    printText = printText + "[L]    " + a_item + "\n";
                                                }
//
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                    }

                                    printText = printText +
                                            "[C]--------------------------------\n" +
                                            "[L]<b>TOTAL [R]$" + user.total_amount + "</b>\n" +
                                            "[L]<b>TIPS [R]$" + "0.00" + "</b>\n" +
                                            "[L]<b>PAID [R]$" + "0.00" + "</b>\n" +
                                            "[L]<b>BALANCE[R]$" + user.total_amount + "</b> \n";

//                                    Log.d("kdjkldsjf", printText);

//                                    printData();

                                    doPrint();

//                                    sendPrintStatus(user.order_id);

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

    private void getTakeAwayTestBillDetails() {

        PrintKotRequest request = new PrintKotRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.printOnlineBill("1234", request);

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;

                        orderNo = user.order_number;

                        totalAmt = user.total_amount;
                        paidAmt = user.paid_amount;
//                        Log.d("kfdjgkl", totalAmt);
//                        dueAmount = Double.parseDouble(totalAmt) - Double.parseDouble(paidAmt);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (printList.size() > 0) {

                                    userDetails = "";
                                    String orderType = user.order_type;
                                    if (orderType.equalsIgnoreCase("Take away")) {
                                        userDetails = "[L]<b>Customer Name: <font size='big'>" + user.customer + "</font>\n" +
                                                "[L]Customer Mobile: " + user.customer_mobile + "</b>\n";
                                        textName = "Pickup Time : ";
                                    } else if (orderType.equalsIgnoreCase("Online Delivery")) {
                                        userDetails = "[L]<b>Customer Name: <font size='big'>" + user.del_customer_name + "</font>\n" +
                                                "[L]Customer Mobile: " + user.del_customer_mobile + "\n" +
                                                "[L]Customer Address: " + user.del_customer_address + "</b>\n";
                                        textName = "Delivery Time : ";
                                    }

                                    printText = "";
                                    printText =
                                            "[L]\n" +
                                                    "[C]<b> <font size='big'>" + user.company_name + "</font></b>\n" +
                                                    "[C]ABN:" + user.abn + "\n" +
                                                    "[C]" + user.company_address + "\n" +
                                                    "[C]Ph:" + user.mobile + "\n" +
                                                    "[L]\n" +
                                                    "[C]<b> <font size='big'>" + user.order_type + "</font></b>\n" +
                                                    "[L]\n" +
                                                    "[L] <b><font size='big'>#" + user.order_number + "</font></b>\n" +
                                                    "[L]\n" +
                                                    "[L]<b> Date : " + user.sales_date + "\n" +
                                                    "[L] Time Taken : " + user.time + "\n";
                                    if (orderType.equalsIgnoreCase("Online Delivery") || orderType.equalsIgnoreCase("Take away")) {
                                        printText = printText + "[L]" + textName + user.delivery_time + "\n";
                                    }
                                    printText = printText +
                                            "[L] Taken/By : " + user.staff + "\n" +
                                            "[L] Payment : <font size='big'>" + user.payment_status + "</font></b>\n";

                                    if (orderType.equalsIgnoreCase("Online Delivery") || orderType.equalsIgnoreCase("Take away")) {
                                        printText = printText + userDetails
                                                +
                                                "[C]----------------------------------\n";
                                    }

                                    printText = printText + "[C]-----------------------------------" +
                                            "[L]\n" +
                                            "[C]<b><font size='big'>Order Details</font></b>" + "\n" +
                                            "[L]\n";

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String total = user.productResults.get(i).unit_total_cost;

                                        printText = printText + "[L]<b><font size='normal'>x " + qty + "  " + item + "[R]" + total + "\n";

                                        try {
                                            if (addOnsproductsList.size() > 0) {

                                                for (int j = 0; j < addOnsproductsList.size(); j++) {
                                                    String a_item = addOnsproductsList.get(j).addon_name;
                                                    printText = printText + "[L]     " + a_item + "</font></b>\n";
                                                }
//
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                        printText = printText + "[L]\n";
                                    }

                                    printText = printText +
                                            "[C]------------------------------------\n" +
                                            "[L]<b>SUB TOTAL [R]$" + user.grand_total + "</b>\n" +
                                            "[L]<b>DISCOUNT [R]$" + user.total_discount + "</b>\n" +
                                            "[L]<b>TIPS [R]$" + user.tips_amount + "</b>\n" +
                                            "[L]<b>PAID [R]$" + user.paid_amount + "</b>\n" +
                                            "[L]<b>BALANCE[R]$" + user.balance_amount + "</b> \n";
                                    printText = printText + "[L]\n" +
                                            "[C]<qrcode>" + user.getCompany_website() + "</qrcode>\n" +
                                            "[L]\n" +
                                            "[L]\n";

//                                    printUsb();

                                    doPrint();

                                    sendPrintStatus(user.order_id);

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

    public void doPrint() {
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
//                Log.d("kldj", connection + "");

                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 65f, 32);

                    String logo = "[L]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                            this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_white_one_line,
                                    DisplayMetrics.DENSITY_XXHIGH, getTheme())) + "</img>\n";

                    final String text1 = logo + printText;

//                    final String text = printText;
                    printer.printFormattedText(text1, 5);
                    SunmiPrintHelper.getInstance().openCashBox();
                    orderNo = null;


                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    private void getCashBoxOpen2() {

        Toast.makeText(getApplicationContext(), "Trying to Open Cashvbox 222", Toast.LENGTH_SHORT).show();


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
//                Log.d("kldjhjhj", connection + "");

                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 75f, 40);

                    final String text1 = "";


//                    getCashBoxOpen();
//                    Log.d("priceId", priceId);
//                    printer.printFormattedTextAndOpenCashBox(text1, 5);
                    SunmiPrintHelper.getInstance().openCashBox();

//                    if (!priceId.equalsIgnoreCase("0")) {
//
//                        Log.d("calinghere1", "calinghere1");
//
//                        printer.printFormattedTextAndOpenCashBox(text1, 5);
//                        SunmiPrintHelper.getInstance().openCashBox();
//
//                    } else {
//                        Log.d("calinghere2", "calinghere2");
////                        SunmiPrintHelper.getInstance().printText(text1, 1, 2, 3, "4");
//                        printer.printFormattedTextAndOpenCashBox(text1, 5);
//                        SunmiPrintHelper.getInstance().openCashBox();
////                        getCashBoxOpen();
//
////                        printer.printFormattedText(text1);
////                        printer.printFormattedTextAndOpenCashBox(text1, 40);
//                    }

                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    public String getPrinter() {
        BluetoothConnection[] bluetoothDevicesList = (new BluetoothConnections()).getList();

        if (bluetoothDevicesList == null) {
            return null;
        }

        int i = 0;
        BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
        for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
            BluetoothDevice device = bluetoothConnection.getDevice();
            Log.i("BLUETOOTH DEVICE", device.getName());
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public BluetoothConnection[] getList() {
        BluetoothConnection[] bluetoothDevicesList = new BluetoothConnections().getList();

        if (bluetoothDevicesList == null) {
            return null;
        }

        int i = 0;
        BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
        for (BluetoothConnection bluetoothConnection : bluetoothDevicesList) {
            BluetoothDevice device = bluetoothConnection.getDevice();
            if (device.getName().equals("InnerPrinter")) {
                printersTmp[i++] = new BluetoothConnection(device);
            }
        }
        BluetoothConnection[] bluetoothPrinters = new BluetoothConnection[i];
        System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);
        return bluetoothPrinters;
    }

    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (NewPosMainActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {
//                             printIt(new UsbConnection(usbManager, usbDevice));

                            try {
                                new AsyncUsbEscPosPrint(context).execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));
                            } catch (RuntimeException e) {

                            }
                        }
                    }
                }
            }
        }
    };

    public void printUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
        if (usbConnection != null && usbManager != null) {
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(NewPosMainActivity.ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(NewPosMainActivity.ACTION_USB_PERMISSION);
            registerReceiver(this.usbReceiver, filter);
            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 60f, 42);
        return printer.setTextToPrint("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_white, DisplayMetrics.DENSITY_HIGH)) + "</img>\n" + printText);

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

    //add admin options

    AlertDialog categoryListDialog;

    public void addNewCategoryPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_new_category, null);
        builder.setView(dialogView);
        categoryListDialog = builder.create();
        categoryListDialog.show();
        categoryListDialog.setCanceledOnTouchOutside(false);
        categoryListDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(categoryListDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.80f);
        int dialogWindowHeight = (int) (displayWidth * 0.60f);

//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
//        int dialogWindowWidth = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        categoryListDialog.getWindow().setAttributes(layoutParams);
        categoryListDialog.getWindow().setGravity(Gravity.CENTER);
        categoryListDialog.setCanceledOnTouchOutside(false);

        final AppCompatImageView closeIcon = categoryListDialog.findViewById(R.id.closeIcon);
        final AppCompatButton newItemButton = categoryListDialog.findViewById(R.id.newItemButton);
        final AppCompatButton itemListButton = categoryListDialog.findViewById(R.id.itemListButton);
        categoryListRecyclerViewLayout = categoryListDialog.findViewById(R.id.categoryListRecyclerViewLayout);
        AddedcategoryListRecyclerView = categoryListDialog.findViewById(R.id.categoryListRecyclerView);
        addNewCategoryLayout = categoryListDialog.findViewById(R.id.addNewCategoryLayout);
        errorText = categoryListDialog.findViewById(R.id.errorText);
        backArrowIv = categoryListDialog.findViewById(R.id.backArrowIv);
        final AppCompatButton addMainProductBtn = categoryListDialog.findViewById(R.id.addMainProductBtn);
        final AppCompatTextView chooseFileTv = categoryListDialog.findViewById(R.id.chooseFileTv);
        categoryimageView = categoryListDialog.findViewById(R.id.imageView);
        categoryNameEt = categoryListDialog.findViewById(R.id.categoryNameEt);
        descriptionEt = categoryListDialog.findViewById(R.id.descriptionEt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
        AddedcategoryListRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        AdmingetHomeCategory();
//        addNewSubCategoryPopup();

        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editId = "0";

                descriptionEt.setText("");

                addNewCategoryLayout.setVisibility(View.VISIBLE);
                categoryListRecyclerViewLayout.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
            }
        });

        chooseFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To pick photo from gallery

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

            }
        });

        itemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCategoryLayout.setVisibility(View.GONE);
                categoryListRecyclerViewLayout.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);
                AdmingetHomeCategory();
            }
        });

        backArrowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryListDialog.dismiss();
                moreLayoutPopup();
            }
        });

        addMainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyBoard();
                admincategoryName = categoryNameEt.getText().toString();
                if (categoryNameEt.getText().toString().length() != 0) {
                    addCategoryResponse();
                } else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Please Enter Category Name");
                }

            }
        });


        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryListDialog.dismiss();
                moreLayoutPopup();
            }
        });

    }

    private void AdmingetHomeCategory() {

        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setOutlet_id(outlet_id);
        request.setAll_list("1");

        retrofit2.Call<CategoryResponse> call = apiService.categoryList("1234", request);

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, Response<CategoryResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CategoryResponse user = response.body();

                    Log.d("responseee", new Gson().toJson(response));

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        categoryList = user.productResults;
                        items = new ArrayList<String>();
                        items.add("Select Category");

                        for (int i = 0; i < categoryList.size(); i++) {
                            items.add(categoryList.get(i).category_name);
                            catIdList.add(categoryList.get(i).id);
                            if (i == 0) {
                                catId = categoryList.get(i).id;
                                Log.d("kjdfkd", catId);
//                                getHomeSubCategory();
                            }
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                addedhomeCatRecyclerAdapter = new AddedhomeCatRecyclerAdapter(NewPosMainActivity.this, categoryList);
                                AddedcategoryListRecyclerView.setAdapter(addedhomeCatRecyclerAdapter);
                                AddedcategoryListRecyclerView.setNestedScrollingEnabled(false);

//                                AdmincategorySpinnerData();


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
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class AddedhomeCatRecyclerAdapter extends RecyclerView.Adapter<AddedhomeCatRecyclerAdapter.ViewHolder> {

        Context activity;
        List<CategoryResponse.ProductListResponse> data;
        CategoryResponse.ProductListResponse masterData;
        int row_index = 0;

        public AddedhomeCatRecyclerAdapter(Activity activity2,
                                           List<CategoryResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.admin_category_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            Glide.with(getApplicationContext()).load(masterData.getCat_image())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.categoryItemIv);

            holder.categoryItemCodeTv.setText(masterData.getId());
            holder.categoryItemNameTv.setText(masterData.getCategory_name());
            holder.categoryItemDescTv.setText(masterData.getDescription());

            String status = masterData.getStatus();

            if (status.equalsIgnoreCase("1")) {
                holder.categoryItemStatusTv.setText("Active");
                holder.categoryItemStatusTv.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                holder.categoryItemStatusTv.setText("InActive");
                holder.categoryItemStatusTv.setBackgroundColor(getResources().getColor(R.color.redColor));
            }

            holder.categoryItemActionIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    masterData = data.get(position);
                    addNewCategoryLayout.setVisibility(View.VISIBLE);
                    categoryListRecyclerViewLayout.setVisibility(View.GONE);

                    editId = masterData.getId();

                    categoryNameEt.setText(masterData.getCategory_name());
                    descriptionEt.setText(masterData.getDescription());

                    Glide.with(getApplicationContext()).load(masterData.getCat_image())
                            .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(categoryimageView);

                    convertUrlToBase64(masterData.getCat_image());

                }
            });


            holder.categoryItemStatusTv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData = data.get(position);

                            if (masterData.getStatus().equalsIgnoreCase("1")) {
                                changeStatus = "0";
                            } else {

                                changeStatus = "1";

                            }

                            changeTable = "2";
                            changeStatusId = masterData.getId();
                            changeStatusResponse();


                        }
                    });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView categoryItemCodeTv, categoryItemNameTv, categoryItemDescTv, categoryItemStatusTv;
            AppCompatImageView categoryItemIv, categoryItemActionIv;
            LinearLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.categoryItemCodeTv = itemView.findViewById(R.id.categoryItemCodeTv);
                this.categoryItemNameTv = itemView.findViewById(R.id.categoryItemNameTv);
                this.categoryItemDescTv = itemView.findViewById(R.id.categoryItemDescTv);
                this.categoryItemStatusTv = itemView.findViewById(R.id.categoryItemStatusTv);
                this.categoryItemIv = itemView.findViewById(R.id.categoryItemIv);
                this.categoryItemActionIv = itemView.findViewById(R.id.categoryItemActionIv);

            }

        }

    }

    private void addCategoryResponse() {
        showProgressDialog();
        AddCategoryRequest request = new AddCategoryRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setCategory_name(admincategoryName);
        request.setProduct_img("data:image/png;base64," + encodedImage);

        if (editId.equalsIgnoreCase("0")) {

        } else {
            request.setId(editId);
        }

        retrofit2.Call<AddCategoryResponse> call = apiService.addCategoryData("1234", request);
//        Log.d("addCatrequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<AddCategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddCategoryResponse> call, Response<AddCategoryResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    AddCategoryResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("savedInvoice", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // showLongSnackBar(message + "," + "Add Another Banner");
//                                errorText.setVisibility(View.VISIBLE);
//                                errorText.setText(message);
                                categoryNameEt.getText().clear();
                                errorText.setVisibility(View.GONE);
                                infoPopup(message);

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
            public void onFailure(Call<AddCategoryResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    private void changeStatusResponse() {
        showProgressDialog();
        ChangeStatusRequest request = new ChangeStatusRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);
        request.setStatus(changeStatus);
        request.setId(changeStatusId);
        request.setChange_table(changeTable);

        retrofit2.Call<TaxResponse> call = apiService.changeStatusData("1234", request);
//        Log.d("changestatusrequest", "response 12: " + new Gson().toJson(request));
        call.enqueue(new Callback<TaxResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TaxResponse> call, Response<TaxResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    TaxResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        Log.d("savedInvoice", "response 12: " + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLongSnackBar(message);

                                if (changeTable.equalsIgnoreCase("2")) {
                                    AdmingetHomeCategory();
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
            public void onFailure(Call<TaxResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    AlertDialog productListDialog;

    public void AddAdminProductPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.admin_add_products, null);
        builder.setView(dialogView);
        productListDialog = builder.create();
        if (!productListDialog.isShowing() && productListDialog != null)
            productListDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(productListDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.70f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        productListDialog.getWindow().setAttributes(layoutParams);
        productListDialog.getWindow().setGravity(Gravity.RIGHT);

        closeIcon = (AppCompatImageView) productListDialog.findViewById(R.id.closeIcon);
        final AppCompatButton newItemButton = productListDialog.findViewById(R.id.newItemButton);
        final AppCompatButton itemListButton = productListDialog.findViewById(R.id.itemListButton);

        categoryListRecyclerViewLayout = productListDialog.findViewById(R.id.categoryListRecyclerViewLayout);
        addedProductListRecyclerView = productListDialog.findViewById(R.id.addedProductListRecyclerView);
        addNewCategoryLayout = productListDialog.findViewById(R.id.addNewCategoryLayout);

        itemCodeEt = (AppCompatEditText) productListDialog.findViewById(R.id.itemCodeEt);
        itemNameEt = (AppCompatEditText) productListDialog.findViewById(R.id.itemNameEt);
        stockEt = (AppCompatEditText) productListDialog.findViewById(R.id.stockEt);
        taxTypeSpinner = (AppCompatSpinner) productListDialog.findViewById(R.id.taxTypeSpinner);

        salesPriceEt = (AppCompatEditText) productListDialog.findViewById(R.id.salesPriceEt);
        errorText = (AppCompatTextView) productListDialog.findViewById(R.id.errorText);

        addcategorySpinner = (AppCompatSpinner) productListDialog.findViewById(R.id.addcategorySpinner);

        addMainProductBtn = (AppCompatButton) productListDialog.findViewById(R.id.addMainProductBtn);

        AppCompatImageView closeIcon = (AppCompatImageView) productListDialog.findViewById(R.id.closeIcon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewPosMainActivity.this, LinearLayoutManager.VERTICAL, false);
        addedProductListRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


//        taxTypeSpinner.setOnItemSelectedListener(this);
//
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, taxTypeArray);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////Setting the ArrayAdapter data on the Spinner
//        taxTypeSpinner.setAdapter(aa);

        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewCategoryLayout.setVisibility(View.VISIBLE);
                categoryListRecyclerViewLayout.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
            }
        });

        itemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCategoryLayout.setVisibility(View.GONE);
                categoryListRecyclerViewLayout.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);
                AdmingetProductListList();
            }
        });

//        addMainProductBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                addProducts();
//            }
//        });


        getCategories();
        AdmingetProductListList();

        addMainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemCode = itemCodeEt.getText().toString();
                itemName = itemNameEt.getText().toString();
                //    stock = stockEt.getText().toString();
//                unitName = unitEt.getText().toString();

//                purchasePrice = purchasePriceEt.getText().toString();
                salePrice = salesPriceEt.getText().toString();
//                supplierTax = supplierTaxSpinner.getSelectedItem().toString();

                if (addMainProductBtn.getText().toString().equalsIgnoreCase("Add Product")) {
                    id = "0";
                }

                if (itemCodeEt.getText().toString().length() == 0) {
                    if (itemNameEt.getText().toString().length() != 0) {
                        if (!addProductCatName.equalsIgnoreCase("Select Category")) {
                            if (salesPriceEt.getText().toString().length() != 0) {

                                addAdminProductResponse();

                            } else {
                                errorText.setText("Enter Sales Price");
                                errorText.setVisibility(View.VISIBLE);
                            }

                        } else {
                            errorText.setText("Enter Category");
                            errorText.setVisibility(View.VISIBLE);
                        }


                    } else {
                        errorText.setText("Enter Item Name");
                        errorText.setVisibility(View.VISIBLE);
                    }
                } else {
                    errorText.setText("Enter Item Code");
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListDialog.dismiss();
            }
        });

    }

    /// adding products

    private void AdmingetProductListList() {

        ProductsRequest request = new ProductsRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);
        request.setAll_list("1");

//        Log.d("SubRespoReq", new Gson().toJson(request));

        Call<ProductsResponse> call = apiService.productList("1234", request);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    ProductsResponse user = response.body();
//                    Log.d("SubRespo", new Gson().toJson(response));
                    subCatId = "0";
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        productsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                adminProductRecyclerAdapter = new AdminProductRecyclerAdapter(NewPosMainActivity.this, productsList);
                                addedProductListRecyclerView.setAdapter(adminProductRecyclerAdapter);
                                addedProductListRecyclerView.setNestedScrollingEnabled(false);

                            }
                        });

                    } else if (success.equalsIgnoreCase("2")) {
//                        showLongSnackBar(message);

//                        subCategoryListRecyclerView.setVisibility(View.GONE);
//                        getProductDetails();
                    } else {

                    }

                } else {
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

            }
        });

    }

    public class AdminProductRecyclerAdapter extends RecyclerView.Adapter<AdminProductRecyclerAdapter.ViewHolder> {

        Context activity;
        List<ProductsResponse.ProductListResponse> data;
        ProductsResponse.ProductListResponse masterData;
        int row_index = 0;

        public AdminProductRecyclerAdapter(Activity activity2,
                                           List<ProductsResponse.ProductListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.admin_product_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData = data.get(position);

            holder.adminProductNameTv.setText(masterData.getItem_name());
//            holder.adminProductBrandTv.setText(masterData.getBrand_name());
            holder.adminProductCatTv.setText(masterData.getCategory_name());
//            holder.adminProductSubCatTv.setText(masterData.getSubcategory_id());
//            holder.adminProductBarcodeTv.setText(masterData.getCustom_barcode());
            holder.adminProductPurchasePriceTv.setText(masterData.getSales_price());
            holder.adminProductQtyTv.setText(masterData.getStock());
            holder.adminProductSalePriceTv.setText(masterData.getSales_price());

//            Log.d("posssssss", "" + position);

//            String status = masterData.getStatus();
//
//            if (status.equalsIgnoreCase("1")) {
//                holder.adminProductStatusTv.setText("Active");
//                holder.adminProductStatusTv.setBackgroundColor(getResources().getColor(R.color.green));
//            } else {
//                holder.adminProductStatusTv.setText("InActive");
//                holder.adminProductStatusTv.setBackgroundColor(getResources().getColor(R.color.darkRedColor));
//            }


            holder.adminProductStatusTv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            masterData = data.get(position);

//                            if (masterData.getStatus().equalsIgnoreCase("1")) {
//                                changeStatus = "0";
//                            } else {
//
//                                changeStatus = "1";
//
//                            }
//
//                            changeTable = "1";
//                            changeStatusId = masterData.getId();
//                            changeStatusResponse();

                        }
                    });

            holder.adminSupplierItemActionIv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addMainProductBtn.setText("Update");

                            masterData = data.get(position);

                            addNewCategoryLayout.setVisibility(View.VISIBLE);
                            categoryListRecyclerViewLayout.setVisibility(View.GONE);

//                            Log.d("selectcat", masterData.getCategory_name());

//                            categoriesNameList.clear();

                            itemNameEt.setText(masterData.getItem_name());
                            itemCodeEt.setText(masterData.getItem_code());
                            salesPriceEt.setText(masterData.getSales_price());

                            addcategorySpinner.setSelection(aa.getPosition(masterData.category_name));


                            id = masterData.getId();

                        }
                    });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView adminProductNameTv, adminProductBrandTv, adminProductCatTv, adminProductSubCatTv, adminProductBarcodeTv,
                    adminProductPurchasePriceTv, adminProductQtyTv, adminProductSalePriceTv, adminProductTaxTv, adminProductStatusTv;
            AppCompatImageView adminProductIv, adminSupplierItemActionIv;
            LinearLayout bgLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.adminProductIv = itemView.findViewById(R.id.adminProductIv);
                this.adminProductNameTv = itemView.findViewById(R.id.adminProductNameTv);
                this.adminProductBrandTv = itemView.findViewById(R.id.adminProductBrandTv);
                this.adminProductCatTv = itemView.findViewById(R.id.adminProductCatTv);
                this.adminProductSubCatTv = itemView.findViewById(R.id.adminProductSubCatTv);
                this.adminProductBarcodeTv = itemView.findViewById(R.id.adminProductBarcodeTv);
                this.adminProductPurchasePriceTv = itemView.findViewById(R.id.adminProductPurchasePriceTv);
                this.adminProductQtyTv = itemView.findViewById(R.id.adminProductQtyTv);
                this.adminProductSalePriceTv = itemView.findViewById(R.id.adminProductSalePriceTv);
                this.adminProductTaxTv = itemView.findViewById(R.id.adminProductTaxTv);
                this.adminProductStatusTv = itemView.findViewById(R.id.adminProductStatusTv);
                this.adminSupplierItemActionIv = itemView.findViewById(R.id.adminSupplierItemActionIv);

            }

        }

    }

    private void getCategories() {
        showProgressDialog();

        showProgressDialog();
        CategoryRequest request = new CategoryRequest();
        request.setOutlet_id(outlet_id);
        request.setAll_list("1");
        request.setUser_id(userId);

        retrofit2.Call<CategoryResponse> call = apiService.addProductCategory("1234", request);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    CategoryResponse user = response.body();
                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        categoriesNameList.clear();
                        categoryIdList.clear();

                        categoriesNameList.add("Select Category");
                        categoryIdList.add("Select Category");

//                        Log.d("cateResp", "response 12: " + new Gson().toJson(response.body()));
                        categoryList = user.productResults;
                        for (int i = 0; i < categoryList.size(); i++) {
                            categoriesNameList.add(categoryList.get(i).category_name);
                            categoryIdList.add(categoryList.get(i).id);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addProductcategorySpinnerData();
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
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public void addProductcategorySpinnerData() {

        hideKeyBoard();

        aa = new ArrayAdapter(this, R.layout.activity_simple_spinner, categoriesNameList);
        aa.setDropDownViewResource(R.layout.activity_simple_spinner);
        addcategorySpinner.setAdapter(aa);
        addcategorySpinner.setSelection(aa.getPosition(String.valueOf(categoryList)));
        addcategorySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                return false;
            }
        });

        addcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addProductCatName = parent.getItemAtPosition(position).toString();
                addProductCatId = categoryIdList.get(position);

//                Log.d("catidddd", addProductCatId);

                retrofitService();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideKeyBoard();
            }
        });

    }

    private void addAdminProductResponse() {

        showProgressDialog();

        AddProductRequest request = new AddProductRequest();
        request.setOutlet_id(outlet_id);
        request.setUser_id(userId);

        request.setCustom_barcode(itemCode);
        request.setItem_name(itemName);
        request.setStock(stock);
        request.setCategory_id(addProductCatId);
        request.setUnit_id(unitName);
        request.setExpire_date(expDate);
        request.setPurchase_price(purchasePrice);
        request.setSales_price(salePrice);
        request.setSupplier_id(supplierId);
        request.setSupplier_price(purchasePrice);
        request.setTax_type(taxType);

        if (id.equalsIgnoreCase("0")) {

        } else {
            request.setId(id);
        }

        Call<AddProductResponse> call = apiService.addMainProductData("1234", request);
//        Log.d("requestsss", new Gson().toJson(request));
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    AddProductResponse user = response.body();
                    final String message = user.getMessage();
                    String success = user.getResponseCode();

                    //  Log.d("pppp", new Gson().toJson(response.body()));

                    if (success.equalsIgnoreCase("0")) {

                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {

                                infoPopup(message);
//                                alertDialog.dismiss();
                                addNewCategoryLayout.setVisibility(View.GONE);
                                categoryListRecyclerViewLayout.setVisibility(View.VISIBLE);
                                AdmingetProductListList();
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
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

//        Log.d("dfdfdfdf", encodedImage);

        return encodedImage;
    }

    public String convertUrlToBase64(String url) {
        java.net.URL newurl;
        Bitmap bitmap;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Log.d("imageeebit", "" + imageBitmap);
                    categoryimageView.setImageBitmap(imageBitmap);

                    Bitmap immagex = imageBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    Log.d("rrrrr", encodedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    categoryimageView.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void customerDetailPopup() {

        customerDetailsLayout.setVisibility(View.VISIBLE);
        categoriesLayout.setVisibility(View.GONE);
        productsRecyclerView.setVisibility(View.GONE);
        addOnsDataLayout.setVisibility(View.GONE);

        customerNameEt = findViewById(R.id.payNowcustomerNameEt);
        customerMobileEt = findViewById(R.id.payNowcustomerMobileEt);
        addNoteEt = findViewById(R.id.addNoteEt);

        customerNameEt.setText("");
        customerMobileEt.setText("");
        addNoteEt.setText("");

        newtablesRecyclerView = findViewById(R.id.payNowtablesRecyclerView);
        final AppCompatTextView addCustomerSaveTv = findViewById(R.id.addCustomerSaveTv);

        final AppCompatImageView closeTextView = findViewById(R.id.closeIcon);

        final RelativeLayout takeAwayLayout = findViewById(R.id.takeAwayLayout);
        final RelativeLayout dineInLayout = findViewById(R.id.dineInLayout);

        final AppCompatTextView takeAwayTv = findViewById(R.id.takeAwayTv);
        final AppCompatTextView dineInTv = findViewById(R.id.dineInTv);

        GridLayoutManager addKotLayoutManager = new GridLayoutManager(NewPosMainActivity.this, 12);
        newtablesRecyclerView.setLayoutManager(addKotLayoutManager); // set LayoutManager to RecyclerView

//        orderType = "1";

        takeAwayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderType = "1";
                tableNo = "0";
                newtablesRecyclerView.setVisibility(View.GONE);
                addNoteEt.setVisibility(View.GONE);

                takeAwayLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_light_blue_bg_filled));
                dineInLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_white_bg_filled));

                takeAwayTv.setTextColor(getResources().getColor(R.color.colorWhite));
                dineInTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));


            }
        });

        dineInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderType = "0";
                addNoteEt.setText("");
                newtablesRecyclerView.setVisibility(View.GONE);
                getLatestTablesList();

                addNoteEt.setVisibility(View.VISIBLE);
                takeAwayLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_white_bg_filled));
                dineInLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_light_blue_bg_filled));

                takeAwayTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                dineInTv.setTextColor(getResources().getColor(R.color.colorWhite));


            }
        });

        addCustomerSaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customerName = customerNameEt.getText().toString();
                mobileNo = customerMobileEt.getText().toString();
                tableNo = addNoteEt.getText().toString();

//                Log.d("ordertypeee", tableNo);

                if (orderType == "-1") {

                    showShortSnackBar("Please Select OrderType");

                } else {

                    if (orderType == "1") {

                        Intent intent = new Intent(getApplicationContext(), NewPaymentActivity.class);
                        intent.putExtra("total", db.getTotalPrice());
                        intent.putExtra("totalPayable", totalAmount);
                        intent.putExtra("qty", products.size());
                        intent.putExtra("discount", disc);
                        intent.putExtra("orderId", orderNo);
                        intent.putExtra("updateData", updateData);
                        intent.putExtra("priceId", priceId);
                        intent.putExtra("customerName", customerName);
                        intent.putExtra("mobileNo", mobileNo);
                        intent.putExtra("tableNo", "0");
                        intent.putExtra("oredrType", orderType);
                        intent.putExtra("addNote", addNote);
//                        Log.d("tableNotableNo", tableNo);
                        startActivity(intent);
                    } else if (orderType == "0") {

                        if (addNoteEt.getText().toString().length() != 0) {
                            Intent intent = new Intent(getApplicationContext(), NewPaymentActivity.class);
                            intent.putExtra("total", db.getTotalPrice());
                            intent.putExtra("totalPayable", totalAmount);
                            intent.putExtra("qty", products.size());
                            intent.putExtra("discount", disc);
                            intent.putExtra("orderId", orderNo);
                            intent.putExtra("updateData", updateData);
                            intent.putExtra("priceId", priceId);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("mobileNo", mobileNo);
                            intent.putExtra("tableNo", tableNo);
                            intent.putExtra("oredrType", orderType);
//                    intent.putExtra("addNote", addNote);
//                            Log.d("tableNotableNo", tableNo);
                            startActivity(intent);

                        } else {
                            showShortSnackBar("Please Enter Table Number");

                        }

                    }
                }


            }
        });


    }

    private void getLatestTablesList() {

        showProgressDialog();

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<NewTableListResponse> call = apiService.newTableListData("1234", request);

        Log.e("rerere", new Gson().toJson(request));

        call.enqueue(new Callback<NewTableListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<NewTableListResponse> call, Response<NewTableListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    NewTableListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        tablesList.clear();
//                        Log.d("tablecateResp", "response 12: " + new Gson().toJson(response.body()));
                        newareaTablesList = user.getAreaResults();

                        //tableNo = newareaTablesList.get(0).getTable_name();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                latestTablesRecyclerAdapter = new LatestTablesRecyclerAdapter(NewPosMainActivity.this, newareaTablesList);
                                newtablesRecyclerView.setAdapter(latestTablesRecyclerAdapter);
                                newtablesRecyclerView.setNestedScrollingEnabled(false);
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
            public void onFailure(Call<NewTableListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class LatestTablesRecyclerAdapter extends RecyclerView.Adapter<LatestTablesRecyclerAdapter.ViewHolder> {

        Context activity;

        List<NewTableListResponse.AreaListResponse> data2;
        NewTableListResponse.AreaListResponse masterData2;
        Boolean selected;
        int row_index = -1;

        public LatestTablesRecyclerAdapter(Activity activity2,
                                           List<NewTableListResponse.AreaListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.new_table_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.tableNumberTv.setText(masterData2.getTable_name());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
//                            tableNo = masterData2.getId();
                            tableNo = masterData2.getId();

//                            Log.d("tabeNo", tableNo);
                            row_index = position;
                            holder.tableNumberTv.setTextColor(R.color.colorWhite);
                            latestTablesRecyclerAdapter.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(), tableNo, Toast.LENGTH_SHORT).show();
                        }
                    });


            if (row_index == position) {
                holder.tableItemBgLayout.setBackgroundColor(Color.parseColor("#1C60AE"));
            } else {
                holder.tableItemBgLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            }

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView tableNumberTv;
            RelativeLayout tableItemBgLayout;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.tableNumberTv = itemView.findViewById(R.id.tableNumberTv);
                this.tableItemBgLayout = itemView.findViewById(R.id.tableItemBgLayout);

            }

        }

    }

    private void getGroupListData() {

        showProgressDialog();

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<GroupListResponse> call = apiService.groupListData("1234", request);

        Log.e("grouppprerere", new Gson().toJson(request));

        call.enqueue(new Callback<GroupListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GroupListResponse> call, Response<GroupListResponse> response) {

                if (response.isSuccessful()) {
//                    Log.e("grouppprersponse", new Gson().toJson(response));
                    hideProgressDialog();
                    GroupListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        groupListNew.clear();

//                        Log.d("tablecateResp", "response 12: " + new Gson().toJson(response.body()));
                        groupListData = user.getProductResults();

//                        for (int i = 0; i < groupListData.size(); i++) {
//                            groupListNew.add(groupListData.get(i).getId());
//
//                            Log.d("groupListNew", "" + groupListNew);
//
//                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                Log.d("grouplistdata", "" + groupListData.get(0));


//                                ArrayList<String> a1 = new ArrayList<String>(groupListData);

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
            public void onFailure(Call<GroupListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    AlertDialog usersPopup;

    public void userDetailPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_users_popup, null);
        builder.setView(dialogView);
        usersPopup = builder.create();
        usersPopup.show();
        usersPopup.setCanceledOnTouchOutside(false);
        usersPopup.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(usersPopup.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.70f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        usersPopup.getWindow().setAttributes(layoutParams);

        userNameTv = usersPopup.findViewById(R.id.userNameTv);
        userNameEt = usersPopup.findViewById(R.id.userInnerPasswordEt);
        userRecyclerView = usersPopup.findViewById(R.id.userRecyclerView);
        loginBtn = usersPopup.findViewById(R.id.loginBtn);
        logoutBtn = usersPopup.findViewById(R.id.logoutBtn);

        oneLayout = usersPopup.findViewById(R.id.oneLayout);
        twoLayout = usersPopup.findViewById(R.id.twoLayout);
        threeLayout = usersPopup.findViewById(R.id.threeLayout);
        fourLayout = usersPopup.findViewById(R.id.fourLayout);
        fiveLayout = usersPopup.findViewById(R.id.fiveLayout);
        sixLayout = usersPopup.findViewById(R.id.sixLayout);
        sevenLayout = usersPopup.findViewById(R.id.sevenLayout);
        eightLayout = usersPopup.findViewById(R.id.eightLayout);
        nineLayout = usersPopup.findViewById(R.id.nineLayout);
        zeroLayout = usersPopup.findViewById(R.id.zeroLayout);
        innerLoginCloseIv = usersPopup.findViewById(R.id.innerLoginCloseIv);
        clearTextLayout = usersPopup.findViewById(R.id.clearTextLayout);

        oneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "1");
            }
        });

        twoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "2");
            }
        });

        threeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "3");
            }
        });

        fourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "4");
            }
        });

        fiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "5");
            }
        });

        sixLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "6");
            }
        });

        sevenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "7");
            }
        });

        eightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "8");
            }
        });

        nineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "9");
            }
        });

        zeroLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText(userNameEt.getText() + "0");
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = userNameTv.getText().toString();
                password = userNameEt.getText().toString();

                if (userNameTv.getText().toString().length() != 0) {
                    if (userNameEt.getText().toString().length() != 0) {
                        getLoginResponse();

                    } else {
                        showShortSnackBar("Please Enter Password");
                    }
                } else {
                    showShortSnackBar("Please Select User");
                }


            }
        });

        clearTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEt.setText("");

            }
        });

        innerLoginCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersPopup.dismiss();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });

        GridLayoutManager adduserListManager = new GridLayoutManager(NewPosMainActivity.this, 3);
        userRecyclerView.setLayoutManager(adduserListManager); // set LayoutManager to RecyclerView

        getLatestUsersList();


    }

    private void getLatestUsersList() {

        showProgressDialog();

        CashInRequest request = new CashInRequest();
        request.setOutlet_id(outlet_id);

        retrofit2.Call<StoreUsersListResponse> call = apiService.storeUserData("1234", request);

//        Log.e("rerere", new Gson().toJson(request));

        call.enqueue(new Callback<StoreUsersListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<StoreUsersListResponse> call, Response<StoreUsersListResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    StoreUsersListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        tablesList.clear();
//                        Log.d("usersdataa", "response 12: " + new Gson().toJson(response.body()));
                        storeUsersListData = user.getProductResults();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                usersDetailsAdapterRecyclerAdapter = new UsersDetailsAdapterRecyclerAdapter(NewPosMainActivity.this, storeUsersListData);
                                userRecyclerView.setAdapter(usersDetailsAdapterRecyclerAdapter);
                                userRecyclerView.setNestedScrollingEnabled(false);
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
            public void onFailure(Call<StoreUsersListResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class UsersDetailsAdapterRecyclerAdapter extends RecyclerView.Adapter<UsersDetailsAdapterRecyclerAdapter.ViewHolder> {

        Context activity;

        List<StoreUsersListResponse.UsersListResponse> data2;
        StoreUsersListResponse.UsersListResponse masterData2;
        Boolean selected;
        int row_index;

        public UsersDetailsAdapterRecyclerAdapter(Activity activity2,
                                                  List<StoreUsersListResponse.UsersListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.users_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.userNameTv.setText(masterData2.getUsername());

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData2 = data2.get(position);
//                            tableNo = masterData2.getId();
                            loginUserName = masterData2.getUsername();
                            row_index = position;
                            usersDetailsAdapterRecyclerAdapter.notifyDataSetChanged();

                            userNameTv.setText(loginUserName);
                        }
                    });


            if (row_index == position) {
                holder.userBgLayout.setBackgroundColor(Color.parseColor("#DC143C"));
            } else {
                holder.userBgLayout.setBackgroundColor(Color.parseColor("#1C60AE"));
            }

        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView userNameTv;
            RelativeLayout userBgLayout;


            public ViewHolder(final View itemView) {
                super(itemView);

                this.userNameTv = itemView.findViewById(R.id.userNameTv);
                this.userBgLayout = itemView.findViewById(R.id.userBgLayout);

            }

        }

    }

    private void getLoginResponse() {

        outlet_id = user1.get(SessionManager.KEY_OUTLET_ID);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setOutlet_id(outlet_id);

//        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<LoginResponse> call = apiService.userLoginData("1234", request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {

//                Log.d("InnerLogresponseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    LoginResponse user = response.body();

                    final String message = user.getMessage();
                    String success = user.getResponseCode();

//                    Log.d("InnerLogresponseee1", success);

                    if (success.equalsIgnoreCase("0")) {

                        loginList = user.userResults;

//                        Log.d("InnerLogresponseee2", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                userId = loginList.getInv_userid();
                                name = loginList.getInv_username();
                                roleId = loginList.getRole_id();
                                roleName = loginList.getRole_name();
                                sessionManager.storeLoginDetails(name, userId);
                                usersPopup.dismiss();

//                                CashInPopup();
                                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                    } else {
                        infoPopup(message);
                    }

                } else {
//                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                infoPopup(message);

            }
        });

    }

    AlertDialog addNoteDialog;

    public void addNotePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addons_note_popup, null);
        builder.setView(dialogView);
        final AlertDialog addNoteDialog = builder.create();
        addNoteDialog.setCanceledOnTouchOutside(false);
        addNoteDialog.setCancelable(false);
        addNoteDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(addNoteDialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        addNoteDialog.getWindow().setAttributes(layoutParams);

        final AppCompatEditText noteEt = addNoteDialog.findViewById(R.id.noteEt);
        final AppCompatImageView closeAddNoteIv = addNoteDialog.findViewById(R.id.closeAddNoteIv);
        AppCompatButton submitNoteBtn = addNoteDialog.findViewById(R.id.submitNoteBtn);

        closeAddNoteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteDialog.dismiss();
                categoriesLayout.setVisibility(View.GONE);
//                addOnsDataLayout.setVisibility(View.VISIBLE);
            }
        });

        submitNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNote = noteEt.getText().toString();

                if (noteEt.getText().toString().length() != 0) {
                    count = 0;
                    count = count + 1;
                    db.updateNote(product_id, "" + rowid, addNote);
//                    Log.d("addingdb", "" + productId + "," + kotId + "," + productName + "," + "1" + "," + addNote + "," + productPrice);
                    kotId = kotId + 1;
//                    Log.d("kottttt1", "" + kotId);
                    updateList();
                    addNoteDialog.dismiss();
                } else {
                    showShortSnackBar("Please Add Note");
                }


//                if (addOnsList.size() <= 0) {
//                    categoriesLayout.setVisibility(View.VISIBLE);
//                    addOnsDataLayout.setVisibility(View.GONE);
//                } else {
//                    categoriesLayout.setVisibility(View.GONE);
////                    addOnsDataLayout.setVisibility(View.VISIBLE);
//                }


            }
        });
    }

    private void getCashBoxOpen() {

        EscPosPrinter printer = null;
        BluetoothConnection[] bluetoothDevicesList = new BluetoothConnections().getList();
//        Log.d("devicelist", "" + bluetoothDevicesList.length);
        if (bluetoothDevicesList == null) {
            Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
                printer.printFormattedTextAndOpenCashBox(
//                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, getApplicationContext().getResources().getDrawableForDensity(R.mipmap.spicy_bw, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                        "[L]\n" +
                                "[L]\n", 5
                );

//            autoPrintUsb();
//            doPrint();
//                Log.d("openinggg", "openingg");
                printer.disconnectPrinter();
            } catch (EscPosConnectionException | EscPosParserException | EscPosEncodingException | EscPosBarcodeException e) {
                e.printStackTrace();
            }
        }
//
    }

    public void refundReasonsPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.refund_reasons_layout, null);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.70f);
        int dialogWindowHeight = (int) (displayHeight * 0.70f);
//        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.getWindow().setGravity(Gravity.RIGHT);

        AppCompatImageView closeIcon = findViewById(R.id.closeIcon);

        closeIcon = dialogView.findViewById(R.id.closeIcon);
        closeAddProductBtn = dialogView.findViewById(R.id.closeAddProductBtn);

        returnOrderIdEt = dialogView.findViewById(R.id.returnOrderIdEt);
        returnOrderIdSubmitBtnTv = dialogView.findViewById(R.id.returnOrderIdSubmitBtnTv);
        returnSubmit = dialogView.findViewById(R.id.returnSubmit);

        refundReasonsRecyclerview = dialogView.findViewById(R.id.refundReasonsRecyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        refundReasonsRecyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        getOrderIdsForRefund();
        getRefundReasonsList();

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        returnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                returnorderNo = returnOrderIdEt.getText().toString();

                if (returnOrderIdEt.getText().toString().length() != 0) {
                    sendRefundCartData();
                    dialog.dismiss();

                } else {
                    showShortSnackBar("Enter Order No Above");
                }

            }
        });

    }

    private void getOrderIdsForRefund() {

        OrdersRequest request = new OrdersRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

//        Log.d("refundordersRequest", request + new Gson().toJson(request));
        retrofit2.Call<LastInvoiceListResponse> call = apiService.getLastInvoiceListResponse("1234", request);

        call.enqueue(new Callback<LastInvoiceListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LastInvoiceListResponse> call, Response<LastInvoiceListResponse> response) {

                if (response.isSuccessful()) {

                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    LastInvoiceListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        productsRecyclerView.setVisibility(View.VISIBLE);

//                        Log.d("refundordersResp", "response 1112: " + new Gson().toJson(response.body()));
                        lastInvoiceList = user.productResults;

                        if (user.productResults != null && user.productResults.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    CustomerAdapter customerAdapter = new CustomerAdapter(getApplicationContext(), R.layout.activity_simple_spinner, lastInvoiceList);
                                    returnOrderIdEt.setAdapter(customerAdapter);
                                }
                            });
                        } else {

                        }

                    } else {

                    }

                } else {
                    mProgressBar.setVisibility(View.GONE);
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<LastInvoiceListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });
    }

    public class CustomerAdapter extends ArrayAdapter<LastInvoiceListResponse.ProductListResponse> {
        private final Context mContext;
        private final List<LastInvoiceListResponse.ProductListResponse> customers;
        private final List<LastInvoiceListResponse.ProductListResponse> customersall;

        private final int mLayoutResourceId;

        public CustomerAdapter(Context context, int resource, List<LastInvoiceListResponse.ProductListResponse> departments) {
            super(context, resource, departments);
            this.mContext = context;
            this.mLayoutResourceId = resource;
            this.customers = new ArrayList<>(departments);
            this.customersall = new ArrayList<>(departments);

        }

        public int getCount() {
            return customers.size();
        }

        public LastInvoiceListResponse.ProductListResponse getItem(int position) {
            return customers.get(position);
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
                final LastInvoiceListResponse.ProductListResponse customerDetails = getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.customerNameTv);
                name.setText(customerDetails.id);


                name.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            //do stuff here
                        }
//                        Log.d("idddddd", customerDetails.id);
//                        Log.i("click text", "kakak");


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
                    return ((LastInvoiceListResponse.ProductListResponse) resultValue).id;
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    List<LastInvoiceListResponse.ProductListResponse> departmentsSuggestion = new ArrayList<>();
                    if (constraint != null) {
                        for (LastInvoiceListResponse.ProductListResponse customer : customersall) {
                            if (!customer.sales_code.isEmpty()) {
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
                    customers.clear();
                    if (results != null && results.count > 0) {
                        // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                        for (Object object : (List<?>) results.values) {
                            if (object instanceof LastInvoiceListResponse.ProductListResponse) {
                                customers.add((LastInvoiceListResponse.ProductListResponse) object);
                            }
                        }
                        notifyDataSetChanged();
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        customers.addAll(customersall);
                        notifyDataSetInvalidated();
                    }
                }
            };
        }
    }

    private void getRefundReasonsList() {

        AddProductRequest request = new AddProductRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

//        Log.d("requtesmisc", new Gson().toJson(request));

        retrofit2.Call<RefundReasonsResponse> call = apiService.refundData("1234", request);

        call.enqueue(new Callback<RefundReasonsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RefundReasonsResponse> call, Response<RefundReasonsResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    RefundReasonsResponse user = response.body();

//                    Log.d("refunddataa", new Gson().toJson(response));

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("requtesmiscres", new Gson().toJson(response));

//                        Log.d("prodResp", "response 111211: " + new Gson().toJson(response.body()));
                        refundReasonsList = user.productResults;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (refundReasonsList.size() > 0) {

                                    refundReasonsListAdapter = new RefundReasonsListAdapter(NewPosMainActivity.this, refundReasonsList);
                                    refundReasonsRecyclerview.setAdapter(refundReasonsListAdapter);
                                    refundReasonsRecyclerview.setNestedScrollingEnabled(false);
                                    refundReasonsListAdapter.notifyDataSetChanged();
                                } else {


                                }
                            }
                        });

                    } else {

                        refundReasonsRecyclerview.setVisibility(View.GONE);

//                        showLongSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<RefundReasonsResponse> call, Throwable t) {
                hideProgressDialog();

                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);

            }
        });

    }

    public class RefundReasonsListAdapter extends RecyclerView.Adapter<RefundReasonsListAdapter.ViewHolder> {
        Context activity;
        List<RefundReasonsResponse.RefundListResponse> data;
        RefundReasonsResponse.RefundListResponse masterData;
        int row_index;

        public RefundReasonsListAdapter(Activity activity2,
                                        List<RefundReasonsResponse.RefundListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.refund_reaasons_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            masterData = data.get(position);
//            Log.d("hdhdhdh", masterData.getId());
            holder.refundReasonTv.setText(masterData.getRefund_type());

            holder.refundOuterLayout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            masterData = data.get(position);

                            row_index = position;
                            notifyDataSetChanged();
                            returnType = masterData.getId();


                        }
                    });
            if (row_index == position) {
                holder.refundLayout.setBackgroundColor(Color.parseColor("#1C60AE"));
                holder.refundReasonTv.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.refundLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.refundReasonTv.setTextColor(Color.parseColor("#1C60AE"));
            }


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView refundReasonTv;
            RelativeLayout refundLayout, refundOuterLayout;
            CardView organic;

            public ViewHolder(final View itemView) {
                super(itemView);
                this.refundReasonTv = (AppCompatTextView) itemView.findViewById(R.id.refundReasonTv);
                this.refundLayout = (RelativeLayout) itemView.findViewById(R.id.refundLayout);
                this.refundOuterLayout = (RelativeLayout) itemView.findViewById(R.id.refundOuterLayout);
                this.organic = (CardView) itemView.findViewById(R.id.organic);
            }
        }
    }

    public void sendRefundCartData() {

        products = db.getAllCategories();

//        productsList.clear();

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

//            Log.d("skjhdj", addOnId + ":" + addOnQty);

            myProdObjects.add(productsObj);

        }

        productsArray.put(myProdObjects);

        getRefundSaveOrderResponse();

    }

    private void getRefundSaveOrderResponse() {

        showProgressDialog();

//        totalAmount = totalAmount + orderTotal;
        OrderRequest request = new OrderRequest();
        request.setCustomer_id(customerId);
        request.setCashier_id(userId);
        request.setOutlet_id(outlet_id);
        request.setTable_number(tableNumber);
        request.setGrand_total("" + totalAmount);
        request.setSubtotal("" + totalAmount);
        request.setPayment_type("");
        request.setChange_return("0");
        request.setPaid_amt("" + totalAmount);
        request.setItem_list(productsArray);
        request.setCustomer_name(customer_name);
        request.setEmail(customer_email);
        request.setMobile(customer_mobile);
        request.setAddress(customer_address);
        request.setPostcode(customer_post_code);
        request.setOrder_number(returnorderNo);


//        Log.d("RefundsaveOrderRequest", new Gson().toJson(request));

        Call<SaveOrderResponse> call = apiService.refundOrdersList("1234", request);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, retrofit2.Response<SaveOrderResponse> response) {
//                Log.d("RefundsaveOrderResponse", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    SaveOrderResponse user = response.body();
                    message = user.getMessage();
                    String success = user.getResponseCode();
                    if (success.equalsIgnoreCase("0")) {
//                        addKotDialog.dismiss();
                        orderNo = user.getOrder_id();
                        orderNumber = user.getSales_code();

//                      Log.d("paymentType", user.getPayment_type());
                        ReturnPrintsuccessFailurePopup();

//                        if (user.getPayment_type().equalsIgnoreCase("1")) {
//
//                        } else if (user.getPayment_type().equalsIgnoreCase("2")) {
////                            getCashBoxOpen();
//                        } else {
//
//                        }

                        showLongSnackBar(message);

                        db.deleteAll();
                        selectedDiscount = 0.00;
                        updateList();
                        updateData = "0";
                        tableNumber = "0";
//                        sessionManager.storeOrderTrackId(orderId);

//                        getSquareUpResponse();
                        placeKotLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        addMiscProductLayout.setVisibility(View.GONE);
                        addOnsDataLayout.setVisibility(View.GONE);
                        addCustomerDataLayout.setVisibility(View.GONE);
                        categoriesLayout.setVisibility(View.VISIBLE);

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

    AlertDialog returnOrdersDialog;

    public void getReturnListPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_returnlist_popup, null);
        builder.setView(dialogView);
        returnOrdersDialog = builder.create();
        returnOrdersDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(returnOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.65f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        returnOrdersDialog.getWindow().setAttributes(layoutParams);
        returnOrdersDialog.getWindow().setGravity(Gravity.RIGHT);

        final AppCompatImageView closeTextView = returnOrdersDialog.findViewById(R.id.closeTextView);

        returnListRecyclerView = (RecyclerView) returnOrdersDialog.findViewById(R.id.returnListRecyclerView);
        returnListRecyclerView.setVisibility(View.GONE);

        noDataTextView = (AppCompatTextView) returnOrdersDialog.findViewById(R.id.noDataTextView);
        noDataTextView.setVisibility(View.GONE);

        progressBar = (ProgressBar) returnOrdersDialog.findViewById(R.id.progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        returnListRecyclerView.setLayoutManager(mLayoutManager);

        getReturnOrderDetails();

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnOrdersDialog.dismiss();
            }
        });

    }

    private void getReturnOrderDetails() {

        AddProductRequest request = new AddProductRequest();
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

//        Log.d("ordersRequest", request + new Gson().toJson(request));
        retrofit2.Call<ReturnListResponse> call = apiService.returnsProductsData("1234", request);

        call.enqueue(new Callback<ReturnListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ReturnListResponse> call, Response<ReturnListResponse> response) {

                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    hideProgressDialog();
                    ReturnListResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;
                    if (success.equalsIgnoreCase("0")) {

                        noDataTextView.setVisibility(View.GONE);
                        returnListRecyclerView.setVisibility(View.VISIBLE);

//                        Log.d("ordersResp", "response 1112: " + new Gson().toJson(response.body()));
                        returnsList = user.returnResults;


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ReturnOrdersRecyclerAdapter LoansListAdapter = new ReturnOrdersRecyclerAdapter(NewPosMainActivity.this, returnsList);
                                returnListRecyclerView.setAdapter(LoansListAdapter);
                                returnListRecyclerView.setNestedScrollingEnabled(false);
                                LoansListAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        noDataTextView.setVisibility(View.VISIBLE);
                        ordersRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    hideProgressDialog();
//                    showLongSnackBar("Server Error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ReturnListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                hideProgressDialog();
                message = "Oops! something went wrong please try again";
                showShortSnackBar(message);
            }
        });

    }

    public class ReturnOrdersRecyclerAdapter extends RecyclerView.Adapter<ReturnOrdersRecyclerAdapter.ViewHolder> {

        Context activity;

        List<ReturnListResponse.ReturnResponse> data2;
        ReturnListResponse.ReturnResponse masterData2;

        List<ReturnListResponse.ReturnResponse.ItemsListResponseListResponse> data3;
        ReturnListResponse.ReturnResponse.ItemsListResponseListResponse masterData3;
        Boolean selected;
        int row_index;

        public ReturnOrdersRecyclerAdapter(Activity activity2,
                                           List<ReturnListResponse.ReturnResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data2 = masterList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.return_main_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData2 = data2.get(position);

            holder.returnOrdersOrderNoTv.setText(masterData2.getSales_id());
            holder.returnOrdersTotalTv.setText("$" + masterData2.getGrand_total());
            holder.returnOrdersCustomerNameTv.setText(masterData2.getCustomer_name());
            holder.returnOrdersCustomerMobileTv.setText(masterData2.getMobile());
            holder.paymentStatusTv.setText(masterData2.getReturn_status());
            holder.returnOrdersTimeTv.setText(masterData2.getReturn_date());
            if (masterData2.getOrder_type().equalsIgnoreCase("1")) {
                holder.returnOrdersTypeTv.setText("Take Away");
                holder.returnOrdersTypeTv.setTextColor(getResources().getColor(R.color.takeawayHeader));
            } else if (masterData2.getOrder_type().equalsIgnoreCase("2")) {
                holder.returnOrdersTypeTv.setText("Delivery");
                holder.returnOrdersTypeTv.setTextColor(getResources().getColor(R.color.deliveryHeader));
            } else {
                holder.returnOrdersTypeTv.setText("In-Store");
                holder.returnOrdersTypeTv.setTextColor(getResources().getColor(R.color.dineinHeader));
            }
            holder.returnItemsLayout.setVisibility(View.GONE);


            holder.viewActionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.viewActionTv.getText().toString().equalsIgnoreCase("Close")) {
                        holder.returnItemsLayout.setVisibility(View.GONE);
                        holder.viewActionTv.setText("View");
                    } else {
                        masterData2 = data2.get(position);
                        data3 = masterData2.getitemsResults();

                        holder.viewActionTv.setText("Close");
                        holder.returnItemsLayout.setVisibility(View.VISIBLE);

                        LinearLayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        holder.returnListItemsRecyclerView.setLayoutManager(mLayoutManager2);

                        returnOrderProductsRecyclerAdapter = new ReturnOrderProductsRecyclerAdapter(NewPosMainActivity.this, data3);
                        holder.returnListItemsRecyclerView.setAdapter(returnOrderProductsRecyclerAdapter);
                        holder.returnListItemsRecyclerView.setNestedScrollingEnabled(false);
                        returnOrderProductsRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return data2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView returnOrdersOrderNoTv;
            AppCompatTextView returnOrdersTypeTv;
            AppCompatTextView returnOrdersTotalTv;
            AppCompatTextView returnOrdersCustomerNameTv;
            AppCompatTextView returnOrdersCustomerMobileTv;
            AppCompatTextView returnOrdersTimeTv;
            AppCompatTextView paymentStatusTv;
            AppCompatTextView actionTv;
            AppCompatTextView viewActionTv;
            RecyclerView returnListItemsRecyclerView;
            LinearLayout returnItemsLayout;

            LinearLayout tableHeaderLayout;

            public ViewHolder(final View itemView) {
                super(itemView);

                this.returnOrdersOrderNoTv = itemView.findViewById(R.id.returnOrdersOrderNoTv);
                this.returnOrdersTypeTv = itemView.findViewById(R.id.returnOrdersTypeTv);
                this.returnOrdersTotalTv = itemView.findViewById(R.id.returnOrdersTotalTv);
                this.returnOrdersCustomerNameTv = itemView.findViewById(R.id.returnOrdersCustomerNameTv);
                this.returnOrdersCustomerMobileTv = itemView.findViewById(R.id.returnOrdersCustomerMobileTv);
                this.returnOrdersTimeTv = itemView.findViewById(R.id.returnOrdersTimeTv);
                this.paymentStatusTv = itemView.findViewById(R.id.paymentStatusTv);
                this.tableHeaderLayout = itemView.findViewById(R.id.tableHeaderLayout);
                this.viewActionTv = itemView.findViewById(R.id.viewActionTv);
                this.returnListItemsRecyclerView = itemView.findViewById(R.id.returnListItemsRecyclerView);
                this.returnItemsLayout = itemView.findViewById(R.id.returnItemsLayout);

            }


        }

    }

    public class ReturnOrderProductsRecyclerAdapter extends RecyclerView.Adapter<ReturnOrderProductsRecyclerAdapter.ViewHolder> {

        Context activity;

        List<ReturnListResponse.ReturnResponse> data;
        ReturnListResponse.ReturnResponse masterData;

        List<ReturnListResponse.ReturnResponse.ItemsListResponseListResponse> data1;
        ReturnListResponse.ReturnResponse.ItemsListResponseListResponse masterData1;

        public ReturnOrderProductsRecyclerAdapter(Activity activity2,
                                                  List<ReturnListResponse.ReturnResponse.ItemsListResponseListResponse> masterList) {
            // TODO Auto-generated constructor stub
            activity = activity2;
            data1 = masterList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(activity)
                    .inflate(R.layout.return_product_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            masterData1 = data1.get(position);

            holder.returnitemNameTv.setText(masterData1.getItem_name());
            holder.returnpriceInvoiceTv.setText(masterData1.getPrice_per_unit());
            holder.returnqtyTv.setText(masterData1.getReturn_qty());
            holder.returnDiscountTv.setText(masterData1.getDiscount_amt());
            holder.returnTotalTv.setText(masterData1.getTotal_cost());

        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView returnitemNameTv, returnpriceInvoiceTv, returnqtyTv, returnDiscountTv, returnTotalTv;


            public ViewHolder(final View itemView) {
                super(itemView);

                returnitemNameTv = (AppCompatTextView) itemView.findViewById(R.id.returnitemNameTv);
                returnpriceInvoiceTv = (AppCompatTextView) itemView.findViewById(R.id.returnpriceInvoiceTv);
                returnqtyTv = (AppCompatTextView) itemView.findViewById(R.id.returnqtyTv);
                returnDiscountTv = (AppCompatTextView) itemView.findViewById(R.id.returnDiscountTv);
                returnTotalTv = (AppCompatTextView) itemView.findViewById(R.id.returnTotalTv);

            }
        }

    }


    AlertDialog returnPrintDialog;

    public void ReturnPrintsuccessFailurePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPosMainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.success_popup, null);
        builder.setView(dialogView);
        returnPrintDialog = builder.create();
        returnPrintDialog.setCanceledOnTouchOutside(false);
        returnPrintDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView logo = returnPrintDialog.findViewById(R.id.logosuccess);
        final TextView success = returnPrintDialog.findViewById(R.id.success);
        final TextView orderIdTextView = returnPrintDialog.findViewById(R.id.orderIdTextView);
        final TextView orderTotalTextView = returnPrintDialog.findViewById(R.id.orderTotalTextView);
        final TextView printBillButton = returnPrintDialog.findViewById(R.id.printBillButton);

        success.setText("Return Successful");

        orderIdTextView.setText("Order Number : " + orderNo);
        orderIdTextView.setVisibility(View.GONE);
//        orderTotalTextView.setText("Change Return : $" + String.valueOf(round(changeReturn, 2)));
        orderTotalTextView.setVisibility(View.GONE);

        if (message.equalsIgnoreCase("success")) {
            success.setText("Return Successful");
            logo.setBackgroundResource(R.mipmap.success_icon);
            success.setTextColor(Color.parseColor("#5c9b45"));
            printBillButton.setVisibility(View.VISIBLE);
        } else if (message.equalsIgnoreCase("Failure")) {
            success.setText("Sorry! Return Failed");
            logo.setBackgroundResource(R.mipmap.failure);
            success.setTextColor(Color.parseColor("#fe1a1a"));
            printBillButton.setVisibility(View.GONE);
        }

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        returnPrintDialog.getWindow().setAttributes(layoutParams);

        returnPrintDialog.findViewById(R.id.shopAgainBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnPrintDialog.dismiss();
            }
        });

        returnPrintDialog.findViewById(R.id.printBillButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnPrintDialog.dismiss();
                getReturnBillDetails();

                priceId = "0";
                db.deleteAll();

                updateList();
                selectedDiscount = 0.00;
                getProductDetails();

//                getCashBoxOpen();

            }
        });

    }

    private void getReturnBillDetails() {

        showProgressDialog();

        PrintKotRequest request = new PrintKotRequest();
        request.setOrder_id(orderNo);
        request.setUser_id(userId);
        request.setOutlet_id(outlet_id);

        retrofit2.Call<PrintBillResponse> call = apiService.returnBillPrint("1234", request);

//        Log.e("lastinvoice", new Gson().toJson(request));

        call.enqueue(new Callback<PrintBillResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PrintBillResponse> call, Response<PrintBillResponse> response) {

                if (response.isSuccessful()) {
                    hideProgressDialog();
                    final PrintBillResponse user = response.body();

                    String message = user.message;
                    String success = user.responseCode;

                    if (success.equalsIgnoreCase("0")) {

//                        Log.d("cateppppResp", "response 1112: " + new Gson().toJson(response.body()));
                        printList = user.productResults;
                        taxList = user.userResults;

                        final String orderNo = user.order_number;
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

                                //orderNo = null;

                                try {
                                    tax = Double.valueOf(taxList.getTax());
                                } catch (NullPointerException e) {
                                    tax = Double.valueOf(taxList.getFree());

                                }

                                //    Log.d("taxssss", "" + tax);

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
                                                    "[C]<b>Return Invoice" + "\n" +
                                                    "[C]<b>Invoice No:" + user.order_id + "\n" +
                                                    "[C]<b>ABN:" + user.abn + "\n" +
                                                    "[C]----------------------------------" +
//                                                    "[C]" + user.company_address + "\n" +
//                                                    "[C]Ph:" + user.mobile + "\n" +
                                                    "[L]\n" +
                                                    "[L]\n";

                                    for (int i = 0; i < printList.size(); i++) {
                                        addOnsproductsList = new ArrayList<>();
                                        addOnsproductsList = user.productResults.get(i).productAddOnsResults;

                                        String item = user.productResults.get(i).item_name;
                                        String qty = user.productResults.get(i).sales_qty;
                                        String total = user.productResults.get(i).unit_total_cost;
                                        String item_note = user.productResults.get(i).item_note;

                                        if (item_note.equalsIgnoreCase("0")) {
                                            printText = printText + "[C]<b><font size='normal'>" + qty + " x " + item + "[R]" + total + "\n";
                                        } else {
                                            printText = printText + "[C]<b><font size='normal'> " + qty + " x " + item + "[R]" + total + "\n" +
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
                                            "[C]------------------------------------\n" +
                                            "[L]<b>SUB TOTAL [R]$" + user.grand_total + "</b>\n" +
                                            "[L]<b>DISCOUNT [R]$" + user.total_discount + "</b>\n" +
                                            "[L]<b>TAX [R]$" + tax + "</b>\n";
                                    printText = printText + "[L]<b>PAID [R]$" + user.paid_amount + "</b>\n" +
                                            "[L]<b>BALANCE[R]$" + user.balance_amount + "</b>\n" +
                                            "[L]<b>CHANGE RETURN[R]$" + user.change_return + "</b>\n" +
                                            "[C]-----------------------------------\n" +
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
                                            "[L]" + tableNewNo;

                                    printText = printText + "[L]\n";
//                                            "[C]<qrcode>" + user.getCompany_website() + "</qrcode>\n" +


                                    doPrint();
//                                    printUsb();
//                                    printTcp();
                                }

                            }
                        });

                    } else {
                        hideProgressDialog();
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

    public static void initializeSSLContext(Context mContext) {
        try {
            SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            ProviderInstaller.installIfNeeded(mContext.getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void reduceChoreographerSkippedFramesWarningThreshold() {
        if (BuildConfig.DEBUG) {
            Field field = null;
            try {
                field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
                field.setAccessible(true);
                field.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, 5);
            } catch (Throwable e) {
                Log.e(TAG, "failed to change choreographer's skipped frames threshold");
            }
        }
    }
}
