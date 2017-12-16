package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

/**
 * Define a a quantidade de números com dígitos dobrados em um mesmo jogo para que este seja válido.
 * <br>Entende-se por dígitos dobrados, números cuja dezena e unidade sejam iguais, tais como:
 * <br><i>11, 22, 33, 44,</i> etc
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public class RegraDigitosDobrados implements Regra {
	private String descricao = TipoEstatisticaEnum.DIGITOS_DOBRADOS.getDescricao();

	@Override
	public boolean validarJogo(int[] j) {
		int qtND = 0x0;
		
		for(int i=0; i<j.length; i++) {
			if(j[i] % 0xB == 0x0) {
				qtND++;
			}
		}
		
		//1 é a quantidade média de dígitos dobrados de acordo com os jogos anteriores
		return qtND <= 0x2;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
