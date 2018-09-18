package de.onestydirect.openiban.data.loader.services;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:excel.properties")
public class ExcelLoaderService {

	private Logger logger = LoggerFactory.getLogger(ExcelLoaderService.class);

	private ResourceLoader resourceLoader;

	@Value("${excel.file.path}")
	private String pathToExcelFile;

	@Autowired
	public ExcelLoaderService(final ResourceLoader resourceLoader){
		this.resourceLoader=resourceLoader;
	}

	public Optional<Workbook> getWorkbook() {
		try {
			Workbook workbook = WorkbookFactory.create(resourceLoader.getResource(pathToExcelFile).getFile());
			return Optional.of(workbook);
		} catch (Exception e) {
			logger.error(e.toString());
			return Optional.empty();
		}
	}
}
