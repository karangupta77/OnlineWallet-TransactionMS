/**
* @author:Karan Gupta
* Description: This is a service class which is providing the functionality like create transaction and transact money
*/

package com.cg.onlinewallet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.onlinewallet.dao.*;
import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.exceptions.*;

@Transactional
@Service
public class OnlineWalletServiceImpl implements OnlineWalletService {

	public OnlineWalletServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OnlineWalletDao onlineWalletDao;

/**
* Method: transactMoney 
* Description: Changes the status of account of user from active to non-active and other-way around
* @param userId:user's userId
* @param beneficiaryEmail:beneficiary's email to transfer money
* @param amount:amount to be transfered
* @throws InvalidException: it is raised if the beneficiary dosen't exist
* @throws InvalidException:it is raised if the beneficiary is not an active user
* @throws WrongValueException:it is raised if the amount is greater then the account balance of the user 
* Created By - Karan Gupta
*/
	@Override
	public void transactMoney(Integer userId, String beneficiaryEmail, Double amount) {
		if (onlineWalletDao.checkUserByEmail(beneficiaryEmail) == false)
			throw new InvalidException("The Beneficary doesn't exist");
		WalletUser beneficiary = onlineWalletDao.getUserByEmail(beneficiaryEmail);
		if (beneficiary.getAccountDetail().getUserStatus() == status.non_active)
			throw new InvalidException("The Beneficiary must be an active user");
		WalletUser user = onlineWalletDao.getUser(userId);
		if (user.getAccountDetail().getAccountBalance() < amount)
			throw new WrongValueException("The Amount cannot be greater then available Balance");
		Integer beneficiaryId = beneficiary.getUserID();
		Double beneficiaryBalance = beneficiary.getAccountDetail().getAccountBalance();
		beneficiary.getAccountDetail().setAccountBalance(beneficiaryBalance + amount);
		Double userBalance = user.getAccountDetail().getAccountBalance();
		user.getAccountDetail().setAccountBalance(userBalance - amount);
		createTransaction(userId, "Amount has been tranfered. Balance has been updated", amount);
		createTransaction(beneficiaryId, "Amount credited to your account. Balance has been updated", amount);
	}
	
/**
* Method: createTransaction 
* Description: it will give the overall description of the transaction made by the user like date,time,amount transferred etc.
* @param userId:user's userId
* @param description: description about the transaction made
* @param amount:amount to be transferred 
* Created By - Karan Gupta
*/

	public void createTransaction(Integer userId, String description, Double amount) {
		WalletUser user = onlineWalletDao.getUser(userId);
		WalletAccount account = user.getAccountDetail();
		Double balance = account.getAccountBalance();
		WalletTransactions transaction = new WalletTransactions(description, LocalDateTime.now(), amount, balance);
		List<WalletTransactions> transactionList = account.getTransactionList();
		if (transactionList == null)
			transactionList = new ArrayList<WalletTransactions>();
		transactionList.add(transaction);
		onlineWalletDao.saveTransaction(transaction);
	}
}