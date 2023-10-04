package com.muriloCruz.ItGames.pagamentos.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.muriloCruz.ItGames.pagamentos.dto.StripeChargeDto;
import com.muriloCruz.ItGames.pagamentos.dto.StripeTokenDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class StripeService {

	@Value("${api.stripe.key}")
	private String stripeApiKey;
	
	@PostConstruct
	public void init() {
		
		Stripe.apiKey = stripeApiKey;
	}
	
	public StripeTokenDto criarTokenDoCartao(StripeTokenDto model) {
		
		try {
			Map<String, Object> card = new HashMap<String, Object>();
			card.put("number", model.getCardNumber());
			card.put("exp_month", Integer.parseInt(model.getExpMonth()));
			card.put("exp_year", Integer.parseInt(model.getExpYear()));
			card.put("cvc", model.getCvc());
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("card", card);
			Token token = Token.create(parametros);
			if (token != null && token.getId() != null) {
				model.setSuccess(true);
				model.setToken(token.getId());
			}
			return model;	
		} catch (StripeException e) {
			log.error("StripeService (createCardToken)", e);
			throw new RuntimeException(e.getMessage());
		}					
	}
	
	public StripeChargeDto charge(StripeChargeDto chargeRequest) {
		
		try {
			chargeRequest.setSuccess(false);
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", (int) (chargeRequest.getAmount() * 100));
			chargeParams.put("currency", "USD");
			chargeParams.put("description", "Pagamento pelo ID" + chargeRequest.getAdditionalInfo()
			.getOrDefault("ID_TAG", ""));
			chargeParams.put("source", chargeRequest.getStripeToken());
			Map<String, Object> metaData = new HashMap<String, Object>();
			metaData.put("id", chargeRequest.getChargeId());
			metaData.putAll(chargeRequest.getAdditionalInfo());
			chargeParams.put("metadata", metaData);
			Charge charge = Charge.create(chargeParams);
			chargeRequest.setMessage(charge.getOutcome().getSellerMessage());
			
			if (charge.getPaid()) {
				chargeRequest.setChargeId(charge.getId());
				chargeRequest.setSuccess(true);
			}
			return chargeRequest;
		} catch (StripeException e) {
			log.error("StripeService (charge)", e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
