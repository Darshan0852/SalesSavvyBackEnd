package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int categoryId;

	@Column(name="category_name")
	String categoryName;

	public Category(int category_id, String category_name) {
		super();
		this.categoryId = category_id;
		this.categoryName = category_name;
	}

	public Category(String category_name) {
		super();
		this.categoryName = category_name;
	}

public Category() {
	// TODO Auto-generated constructor stub
}

public int getCategory_id() {
	return categoryId;
}

public void setCategory_id(int category_id) {
	this.categoryId = category_id;
}

public String getCategory_name() {
	return categoryName;
}

public void setCategory_name(String category_name) {
	this.categoryName = category_name;
}

}
