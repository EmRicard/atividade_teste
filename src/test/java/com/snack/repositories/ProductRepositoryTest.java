package com.snack.repositories;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.List;
import java.lang.IllegalArgumentException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository productRepository;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        product1 = new Product(1, "Hot Dog", 10.4f, "");
        product2 = new Product(2, "Pastel", 12.4f, "");
    }

    @Test
    public void verificarSeOProdutoEstaNoArray() {
        // Arrange
        productRepository.append(product1);

        // Act
        Product productId1 = productRepository.getById(1);
        // Assert
        assertNotNull(productId1);
    }

    @Test
    public void verificarSeEPossívelRecuperarUmProdutoUsandoSeuID() {

        productRepository.append(product2);

        Product productId2 = productRepository.getById(2);

        assertEquals(productId2, product2);
    }

    @Test
    public void confirmarSeUmProdutoExisteNoRepositório(){
        productRepository.append(product1);

        boolean exists = productRepository.exists(1);
        boolean notExistent = productRepository.exists(3);

        assertTrue(exists);
        assertFalse(notExistent);
    }

    @Test
    public void confirmarSeUmProdutoERemovido(){
        productRepository.append(product1);
        productRepository.append(product2);

        List<Product> products = productRepository.getAll();
        boolean product1Exists = productRepository.exists(1);

        assertEquals(2, products.size());
        assertTrue(product1Exists);

        products.remove(product1);
        boolean product1StillExists = productRepository.exists(1);

        assertEquals(1, products.size());
        assertFalse(product1StillExists);
    }

    @Test
    public void verificarSeUmProdutoEAtualizado(){
        productRepository.append(product1);
        Product productId1 = productRepository.getById(1);
        assertEquals(productId1, product1);

        productRepository.update(1, product2);

        Product newProductId1 = productRepository.getById(1);

        assertEquals(newProductId1.getPrice(), product2.getPrice());
        assertEquals(newProductId1.getDescription(), product2.getDescription());
        assertEquals(newProductId1.getImage(), product2.getImage());

    }

    @Test
    public void TestarSeTodosOsProdutosArmazenadosSaoRecuperadosCorretamente(){
        productRepository.append(product1);
        productRepository.append(product2);

        List<Product> products = productRepository.getAll();

        assertEquals(2, products.size());
        assertTrue(productRepository.exists(1));
        assertTrue(productRepository.exists(2));
        assertEquals(products.get(0),product1);
        assertEquals(products.get(1),product2);

    }

    @Test
    public void verificarOComportamentoAoTentarRemoverUmProdutoQueNaoExiste(){

        productRepository.append(product1);

        List<Product> products = productRepository.getAll();
        assertEquals(1, products.size());

        productRepository.remove(2);

        List<Product> products2 = productRepository.getAll();
        assertEquals(1, products2.size());

        productRepository.remove(1);

        List<Product> products3 = productRepository.getAll();
        assertEquals(0, products3.size());

    }

    @Test
    public void testarOQueAconteceAoTentarAtualizarUmProdutoQueNaoEstaNoRepositorio() {
        productRepository.append(product1);

        assertThrowsExactly(NoSuchElementException.class, () -> {
            productRepository.update(2, product2);
        });
    }

    @Test
    public void verificarSeORepositorioAceitaAAdicaoDeProdutosComIDsDuplicados(){
        productRepository.append(product1);


        assertThrowsExactly(IllegalArgumentException.class, () -> {
            productRepository.append(product1);
        });
    }

    @Test
    public void  confirmarQueORepositorioRetornaUmaListaVaziaAoSerInicializado(){
        List<Product> products = productRepository.getAll();
        assertEquals(0, products.size());
    }
}
