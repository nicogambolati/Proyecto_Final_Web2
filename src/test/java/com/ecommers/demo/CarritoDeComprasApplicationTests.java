package com.ecommers.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommers.models.Cart;
import com.ecommers.repository.ICartRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarritoDeComprasApplicationTests {
	

	//@Autowired
	//private CartService cartService;
	
	@Autowired
	private ICartRepository cartRepo;

	
	public CarritoDeComprasApplicationTests() {
	}

	
    @Before
    public void seEjecutaAntesDeCadaTest() {
    }

    @BeforeClass
    public static void seEjecutaUnaSolaVezAlPrincipio() {
    }
    
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test()
    public void testCartSearch() {
    	
    	List <Cart> carts=this.cartRepo.findAll();
    	assertNotNull(carts);
    	assertEquals(2,carts.size());
    	assertEquals(0, this.cartRepo.findAllByStatus("PROCESSED").size());
    	assertEquals(1, this.cartRepo.findAllByStatus("NEW").size());
    	assertEquals(0, this.cartRepo.findAllByStatus("FAILED").size());
    	assertEquals(1, this.cartRepo.findAllByStatus("READY").size());
    	
    }
    
    
    @Test
	public void cartCheckoutWithInvalidIdThrowException() {

	}
    
    @After
    public void seEjecutaDespuesDeCadaTest() {
    }
 
    @AfterClass
    public static void seEjecutaUnaSolaVezAlFinal() {
    }
	
}
