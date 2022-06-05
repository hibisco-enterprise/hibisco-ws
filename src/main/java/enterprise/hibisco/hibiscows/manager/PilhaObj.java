package enterprise.hibisco.hibiscows.manager;

public class PilhaObj<T> {

    private T[] pilha;
    private int topo;

    public PilhaObj(int tamanho) {
        topo = -1;
        pilha = (T[]) new Object[tamanho];
    }

    public Boolean isEmpty() {
        return topo == -1;
    }

    public Boolean isFull() {
        return topo == pilha.length -1;
    }

    public void push(T info) {
        if (isFull()) {
            throw new IllegalStateException("Pilha cheia!");
        }
        else {
            pilha[++topo] = info;
        }
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo--];
    }

    public T peek() {
        return pilha[topo];
    }

}
