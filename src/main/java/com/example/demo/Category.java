package com.example.demo;


import java.util.List;

import jakarta.persistence.*;

@Entity
public class Category 
{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private List<Product> product;

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Product> getProduct() 
	{
		return product;
	}

	public void setProduct(List<Product> product)
	{
		this.product = product;
	}	

}