package de.onestydirect.openiban.data.loader.common;

import de.onestydirect.openiban.data.loader.mapper.BankDataMapper;
import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataDTO;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.util.Date;

public class BankDataItemProcessor implements ItemProcessor<BankDataDTO, BankData> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankDataItemProcessor.class);

	private final BankDataMapper bankDataMapper = Mappers.getMapper(BankDataMapper.class);

	@Override
	public BankData process(BankDataDTO bankDataDto) throws Exception {
		LOGGER.info("Processing student information: {}", bankDataDto);
		final BankData bankData = bankDataMapper.bankDataDTOtoBankData(bankDataDto);
		bankData.setCountry("DE");
		bankData.setSource("1");
		final Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		bankData.setCreated(timestamp);
		bankData.setLastUpdate(timestamp);
		return bankData;
	}
}
