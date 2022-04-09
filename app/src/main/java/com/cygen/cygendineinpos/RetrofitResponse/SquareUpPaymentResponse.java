package com.cygen.cygendineinpos.RetrofitResponse;

public class SquareUpPaymentResponse {

    String status, responseCode, message;
    String checkout_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(String checkout_id) {
        this.checkout_id = checkout_id;
    }

//    @SerializedName("list")
//    @Expose
//    public SquareUpResponse productResults;
//
//    public static class SquareUpResponse {
//
//        @SerializedName("checkout")
//        @Expose
//        public CheckOutResponse checkoutResults;
//
//        public static class CheckOutResponse {
//
//            @SerializedName("id")
//            @Expose
//            public String id;
//
//            @SerializedName("reference_id")
//            @Expose
//            public String reference_id;
//
//            @SerializedName("status")
//            @Expose
//            public String status;
//
//            @SerializedName("created_at")
//            @Expose
//            public String created_at;
//
//            @SerializedName("updated_at")
//            @Expose
//            public String updated_at;
//
//            @SerializedName("app_id")
//            @Expose
//            public String app_id;
//
//            @SerializedName("deadline_duration")
//            @Expose
//            public String deadline_duration;
//
//            @SerializedName("location_id")
//            @Expose
//            public String location_id;
//
//            @SerializedName("payment_type")
//            @Expose
//            public String payment_type;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getReference_id() {
//                return reference_id;
//            }
//
//            public void setReference_id(String reference_id) {
//                this.reference_id = reference_id;
//            }
//
//            public String getStatus() {
//                return status;
//            }
//
//            public void setStatus(String status) {
//                this.status = status;
//            }
//
//            public String getCreated_at() {
//                return created_at;
//            }
//
//            public void setCreated_at(String created_at) {
//                this.created_at = created_at;
//            }
//
//            public String getUpdated_at() {
//                return updated_at;
//            }
//
//            public void setUpdated_at(String updated_at) {
//                this.updated_at = updated_at;
//            }
//
//            public String getApp_id() {
//                return app_id;
//            }
//
//            public void setApp_id(String app_id) {
//                this.app_id = app_id;
//            }
//
//            public String getDeadline_duration() {
//                return deadline_duration;
//            }
//
//            public void setDeadline_duration(String deadline_duration) {
//                this.deadline_duration = deadline_duration;
//            }
//
//            public String getLocation_id() {
//                return location_id;
//            }
//
//            public void setLocation_id(String location_id) {
//                this.location_id = location_id;
//            }
//
//            public String getPayment_type() {
//                return payment_type;
//            }
//
//            public void setPayment_type(String payment_type) {
//                this.payment_type = payment_type;
//            }
//
//            public AmountResponse getAmountResults() {
//                return amountResults;
//            }
//
//            public void setAmountResults(AmountResponse amountResults) {
//                this.amountResults = amountResults;
//            }
//
//            public DeviceResponse getDeviceResults() {
//                return deviceResults;
//            }
//
//            public void setDeviceResults(DeviceResponse deviceResults) {
//                this.deviceResults = deviceResults;
//            }
//
//
//            @SerializedName("amount_money")
//            @Expose
//            public AmountResponse amountResults;
//
//            public static class AmountResponse {
//
//                @SerializedName("amount")
//                @Expose
//                public String amount;
//
//                @SerializedName("currency")
//                @Expose
//                public String currency;
//
//                public String getAmount() {
//                    return amount;
//                }
//
//                public void setAmount(String amount) {
//                    this.amount = amount;
//                }
//
//                public String getCurrency() {
//                    return currency;
//                }
//
//                public void setCurrency(String currency) {
//                    this.currency = currency;
//                }
//
//            }
//
//            @SerializedName("device_options")
//            @Expose
//            public DeviceResponse deviceResults;
//
//            public static class DeviceResponse {
//
//                @SerializedName("device_id")
//                @Expose
//                public String device_id;
//
//                @SerializedName("skip_receipt_screen")
//                @Expose
//                public Boolean skip_receipt_screen;
//
//                public String getDevice_id() {
//                    return device_id;
//                }
//
//                public void setDevice_id(String device_id) {
//                    this.device_id = device_id;
//                }
//
//                public Boolean getSkip_receipt_screen() {
//                    return skip_receipt_screen;
//                }
//
//                public void setSkip_receipt_screen(Boolean skip_receipt_screen) {
//                    this.skip_receipt_screen = skip_receipt_screen;
//                }
//
//
//                @SerializedName("tip_settings")
//                @Expose
//                public TipResponse deviceResults;
//
//                public static class TipResponse {
//
//                    @SerializedName("allow_tipping")
//                    @Expose
//                    public String allow_tipping;
//
//
//                    public String getAllow_tipping() {
//                        return allow_tipping;
//                    }
//
//                    public void setAllow_tipping(String allow_tipping) {
//                        this.allow_tipping = allow_tipping;
//                    }
//
//                }
//
//            }
//
//        }
//
//    }

}
