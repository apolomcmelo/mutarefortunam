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
 * Estuda a quantidade de números com dígitos dobrados em um mesmo jogo.
 * Entende-se como números dobrados os números cuja dezena e unidade possuem o mesmo dígito, como 11, 22, 33, etc. Pode ser interpretado por:
 * (xn % 11) = 0
 *  
 * @since 2015-04-17
 * @author Apolo Mc Melo
 */
public class EstatisticaDigitosDobrados implements Estatistica {
	
	@Override
	public List<AnaliseEstatistica> verificarEstatistica(List<int[]> jogos, int intervalo) {
		Map<Integer, Integer> mapQtdNumDob = new HashMap<Integer, Integer>();
		List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();
		
		int[] jogoAtual;
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
				
				count = 0x0;
				
				// Percorre as dezenas dentro de cada jogo
				for(int k=0; k<jogoAtual.length; k++) {
					if((jogoAtual[k] % 11) == 0) {
						count++;
					}
				}
			
				if(!mapQtdNumDob.containsKey(count)) {
					mapQtdNumDob.put(count, 0);
				}
				
				mapQtdNumDob.put(count, mapQtdNumDob.get(count) + 1);			
			}
		
			estatisticas.addAll(preencherObjetoParaAnalise(dataExecucao, mapQtdNumDob, TipoEstatisticaEnum.DIGITOS_DOBRADOS.getCodigo(), intervalo, qtdJogos, jogos.size(), i+qtdJogos));
			mapQtdNumDob.clear();
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
			
			String elemento = intervalo != 1 ? (listaElementos.get(i).toString()) : "Qtd. D.D.";
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