package br.com.apolo.mc.melo.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.apolo.mc.melo.controllers.QuantidadeCadaDezenaController;

@ViewScoped
@ManagedBean(name="quantidadeCadaDezenaView")
public class QuantidadeCadaDezenaView {
	private int qtdSorteios = 10;
	
	private List<int[][]> listaMatrizesSorteios;
	private List<int[][]> listaMatrizesPrimOc;
	
	private List<int[]> estProxSort;
	
	private QuantidadeCadaDezenaController quantidadeCadaDezenaController = new QuantidadeCadaDezenaController();
	
	@PostConstruct
	private void init() {
		buscarSorteios();
		iniciarEstatisticas();
	}
	
	// Métodos de Negócio
	public void buscarSorteios() {
		try {			
			buscarUltimosSorteios(qtdSorteios);
			buscarPrimeiraOcorrencia();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}
	
	private void buscarUltimosSorteios(int qtdSorteios) throws Exception {
		listaMatrizesSorteios = quantidadeCadaDezenaController.buscarUltimosSorteios(qtdSorteios);
	}
	
	private void buscarPrimeiraOcorrencia() { 
		listaMatrizesPrimOc = quantidadeCadaDezenaController.buscarPrimeirasOcorrencias(listaMatrizesSorteios);
	}
	
	private void iniciarEstatisticas() {
		estProxSort = quantidadeCadaDezenaController.buscarEstatisticasProximoSorteio();
	}

	// Getters e Setters
	public int getQtdSorteios() {
		return qtdSorteios;
	}

	public void setQtdSorteios(int qtdSorteios) {
		this.qtdSorteios = qtdSorteios;
	}

	public List<int[][]> getListaMatrizesSorteios() {
		return listaMatrizesSorteios;
	}

	public void setListaMatrizesSorteios(List<int[][]> listaMatrizesSorteios) {
		this.listaMatrizesSorteios = listaMatrizesSorteios;
	}

	public List<int[][]> getListaMatrizesPrimOc() {
		return listaMatrizesPrimOc;
	}

	public void setListaMatrizesPrimOc(List<int[][]> listaMatrizesPrimOc) {
		this.listaMatrizesPrimOc = listaMatrizesPrimOc;
	}

	public List<int[]> getEstProxSort() {
		return estProxSort;
	}

	public void setEstProxSort(List<int[]> estProxSort) {
		this.estProxSort = estProxSort;
	}
}
