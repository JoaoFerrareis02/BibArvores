/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

/**
 *
 * @author victoriocarvalho
 */
public class No<T> {
    
    private T valor;
    private No<T> filhoDireita;
    private No<T> filhoEsquerda;

    
    public No(T valor){
        this.valor = valor;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
    }
    
    /**
     * @return the valor
     */
    public T getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * @return the filhoDireita
     */
    public No<T> getFilhoDireita() {
        return filhoDireita;
    }

    /**
     * @param filhoDireita the filhoDireita to set
     */
    public void setFilhoDireita(No<T> filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    /**
     * @return the filhoEsquerda
     */
    public No<T> getFilhoEsquerda() {
        return filhoEsquerda;
    }

    /**
     * @param filhoEsquerda the filhoEsquerda to set
     */
    public void setFilhoEsquerda(No<T> filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public int obterAltura(){
        // Retorna a altura do nó chamado
        return obterAltura(this);
    }

    private int obterAltura(No<T> r) {
        //Caso o nó for nulo, retorna -1
        if (r == null) {
            return -1;
        }
        else {
            int hd = obterAltura(r.getFilhoDireita()); //Obtem a altura do  nó filho a direita
            int he = obterAltura(r.getFilhoEsquerda()); //Obtem a altura do  nó filho a esquera
            return Math.max(hd,he) + 1; //Retorne o maior entre as alturas + 1
        }
    }

    public int fatorBalanceamento(){
        //Retorna a subtração entre as alturas do  nó filho a direita com o nó filho a esquerda.
        return obterAltura(this.filhoDireita) - obterAltura(this.filhoEsquerda); 
    }
    
}
