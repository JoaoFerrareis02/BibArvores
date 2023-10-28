package lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T>{

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }
    
    private No<T> rotacaoEsquerda(No<T> r) {
        No<T> f = r.getFilhoDireita();
        r.setFilhoDireita(f.getFilhoEsquerda());
        f.setFilhoEsquerda(r);
        return f;
    }
    
    private No<T> rotacaoDireita(No<T> r) {
        No<T> f = r.getFilhoEsquerda();
        r.setFilhoEsquerda(f.getFilhoDireita());
        f.setFilhoDireita(r);
        return f;
    }

    private No<T> rotacaoEsquerdaDireita(No<T> r) {
        r.setFilhoEsquerda(rotacaoEsquerda(r.getFilhoDireita()));
        return rotacaoDireita(r);
    }

    private No<T> rotacaoDireitaEsquerda(No<T> r) {
        r.setFilhoDireita(rotacaoDireita(r.getFilhoDireita()));
        return rotacaoEsquerda(r);
    }

    @Override
    protected No<T> adicionarRecursivo(No<T> raiz, No<T> novo) {
        raiz = super.adicionarRecursivo(raiz, novo);
        if (raiz.fatorBalanceamento() > 1) {
            if (raiz.getFilhoDireita().fatorBalanceamento() > 0) {
                raiz = this.rotacaoEsquerda(raiz);
            } else {
                raiz = this.rotacaoDireitaEsquerda(raiz);
            }
        } else if (raiz.fatorBalanceamento() < -1) {
            if (raiz.getFilhoEsquerda().fatorBalanceamento() < 0) {
                raiz = this.rotacaoDireita(raiz);
            } else {
                raiz = this.rotacaoEsquerdaDireita(raiz);
            }
        }
        return raiz;
    }

}
