package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.LoaderApplication;
import de.onestydirect.openiban.data.loader.model.BankData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LoaderApplication.class)
@TestPropertySource("classpath:excel-test.properties")
public class ExcelLoaderServiceImplTest {

	@Autowired
	private ExcelLoaderService excelLoaderService;

	private List<BankData> allBankDataFromExcelFile;

	@Before
	public void setUp() {
		allBankDataFromExcelFile = excelLoaderService.getAllBankDataFromExcelFile();
	}

	@Test
	public void getAllBankDataFromExcelFileTest() {
		//then
		assertThat(allBankDataFromExcelFile.size()).isEqualTo(3562);
	}

	@Test
	public void thereAreNoDuplicatesInListTest() {
		//when
		Set<BankData> setToTest = new HashSet<>(allBankDataFromExcelFile);

		//then
		assertThat(setToTest.size()).isEqualTo(3562);
	}

	@Test
	public void specialBicIsSetTest() {
		//when
		List<BankData> bankData = allBankDataFromExcelFile.stream().filter(i -> i.getBankcode().equals("12040000") && i.getBic().equals("COBADEBB120"))
				.collect(Collectors.toList());

		//then
		assertThat(bankData.size()).isEqualTo(0);
	}
}
