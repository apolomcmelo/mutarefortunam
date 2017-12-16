package br.com.apolo.mc.melo.testes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.apolo.mc.melo.controllers.GerarJogosController;
import br.com.apolo.mc.melo.controllers.ImportarSorteiosController;
import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.enums.TipoJogo;
import br.com.apolo.mc.melo.estatisticas.EstatisticaDigitosDobrados;
import br.com.apolo.mc.melo.estatisticas.EstatisticaMediaEntreNumeros;
import br.com.apolo.mc.melo.estatisticas.EstatisticaNumerosSeguidos;
import br.com.apolo.mc.melo.estatisticas.EstatisticaNumerosSorteados;
import br.com.apolo.mc.melo.estatisticas.EstatisticaParesImpares;
import br.com.apolo.mc.melo.estatisticas.EstatisticaQuadrantes;
import br.com.apolo.mc.melo.estatisticas.EstatisticaQuantidadeDigitos;
import br.com.apolo.mc.melo.estatisticas.EstatisticaSomaDigitos;
import br.com.apolo.mc.melo.interfaces.Regra;
import br.com.apolo.mc.melo.regras.RegraDigitosDobrados;
import br.com.apolo.mc.melo.regras.RegraMediaEntreNumeros;
import br.com.apolo.mc.melo.regras.RegraNumerosSeguidos;
import br.com.apolo.mc.melo.regras.RegraParesImpares;
import br.com.apolo.mc.melo.regras.RegraQuadrantes;
import br.com.apolo.mc.melo.regras.RegraQuantidadeDigitos;
import br.com.apolo.mc.melo.regras.RegraSomaDigitos;
import br.com.apolo.mc.melo.services.AnaliseEstatisticaService;
import br.com.apolo.mc.melo.services.SorteioService;
import br.com.apolo.mc.melo.util.Util;

public class MainClass {
	private static List<Sorteio> sorteios = new ArrayList<Sorteio>();
	private static List<int[]> jogosSorteados = new ArrayList<int[]>();
	
