package de.onestydirect.openiban.data.loader.mapper;

import de.onestydirect.openiban.data.loader.models.BankDataDTO;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class BankDataExcelRowMapper implements RowMapper<BankDataDTO> {
	@Override
	public BankDataDTO mapRow(RowSet rs) throws Exception {
		BankDataDTO bankDataDto = new BankDataDTO();
		bankDataDto.setBankleitzahl(rs.getColumnValue(0));
		bankDataDto.setMerkmal(rs.getColumnValue(1));
		bankDataDto.setBezeichnung(rs.getColumnValue(2));
		bankDataDto.setPlz(rs.getColumnValue(3));
		bankDataDto.setOrt(rs.getColumnValue(4));
		bankDataDto.setKurzbezeichnung(rs.getColumnValue(5));
		bankDataDto.setPan(rs.getColumnValue(6));
		bankDataDto.setBic(rs.getColumnValue(7));
		bankDataDto.setPruefzifferBerechnungsMethode(rs.getColumnValue(8));
		bankDataDto.setDatensatzNummer(rs.getColumnValue(9));
		bankDataDto.setAenderungskennzeichen(rs.getColumnValue(10));
		bankDataDto.setBankleitzahlLoeschung(rs.getColumnValue(11));
		bankDataDto.setNachfolgeBankleitzahl(rs.getColumnValue(12));
		return bankDataDto;
	}
}
