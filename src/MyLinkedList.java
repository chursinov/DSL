import java.util.Collection;
import java.util.Iterator;

public class MyLinkedList<T>{
    /**
     * Узел внутреннего класса
     * @author newapps
     * @param <T> означает универсальный
     */
    private  class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value=value;
            this.next=null;
        }
    }
	 /* Определить головной узел связанного списка */
    private Node<T> head=null;
    /**
     * Количество узлов в связанном списке
     */
    public int size() {
        Node<T> p;
        int size=0;
        for(p=head;p!=null;p=p.next){
            size++;
        }
        return size;
    }
    /**
     * Определить, равно ли число узлов в связанном списке нулю
     */
    public boolean isEmpty() {
        if(size()==0){
            return true;
        }
        return false;
    }
    /**
     * Найти, если связанный список содержит узел с указанным значением узла
     * @param o значение узла
     * @return содержит
     */
    public boolean contains(T o) {
        if(isEmpty()){
            return false;
        }
        Node<T> p;
        for(p=head;p!=null;p=p.next){
            if(p.value.equals(o)){
                return true;
            }
        }
        return false;
    }
    /**
     * Метод итератора
     * @return
     */
    public Iterator iterator() {
        return new Itor();
    }
    /**
     * Класс реализации итератора
     * @author newapps
     * 2009-12-9
     */
    private class Itor implements Iterator{
		 /* местоположение */
        private int index=0;
        //	private int cursor=0;
        public boolean hasNext() {

            if(index<size()){
                return true;
            }
            return false;
        }

        public T next(){
            T o = get(index++);
            return o;
        }

        public void remove() {
            MyLinkedList.this.remove(size()-1);
        }

    }
    /**
     * Возвращает все элементы в коллекции в виде массива Object [].
     * @return
     */
    public Object[] toArray() {
        if(isEmpty()){
            return null;
        }
        int length=size(),i=0;
        Object[] a=new Object[length];
        Node<T> p;
        for(p=head;p!=null;p=p.next){
            a[i]=p.value;
            i++;
        }
        return a;
    }

    /**
     * Поскольку интерфейс списка упорядочен
     * Так что добавьте количество узлов в связанном списке
     * Должен быть добавлен после последней позиции узла
     * @param o значение узла
     * @return успешно добавлен
     */
    public void add(T o){
        if(isEmpty()){
            head=new Node<T>(o);
        }else{
            Node<T> p=head;
            Node<T> node=new Node<T>(o);
            while(p.next!=null){
                p=p.next;
            }
            p.next=node;
            node.next=null;
        }
    }
    /**
     * Чтобы удалить значение узла в связанном списке
     * Сначала найдите узел
     * Последний удален
     * @param o
     * @return
     */
    public boolean remove(T o){
        Node<T> p=head,p1=null;
        boolean have=false;
        if(isEmpty()){
            return false;
        }
        while(p!=null){
            if(p.value.equals(o)){
                if(p1==null){
                    head=head.next;
                }else{
                    p1.next=p.next;
                }
                have=true;
            }
            p1=p;
            p=p.next;
        }
        return have;
    }
    /**
     * Найти, если все элементы в наборе также существуют в связанном списке
     * @param c
     * @return
     */
    public boolean containsAll(Collection c) {
        if(isEmpty()){
            return false;
        }
        if(c.size()==0){
            return false;
        }
        if(c==null||c.size()>size()){
            return false;
        }
        Iterator it=c.iterator();
        while(it.hasNext()){
            T o=(T)it.next();
            if(!contains(o)){
                return false;
            }
        }
        return true;
    }
    /**
     * Добавить все элементы коллекции в связанный список
     * @param c
     * @return
     */
    public boolean addAll(Collection c){
        if(c==null||c.size()==0){
            return false;
        }
        Iterator it=c.iterator();
        while(it.hasNext()){
            T o=(T)it.next();
            add(o);
        }
        return true;
    }
    /**
     * Добавить все элементы коллекции в связанный список из указанной позиции индекса
     * @param index
     * @param c
     * @return
     */
    public boolean addAll(int index, Collection c) {
        if(c==null||c.size()==0){
            return false;
        }
        if(isEmpty()){
            addAll(c);
            return true;
        }
        if(index<-1){
            return true;
        }else if(index>=size()){
            addAll(c);
        }else{
            int i=index;
            Iterator it=c.iterator();
            while(it.hasNext()){
                T o=(T)it.next();
                add(i,o);
                i++;
            }
        }
        // будет в коллекции
        return false;
    }
    /**
     * Удалить все элементы из содержащего списка в связанном списке
     * @param c
     * @return
     */
    public boolean removeAll(Collection c){
        if(c==null||c.size()==0){
            return false;
        }
        if(isEmpty()){
            return false;
        }
        Node<T> p;
        Iterator it=c.iterator();
        while(it.hasNext()){
            T o=(T)it.next();
            remove(o);
        }

        return true;
    }
    /**
     * Только остальные элементы в наборе удаляются в связанном списке
     * @param c
     * @return
     */
    public boolean retainAll(Collection c) {
        if(isEmpty()){
            return false;
        }
        if(c==null||c.size()==0){
            return false;
        }

        Node<T> p;
        for(p=head;p!=null;p=p.next){
            T m=p.value;
            boolean have=false;
            Iterator it=c.iterator();
            while(it.hasNext()){
                T o=(T)it.next();
                if(m.equals(o)){
                    have=true;
                }
            }
            if(!have){
                this.remove(m);
            }
        }
        return true;
    }

    public void clear() {
        head=null;
    }
    /**
     * Найти значение элемента в связанном списке согласно указанному индексу
     * @param index
     * @return
     */
    public T get(int index) {
        int i=-1;
        if(isEmpty()){
            return null;
        }
        if(index<0||index>size()){
            return null;
        }
        Node<T> p=head;
        while(p!=null){
            i++;
            if(i==index){
                return p.value;
            }
            p=p.next;
        }
        return null;
    }
    /**
     * Заменить элемент в указанной позиции в связанном списке
     * И вернуть элемент перед заменой
     * @param index
     * @param element
     * @return
     */
    public T set(int index, T element) {
        int i=-1;
        if(isEmpty()){
            add(element);
            return null;
        }
        if(index<0||index>size()){
            return null;
        }
        Node<T> p=head;
        T o=null;
        while(p!=null){
            i++;
            if(i==index){
                o=p.value;
                p.value=element;
                return o;
            }
            p=p.next;
        }
        return null;
    }
    /**
     * Добавить элемент в указанную позицию в связанном списке
     * @param index
     * @param element
     */
    public void add(int index, T element) {
        int i=-1;
        if(isEmpty()){
            this.add(element);
            return;
        }
        if(index<0||index>size()){
            return;
        }
        Node<T> p=head,p1=null;
        while(p!=null){
            i++;
            if(i==index){
                Node<T> newNode=new Node<T>(element);
                if(p1==null){
                    newNode.next=head;
                    head=newNode;
                }else{
                    p1.next=newNode;
                    newNode.next=p;
                }
            }
            p1=p;
            p=p.next;
        }
    }
    /**
     * Удалить элемент в указанной позиции в связанном списке
     * @param index
     * @return
     */
    public T remove(int index) {
        if(isEmpty()){
            return null;
        }
        if(index<0||index>size()){
            return null;
        }
        Node<T> p=head,p1=null;
        int i=-1;
        while(p!=null){
            i++;
            if(i==index){
                if(p1==null){
                    head=head.next;
                }else{
                    p1.next=p.next;
                }
                return p.value;
            }
            p1=p;
            p=p.next;
        }
        return null;
    }
    /**
     * Возвращает индекс, содержащий указанный элемент в связанном списке, или -1, если не найден;
     * @param o
     * @return
     */
    public int indexOf(T o) {
        int i=-1;
        if(isEmpty()){
            return -1;
        }
        Node<T> p=head;
        while(p!=null){
            i++;
            if(p.value.equals(o)){
                return i;
            }
            p=p.next;
        }
        return -1;
    }
    /**
     * Найти последний индекс указанного элемента в связанном списке
     * -1, если не найден;
     * @param o
     * @return
     */
    public int lastIndexOf(T o) {
        if(isEmpty()){
            return -1;
        }
        Node<T> p=head;
        int i=-1,index=-1;
        while(p!=null){
            i++;
            if(p.value.equals(o)){
                index=i;
            }
            p=p.next;
        }
        return index;
    }
    /**
     * Как распечатать связанный список
     *
     */
    public void printLinkList(){
        Node<T> p;
        for(p=head;p!=null;p=p.next){
            System.out.print(p.value+"--->");
        }
        System.out.println();
    }
}