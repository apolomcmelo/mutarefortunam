	package br.com.apolo.mc.melo.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.enums.TipoJogo;
import br.com.apolo.mc.melo.estatisticas.EstatisticaNumerosSorteados;
import br.com.apolo.mc.melo.interfaces.Regra;
import br.com.apolo.mc.melo.util.Util;

public class GerarJogosController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static List<Sorteio> sorteios = new ArrayList<Sorteio>();
	public static List<int[]> jogosSorteados = new ArrayList<int[]>();

	private List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();
	private EstatisticaNumerosSorteados estNumSort = new EstatisticaNumerosSorteados();
	
	private int[] numerosPossiveis;
	
	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();

	/**
	 * Responsável por gerar o jogo baseado nas regras escolhidas
	 * @param qtdJogos
	 * @param qtdDezenas
	 * @param regras
	 * @param modoGeracao
	 */
	public List<int[]> gerarJogo(int qtdJogos, int qtdDezenas, List<Regra> regras, int modoGeracao) {
		numerosPossiveis = Util.gerarNumerosPossiveis(TipoJogo.SENA.getQtdDezenas());
		
		List<int[]> jogosPremiados = new ArrayList<int[]>();
		
		// Busca jogos
		try {
			sorteios = resultadosJogosController.listar();
			jogosSorteados = resultadosJogosController.obterJogosDosSorteios(sorteios);
			Util.ordenarDezenasJogos(jogosSorteados);
		
			// Calcula estatisticas
			estatisticas.addAll(estNumSort.verificarEstatistica(jogosSorteados, 0));
			
			int[] jogoPrem = new int[qtdDezenas];
			
			for(int i=0; i<qtdJogos; i++) {
				jogoPrem = gerarSequenciaPremiada(qtdDezenas, regras, modoGeracao);
				
				jogosPremiados.add(jogoPrem);
			}
			
			return jogosPremiados;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	
	/**
	 * Responsavel por gerar uma sequencia de números aleatórios que
	 * podem ser premiados pelo jogo
	 * @param qtdDezenas
	 * @param regras
	 * @param modoGeracao
	 * 
	 * @return Retorna uma sequencia de numeros para jogar que nunca foram sorteados
	 */
	private int[] gerarSequenciaPremiada(int qtdDezenas, List<Regra> regras, int modoGeracao)
	{	
		int[] sequencia = new int[qtdDezenas];
		double dP = Util.calcularDesvioPadrao(jogosSorteados, numerosPossiveis, estNumSort.getEstNumSort());
		
		int fatorMais = 0, fatorMedio = 0, fatorPouco = 0;
		Random r = new Random();
		do{
			//TODO Alterar para utilizar pelas dezenas mais, menos e mediamente sorteadas
			fatorMais = r.nextInt(2) + 1; // Representa o 0
			fatorPouco = r.nextInt(3) + 1; // Representa o 1
			fatorMedio = r.nextInt(2); // Representa o 2
		}while((fatorMais + fatorMedio + fatorPouco) != 6);	
		
		for(int i=0; i<sequencia.length; i++)
		{
			switch(modoGeracao) {
			case 0:
				sequencia[i] = Util.gerarNumeroAleatorio(numerosPossiveis ,estNumSort.getEstNumSort(), dP);
				break;
			case 1:
				sequencia[i] = obterNumeroProvavel();
				break;
			case 2:
				sequencia[i] = obterNumeroFrequenciaSorteio(i, fatorMais, fatorPouco);
				break;
			}
			
			// Verifica se o número gerado já foi gerado no mesmo jogo para que não se repita 
			for(int j=0; j<i; j++)
			{
				if(sequencia[j] == sequencia[i])
				{
					i--;
					break;
				}
			}
		}
		
		Arrays.sort(sequencia);
		List<int[]> combinacoesSequencia = Util.obterCombinacoesSequencia(sequencia);

		List<int[]> possiveisSorteados = new ArrayList<int[]>();
		
		for(int i=0; i<combinacoesSequencia.size(); i++) {
			for(int j=0; j<regras.size(); j++)
			{
				if(!regras.get(j).validarJogo(combinacoesSequencia.get(i)))
				{
					break;
				}
				
				if(j == regras.size() - 1) {
					possiveisSorteados.add(combinacoesSequencia.get(i));
				}
			}
		}
		
		for(int i=0; i<possiveisSorteados.size(); i++) {
			if(!verificarSequenciaJaSorteada(possiveisSorteados.get(i))) {
				return sequencia;
			}
		}
		
		return gerarSequenciaPremiada(qtdDezenas, regras, modoGeracao);
	}
	
	// Métodos auxiliares		
	/**
	 * Verifica se uma sequencia ja foi sorteada alguma vez.
	 * @param sequencia
	 * @return Retorna true se a sequencia ja foi sorteada, false se uma sequencia possivelmente premiada
	 */
	private boolean verificarSequenciaJaSorteada(int[] sequencia)
	{
		int[] jogoSorteado = new int[6];
		int contIguais;
		
		//TODO Retirar isso
//		int[] ultimoSorteio = {3, 23, 26, 35, 39, 49};
		
//		jogosSorteados.clear();
//		jogosSorteados.add(ultimoSorteio);
		
		for(int i=0; i<jogosSorteados.size(); i++) {
			jogoSorteado = jogosSorteados.get(i);
			
			contIguais = 0x0;
			
			for(int j=0; j<sequencia.length; j++) {
				if(jogoSorteado[j] == sequencia[j]) {
					contIguais++;
				}
			}
			if(contIguais == TipoJogo.SENA.getQtdDezenasSorteadas()) {
//				System.out.println("Concurso - " + (i+1));
//				System.out.println("Dezenas iguais - " + (contIguais));
				return true;
			}
		}
		return false;
	}
	
	//TODO Melhorar implementação
	private int obterNumeroDezenasMaisSorteadas() {
		Random r = new Random();
		int[] dezenasMaisSorteadas = new int[] {1, 3, 7, 18, 28, 30, 41, 44, 45, 60};
		
		int fatorSorte = r.nextInt(9);
		/*
		if(r.nextInt(100) >= 45) {
			fatorSorte += 10;
		}*/
		
		return dezenasMaisSorteadas[fatorSorte];
	}
	
	private int obterNumeroDezenasMediamenteSorteadas() {
		Random r = new Random();
		int[] dezenasMediamenteSorteadas = new int[] {2, 6, 8, 21, 22, 29, 31, 36, 42, 49, 50};
		
		int fatorSorte = r.nextInt(10);
		/*
		if(r.nextInt(100) >= 44) {
			fatorSorte += 10;
		}
		*/
		return dezenasMediamenteSorteadas[fatorSorte];
	}
	
	private int obterNumeroDezenasMenosSorteadas() {
		Random r = new Random();
		int[] dezenasMenosSorteadas = new int[] {5, 14, 15, 48, 54};
		
		int fatorSorte = r.nextInt(4);
		/*
		if(r.nextInt(100) <= 58) {
			fatorSorte += 10;
		}*/
		
		return dezenasMenosSorteadas[fatorSorte];
	}
	
	private int obterNumeroProvavel() {
		//int[] numerosProvaveis = new int[] {4, 5, 9, 10, 13, 14, 15, 17, 18, 21, 22, 23, 24, 25, 26, 29, 33, 34, 39, 41, 42, 46, 48, 51, 53, 54, 55};
		int[] numerosProvaveis = new int[] {5, 14, 15, 48, 54, 2, 6, 8, 21, 22, 29, 31, 36, 42, 49, 50, 1, 3, 7, 18, 28, 30, 41, 44, 45, 60};
		Random r = new Random();
		
		return numerosProvaveis[r.nextInt(numerosProvaveis.length)];
	}
	
	private int obterNumeroFrequenciaSorteio(int indiceDezena, int fatorMais, int fatorPouco) {
		if(indiceDezena < fatorMais) {
			return obterNumeroDezenasMaisSorteadas();
		} else if((fatorPouco > 0) && (indiceDezena < (fatorMais + fatorPouco))) {
			return obterNumeroDezenasMenosSorteadas();
		} else {
			return obterNumeroDezenasMediamenteSorteadas();
		}
	}
}
