package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

/**
 * Define a quantidade de dígitos em um mesmo jogo para que este seja válido
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public class RegraQuantidadeDigitos implements Regra {
	private String descricao = TipoEstatisticaEnum.QUANTIDADE_DIGITOS.getDescricao();

	@Override
	public boolean validarJogo(int[] j) {
		int qtDg = 0x0;
		
		for(int i=0; i<j.length; i++) {
			qtDg += (j[i] / 0xA) > 0x0 ? 0x2 : 0x1;
		}
		
		//10 é a quantidade de dígitos medios de acordo com os jogos anteriores
		return qtDg > 0xA;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}