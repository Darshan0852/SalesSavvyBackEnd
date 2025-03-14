package com.example.demo.AdminController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AdminServices.AdminUserService;
import com.example.demo.entities.User;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminUserController {

	private final AdminUserService adminUserService;
	
	public AdminUserController(AdminUserService adminUserService) {
		// TODO Auto-generated constructor stub
		this.adminUserService=adminUserService;
	}
	 @PostMapping("/getbyid")
	    public ResponseEntity<?> getUserById(@RequestBody Map<String, Integer> userRequest) {
	    	 try {
	             Integer userId = userRequest.get("userId");
	             User user = adminUserService.getUserById(userId);
	             return ResponseEntity.status(HttpStatus.OK).body(user);
	         } catch (IllegalArgumentException e) {
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	         }
	     }
	 
	 @PostMapping("/modify")
	 public ResponseEntity<?> modifyUser(@RequestBody Map<String ,Object> userRequest) {
		 try {
			 Integer userId = (Integer) userRequest.get("userId");
			 String username = (String) userRequest.get("username");
			 String emial = (String) userRequest.get("email");
			 String role = (String) userRequest.get("role");
			 
			 User updatedUser = adminUserService.modifyUser(userId, username, emial, role);
			 Map<String, Object> response = new HashMap<>();
			 
             response.put("userId", updatedUser.getUser_id());
             response.put("username", updatedUser.getUsername());
             response.put("email", updatedUser.getEmail());
             response.put("role", updatedUser.getRole().name());
             response.put("createdAt", updatedUser.getCreated_at());
             response.put("updatedAt", updatedUser.getUpdated_at());
             return ResponseEntity.status(HttpStatus.OK).body(response);
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
         }
 
		 
	 }
	
}
