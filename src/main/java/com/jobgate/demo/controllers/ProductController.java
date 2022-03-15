package com.jobgate.demo.controllers;

import com.jobgate.demo.Models.Order;
import com.jobgate.demo.Models.Products;
import com.jobgate.demo.Models.Response;
import com.jobgate.demo.Models.SubCategory;
import com.jobgate.demo.dao.IProduct;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@SecurityRequirement(name = "jobgate")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProduct iProduct;



    @PostMapping("/addProduct")
    public Response<Products> addProduct (@RequestBody Products p){
        try{
            if (p!=null) {
                return new Response<Products>("200", "product created", iProduct.save(p));
            } else {
                return new Response<Products>("500", "product not found", iProduct.save(p) );
            }
        } catch (Exception e){
            return new Response<Products> ("406",e.getMessage(),null);
        }
    }

    @PutMapping("/updateProduct")
    public Response<Products> updateProduct (Products p){
        Optional<Products> products = iProduct.findById(p.getId());
        if (products.isPresent()){
            Products newProduct = products.get();
            newProduct.setName(p.getName());
            newProduct.setPrice(p.getPrice());
            newProduct.setReference(p.getReference());

             iProduct.save(newProduct);
            try{
                return new Response<Products>("200", "product updated", iProduct.save(p));
            } catch (Exception e){
                return new Response<Products> ("406",e.getMessage(),null);
            }
        }
        try{
            return new Response<Products>("200", "product updated", iProduct.save(p));
        } catch (Exception e){
            return new Response<Products> ("406",e.getMessage(),null);
        }     }

    @DeleteMapping("/deleteProduct/{id}")
    public Response<Products> deleteProduct (@PathVariable("id") long  id){

         iProduct.deleteById(id);
        try{
            return new Response<Products>("200", "product deleted", null);
        } catch (Exception e){
            return new Response<Products> ("406",e.getMessage(),null);
        }    }

    @GetMapping("/")
    public Response<List<Products>> findAllProduct (){

        try {
            return new Response<List<Products>>("200", "Get all products", iProduct.findAll());
        }catch (Exception e){
            return new Response<List<Products>>("406", e.getMessage(), iProduct.findAll());

        }      }
}
