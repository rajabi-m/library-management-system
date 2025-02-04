package org.example.model;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>{
    private Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node newNode = new Node(data);
        Node current = this.head;

        if (current == null) {
            this.head = newNode;
            return;
        }

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public boolean remove(T data) {
        Node current = this.head;

        if (current == null) {
            return false;
        }

        if (current.data.equals(data)) {
            this.head = current.next;
            return true;
        }

        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    public void sort(Comparator<T> comparator){
        Node firstBefore = null;
        Node first = this.head;
        Node secondBefore = null;
        Node second = null;

        if (this.head == null) {
            return;
        }

        // Bubble sort
        while (first != null) {
            second = first.next;
            secondBefore = first;
            while (second != null) {
                if (comparator.compare(first.data, second.data) > 0) {
                    swapNodes(first, firstBefore, second, secondBefore);
                    Node temp = first;
                    first = second;
                    second = temp;
                }
                secondBefore = second;
                second = second.next;
            }
            firstBefore = first;
            first = first.next;
        }
    }

    private void swapNodes(Node first, Node firstBefore, Node second, Node secondBefore) {
        if (first != this.head) {
            firstBefore.next = second;
        }
        else {
            this.head = second;
        }

        if (first.next != second){
            secondBefore.next = first;
            Node tmp = first.next;
            first.next = second.next;
            second.next = tmp;
        }
        else {
            first.next = second.next;
            second.next = first;
        }
    }

    public boolean contains(T value){
        Node current = this.head;
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public T get(int index){
        Node result = this.head;
        while (index > 0 && result != null){
            result = result.next;
            index --;
        }

        if (result == null){
            return null;
        }

        return result.data;
    }

    public T getHead() {
        if (this.head == null){
            return null;
        }
        return head.data;
    }

    public int size() {
        int size = 0;
        Node current = this.head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    private class Node{
        private final T data;
        private Node next;

        public Node(T data){
            this.data = data;
            this.next = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (T item : this) {
            output.append(item).append("\n");
        }
        return output.toString();
    }
}
