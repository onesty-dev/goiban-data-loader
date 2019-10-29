package de.onestydirect.openiban.data.loader.components;

import de.onestydirect.openiban.data.loader.model.BankData;
import de.onestydirect.openiban.data.loader.services.BankDataService;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:excel.properties")
public class ReadExcelScheduler {

	private ExcelLoaderService excelLoaderService;

	private BankDataService bankDataService;

	private static final Logger logger = LoggerFactory.getLogger(ReadExcelScheduler.class);

	@Autowired
	public ReadExcelScheduler(final ExcelLoaderService excelLoaderService, final BankDataService bankDataService) {
		this.excelLoaderService = excelLoaderService;
		this.bankDataService = bankDataService;
	}

	@Scheduled(cron = "${excel.to.database.job.cron}")
	public void updateBankData() {
		logger.info("Cron job started.");
		final List<BankData> allBySource = bankDataService.getBankDataBySource("1");
		List<BankData> insertList = excelLoaderService.getAllBankDataFromExcelFile();
		if (!insertList.isEmpty()) {
			if (!allBySource.isEmpty())
				logger.info("clear database");
				bankDataService.removeAllBankData(allBySource);
			insertList.forEach(element -> {
				bankDataService.saveBankData(element);
			});
			logger.info("insert finished");
		}
	}
}
