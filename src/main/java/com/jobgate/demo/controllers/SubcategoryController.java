package com.jobgate.demo.controllers;


import com.jobgate.demo.Models.Response;
import com.jobgate.demo.Models.SubCategory;
import com.jobgate.demo.dao.ISubcategory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Subcategories")
@SecurityRequirement(name = "jobgate")
public class SubcategoryController {

    @Autowired
    private ISubcategory iSubcategory;

    @PostMapping("/addSub")
    public Response<SubCategory> addSubcategory (@RequestBody SubCategory s){

        try{
            if (s!=null) {
                return new Response<SubCategory>("200", "Subcategory Created", iSubcategory.save(s));
            } else {
                return new Response<SubCategory>("500", "Subcategory not found", iSubcategory.save(s) );
            }
        } catch (Exception e){
            return new Response<SubCategory> ("406",e.getMessage(),null);
        }    }

    @DeleteMapping("/deleteSub/{id}")
    public Response<SubCategory> deleteSubcategory (@PathVariable("id") Long id){
        iSubcategory.deleteById(id);
        try{

            return new Response<SubCategory>("200", "Subcategory deleted", null );

        } catch (Exception e){
            return new Response<SubCategory> ("406",e.getMessage(),null);
        }     }

    @PutMapping("/updateSub/{id}")
    public Response<SubCategory> updateSubcategory (@PathVariable("id") Long id, SubCategory s){
        Optional<SubCategory> subCategory = iSubcategory.findById(s.getId());
        if (subCategory.isPresent()){
            SubCategory newSubCategory = subCategory.get();
            newSubCategory.setName(s.getName());
            newSubCategory.setDescription(s.getDescription());

            iSubcategory.save(newSubCategory);
            try{

                return new Response<SubCategory>("200", "Subcategory updated", null );

            } catch (Exception e){
                return new Response<SubCategory> ("406",e.getMessage(),null);
            }         }
        try{

            return new Response<SubCategory>("200", "Subcategory updated", null );

        } catch (Exception e){
            return new Response<SubCategory> ("406",e.getMessage(),null);
        }     }

    @GetMapping("/getAllSubs")
    public Response<List<SubCategory>> findAllProduct (){

        try {
            return new Response<List<SubCategory>>("200", "Get all SubCategories", iSubcategory.findAll());
        }catch (Exception e){
            return new Response<List<SubCategory>>("406", e.getMessage(), null);

        }
    }

}
