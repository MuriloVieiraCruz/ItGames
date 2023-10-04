package com.muriloCruz.ItGames.pagamentos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.muriloCruz.ItGames.pagamentos.dto.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;

@Service
public class StripeService {

	@Value("${api.stripe.secret.key}")
	private String stripeApiKey;
	
	@Value("${base_url}")
	private String baseUrl;

	public Session createSession(List<CheckoutItemDto> checkItemDtoList) throws StripeException {
		String sucessUrl = baseUrl + "payment/success";
		
		String failureUrl = baseUrl + "payment/failed";
		
		Stripe.apiKey = stripeApiKey;
		
		List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();
		
		for (CheckoutItemDto checkItemDto: checkItemDtoList) {
			sessionItemList.add(createSessionLineItem(checkItemDto));
		}
		
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setCancelUrl(failureUrl)
				.setSuccessUrl(sucessUrl)
				.addAllLineItem(sessionItemList)
				.build();
		
		return Session.create(params);
		
	}

	private LineItem createSessionLineItem(CheckoutItemDto checkItemDto) {
		return SessionCreateParams.LineItem.builder()
				.setPriceData(createPriceData(checkItemDto))
				.setQuantity(Long.parseLong(String.valueOf(checkItemDto.getQuantity())))
				.build();
	}

	private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkItemDto) {
		return SessionCreateParams.LineItem.PriceData.builder()
				.setCurrency("brl")
				.setUnitAmount((long)checkItemDto.getPrice() * 100)
				.setProductData(
						SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName(checkItemDto.getProductName())
						.build()
				).build();
	}
	
}
