package de.onestydirect.openiban.data.loader.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BANK_DATA", indexes = {@Index(columnList = "id, source, bankcode, bic, country")})
public class BankData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "source")
	private String source;

	@Column(name = "bankcode")
	private String bankcode;

	@Column(name = "name")
	private String name;

	@Column(name = "zip")
	private String zip;

	@Column(name = "city")
	private String city;

	@Column(name = "bic")
	private String bic;

	@Column(name = "country")
	private String country;

	@Column(name = "algorithm")
	private String algorithm;

	@Column(name = "created")
	private Timestamp created;

	@Column(name = "last_update")
	private Timestamp lastUpdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
