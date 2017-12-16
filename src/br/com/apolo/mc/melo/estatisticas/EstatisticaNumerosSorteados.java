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
 * Verifica a quantidade que cada número foi sorteado ao longo da história dos jogos
 *
 * @since 2015-04-16
 * @author Apolo Mc Melo
 */
public class EstatisticaNumerosSorteados implements Estatistica {
	
	private int[] estNumSort;

	@Override
	public List<AnaliseEstatistica> verificarEstatistica(List<int[]> jogos, int intervalo) {
		Map<Integer, Integer> mapNumSort = new HashMap<Integer, Integer>();
		List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();
		
		//TODO Array utilizado para testes
//		List<int[]> testeJogos = new ArrayList<int[]>();
//		int[] a = new int[]{1, 2, 3, 4, 5, 6};
//		int[] b = new int[]{1, 2, 7, 8, 9, 10};
//		int[] c = new int[]{1, 8, 23, 15, 18, 20};
//		int[] d = new int[]{1, 2, 3, 11, 12, 13};
//		int[] e = new int[]{1, 2, 3, 4, 16, 19};
//		
//		testeJogos.add(a);
//		testeJogos.add(b);
//		testeJogos.add(c);
//		testeJogos.add(d);
//		testeJogos.add(e);
//		
//		jogos = testeJogos;
		// quantidade maior deve ser 1
		//TODO Testes encerram aqui
		
		int[] jogoAtual;
		int qtdJogos = intervalo == 0x0 ? jogos.size() : intervalo;
		
		Date dataExecucao = new Date();
		
		// Percorre toda a lista de jogos
		for(int i=0; i<jogos.size(); i+=qtdJogos) {
			
			if((i+qtdJogos) > jogos.size()) {
				qtdJogos = jogos.size() - i;
			}
			
			// Percorre lista de jogos de acordo com o intervalo
			for(int j=i; j<i+qtdJogos; j++) {
				jogoAtual = jogos.get(j);
			
				for(int k=0; k<jogoAtual.length; k++) {
					if(!mapNumSort.containsKey(jogoAtual[k])) {
						mapNumSort.put(jogoAtual[k], 0);
					}
					
					mapNumSort.put(jogoAtual[k], mapNumSort.get(jogoAtual[k]) + 1);
				}	
			}
			
//			mapNumSort.clear();			
		}
		estatisticas.addAll(preencherObjetoParaAnalise(dataExecucao, mapNumSort, TipoEstatisticaEnum.NUMEROS_SORTEADOS.getCodigo(), intervalo, qtdJogos, jogos.size(), jogos.size()));
		
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
		
		estNumSort = new int[listaElementos.size()];
		
		for(int i=0; i<listaElementos.size(); i++) {
			Integer e = listaElementos.get(i);
			
			AnaliseEstatisticaId aeId = new AnaliseEstatisticaId();
			AnaliseEstatistica ae = new AnaliseEstatistica();
			
			aeId.setTipo(tipo);

//			String elemento = intervalo != 1 ? (e < 10 ? "0" + e.toString() : e.toString()) : "Num.Sort.";
			String elemento = e < 10 ? "0" + e.toString() : e.toString();
			aeId.setElemento(elemento);
			
			aeId.setIntervalo(intervalo);
			aeId.setQtdGeralJogos(qtdJogos);
			aeId.setNumeroUltimoConcurso(numeroUltimoConcurso);

			ae.setId(aeId);
			ae.setDataAnalise(dataAnalise);

			BigDecimal percentual = intervalo != 1 ? (new BigDecimal((mapaEstatisticas.get(listaElementos.get(i)) * 100d) / intervaloEspecial).setScale(6, BigDecimal.ROUND_HALF_UP)) : (new BigDecimal(mapaEstatisticas.get(listaElementos.get(i))));
			ae.setPercentual(percentual);

			listaEstatistica.add(ae);
			
			estNumSort[i] = mapaEstatisticas.get(listaElementos.get(i));
		}
		
		return listaEstatistica;
	}

	public int[] getEstNumSort() {
		return estNumSort;
	}

	public void setEstNumSort(int[] estNumSort) {
		this.estNumSort = estNumSort;
	}
}