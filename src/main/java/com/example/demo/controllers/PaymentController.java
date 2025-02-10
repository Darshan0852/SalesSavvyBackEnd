package com.example.demo.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequestMapping("/api/payment")
@RestController
@CrossOrigin
public class PaymentController {

	@GetMapping("doPayment")
	public String Payments() {
		return "Payment Successful";
	}
	
}
