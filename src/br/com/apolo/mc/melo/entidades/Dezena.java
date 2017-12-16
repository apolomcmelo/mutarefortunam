package br.com.apolo.mc.melo.entidades;

import java.io.Serializable;

/**
 * Representa o número do jogo disposto no volante
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public class Dezena implements Serializable, Comparable<Dezena> {
	 
	private static final long serialVersionUID = 1L;
	
	private String cor;
	private int valor;
	
	public Dezena() {
	}
	
	public Dezena(int valor) {
		this.valor = valor;
	}
	
	public Dezena(String cor, int valor) {
		this.cor = cor;
		this.valor = valor;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + valor;
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
		Dezena other = (Dezena) obj;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}

	@Override
	public int compareTo(Dezena o) {
		return this.valor < o.getValor() ? -1 : (this.valor == o.getValor() ? 0 : 1);
	}
}