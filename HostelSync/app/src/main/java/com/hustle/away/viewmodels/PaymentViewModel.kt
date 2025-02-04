package com.hustle.away.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.hustle.away.network.OrderRequest
import com.hustle.away.network.OrderResponse
import com.hustle.away.network.RetrofitClient
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel: ViewModel(), PaymentResultWithDataListener, ExternalWalletListener {
    fun startPayment(context: Context, amountInRupees: Int) {
        // Call backend to get order_id
        RetrofitClient.instance.createOrder(OrderRequest(amountInRupees))
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                    if (response.isSuccessful) {
                        val order = response.body()
                        if (order != null) {
                            initiateRazorpay(context, order.id, order.amount)
                        }
                    } else {
                        Toast.makeText(context, "Failed to create order", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            })
    }

    private fun initiateRazorpay(context: Context, orderId: String, amount: Int) {
        val activity = context as Activity
        val co = Checkout()
        co.setKeyID("YOUR_RAZORPAY_ID")

        try {
            val options = JSONObject()
            options.put("name", "Your Business")
            options.put("description", "Test Payment")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("order_id", orderId)

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(context, "Payment error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


    override fun onPaymentSuccess(paymentId: String?, paymentData: PaymentData?) {
        Log.d("PaymentViewModel", "Payment Success: $paymentId Data: $paymentData")
        // Send paymentData.paymentId and paymentData.orderId to your backend for verification
    }

    override fun onPaymentError(errorCode: Int, errorMessage: String?, paymentData: PaymentData?) {
        Log.e("PaymentViewModel", "Payment Error: $errorMessage Code: $errorCode Data: $paymentData")
//        Toast.makeText(context, "Payment Failed: $errorMessage", Toast.LENGTH_LONG).show()
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        Log.d("PaymentViewModel", "External Wallet Selected: $p0 Data: $p1")
    }
}
