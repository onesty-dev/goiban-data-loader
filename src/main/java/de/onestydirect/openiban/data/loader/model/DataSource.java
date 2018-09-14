package de.onestydirect.openiban.data.loader.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DATA_SOURCE")
public class DataSource {

	@Id
	private int id;

	@Column(name = "source")
	private String source;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "source")
	private List<BankData> bankData = new ArrayList<>();

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

	public List<BankData> getBankData() {
		return bankData;
	}

	public void setBankData(List<BankData> bankData) {
		this.bankData = bankData;
	}
}
