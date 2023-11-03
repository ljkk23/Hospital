//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class LRUCache {
//    private Node head;
//    private Node tail;
//    private Integer size;
//    private Map<Integer,Node> nodeMap;
//    class Node{
//        private Integer key;
//        private Integer value;
//        private Node pre;
//        private Node next;
//        public Node(){
//
//        }
//        public Node(Integer key,Integer value){
//            this.key=key;
//            this.value=value;
//        }
//    }
//    public LRUCache(Integer size){
//        this.size=size;
//        this.nodeMap=new HashMap<>();
//        this.head=new Node();
//        this.tail=head;
//    }
//    public Integer get(Integer key){
//        if (nodeMap.containsKey(key)){
//            Node node = nodeMap.get(key);
//            refreshNode(node);
//            return node.value;
//        }
//        return -1;
//    }
//
//    public void put(Integer key,Integer value){
//        if (nodeMap.containsKey(key)){
//            Node oldNode = nodeMap.get(key);
//            deleteNode(oldNode);
//            Node node=new Node(key,value);
//            addTailNode(node);
//            return;
//        }
//        Node node=new Node(key,value);
//        if (nodeMap.size()>=this.size){
//            deleteNode(head.next);
//            addTailNode(node);
//            return;
//        }
//        addTailNode(node);
//    }
//
//
//    public void refreshNode(Node node){
//        deleteNode(node);
//        addTailNode(node);
//    }
//    public void deleteNode(Node node){
//        Node pre = node.pre;
//        Node end = node.next;
//        pre.next=end;
//        end.pre=pre;
////        node.pre.next=node.next;
////        node.pre=null;
////        node.next=null;
//        nodeMap.remove(node.key);
//    }
//    public void addTailNode(Node node){
//        tail.next=node;
//        node.pre=tail;
//        tail=node;
//        nodeMap.put(node.key,node);
//    }
//}