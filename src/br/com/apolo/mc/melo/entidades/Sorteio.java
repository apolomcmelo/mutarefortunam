package br.com.apolo.mc.melo.entidades;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Representa o sorteio de um determinado jogo.
 * 
 * @since 2015-04-16
 * @author Apolo Mc Melo
 */
@Entity
@Table(name="sorteio")
public class Sorteio implements Serializable, Comparable<Sorteio> {
	
	private static final long serialVersionUID = 1L;
	
	private int concurso;
	private Date data;
	private String sorteio;
	private int[] dezenas;
	
	public Sorteio() {
	}
	
	public Sorteio(Sorteio sorteio) {
		this.concurso = sorteio.getConcurso();
		this.data = new Date(sorteio.getData().getTime());
		this.sorteio = sorteio.getSorteio() + "";
		this.dezenas = Arrays.copyOf(sorteio.getDezenas(), sorteio.getDezenas().length);
	}
	
	public Sorteio(int concurso, Date data, String uf, String sorteio) {
		this.concurso = concurso;
		this.data = data;
		this.sorteio = sorteio;
	}

	@Id
	@Column(name="concurso")
	public int getConcurso() {
		return concurso;
	}

	public void setConcurso(int concurso) {
		this.concurso = concurso;
	}

	@Column(name="data")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name="sorteio")
	public String getSorteio() {
		return sorteio;
	}

	public void setSorteio(String sorteio) {
		if(sorteio != null && !"".equals(sorteio)){
			this.sorteio = sorteio;

			String[] strDezenas = this.sorteio.split(";");
			this.dezenas = new int[strDezenas.length];
			
			for (int i = 0; i < strDezenas.length; i++) {
				this.dezenas[i] = Integer.parseInt(strDezenas[i].trim());
			}
		}
	}

	@Transient
	public int[] getDezenas() {
		return dezenas;
	}

	public void setDezenas(int[] dezenas) {
		this.dezenas = dezenas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + concurso;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((sorteio == null) ? 0 : sorteio.hashCode());
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
		Sorteio other = (Sorteio) obj;
		if (concurso != other.concurso)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (sorteio == null) {
			if (other.sorteio != null)
				return false;
		} else if (!sorteio.equals(other.sorteio))
			return false;
		return true;
	}

	@Override
	public int compareTo(Sorteio o) {
		return this.concurso < o.getConcurso() ? -1 : (this.concurso == o.getConcurso() ? 0 : 1);
	}
}