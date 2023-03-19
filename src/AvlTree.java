import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AvlTree {
	class Node{
		String data;
		int height;
		Node left,right;
		
		Node(String d){
			data = d;
			height = 1;
		}
	}
	public FileWriter avlWriter ;
	public AvlTree() throws IOException {
		
		//avlWriter = new FileWriter("C:\\Users\\ahmet\\eclipse-workspace\\PS--1\\output_AVL.txt");	
	}
	
	Node root;
	
	int height(Node node) {
		if(node == null) {
			return 0;
		}
		return node.height;
	}
	
	int getMax(int a,int b) {
		if(a>b)
			return a;
		return b;
	}
	
	Node rightRotate(Node y) {
		Node x = y.left;
		Node temp = x.right;
		x.right = y;
		y.left = temp;
		
		y.height = getMax(height(y.left), height(y.right)) + 1;
		x.height = getMax(height(x.left), height(x.right)) + 1;
		//root = x;
		return x;
	}
	
	Node leftRotate(Node x) {
		Node y = x.right;
		Node temp = y.left;
		
		y.left = x;
		x.right = temp;
		x.height = getMax(height(x.left), height(x.right)) + 1;
		y.height = getMax(height(y.left), height(y.right)) + 1;
		return y;
	}
	
	int getBalance(Node node) {
		if(node == null) 
			return 0;
		return height(node.left)-height(node.right);
	}
	public void ADDNODE(String IP) throws IOException {
		root =  insert(root,IP);
	}
	
	Node insert(Node node,String IP) throws IOException {

		if(node ==null)
			return (new Node(IP));
		avlWriter.write(node.data + ": New node being added with IP:"+IP + "\n");

		if(node.data.compareTo(IP) > 0) 
			node.left = insert(node.left,IP);
		else if(node.data.compareTo(IP) < 0)
			node.right = insert(node.right,IP);
		else
			return node;
		node.height = 1+ getMax(height(node.left),height(node.right));
		
		int balanceNumber = getBalance(node);
		if(balanceNumber >1 && node.left.data.compareTo(IP) > 0) {
			avlWriter.write("Rebalancing: right rotation"+"\n");
			return rightRotate(node);
		}
		if(balanceNumber < -1 && node.right.data.compareTo(IP) < 0) {//?????
			avlWriter.write("Rebalancing: left rotation"+"\n");

			return leftRotate(node);
		}
		if(balanceNumber >1 && node.left.data.compareTo(IP) < 0) {
			avlWriter.write("Rebalancing: left-right rotation"+"\n");
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		if(balanceNumber < -1 && node.right.data.compareTo(IP) > 0) { //??????
			avlWriter.write("Rebalancing: right-left rotation"+"\n");
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		
		return node;
	}
	
	Node minValue(Node node) {
		Node curr = node;
		while(curr.left != null) {
			curr = curr.left;
		}
		return curr;	
	}
	boolean checker1 = true;
	public void DELETE(String IP_ADDRESS) throws IOException {
		root = remove(root,null,IP_ADDRESS);
		
	}
	Node remove(Node root,Node parent,String IP) throws IOException {
		//boolean checker = true;
		if(root == null)
			return root;
		if(root.data.compareTo(IP) > 0) {
			parent = root;
			root.left = remove(root.left,parent,IP);
		}
		else if(root.data.compareTo(IP) < 0) {
			parent = root;
			root.right = remove(root.right,parent,IP);
		}
		 else {
      
 
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)){
                Node temp = null;

            	if(root.left == null && root.right == null) {
            		temp = root;
            		root = null;
            		if(checker1)
            			avlWriter.write(parent.data+": Leaf Node Deleted: "+IP+"\n");

            		/*
            		if(checker == true ) {
            			System.out.println(parent.data+": Leaf Node Deleted: "+IP);
            			checker = false;
            		}
            		*/
            	}
            	else {
		    		if (temp == root.left)
		                temp = root.right;
		    		else
		                temp = root.left;
		            root = temp;
		            if(checker1)
		            	avlWriter.write(parent.data+": Node with single child Deleted: "+IP+"\n");

		            /*
		            if(checker == true ) {
		            	System.out.println(parent.data+": Node with single child Deleted: "+IP);
		            	checker = false;
		            }
		            */
            	}
                
            }
            else{
            
 
                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValue(root.right);
 
                // Copy the inorder successor's data to this node
                root.data = temp.data;
 
                // Delete the inorder successor
                avlWriter.write("Non Leaf Node Deleted; removed: "+IP+" replaced: "+temp.data+"\n");
            	checker1 = false;
            	parent = root;
                root.right = remove(root.right,parent,temp.data);
                checker1 = true;

                
                
                /*
                if(checker) {
                	parent = root;
                	System.out.println("Non Leaf Node Deleted; removed: "+IP+" replaced: "+temp.data);
                	
                    root.right = remove(root.right,parent,temp.data);

                	checker = false;
                }
                */
            }
        }
 
        // If the tree had only one node then return
        if (root == null)
            return root;
 
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = getMax(height(root.left), height(root.right)) + 1;
 
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);
 
        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
        	avlWriter.write("Rebalancing: right rotation"+"\n");
            return rightRotate(root);
        }
 
        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
        	avlWriter.write("Rebalancing: left-right rotation"+"\n");
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
 
        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
        	avlWriter.write("Rebalancing: left rotation"+"\n");

            return leftRotate(root);
        }
        
        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
        	avlWriter.write("Rebalancing: right-left rotation"+"\n");
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
 
        return root;
    }
			
			
		
	
	
	/*
	Node remove(Node root,String IP) {
	
		Node curr = root;
		Node prev = null;
		if(curr == null)
			return null;
		while(curr != null && curr.data != IP) {
			prev = curr;
			System.out.println("curr"+curr.data);
			if(curr.data.compareTo(IP) > 0) {
				curr = curr.left;
				if(curr.data == IP)
					break;
			}
			if(curr.data.compareTo(IP) <0) {
				curr = curr.right;	
				if(curr == null)
					break;
			}
			//System.out.println(curr.data);

			if(curr.data.compareTo(IP) == 0)
				break;
		}
		if(curr == null) {
			System.out.println("not found ");
			return root;
		}
		if(curr.left == null || curr.right == null) {
			if(curr.left == null && curr.right == null) {
				System.out.println(prev.data+": Leaf Node Deleted: "+curr.data);
				if(curr == prev.left)
					prev.left = null;
				else
					prev.right = null;
				prev.height = 1+ getMax(height(prev.left),height(prev.right));
				
				int balanceNumber = getBalance(prev);
				if(balanceNumber >1 && prev.left.data.compareTo(IP) > 0) {
					System.out.println("Rebalancing: right rotation");
					return rightRotate(prev);
				}
				if(balanceNumber < -1 && prev.right.data.compareTo(IP) < 0) {//?????
					System.out.println("Rebalancing: left rotation");

					return leftRotate(prev);
				}
				if(balanceNumber >1 && prev.left.data.compareTo(IP) < 0) {
					System.out.println("Rebalancing: left-right rotation");
					prev.left = leftRotate(prev.left);
					return rightRotate(prev);
				}
				if(balanceNumber < -1 && prev.right.data.compareTo(IP) > 0) { //??????
					System.out.println("Rebalancing: right-left rotation");
					prev.right = rightRotate(prev.right);
					return leftRotate(prev);
				}
				
				return prev;
				
				
			}
			else {
				System.out.println(prev.data+": Node with single child Deleted: "+curr.data);
				Node newCurr;
				if(curr.left == null)
					newCurr = curr.right;
				else
					newCurr = curr.left;
				if(curr == prev.left) 
					prev.left = newCurr;
				else
					prev.right = newCurr;
				
				prev.height = 1+ getMax(height(prev.left),height(prev.right));
				
				int balanceNumber1 = getBalance(prev);
				if(balanceNumber1 >1 && prev.left.data.compareTo(IP) > 0) {
					System.out.println("Rebalancing: right rotation");
					return rightRotate(prev);
				}
				if(balanceNumber1 < -1 && prev.right.data.compareTo(IP) < 0) {//?????
					System.out.println("Rebalancing: left rotation");

					return leftRotate(prev);
				}
				if(balanceNumber1 >1 && prev.left.data.compareTo(IP) < 0) {
					System.out.println("Rebalancing: left-right rotation");
					prev.left = leftRotate(prev.left);
					return rightRotate(prev);
				}
				if(balanceNumber1 < -1 && prev.right.data.compareTo(IP) > 0) { //??????
					System.out.println("Rebalancing: right-left rotation");
					prev.right = rightRotate(prev.right);
					return leftRotate(prev);
				}
				
				return prev;
			}
			
		}
		if(curr.left != null && curr.right != null) {
			Node x = null; // previous node of the smallest node from  right subtree
			Node temp;
			
			temp = curr.right;
			while(temp.left != null) {
				x = temp;
				temp = temp.left;					
			}
			
			if(x != null) 
				x.left = temp.right;
			
			else
				curr.right = temp.right;
			//System.out.println(prev.data+": Non Leaf Node Deleted; removed:"+curr.data+" replaced:"+temp.data);
			System.out.println("Non Leaf Node Deleted; removed: "+curr.data+" replaced: "+temp.data);

			curr.data = temp.data;
			curr.height = 1+ getMax(height(curr.left),height(curr.right));
			
			int balanceNumber1 = getBalance(curr);
			if(balanceNumber1 >1 && curr.left.data.compareTo(IP) > 0) {
				System.out.println("Rebalancing: right rotation");
				return rightRotate(curr);
			}
			if(balanceNumber1 < -1 && curr.right.data.compareTo(IP) < 0) {//?????
				System.out.println("Rebalancing: left rotation");

				return leftRotate(curr);
			}
			if(balanceNumber1 >1 && curr.left.data.compareTo(IP) < 0) {
				System.out.println("Rebalancing: left-right rotation");
				curr.left = leftRotate(curr.left);
				return rightRotate(curr);
			}
			if(balanceNumber1 < -1 && curr.right.data.compareTo(IP) > 0) { //??????
				System.out.println("Rebalancing: right-left rotation");
				curr.right = rightRotate(curr.right);
				return leftRotate(curr);
			}
			
			return curr;
		}
		
		return null;	
	}
	*/
				
		
		/*
		root.height = getMax(height(root.left), height(root.right)) + 1;
		 

        int balance = getBalance(root);
        System.out.println(balance );
        
        if (balance > 1 && getBalance(root.left) >= 0) {
			System.out.println("Rebalancing: right rotation");

            return rightRotate(root);
        }
 
        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
			System.out.println("Rebalancing: left-right rotation");
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
 
        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
			System.out.println("Rebalancing: right-left rotation");
            return leftRotate(root);
        }
 
        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
			System.out.println("Rebalancing: left rotation");

            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
 
        return root;
    	*/
	
	
		
	
	
	
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
		//System.out.println(list1.toString());
		for(int i = list1.size()-1;i>=0;i--) {
			list.add(list1.get(i));
		}
		//System.out.println(list.toString());
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
		avlWriter.write(sender+": Sending message to: "+receiver + "\n");
		for(int i = 0 ; i < list.size()-2;i++) {/////////////////-2 or -1?????????????????
			avlWriter.write(list.get(i+1)+": Transmission from: "+list.get(i)+" receiver: "+receiver+" sender:"+sender + "\n");
		}
		avlWriter.write(receiver+": Received message from: "+sender + "\n");			
	}
	
	
	
	
	
	
	
}
