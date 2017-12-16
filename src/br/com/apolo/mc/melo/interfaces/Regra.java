package br.com.apolo.mc.melo.interfaces;

/**
 * Interface para regras que validam a sequência de jogos
 * 
 * @since 2015-03-10
 * @author Apolo Mc Melo
 */
public interface Regra {
	String descricao = "";
	/**
	 * Verifica se a sequência de números do jogo é valida de acordo com a regra estipulada
	 * @param j - Números do jogo
	 * @return <code>true</code> - se sequência for válida
	 * 	   <br><code>false</code> - se sequência não for válida 
	 */
	boolean validarJogo(int[] j);
}
