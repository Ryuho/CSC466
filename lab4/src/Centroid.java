package src;

import java.util.ArrayList;

public class Centroid {
	private ArrayList<ArrayList<Double>> dataPoints;
	private ArrayList<Double> pos;
	private ArrayList<Double> lastPosition;
	private int reassigned;
	private int lastReassigned;
	
	//default constructor
	public Centroid(){
		dataPoints = new ArrayList<ArrayList<Double>>();
		pos = new ArrayList<Double>();
		lastPosition = new ArrayList<Double>();
		reassigned = 0;
		lastReassigned = 0;
	}
	
	//Copy constructor
	public Centroid(Centroid c){
		this.dataPoints = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < c.dataPoints.size(); i++){
			this.dataPoints.add(new ArrayList<Double>(c.dataPoints.get(i)));
		}

		this.pos = new ArrayList<Double>();
		for(int i = 0; i < c.pos.size(); i++){
			this.pos.add(c.pos.get(i));
		}
		
		this.lastPosition = new ArrayList<Double>();
		for(int i = 0; i < c.lastPosition.size(); i++){
			this.lastPosition.add(c.lastPosition.get(i));
		}
		
		this.reassigned = c.reassigned;
		this.lastReassigned = c.lastReassigned;
		
	}
	
	//Adds a datapoint to dataPoints as well as increment the reassigned number
	public void add(ArrayList<Double> dataPoint){
		dataPoints.add(dataPoint);
		reassigned++;
	}
	
	//Reset the dataPoints and reassigned, but NOT the pos or lastPosition
	public void reset(){
		dataPoints.clear();
		lastReassigned = reassigned;
		reassigned = 0;
	}
	
	public double distChange(){
		return kmeans.pointDistance(pos, lastPosition);
	}
	
	public void calcCenter_Mean(){
		if(dataPoints.size() == 0){
			System.err.println("Trying to calculate the center for empty Centroid!");
		}
		lastPosition = new ArrayList<Double>(pos);
		pos.clear();
		
		//for each value in a dataPoint
		for(int doubleIdx = 0; doubleIdx < dataPoints.get(0).size(); doubleIdx++){
			double sum = 0;
			for(int dataIdx = 0; dataIdx < dataPoints.size(); dataIdx++){
				sum += dataPoints.get(dataIdx).get(doubleIdx);
			}
			pos.add(sum/(double)dataPoints.size());
		}
	}
	
	public void calcCenter_Median(){
		//TODO
	}
	public void calcCenter_Mode(){
		//TODO
	}

	public String toString(){
		String answer = "";
		answer += "dataPoints="+dataPoints+"| ";
		answer += "pos="+pos+"| ";
		answer += "lastPosition="+lastPosition+"| ";
		answer += "reassigned="+reassigned;
		return answer;
	}

	
	//
	// Getters and setters
	//
	
	public ArrayList<ArrayList<Double>> getDP(){
		return dataPoints;
	}

	public void setDP(ArrayList<ArrayList<Double>> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public ArrayList<Double> getPos() {
		return pos;
	}

	public void setPos(ArrayList<Double> pos) {
		this.pos = pos;
	}

	public ArrayList<Double> getLP() {
		return lastPosition;
	}

	public void setLP(ArrayList<Double> lastPosition) {
		this.lastPosition = lastPosition;
	}

	public int getRE() {
		return reassigned;
	}

	public void setRE(int reassigned) {
		this.reassigned = reassigned;
	}

	public void setLR(int lastReassigned) {
		this.lastReassigned = lastReassigned;
	}

	public int getLR() {
		return lastReassigned;
	}
}
