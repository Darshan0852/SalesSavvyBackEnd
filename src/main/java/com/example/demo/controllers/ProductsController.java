package com.example.demo.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductsController {

	@GetMapping("/getProducts")
	public List Products() {
		
		List AllProducts = new LinkedList<>();
		
		return AllProducts;
	}
}
