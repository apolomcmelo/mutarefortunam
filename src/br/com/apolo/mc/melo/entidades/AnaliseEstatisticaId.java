package br.com.apolo.mc.melo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AnaliseEstatisticaId implements Serializable, Comparable<AnaliseEstatisticaId> {

	private static final long serialVersionUID = 1L;
	

	@Column(name="tipo")
	private int tipo;

	@Column(name="intervalo")
	private int intervalo;

	@Column(name="elemento")
	private String elemento;

	@Column(name="qtd_geral_jogos")
	private int qtdGeralJogos;
	
	@Column(name="numero_ultimo_concurso")
	private int numeroUltimoConcurso;
	
	public AnaliseEstatisticaId() {
	}
	
	public AnaliseEstatisticaId(int tipo, int intervalo, String elemento,
			int qtdGeralJogos, int numeroUltimoConcurso) {
		this.tipo = tipo;
		this.intervalo = intervalo;
		this.elemento = elemento;
		this.qtdGeralJogos = qtdGeralJogos;
		this.numeroUltimoConcurso = numeroUltimoConcurso;
	}

	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getIntervalo() {
		return intervalo;
	}
	
	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
	
	public String getElemento() {
		return elemento;
	}
	
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}
	
	public int getQtdGeralJogos() {
		return qtdGeralJogos;
	}
	
	public void setQtdGeralJogos(int qtdGeralJogos) {
		this.qtdGeralJogos = qtdGeralJogos;
	}
	
	public int getNumeroUltimoConcurso() {
		return numeroUltimoConcurso;
	}
	
	public void setNumeroUltimoConcurso(int numeroUltimoConcurso) {
		this.numeroUltimoConcurso = numeroUltimoConcurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elemento == null) ? 0 : elemento.hashCode());
		result = prime * result + intervalo;
		result = prime * result + numeroUltimoConcurso;
		result = prime * result + qtdGeralJogos;
		result = prime * result + tipo;
	
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
		AnaliseEstatisticaId other = (AnaliseEstatisticaId) obj;
		if (elemento == null) {
			if (other.elemento != null)
				return false;
		} else if (!elemento.equals(other.elemento))
			return false;
		if (intervalo != other.intervalo)
			return false;
		if (numeroUltimoConcurso != other.numeroUltimoConcurso)
			return false;
		if (qtdGeralJogos != other.qtdGeralJogos)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(AnaliseEstatisticaId o) {
		return this.qtdGeralJogos < o.getQtdGeralJogos() ? -1 : (this.qtdGeralJogos == o.getQtdGeralJogos() ? (this.tipo < o.getTipo() ? -1 : (this.tipo == getTipo() ? 0 : 1)) : 1);
	}
}
