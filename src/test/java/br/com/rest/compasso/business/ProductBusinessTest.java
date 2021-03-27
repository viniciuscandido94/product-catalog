package br.com.rest.compasso.business;

import br.com.rest.compasso.entity.Product;
import br.com.rest.compasso.repository.ProductRepository;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ProductBusinessTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ProductBusiness productBusiness;
    private ProductRepository productRepository;

    private static final Long ID_PRODUCT_VALID = 10L;
    private static final Long ID_PRODUCT_INVALID = 12L;

    @Before
    public void setup() {
        this.productRepository = EasyMock.createNiceMock(ProductRepository.class);
        this.productBusiness = new ProductBusiness(productRepository);

        EasyMock.expect(productRepository.findById(ID_PRODUCT_VALID)).andReturn(Optional.of(new Product()));
        EasyMock.expect(productRepository.findById(ID_PRODUCT_INVALID)).andReturn(Optional.empty());
    }

    @Test
    public void validationFindAndValidateProductSuccess(){
        EasyMock.replay(productRepository);
        productBusiness.findAndValidateProduct(ID_PRODUCT_VALID);
    }

    @Test
    public void validationFindAndValidateProductInvalid(){
        EasyMock.replay(productRepository);

        expectedException.expect(ResponseStatusException.class);
        expectedException.expectMessage("");

        productBusiness.findAndValidateProduct(ID_PRODUCT_INVALID);
    }
}
