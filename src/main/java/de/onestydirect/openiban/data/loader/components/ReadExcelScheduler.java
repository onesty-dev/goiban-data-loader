package de.onestydirect.openiban.data.loader.components;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataRepository;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private BankDataRepository bankDataRepository;

	private static final Logger log = LoggerFactory.getLogger(ReadExcelScheduler.class);

	@Autowired
	public ReadExcelScheduler(final ExcelLoaderService excelLoaderService,
			final BankDataRepository bankDataRepository) {
		this.excelLoaderService = excelLoaderService;
		this.bankDataRepository = bankDataRepository;
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void updateBankData() {
		final List<BankData> allBySource = bankDataRepository.findAllBySource("1");
		bankDataRepository.deleteAll(allBySource);
		List<BankData> bankDataList = generateBankDataList();
		List<BankData> insertList = hackSpecialBics(bankDataList);
		insertList.forEach(element -> {
			bankDataRepository.save(element);
		});
	}

	private List<BankData> generateBankDataList() {
		List<BankData> bankDataList = new ArrayList<>();
		final Optional<Workbook> workbook = excelLoaderService.getWorkbook();
		workbook.ifPresent(wb -> {
			Iterator<Sheet> sheetIterator = wb.sheetIterator();
			if (sheetIterator.hasNext()) {
				DataFormatter dataFormatter = new DataFormatter();
				final Sheet sheet = workbook.get().getSheetAt(0);
				final Iterator<Row> rowIterator = sheet.rowIterator();
				int i = 0;
				while (rowIterator.hasNext()) {
					final Row row = rowIterator.next();
					if (i > 0) {
						Iterator<Cell> cellIterator = row.cellIterator();
						final BankData bankData = new BankData();
						bankData.setSource("1");
						final Date date = new Date();
						bankData.setCreated(new Timestamp(date.getTime()));
						bankData.setCountry("DE");
						int cellCounter = 0;
						while (cellIterator.hasNext()) {
							final Cell cell = cellIterator.next();
							final String cellValue = dataFormatter.formatCellValue(cell);
							switch (cellCounter) {
							case 0:
								bankData.setBankcode(cellValue);
								break;
							case 2:
								bankData.setName(cellValue);
								break;
							case 3:
								bankData.setZip(cellValue);
								break;
							case 4:
								bankData.setCity(cellValue);
								break;
							case 7:
								bankData.setBic(cellValue);
								break;
							case 8:
								bankData.setAlgorithm(cellValue);
								break;
							default:
								break;
							}
							cellCounter++;
						}
						bankDataList.add(bankData);
					}
					i++;
				}
			}
		});
		log.info("Bankdata list generated.");
		return filterBankDataList(bankDataList);
	}

	private List<BankData> filterBankDataList(final List<BankData> bankDataList) {
		log.info("Bankdata list filtered.");
		return bankDataList.stream().filter(distinctByBankCode(BankData::getBankcode)).collect(Collectors.toList());
	}

	private static <T> Predicate<T> distinctByBankCode(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private List<BankData> hackSpecialBics(final List<BankData> bankDataList) {
		bankDataList.forEach(entry -> {
			if (entry.getBic().equals("COBADEBB120") && entry.getBankcode().equals("12040000")) {
				entry.setBic("COBADEFFXXX");
			}
		});
		return bankDataList;
	}
}
