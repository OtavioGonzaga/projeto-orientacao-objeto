package br.edu.utf.project.service;

public interface HashService {

    /**
     * Gera um hash seguro para a string fornecida.
     * @param string texto a ser criptografado
     * @return hash gerado
     */
    String cypherString(String string);

    /**
     * Compara uma string com seu hash.
     * @param string texto original
     * @param hash hash gerado previamente
     * @return true se corresponder
     */
    boolean compareString(String string, String hash);
}
