package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController 
{

    @Autowired
    ProductRepo pr;
    
    @GetMapping("/all")
    public List<Product> getAllProducts() 
    {
        return pr.findAll();
    }
    
    @GetMapping("/page/{pageNo}/{pageSize}")
    public List<Product> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize) 
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> pagedResult = pr.findAll(pageable);
        
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Product>();
    }

  
    @GetMapping("/sort")
    public List<Product> getAllSorted(@RequestParam String field, @RequestParam String direction)
    {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return pr.findAll(Sort.by(sortDirection, field));
    }


    @GetMapping("/page/{pageNo}/{pageSize}/sort")
    public List<Product> getPaginatedAndSorted(@PathVariable int pageNo, @PathVariable int pageSize, 
                                               @RequestParam String sortField, @RequestParam String sortDir) 
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> pagedResult = pr.findAll(pageable);
        
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Product>();
    }


    @GetMapping("/{id}")
    public Product getId(@PathVariable int id) 
    {
        return pr.findById(id).orElse(null);
    }

    @PostMapping
    public Product saveAll(@RequestBody Product p) 
    {
        return pr.save(p);
    }

    @PutMapping("/{id}")
    public Product putId(@PathVariable int id, @RequestBody Product p) 
    {
        Product existingProduct = pr.findById(id).orElseThrow();
        existingProduct.setCategory(p.getCategory());
        existingProduct.setName(p.getName());

        return pr.save(existingProduct);
    }


    @DeleteMapping("/{id}")
    public void deleteId(@PathVariable int id) 
    {
        pr.deleteById(id);
    }
}
