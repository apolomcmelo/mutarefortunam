package br.com.apolo.mc.melo.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;
import br.com.apolo.mc.melo.regras.RegraDigitosDobrados;
import br.com.apolo.mc.melo.regras.RegraMediaEntreNumeros;
import br.com.apolo.mc.melo.regras.RegraNumerosSeguidos;
import br.com.apolo.mc.melo.regras.RegraQuadrantes;
import br.com.apolo.mc.melo.regras.RegraQuantidadeDigitos;
import br.com.apolo.mc.melo.regras.RegraSomaDigitos;

@FacesConverter("regraConverter")
public class RegraConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (TipoEstatisticaEnum.MEDIA_ENTRE_NUMEROS.name().equals(arg2)) {
			return new RegraMediaEntreNumeros();
			
		} else if (TipoEstatisticaEnum.DIGITOS_DOBRADOS.name().equals(arg2)) {
			return new RegraDigitosDobrados();
			
		} else if (TipoEstatisticaEnum.NUMEROS_SEGUIDOS.name().equals(arg2)) {
			return new RegraNumerosSeguidos();
		
		} else if (TipoEstatisticaEnum.QUADRANTES.name().equals(arg2)) {
			return new RegraQuadrantes();
			
		} else if (TipoEstatisticaEnum.QUANTIDADE_DIGITOS.name().equals(arg2)) {
			return new RegraQuantidadeDigitos();
			
		} else if (TipoEstatisticaEnum.SOMA_DIGITOS.name().equals(arg2)) {
			return new RegraSomaDigitos();
			
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			if(arg2 != null && arg2 instanceof Regra) {
				return ((Regra)arg2).getClass().getField("descricao").toString();
			}
		} catch (NoSuchFieldException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
		} catch (SecurityException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
		}
		
		return "";
	}
}
