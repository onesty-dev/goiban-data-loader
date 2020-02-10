package de.onestydirect.openiban.data.loader.components;

import de.onestydirect.openiban.data.loader.model.BankData;
import de.onestydirect.openiban.data.loader.services.BankDataService;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:excel.properties")
@Slf4j
public class ReadExcelScheduler {

	private ExcelLoaderService excelLoaderService;

	private BankDataService bankDataService;

	@Autowired
	public ReadExcelScheduler(final ExcelLoaderService excelLoaderService, final BankDataService bankDataService) {
		this.excelLoaderService = excelLoaderService;
		this.bankDataService = bankDataService;
	}

	@Scheduled(cron = "${excel.to.database.job.cron}")
	public void updateBankData() {
		log.info("Cron job started.");
		final List<BankData> allBySource = bankDataService.getBankDataBySource("1");
		List<BankData> insertList = excelLoaderService.getAllBankDataFromExcelFile();
		if (!insertList.isEmpty()) {
			if (!allBySource.isEmpty())
				log.info("clear database");
				bankDataService.removeAllBankData(allBySource);
			insertList.forEach(element -> {
				bankDataService.saveBankData(element);
			});
			log.info("insert finished");
		}
	}
}
