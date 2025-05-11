package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hello.ecommerce.dto.*;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.*;


public interface ProductService {
    void save(ProductSaveDto dto) throws JsonProcessingException;

    ProductDataResDto selectProductList(ProductListReqDto dto);

    ProductDetailResDto selectProductDetail(Long id);

    ProductResDto updateProduct(Long id, ProductSaveDto dto);

    void deleteProduct(Long id);

    ProductSearchResDto findProductsBySearch(ProductSearchReqDto dto);
    
    ProductMainResDto findForMain(int limit);

}
