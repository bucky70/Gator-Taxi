import java.util.ArrayList;

public class RedBlackTree { //order by ridenumber
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private Node root;
  private int size=0;
  private class Node {
    //Our ride class will be key
      Ride key;
      Node left, right;
      boolean color; //true for re, false for black

      Node(Ride key, boolean color) {
          this.key = key;
          this.color = color;
      }
  }

  private boolean isRed(Node x) {
      if (x == null) return false;
      return x.color == RED;
  }

  private Node rotateLeft(Node h) {
    if(h==null){
        return h;
    }
      Node x = h.right;
      h.right = x.left;
      x.left = h;
      x.color = h.color;
      h.color = RED;
      return x;
  }

  private Node rotateRight(Node h) {
    if(h==null){
        return h;
    }
      Node x = h.left;
      h.left = x.right;
      x.right = h;
      x.color = h.color;
      h.color = RED;
      return x;
  }

  private void flipColors(Node h) {
    if(h.left==null || h.right==null){
        return;
    }
      h.color = RED;
      h.left.color = BLACK;
      h.right.color = BLACK;
  }

  public void put(Ride key) {
      root = put(root, key);
      root.color = BLACK;
      size++;
  }
  public int size(){
    return size;
  }
  public void printInRange(int rideNumber1,int rideNumber2){
    ArrayList<Ride> list=new ArrayList<>();
     printInRange(root,rideNumber1, rideNumber2,list);
     if(list.size()==0){
        Ride ride=new Ride(0,0,0);
        System.out.println();
        return;
     }
     for(int i=0;i<list.size()-1;i++){
        System.out.print(list.get(i)+",");
     }
    
        System.out.print(list.get(list.size()-1));
        System.out.println();
     
     
  }
  private void printInRange(Node root,int rideNumber1,int rideNumber2,ArrayList<Ride> list){
    if(root==null){
        return;
    }
     if(rideNumber1<root.key.getRideNumber()){
        printInRange(root.left, rideNumber1, rideNumber2,list);
     }
     if (rideNumber1 <= root.key.getRideNumber() && rideNumber2 >= root.key.getRideNumber()) {
        list.add(root.key);
     }
     printInRange(root.right, rideNumber1, rideNumber2,list);
     
  }
  private Node put(Node h, Ride key) {
      if (h == null) return new Node(key, RED);

      int cmp = compare(key, h.key);

      if (cmp < 0) h.left = put(h.left, key);
      else if (cmp > 0) h.right = put(h.right, key);
      else h.key = key;

      if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
      if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
      if (isRed(h.left) && isRed(h.right)) flipColors(h);

      return h;
  }

  private int compare(Ride obj1, Ride obj2) {
      if (obj1.getRideNumber() < obj2.getRideNumber()) {
          return -1;
      } else if (obj1.getRideNumber() > obj2.getRideNumber()) {
          return 1;
      } else {
          return 0;
      }
  }

  public Ride get(Ride key) {
      Node x = root;
      while (x != null) {
          int cmp = compare(key, x.key);
          if (cmp < 0) x = x.left;
          else if (cmp > 0) x = x.right;
          else return x.key;
      }
      return null;
  }

  //-----------------------------------------------------------------
  public void delete(Ride key) {
    Node z = search(key);
    if (z == null) {
        return;
    }
    delete(z);
    size--;
}

  private void delete(Node node) {
    if (node == null) {
        return;
    }
    Ride key = node.key;
    root = delete(root, key);
    if (root != null) {
        root.color = BLACK;
    }
  }

  private Node delete(Node h, Ride key) {
    if (h == null) {
        return null;
    }

    int cmp = compare(key, h.key);

    if (cmp < 0) {
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = delete(h.left, key);
    } else {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (cmp == 0 && (h.right == null)) {
            return null;
        }
        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        if (cmp == 0) {
            Node x = min(h.right);
            h.key = x.key;
            h.right = deleteMin(h.right);
        } else {
            h.right = delete(h.right, key);
        }
    }

    return balance(h);
}
private Node search(Ride key) {
    Node x = root;
    while (x != null && !key.equals(x.key)) {
        if (compare(key,x.key) < 0) {
            x = x.left;
        } else {
            x = x.right;
        }
    }
    return x != null ? x : null;
}
private Node moveRedLeft(Node h) {
    if(h==null){
        return h;
    }
    flipColors(h);
    if (h.right!=null && isRed(h.right.left)) {
        h.right = rotateRight(h.right);
        h = rotateLeft(h);
        flipColors(h);
    }
    return h;
}

private Node moveRedRight(Node h) {
    if(h==null){
        return h;
    }
    if(h.left==null){
        return h;
    }
    flipColors(h);
    if (isRed(h.left.left)) {
        h = rotateRight(h);
        flipColors(h);
    }
    return h;
}

private Node deleteMin(Node h) {
    if (h.left == null) {
        return null;
    }
    if (!isRed(h.left) && !isRed(h.left.left)) {
        h = moveRedLeft(h);
    }
    h.left = deleteMin(h.left);
    return balance(h);
}

private Node min(Node x) {
    if(x==null){
        return x;
    }
    while (x.left != null) {
        x = x.left;
    }
    return x;
}

private Node balance(Node h) {
    if(h==null){
        return h;
    }
    if (isRed(h.right)) {
        h = rotateLeft(h);
    }
    if (isRed(h.left) && isRed(h.left.left)) {
        h = rotateRight(h);
    }
    if (isRed(h.left) && isRed(h.right)) {
        flipColors(h);
    }
    return h;
}
}
