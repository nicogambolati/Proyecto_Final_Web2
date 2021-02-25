package com.ecommers.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommers.exceptions.ProductNotPresentException;
import com.ecommers.models.Cart;
import com.ecommers.models.Product;
import com.ecommers.models.ProductInCart;
import com.ecommers.models.Report;
import com.ecommers.models.ReportProduct;
import com.ecommers.models.dto.CartDto;
import com.ecommers.models.dto.ProductInCartDto;
import com.ecommers.models.dto.ReportDto;
import com.ecommers.repository.ICartRepository;
import com.ecommers.repository.IProductRepository;
import com.ecommers.repository.IReportProductRepository;
import com.ecommers.repository.IReportRepository;
import com.ecommers.service.Interface.IBatchService;

@Service
public class BatchService implements IBatchService {

	private ICartRepository cartRepository;
	private IProductRepository productRepository;
	private IReportRepository reportRepository;
	private IReportProductRepository reportProductRepository;
	
	public BatchService(ICartRepository cartRepository, 
			IProductRepository productRepository, 
			IReportRepository reportRepository, 
			IReportProductRepository reportProductRepository) {
		super();
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.reportRepository = reportRepository;
		this.reportProductRepository = reportProductRepository;
	}

	@Override
	public ReportDto processedReadyCarts() {
		List<Cart> carts = this.cartRepository.findAllByStatus("READY");
		Collections.sort(carts, new Comparator<Cart>() {
			  @Override
			  public int compare(Cart c1, Cart c2) {
			    return c1.getCreationDate().compareTo(c2.getCreationDate());
			  }
		});
	
		Report report = new Report();
		Integer processed = 0;
		Integer failed = 0;
		double profit = 0;
		Set<ReportProduct> productsWithoutStock = new HashSet<ReportProduct>();
		
		for(Cart cart : carts) {
			try {				
				HashMap <Long, Double> productNewStock = new HashMap <Long, Double>();
				Integer productsWithoutStockQuantity = productsWithoutStock.size();
				
				checkProductsStock(productsWithoutStock, cart, productNewStock);
				
				if (productsWithoutStockQuantity == productsWithoutStock.size()) {
					updateProductsStock(productNewStock);
				
					cart.setStatus("PROCESSED");
					processed++;
					profit += cart.getTotal();
				} else {
					cart.setStatus("FAILED");
					failed++;
				}
			} catch (Exception e) {
				cart.setStatus("FAILED");
				failed++;
			} finally {
				this.cartRepository.save(cart);
			}			
		}
		
		report.setTotalCartsProcessed(processed);
		report.setTotalCartsFailed(failed);
		report.setProfit(profit);

		this.reportRepository.save(report);
		
		return getReportDto(report, productsWithoutStock);
	}

	private ReportDto getReportDto(Report report, Set<ReportProduct> productsWithoutStock) {
		Set<ProductInCartDto> productsResult = new HashSet<ProductInCartDto>();
		
		for (ReportProduct reportProduct : productsWithoutStock) {
			reportProduct.setReport(report);
			this.reportProductRepository.save(reportProduct);
			
			 ProductInCartDto dto = new ProductInCartDto();
			 BeanUtils.copyProperties(reportProduct, dto);
			 
			 productsResult.add(dto);
		}
		
		ReportDto result = new ReportDto();
		BeanUtils.copyProperties(report, result);
		result.setProducts(productsResult);
		return result;
	}

	private void updateProductsStock(HashMap<Long, Double> productNewStock) {
		for(Long i : productNewStock.keySet()) {
			Optional <Product> product = this.productRepository.findById(i);
			if(!product.isPresent()) {
				throw new ProductNotPresentException();
			}
			
			 Product _product = product.get();
			 _product.setStock(productNewStock.get(i));
			 this.productRepository.save(_product);
		}
	}

	private void checkProductsStock(Set<ReportProduct> productsWithoutStock, Cart cart,
			HashMap<Long, Double> productNewStock) {
		cart.getProducts().parallelStream().forEach(product -> {
			Optional <Product> _product = this.productRepository.findById(product.getProductId());
			if(!_product.isPresent()) {
				throw new ProductNotPresentException();
			}
			
			if ((_product.get().getStock() - product.getQuantity()) >= 0) {
				// si tenemos stock
				productNewStock.put(product.getProductId(), _product.get().getStock() - product.getQuantity());
			} else {
				// sino tenemos stock
				ReportProduct failedProduct = new ReportProduct();
				BeanUtils.copyProperties(product, failedProduct);						
				productsWithoutStock.add(failedProduct);
			}
		});
	}

	private List<CartDto> getCartsDto(List<Cart> carts) {
		 List <CartDto> _cartsDto = new ArrayList<CartDto>();
		 for (Cart _cart : carts) {
			 CartDto _cartDto = new CartDto();
			 BeanUtils.copyProperties(_cart, _cartDto);
			 
			 Set<ProductInCartDto> result = new HashSet<ProductInCartDto>();
			 for (ProductInCart product : _cart.getProducts() ) {
				 ProductInCartDto dto = new ProductInCartDto();
				 BeanUtils.copyProperties(product, dto);
				 result.add(dto);
			 }
			 _cartDto.setProducts(result);
			 _cartsDto.add(_cartDto);
		 }
		 return _cartsDto;	
	}
	
	@Override
	public List<CartDto> getCartsProcessed( String from, String to) {
		List<Cart> carts = new ArrayList<Cart>();
		
		try {
			carts = this.cartRepository.findAllByStatusAndCreationDateBetween("PROCESSED", 
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(from), 
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(to));
		} catch (ParseException ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return getCartsDto(carts);
	}

	@Override
	public List<CartDto> getCartsProcessed() {
		List<Cart> carts = this.cartRepository.findAllByStatus("PROCESSED");
		return getCartsDto(carts);
	}
	
}
