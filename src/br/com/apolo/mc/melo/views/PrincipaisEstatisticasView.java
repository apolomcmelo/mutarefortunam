package br.com.apolo.mc.melo.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.apolo.mc.melo.controllers.AnaliseEstatisticaController;
import br.com.apolo.mc.melo.controllers.ResultadosJogosController;
import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.regras.RegraDigitosDobrados;
import br.com.apolo.mc.melo.regras.RegraMediaEntreNumeros;
import br.com.apolo.mc.melo.regras.RegraNumerosSeguidos;
import br.com.apolo.mc.melo.regras.RegraParesImpares;
import br.com.apolo.mc.melo.regras.RegraQuadrantes;
import br.com.apolo.mc.melo.regras.RegraQuantidadeDigitos;
import br.com.apolo.mc.melo.regras.RegraSomaDigitos;
import br.com.apolo.mc.melo.util.Util;

@ViewScoped
@ManagedBean(name = "principaisEstatisticasView")
public class PrincipaisEstatisticasView {
	private static final int QTD_DEZENAS_MENOS_SORTEADAS = 10;
	private static final int QTD_DEZENAS_MAIS_SORTEADAS = 10;
	private static final String ESTILO_DEZENA_MENOS_SORTEADA = "color: lightcoral !important;";
	private static final String ESTILO_DEZENA_MAIS_SORTEADA = "color: lime !important;";
	
	private Sorteio ultimoSorteio;
	private String dataUltimoSorteio;
	private List<Integer> dezenasMenosSorteadas = new ArrayList<Integer>();
	private List<Integer> dezenasMaisSorteadas = new ArrayList<Integer>();
	private List<AnaliseEstatistica> listaAnaliseDezenasMaisSorteadas;

	// Regras que se aplicam ou não no último sorteio
	private String digitosDobrados;	
	private String mediaEntreNumeros;
	private String numerosSeguidos;
	private String paresImpares;
	private String quadrantes;
	private String quantidadeDigitos;
	private String somaDigitos;
	
	private AnaliseEstatisticaController analiseEstatisticaController = new AnaliseEstatisticaController();
	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();

