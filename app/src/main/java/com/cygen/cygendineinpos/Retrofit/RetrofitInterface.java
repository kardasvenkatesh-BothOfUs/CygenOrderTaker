package com.cygen.cygendineinpos.Retrofit;

import com.cygen.cygendineinpos.RetrofitRequest.AddCategoryRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddCompanyRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddCustomerRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddProductRequest;
import com.cygen.cygendineinpos.RetrofitRequest.AddSiteRequest;
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
import com.cygen.cygendineinpos.RetrofitRequest.SaveInvoiceRequest;
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
import com.cygen.cygendineinpos.RetrofitResponse.CompanyResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CountryListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CouponsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CurrencyListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.CustomerResponse;
import com.cygen.cygendineinpos.RetrofitResponse.DayEndReportResponse;
import com.cygen.cygendineinpos.RetrofitResponse.GroupListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.HoldInvoiceListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.HoldItemsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.KotItemStatusResponse;
import com.cygen.cygendineinpos.RetrofitResponse.KotListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LastInvoiceListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LastInvoiceResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LoginResponse;
import com.cygen.cygendineinpos.RetrofitResponse.NewTableListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OrdersCountResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OrdersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OutletResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintBillResponse;
import com.cygen.cygendineinpos.RetrofitResponse.PrintKotResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ProductsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.RefundReasonsResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReservationResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReservationStatusResponse;
import com.cygen.cygendineinpos.RetrofitResponse.ReturnListResponse;
import com.cygen.cygendineinpos.RetrofitResponse.RunningOrdersResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SaveInvoiceResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SaveOrderResponse;
import com.cygen.cygendineinpos.RetrofitResponse.SiteSettingsResponse;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface

