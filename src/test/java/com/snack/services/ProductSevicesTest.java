package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSevicesTest {
    private ProductService productService;
    private Product product1;


    @BeforeEach
    public void setup() {
        productService = new ProductService();
        product1 = new Product(1, "Hot Dog", 10.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\cachorro.jpeg");
    }

    @Test
    public void salvarUmProdutoComImagemValida() {

        boolean save = productService.save(product1);

        assertTrue(save);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");
    }

    @Test
    public void salvarUmProdutoComImagemInexistente() {
        Product product3 = new Product(1, "Pastel", 12.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\inexistente.jpeg");

        boolean save = productService.save(product3);

        assertFalse(save);
    }

    @Test
    public void atualizarUmProdutoExistente() {
        productService.save(product1);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");

        Product product3 = new Product(1, "Pastel", 12.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\pastel.jpeg");

        productService.update(product3);

        String newImagePathProductID = productService.getImagePathById(1);

        assertEquals(newImagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");
    }

    @Test
    public void removerUmProdutoExistente() {
        boolean save = productService.save(product1);

        assertTrue(save);

        productService.remove(1);

        assertThrowsExactly(NoSuchElementException.class, () -> {
            String imagePathProductID = productService.getImagePathById(1);
        });
    }

    @Test
    public void obterCaminhoDaImagemPorID() {
        productService.save(product1);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");
    }
}

