package com.muriloCruz.ItGames.pagamentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muriloCruz.ItGames.pagamentos.dto.CheckoutItemDto;
import com.muriloCruz.ItGames.pagamentos.dto.StripeResponse;
import com.muriloCruz.ItGames.pagamentos.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/pagamento")
public class StripeApiController {

	@Autowired
	private StripeService service;
	
	@PostMapping("criar-checkout-secao")
	public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkItemDtoList) throws StripeException{
		Session session = service.createSession(checkItemDtoList);
		StripeResponse stripeResponse = new StripeResponse(session.getId());
		return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
	}
}
