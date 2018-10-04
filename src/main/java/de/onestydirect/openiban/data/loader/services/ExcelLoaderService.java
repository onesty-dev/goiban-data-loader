package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.models.BankData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExcelLoaderService {
	List<BankData> getAllBankDataFromExcelFile();
}
