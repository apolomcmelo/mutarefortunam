package br.com.apolo.mc.melo.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.util.Util;

public class QuantidadeCadaDezenaController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int QTD_CHAVE_CONSIDERADA = 10; // Indica quantos jogos anteriores serão considerados para o cálculo
	private int QTD_PROCURADA = 0x0; // Inidica a quantidade que a dezena ocorreu nos últimos 10 jogos para buscar a primeira ocorrência
	
	private List<Sorteio> sorteios;
	private List<int[]> jogosSorteados;

	private ResultadosJogosController resultadosJogosController = new ResultadosJogosController();
	
	public List<int[][]> buscarUltimosSorteios(int qtdSorteios) throws Exception {
		List<int[][]> listMat = new ArrayList<int[][]>();
		
		// Busca jogos
		sorteios = resultadosJogosController.listar();
		jogosSorteados = resultadosJogosController.obterJogosDosSorteios(sorteios);
		Util.ordenarDezenasJogos(jogosSorteados);
		
		int initSortCalc = jogosSorteados.size() - qtdSorteios;
		
		for(int i=initSortCalc; i<jogosSorteados.size(); i++) {
			int[] jogo = jogosSorteados.get(i);
			int[][] mat = new int[6][3];
			
			mat[0][0] = jogo[0];
			mat[1][0] = jogo[1];
			mat[2][0] = jogo[2];
			mat[3][0] = jogo[3];
			mat[4][0] = jogo[4];
			mat[5][0] = jogo[5];
			
			mat[0][1] = buscarQtdDezenas(jogo[0], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			mat[1][1] = buscarQtdDezenas(jogo[1], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			mat[2][1] = buscarQtdDezenas(jogo[2], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			mat[3][1] = buscarQtdDezenas(jogo[3], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			mat[4][1] = buscarQtdDezenas(jogo[4], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			mat[5][1] = buscarQtdDezenas(jogo[5], jogosSorteados, i, i-QTD_CHAVE_CONSIDERADA);
			
			mat[0][2] = i+1;
			
			listMat.add(mat);
		}
		
		return listMat;	
	}
	
	private int buscarQtdDezenas(int dezena, List<int[]> jogos, int pInicio, int pFim) {
		if(pFim < 0) {
			pFim = 0;
		}
		if(pInicio > jogos.size()) {
			pInicio = jogos.size();
		}
		
		int qtdDezenas = 0x0;
		
		for(int i=pInicio-1; i>=pFim; i--) {
			int[] jogo = jogos.get(i);
			
			for(int j=0; j<jogo.length; j++) {
				if(jogo[j] == dezena) {
					qtdDezenas++;
				}
			}
		}
		
		return qtdDezenas;
	}
	
	public List<int[][]> buscarPrimeirasOcorrencias(List<int[][]> matrizesSorteios) {
		List<int[][]> listMat = new ArrayList<int[][]>();
		
		for(int i=0; i<matrizesSorteios.size(); i++) {
			int[][] mat = matrizesSorteios.get(i);
			int[][] matCopy = new int[6][3];
			
			for(int j=0; j<6; j++) {
				matCopy[j][0] = mat[j][0];
				
				if(mat[j][1] == QTD_PROCURADA) {
					matCopy[j][1] = buscarPrimeiraOcorrencia(mat[j][0], jogosSorteados, mat[0][2]);
				} else {
					matCopy[j][1] = -1;
				}
			}
			matCopy[0][2] = mat[0][2];
			listMat.add(matCopy);
		}
		
		return listMat;
	}
	
	private int buscarPrimeiraOcorrencia(int dezena, List<int[]> jogos, int pInicio) {
		int posOc = 1;
		
		for(int i=pInicio-2; i>=0; i--) {
			int[] jogo = jogos.get(i);
			
			for(int j=0; j<jogo.length; j++) {
				if(jogo[j] == dezena) {
					return posOc;
				}
			}
			
			posOc++;
		}
		
		return posOc;
	}
	
	public List<int[]> buscarEstatisticasProximoSorteio() {
		List<int[]> estProxSort = new ArrayList<int[]>();
		
		for(int i=1; i<=60; i++) {
			int[] vet = new int[3];
			
			vet[0] = i;
			vet[1] = buscarQtdDezenas(i, jogosSorteados, jogosSorteados.size(), jogosSorteados.size()-QTD_CHAVE_CONSIDERADA);	
			vet[2] = (vet[1] == QTD_PROCURADA) ? buscarPrimeiraOcorrencia(vet[0], jogosSorteados, jogosSorteados.size()+1) : -1;
	
			estProxSort.add(vet);
		}
		
		return estProxSort;
	}
}
