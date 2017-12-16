package br.com.apolo.mc.melo.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.apolo.mc.melo.controllers.ResultadosJogosController;
import br.com.apolo.mc.melo.entidades.Sorteio;

@ViewScoped
@ManagedBean(name="resultadosJogosView")
public class ResultadosJogosView {
	private boolean ordemDezenas = true;
	
	private List<Sorteio> sorteios;
	private List<Sorteio> sorteiosOrdemCrescente = new ArrayList<Sorteio>();
	private List<Sorteio> sorteiosOrdemSorteada = new ArrayList<Sorteio>();
	
	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();

	@PostConstruct
	private void init() {
		try {
			//Inicia como padrão de ordenação das dezenas como a forma que foram sorteadas
			ordemDezenas = true;
			
			buscarJogos();
			
			sorteios = sorteiosOrdemSorteada;
			
			if(sorteios == null) {
				sorteios = new ArrayList<Sorteio>();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}

	@SuppressWarnings("static-access")
	private void buscarJogos() throws Exception {
		sorteiosOrdemSorteada = resultadosJogosController.listar();
		sorteiosOrdemCrescente = new ArrayList<Sorteio>();
		
		
		if(sorteiosOrdemSorteada != null) {
			
			for(int i=0; i<sorteiosOrdemSorteada.size(); i++) {
				sorteiosOrdemCrescente.add(new Sorteio(sorteiosOrdemSorteada.get(i)));
				
				Arrays.sort(sorteiosOrdemCrescente.get(i).getDezenas());
			}
		}
	}

	public void atualizarOrdemDezenas() {
		sorteios = isOrdemDezenas() ? sorteiosOrdemSorteada : sorteiosOrdemCrescente;
	}
	
	public boolean isOrdemDezenas() {
		return ordemDezenas;
	}

	public void setOrdemDezenas(boolean ordemDezenas) {
		this.ordemDezenas = ordemDezenas;
	}

	public List<Sorteio> getSorteios() {
		return sorteios;
	}

	public void setSorteios(List<Sorteio> sorteios) {
		this.sorteios = sorteios;
	}
}
