package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

public class RegraNumerosSeguidos implements Regra {
	private String descricao = TipoEstatisticaEnum.NUMEROS_SEGUIDOS.getDescricao();

	@Override
	public boolean validarJogo(int[] j) {
		int count = 0x0;
		
		for(int i=0; i<j.length-1; i++) {
			if(j[i] == j[i+1]-1) {
				count++;
			}
		}
		
		return count <= 0x1;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
