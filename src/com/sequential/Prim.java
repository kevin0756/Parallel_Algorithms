package com.sequential;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Prim {
    // Number of vertices in the graph
    private static final int V=10;
 
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(List<Integer> keyList, List<Boolean> mstSetList){
    	
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
        Iterator<Integer> iterator = keyList.iterator();
        int v = 0;
        while(iterator.hasNext()) {
        	iterator.next();
        	if(mstSetList.get(v) == Boolean.FALSE && keyList.get(v) < min) {
        		min = keyList.get(v);
        		min_index = v;
        	}
        	v++;
        }
        return min_index;
    }
 
    void printMST(List parentList, int n, List<List> graphList){
        System.out.println("Edge   Weight");
        for (int i = 1; i < V; i++)
        	System.out.println(parentList.get(i)+" - "+ i+"    "+
                    graphList.get(i).get((int) parentList.get(i)));
    }
 
    void primMST(List<List>graphList){
    	Integer[] integers = new Integer[V];
    	Boolean[] booleans = new Boolean[V];
    	Arrays.fill(integers, 0);
    	Arrays.fill(booleans, Boolean.FALSE);
    	List<Integer> parentList = Arrays.asList(integers);
    	List<Integer> keyList = new ArrayList<>();
    	keyList.addAll(parentList);
    	
        List<Boolean> mstSetList =  Arrays.asList(booleans);
        Iterator<Boolean> iterator = mstSetList.iterator();
        int idx=0;
        while(iterator.hasNext()) {
        	iterator.next();
        	keyList.set(idx, Integer.MAX_VALUE);
        	idx++;
        }
        
        keyList.set(0, 0);
        parentList.set(0, -1);
 
        int count = 0;
        iterator = mstSetList.iterator();
        while(iterator.hasNext() && count<V-1) {
        	iterator.next();
        	int u = minKey(keyList, mstSetList);
        	mstSetList.set(u, Boolean.TRUE);
        	Iterator<Integer> iterator2 = keyList.iterator();
        	int v = 0;
        	while(iterator2.hasNext()) {
        		iterator2.next();
        		if ((int)graphList.get(u).get(v) !=0 && mstSetList.get(v) == Boolean.FALSE &&
            			(int)graphList.get(u).get(v) <  keyList.get(v)){
        		parentList.set(v, u);
        		keyList.set(v, (int)graphList.get(u).get(v));
        		}
        		v++;
        	}
        	count++;
        }
        
        // print the constructed MST
        printMST(parentList, V, graphList);
    }
 
    public static void main (String[] args)
    {
        /* Let us create the following graph
           2    3
        (0)--(1)--(2)
        |    / \   |
        6| 8/   \5 |7
        | /      \ |
        (3)-------(4)
             9          */
    	Prim t = new Prim();
    	List<List> graphList= new ArrayList<>();
    	List<Integer> elements1 = new ArrayList<>();
    	elements1.add(0);
    	elements1.add(2);
    	elements1.add(0);
    	elements1.add(6);
    	elements1.add(0);
    	List<Integer> elements2 = new ArrayList<>();
    	elements2.add(2);
    	elements2.add(0);
    	elements2.add(3);
    	elements2.add(8);
    	elements2.add(5);
    	List<Integer> elements3 = new ArrayList<>();
    	elements3.add(0);
    	elements3.add(3);
    	elements3.add(0);
    	elements3.add(0);
    	elements3.add(7);
    	List<Integer> elements4 = new ArrayList<>();
    	elements4.add(6);
    	elements4.add(8);
    	elements4.add(0);
    	elements4.add(0);
    	elements4.add(9);
    	List<Integer> elements5 = new ArrayList<>();
    	elements5.add(0);
    	elements5.add(5);
    	elements5.add(7);
    	elements5.add(9);
    	elements5.add(0);
    	/*List<Integer> elements6 = new ArrayList<>();
    	elements6.add(0);
    	elements6.add(2);
    	elements6.add(0);
    	elements6.add(6);
    	elements6.add(0);
    	List<Integer> elements7 = new ArrayList<>();
    	elements7.add(2);
    	elements7.add(0);
    	elements7.add(3);
    	elements7.add(8);
    	elements7.add(5);
    	List<Integer> elements8 = new ArrayList<>();
    	elements8.add(0);
    	elements8.add(3);
    	elements8.add(0);
    	elements8.add(0);
    	elements8.add(7);
    	List<Integer> elements9 = new ArrayList<>();
    	elements9.add(6);
    	elements9.add(8);
    	elements9.add(0);
    	elements9.add(0);
    	elements9.add(9);
    	List<Integer> elements10 = new ArrayList<>();
    	elements10.add(0);
    	elements10.add(5);
    	elements10.add(7);
    	elements10.add(9);
    	elements10.add(0);*/
    	graphList.add(elements1);
    	graphList.add(elements2);
    	graphList.add(elements3);
    	graphList.add(elements4);
    	graphList.add(elements5);
    	/*graphList.add(elements6);
    	graphList.add(elements7);
    	graphList.add(elements8);
    	graphList.add(elements9);
    	graphList.add(elements10);*/
    	/*
        int graph[][] = new int[][] {{0, 2, 0, 6, 0},
                                    {2, 0, 3, 8, 5},
                                    {0, 3, 0, 0, 7},
                                    {6, 8, 0, 0, 9},
                                    {0, 5, 7, 9, 0},
                                   };*/
 
        // Print the solution
    	long startTime = System.nanoTime();
    	t.primMST(graphList);
    	long endTime   = System.nanoTime();
    	long totalTime = endTime - startTime;
    	double milliseconds = (double)totalTime / 1000000.0;
    	System.out.println(milliseconds);
        
    }
}
