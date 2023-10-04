package com.muriloCruz.ItGames.pagamentos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutItemDto {

	private String productName;
	private int quantity;
	private double price;
	private long productId;
	private int userId;
}