	private static int[] numerosPossiveis = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
		  21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		  41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60};
	
	private static List<AnaliseEstatistica> estatisticas = new ArrayList<AnaliseEstatistica>();
	
	private static EstatisticaNumerosSorteados estNumSort = new EstatisticaNumerosSorteados();
	private static EstatisticaDigitosDobrados estDigDob = new EstatisticaDigitosDobrados();
	private static EstatisticaNumerosSeguidos estNumSeg = new EstatisticaNumerosSeguidos();
	private static EstatisticaQuadrantes estQuad = new EstatisticaQuadrantes();
	private static EstatisticaMediaEntreNumeros estMedNum = new EstatisticaMediaEntreNumeros();
	private static EstatisticaQuantidadeDigitos estQtdDig = new EstatisticaQuantidadeDigitos();
	private static EstatisticaSomaDigitos estSomDig = new EstatisticaSomaDigitos();
	private static EstatisticaParesImpares estParImp = new EstatisticaParesImpares();
	
	private static List<Regra> regras = new ArrayList<Regra>();
	
	private static RegraQuantidadeDigitos regraQuantidadeDigitos = new RegraQuantidadeDigitos();
	private static RegraSomaDigitos regraSomaDigitos = new RegraSomaDigitos();
	private static RegraParesImpares regraParesImpares = new RegraParesImpares();
	private static RegraDigitosDobrados regraNumerosDobrados = new RegraDigitosDobrados();
	private static RegraQuadrantes regraQuadrantes = new RegraQuadrantes();
	private static RegraMediaEntreNumeros regraMediaEntreDigitos = new RegraMediaEntreNumeros();
	private static RegraNumerosSeguidos regraNumerosSeguidos = new RegraNumerosSeguidos();
	
	public static void main(String[] args) {
		ImportarSorteiosController importarSorteios = new ImportarSorteiosController();
		AnaliseEstatisticaService analiseEstatisticaService = new AnaliseEstatisticaService();
		GerarJogosController gerador = new GerarJogosController();
		try{
			// Importa arquivo conteudo historico de sorteios
//			importarSorteios.importarSorteios(); 
			
			// Busca jogos
			sorteios = listar();
			jogosSorteados = obterJogosDosSorteios(sorteios);
			Util.ordenarDezenasJogos(jogosSorteados);
			
//			// Calcula estatisticas
			estatisticas.addAll(estNumSort.verificarEstatistica(jogosSorteados, 0));
			estatisticas.addAll(estDigDob.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estNumSeg.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estQuad.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estMedNum.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estQtdDig.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estSomDig.verificarEstatistica(jogosSorteados, 171));
			estatisticas.addAll(estParImp.verificarEstatistica(jogosSorteados, 171));

			// Salva as estatisticas
//			analiseEstatisticaService.salvarTodos(estatisticas);
		
			// Inicia as regras
			regras.add(regraQuantidadeDigitos);
//			regras.add(regraSomaDigitos);
//			regras.add(regraParesImpares);
			regras.add(regraNumerosDobrados);
			regras.add(regraQuadrantes);
//			regras.add(regraMediaEntreDigitos);
			regras.add(regraNumerosSeguidos);
			
			int[] sequencia = {1, 7, 11, 32, 51, 59};
			
			for(int i=0; i<regras.size(); i++)
			{
				System.out.println(regras.get(i).validarJogo(sequencia));
			}
			
			int c= 0;
			int[] jp = new int[6];
			do {
				jp = gerarSequenciaPremiada(jp.length);
				c++;
			}while(!verificarSequenciaJaSorteada(jp));
			
			System.out.println(Arrays.toString(jp) + " - " + c);
			Util.desenharJogo(jp, 6, 10);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		
	}

	/**
	 * Responsavel por gerar uma sequencia de números aleatórios que
	 * podem ser premiados pelo jogo
	 * 
	 * @return Retorna uma sequencia de numeros para jogar que nunca foram sorteados
	 */
	public static int[] gerarSequenciaPremiada(int tamanhoSequencia)
	{	
		int[] sequencia = new int[tamanhoSequencia];
		double dP = Util.calcularDesvioPadrao(jogosSorteados, numerosPossiveis, estNumSort.getEstNumSort());
		
		for(int i=0; i<sequencia.length; i++)
		{
			sequencia[i] = Util.gerarNumeroAleatorio(numerosPossiveis ,estNumSort.getEstNumSort(), dP);
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
		
//		int[][] combinacoes = gerarCombinacoesSequencia(sequencia);
		
		for(int i=0; i<regras.size(); i++)
		{
			if(!regras.get(i).validarJogo(sequencia))
			{
				sequencia = gerarSequenciaPremiada(tamanhoSequencia);
				i = -1;
			}
		}
//		while(verificarSequenciaJaSorteada(sequencia))
//		{
//			sequencia = gerarSequenciaPremiada();
//		}
		
		return sequencia;
	}	
	
	public static List<int[]> gerarCombinacoesSequencia(int[] sequencia) {
		int MAX = sequencia.length;
		int combinacoes = Util.calcularFatorial(MAX) / (0x2D0 * Util.calcularFatorial(MAX - 0x6));
		
		List<int[]> listaCombinacoes = new ArrayList<int[]>();
		
		for(int i=0; i<combinacoes; i++) {
			for(int a=0; a<MAX; a++) {
				for(int b=a+1; b<MAX; b++) {
					for(int c=b+1; c<MAX; c++) {
						for(int d=c+1; d<MAX; d++) {
							for(int e=d+1; e<MAX; e++) {
								for(int f=e+1; f<MAX; f++) {
									 listaCombinacoes.add(new int[]{sequencia[a], sequencia[b], sequencia[c], sequencia[d], sequencia[e], sequencia[f]});
								}
							}
						}
					}
				}
			}
		} 
		
		return listaCombinacoes;
	}
	

	public static List<Sorteio> listar() throws Exception {
		SorteioService sorteioService = new SorteioService();
		
		List<Sorteio> listaSorteios = sorteioService.listar();

		return listaSorteios;
	}
	
	public static List<int[]> obterJogosDosSorteios(List<Sorteio> listaSorteios) {
		List<int[]> jogos = new ArrayList<int[]>();
		
		for(int i=0; i<listaSorteios.size(); i++) {
			jogos.add(listaSorteios.get(i).getDezenas());
		}
		
		return jogos;
	}
	

	
	/**
	 * Verifica se uma sequencia ja foi sorteada alguma vez.
	 * @param sequencia
	 * @return Retorna true se a sequencia ja foi sorteada, false se � uma sequencia possivelmente premiada
	 */
	public static boolean verificarSequenciaJaSorteada(int[] sequencia)
	{
		int[] jogoSorteado = new int[sequencia.length];
		int contIguais;
		
		//TODO Retirar isso
		int[] ultimoSorteio = {1, 7, 11, 32, 51, 59};
		
		jogosSorteados.clear();
		jogosSorteados.add(ultimoSorteio);
		
		for(int i=0; i<jogosSorteados.size(); i++)
		{
			jogoSorteado = jogosSorteados.get(i);
			
			contIguais = 0x0;
			
			for(int j=0; j<sequencia.length; j++)
			{
				if(jogoSorteado[j] == sequencia[j])
				{
					contIguais++;
				}
			}//TODO Alterar para == 6
			if(contIguais >= TipoJogo.QUADRA.getQtdDezenas())
			{
				System.out.println("Concurso - " + (i+1));
				System.out.println("Dezenas iguais - " + (contIguais));
				return true;
			}
		}
		return false;
	}
}
