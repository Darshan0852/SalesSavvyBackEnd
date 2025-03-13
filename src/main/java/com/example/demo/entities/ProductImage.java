package com.example.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productimages")
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int imageId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", nullable = false)
	Product product;

	@Column(nullable = false, columnDefinition = "TEXT")
	String imageUrl;

	public ProductImage(Product product, String imageUrl) {
		super();
		this.product = product;
		this.imageUrl = imageUrl;
	}

	public ProductImage(int imageId, Product product, String imageUrl) {
		super();
		this.imageUrl = imageUrl;
		this.product = product;
		this.imageId = imageId;
	}
public ProductImage() {
	// TODO Auto-generated constructor stub
}

public int getImage_id() {
	return imageId;
}

public void setImage_id(int image_id) {
	this.imageId = image_id;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public String getImage_url() {
	return imageUrl;
}

public void setImage_url(String image_url) {
	this.imageUrl = image_url;
}

}
