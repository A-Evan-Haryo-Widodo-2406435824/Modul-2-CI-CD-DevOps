package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp(){
        product = new Product();
        product.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product.setProductName("barang-1");
        product.setProductQuantity(20);
    }

    @Test
    void testCreateProductSucceed(){
        when(productRepository.create(product)).thenReturn(product);

        Product productCreated = productService.create(product);
        assertEquals(product.getProductId(), productCreated.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testCreateProductNullId(){
        product.setProductId(null);

        Product productCreated = productService.create(product);

        assertNotNull(productCreated.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAll(){
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertEquals(productList.getFirst(), result.getFirst());
        assertEquals(1, result.size());
        verify(productRepository).findAll();

    }

    @Test
    void testFindById(){
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product result = productService.findById(product.getProductId());
        assertEquals(product, result);
        verify(productRepository).findById(product.getProductId());
    }

    @Test
    void testUpdate(){
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);
        assertEquals(product, result);
        verify(productRepository).update(product);
    }

    @Test
    void testDelete(){
        when(productRepository.delete(product.getProductId())).thenReturn(true);

        Boolean result = productService.delete(product.getProductId());
        assertTrue(result);
        verify(productRepository).delete(product.getProductId());
    }
}
