package com.springReactive.mongo.controller;

import com.springReactive.mongo.dto.ProductDto;
import com.springReactive.mongo.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    @GetMapping
    public Flux<ProductDto> products(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> product(@PathVariable String id){
        return service.getProduct(id);
    }

    @GetMapping("/productRange")
    public Flux<ProductDto> getProductRange(@RequestParam("min") double min,
                                            @RequestParam("max") double max){
        return service.getProductInRange(min,max);
    }

    @PostMapping
    public Mono<ProductDto> save(@RequestBody Mono<ProductDto> productDtoMono) {
        return service.saveProduct(productDtoMono);
    }
    @PutMapping("/update/{id}")
    public Mono<ProductDto> update(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
        return service.updateProduct(productDtoMono, id);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
       return service.deleteProduct(id);
    }
}
