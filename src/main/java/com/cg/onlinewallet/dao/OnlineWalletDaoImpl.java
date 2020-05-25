package com.cg.onlinewallet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.cg.onlinewallet.entities.*;

@Repository
public class OnlineWalletDaoImpl implements OnlineWalletDao {
	@PersistenceContext
	private EntityManager entityManager;

	public OnlineWalletDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveTransaction(WalletTransactions transaction) {
		entityManager.persist(transaction);
	}
	
	@Override
	public WalletTransactions getTransaction(Integer transactionId) {
		WalletTransactions transaction = entityManager.find(WalletTransactions.class, transactionId);
		return transaction;
	}
}