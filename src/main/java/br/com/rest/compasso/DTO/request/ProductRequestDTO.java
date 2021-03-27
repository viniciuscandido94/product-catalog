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
    @NotBlank
    @Size(max = 100)
    private String name;

    @ApiModelProperty(dataType = "String", example = "Rolamento Tensor da Correia Dentada do Gol 1.0 8V", required = true)
    @NotBlank
    private String description;

    @ApiModelProperty(dataType = "BigDecimal", example = "15.90", required = true)
    @NotNull
    @Digits(integer = 18, fraction = 2)
    @Positive
    private BigDecimal price;
}
