package br.com.apolo.mc.melo.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.apolo.mc.melo.controllers.GerarJogosController;
import br.com.apolo.mc.melo.entidades.Dezena;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;
import br.com.apolo.mc.melo.regras.RegraDigitosDobrados;
import br.com.apolo.mc.melo.regras.RegraMediaEntreNumeros;
import br.com.apolo.mc.melo.regras.RegraNumerosSeguidos;
import br.com.apolo.mc.melo.regras.RegraParesImpares;
import br.com.apolo.mc.melo.regras.RegraQuadrantes;
import br.com.apolo.mc.melo.regras.RegraQuantidadeDigitos;
import br.com.apolo.mc.melo.regras.RegraSomaDigitos;

@ViewScoped
@ManagedBean(name="gerarJogosView")
public class GerarJogosView {
	private int qtdJogos = 1;
	private int qtdDezenas = 6;
	
	private int modoGeracao = 0;
	
	private String numFixos;
	private String jogoSorteadoAExibir;
	
	private List<Regra> regrasGerais;
	private List<Regra> regrasDeValidacao;
	private List<String> jogosSorteados;
	
	private List<Dezena[]> volantesJogosSorteados;
	
	private List<int[]> jogosPremiados;

	private String[] regrasSelecionadas;
	
	private GerarJogosController gerarJogosController = new GerarJogosController();
	
	@PostConstruct
	private void init() {
		iniciarRegrasGerais();
	}
	
