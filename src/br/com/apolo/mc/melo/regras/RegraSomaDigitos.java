package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;
import br.com.apolo.mc.melo.util.Util;

/**
 * Define o valor da soma dos dígitos de todos os números existentes em um mesmo jogo para que este seja válido
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public class RegraSomaDigitos implements Regra {
	private String descricao = TipoEstatisticaEnum.SOMA_DIGITOS.getDescricao();

	@Override
	public boolean validarJogo(int[] j) {
		int sD = 0x0;
		
		for(int i=0; i<j.length; i++) {
			sD += Util.somarDigitos(j[i]);
		}
		
		//39 e 44 são os limites médios da soma de dígitos de acordo com os jogos anteriores
		return (sD >= 40) && (sD <= 60);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}