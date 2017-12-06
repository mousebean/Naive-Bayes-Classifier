package datamining.classifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Create by LinBin on 2017/11/12
 */

public class Data {
	
	private int count=0;
	private List<FlowerBean> flowers=new ArrayList<FlowerBean>();
	private Set<String> types=new HashSet<String>();
	private List<IrisCollection> collections=new ArrayList<IrisCollection>();
	public Data() {
	}
	
	public void setFlowers(List<FlowerBean> flowers) {
		this.flowers = flowers;
	}
	
	public void setTypes(Set<String> types) {
		this.types = types;
	}
	
	public List<FlowerBean> getFlowers() {
		return flowers;
	}
	
	public Set<String> getTypes() {
		return types;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<IrisCollection> getCollections() {
		return collections;
	}
	
	public void setCollections(List<IrisCollection> collections) {
		this.collections = collections;
	}

}

class FlowerBean{
	
	private String type;
	//花萼长度,花萼宽度,花瓣长度,花瓣宽度
	private double calyxLength,calyxWidth,petalLength,petalWidth;
	
	public FlowerBean(String type,double calyxLength,double calyxWidth,double petalLength,double petalWidth) {
		this.type=type;
		this.calyxLength=calyxLength;
		this.calyxWidth=calyxWidth;
		this.petalLength=petalLength;
		this.petalWidth=petalWidth;
	}
	
	public double getCalyxLength() {
		return calyxLength;
	}
	
	public double getCalyxWidth() {
		return calyxWidth;
	}
	
	public String getType() {
		return type;
	}
	
	public double getPetalLength() {
		return petalLength;
	}
	
	public double getPetalWidth() {
		return petalWidth;
	}
}

@SuppressWarnings("serial")
class IrisCollection extends ArrayList<FlowerBean>{
	private String type;
	private double averageCLength,averageCWidth,averagePLength,averagePWidth;
	private double stdDevCLength,stdDevCWidth,stdDevPLength,stdDevPWidth;
	public IrisCollection(String type) {
		super();
		this.type=type;
	}

	public void setAverageClength(double averageClength) {
		this.averageCLength = averageClength;
	}

	public double getAverageCWidth() {
		return averageCWidth;
	}

	public void setAverageCWidth(double averageCWidth) {
		this.averageCWidth = averageCWidth;
	}

	public double getAveragePLength() {
		return averagePLength;
	}

	public void setAveragePLength(double averagePLength) {
		this.averagePLength = averagePLength;
	}

	public double getAveragePWidth() {
		return averagePWidth;
	}

	public void setAveragePWidth(double averagePWidth) {
		this.averagePWidth = averagePWidth;
	}
	
	
	
	public double getAverageCLength() {
		return averageCLength;
	}

	public void setAverageCLength(double averageCLength) {
		this.averageCLength = averageCLength;
	}

	public double getStdDevCLength() {
		return stdDevCLength;
	}

	public void setStdDevCLength(double stdDevCLength) {
		this.stdDevCLength = stdDevCLength;
	}

	public double getStdDevCWidth() {
		return stdDevCWidth;
	}

	public void setStdDevCWidth(double stdDevCWidth) {
		this.stdDevCWidth = stdDevCWidth;
	}

	public double getStdDevPLength() {
		return stdDevPLength;
	}

	public void setStdDevPLength(double stdDevPLength) {
		this.stdDevPLength = stdDevPLength;
	}

	public double getStdDevPWidth() {
		return stdDevPWidth;
	}

	public void setStdDevPWidth(double stdDevPWidth) {
		this.stdDevPWidth = stdDevPWidth;
	}

	public String getType() {
		return type;
	}
	
	
	
}
