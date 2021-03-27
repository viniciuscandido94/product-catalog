package br.com.rest.compasso.controller;

import br.com.rest.compasso.DTO.request.ProductRequestDTO;
import br.com.rest.compasso.DTO.response.ProductResponseDTO;
import br.com.rest.compasso.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "find all products")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> productResponseDTOList = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOList);
    }

    @ApiOperation(value = "find product by id")
    @GetMapping(path="/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable(name = "id", required = true) Long id) throws Exception {
        ProductResponseDTO productResponseDTO = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @ApiOperation(value = "find products by filters (query parameters)",
                  notes = "query parameters example: '?min_price=10.5&max_price=50&q=superget', none of the parameters are required")
    @GetMapping(path="/search")
    public ResponseEntity<List<ProductResponseDTO>> findBySearch(HttpServletRequest request) throws Exception {
        List<ProductResponseDTO> productResponseDTOList = productService.findBySearch(request);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOList);
    }

    @ApiOperation(value = "create product")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDTO) throws Exception {
        ProductResponseDTO productResponseDTO = productService.create(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }


    @ApiOperation(value = "update product by id")
    @PutMapping(path="/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable(name = "id", required = true) Long id,
                                                     @Valid @RequestBody ProductRequestDTO productRequestDTO) throws Exception {
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @ApiOperation(value = "delete product by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String removeProductById(@PathVariable("id") Long id) throws Exception {
        productService.removeProductById(id);
        return "Successfully deleted!";
    }

}
