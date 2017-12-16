package br.com.apolo.mc.melo.interfaces;

import java.util.List;

import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;

/**
 * Interface para implementação de estatísticas que servem de apoio para análise de comportamento de jogos passados, 
 * podendo assim, facilitar o estudo e construção de novas regras
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public interface Estatistica {	

	/**
	 * Calcula uma determinada estatística em um conjunto de n jogos
	 * 
	 * @param jogos - Lista de jogos
	 * @param intervalo - Número da amostragem para análise
	 * @return Lista da análise estatística realizada com o percentual de cada elemento
	 * 
	 * @since 2015-03-10
	 * @author Apolo Mc Melo
	 */
	List<AnaliseEstatistica> verificarEstatistica(List<int[]> jogos, int intervalo);
}
