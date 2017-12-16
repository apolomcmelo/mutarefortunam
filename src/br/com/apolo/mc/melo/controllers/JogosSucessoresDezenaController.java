package br.com.apolo.mc.melo.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.util.Util;

public class JogosSucessoresDezenaController implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<Sorteio> sorteios = new ArrayList<Sorteio>();
	public static List<Sorteio> sorteiosSucessores = new ArrayList<Sorteio>();

	public static List<int[]> jogosSorteados = new ArrayList<int[]>();

	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();

	/**
	 * Busca os jogos sucessores à dezena procurada
	 * 
	 * @param dezena
	 * @return Lista dos sorteios que sucedem à dezena procurada
	 */
	public List<Sorteio> buscarSorteiosSucessores(int dezena) throws Exception {
			
		// Busca jogos
		sorteios = resultadosJogosController.listar();
		jogosSorteados = resultadosJogosController.obterJogosDosSorteios(sorteios);
		Util.ordenarDezenasJogos(jogosSorteados);

		// Busca a dezena nos jogos
		for (int i = 0; i < jogosSorteados.size(); i++) {
			int[] jogo = jogosSorteados.get(i);
			
			boolean temQuinze = false;
			boolean temDezessete = false;
			boolean temVinte = false;
			boolean temTrintaUm = false;
			boolean temQuarentaUm = false;
			boolean temQuarentaOito = false;

			for (int j = 0; j < jogo.length; j++) {
				switch(jogo[j]) {
				case 15:
					temQuinze = true;
					break;
				case 17:
					temDezessete = true;
					break;
				case 20:
					temVinte = true;
					break;
				case 31:
					temTrintaUm= true;
					break;
				case 41:
					temQuarentaUm= true;
					break;
				case 48:
					temQuarentaOito = true;
					break;
				}
			}
			
			if(temDezessete && temVinte && temTrintaUm) {
				sorteiosSucessores.add(sorteios.get(i));
			}
		}
		
		return sorteiosSucessores;
	}
}
