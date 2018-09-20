package de.onestydirect.openiban.data.loader.models;

public class BankDataDTO {

	private String bankleitzahl;

	private String merkmal;

	private String bezeichnung;

	private String plz;

	private String ort;

	private  String  kurzbezeichnung;

	private String pan;

	private String bic;

	private String pruefzifferBerechnungsMethode;

	private String datensatzNummer;

	private String aenderungskennzeichen;

	private String bankleitzahlLoeschung;

	private String nachfolgeBankleitzahl;

	public String getBankleitzahl() {
		return bankleitzahl;
	}

	public void setBankleitzahl(String bankleitzahl) {
		this.bankleitzahl = bankleitzahl;
	}

	public String getMerkmal() {
		return merkmal;
	}

	public void setMerkmal(String merkmal) {
		this.merkmal = merkmal;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}

	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getPruefzifferBerechnungsMethode() {
		return pruefzifferBerechnungsMethode;
	}

	public void setPruefzifferBerechnungsMethode(String pruefzifferBerechnungsMethode) {
		this.pruefzifferBerechnungsMethode = pruefzifferBerechnungsMethode;
	}

	public String getDatensatzNummer() {
		return datensatzNummer;
	}

	public void setDatensatzNummer(String datensatzNummer) {
		this.datensatzNummer = datensatzNummer;
	}

	public String getAenderungskennzeichen() {
		return aenderungskennzeichen;
	}

	public void setAenderungskennzeichen(String aenderungskennzeichen) {
		this.aenderungskennzeichen = aenderungskennzeichen;
	}

	public String getBankleitzahlLoeschung() {
		return bankleitzahlLoeschung;
	}

	public void setBankleitzahlLoeschung(String bankleitzahlLoeschung) {
		this.bankleitzahlLoeschung = bankleitzahlLoeschung;
	}

	public String getNachfolgeBankleitzahl() {
		return nachfolgeBankleitzahl;
	}

	public void setNachfolgeBankleitzahl(String nachfolgeBankleitzahl) {
		this.nachfolgeBankleitzahl = nachfolgeBankleitzahl;
	}
}
