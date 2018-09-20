package de.onestydirect.openiban.data.loader.mapper;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.models.BankDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface BankDataMapper {
	@Mappings({
			@Mapping(target = "bankcode", source = "bankleitzahl"),
			@Mapping(target = "name", source = "bezeichnung"),
			@Mapping(target = "zip", source = "plz"),
			@Mapping(target = "city", source = "ort"),
			@Mapping(target = "bic", source = "bic"),
			@Mapping(target = "algorithm", source = "pruefzifferBerechnungsMethode"),
	})
	BankData bankDataDTOtoBankData(BankDataDTO bankDataDto);
}
