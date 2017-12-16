package br.com.apolo.mc.melo.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.apolo.mc.melo.controllers.JogosSucessoresDezenaController;
import br.com.apolo.mc.melo.entidades.Sorteio;

@ViewScoped
@ManagedBean(name = "jogosSucessoresDezenaView")
public class JogosSucessoresDezenaView {

	public List<Sorteio> sorteiosSucessores = new ArrayList<Sorteio>();

	private JogosSucessoresDezenaController jogosSucessoresDezenaController = new JogosSucessoresDezenaController();

	@PostConstruct
	private void init() {
		try {
			buscarJogosSucessoresDezena();

			if (sorteiosSucessores == null) {
				sorteiosSucessores = new ArrayList<Sorteio>();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", e.getMessage()));
		}
	}

	private void buscarJogosSucessoresDezena() throws Exception {
		// TODO Receber dezena pela tela
		int dezena = 15;

		sorteiosSucessores = jogosSucessoresDezenaController.buscarSorteiosSucessores(dezena);

		if (sorteiosSucessores != null) {

			for (int i = 0; i < sorteiosSucessores.size(); i++) {

				Arrays.sort(sorteiosSucessores.get(i).getDezenas());
			}
		}
	}

	public List<Sorteio> getSorteiosSucessores() {
		return sorteiosSucessores;
	}

	public void setSorteiosSucessores(List<Sorteio> sorteiosSucessores) {
		this.sorteiosSucessores = sorteiosSucessores;
	}

}
