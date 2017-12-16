package br.com.apolo.mc.melo.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.services.AnaliseEstatisticaService;
import br.com.apolo.mc.melo.util.EstatisticaUtil;
import br.com.apolo.mc.melo.util.Util;

public class AnaliseEstatisticaController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Sorteio> sorteios;
	private List<int[]> jogosSorteados;
	
	private GerarJogosController gerarJogosController = new GerarJogosController();
	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();
	private AnaliseEstatisticaService analiseEstatisticaService = new AnaliseEstatisticaService();
	
	private List<AnaliseEstatistica> listarPorTipoEIntervalo(String tipoEstatistica, int intervalo) throws Exception {
		AnaliseEstatisticaService analiseEstatisticaService = new AnaliseEstatisticaService();
		
		List<AnaliseEstatistica> listaAnaliseEstatistica = analiseEstatisticaService.listarPorTipoEIntervalo(TipoEstatisticaEnum.descricaoToEnum(tipoEstatistica), intervalo);

		return listaAnaliseEstatistica;
	}
	
	@SuppressWarnings("static-access")
	public List<AnaliseEstatistica> obterEstatisticas(String tipoEstatistica, int intervalo) throws Exception {
		List<AnaliseEstatistica> listaAnaliseEstatistica = listarPorTipoEIntervalo(tipoEstatistica, intervalo);
		
		// Caso a lista retorne vazia, significa que ainda não existe estatística calculada para o intervalo selecionado,
		// então calcula e salva na base de dados
		if(listaAnaliseEstatistica == null || listaAnaliseEstatistica.isEmpty()) {
			sorteios = resultadosJogosController.listar();
			jogosSorteados = resultadosJogosController.obterJogosDosSorteios(sorteios);
			Util.ordenarDezenasJogos(jogosSorteados);

			listaAnaliseEstatistica.addAll(EstatisticaUtil.obterEstatisticaPorNome(tipoEstatistica).verificarEstatistica(jogosSorteados, intervalo));
			//analiseEstatisticaService.salvarTodos(listaAnaliseEstatistica);
		}
		
		return listaAnaliseEstatistica;
	}
}
