package br.com.rest.compasso.business;

import br.com.rest.compasso.DTO.request.ProductRequestDTO;
import br.com.rest.compasso.DTO.response.ProductResponseDTO;
import br.com.rest.compasso.entity.Product;
import br.com.rest.compasso.exception.BusinessException;
import br.com.rest.compasso.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductBusiness {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        products.forEach( product -> {
            productResponseDTOList.add(buildResponse(product));
        });
        return productResponseDTOList;
    }

    public ProductResponseDTO findById(Long id) {
        Product product = findAndValidateProduct(id);
        return buildResponse(product);
    }

    public List<ProductResponseDTO> findBySearch(HttpServletRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> queryGeneral = cb.createQuery(Product.class);

        Root<Product> root = queryGeneral.from(Product.class);
        Predicate predicateQParameter = null;
        Predicate predicatePrice = null;

        String qParameter = request.getParameter("q");
        BigDecimal minPrice = request.getParameter("min_price") != null ? new BigDecimal(request.getParameter("min_price")) : null;
        BigDecimal maxPrice = request.getParameter("max_price") != null ? new BigDecimal(request.getParameter("max_price")) : null;

        if (qParameter != null){
            Predicate predicateName = cb.equal(cb.lower(root.get("name")), qParameter.toLowerCase());
            Predicate predicateDescription = cb.equal(cb.lower(root.get("description")), qParameter.toLowerCase());
            predicateQParameter = cb.or(predicateName, predicateDescription);
        }

        if (minPrice != null && maxPrice != null) {
            predicatePrice = cb.between(root.get("price"), minPrice, maxPrice);
        } else {
            if (minPrice != null){
                predicatePrice = cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                predicatePrice = cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        }

        if (predicateQParameter != null && predicatePrice != null) {
            queryGeneral.where(cb.and(predicateQParameter, predicatePrice));
        } else {
            if (predicateQParameter != null) {
                queryGeneral.where(predicateQParameter);
            } else if (predicatePrice != null) {
                queryGeneral.where(predicatePrice);
            }
        }

        TypedQuery<Product> query = entityManager.createQuery(queryGeneral);
        Page<Product> products = new PageImpl<>(query.getResultList());

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        products.forEach( product -> {
            productResponseDTOList.add(buildResponse(product));
        });

        return productResponseDTOList;
    }

    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Product product = createProduct(productRequestDTO, new Product());
        return buildResponse(product);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = findAndValidateProduct(id);
        Product productUpdated = createProduct(productRequestDTO, product);
        return buildResponse(productUpdated);

    }

    public void removeProductById(Long id) {
        Product product = findAndValidateProduct(id);
        productRepository.delete(product);
    }

    private Product findAndValidateProduct(Long id)  {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " doesn't exists");
        }
        return productOptional.get();
    }

    private ProductResponseDTO buildResponse(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        return productResponseDTO;
    }

    private Product createProduct(ProductRequestDTO productRequestDTO, Product product) {
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        productRepository.save(product);
        return product;
    }
}

