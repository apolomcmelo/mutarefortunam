package br.com.apolo.mc.melo.enums;


/**
 * Enum que representa os jogos, contendo quantidade de dezenas a sortear e dezenas sorteadas
 * 
 * @since 2015-05-07
 * @author Apolo Mc Melo
 */
public enum TipoJogo {
	QUADRA(40, 4, "Quadra"),
	QUINA(50, 5, "Quina"),
	SENA(60, 6, "MegaSena");	
	

	private int qtdDezenas;
	private int qtdDezenasSorteadas;
	private String descricao;
	
	private TipoJogo(int qtdDezenas, int qtdDezenasSorteadas, String descricao) {
		this.qtdDezenas = qtdDezenas;
		this.qtdDezenasSorteadas = qtdDezenasSorteadas;
		this.descricao = descricao;
	}

	public int getQtdDezenas() {
		return qtdDezenas;
	}
	
	public void setQtdDezenas(int qtdDezenas) {
		this.qtdDezenas = qtdDezenas;
	}
	
	public int getQtdDezenasSorteadas() {
		return qtdDezenasSorteadas;
	}

	public void setQtdDezenasSorteadas(int qtdDezenasSorteadas) {
		this.qtdDezenasSorteadas = qtdDezenasSorteadas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static TipoJogo descricaoToEnum(String strJogo) {
		if (QUADRA.name().equals(strJogo)) {
			return QUADRA;
		} else if (QUINA.name().equals(strJogo)) {
			return QUINA;
		} else if (SENA.name().equals(strJogo)) {
			return SENA;
		} else {
			return null;
		}
	}
	
	public static TipoJogo qtdDezenasToEnum(int qtdDezenas) {
		switch (qtdDezenas) {
		case 40:
			return QUADRA;
		case 50:
			return QUINA;
		case 60:
			return SENA;
		default:
			return null;
		}
	}
	
	public static TipoJogo qtdDezenasSorteadasToEnum(int qtdDezenasSorteadas) {
		switch (qtdDezenasSorteadas) {
		case 4:
			return QUADRA;
		case 5:
			return QUINA;
		case 6:
			return SENA;
		default:
			return null;
		}
	}
}