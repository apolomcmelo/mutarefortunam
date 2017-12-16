package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

public class RegraParesImpares implements Regra {
	private String descricao = TipoEstatisticaEnum.PARES_IMPARES.getDescricao();

	@Override
	public boolean validarJogo(int[] sequencia) {
		int pares = 0;
		
		for(int i=0; i<sequencia.length; i++)
		{
			if(sequencia[i] % 2 == 0)
			{
				pares++;
			}
		}
		return (pares >= 1) && (pares <= 5);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}