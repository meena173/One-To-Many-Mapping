package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController
{	
	@Autowired
	ProductRepo cr;	
	
	@GetMapping
	public Page<Product> getAll(@PageableDefault(sort="name", direction=Sort.Direction.ASC)Pageable page)
	{		
		return cr.findAll(page);
	}
		
	@GetMapping("/{id}")
	public Product getId(@PathVariable int id)
	{
		return cr.findById(id).orElse(null);
	}

	@PostMapping
	public Product saveAll(@RequestBody Product ce)
	{		
		return cr.save(ce);		
	}
	
	@PutMapping("/{id}")
	public Product putId(@PathVariable int id, @RequestBody Product c)
	{
		Product ce = cr.findById(id).orElseThrow();
		ce.setCategory(c.getCategory());
		ce.setName(c.getName());
		
		return cr.save(ce);
				
	}	
	
	@DeleteMapping("/{id}")
	public void dleteId(@PathVariable int id)
	{
		cr.deleteById(id);
	}	
}