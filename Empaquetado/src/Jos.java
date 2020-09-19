
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Jos {

	public static void main(String[] args) throws IOException {
                // LEEMOS EL ARCHIVO
		//FileReader arq = new FileReader("C:\\Users\\yunyor\\Documents\\NetBeansProjects\\Proyecto1_Minimisacion_AFD\\src\\leerarchivo.txt");
                FileReader arq = new FileReader("C:\\Users\\jsgl8\\Documents\\umg_2020\\segundo_semestre\\automatas\\proyecto\\Proyecto1_Minimisacion_AFD\\src\\leerarchivo.txt");
                //Almacenamos la rectura en el bufer
		BufferedReader lerArq = new BufferedReader(arq);

		int quantidadeEstados = 0;
		int tamanhoDoAlfabeto = 0;
                // indentificamos nuestra matris para nuestros conjuntos
		int[][][] tabelaAFNDLambda;
		int[][][] tabelaAFND;
		int[][][] tabelaAFD;
                // lee la primera linea y descarta el (
		String readLine = lerArq.readLine(); // Ler linha 1 e descartar '('
                //lee la segunda linea del conjunto de estados 
		readLine = lerArq.readLine(); // Ler linha 2// linha do conjunto de  a,b,c
		String[] removeLinhaBranca = readLine.split("}");// Separar conjunto
		char[] vetorChar = removeLinhaBranca[0].toCharArray();// convierte los estados en un vector

		for (int i = 0; i < vetorChar.length; i++)
			// contamos cuantos estados tiene  conjunto de estados y los indentificamos a traves de la q
			if (vetorChar[i] == 'q')
				quantidadeEstados++;

		int tamanhoMaxDosConjuntos = quantidadeEstados + 1;
		
		readLine = lerArq.readLine(); // linea tres para leer el alfabeto ingresado
		removeLinhaBranca = readLine.split(" ");// Separar conjunto
		vetorChar = removeLinhaBranca[0].toCharArray();// Convierte un vector entero en uno char

		for (int i = 0; i < vetorChar.length; i++)
			// vemos cuantos conjuntos tiene a traves de la , 
			if (vetorChar[i] == ',')

				tamanhoDoAlfabeto++;// a,b 

		int colunaLambda = tamanhoDoAlfabeto;

		int quantidadeDeColunasTabelaAFNDLambda = tamanhoDoAlfabeto + 2;
																		
		int colunaFechoLambda = colunaLambda + 1;
                    // intento de lambar
		tabelaAFNDLambda = new int[tamanhoMaxDosConjuntos][quantidadeDeColunasTabelaAFNDLambda][tamanhoMaxDosConjuntos];
                // intento de uso de lambar

		for (int i = 0; i < tamanhoMaxDosConjuntos; i++) {
			for (int j = 0; j < quantidadeDeColunasTabelaAFNDLambda; j++) {
				for (int l = 0; l < tamanhoMaxDosConjuntos; l++) {
					tabelaAFNDLambda[i][j][l] = 999;
				}

			}

		}

		readLine = lerArq.readLine(); // lee linea 4 y descarta
		readLine = lerArq.readLine(); // Lee lina 5 y descarta 
		removeLinhaBranca = readLine.split(" ");// Separar conjunto
		vetorChar = removeLinhaBranca[0].toCharArray();// convierte un strinc en un char 

		while (vetorChar[0] != '}') {// busca el vector la llave  y no para 
			int posConjunto = 0;// cuenta las posicones en donde sera insertado
			int i = 2; 
			String StringAux = String.valueOf(vetorChar[i]);
			i++;
			while (vetorChar[i] != ',') { // vector que cuenta las posiciones de las ,
				StringAux = StringAux + String.valueOf(vetorChar[i]);
				i++;
			}
			i++;
			int estadoLinha = Integer.parseInt(StringAux);

			int caracterColuna = (int) vetorChar[i];
													// ...

			if (caracterColuna == '.') {// intento de lambar
				
				caracterColuna = colunaLambda + 48;

			} else {
				caracterColuna = caracterColuna - 49;
											
			}
			i += 5;// Pular ")->{q"
			posConjunto = 0;

			do {

				i++;
				StringAux = String.valueOf(vetorChar[i]);
				i++;
				while (vetorChar[i] != ',' && vetorChar[i] != '}') {// concatena
										
					StringAux = StringAux + String.valueOf(vetorChar[i]);
					i++;
				}
				if (i < vetorChar.length - 1)
					i++;

				tabelaAFNDLambda[estadoLinha][Character
						.getNumericValue(caracterColuna)][posConjunto] = Integer
    						.parseInt(StringAux);
				posConjunto++;

			} while (vetorChar[i] == 'q');

			readLine = lerArq.readLine();
			removeLinhaBranca = readLine.split(" ");// Separa lo conjuntos
			vetorChar = removeLinhaBranca[0].toCharArray();// convierte en string un vector char
														

		}

		for (int estado = 0; estado < quantidadeEstados; estado++)
			fecholambdaRecursiva(tabelaAFNDLambda, colunaLambda,
					colunaFechoLambda, estado, estado);

		tabelaAFND = new int[tamanhoMaxDosConjuntos][tamanhoDoAlfabeto][tamanhoMaxDosConjuntos]; //tabla de AFND
																								
		for (int i = 0; i < tamanhoMaxDosConjuntos; i++) {//LLENA LA TABLA
			for (int j = 0; j < tamanhoDoAlfabeto; j++) {
				for (int l = 0; l < tamanhoMaxDosConjuntos; l++) {
					tabelaAFND[i][j][l] = 999;
				}
			}
		}

		for (int i = 0; i < quantidadeEstados; i++) {
			System.out.println();
			for (int j = 0; j < quantidadeDeColunasTabelaAFNDLambda; j++) {
				for (int l = 0; l < quantidadeEstados; l++) {
					if (tabelaAFNDLambda[i][j][l] == 999) {
						System.out.print("                  ");
					} else {
						System.out.print(" q" + tabelaAFNDLambda[i][j][l]);
					}
				}
			}
		}

		for (int linha = 0; linha < quantidadeEstados; linha++) {
			for (int coluna = 0; coluna < tamanhoDoAlfabeto; coluna++) {
				int elementoDoConjunto = 0;
				while (tabelaAFNDLambda[linha][coluna][elementoDoConjunto] != 999) {// CICLO PARA LOS ELEMENTOS DEL CONJUNTOS
					if (verificarExistenciaDoElementoNoCojunto(tabelaAFND,
							linha, coluna,
							tabelaAFNDLambda[linha][coluna][elementoDoConjunto]) == false)
						inserirOrdenado(
								tabelaAFND,
								linha,
								coluna,
								tabelaAFNDLambda[linha][coluna][elementoDoConjunto]);
					int elementoDoFechoLambda = 0;

					while (tabelaAFNDLambda[tabelaAFNDLambda[linha][coluna][elementoDoConjunto] /* Linea del element estado insertado*/][colunaFechoLambda][elementoDoFechoLambda] != 999) {// Até o utimo elento valido
																																					
						if (verificarExistenciaDoElementoNoCojunto(
								tabelaAFND,
								linha,
								coluna,
								tabelaAFNDLambda[tabelaAFNDLambda[linha][coluna][elementoDoConjunto]][colunaFechoLambda][elementoDoFechoLambda]) == false)
							inserirOrdenado(
									tabelaAFND,
									linha,
									coluna,
									tabelaAFNDLambda[tabelaAFNDLambda[linha][coluna][elementoDoConjunto]][colunaFechoLambda][elementoDoFechoLambda]);

						elementoDoFechoLambda++;
					}

					elementoDoConjunto++;

				}

				int elementoDoConjuntoFechoLambda = 0;
				while (tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda] != 999) {
																										
                                        // intento lambar
					elementoDoConjunto = 0;
					while (tabelaAFNDLambda[ /*elemento lambar*/tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto] != 999) {																																									// do																																											// conjunto
						if (verificarExistenciaDoElementoNoCojunto(
								tabelaAFND,
								linha,
								coluna,
								tabelaAFNDLambda[tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto]) == false)
							inserirOrdenado(
									tabelaAFND,
									linha,
									coluna,
									tabelaAFNDLambda[tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto]);
						int elementoDoFechoLambda = 0;

						while (tabelaAFNDLambda[tabelaAFNDLambda[tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto]][colunaFechoLambda][elementoDoFechoLambda] != 999) {// Até
	if (verificarExistenciaDoElementoNoCojunto(
									tabelaAFND,
									linha,
									coluna,
									tabelaAFNDLambda[tabelaAFNDLambda[tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto]][colunaFechoLambda][elementoDoFechoLambda]) == false)
								inserirOrdenado(
										tabelaAFND,
										linha,
										coluna,
										tabelaAFNDLambda[tabelaAFNDLambda[tabelaAFNDLambda[linha][colunaFechoLambda][elementoDoConjuntoFechoLambda]][coluna][elementoDoConjunto]][colunaFechoLambda][elementoDoFechoLambda]);

							elementoDoFechoLambda++;
						}

						elementoDoConjunto++;

					}

					elementoDoConjuntoFechoLambda++;

				}
			}
		}

		for (int i = 0; i < quantidadeEstados; i++) {
			System.out.println();
			for (int j = 0; j < tamanhoDoAlfabeto; j++) {
				for (int l = 0; l < quantidadeEstados; l++) {
					if (tabelaAFND[i][j][l] == 999) {
						System.out.print("                              ");
					} else {
						System.out.print(" q" + tabelaAFND[i][j][l]);
					}
				}
			}
		}
		int maximoDeEstadoAFD = 30;

		tabelaAFD = new int[maximoDeEstadoAFD][tamanhoDoAlfabeto + 1][tamanhoMaxDosConjuntos];
																							

		for (int i = 0; i < maximoDeEstadoAFD; i++) {
			for (int j = 0; j < tamanhoDoAlfabeto + 1; j++) {
				for (int l = 0; l < tamanhoMaxDosConjuntos; l++) {
					tabelaAFD[i][j][l] = 999;
				}
			}
		}

		int linha = 0;

		tabelaAFD[0][0] = tabelaAFNDLambda[0][colunaFechoLambda]; // define el primero el afd
																

		while (tabelaAFD[linha][0][0] != 999) {

			for (int coluna = 1; coluna < tamanhoDoAlfabeto + 1; coluna++) {
																		

				int elementoDoNovoEstado = 0;

				while (tabelaAFD[linha][0][elementoDoNovoEstado] != 999) {
																		
					

					int elementoDaTabelaAFND = 0;

					while (tabelaAFND[tabelaAFD[linha][0][elementoDoNovoEstado]][coluna - 1][elementoDaTabelaAFND] != 999) {
																																										// o
																																										// ultimo
																																										// elemento
																																										// valido
						if (verificarExistenciaDoElementoNoCojunto(
								tabelaAFD,
								linha,
								coluna,
								tabelaAFND[tabelaAFD[linha][0][elementoDoNovoEstado]][coluna - 1][elementoDaTabelaAFND]) == false)
							inserirOrdenado(
									tabelaAFD,
									linha,
									coluna,
									tabelaAFND[tabelaAFD[linha][0][elementoDoNovoEstado]][coluna - 1][elementoDaTabelaAFND]);

						elementoDaTabelaAFND++;
					}

					elementoDoNovoEstado++;

				}

				Boolean conjutoInserido = false;

				for (int i = 0; i < maximoDeEstadoAFD; i++) {
					if (comparaDoisConjunto(tabelaAFD[i][0],
							tabelaAFD[linha][coluna])) {
						conjutoInserido = true;
					}
				}

				if (conjutoInserido == false) {
					int fimColuna = 0;

					while (tabelaAFD[fimColuna][0][0] != 999) {
						fimColuna++;
					}
					tabelaAFD[fimColuna][0] = tabelaAFD[linha][coluna];

				}

			}
			linha++;

		}

		System.out.println();

		System.out.println(" TABLA AFD ");

		for (int i = 0; i < maximoDeEstadoAFD; i++) {
			System.out.println();
			for (int j = 0; j < tamanhoDoAlfabeto + 1; j++) {
				for (int l = 0; l < quantidadeEstados; l++) {
					if (tabelaAFD[i][j][l] == 999) {
						System.out.print("   ");
					} else {
						System.out.print(" q" + tabelaAFD[i][j][l]);
					}
				}
			}
		}

		// escribe los resultados en un archivo de texto
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:\\Users\\jsgl8\\Documents\\umg_2020\\segundo_semestre\\automatas\\proyecto\\Proyecto1_Minimisacion_AFD\\src\\salida1.txt"));

	
		String espaco = "   ";
		for (int i = 1; i < quantidadeEstados; i++)
			// Concatenar espaço em branco "q0         {....}"
			espaco += "   ";

		//buffWrite.write("TABLA AFND-LAMBDA");
		//buffWrite.append("\n");
		/*
		 * Savando tabela AFND LAMBDA no arquivo
		 */

		StringBuilder strBuilderCabecalho = new StringBuilder();// Forma
																// cabeçalho da
																// tabela ex:
																// "&            a              b             c         lamb"

		strBuilderCabecalho.append(" " + espaco); // estado \ alfabeto

		for (int i = 0; i < tamanhoDoAlfabeto; i++) {// Concatenar simbolos
														// primeira linha
														// (cabeçalho) da tabela
			char aux = (char) (i + 97);
			String string = aux + espaco;
			strBuilderCabecalho.append(string);
		}

		strBuilderCabecalho.append("L" + espaco);

		//buffWrite.write(strBuilderCabecalho.toString(), 0,
				//strBuilderCabecalho.length());
		//buffWrite.append("\n");

		for (int i = 0; i < quantidadeEstados; i++) {

			StringBuilder strBuilderLinha = new StringBuilder();
			strBuilderLinha.append("q" + i + espaco);

			for (int j = 0; j < quantidadeDeColunasTabelaAFNDLambda - 1; j++) {
																			
				for (int l = 0; l < quantidadeEstados; l++) {
																

					if (tabelaAFNDLambda[i][j][l] == 999) {

						if (l == 0) {// Primeiro elemento do conjunto for vazio
										// insera "0"(simbolo de conjuto vazio

							strBuilderLinha.append(0 + "   ");

						} else {

							strBuilderLinha.append("   ");
						}

					} else {

						if (l == 0)
							strBuilderLinha.append('{');

						
						if (tabelaAFNDLambda[i][j][l + 1] == 999) {
							strBuilderLinha.append("q"
									+ tabelaAFNDLambda[i][j][l] + "}");
						} else {
							strBuilderLinha.append("q"
									+ tabelaAFNDLambda[i][j][l] + ",");
						}

					}
				}
			}

			
		}

		buffWrite.append("\n");
		buffWrite.append("\n");
		buffWrite.write("TABLA AFN");
		buffWrite.append("\n");
                    buffWrite.write("estados             alfabeto");
		buffWrite.append("\n");
		

		StringBuilder strBuilderCabecalhoAFND = new StringBuilder();// Forma
																	// 
																	// 
																	// 
																	// 

		strBuilderCabecalhoAFND.append(" " + espaco);

		for (int i = 0; i < tamanhoDoAlfabeto; i++) {
														
			char aux = (char) (i + 97);
			String string = aux + espaco;
			strBuilderCabecalhoAFND.append(string);
		}

		buffWrite.write(strBuilderCabecalhoAFND.toString(), 0,
				strBuilderCabecalhoAFND.length());
												
           
		buffWrite.append("\n");
                  
		for (int i = 0; i < quantidadeEstados; i++) {

			StringBuilder strBuilderLinha = new StringBuilder();
			strBuilderLinha.append("q" + i + espaco);

			for (int j = 0; j < tamanhoDoAlfabeto; j++) {
															
				for (int l = 0; l < quantidadeEstados; l++) {
																

					if (tabelaAFND[i][j][l] == 999) {

						if (l == 0) {
							strBuilderLinha.append(0 + "   ");

						} else {

							strBuilderLinha.append("   ");
						}

					} else {

						if (l == 0)
							strBuilderLinha.append('{');

						
						if (tabelaAFND[i][j][l + 1] == 999) {
							strBuilderLinha.append("q" + tabelaAFND[i][j][l]
									+ "}");
						} else {
							strBuilderLinha.append("q" + tabelaAFND[i][j][l]
									+ ",");
						}

					}
				}
			}

			buffWrite.write(strBuilderLinha.toString(), 0,
					strBuilderLinha.length());
			buffWrite.append("\n");
		}

		buffWrite.append("\n");
		buffWrite.append("\n");
                buffWrite.write("NUEVA TRANSICIONES EN AFD");
                buffWrite.append("\n");
                 buffWrite.write("ESTADOS INACCESIBLES ELIMINADOS");
                buffWrite.append("\n");
		buffWrite.write("TABLA AFD");
                buffWrite.append("\n");
                        buffWrite.write("estados             alfabeto");
		buffWrite.append("\n");

		

		StringBuilder strBuilderCabecalhoAFD = new StringBuilder();
																	

		strBuilderCabecalhoAFD.append(" " + espaco);

		for (int i = 0; i < tamanhoDoAlfabeto; i++) {
														
			char aux = (char) (i + 97);
			String string = aux + espaco;
			strBuilderCabecalhoAFD.append(string);
		}

		buffWrite.write(strBuilderCabecalhoAFD.toString(), 0,
				strBuilderCabecalhoAFD.length());
													
		buffWrite.append("\n");

		for (int i = 0; i < maximoDeEstadoAFD; i++) {

			StringBuilder strBuilderLinha = new StringBuilder();

			for (int j = 0; j < tamanhoDoAlfabeto + 1; j++) {
																
				for (int l = 0; l < quantidadeEstados; l++) {
																

					if (tabelaAFD[i][j][l] == 999) {

						if (l == 0) {

							strBuilderLinha.append('-' + "   ");

						} else {

							strBuilderLinha.append("   ");
						}
					} else {
						if (l == 0)
							strBuilderLinha.append('<');

				
						if (tabelaAFD[i][j][l + 1] == 999) {
							strBuilderLinha.append("q" + tabelaAFD[i][j][l]
									+ ">");
						} else {
							strBuilderLinha.append("q" + tabelaAFD[i][j][l]
									+ ",");
						}

					}
				}
			}
			if (tabelaAFD[i][0][0] != 999) {
				buffWrite.write(strBuilderLinha.toString(), 0,
						strBuilderLinha.length());
				buffWrite.append("\n");
			}

		}

		buffWrite.close();

		
	}

	public static Boolean comparaDoisConjunto(int[] conjunto1, int[] conjunto2) {
		
		Boolean igual = true;
		int i = 0, TamanhoDoConjunto1 = 0, TamanhoDoConjunto2 = 0;

		while (conjunto1[i] != 999) {
			TamanhoDoConjunto1++;
			i++;
		}

		i = 0;
		while (conjunto2[i] != 999) {
			TamanhoDoConjunto2++;
			i++;
		}

		if (TamanhoDoConjunto1 == TamanhoDoConjunto2) {
			i = 0;
			while (i < TamanhoDoConjunto1) {
				if (conjunto1[i] != conjunto2[i])
					igual = false;

				i++;
			}
		} else {
			igual = false;
		}

		return igual;

	}

	public static Boolean verificarExistenciaDoElementoNoCojunto(
			int[][][] tabelaEntrada, int estadoDonoLinha, int colunaDoConjunto,
			int elemento) {

		int[][][] tabela = tabelaEntrada;
		Boolean achou = false;

		for (int i = 0; i < tabela[estadoDonoLinha][colunaDoConjunto].length; i++)
			if (tabela[estadoDonoLinha][colunaDoConjunto][i] == elemento)
				achou = true;
		return achou;
	}

	public static void inserirOrdenado(int[][][] tabelaEntrada,
			int estadoDonoDaLinha, int coluna, int elemento) {

		int[][][] tabela = tabelaEntrada;
		int posDeslocar = 0, j = 0;
		Boolean inserir = false;

		while (inserir != true) {
			if (tabela[estadoDonoDaLinha][coluna][j] > elemento) {
				posDeslocar = j;
				inserir = true;
			}
			j++;
		}

		if (posDeslocar != tabela[0][0].length - 1)
													
			for (int i = tabela[0][0].length - 1; i > posDeslocar; i--)
				tabela[estadoDonoDaLinha][coluna][i] = tabela[estadoDonoDaLinha][coluna][i - 1];

		tabela[estadoDonoDaLinha][coluna][posDeslocar] = elemento;

	}

	public static void fecholambdaRecursiva(int[][][] tabelaFNDLambda,
			int colunaLambda, int colunaFechoLambda, int estadoAtual,
			int estadoFechoLambda) {
		

		int[][][] tabela = tabelaFNDLambda;

		if (tabela[estadoAtual][colunaLambda][0] == 999) {
															
			if (verificarExistenciaDoElementoNoCojunto(tabela,
					estadoFechoLambda, colunaFechoLambda, estadoAtual) == false)
																					
				inserirOrdenado(tabela, estadoFechoLambda, colunaFechoLambda,
						estadoAtual);
		} else {
			int i = 0;

			while (tabela[estadoAtual][colunaLambda][i] != 999) {
																	
				int estado = tabela[estadoAtual][colunaLambda][i];

				fecholambdaRecursiva(tabela, colunaLambda, colunaFechoLambda,
						estado, estadoFechoLambda);
				i++;
			}
			if (verificarExistenciaDoElementoNoCojunto(tabela,
					estadoFechoLambda, colunaFechoLambda, estadoAtual) == false)
																			
				inserirOrdenado(tabela, estadoFechoLambda, colunaFechoLambda,
						estadoAtual);
		}

	}

}