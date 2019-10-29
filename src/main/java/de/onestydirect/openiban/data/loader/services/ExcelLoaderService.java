package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.model.BankData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExcelLoaderService {
	List<BankData> getAllBankDataFromExcelFile();
}
