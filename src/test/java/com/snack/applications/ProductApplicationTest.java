package com.snack.applications;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductApplicationTest {
    private ProductRepository productRepository;
    private ProductService productService;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
        product1 = new Product(1, "Hot Dog", 10.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\cachorro.jpeg");
        product2 = new Product(2, "Pastel", 12.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\pastel.jpeg");
    }

    @Test
    public void listarTodosOsProdutos() {
        productRepository.append(product1);
        productRepository.append(product2);

        List<Product> products = productRepository.getAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

    }

    @Test
    public void buscarPorId() {
        productRepository.append(product1);

        Product productId1 = productRepository.getById(1);

        assertEquals(productId1, product1);
    }

    @Test
    public void retornarNuloOuErroAoTentarObterProdutoPorIDInvalido() {
        productRepository.append(product1);

        assertThrowsExactly(NoSuchElementException.class, () -> {
            Product productId1 = productRepository.getById(3);
        });
    }

    @Test
    public void verificarSeUmProdutoExistePorIDValido() {
        productRepository.append(product1);

        boolean productId1 = productRepository.exists(1);

        assertTrue(productId1);
    }

    @Test
    public void retornarFalsoAoVerificarAExistenciaDeUmProdutoComIDInvalido() {
        boolean productId1 = productRepository.exists(1);

        assertFalse(productId1);
    }

    @Test
    public void adicionarUmNovoProdutoESalvarSuaImagemCorretamente (){
        productRepository.append(product1);
        boolean save = productService.save(product1);

        assertTrue(save);

        Product productId = productRepository.getById(1);

        assertEquals(productId, product1);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");

    }

    @Test
    public void removerUmProdutoExistenteEDeletarSuaImagem (){
        productRepository.append(product1);
        boolean save = productService.save(product1);

        assertTrue(save);

        productRepository.remove(1);
        productService.remove(1);

        assertThrowsExactly(NoSuchElementException.class, () -> {
            Product productId1 = productRepository.getById(1);
        });

        assertThrowsExactly(NoSuchElementException.class, () -> {
            String imagePathProductID = productService.getImagePathById(1);
        });

    }

    @Test
    public void  NaoAlterarOSistemaAoTentarRemoverUmProdutoComIDInexistente (){
        productRepository.append(product1);
        boolean save = productService.save(product1);

        assertTrue(save);

        Product productId = productRepository.getById(1);

        assertEquals(productId, product1);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");

        productRepository.remove(2);

        assertThrowsExactly(NoSuchElementException.class, () -> {
            productService.remove(2);
        });


        Product productIdNew = productRepository.getById(1);

        assertEquals(productIdNew, product1);

        String imagePathProductIDNew = productService.getImagePathById(1);

        assertEquals(imagePathProductIDNew, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");

    }

    @Test
    public void atualizarUmProdutoExistenteESubstituirSuaImagem(){

        productRepository.append(product1);
        productService.save(product1);

        Product productId = productRepository.getById(1);

        assertEquals(productId, product1);

        String imagePathProductID = productService.getImagePathById(1);

        assertEquals(imagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");

        productRepository.update(1, product2);

        Product newProductId1 = productRepository.getById(1);

        assertEquals(newProductId1.getPrice(), product2.getPrice());
        assertEquals(newProductId1.getDescription(), product2.getDescription());
        assertEquals(newProductId1.getImage(), product2.getImage());

        Product product3 = new Product(1, "Pastel", 12.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\pastel.jpeg");

        productService.update(product3);

        String newImagePathProductID = productService.getImagePathById(1);

        assertEquals(newImagePathProductID, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\img\\1.jpeg");
    }


}
