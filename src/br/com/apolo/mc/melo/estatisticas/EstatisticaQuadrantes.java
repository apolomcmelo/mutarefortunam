package br.com.apolo.mc.melo.estatisticas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.entidades.AnaliseEstatisticaId;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Estatistica;

/**
 * Estuda a quantidade de quadrantes ocupados por números em um mesmo jogo.
 * Cada volante é dividido em 4 quadrantes dispostos da seguinte maneira:
 * 
 *<pre>0  0  0  0  0 | 0  0  0  0  0
 *0  0  1º 0  0 | 0  0  2º 0  0  
 *0  0  0  0  0 | 0  0  0  0  0
 *--------------|--------------
 *0  0  0  0  0 | 0  0  0  0  0
 *0  0  3º 0  0 | 0  0  4º 0  0
 *0  0  0  0  0 | 0  0  0  0  0
 *  
 * @since 2015-04-17
 * @author Apolo Mc Melo
 */
public class EstatisticaQuadrantes implements Estatistica {

	@Override
	public List<AnaliseEstatistica> verificarEstatistica(List<int[]> jogos, int intervalo) {
		Map<Integer, Integer> mapQuad = new HashMap<Integer, Integer>();
		List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();

		int[] jogoAtual;
		int[] quadJogo = new int[4];

		int qtdJogos = intervalo == 0x0 ? jogos.size() : intervalo;
		int count;
		
		Date dataExecucao = new Date();

		// Percorre toda a lista de jogos
		for(int i=0; i<jogos.size(); i+=qtdJogos) {
			
			if((i+qtdJogos) > jogos.size()) {
				qtdJogos = jogos.size() - i;
			}
			
			// Percorre lista de jogos de acordo com o intervalo
			for(int j=i; j<i+qtdJogos; j++) {
				jogoAtual = jogos.get(j);
				
				count = 0x4;
				
				//Separa os números do jogo em quadrantes
				for(int k=0; k<jogoAtual.length; k++) {
					//Primeiros quadrantes
					if(jogoAtual[k] <= 30) {
						//1º
						if((jogoAtual[k] % 10 > 0) && (jogoAtual[k] % 10 < 6)) {
							quadJogo[0]++;
						} else {//2º
							quadJogo[1]++;
						}
					} else { // Últimos quadrantes
						//3º
						if((jogoAtual[k] % 10 >= 1) && (jogoAtual[k] % 10 <=5)) {
							quadJogo[2]++;
						} else { //4º
							quadJogo[3]++;
						}
					}
				}
				
				//Realiza a contagem dos quadrantes ocupados em um mesmo jogo
				for(int k=0; k<quadJogo.length; k++) {
					if(quadJogo[k] == 0) {
						count--;
					}
					
					quadJogo[k] = 0;
				}
				
				if(!mapQuad.containsKey(count)) {
					mapQuad.put(count, 0);
				}
				
				mapQuad.put(count, mapQuad.get(count) + 1);
			}
			
			estatisticas.addAll(preencherObjetoParaAnalise(dataExecucao, mapQuad, TipoEstatisticaEnum.QUADRANTES.getCodigo(), intervalo, qtdJogos, jogos.size(), i+qtdJogos));
			mapQuad.clear();
		}
		
		return estatisticas;
	}	
	
	/**
	 * Preenche lista de objetos para análise de estatística
	 * @param dataAnalise
	 * @param mapaEstatisticas
	 * @param tipo
	 * @param intervalo
	 * @param intervaloEspecial
	 * @param qtdJogos
	 * @param numeroUltimoConcurso
	 * @return Lista de objetos para análise de estatística
	 */
	private List<AnaliseEstatistica> preencherObjetoParaAnalise(Date dataAnalise, Map<Integer, Integer> mapaEstatisticas, int tipo, int intervalo, int intervaloEspecial, int qtdJogos, int numeroUltimoConcurso) {
		List<Integer> listaElementos = new ArrayList<Integer>(mapaEstatisticas.keySet());
		List<AnaliseEstatistica> listaEstatistica = new ArrayList<AnaliseEstatistica>();
		Collections.sort(listaElementos);
		
		for(int i=0; i<listaElementos.size(); i++) {
			AnaliseEstatisticaId aeId = new AnaliseEstatisticaId();
			AnaliseEstatistica ae = new AnaliseEstatistica();
			
			aeId.setTipo(tipo);
			
			String elemento = intervalo != 1 ? (listaElementos.get(i).toString()) : "Qtd. Quad.";
			aeId.setElemento(elemento);
			
			aeId.setIntervalo(intervalo);
			aeId.setQtdGeralJogos(qtdJogos);
			aeId.setNumeroUltimoConcurso(numeroUltimoConcurso);
			
			ae.setId(aeId);
			ae.setDataAnalise(dataAnalise);
			
			BigDecimal percentual = intervalo != 1 ? (new BigDecimal((mapaEstatisticas.get(listaElementos.get(i)) * 100d) / intervaloEspecial).setScale(6, BigDecimal.ROUND_HALF_UP)) : (new BigDecimal(listaElementos.get(i)));
			ae.setPercentual(percentual);

			listaEstatistica.add(ae);
		}
		
		return listaEstatistica;
	}
}