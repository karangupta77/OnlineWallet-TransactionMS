/**
* @author:Karan Gupta
* Description: This is a controller class which handles the web requests made by the user and then maps it according to the required handler method.
*/
package com.cg.onlinewallet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinewallet.service.*;
@CrossOrigin(origins="*")
@RestController
public class OnlineWalletController {

	@Autowired
	private OnlineWalletService onlineWalletService;

	public OnlineWalletController() {
		// TODO Auto-generated constructor stub
	}
    
	@RequestMapping("/home")
	public String check() {
		return "WORKING";
	}
/**
* Method:transactMoney
* Description:To map the request of user for transferring the amount from one user to another user account
* @param userId:User's Id
* @param amount:Amount to be transferred
* @returns Entity:After transferring,it will give the message that transaction is completed
* Created By-Karan Gupta
*/
	@GetMapping("/transactmoney/{userId}")
	public ResponseEntity<String> transactMoney(@PathVariable("userId") Integer userId, Double amount,
			String email) {
		onlineWalletService.transactMoney(userId, email, amount);
		return new ResponseEntity<String>("Transaction Completed", HttpStatus.OK);
	}
}