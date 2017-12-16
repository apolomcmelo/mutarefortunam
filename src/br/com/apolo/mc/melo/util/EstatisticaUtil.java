package br.com.apolo.mc.melo.util;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.estatisticas.EstatisticaDigitosDobrados;
import br.com.apolo.mc.melo.estatisticas.EstatisticaMediaEntreNumeros;
import br.com.apolo.mc.melo.estatisticas.EstatisticaNumerosSeguidos;
import br.com.apolo.mc.melo.estatisticas.EstatisticaNumerosSorteados;
import br.com.apolo.mc.melo.estatisticas.EstatisticaQuadrantes;
import br.com.apolo.mc.melo.estatisticas.EstatisticaQuantidadeDigitos;
import br.com.apolo.mc.melo.estatisticas.EstatisticaSomaDigitos;
import br.com.apolo.mc.melo.interfaces.Estatistica;

public class EstatisticaUtil {
	public static Estatistica obterEstatisticaPorNome(String estatistica) {
		if (TipoEstatisticaEnum.MEDIA_ENTRE_NUMEROS.getDescricao().equals(estatistica)) {
			return new EstatisticaMediaEntreNumeros();
			
		} else if (TipoEstatisticaEnum.DIGITOS_DOBRADOS.getDescricao().equals(estatistica)) {
			return new EstatisticaDigitosDobrados();
			
		} else if (TipoEstatisticaEnum.NUMEROS_SEGUIDOS.getDescricao().equals(estatistica)) {
			return new EstatisticaNumerosSeguidos();
			
		} else if (TipoEstatisticaEnum.NUMEROS_SORTEADOS.getDescricao().equals(estatistica)) {
			return new EstatisticaNumerosSorteados();
			
		} else if (TipoEstatisticaEnum.QUADRANTES.getDescricao().equals(estatistica)) {
			return new EstatisticaQuadrantes();
			
		} else if (TipoEstatisticaEnum.QUANTIDADE_DIGITOS.getDescricao().equals(estatistica)) {
			return new EstatisticaQuantidadeDigitos();
			
		} else if (TipoEstatisticaEnum.SOMA_DIGITOS.getDescricao().equals(estatistica)) {
			return new EstatisticaSomaDigitos();
			
		} else {
			return null;
		}
	}
}
