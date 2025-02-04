package com.sd.electronicstore.ElectronicStore.controllers;

import com.sd.electronicstore.ElectronicStore.dtos.*;
import com.sd.electronicstore.ElectronicStore.services.FileService;
import com.sd.electronicstore.ElectronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<ProductDto> createUser(@RequestBody ProductDto productDto){
        ProductDto products = productService.create(productDto);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateUser(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto updatedProduct = productService.update(productDto,productId);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        productService.delete(productId);
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setMessage("product deleted successfully");
        apiResponseMessage.setStatus(HttpStatus.OK);
        apiResponseMessage.setSuccess(true);
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber" , defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize" , defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc" ,required = false) String sortDir
    ){
        PageableResponse<ProductDto> response= productService.getall(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getProductByLive(
            @RequestParam(value = "pageNumber" , defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize" , defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc" ,required = false) String sortDir
    ){
        return new ResponseEntity<>(productService.getAllLive(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductBySearch(
            @PathVariable String subTitle,
            @RequestParam(value = "pageNumber" , defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize" , defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc" ,required = false) String sortDir
            ){
        return new ResponseEntity<>(productService.search(subTitle,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage") MultipartFile image, @PathVariable String productId) throws IOException {
        String imageName = fileService.uploadFile(image,imageUploadPath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImage(imageName);
        productService.update(productDto,productId);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setMessage("image uploaded successfully");
        imageResponse.setImageName(imageName);
        imageResponse.setStatus(HttpStatus.CREATED);
        imageResponse.setSuccess(true);

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{productId}")
    public void serveCategoryImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);
        InputStream resource = fileService.getResource(imageUploadPath,productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
