package br.com.apolo.mc.melo.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.apolo.mc.melo.controllers.GerarJogosController;
import br.com.apolo.mc.melo.controllers.ResultadosJogosController;
import br.com.apolo.mc.melo.entidades.Dezena;

@ViewScoped
@ManagedBean(name="historicoJogosView")
public class HistoricoJogosView {
	private List<Dezena[]> historicoJogos;
	
	private GerarJogosController gerarJogosController = new GerarJogosController();
	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();
	
	@PostConstruct
	private void init() {
		try {
			buscarJogos();
			iniciarLista();
			atualizarCorDezenasSorteadas();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}
	
	private void buscarJogos() throws Exception {
		resultadosJogosController.sorteios = resultadosJogosController.listar();
		gerarJogosController.jogosSorteados = resultadosJogosController.obterJogosDosSorteios(ResultadosJogosController.sorteios);
		Collections.reverse(gerarJogosController.jogosSorteados);
	}
	
	private void iniciarLista() {
		historicoJogos = new ArrayList<Dezena[]>();
		
//		for(int i=0; i<gerarJogosController.jogosSorteados.size(); i++) {
		for(int i=0; i<11; i++) {
			historicoJogos.add(criarVetorDezenas());
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
		for(int i=0; i<historicoJogos.size(); i++) {
			for(int j=0; j<gerarJogosController.jogosSorteados.get(i).length; j++) {
				historicoJogos.get(i)[gerarJogosController.jogosSorteados.get(i)[j]-1].setCor("teste-dezena-sorteada");		
			}
		}
	}

	public List<Dezena[]> getHistoricoJogos() {
		return historicoJogos;
	}

	public void setHistoricoJogos(List<Dezena[]> historicoJogos) {
		this.historicoJogos = historicoJogos;
	}
}
