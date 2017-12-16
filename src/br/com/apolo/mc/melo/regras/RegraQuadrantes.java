package br.com.apolo.mc.melo.regras;

import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.interfaces.Regra;

public class RegraQuadrantes implements Regra {
	private String descricao = TipoEstatisticaEnum.QUADRANTES.getDescricao();

	@Override
	public boolean validarJogo(int[] sequencia) {
		int[] quadrantes = new int[4];
		int cont = 4;
		
		for(int i=0; i<6; i++)
		{
			//Primeiros quadrantes
			if(sequencia[i] <= 30)
			{
				//1º
				if((sequencia[i] % 10 >= 1) && (sequencia[i] % 10 <=5))
				{
					quadrantes[0]++;
				}
				else
				{
					quadrantes[1]++;
				}
			}
			else //Últimos quadrantes
			{
				//3º
				if((sequencia[i] % 10 >= 1) && (sequencia[i] % 10 <=5))
				{
					quadrantes[2]++;
				}
				else
				{
					quadrantes[3]++;
				}
			}
		}
		
		for(int k=0; k<4; k++)
		{
			if(quadrantes[k] == 0)
			{
				cont--;
			}
		}
		
		return cont >= 3;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
