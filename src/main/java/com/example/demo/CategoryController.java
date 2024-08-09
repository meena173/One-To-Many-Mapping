package com.example.demo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cateories")
public class CategoryController 
{
	@Autowired
	CategoryRepo cr;	
	
	@GetMapping
	public Page<Category> getAll(@PageableDefault(sort="name", direction=Sort.Direction.ASC)Pageable page)
	{		
		return cr.findAll(page);
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
		
		for(Product p : c.getProduct() )
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