package com.eauction.seller;
import com.eauction.seller.entity.Product;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductEntityTest {

    @Test
    public void testProductEntityValidation() {
        // Create a Validator using the ValidatorFactory
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create a Product instance with invalid data to test the constraints
        Product product = new Product();
        product.setProductName("Pro"); // Less than the minimum size
        product.setProductCategory("Electronics");
        product.setStartingPrice(0); // Invalid starting price

        // Validate the Product entity against its constraints
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Check for violated constraints
        assertFalse(violations.isEmpty(), "There should be validation errors");

        // Assert individual constraints are being violated
        violations.forEach(violation -> {
            if ("productName".equals(violation.getPropertyPath().toString())) {
                assertEquals("Product Name must have minimum 5 letters, max 30", violation.getMessage());
            } else if ("startingPrice".equals(violation.getPropertyPath().toString())) {
                assertEquals("must be greater than or equal to 1", violation.getMessage());
            }
        });
    }

    @Test
    public void testProductEntityValidationSuccess() {
        // Create a Validator using the ValidatorFactory
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create a Product instance with valid data to test the constraints
        Product product = new Product();
        product.setProductName("Example Product");
        product.setProductCategory("Electronics");
        product.setStartingPrice(10);

        // Validate the Product entity against its constraints
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Check for no violated constraints
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }
}