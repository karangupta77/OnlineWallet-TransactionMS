package com.cg.onlinewallet.service;

import java.util.List;

public interface OnlineWalletService {

	void transactMoney(Integer userId, String beneficiaryLoginName, Double amount);

}