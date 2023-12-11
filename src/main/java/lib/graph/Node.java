package lib.graph;

public class Node<T> {

    private T valor;
    private int id;

    public Node(T valor, int id) {
        this.valor = valor;
        this.id = id;
    }

    /**
     * @return the valor
     */
    public T getValue() {
        return this.valor;
    }

    /**
     * @return the valor
     */
    public int getId() {
        return this.id;
    }

   
}
