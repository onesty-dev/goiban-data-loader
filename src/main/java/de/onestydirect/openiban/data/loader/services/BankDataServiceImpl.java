package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankDataServiceImpl implements BankDataService {

	private BankDataRepository bankDataRepository;

	public BankDataServiceImpl(BankDataRepository bankDataRepository) {
		this.bankDataRepository = bankDataRepository;
	}

	@Override
	public List<BankData> getBankDataBySource(String source) {
		return bankDataRepository.findAllBySource(source);
	}

	@Override
	public void removeAllBankData(List<BankData> bankDataList) {
		bankDataRepository.deleteAll(bankDataList);
	}

	@Override
	public void saveBankData(BankData bankData) {
		bankDataRepository.save(bankData);
	}
}
