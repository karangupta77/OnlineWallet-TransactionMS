package com.cg.onlinewallet.dao;

import com.cg.onlinewallet.entities.WalletTransactions;

public interface OnlineWalletDao {

	WalletTransactions getTransaction(Integer transactionId);

	void saveTransaction(WalletTransactions transaction);

}