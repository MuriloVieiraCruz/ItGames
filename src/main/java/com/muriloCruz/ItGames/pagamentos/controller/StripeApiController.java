package com.muriloCruz.ItGames.pagamentos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.muriloCruz.ItGames.pagamentos.dto.StripeChargeDto;
import com.muriloCruz.ItGames.pagamentos.dto.StripeTokenDto;
import com.muriloCruz.ItGames.pagamentos.service.StripeService;

@RestController
@RequestMapping("/stripe")
public class StripeApiController {

	@Autowired
	private StripeService service;
	
	@ResponseBody
	@PostMapping("/card/token")
	public StripeTokenDto criarTokenDoCartaoPor(@RequestBody StripeTokenDto model) {
		return service.criarTokenDoCartao(model);
	}
	
	@ResponseBody
	@PostMapping("/charge")
	public StripeChargeDto charge(@RequestBody StripeChargeDto model) {
		return service.charge(model);
	}
}
