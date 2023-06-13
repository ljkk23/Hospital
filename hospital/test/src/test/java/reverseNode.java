import org.springframework.data.mapping.IdentifierAccessor;

public class reverseNode {

    static class Node{
        int val;
        Node next;
        public Node(){
            this.next=null;
        }
        public Node(int val){
            this.val=val;
            this.next=null;
        }
    }
    public static void main(String[] args) {
        int[] nums=new int[]{1,2,3,4,5};
        Node dummy=new Node();
        Node tmp=dummy;
        for (int i=0;i< nums.length;i++){
            Node node=new Node(nums[i]);
            tmp.next=node;
            tmp=node;
        }
        Node head=dummy.next;
        while (head!=null){
            System.out.println(head.val);
            head=head.next;
        }
        Node fast=dummy.next;
        Node slow=dummy.next;
        while (fast!=null && fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
//        System.out.println(slow.val);
        Node first=null;
        Node p=slow.next;
        slow.next=null;
        while (p!=null){
            Node tail=p.next;
            p.next=first;
            first=p;
            p=tail;
        }
//        while (first!=null){
//
//            System.out.println(first.val);
//            first=first.next;
//        }
        Node reshead=dummy.next;
        Node res=reshead;
        while (reshead!=null && first!=null){
            Node tali1=reshead.next;
            Node tail2=first.next;
            reshead.next=first;
            first.next=tali1;
            reshead=tali1;
            first=tail2;
        }
        while (res!=null){
            System.out.println(res.val);
            res=res.next;
        }
    }
}
