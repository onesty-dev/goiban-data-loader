package de.onestydirect.openiban.data.loader.model.repository;

import de.onestydirect.openiban.data.loader.model.BankData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankDataRepository extends CrudRepository<BankData, Integer> {
	List<BankData> findAllBySource(String source);
}
