package br.com.apolo.mc.melo.views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.apolo.mc.melo.controllers.ImportarSorteiosController;


@ViewScoped
@ManagedBean(name="importarSorteiosView")
public class ImportarSorteiosView {
	private boolean sobrescreverSorteios;
	
	private ImportarSorteiosController importarSorteiosController = new ImportarSorteiosController();

	// Método de negócio
	/**
	 * Responsável por controlar o evento de upload de arquivo
	 * @param event
	 */
	public void importarSorteios(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		try{
			if(file != null) {
				importarSorteiosController.importarSorteios(file.getInputstream(), isSobrescreverSorteios());
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:", "Os sorteios foram gravados na base de dados com sucesso."));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "O arquivo está nulo."));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getLocalizedMessage()));
		}
	}

	// Getters e Setters
	public boolean isSobrescreverSorteios() {
		return sobrescreverSorteios;
	}

	public void setSobrescreverSorteios(boolean sobrescreverSorteios) {
		this.sobrescreverSorteios = sobrescreverSorteios;
	}
}