

import java.util.ArrayList;

public class CustomPriorityQueue {
    private ArrayList<Ride> elements;

    public  CustomPriorityQueue() {
        this.elements = new ArrayList<>();
    }

    public void insert(Ride obj) {
        elements.add(obj);
        int current = elements.size() - 1;
        while (current > 0 && compare(elements.get(current), elements.get(parent(current))) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }
    public boolean isEmpty(){
        return elements.size()==0;
    }
    public Ride delete() {
        if(elements.size()==0){
            return null;
        }
        Ride root = elements.get(0);
        elements.set(0, elements.get(elements.size() - 1));
        elements.remove(elements.size() - 1);
        minHeapify(0);
        return root;
    }

    public void delete(Ride node) {
        int index = elements.indexOf(node);
        if (index == -1) {
            return;
        }
        int size = elements.size() - 1;
        if (index == size) {
            elements.remove(size);
            return;
        }
        swap(index, size);
        elements.remove(size);
        minHeapify(index);
    }

    private void minHeapify(int index) {
        int left = left(index);
        int right = right(index);
        int smallest = index;
        if (left < elements.size() && compare(elements.get(left), elements.get(smallest)) < 0) {
            smallest = left;
        }
        if (right < elements.size() && compare(elements.get(right), elements.get(smallest)) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            minHeapify(smallest);
        }
    }

    private int compare(Ride obj1, Ride obj2) {
        if (obj1.getRideCost() < obj2.getRideCost()) {
            return -1;
        } else if (obj1.getRideCost() > obj2.getRideCost()) {
            return 1;
        } else {
            if (obj1.getTripDuration() < obj2.getTripDuration()) {
                return -1;
            } else if (obj1.getTripDuration() > obj2.getTripDuration()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private void swap(int i, int j) {
        Ride temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return 2 * index + 1;
    }

    private int right(int index) {
        return 2 * index + 2;
    }
}
