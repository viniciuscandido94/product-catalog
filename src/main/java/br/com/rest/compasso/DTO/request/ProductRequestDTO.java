package br.com.rest.compasso.DTO.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @ApiModelProperty(dataType = "String", example = "Tensor", required = true)
    @NotBlank(message = "Campo 'name' é obrigatório o preenchimento")
    @Size(max = 100)
    private String name;

    @ApiModelProperty(dataType = "String", example = "Rolamento Tensor da Correia Dentada do Gol 1.0 8V", required = true)
    @NotBlank(message = "Campo 'description' é obrigatório o preenchimento")
    private String description;

    @ApiModelProperty(dataType = "BigDecimal", example = "15.90", required = true)
    @NotNull(message = "Campo 'price' é obrigatório o preenchimento")
    @Digits(integer = 18, fraction = 2)
    @Positive(message = "Campo 'price' deve ser positivo")
    private BigDecimal price;
}
