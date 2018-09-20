package de.onestydirect.openiban.data.loader.config;

import de.onestydirect.openiban.data.loader.mapper.BankDataExcelRowMapper;
import de.onestydirect.openiban.data.loader.models.BankDataDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ExcelFileJobConfig {

	@Value("${excel.file.path}")
	private String pathToExcelFile;

	@Bean
	ItemReader<BankDataDto> excelStudentReader() {
		PoiItemReader<BankDataDto> reader = new PoiItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource(pathToExcelFile));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<BankDataDto> excelRowMapper() {
		return new BankDataExcelRowMapper();
	}
}
