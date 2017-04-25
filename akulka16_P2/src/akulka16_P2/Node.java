package akulka16_P2;

import java.util.ArrayList;

public class Node {
	
	public Node(ArrayList<Integer> path, int city, int bound, int level) {
		super();
		this.path = path;
		this.path.add(city);
		this.bound = bound;
		this.level = level;
	}
	public Node() {
		// TODO Auto-generated constructor stub
		this.path = new ArrayList<Integer>();
		this.bound = 0;
		this.level = 0;
	}
	public Node(ArrayList<Integer> path2, int level2, int bound2) {
		// TODO Auto-generated constructor stub
		this.path = path2;
		this.level = level2;
		this.bound = bound2;
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = new ArrayList<>(path);
	}
	public int getBound() {
		return bound;
	}
	public void setBound(int bound) {
		this.bound = bound;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	private ArrayList<Integer> path;
	private int bound;
	private int level;
	
	public int computeLength(int[][] distanceMatrix) {
		// TODO Auto-generated method stub
		int distance = 0;
		for(int i=0;i<this.getPath().size()-1;i++){
			distance = distance + distanceMatrix[this.getPath().get(i)][this.getPath().get(i+1)];
		}
		return distance;
	}
}
