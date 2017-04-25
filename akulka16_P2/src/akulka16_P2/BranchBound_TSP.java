package akulka16_P2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class BranchBound_TSP {

	/*
	 * shortestPath: shortest path to visit all cities exactly once.
	 * shortestDistance: variable will store distance to traverse shortestPath.
	 * longestDistance: variable will contain longest distance to visit all
	 * cities.
	 * 
	 */
	public static int shortestDistance;
	public static int queueCount;
	public static ArrayList<Integer> shortestPath;
	public static PriorityQueue queue = new PriorityQueue(100000000);

	/*
	 * Reads input file; solves the traveling salesman problem using brute force
	 * search; writes the output file
	 * 
	 * @param pathName path of input file.
	 */
	public static void startTSP(String pathName) {
		int[][] distanceMatrix = null;
		File file = new File("output.txt");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			String citiesNumber = "";
			int problemNo = 0;
			while ((citiesNumber = bufferedReader.readLine()) != null) {
				shortestDistance = Integer.MAX_VALUE;
				queueCount = 0;
				shortestPath = null;
				int totalCities = Integer.parseInt(citiesNumber);
				distanceMatrix = new int[totalCities][totalCities];
				for (int i = 0; i < totalCities * totalCities; i++) {
					String[] dist = bufferedReader.readLine().split("\\s+");
					distanceMatrix[Integer.parseInt(dist[0])][Integer.parseInt(dist[1])] = Integer.parseInt(dist[2]);
				}
				long startTime = System.currentTimeMillis();
				queue.clear();
				solveTSP(distanceMatrix);
				long endTime = System.currentTimeMillis();
				if (!file.exists()) {
					file.createNewFile();
				}
				bufferedWriter
						.write(++problemNo + " " + totalCities + " " + shortestDistance + " " + queueCount + " " + (endTime - startTime));
				bufferedWriter.newLine();
				for (int city : shortestPath) {
					bufferedWriter.write(Integer.toString(city));
					bufferedWriter.newLine();
				}
			}
			bufferedReader.close();
			bufferedWriter.close();

		} catch (Exception expception) {

			expception.printStackTrace();
		}
	}

	private static void solveTSP(int[][] distanceMatrix) {

		int totalCities = distanceMatrix.length;
		ArrayList<Integer> cities = new ArrayList<Integer>();
		for (int i = 0; i < totalCities; i++) {
			cities.add(i);
		}
		ArrayList<Integer> path;
		int initB = initbound(totalCities, distanceMatrix);
		Node v = new Node(new ArrayList<>(), 0, initB, 0);
		queue.add(v);
		queueCount++;
		while (!queue.isEmpty()) {
			v = queue.remove();
			if (v.getBound() < shortestDistance) {
				Node u = new Node();
				u.setLevel(v.getLevel() + 1);
				for (int i = 1; i < totalCities; i++) {
					path = v.getPath();
					if (!path.contains(i)) {
						u.setPath(v.getPath());
						path = u.getPath();
						path.add(i);
						u.setPath(path);
						if (u.getLevel() == totalCities - 2) {
							// put index of only vertex not in u.path at the end
							// of u.path
							for (int j = 1; j < cities.size(); j++) {
								if (!u.getPath().contains(j)) {
									ArrayList<Integer> temp = new ArrayList<>();
									temp = u.getPath();
									temp.add(j);
									u.setPath(temp);
								}
							}
							path = u.getPath();
							path.add(0);
							u.setPath(path);
							if (u.computeLength(distanceMatrix) < shortestDistance) {
								shortestDistance = u.computeLength(distanceMatrix);// implement
								shortestPath = u.getPath();
							}
						} else {
							u.setBound(computeBound(u, distanceMatrix, cities));
							if (u.getBound() < shortestDistance) {
								queue.add(u);
								queueCount++;
							}
						}
					}
				}
			}
		}
	}

	private static int computeBound(Node u, int[][] distanceMatrix, ArrayList<Integer> cities) {
		int bound = 0;
		ArrayList<Integer> path = u.getPath();
		for (int i = 0; i < path.size() - 1; i++) {
			bound = bound + distanceMatrix[path.get(i)][path.get(i + 1)];
		}
		int last = path.get(path.size() - 1);
		List<Integer> subPath1 = path.subList(1, path.size());
		List<Integer> subPath2 = path.subList(0, path.size() - 1);
		int min;
		for (int i = 0; i < cities.size(); i++) {
			min = Integer.MAX_VALUE;
			if (!path.contains(cities.get(i))) {
				for (int j = 0; j < cities.size(); j++) {
					if (i != j && !subPath1.contains(cities.get(j))) {
						if (min > distanceMatrix[i][j]) {
							min = distanceMatrix[i][j];
						}
					}
				}
			}
			if (min != Integer.MAX_VALUE)
				bound = bound + min;
		}
		min = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != last && !subPath2.contains(i) && min > distanceMatrix[last][i]) {
				min = distanceMatrix[last][i];
			}
		}
		bound = bound + min;
		return bound;
	}

	private static int initbound(int totalCities, int[][] distanceMatrix) {
		int min;
		int bound = 0;
		for (int i = 0; i < totalCities; i++) {
			min = Integer.MAX_VALUE;
			for (int j = 0; j < totalCities; j++) {
				if (distanceMatrix[i][j] != 0) {
					if (min > distanceMatrix[i][j])
						min = distanceMatrix[i][j];
				}
			}
			bound = bound + min;
		}
		return bound;
	}

	public static void main(String[] args) {
		String pathName = "input.txt";
		startTSP(pathName);
	}

}