RetrofitInterface {
//
//    @POST("outletlogin.php")
//    Call<OutletResponse> outletLoginData(@Header("restaurantToken") String restaurantToken, @Body LoginRequest loginRequest);

    @POST("outletLogin")
    Call<OutletResponse> outletLoginData(@Header("restaurantToken") String restaurantToken, @Body LoginRequest loginRequest);

    @POST("login")
    Call<LoginResponse> userLoginData(@Header("restaurantToken") String restaurantToken, @Body LoginRequest loginRequest);

    @POST("addholdinvoice")
    Call<HoldItemsResponse> holdAdding(@Header("restaurantToken") String restaurantToken, @Body HoldRequest holdRequest);

    @POST("editholdinvoiceList")
    Call<HoldInvoiceListResponse> holdEditing(@Header("restaurantToken") String restaurantToken, @Body HoldRequest holdRequest);

    @POST("deleteholdinvoiceList")
    Call<HoldItemsResponse> deleteHoldInvoice(@Header("restaurantToken") String restaurantToken, @Body HoldRequest holdRequest);

    @POST("holdinvoiceList")
    Call<HoldInvoiceListResponse> holdList(@Header("restaurantToken") String restaurantToken);

    @POST("categoryList")
    Call<CategoryResponse> categoryList(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("categoryList")
    Call<CategoryResponse> subCategoryList(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("tableList")
    Call<TableResponse> tableList(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("tableList")
    Call<TableResponse> runningOrderTablesList(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("tableList")
    Call<TableResponse> getTablesListBasedOnAreaId(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("saveposOrder")
    Call<SaveOrderResponse> saveOrdersList(@Header("restaurantToken") String restaurantToken, @Body OrderRequest orderRequest);

    @POST("checkoutfinal")
    Call<SaveOrderResponse> checkout(@Header("restaurantToken") String restaurantToken, @Body OrderRequest orderRequest);

    @POST("runningOrderList")
    Call<RunningOrdersResponse> runningOrdersList(@Header("restaurantToken") String restaurantToken, @Body OrdersRequest ordersRequest);

    @POST("customerList")
    Call<CustomerResponse> customerList(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("singleProduct")
    Call<AddOnsResponse> singleProductList(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("pos_productList")
    Call<ProductsResponse> productList(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("pos_productList")
    Call<ProductsResponse> allProductList(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("printbill")
    Call<PrintBillResponse> printbill(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("printkot")
    Call<PrintKotResponse> printkot(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("pos_searchproductList")
    Call<ProductsResponse> searchProductList(@Header("restaurantToken") String restaurantToken, @Body ProductsRequest productsRequest);

    @POST("countryList")
    Call<CountryListResponse> countryList(@Header("restaurantToken") String restaurantToken, @Body HoldRequest holdRequest);

    @POST("stateList")
    Call<StateListResponse> stateList(@Header("restaurantToken") String restaurantToken, @Body StateListRequest save);

    @POST("cashinrequest")
    Call<CashInResponse> cashIn(@Header("restaurantToken") String restaurantToken, @Body CashInRequest save);

    @POST("ordernotification")
    Call<OrdersResponse> orderDetails(@Header("restaurantToken") String restaurantToken, @Body OrdersRequest orderRequest);

    @POST("addcustomer")
    Call<AddCustomerResponse> addCustomerData(@Header("restaurantToken") String restaurantToken, @Body AddCustomerRequest save);

    @POST("saveinvoice")
    Call<SaveInvoiceResponse> saveInvoiceData(@Header("restaurantToken") String restaurantToken, @Body SaveInvoiceRequest saveInvoiceRequest);

    @POST("tablelayout")
    Call<TableLayoutResponse> getAreaTablesData(@Header("restaurantToken") String restaurantToken, @Body CashInRequest cashInRequest);

    @POST("tablelayoutforarea")
    Call<TableListResponse> getAreaWiseTablesData(@Header("restaurantToken") String restaurantToken, @Body CashInRequest cashInRequest);

    @POST("cashin")
    Call<CashInResponse> cashInData(@Header("restaurantToken") String chickyToken, @Body CashInRequest cashInRequest);

    @POST("cashout")
    Call<CashInOutResponse> cashInOutData(@Header("restaurantToken") String chickyToken, @Body CashInOutRequest cashInOutRequest);

    @POST("shiftout")
    Call<CashInOutResponse> shiftInOutData(@Header("restaurantToken") String chickyToken, @Body CashInOutRequest cashInOutRequest);

    @POST("couponlist")
    Call<CouponsResponse> couponsData(@Header("restaurantToken") String chickyToken, @Body CouponsRequest couponsRequest);

    @POST("squareuppayment")
    Call<SquareUpPaymentResponse> squareupPaymentData(@Header("restaurantToken") String chickyToken, @Body SquareUpPaymentRequest squareUpPaymentRequest);

    @POST("getterminalcheckout")
    Call<TermicalCheckoutResponse> termicalCheckoutData(@Header("restaurantToken") String chickyToken, @Body GetTermicalCheckoutRequest getTermicalCheckoutRequest);

    @POST("cancelterminal")
    Call<TermicalCancelCheckoutResponse> cancelTerminalCheckoutData(@Header("restaurantToken") String chickyToken, @Body GetTermicalCheckoutRequest getTermicalCheckoutRequest);

    @POST("ordercount")
    Call<OrdersCountResponse> getOnlineOrdersCountResponse(@Header("restaurantToken") String chickyToken, @Body CouponsRequest couponsRequest);

    @POST("lastinvoice")
    Call<LastInvoiceResponse> getLastInvoiceResponse(@Header("restaurantToken") String chickyToken, @Body OrdersRequest ordersRequest);

    @POST("posordernotification")
    Call<LastInvoiceListResponse> getLastInvoiceListResponse(@Header("restaurantToken") String chickyToken, @Body OrdersRequest ordersRequest);

    @POST("dayend")
    Call<DayEndReportResponse> dayEndData(@Header("restaurantToken") String chickyToken, @Body CouponsRequest couponsRequest);

    @POST("bannerList")
    Call<BannersResponse> bannersData(@Header("restaurantToken") String chickyToken, @Body CouponsRequest couponsRequest);

    @POST("addproduct")
    Call<AddProductResponse> addProductData(@Header("restaurantToken") String restaurantToken, @Body AddProductRequest addProductRequest);

    @POST("updateproduct")
    Call<AddProductResponse> updateProductData(@Header("restaurantToken") String restaurantToken, @Body AddProductRequest addProductRequest);

    @POST("allkotlist")
    Call<KotListResponse> allKotList(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("kotitemallstatus")
    Call<KotItemStatusResponse> kotItemClearOrder(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("changeprintstatus_pos")
    Call<LoginResponse> changeprintstatus_data(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("changeprintstatus_kichen")
    Call<LoginResponse> changeprintstatus_kichenData(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("kotitemstatus")
    Call<KotItemStatusResponse> kotItemStatusUpdateData(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("printonlinebill")
    Call<PrintBillResponse> printOnlineBill(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("printkitchenbill")
    Call<PrintBillResponse> kitchenprintbill(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("reservationlist")
    Call<ReservationResponse> getReservationList(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);

    @POST("reservation_status")
    Call<ReservationStatusResponse> changeReservationStatus(@Header("restaurantToken") String restaurantToken, @Body ReservationStatusRequest reservationStatusRequest);

    @POST("reservation_count")
    Call<ReservationStatusResponse> getReservationCount(@Header("restaurantToken") String restaurantToken, @Body ReservationStatusRequest reservationStatusRequest);

    @POST("changestatus")
    Call<TaxResponse> changeStatusData(@Header("restaurantToken") String restaurantToken, @Body ChangeStatusRequest changeStatusRequest);

    @POST("addcategory")
    Call<AddCategoryResponse> addCategoryData(@Header("restaurantToken") String restaurantToken, @Body AddCategoryRequest addCategoryRequest);

    @POST("categoryList")
    Call<CategoryResponse> addProductCategory(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("addItem")
    Call<AddProductResponse> addMainProductData(@Header("restaurantToken") String restaurantToken, @Body AddProductRequest addProductRequest);

    @POST("sitesetting")
    Call<SiteSettingsResponse> siteSettingsData(@Header("restaurantToken") String restaurantToken, @Body LoginRequest loginRequest);

    @POST("addcompany")
    Call<CompanyResponse> addCompanyData(@Header("restaurantToken") String restaurantToken, @Body AddCompanyRequest addCompanyRequest);

    @POST("currencylist")
    Call<CurrencyListResponse> getCurrencyData(@Header("restaurantToken") String restaurantToken, @Body CategoryRequest categoryRequest);

    @POST("addsite")
    Call<TaxResponse> addSiteData(@Header("restaurantToken") String restaurantToken, @Body AddSiteRequest addSiteRequest);

    @POST("tableshift")
    Call<TableShiftResponse> tableShiftData(@Header("restaurantToken") String restaurantToken, @Body TableShiftRequest addSiteRequest);

    @POST("tablelistall")
    Call<NewTableListResponse> newTableListData(@Header("restaurantToken") String restaurantToken, @Body CashInRequest cashInRequest);

    @POST("grouplist")
    Call<GroupListResponse> groupListData(@Header("restaurantToken") String restaurantToken, @Body CashInRequest cashInRequest);

    @POST("useroutletlist")
    Call<StoreUsersListResponse> storeUserData(@Header("restaurantToken") String restaurantToken, @Body CashInRequest cashInRequest);

    @POST("refundlist")
    Call<RefundReasonsResponse> refundData(@Header("restaurantToken") String restaurantToken, @Body AddProductRequest addProductRequest);

    @POST("refundOrder")
    Call<SaveOrderResponse> refundOrdersList(@Header("restaurantToken") String restaurantToken, @Body OrderRequest orderRequest);

    @POST("returnlist")
    Call<ReturnListResponse> returnsProductsData(@Header("restaurantToken") String restaurantToken, @Body AddProductRequest addProductRequest);

    @POST("returnbill")
    Call<PrintBillResponse> returnBillPrint(@Header("restaurantToken") String restaurantToken, @Body PrintKotRequest printKotRequest);


}

