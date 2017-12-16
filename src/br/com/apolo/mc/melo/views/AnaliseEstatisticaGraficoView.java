package br.com.apolo.mc.melo.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.apolo.mc.melo.controllers.AnaliseEstatisticaController;
import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;

@ViewScoped
@ManagedBean(name="analiseEstatisticaGraficoView")
public class AnaliseEstatisticaGraficoView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Constantes de configuração do gráfico
	private static final String LEGENDA_EIXO_X = "Nº de Concurso";
	private static final String LEGENDA_EIXO_Y = "Percentual";
	private static final String FORMATO_LEGENDA_EIXO_Y = "%.2f";
	private static final int VALOR_MINIMO_EIXOS = 0x0;
	
	private int intervalo = 300;
	private String estatisticaSelecionada;
	private List<String> listaEstatisticas;
	
	private LineChartModel graficoLinha;
	
	private Map<String, List<AnaliseEstatistica>> mapaAnaliseEstatistica;
	
	private AnaliseEstatisticaController analiseEstatisticaController = new AnaliseEstatisticaController();
	
	@PostConstruct
	private void init() {
		iniciarListaEstatistica();
		estatisticaSelecionada = listaEstatisticas.get(4);
		analisar();
	}

	private void iniciarListaEstatistica() {
		listaEstatisticas = new ArrayList<String>();
		
		for(int i=0; i<TipoEstatisticaEnum.values().length; i++) {
			listaEstatisticas.add(TipoEstatisticaEnum.values()[i].getDescricao());
		}
	}
	
	public void analisar() {
		graficoLinha = new LineChartModel();
		
		try {
			List<AnaliseEstatistica> listaAnaliseEstatistica = obterListaEstatistica();
			iniciarMapaAnaliseEstatistica(listaAnaliseEstatistica);
			popularGrafico(graficoLinha, mapaAnaliseEstatistica);
			configurarExibicaoGrafico(graficoLinha);
					
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}
	
	/**
	 * Obtém lista de estatísticas calculadas para o intervalo e regra selecionada
	 * @return
	 */
	private List<AnaliseEstatistica> obterListaEstatistica() throws Exception {
		return analiseEstatisticaController.obterEstatisticas(estatisticaSelecionada, intervalo);
	}
	
	/**
	 * Inicia o mapa de estatísticas a partir da lista de estatísticas
	 * @param listaAnaliseEstatistica
	 */
	private void iniciarMapaAnaliseEstatistica(List<AnaliseEstatistica> listaAnaliseEstatistica) {
		mapaAnaliseEstatistica = new HashMap<String, List<AnaliseEstatistica>>();
		
		for(int i=0; i<listaAnaliseEstatistica.size(); i++) {
			AnaliseEstatistica ae = listaAnaliseEstatistica.get(i);

			if(!mapaAnaliseEstatistica.containsKey(ae.getId().getElemento())) {
				mapaAnaliseEstatistica.put(ae.getId().getElemento(), new ArrayList<AnaliseEstatistica>());
			}
			
			mapaAnaliseEstatistica.get(ae.getId().getElemento()).add(ae);
		}
	}
	
	/**
	 * Responsável por criar as séries do gráfico para exibição
	 * @param grafico
	 * @param mapaAnaliseEstatistica
	 */
	private void popularGrafico(LineChartModel grafico, Map<String, List<AnaliseEstatistica>> mapaAnaliseEstatistica) {
		List<String> nomeSeries = new ArrayList<String>(mapaAnaliseEstatistica.keySet());
		
		for(int i=0; i<nomeSeries.size(); i++) {
			List<AnaliseEstatistica> listaAnaliseEstatistica = mapaAnaliseEstatistica.get(nomeSeries.get(i));
			
			LineChartSeries serie = new LineChartSeries();
			
			serie.setLabel(nomeSeries.get(i));
			
			for(int j=0; j<listaAnaliseEstatistica.size(); j++) {
				AnaliseEstatistica ae = listaAnaliseEstatistica.get(j);
				
				serie.set(ae.getId().getNumeroUltimoConcurso(), ae.getPercentual());
			}
			
			grafico.addSeries(serie);
		}	
	}
	
	/**
	 * Configura a exibição do gráfico, como legendas, formato de número, etc
	 * @param grafico
	 */
	private void configurarExibicaoGrafico(LineChartModel grafico) {
		// Configuração de apresentação do gráfico
		grafico.setLegendPosition("sw");
		grafico.setZoom(true);
		
		Axis eixoX = grafico.getAxis(AxisType.X);
		Axis eixoY = grafico.getAxis(AxisType.Y);
		
		// Configuração dos eixos
		eixoX.setLabel(LEGENDA_EIXO_X);
		eixoX.setMin(VALOR_MINIMO_EIXOS);
//		eixoX.setTickInterval(intervalo + "");
		
		eixoY.setMin(VALOR_MINIMO_EIXOS);
		eixoY.setLabel(LEGENDA_EIXO_Y);
		eixoY.setTickFormat(FORMATO_LEGENDA_EIXO_Y);	
	}
	
	// Getters e Setters
	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public String getEstatisticaSelecionada() {
		return estatisticaSelecionada;
	}

	public void setEstatisticaSelecionada(String estatisticaSelecionada) {
		this.estatisticaSelecionada = estatisticaSelecionada;
	}

	public List<String> getListaEstatisticas() {
		return listaEstatisticas;
	}

	public void setListaEstatisticas(List<String> listaEstatisticas) {
		this.listaEstatisticas = listaEstatisticas;
	}

	public LineChartModel getGraficoLinha() {
		return graficoLinha;
	}

	public void setGraficoLinha(LineChartModel graficoLinha) {
		this.graficoLinha = graficoLinha;
	}
}