	// Métodos de negócio
	public void gerarJogo() {
		iniciarRegrasDeValidacao();
		
		jogosSorteados = new ArrayList<String>();		
		
		//TODO Melhorar implementação para receber lista. Essa é utilizada para testes de View
		jogosPremiados = gerarJogosController.gerarJogo(qtdJogos, qtdDezenas, regrasDeValidacao, modoGeracao);
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<jogosPremiados.size(); i++) {
			for(int j=0; j<qtdDezenas; j++) {
				sb.append(jogosPremiados.get(i)[j]);
				
				if(j != qtdDezenas - 1) {
					sb.append(" - ");
				}
			}
			
			jogosSorteados.add(sb.toString());
			sb = new StringBuilder();
		}
		iniciarLista();
		atualizarCorDezenasSorteadas();
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Jogo gerado:", jogo));
	}
	
	// Métodos de iniciação de variáveis
	//TODO Encontrar melhor forma para instanciar regras dinamicamente
	/**
	 * Inicia a lista de regras, contendo todas as regras já implementadas
	 */
	private void iniciarRegrasGerais() {
		regrasGerais = new ArrayList<Regra>();
		
		RegraQuantidadeDigitos regraQuantidadeDigitos = new RegraQuantidadeDigitos();
		RegraSomaDigitos regraSomaDigitos = new RegraSomaDigitos();
		RegraParesImpares regraParesImpares = new RegraParesImpares();
		RegraDigitosDobrados regraDigitosDobrados = new RegraDigitosDobrados();
		RegraQuadrantes regraQuadrantes = new RegraQuadrantes();
		RegraMediaEntreNumeros regraMediaEntreDigitos = new RegraMediaEntreNumeros();
		RegraNumerosSeguidos regraNumerosSeguidos = new RegraNumerosSeguidos();
		
		regrasGerais.add(regraDigitosDobrados);
		regrasGerais.add(regraMediaEntreDigitos);
		regrasGerais.add(regraNumerosSeguidos);
		regrasGerais.add(regraParesImpares);
		regrasGerais.add(regraQuadrantes);
		regrasGerais.add(regraQuantidadeDigitos);
		regrasGerais.add(regraSomaDigitos);	
	}
	
	/**
	 * Instancia as regras de validação baseado na seleção de regras do usuário
	 */
	private void iniciarRegrasDeValidacao() {
		regrasDeValidacao = new ArrayList<Regra>();
		
		if(regrasSelecionadas != null) {
			for(int i=0; i<regrasSelecionadas.length; i++) {
				if (TipoEstatisticaEnum.MEDIA_ENTRE_NUMEROS.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraMediaEntreNumeros());
					
				} else if (TipoEstatisticaEnum.DIGITOS_DOBRADOS.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraDigitosDobrados());
					
				} else if (TipoEstatisticaEnum.NUMEROS_SEGUIDOS.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraNumerosSeguidos());
				
				} else if (TipoEstatisticaEnum.QUADRANTES.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraQuadrantes());
					
				} else if (TipoEstatisticaEnum.QUANTIDADE_DIGITOS.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraQuantidadeDigitos());
					
				} else if (TipoEstatisticaEnum.SOMA_DIGITOS.getDescricao().equals(regrasSelecionadas[i])) {
					regrasDeValidacao.add(new RegraSomaDigitos());
				}
			}
		}
	}
	
	//TODO Organizar os métodos auxiliares junto dos comentários
	private void iniciarLista() {
		volantesJogosSorteados = new ArrayList<Dezena[]>();
		
		for(int i=0; i<jogosSorteados.size(); i++) {
			volantesJogosSorteados.add(criarVetorDezenas());
		}
	}

	private Dezena[] criarVetorDezenas() {
		Dezena[] jogo = new Dezena[60];
		
		for(int i=0; i<60; i++) {
			jogo[i] = new Dezena("", i+1);
		}
		
		return jogo;
	}
	
	private void atualizarCorDezenasSorteadas() {
		for(int i=0; i<volantesJogosSorteados.size(); i++) {
			for(int j=0; j<jogosPremiados.get(i).length; j++) {
				volantesJogosSorteados.get(i)[jogosPremiados.get(i)[j]-1].setCor("dezena-volante-sorteada");		
			}
		}
	}
	
	// Getters e Setters
	public int getQtdJogos() {
		return qtdJogos;
	}

	public void setQtdJogos(int qtdJogos) {
		this.qtdJogos = qtdJogos;
	}

	public int getQtdDezenas() {
		return qtdDezenas;
	}

	public void setQtdDezenas(int qtdDezenas) {
		this.qtdDezenas = qtdDezenas;
	}

	public int getModoGeracao() {
		return modoGeracao;
	}

	public void setModoGeracao(int modoGeracao) {
		this.modoGeracao = modoGeracao;
	}

	public String getNumFixos() {
		return numFixos;
	}

	public void setNumFixos(String numFixos) {
		this.numFixos = numFixos;
	}

	public String getJogoSorteadoAExibir() {
		return jogoSorteadoAExibir;
	}

	public void setJogoSorteadoAExibir(String jogoSorteadoAExibir) {
		this.jogoSorteadoAExibir = jogoSorteadoAExibir;
	}

	public List<Regra> getRegrasGerais() {
		return regrasGerais;
	}
	public void setRegrasGerais(List<Regra> regrasGerais) {
		this.regrasGerais = regrasGerais;
	}
	public List<Regra> getRegrasDeValidacao() {
		return regrasDeValidacao;
	}
	public void setRegrasDeValidacao(List<Regra> regrasDeValidacao) {
		this.regrasDeValidacao = regrasDeValidacao;
	}
	public List<String> getJogosSorteados() {
		return jogosSorteados;
	}

	public void setJogosSorteados(List<String> jogosSorteados) {
		this.jogosSorteados = jogosSorteados;
	}

	public List<Dezena[]> getVolantesJogosSorteados() {
		return volantesJogosSorteados;
	}

	public void setVolantesJogosSorteados(List<Dezena[]> volantesJogosSorteados) {
		this.volantesJogosSorteados = volantesJogosSorteados;
	}

	public String[] getRegrasSelecionadas() {
		return regrasSelecionadas;
	}
	public void setRegrasSelecionadas(String[] regrasSelecionadas) {
		this.regrasSelecionadas = regrasSelecionadas;
	}
}