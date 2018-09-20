package de.onestydirect.openiban.data.loader.config;

import de.onestydirect.openiban.data.loader.common.BankDataItemProcessor;
import de.onestydirect.openiban.data.loader.common.BankDataWriter;
import de.onestydirect.openiban.data.loader.mapper.BankDataExcelRowMapper;
import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

@Configuration
@PropertySource("classpath:excel.properties")
public class ExcelFileJobConfig {

	@Value("${excel.file.path}")
	private String pathToExcelFile;

	@Bean
	ItemReader<BankDataDTO> excelBankDataReader() throws MalformedURLException {
		PoiItemReader<BankDataDTO> reader = new PoiItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new UrlResource(pathToExcelFile));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<BankDataDTO> excelRowMapper() {
		return new BankDataExcelRowMapper();
	}

	@Bean
	ItemProcessor<BankDataDTO, BankData> excelBankDataProcessor() {
		return new BankDataItemProcessor();
	}

	@Bean
	ItemWriter<BankData> excelBankDataWriter() {
		return new BankDataWriter();
	}

	@Bean
	Step excelFileToDatabaseStep(ItemReader<BankDataDTO> excelBankDataReader,
			ItemProcessor<BankDataDTO, BankData> excelBankDataProcessor, ItemWriter<BankData> excelBankDataWriter,
			StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("excelFileToDatabaseStep").<BankDataDTO, BankData>chunk(1)
				.reader(excelBankDataReader).processor(excelBankDataProcessor).writer(excelBankDataWriter).build();
	}

	@Bean
	Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
			@Qualifier("excelFileToDatabaseStep") Step excelStudentStep) {
		return jobBuilderFactory.get("excelFileToDatabaseJob").incrementer(new RunIdIncrementer())
				.flow(excelStudentStep).end().build();
	}
}
