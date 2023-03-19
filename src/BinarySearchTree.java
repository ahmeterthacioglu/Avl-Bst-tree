import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//import AvlTree.Node;

public class BinarySearchTree {
	class Node{
		
		String data;
		Node left,right;
		
		public Node(String IP_ADDRESS) {
			data = IP_ADDRESS;
			left = null;
			right = null;
		}
	}	
	public FileWriter bstWriter;
	//public FileWriter bstWriter = new FileWriter("C:\\Users\\ahmet\\eclipse-workspace\\PS--1\\output_BST.txt");
	public BinarySearchTree() throws IOException {
		
		//bstWriter = new FileWriter("C:\\Users\\ahmet\\eclipse-workspace\\PS--1\\output_BST.txt");	
	}
	
	Node root;
	
	
	
	public void ADDNODE(String IP_ADDRESS) throws IOException {
		Node node = new Node(IP_ADDRESS);
		if(root == null) {
			root = node;
			return;
		}
		Node prev = null;
		Node temp = root;
		
		while(temp != null) {
			//if(temp.data.compareTo(IP_ADDRESS) == 0)
			//	break;
			bstWriter.write(temp.data + ": New node being added with IP:"+IP_ADDRESS +"\n");
			if(temp.data.compareTo(IP_ADDRESS) > 0) {
				prev = temp;
				temp = temp.left;	
			}
			else if(temp.data.compareTo(IP_ADDRESS) <= 0) {
				prev = temp;
				temp = temp.right;
			}
		}
		
		if(prev.data.compareTo(IP_ADDRESS) > 0)
			prev.left = node;
		else
			prev.right = node;
	}
	
	public void DELETE(String IP_ADDRESS) throws IOException {
		root = remove(root,IP_ADDRESS);
	}
	Node remove(Node root,String IP) throws IOException {
		if(root == null) {
			return null;
		}
		Node curr = root;
		Node prev = null;
		while(curr != null && curr.data != IP) {
			//System.out.println(curr.data);
			prev = curr;
			if(curr.data.compareTo(IP) > 0) {
				curr = curr.left;
			}
			if(curr.data.compareTo(IP)<0)
				curr = curr.right;	
			if(curr.data.compareTo(IP) == 0)
				break;
		}
		if(curr == null) {
			System.out.println("not found ");
			return root;
		}
		if(curr.left == null || curr.right == null) {
			if(curr.left == null && curr.right == null) {
				bstWriter.write(prev.data+": Leaf Node Deleted: "+curr.data+"\n");
				if(curr == prev.left)
					prev.left = null;
				else
					prev.right = null;
			}
			else {
				bstWriter.write(prev.data+": Node with single child Deleted: "+curr.data+"\n");
				Node newCurr;
				if(curr.left == null)
					newCurr = curr.right;
				else
					newCurr = curr.left;
				if(curr == prev.left) 
					prev.left = newCurr;
				else
					prev.right = newCurr;
			}
			
		}
		else {
			Node x = null; // previous node of the smallest node from  right subtree
			Node temp;
			
			temp = curr.right;
			while(temp.left != null) {
				x = temp;
				temp = temp.left;					
			}
			
			if(x != null) { 
				x.left = temp.right;
			}
			else
				curr.right = temp.right;
			//System.out.println(prev.data+": Non Leaf Node Deleted; removed: "+curr.data+" replaced: "+temp.data);
			bstWriter.write("Non Leaf Node Deleted; removed: "+curr.data+" replaced: "+temp.data+"\n");

			curr.data = temp.data;
				
		}
		
		return root;
	}
	
	public Node lowestCommonAncestor(Node root,String x, String y) {
		if(root.data.compareTo(x) > 0 && root.data.compareTo(y) > 0) {
			return lowestCommonAncestor(root.left, x, y);
		}
		if(root.data.compareTo(x) < 0 && root.data.compareTo(y) < 0) {
			return lowestCommonAncestor(root.right, x, y);
		}
		return root;
	}
	
	public void getPath(Node root,String IP,ArrayList<String> path) {
		Node temp = root;
		while(temp !=null) {
			if(temp.data.compareTo(IP) == 0)
				//System.out.println(temp.data);
				//path.add(temp.data);
				break;
			path.add(temp.data);
			if(temp.data.compareTo(IP) > 0) {
				temp = temp.left;
			}
			else if(temp.data.compareTo(IP) < 0)
				temp = temp.right;
			
		}
		path.add(IP);//????????????????????????
	}
	public void printPath(Node root,String IP1,String IP2,ArrayList<String> list) {
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		root = lowestCommonAncestor(root, IP1, IP2);
		getPath(root, IP1, list1);
		getPath(root, IP2, list2);
		//System.out.println(list1.toString());
		//System.out.println(list2.toString());

		//ArrayList<String> list3 = new ArrayList<String>();
		for(int i = list1.size()-1;i>=0;i--) {
			list.add(list1.get(i));
		}
		for(int i = 1; i<list2.size();i++) {
			list.add(list2.get(i));
		}
		//return list3;
	}
	public void SEND(String IP1,String IP2) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		printPath(root, IP1, IP2, list);
		//System.out.println(list.toString());
		String sender = list.get(0);
		String receiver = list.get(list.size()-1);
		bstWriter.write(sender+": Sending message to: "+receiver + "\n");
		for(int i = 0 ; i < list.size()-2;i++) {/////////////////-2 or -1?????????????????
			bstWriter.write(list.get(i+1)+": Transmission from: "+list.get(i)+" receiver: "+receiver+" sender:"+sender + "\n");
		}
		bstWriter.write(receiver+": Received message from: "+sender + "\n");			
	}
		
		/*
		void printPath(Node root,int n1,int n2) {
			ArrayList<String> list1 = new ArrayList<String>();
			ArrayList<String> list2 = new ArrayList<String>();
			path(root,list1,n1);
			path(root,list2,n2);
			int intersection = -1;
			int i = 0;
			int j = 0;
			while(i != list1.size() || j != list2.size()) {
				if(i == j && list1.get(i) == list2.get(j)) {
					i++;
					j++;	
				}
				else {
					intersection = i-1;	
					break;
				}	
			}
			for(int a = list1.size()-1;a > intersection;a--) {
				
				
			}
			
			
		}
		*/
		
		
		/*
		public void DELETE(String IP_ADDRESS) {
			root = remove(IP_ADDRESS,root);
		}
		private Node remove(String IP,Node node) {
			if(node == null) {
				return node;
			}
			if(IP.compareTo(node.data)>0) {
				node.left = remove(IP,node.left);
			}
			else if(IP.compareTo(node.data)<0) {
				node.right = remove(IP,node.right);
			}
			
			else {
				if(root.left == null) {
					return root.right;
				}
				else if(root.right == null) {
					return root.left;
					
				}
				else if(node.left != null && node.right != null) {
					//
				}
			}
						
		}
		*/
		
		
		
		
		
	
	

}
