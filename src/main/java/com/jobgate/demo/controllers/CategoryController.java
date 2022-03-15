package com.jobgate.demo.controllers;


import com.jobgate.demo.Models.Category;
import com.jobgate.demo.Models.Response;
import com.jobgate.demo.dao.ICategory;
import com.jobgate.demo.utils.StorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@SecurityRequirement(name = "jobgate")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private ICategory iCategory;

    @Autowired
    private StorageService storageService;

   /* @PostMapping("/addCategory")
    public Response<Category> addCategory(@RequestBody Category c) {
        try{
            if (c!=null) {
                return new Response<Category>("200", "Category created", iCategory.save(c));
            } else {
                return new Response<Category>("500", "Category not found", iCategory.save(c) );
            }
        } catch (Exception e){
            return new Response<Category> ("406",e.getMessage(),null);
        }
    }*/

    /*@PostMapping("/")
    public Response<Category> addCategory(Category c,@RequestParam("file") MultipartFile file) {

        Date d=new Date();
        String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
        c.setPhoto(date+file.getOriginalFilename());
        storageService.store(file,date+file.getOriginalFilename());
        return  new Response<Category>("200","create categroy",iCategory.save(c));}*/

    @PostMapping("/")
    public Response<Category> addCategory(Category c,@RequestParam("file") MultipartFile file) {

        Date d=new Date();
        String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
        c.setPhoto(date+file.getOriginalFilename());
        storageService.store(file,date+file.getOriginalFilename());
        return  new Response<Category>("200","create category",iCategory.save(c));
    }

    @DeleteMapping("/deleteCategory/{id}")
    public Response<Category> deleteCategory(@PathVariable("id") long id) {


        try{
            iCategory.deleteById(id);
                return new Response<Category>("200", "Category deleted", null);
        } catch (Exception e){
            return new Response<Category> ("406",e.getMessage(),null);
        }     }


    /*@PutMapping("/updateCategory/")
    public Response<Category> updateCategory(Category c) {
        Optional<Category> category = iCategory.findById(c.getId());
        if (category.isPresent()){
            Category newCategory = category.get();
            newCategory.setName(c.getName());
            newCategory.setDescription(c.getDescription());

            iCategory.save(newCategory);
            try{
                return new Response<Category>("200", "Category updated", null);
            } catch (Exception e){
                return new Response<Category> ("406",e.getMessage(),null);
            }     }
        try{
            return new Response<Category>("200", "Category updated", null);
        } catch (Exception e){
            return new Response<Category> ("406",e.getMessage(),null);
        }     }*/


    @GetMapping("/")
    public Response<List<Category>> findAllProduct (){
       // return /*iCategory.findAll();*/

        try {

            return new Response<List<Category>>("200", "Get all Categories", iCategory.findAll());
        }catch (Exception e){
            return new Response<List<Category>>("406", e.getMessage(), null);

        }     }

    @GetMapping("/getName")
    public Response<List<Category>> getAllCategoryByName(String name) {

        System.out.println(name);
        return  new Response<List<Category>>("200","create categroy",iCategory.getAllByName(name));

    }

    @GetMapping("/pages/{page}/{size}")
    public Response<Page<Category>> getAllCategories(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageable= PageRequest.of(page,size);
        return  new Response<Page<Category>>("200","create categroy",iCategory.getAllCategory(pageable));

    }

    @GetMapping("/filter/{page}/{size}")
    public Response<Page<Category>> getAllCategoriesByFilter(@PathVariable("page") int page, @PathVariable("size") int size,String x) {
        Pageable pageable=PageRequest.of(page,size);
        return  new Response<Page<Category>>("200","create categroy",iCategory.getAllCategoryByfilter("%"+x+"%",pageable));

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/{id}")
    public Response<Category> getCategoryById(@PathVariable(value = "id") long id) {
        Optional<Category> categoryOptional = iCategory.findById(id);
        try {
            if (categoryOptional.isPresent()) {
                iCategory.findById(id);
                return new Response<Category>("200", " Category found with id:" + id, iCategory.getById(id));
            } else {
                return new Response<Category>("404", "\"Category not found with id :" + id, null);
            }

        } catch (Exception e) {
            return new Response<>("406", "Error finding Category", null);

        }
    }

    @PutMapping("/{id}")
    public Response<Category> updateCategory(Category c,@RequestParam("file") MultipartFile file) {
        Date d = new Date();
        String date = "" + d.getDay() + d.getMonth() + d.getYear() + d.getHours() + d.getMinutes() + d.getSeconds();
        c.setPhoto(date + file.getOriginalFilename());
        storageService.store(file, date + file.getOriginalFilename());

        return new Response<Category>("200", "create categroy", iCategory.saveAndFlush(c));

    }
}