	@PostConstruct
	private void init() {
		try {
			buscarUltimoSorteio();
			
			dataUltimoSorteio = new SimpleDateFormat("dd/MM/yyyy").format(ultimoSorteio.getData());
			
			analisarRegrasAplicadas();
			
			listaAnaliseDezenasMaisSorteadas = analiseEstatisticaController.obterEstatisticas(TipoEstatisticaEnum.NUMEROS_SORTEADOS.getDescricao(), 0x1);
			
			encontrarDezenasMaisSorteadas();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}

	/**
	 * Obtém o último sorteio realizado
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private void buscarUltimoSorteio() throws Exception {
		ultimoSorteio = resultadosJogosController.buscarUltimoSorteio();
		if (ultimoSorteio == null) {
			ultimoSorteio = new Sorteio();
		}
	}

	/**
	 * Analisa quais regras foram aplicadas para o último sorteio realizado
	 */
	private void analisarRegrasAplicadas() {
		RegraQuantidadeDigitos regraQuantidadeDigitos = new RegraQuantidadeDigitos();
		RegraSomaDigitos regraSomaDigitos = new RegraSomaDigitos();
		RegraParesImpares regraParesImpares = new RegraParesImpares();
		RegraDigitosDobrados regraDigitosDobrados = new RegraDigitosDobrados();
		RegraQuadrantes regraQuadrantes = new RegraQuadrantes();
		RegraMediaEntreNumeros regraMediaEntreNumeros = new RegraMediaEntreNumeros();
		RegraNumerosSeguidos regraNumerosSeguidos = new RegraNumerosSeguidos();
		
		digitosDobrados = regraDigitosDobrados.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		mediaEntreNumeros = regraMediaEntreNumeros.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		numerosSeguidos = regraNumerosSeguidos.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		paresImpares = regraParesImpares.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		quadrantes = regraQuadrantes.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		quantidadeDigitos = regraQuantidadeDigitos.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
		somaDigitos = regraSomaDigitos.validarJogo(ultimoSorteio.getDezenas()) ? Util.ICONE_CHECK :Util.ICONE_ERRO;
	}
	
	/**
	 * Inicia a lista com as dezenas mais sorteadas durante o histórico de jogos
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void encontrarDezenasMaisSorteadas() {
		Comparator c = new Comparator<AnaliseEstatistica>() {

			@Override
			public int compare(AnaliseEstatistica o1, AnaliseEstatistica o2) {
				return o1.getPercentual().compareTo(o2.getPercentual());
			}
		};
		
		listaAnaliseDezenasMaisSorteadas.sort(c);
		Collections.reverse(listaAnaliseDezenasMaisSorteadas);
		
		dezenasMaisSorteadas = new ArrayList<Integer>();
		dezenasMenosSorteadas = new ArrayList<Integer>();
		
		for(int i=0; i<QTD_DEZENAS_MAIS_SORTEADAS; i++) {
			dezenasMaisSorteadas.add(Integer.parseInt(listaAnaliseDezenasMaisSorteadas.get(i).getId().getElemento()));
		}
		
		Collections.reverse(listaAnaliseDezenasMaisSorteadas);
		
		for(int i=0; i<QTD_DEZENAS_MENOS_SORTEADAS; i++) {
			dezenasMenosSorteadas.add(Integer.parseInt(listaAnaliseDezenasMaisSorteadas.get(i).getId().getElemento()));
		}
		
		Collections.reverse(listaAnaliseDezenasMaisSorteadas);

		dezenasMenosSorteadas.sort(null);
		dezenasMaisSorteadas.sort(null);
	}
	
	/**
	 * Atualiza a cor da borda das dezenas do último sorteio, caso elas façam parte das dezenas mais ou menos sorteadas
	 * 
	 * @param dezena Representa a dezena do último sorteio
	 */
	public String dezenaMaisMenosSorteada(int dezena){
		String estiloDezena = "";
		
		if(dezenasMaisSorteadas.contains(dezena)) {
			estiloDezena = ESTILO_DEZENA_MAIS_SORTEADA;
		} else if(dezenasMenosSorteadas.contains(dezena)) {
			estiloDezena = ESTILO_DEZENA_MENOS_SORTEADA;
		}
		
		return estiloDezena;		
	}

	// Getters e Setters
	public Sorteio getUltimoSorteio() {
		return ultimoSorteio;
	}

	public void setUltimoSorteio(Sorteio ultimoSorteio) {
		this.ultimoSorteio = ultimoSorteio;
	}

	public String getDataUltimoSorteio() {
		return dataUltimoSorteio;
	}

	public void setDataUltimoSorteio(String dataUltimoSorteio) {
		this.dataUltimoSorteio = dataUltimoSorteio;
	}

	public List<Integer> getDezenasMaisSorteadas() {
		return dezenasMaisSorteadas;
	}

	public void setDezenasMaisSorteadas(List<Integer> dezenasMaisSorteadas) {
		this.dezenasMaisSorteadas = dezenasMaisSorteadas;
	}
	
	public List<Integer> getDezenasMenosSorteadas() {
		return dezenasMenosSorteadas;
	}

	public void setDezenasMenosSorteadas(List<Integer> dezenasMenosSorteadas) {
		this.dezenasMenosSorteadas = dezenasMenosSorteadas;
	}

	public List<AnaliseEstatistica> getListaAnaliseDezenasMaisSorteadas() {
		return listaAnaliseDezenasMaisSorteadas;
	}

	public void setListaAnaliseDezenasMaisSorteadas(
			List<AnaliseEstatistica> listaAnaliseDezenasMaisSorteadas) {
		this.listaAnaliseDezenasMaisSorteadas = listaAnaliseDezenasMaisSorteadas;
	}

	public String getDigitosDobrados() {
		return digitosDobrados;
	}

	public void setDigitosDobrados(String digitosDobrados) {
		this.digitosDobrados = digitosDobrados;
	}

	public String getMediaEntreNumeros() {
		return mediaEntreNumeros;
	}

	public void setMediaEntreNumeros(String mediaEntreNumeros) {
		this.mediaEntreNumeros = mediaEntreNumeros;
	}

	public String getNumerosSeguidos() {
		return numerosSeguidos;
	}

	public void setNumerosSeguidos(String numerosSeguidos) {
		this.numerosSeguidos = numerosSeguidos;
	}

	public String getParesImpares() {
		return paresImpares;
	}

	public void setParesImpares(String paresImpares) {
		this.paresImpares = paresImpares;
	}

	public String getQuadrantes() {
		return quadrantes;
	}

	public void setQuadrantes(String quadrantes) {
		this.quadrantes = quadrantes;
	}

	public String getQuantidadeDigitos() {
		return quantidadeDigitos;
	}

	public void setQuantidadeDigitos(String quantidadeDigitos) {
		this.quantidadeDigitos = quantidadeDigitos;
	}

	public String getSomaDigitos() {
		return somaDigitos;
	}

	public void setSomaDigitos(String somaDigitos) {
		this.somaDigitos = somaDigitos;
	}
}
