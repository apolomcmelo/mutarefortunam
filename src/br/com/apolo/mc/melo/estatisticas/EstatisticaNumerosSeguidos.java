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
 * Estuda a quantidade de números seguidos em um mesmo jogo.
 * Entende-se como números seguidos os números vizinhos, podendo ser interpretado por:
 * x1 = x2-1
 *  
 * @since 2015-04-16
 * @author Apolo Mc Melo
 */
public class EstatisticaNumerosSeguidos implements Estatistica {
	
	@Override
	public List<AnaliseEstatistica> verificarEstatistica(List<int[]> jogos, int intervalo) {
		Map<Integer, Integer> mapQtdNumSeg = new HashMap<Integer, Integer>();
		List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();
		
		int[] jogoAtual;
		int qtdJogos = intervalo == 0x0 ? jogos.size() : intervalo;
		int count, maxSeg;
		
		Date dataExecucao = new Date();

		// Percorre toda a lista de jogos
		for(int i=0; i<jogos.size(); i+=qtdJogos) {
			
			if((i+qtdJogos) > jogos.size()) {
				qtdJogos = jogos.size() - i;
			}
			
			// Percorre lista de jogos de acordo com o intervalo
			for(int j=i; j<i+qtdJogos; j++) {
				jogoAtual = jogos.get(j);
	
				count = 0x0;
				maxSeg = 0x0;
				
				// Calcula a quantidade de números seguidos em um mesmo jogo
				for(int k=0; k<jogoAtual.length-1; k++) {
					if(jogoAtual[k] == jogoAtual[k+1]-1) {
						count++;
						maxSeg = count > maxSeg ? count : maxSeg;
					} else {
						count = 0x1;
					}
				}
				
				if(!mapQtdNumSeg.containsKey(maxSeg)) {
					mapQtdNumSeg.put(maxSeg, 0);
				}
				
				mapQtdNumSeg.put(maxSeg, mapQtdNumSeg.get(maxSeg) + 0x1);
			}
	
			estatisticas.addAll(preencherObjetoParaAnalise(dataExecucao, mapQtdNumSeg, TipoEstatisticaEnum.NUMEROS_SEGUIDOS.getCodigo(), intervalo, qtdJogos, jogos.size(), i+qtdJogos));
			mapQtdNumSeg.clear();
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

			String elemento = intervalo != 1 ? (listaElementos.get(i).toString()) : "Qtd. N.Seg.";
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