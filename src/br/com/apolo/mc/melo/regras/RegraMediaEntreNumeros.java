package br.com.apolo.mc.melo.regras;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

/**
 * Define a distância média entre números de um mesmo jogo para que este seja válido
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public class RegraMediaEntreNumeros implements Regra {
	private String descricao = TipoEstatisticaEnum.MEDIA_ENTRE_NUMEROS.getDescricao();

	@Override
	public boolean validarJogo(int[] j) {
		double m = new BigDecimal((j[j.length - 1] - j[0]) / (double)j.length).setScale(1, RoundingMode.HALF_UP).doubleValue();
			
		//6.3 e 9.5 são os limites da distância média entre números de acordo com os jogos anteriores
		return (m >= 5.8) && (m <= 8.5);	
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}