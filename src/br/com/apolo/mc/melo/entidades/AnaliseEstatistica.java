package br.com.apolo.mc.melo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Representa a análise estatística de jogos passados
 * 
 * @since 2015-04-16
 * @author Apolo Mc Melo
 */
@Entity
@Table(name="analise_estatistica")
public class AnaliseEstatistica implements Serializable, Comparable<AnaliseEstatistica> {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AnaliseEstatisticaId id;

	@Column(name="percentual")
	private BigDecimal percentual;

	@Column(name="data_analise")
	private Date dataAnalise;
	
	public AnaliseEstatistica() {
	}

	public AnaliseEstatistica(AnaliseEstatisticaId id, BigDecimal percentual,
			Date dataAnalise) {
		super();
		this.id = id;
		this.percentual = percentual;
		this.dataAnalise = dataAnalise;
	}

	public AnaliseEstatisticaId getId() {
		return id;
	}

	public void setId(AnaliseEstatisticaId id) {
		this.id = id;
	}

	public BigDecimal getPercentual() {
		return percentual;
	}

	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}

	public Date getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(Date dataAnalise) {
		this.dataAnalise = dataAnalise;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataAnalise == null) ? 0 : dataAnalise.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((percentual == null) ? 0 : percentual.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnaliseEstatistica other = (AnaliseEstatistica) obj;
		if (dataAnalise == null) {
			if (other.dataAnalise != null)
				return false;
		} else if (!dataAnalise.equals(other.dataAnalise))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (percentual == null) {
			if (other.percentual != null)
				return false;
		} else if (!percentual.equals(other.percentual))
			return false;
		return true;
	}

	@Override
	public int compareTo(AnaliseEstatistica o) {
		return this.id.compareTo(o.getId());
	}
}