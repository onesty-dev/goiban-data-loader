package de.onestydirect.openiban.data.loader.model.repository;

import de.onestydirect.openiban.data.loader.model.BankData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankDataRepository extends JpaRepository<BankData, Integer> {
	List<BankData> findAllBySource(String source);
}
