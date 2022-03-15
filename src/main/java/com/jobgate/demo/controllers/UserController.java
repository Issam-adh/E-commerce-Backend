package com.jobgate.demo.controllers;

import com.jobgate.demo.Models.Category;
import com.jobgate.demo.Models.Response;
import com.jobgate.demo.Models.User;
import com.jobgate.demo.dao.IUser;
import com.jobgate.demo.utils.StorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "jobgate")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IUser iUser;

    @Autowired
    private StorageService storageService;

    /*@PostMapping("/addUser")
    public Response<User> addUser(User p, @RequestParam("file") MultipartFile file) {
        //return new Response<User>("200", "Create user", iUser.save(p));

        try {
            if(p!=null) {
                Date d=new Date();
                String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
                p.setPhoto(date+file.getOriginalFilename());
                storageService.store(file,date+file.getOriginalFilename());
                return new Response<User>("200", "Create user", iUser.save(p));
            } else {
                return new Response<User>("500", "User not found", null);
            }
        } catch (Exception e) {
            return new Response<User>("406", e.getMessage(), null);
        }

    }*/
    @CrossOrigin("*")
    @GetMapping("/{id}")
    public Response<User> getUserByUd(@PathVariable(value = "id") long id) {
        Optional<User> userOptional = iUser.findById(id);
        try {
            if (userOptional.isPresent()) {
                iUser.findById(id);
                return new Response<User>("200", " User found with id:" + id, iUser.getById(id));
            } else {
                return new Response<User>("404", "\"User not found with id :" + id, null);
            }

        } catch (Exception e) {
            return new Response<>("406", "Error finding User", null);

        }
    }


    @PostMapping("/")
    public Response<User> addUser(User u,@RequestParam("file") MultipartFile file) {

        Date d=new Date();
        String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
        u.setPhoto(date+file.getOriginalFilename());
        storageService.store(file,date+file.getOriginalFilename());
        return  new Response<User>("200","create category",iUser.save(u));
    }

    // upload many files at a time

    @PostMapping("/uploads")
    public Response<User> addUser(User p, @RequestParam("file") MultipartFile[] files) {
        //return new Response<User>("200", "Create user", iUser.save(p));

        try {
            Date d=new Date();
            String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();

            if(files!=null) {

                Arrays.asList(files).stream().forEach(

                        file-> storageService.store(file,date+file.getOriginalFilename()));

                return new Response<User>("200", "Files uploaded", null);
            } else {
                return new Response<User>("500", "Files are not found", null);
            }
        } catch (Exception e) {
            return new Response<User>("406", e.getMessage(), null);
        }

    }

    @DeleteMapping("/deleteUser/{id}")
    public Response<User> deleteUser(@PathVariable("id") long id) {


        try{
            iUser.deleteById(id);
            return new Response<User>("200", "User deleted", null);
        } catch (Exception e){
            return new Response<User> ("406",e.getMessage(),null);
        }     }

    /*@PutMapping("updateUser/{id}")
    public Response<User> updateUser(@PathVariable("id") Long id,@RequestBody User p) {
        Optional<User> user = iUser.findById(p.getId());
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setFirstName(p.getFirstName());
            newUser.setLastName(p.getLastName());
            newUser.setEmail(p.getEmail());
            newUser.setPassword(p.getPassword());
            iUser.save(newUser);
            try {
                return new Response<User>("200", "Update user", iUser.saveAndFlush(p));
            } catch (Exception e) {
                return new Response<User>("406", e.getMessage(), iUser.saveAndFlush(p));
            }

        }
        try {
            return new Response<User>("200", "Update user", iUser.saveAndFlush(p));
        } catch (Exception e) {
            return new Response<User>("406", e.getMessage(), iUser.saveAndFlush(p));

        }
    }*/


    @PutMapping("/{id}")
    public Response<User> updateUser(User u,@RequestParam("file") MultipartFile file) {
        Date d = new Date();
        String date = "" + d.getDay() + d.getMonth() + d.getYear() + d.getHours() + d.getMinutes() + d.getSeconds();
        u.setPhoto(date + file.getOriginalFilename());
        storageService.store(file, date + file.getOriginalFilename());

        return new Response<User>("200", "Update User", iUser.saveAndFlush(u));

    }

    @GetMapping("/")
    public Response<List<User>> findAllProduct() {

        try {
            return new Response<List<User>>("200", "Get all users", iUser.findAll());
        } catch (Exception e) {
            return new Response<List<User>>("406", e.getMessage(), iUser.findAll());

        }
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
