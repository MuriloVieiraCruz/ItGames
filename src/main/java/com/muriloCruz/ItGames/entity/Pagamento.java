package com.muriloCruz.ItGames.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

import com.muriloCruz.ItGames.entity.enums.TipoPagamento;

@Data
@Entity(name = "Pagamento")
@Table(name = "pagamento")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @DecimalMin(value = "0.0", inclusive = true, message = "O valor deve ser posistivo")
    @Digits(integer = 10, fraction = 2, message = "O valor deve possuir o formato 'NNNNNNNNNN.NN'")
    @NotNull(message = "O valor não pode ser nulo")
    @Column(name = "valor")
    private BigDecimal valor;

    @NotNull(message = "A data de pagamento não pode ser nulo")
    @Column(name = "data_pag")
    private Date dataPag;

    @Size(max = 20, message = "O nome deve conter entre 3 e 100 caracteres")
    @Column(name = "tipo")
    private TipoPagamento tipo;
}
