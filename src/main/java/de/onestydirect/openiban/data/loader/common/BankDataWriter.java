package de.onestydirect.openiban.data.loader.common;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataDTO;
import de.onestydirect.openiban.data.loader.services.BankDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class BankDataWriter implements ItemWriter<BankData> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankDataWriter.class);

	@Autowired
	private BankDataService bankDataService;

	@Override
	public void write(List<? extends BankData> bankDataLst) throws Exception {
		LOGGER.info("Received the information of {} students", bankDataLst.size());
		if (!bankDataLst.isEmpty()) {
			List<BankData> filteredList = filterBankDataList(bankDataLst);
			List<BankData> insertList = hackSpecialBics(filteredList);
			insertList.forEach(element -> {
				bankDataService.saveBankData(element);
			});
		}
	}

	private List<BankData> filterBankDataList(final List<? extends BankData> bankDataList) {
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
		LOGGER.info("Bankdata list hacked. Replaced COBADEBB120 on bankcode 12040000 with COBADEFFXXX");
		return bankDataList;
	}
}
