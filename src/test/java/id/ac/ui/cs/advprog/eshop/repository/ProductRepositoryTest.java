package id.ac.ui.cs.advprog.eshop.repository;


import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp(){

    }

    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());

    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        product1.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product1.setProductName("barang 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("cac96a53-6f22-4a52-bb13-0b59890352c1");
        product2.setProductName("barang 2");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());


    }

    @Test
    void testEditProduct_Success(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("sabun biore");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("sabun nivea");
        updatedProduct.setProductQuantity(20);
        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("sabun nivea", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        Product storedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("sabun nivea", storedProduct.getProductName());
        assertEquals(20, storedProduct.getProductQuantity());

    }


    @Test
    void testEditProduct_Failed_NotFound(){
        Product updatedProduct = new Product();
        updatedProduct.setProductId("ab558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("sabun niveas");
        updatedProduct.setProductQuantity(20);
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct_Success(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("sabun biore");
        product.setProductQuantity(10);
        productRepository.create(product);

        Boolean result = productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertTrue(result);
    }

    @Test
    void testDeleteProduct_Failed_NotFound(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("sabun biore");
        product.setProductQuantity(10);
        productRepository.create(product);
        Boolean result = productRepository.delete("ac558e9f-1c39-460e-8860-71af6af63bd6");
        assertFalse(result);
    }

    @Test
    void testFindByIdFailed(){
        Product product = new Product();
        product.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product result = productRepository.findById("acb96a53-6f22-4a52-bb13-0b59890352c1");
        assertNull(result);
    }


    @Test
    void testFindByIdForMoreThanOneProductsFailed(){
        Product product_1 = new Product();
        product_1.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product_1.setProductName("Sampo Cap Bambang");
        product_1.setProductQuantity(100);

        Product product_2 = new Product();
        product_2.setProductId("acb96a53-6f22-4a52-bb13-0b59890352c1");
        product_2.setProductName("Sampo Cap Dodo");
        product_2.setProductQuantity(200);


        productRepository.create(product_1);
        productRepository.create(product_2);

        Product result = productRepository.findById("acb96a53-6f22-4a52-bb13-0b59890352c1");
        assertNotNull(result);
        assertEquals(product_2.getProductId(), result.getProductId());
    }




}
