package br.com.apolo.mc.melo.testes;

public class Teste {

	public static void main(String[] args) {
		int[] SORTEIO_1761 = {9, 10, 36, 50, 53, 55, 1761};
		int[] SORTEIO_1762 = {26, 32, 42, 45, 55, 59, 1762};
		int[] SORTEIO_1763 = {9, 12, 15, 21, 31, 36, 1763};
		int[] SORTEIO_1764 = {6, 7, 29, 39, 41, 55, 1764};
		int[] SORTEIO_1765 = {1, 6, 28, 37, 56, 58, 1765};
		int[] SORTEIO_1766 = {22, 23, 41, 46, 53, 60, 1766};
		int[] SORTEIO_1767 = {16, 26, 35, 39, 44, 45, 1767};
		int[] SORTEIO_1768 = {5, 7, 11, 34, 35, 50, 1768};
		int[] SORTEIO_1769 = {32, 37, 44, 47, 54, 60, 1769};
		int[] SORTEIO_1770 = {11, 26, 27, 30, 34, 41, 1770};
		int[] SORTEIO_1771 = {2, 20, 27, 28, 32, 38, 1771};
		int[] SORTEIO_1772 = {12, 19, 27, 39, 41, 45, 1772};
		int[] SORTEIO_1773 = {15, 30, 39, 41, 45, 59, 1773};
		int[] SORTEIO_1774 = {1, 12, 20, 30, 52, 60, 1774};
		int[] SORTEIO_1775 = {3, 11, 16, 51, 56, 57, 1775};
		int[] SORTEIO_1776 = {4, 19, 23, 37, 52, 56, 1776};
		int[] SORTEIO_1777 = {3, 19, 23, 26, 37, 51, 1777};
		
		encontraPosicaoJogo(SORTEIO_1761);
		encontraPosicaoJogo(SORTEIO_1762);
		encontraPosicaoJogo(SORTEIO_1763);
		encontraPosicaoJogo(SORTEIO_1764);
		encontraPosicaoJogo(SORTEIO_1765);
		encontraPosicaoJogo(SORTEIO_1766);
		encontraPosicaoJogo(SORTEIO_1767);
		encontraPosicaoJogo(SORTEIO_1768);
		encontraPosicaoJogo(SORTEIO_1769);
		encontraPosicaoJogo(SORTEIO_1770);
		encontraPosicaoJogo(SORTEIO_1771);
		encontraPosicaoJogo(SORTEIO_1772);
		encontraPosicaoJogo(SORTEIO_1773);
		encontraPosicaoJogo(SORTEIO_1774);
		encontraPosicaoJogo(SORTEIO_1775);
		encontraPosicaoJogo(SORTEIO_1776);
		encontraPosicaoJogo(SORTEIO_1777);

	}

	public static void encontraPosicaoJogo(int[] jogo) {
		int MAX = 60;
		int pos = 0;

		for (int a = 1; a <= MAX; a++) {
			for (int b = a + 1; b <= MAX; b++) {
				for (int c = b + 1; c <= MAX; c++) {
					for (int d = c + 1; d <= MAX; d++) {
						for (int e = d + 1; e <= MAX; e++) {
							for (int f = e + 1; f <= MAX; f++) {
								pos++;
								if(a == jogo[0] && b == jogo[1] && c == jogo[2] && d == jogo[3] && e == jogo[4] && f == jogo[5]){
									System.out.println("Posição do jogo " + jogo[6] + ": " + pos);
								}
							}
						}
					}
				}
			}
		}
	}
}
