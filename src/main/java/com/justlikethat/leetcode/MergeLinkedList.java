package com.justlikethat.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for singly-linked list.
 */
class ListNode {
	int val;
	ListNode next;

	ListNode() {}
	ListNode(int val) { this.val = val;	}
	ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	public String toString() {
		StringBuffer buff = new StringBuffer();
		ListNode t = this;
		int loopCount = 0;
		do {
			buff.append(t.val).append(" ");
			t = t.next;
			if(loopCount ++ > 40) break;
			System.out.println("generating toString");
		} while(t != null);
		return buff.toString();
	}
}

class MergeLinkedList {
	
	public static void main(String s[]) {

		
		ListNode[] arr = new ListNode[5];
		arr[0] = new ListNode(10, new ListNode(15, new ListNode(20, new ListNode(22, new ListNode(25)))));
		arr[1] = new ListNode(11, new ListNode(14, new ListNode(22, new ListNode(22, new ListNode(30, new ListNode(35))))));
		arr[2] = new ListNode(5, new ListNode(8, new ListNode(24, new ListNode(34, new ListNode(45, new ListNode(55))))));
		arr[3] = new ListNode(9, new ListNode(16, new ListNode(18, new ListNode(24, new ListNode(29, new ListNode(36))))));
		arr[4] = new ListNode(13, new ListNode(16, new ListNode(26, new ListNode(29, new ListNode(31, new ListNode(43))))));
		//*/
		
		MergeLinkedList instance = new MergeLinkedList();
		
		ListNode result = instance.mergeKLists(arr);
		System.out.println("result) " + result);
		
	}
	
	public ListNode mergeKLists(ListNode[] lists) {
		List<ListNode> list = new ArrayList<>();
		
		for (int i = 0; i < lists.length; i++) {
			System.out.println("converting into ArrayList");
			list.add(lists[i]);
		}
		
		int limitCount = 0;
		while(true) {
			System.out.println("Loop 1");
			List<ListNode> updatedList = new ArrayList<>();
			updatedList.clear();
			int i;
			for (i = 0; i < list.size() - 1; i += 2) {
				System.out.println("loop 1.1");
				updatedList.add(mergeNodes(list.get(i), list.get(i + 1)));
			}
			if(i < list.size()) {
				updatedList.add(list.get(i));
			}
			list.clear();
			for (i = 0; i < updatedList.size(); i++) {
				System.out.println("Loop 1.2");
				list.add(lists[i]);
			}
			if(list.size() == 1) {
				break;
			}
			list.forEach(l -> System.out.println("loop 1.3) " + l));
			
			limitCount++;
			if(limitCount > 5) break;
		}
		return list.get(0);

	}

	private ListNode mergeNodes(ListNode a, ListNode b) {
		ListNode root;
		ListNode anchor;
		System.out.println("a: " + a);
		System.out.println("b: " + b);
		if (a.val < b.val) {
			anchor = a;
			a = a.next;
		} else {
			anchor = b;
			b = b.next;
		}

		root = anchor;

		int limitCount = 0;
		while (a != null && b != null) {
			System.out.println("Loop 2");
			if (a.val < b.val) {
				anchor.next = a;
				a = a.next;
			} else {
				anchor.next = b;
				b = b.next;
			}
			anchor = anchor.next;
			anchor.next = null;
			if(limitCount++ > 50) break;
		}

		if (a == null) {
			anchor.next = b;
		} else if (b == null) {
			anchor.next = a;
		}

		return root;

	}

}