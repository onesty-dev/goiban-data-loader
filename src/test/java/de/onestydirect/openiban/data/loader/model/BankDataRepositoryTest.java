package de.onestydirect.openiban.data.loader.model;

import static org.assertj.core.api.Assertions.assertThat;

import de.onestydirect.openiban.data.loader.model.repository.BankDataRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class BankDataRepositoryTest {

	@Autowired
	private BankDataRepository bankDataRepository;

	@Test
	public void whenFindBySource_thenReturnBankData() {
		//given
		BankData bankData = new BankData();
		bankData.setBic("GENODEF1CH1");
		bankData.setCountry("DE");
		bankData.setAlgorithm("09");
		bankData.setSource("1");

		bankDataRepository.save(bankData);
		bankDataRepository.flush();

		//when
		List<BankData> foundBankData = bankDataRepository.findAllBySource(bankData.getSource());

		//then
		assertThat(foundBankData.size()).isEqualTo(1);
		assertThat(foundBankData.get(0).getSource()).isEqualTo(foundBankData.get(0).getSource());
	}
}
