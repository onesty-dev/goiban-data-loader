package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.models.BankData;

import java.util.List;

public interface ExcelLoaderService {
	List<BankData> getAllBankDataFromExcelFile();
}
