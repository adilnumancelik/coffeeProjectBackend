package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CoffeeRep;
import com.example.demo.OrderRep;
import com.example.demo.UserRep;
import com.example.demo.model.Coffee;
import com.example.demo.model.Order;
import com.example.demo.model.User;

@RestController
public class CoffeeController {
	@Autowired
	CoffeeRep cofdao=new CoffeeRep();
	@Autowired 
	UserRep usdao=new UserRep();
	@Autowired 
	OrderRep ordao=new OrderRep();
	
	@RequestMapping(value ="/giveorder", method = RequestMethod.GET)
	public ResponseEntity<Object> home(){
		return new ResponseEntity<>("Please Give Order", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ordergiven", method = RequestMethod.PUT)
	public ResponseEntity<Object> orderDetails(@RequestBody List<Order> list) {
		String username;
		String coffeename;
		int amount;
		for(int i=0 ;i<list.size() ; i++) {
			username=list.get(i).getUsername();
			coffeename=list.get(i).getCoffeename();
			amount=list.get(i).getAmount();
			cofdao.update(coffeename, amount);
			ordao.save(list.get(i));
		}
	      return new ResponseEntity<>(list, HttpStatus.CREATED);
	}

	@PostMapping("/addcoffee")
    public Coffee orderDetails(@RequestBody Coffee coffee) {
		cofdao.save(coffee);
        return cofdao.getCoffee(coffee.getName());
   }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	   public boolean checkCredentials(@RequestBody User user) {
	      if(usdao.userFound(user.getName())) {
	    	  User a=usdao.getUser(user.getName());
	    	  if(a.getPassword().equals(user.getPassword())) {
		    	  return true;
		      } else {
		    	  return false;
		      }
	      } 
	      return false;
	      
	     
	     
	   }
}

