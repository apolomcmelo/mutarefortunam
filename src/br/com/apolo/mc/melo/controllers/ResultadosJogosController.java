package br.com.apolo.mc.melo.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.services.SorteioService;

public class ResultadosJogosController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static List<Sorteio> sorteios = new ArrayList<Sorteio>();
	
	/**
	 * Realiza a busca de todos os sorteios já feitos
	 * @return
	 * @throws Exception
	 */
	public static List<Sorteio> listar() throws Exception {
		SorteioService sorteioService = new SorteioService();
		
		List<Sorteio> listaSorteios = sorteioService.listar();

		return listaSorteios;
	}
	
	/**
	 * Obtém os dados do sorteio solicitado
	 * 
	 * @param concurso - Número do concurso procurado
	 * @return
	 * @throws Exception
	 */
	public static Sorteio buscarSorteio(int concurso) throws Exception {
		SorteioService sorteioService = new SorteioService();
		
		Sorteio ultimoSorteio = sorteioService.buscarSorteio(concurso);
		
		return ultimoSorteio;
	}
	
	/**
	 * Obtém os dados do último sorteio realizado
	 * @return
	 * @throws Exception
	 */
	public static Sorteio buscarUltimoSorteio() throws Exception {
		SorteioService sorteioService = new SorteioService();
		
		Sorteio ultimoSorteio = sorteioService.buscarUltimoSorteio();
		
		return ultimoSorteio;
	}
	
	/**
	 * Obtém apenas as informações das dezenas sorteadas, descartando data e números de sorteio
	 * @param listaSorteios
	 * @return
	 */
	public static List<int[]> obterJogosDosSorteios(List<Sorteio> listaSorteios) {
		List<int[]> jogos = new ArrayList<int[]>();
		
		for(int i=0; i<listaSorteios.size(); i++) {
			jogos.add(listaSorteios.get(i).getDezenas());
		}
		
		return jogos;
	}
}