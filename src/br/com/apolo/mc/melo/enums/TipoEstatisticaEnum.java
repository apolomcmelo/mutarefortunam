package br.com.apolo.mc.melo.enums;

/**
 * Enum que representa os ids dos tipos de Estatísticas
 * 
 * @since 2015-04-16
 * @author amelo
 */
public enum TipoEstatisticaEnum {
	MEDIA_ENTRE_NUMEROS(1, "Média entre números"),
	DIGITOS_DOBRADOS(2, "Dígitos dobrados"),
	NUMEROS_SEGUIDOS(3, "Números seguidos"), 
	NUMEROS_SORTEADOS(4, "Números sorteados"),
	QUADRANTES(5, "Quadrantes"),
	QUANTIDADE_DIGITOS(6, "Quantidade de dígitos"),
	SOMA_DIGITOS(7, "Soma dos dígitos"),
	PARES_IMPARES(8, "Quantidade de Pares e Ímpares");
	
	private int codigo;
	private String descricao;
	
	private TipoEstatisticaEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static TipoEstatisticaEnum descricaoToEnum(String strEstatistica) {
		if (MEDIA_ENTRE_NUMEROS.getDescricao().equals(strEstatistica)) {
			return MEDIA_ENTRE_NUMEROS;
			
		} else if (DIGITOS_DOBRADOS.getDescricao().equals(strEstatistica)) {
			return DIGITOS_DOBRADOS;
			
		} else if (NUMEROS_SEGUIDOS.getDescricao().equals(strEstatistica)) {
			return NUMEROS_SEGUIDOS;
			
		} else if (NUMEROS_SORTEADOS.getDescricao().equals(strEstatistica)) {
			return NUMEROS_SORTEADOS;
			
		} else if (QUADRANTES.getDescricao().equals(strEstatistica)) {
			return QUADRANTES;
			
		} else if (QUANTIDADE_DIGITOS.getDescricao().equals(strEstatistica)) {
			return QUANTIDADE_DIGITOS;
			
		} else if (SOMA_DIGITOS.getDescricao().equals(strEstatistica)) {
			return SOMA_DIGITOS;
			
		} else {
			return null;
		}
	}

	public static TipoEstatisticaEnum codigoToEnum(int codigo) {
		switch (codigo) {
		case 1:
			return MEDIA_ENTRE_NUMEROS;
		case 2:
			return DIGITOS_DOBRADOS;
		case 3:
			return NUMEROS_SEGUIDOS;
		case 4:
			return NUMEROS_SORTEADOS;
		case 5:
			return QUADRANTES;
		case 6:
			return QUANTIDADE_DIGITOS;
		case 7:
			return SOMA_DIGITOS;	
		default:
			return null;
		}
	}
}