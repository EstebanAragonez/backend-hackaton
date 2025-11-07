package com.backend.hackaton.product.presentation;

import com.backend.hackaton.product.application.dto.ProductRequest;
import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.application.usecase.CreateProductUseCase;
import com.backend.hackaton.product.application.usecase.DeleteProductUseCase;
import com.backend.hackaton.product.application.usecase.GetProductUseCase;
import com.backend.hackaton.product.application.usecase.ListProductsUseCase;
import com.backend.hackaton.product.application.usecase.ToggleProductStatusUseCase;
import com.backend.hackaton.product.application.usecase.UpdateProductUseCase;
import com.backend.hackaton.shared.security.JwtUserExtractor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ToggleProductStatusUseCase toggleProductStatusUseCase;
    private final JwtUserExtractor jwtUserExtractor;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            ListProductsUseCase listProductsUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase,
            ToggleProductStatusUseCase toggleProductStatusUseCase,
            JwtUserExtractor jwtUserExtractor) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.toggleProductStatusUseCase = toggleProductStatusUseCase;
        this.jwtUserExtractor = jwtUserExtractor;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        ProductResponse response = createProductUseCase.execute(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        ProductResponse response = getProductUseCase.execute(userId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) Boolean activeOnly) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        List<ProductResponse> products = listProductsUseCase.execute(userId, activeOnly);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        ProductResponse response = updateProductUseCase.execute(userId, id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponse> toggleProductStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        ProductResponse response = toggleProductStatusUseCase.execute(userId, id, active);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        deleteProductUseCase.execute(userId, id);
        return ResponseEntity.noContent().build();
    }
}

