import java.io.*;
import java.util.Scanner;




public class Project2 {
	public static void main(String[] args) throws IOException {
		Scanner myReader;
		AvlTree Avl = new AvlTree();
		BinarySearchTree Bst = new BinarySearchTree();
		/*
		PrintStream exitFile = null;
		try {
			exitFile = new PrintStream(args[0]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.setOut(exitFile);
		*/
		try {
			File myObj = new File(args[0]);
			Avl.avlWriter = new FileWriter(args[1]+"_AVL.txt");
			Bst.bstWriter = new FileWriter(args[1]+"BST.txt");
			
			myReader = new Scanner(myObj);
			//PrintStream avlOutput = new PrintStream(args[1]+ "_avl.txt");
			//avlOutput.createNewFile();
			//System.setOut(avlOutput);
			//File bstOutput = new File(args[1]+ "_bst.txt");
			//bstOutput.createNewFile();
			//PrintStream avlOutput = new PrintStream("C:\\Users\\ahmet\\eclipse-workspace\\PS--1\\output1.txt");
			
		}
		catch(Exception e ){
			System.out.println(e);
			myReader = null;
			
		}
		//Avl.ADDNODE("4");
		//Bst.ADDNODE("4");
		while(myReader.hasNextLine()) {
			String data = myReader.nextLine();
			String[] parts = data.split(" ");
			String name = parts[0];
			String IP;
			String sender;
			String receiver;
			if(parts.length == 1) {
				IP = parts[0];
				Avl.ADDNODE(IP);
				Bst.ADDNODE(IP);
			}
			switch(name) {
				case("ADDNODE"):
					IP = parts[1];
					Avl.ADDNODE(IP);
					Bst.ADDNODE(IP);
					break;
				case("DELETE"):
					IP = parts[1];
					Avl.DELETE(IP);
					Bst.DELETE(IP);
					break;
				case("SEND"):
					sender = parts[1];
					receiver = parts[2];
					Avl.SEND(sender, receiver);
					Bst.SEND(sender, receiver);
					break;
			}
			
		}
		myReader.close();
		Avl.avlWriter.close();
		Bst.bstWriter.close();
	}
}


		

	


