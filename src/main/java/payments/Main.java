package payments;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
public class Main {
	public static void main(String[] args) throws RazorpayException {
		RazorpayClient razorpay = new RazorpayClient("", "");
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount",1000);
		paymentLinkRequest.put("currency","INR");
		paymentLinkRequest.put("accept_partial",false);
        Instant now = Instant.now();
        Instant futureDate = now.plus(30, ChronoUnit.DAYS);
        long futureTimestamp = futureDate.getEpochSecond();
		paymentLinkRequest.put("expire_by",futureTimestamp);
		paymentLinkRequest.put("reference_id","TS1997");
		paymentLinkRequest.put("description","Payment for policy no #23456");
		JSONObject customer = new JSONObject();
		customer.put("name","");
		customer.put("contact","");
		customer.put("email","");
		paymentLinkRequest.put("customer",customer);
		JSONObject notify = new JSONObject();
		notify.put("whatsapp",true);
		notify.put("sms",false);
		notify.put("email",true);
		paymentLinkRequest.put("notify",notify);
		paymentLinkRequest.put("reminder_enable",true);

		JSONObject options = new JSONObject();

		JSONObject method = new JSONObject();
		method.put("netbanking",0);
		method.put("card",1);
		method.put("upi",1);
		method.put("wallet",0);
		method.put("pay_later", 0);
		JSONObject checkout = new JSONObject();
		checkout.put("method",method);
		options.put("checkout",checkout);
		paymentLinkRequest.put("options",options);
		              
		PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
		System.out.println(payment.toString());
		
	}
}
