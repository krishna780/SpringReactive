package com.springReactive.mongo.service;

import com.springReactive.mongo.dto.ProductDto;
import com.springReactive.mongo.entity.Product;
import com.springReactive.mongo.repository.ProductRepository;
import com.springReactive.mongo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getProducts(){
        return productRepository.findAll()
                .map(AppUtils::entityDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return productRepository.findById(id).map(AppUtils::entityDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max){
        return productRepository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return  productDtoMono.map(AppUtils::dtoEntity)
                .flatMap(productRepository::insert).map(AppUtils::entityDto);
    }
  public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){
      return   productRepository.findById(id)
                 .flatMap(p->productDtoMono.map(AppUtils::dtoEntity)
                .doOnNext(e->e.setId(id)))
                .flatMap(productRepository::save)
                .map(AppUtils::entityDto);
  }

  public Mono<Void> deleteProduct(String id){
       return productRepository.deleteById(id);
  }

}
