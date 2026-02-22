package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp(){
        product = new Product();
        product.setProductId("bab96a53-6f22-4a52-bb13-0b59890352c1");
        product.setProductName("barang-1");
        product.setProductQuantity(20);
    }

    @Test
    void testCreateProductPage(){
        String viewName = productController.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost(){
        String viewName = productController.createProductPost(product, model);
        verify(productService).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage(){

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);

        verify(productService).findAll();
        verify(model).addAttribute("products", productList);

        assertEquals("productList", viewName);
    }

    @Test
    void testEditProductPage(){
        when(productService.findById(product.getProductId())).thenReturn(product);

        String viewName = productController.editProductPage(product.getProductId(), model);
        verify(productService).findById(product.getProductId());
        verify(model).addAttribute("product", product);

        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPost(){
        String viewName = productController.editProductPost(product);
        verify(productService).update(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testDeleteProduct(){
        String viewName = productController.deleteProduct(product.getProductId());

        verify(productService).delete(product.getProductId());
        assertEquals("redirect:/product/list", viewName);
    }

}
