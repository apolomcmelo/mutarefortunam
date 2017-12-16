package br.com.apolo.mc.melo.controllers;

import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.services.SorteioService;
import br.com.apolo.mc.melo.util.Util;

public class ImportarSorteiosController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Constantes de posicionamento da tabela do arquivo htm
	private final int INICIO_DADOS = 1;
	private final int CONCURSO = 0;
	private final int DATA_SORTEIO = 1;
	private final int PRIMEIRA_DEZENA = 2;
	private final int ULTIMA_DEZENA = 7;
	
	private final String SEPARADOR_DEZENA = ";";
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private List<Sorteio> listaSorteios = new ArrayList<Sorteio>();
	
	private SorteioService sorteioService = new SorteioService();

	/**
	 * Importa os sorteios de jogos já realizados para a base de dados
	 * @param arquivoSorteios
	 * @param sobrescreverSorteios
	 * @throws Exception
	 */
	public void importarSorteios(InputStream arquivoSorteios, boolean sobrescreverSorteios) throws Exception {
		StringBuilder sb = new StringBuilder();
//		List<Sorteio> ultimoSorteio = sorteioService.buscarUltimoSorteio();
		
		Document doc = Jsoup.parse(arquivoSorteios, "UTF-8", "");

		// Varre o arquivo HTML em busca das linhas da tabela, onde contém os
		// dados dos jogos
		Elements concursos = doc.getElementsByTag("tr");

		// Obtém os dados dentro de cada célula
		for (int i = INICIO_DADOS; i < concursos.size(); i++) {
			Element e = concursos.get(i);

			if (!Util.isNumero(e.child(CONCURSO).text())) {
				continue;
			}

			Sorteio s = new Sorteio();

			s.setConcurso(Integer.parseInt(e.child(CONCURSO).text().trim()));
			
			//TODO Validar a parte de deletar os dados antigos caso seja para sobreescrever
//			if(!sobrescreverSorteios) {
//				if(s.getConcurso() <= ultimoSorteio.get(0).getConcurso()) {
//					continue;
//				}
//			}
				
			s.setData(df.parse(e.child(DATA_SORTEIO).text()));

			// Obtém os números das dezenas sorteadas
			for (int j = PRIMEIRA_DEZENA; j <= ULTIMA_DEZENA; j++) {
				sb.append(Integer.parseInt(e.child(j).text().trim()));

				if (j < ULTIMA_DEZENA) {
					sb.append(SEPARADOR_DEZENA);
				}
			}

			s.setSorteio(sb.toString());
			listaSorteios.add(s);

			// Limpa o StringBuilder
			sb.setLength(0);
		}
		
		sorteioService.salvarTodos(listaSorteios);		
	}
}