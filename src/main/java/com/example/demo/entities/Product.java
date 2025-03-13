package com.example.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
private	int productId;

	@Column
private	String name;
	@Column(columnDefinition = "TEXT")
private	String description;
	@Column(nullable = false, precision = 10, scale=2)
private	BigDecimal price;

	@Column
private Integer stock;

@ManyToOne
@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
private  Category category;

@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
private LocalDateTime updated_at;

public Product() {
	// TODO Auto-generated constructor stub
}

public Product(String name, String descrption, BigDecimal price, int stock, Category category, LocalDateTime updated_at) {
	super();
	this.name = name;
	this.description = descrption;
	this.price = price;
	this.stock = stock;
	this.category = category;
	this.updated_at = updated_at;
}

public Product(int product_id, String name, String descrption, BigDecimal price, int stock, Category category,
		LocalDateTime updated_at) {
	super();
	this.productId = product_id;
	this.name = name;
	this.description = descrption;
	this.price = price;
	this.stock = stock;
	this.category = category;
	this.updated_at = updated_at;
}

public int getProduct_id() {
	return productId;
}

public void setProduct_id(int product_id) {
	this.productId = product_id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDescrption() {
	return description;
}

public void setDescrption(String descrption) {
	this.description = descrption;
}

public BigDecimal getPrice() {
	return price;
}

public void setPrice(BigDecimal price) {
	this.price = price;
}

public int getStock() {
	return stock;
}

public void setStock(int stock) {
	this.stock = stock;
}

public Category getCategorie() {
	return category;
}

public void setCategorie(Category categorie) {
	this.category = categorie;
}

public LocalDateTime getUpdated_at() {
	return updated_at;
}

public void setUpdated_at(LocalDateTime updated_at) {
	this.updated_at = updated_at;
}

}
