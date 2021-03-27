package br.com.rest.compasso.service;

import br.com.rest.compasso.DTO.request.ProductRequestDTO;
import br.com.rest.compasso.DTO.response.ProductResponseDTO;
import br.com.rest.compasso.business.ProductBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductBusiness productBusiness;

    public List<ProductResponseDTO> findAll() {
        return productBusiness.findAll();
    }

    public ProductResponseDTO findById(Long id) throws Exception {
        return productBusiness.findById(id);
    }

    public List<ProductResponseDTO> findBySearch(HttpServletRequest request) {
        return productBusiness.findBySearch(request);
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        return productBusiness.create(productRequestDTO);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) throws Exception {
        return productBusiness.update(id, productRequestDTO);
    }

    public void removeProductById(Long id) throws Exception {
        productBusiness.removeProductById(id);
    }
}
