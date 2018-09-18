package de.onestydirect.openiban.data.loader.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankDataRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

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

		entityManager.persist(bankData);
		entityManager.flush();

		//when
		List<BankData> foundBankData = bankDataRepository.findAllBySource(bankData.getSource());

		//then
		assertThat(foundBankData.size()).isEqualTo(1);
		assertThat(foundBankData.get(0).getSource()).isEqualTo(foundBankData.get(0).getSource());
	}
}