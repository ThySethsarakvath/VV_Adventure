package ai;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode;
	public Node goalNode;
	Node currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}
	
	public void instantiateNodes() {
		
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col <gp.maxWorldCol && row < gp.maxWorldRow) {
			
			node[col][row] = new Node(col,row);
			
			col++;
			if(col == gp.maxWorldCol) {
				col =0;
				row++;
			}
		}
	}
	
	public void resetNodes() {
	    // Clear existing data faster
	    openList.clear();
	    pathList.clear();
	    
	    // Reset all nodes
	    for(int col = 0; col < gp.maxWorldCol; col++) {
	        for(int row = 0; row < gp.maxWorldRow; row++) {
	            node[col][row].open = false;
	            node[col][row].checked = false;
	            node[col][row].solid = false;
	        }
	    }
	    
	    goalReached = false;
	    step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow,Entity entity) {
		
		resetNodes();
		
		// set start and goal nodes
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col <gp.maxWorldCol && row <gp.maxWorldRow) {
			
			//set solid nodes
			// check tiles
			int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
			if(gp.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			//check interactive tiles
			for(int i = 0; i<gp.iTile[1].length;i++) {
				if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
					int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
					int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
					node[itCol][itRow].solid = true;

				}
			}
			// set cost
			getCost(node[col][row]);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	
	public void getCost(Node node) {
	    int xDistToStart = Math.abs(node.col - startNode.col);
	    int yDistToStart = Math.abs(node.row - startNode.row);
	    node.gCost = xDistToStart + yDistToStart;

	    xDistToStart = Math.abs(node.col - goalNode.col);
	    yDistToStart = Math.abs(node.row - goalNode.row);
	    node.hCost = xDistToStart + yDistToStart;

	    node.fCost = node.gCost + node.hCost;
	}

	
	public boolean search() {
		
		while(goalReached == false && step < 500) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			// check current nodes
			currentNode.checked = true;
			openList.remove(currentNode);
			
			// open up node
			if(row -1 >= 0) {
				openNode(node[col][row-1]);
			}
			// open left node
			if(col -1 >= 0) {
				openNode(node[col-1][row]);
			}
			// open down node
			if(row+1  <gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			// open right node
			if(col +1 <gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			// find the best node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i<openList.size();i++) {
				
				// if node of f cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				// if f cost is equal, check g cost
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
						
					}
				}
			}
			
			// if there's no node in the openList, end loop
			if(openList.size() == 0) {
				goalReached = false;
				break;
			}
			
			// after loop , openList(bestNodeIndex) is the next step (= currentNode)
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		return goalReached;
	}
	
	public void openNode(Node node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			pathList.add(0,current);
			current = current.parent;
		}
	}
}