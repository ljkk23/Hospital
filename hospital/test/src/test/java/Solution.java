import java.util.Objects;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static void main(String[] args) {
        ListNode head=new ListNode();
        ListNode tmp=head;
        for (int i = 1; i < 6; i++) {
            ListNode node=new ListNode(i);
            tmp.next=node;
            tmp=node;
        }
        ListNode realHead=head.next;
        reverseKGroup(realHead,2);
//        while(realHead!=null){
//            System.out.println(realHead.val);
//            realHead=realHead.next;
//        }
    }
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy=new ListNode();
        dummy.next=head;
        ListNode pre=dummy;
        ListNode end=dummy;
        while(end.next!=null){
            for(int i=0;i<k && end!=null;i++){
                System.out.println("end "+ end.val);
                end=end.next;
            }
            if(end==null){
                break;
            }
            //reverseNode(pre,end);
            ListNode movpre=pre;
            ListNode start=pre.next;

            ListNode cur=movpre.next;
            System.out.println("end next"+end.next.val+"dd"+end.next);
            ListNode tmpNode=end.next;
            System.out.println(tmpNode);
            while(cur!=end.next){
                System.out.println(cur.val);

                ListNode newCur=cur.next;
                cur.next=movpre;
                movpre=cur;
                cur=newCur;
                boolean equals = Objects.equals(cur, end.next);
                System.out.println(cur);
                System.out.println(end.next);
                System.out.println("=="+equals);
            }
            pre.next=movpre;
            pre=start;
            start.next=end.next;
            end=start;
        }
//        while(dummy.next!=null){
//            System.out.println(dummy.next.val);
//            dummy=dummy.next;
//        }
        return dummy.next;
    }
}