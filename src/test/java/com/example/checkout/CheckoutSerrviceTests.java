package com.example.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.checkout.entity.ProductDiscountEntity;
import com.example.checkout.entity.ProductEntity;
import com.example.checkout.repository.ProductRepository;
import com.example.checkout.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

@SpringBootTest
class CheckoutSerrviceTests {

	private final ProductEntity firstProduct = new ProductEntity();
	
	private final ProductDiscountEntity firstProductDiscount = new ProductDiscountEntity();

	private final ProductEntity secondProduct = new ProductEntity();

	private final ProductDiscountEntity secondProductDiscount = new ProductDiscountEntity();

	private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private CheckoutService checkoutService;


	@BeforeEach
	void setup() {

		firstProductDiscount
			.setCount(3L)
			.setPrice(2000.45);

		firstProduct
			.setId(1L)
			.setName("Rolex Watch")
			.setPrice(1000.05)
			.setDiscount(firstProductDiscount);

		secondProductDiscount
				.setCount(2L)
				.setPrice(120.05);

		secondProduct
				.setId(2L)
				.setName("Michael Kors Purse")
				.setPrice(80.40)
				.setDiscount(secondProductDiscount);
	}


	@Test
	void ShouldDiscountCalculateTwiceForOneProduct() {
		List<Long> productIds = List.of(1L, 1L, 1L, 1L, 1L, 1L);
		
		List<ProductEntity> products = List.of(firstProduct);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = decimalFormat.format(firstProduct.getDiscount().getPrice() * 2);
				
		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}

	@Test
	void ShouldDiscountAndAdditionalUnitMustCalculateAtSeparateProductPrices() {
		List<Long> productIds = List.of(1L, 1L, 1L, 1L);

		List<ProductEntity> products = List.of(firstProduct);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = decimalFormat.format(firstProduct.getPrice() + firstProduct.getDiscount().getPrice());

		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}

	@Test
	void ShouldCalculateCostWithDiscountForDiferentProducts() {
		List<Long> productIds = List.of(1L, 2L, 1L, 1L, 2L);

		List<ProductEntity> products = List.of(firstProduct, secondProduct);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = decimalFormat.format(firstProduct.getDiscount().getPrice() + secondProduct.getDiscount().getPrice());

		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}

	@Test
	void ShouldNotDiscountCalculateForProductsWitoutDiscount() {
		List<Long> productIds = List.of(2L, 2L, 2L);

		List<ProductEntity> products = List.of(secondProduct);

		secondProduct.setDiscount(null);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = decimalFormat.format(secondProduct.getPrice() * 3);

		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}


	@Test
	void ShouldNotCostCalculateForEmptyProductList() {
		List<Long> productIds = List.of();

		List<ProductEntity> products = List.of();

		secondProduct.setDiscount(null);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = "0.00";

		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}

	@Test
	void ShouldNotCostCalculateForNotValidProducts() {
		List<Long> productIds = List.of(150L);

		List<ProductEntity> products = List.of();

		secondProduct.setDiscount(null);

		when(productRepository.findAllById(Set.copyOf(productIds))).thenReturn(products);

		String calculatedTotalCost = checkoutService.getTotalCost(productIds).getPrice();

		String expectedTotalCost = "0.00";

		assertThat(expectedTotalCost).isEqualTo(calculatedTotalCost);
	}
}
