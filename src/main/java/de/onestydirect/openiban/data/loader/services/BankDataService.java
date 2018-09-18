package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.models.BankData;

import java.util.List;

public interface BankDataService {

	List<BankData> getBankDataBySource(String source);

	void removeAllBankData(List<BankData> bankDataList);

	void saveBankData(BankData bankData);
}
