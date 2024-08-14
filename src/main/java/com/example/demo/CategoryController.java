package com.example.demo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController 
{
	@GetMapping("/all")
	public List<Category> getAllCategories() 
	{
	     System.out.println("Fetching all categories");
	     List<Category> categories = cr.findAll();
	    
	    System.out.println("Categories fetched: " + categories.size());	    
	    return categories;
	}

    @Autowired
    CategoryRepo cr;

    @GetMapping("/page/{pageNo}/{pageSize}")
    public List<Category> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize) 
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> pagedResult = cr.findAll(pageable);
        
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Category>();
    }

    @GetMapping("/sort")
    public List<Category> getAllSorted(@RequestParam String field, @RequestParam String direction) 
    {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return cr.findAll(Sort.by(sortDirection, field));
    }

    @GetMapping("/page/{pageNo}/{pageSize}/sort")
    public List<Category> getPaginatedAndSorted(@PathVariable int pageNo, @PathVariable int pageSize, 
                                                @RequestParam String sortField, @RequestParam String sortDir) 
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> pagedResult = cr.findAll(pageable);
        
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Category>();
    }

   
    @GetMapping("/{id}")
    public Category getId(@PathVariable int id)
    {
        return cr.findById(id).orElse(null);
    }

    
    @PostMapping
    public Category saveAll(@RequestBody Category c) 
    {
        Category ce = new Category();
        ce.setName(c.getName());

        List<Product> list = new ArrayList<>();

        for (Product p : c.getProduct()) 
        {
            Product pe = new Product();
            pe.setCategory(ce);
            pe.setName(p.getName());

            list.add(pe);
        }
        ce.setProduct(list);

        return cr.save(ce);
    }

  
    @PutMapping("/{id}")
    public Category putId(@PathVariable int id, @RequestBody Category c) 
    {
        Category ce = cr.findById(id).orElseThrow();
        ce.setProduct(c.getProduct());
        ce.setName(c.getName());

        return cr.save(ce);
    }

   
    @DeleteMapping("/{id}")
    public void deleteId(@PathVariable int id)
    {
        cr.deleteById(id);
    }
}
