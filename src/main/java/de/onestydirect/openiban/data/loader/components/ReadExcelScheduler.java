package de.onestydirect.openiban.data.loader.components;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.services.BankDataService;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderService;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderServiceImpl;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ReadExcelScheduler {

	private ExcelLoaderService excelLoaderService;

	private BankDataService bankDataService;

	private static final Logger log = LoggerFactory.getLogger(ReadExcelScheduler.class);

	@Autowired
	public ReadExcelScheduler(final ExcelLoaderService excelLoaderService, final BankDataService bankDataService) {
		this.excelLoaderService = excelLoaderService;
		this.bankDataService = bankDataService;
	}

	@Scheduled(cron = "*/60 * * * * *")
	public void updateBankData() {
		final List<BankData> allBySource = bankDataService.getBankDataBySource("1");
		List<BankData> insertList = excelLoaderService.getAllBankDataFromExcelFile();
		if (!insertList.isEmpty()) {
			if (!allBySource.isEmpty())
				bankDataService.removeAllBankData(allBySource);
			insertList.forEach(element -> {
				bankDataService.saveBankData(element);
			});
		}
	}
}
