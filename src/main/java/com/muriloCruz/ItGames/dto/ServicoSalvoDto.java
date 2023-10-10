package com.muriloCruz.ItGames.dto;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.entity.Postagem;
import com.muriloCruz.ItGames.entity.enums.Disponibilidade;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServicoSalvoDto {
	
	@Positive(message = "O ID deve ")
	@NotNull(message = "O ID é obrigatório")
	private Integer id;

	@NotNull(message = "A descrição não pode ser nula")
	private String descricao;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "O preco deve ser posistivo")
    @Digits(integer = 10, fraction = 2, message = "O preco deve possuir o formato 'NNNNNNNNNN.NN'")
    @NotNull(message = "O preco não pode ser nulo")
	private BigDecimal preco;
	
	@NotNull(message = "A disponibilidade é obrigatória")
	private Disponibilidade disponibilidade;
	
	@Positive(message = "O ID do jogo deve ser maior que 0")
	@NotNull(message = "O ID do jogo é obrigatório")
	private Integer idDojogo;
	
	@NotNull(message = "A postagem é obrigatória")
	private Postagem postagem;
}
