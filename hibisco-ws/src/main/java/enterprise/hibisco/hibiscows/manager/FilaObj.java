package enterprise.hibisco.hibiscows.manager;

public class FilaObj <T> {
    private int tamanho;
    private T[] fila;

    public FilaObj(int tamanho) {
        this.tamanho = 0;
        fila = (T[]) new Object[tamanho];
    }
    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == fila.length;
    }

    public void insert(T info) {
        if (isFull()) {
            throw new IllegalStateException("Fila cheia!");
        }
        else {
            fila[tamanho++] = info;
        }
    }

    public T peek() {
        return fila[0];
    }

    public T poll() {
        T first = peek();

        if (!isEmpty()) {
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }
            fila[tamanho - 1] = null;
            tamanho--;
        }
        return first;
    }

    public int getTamanho() {
        return tamanho;
    }
}
