package datamining.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Create by LinBin on 2017/11/12
 */

public class ClassifierHelper {
	
	/**
	 * 获取数据
	 * @param dir 文件路径
	 * @return 数据
	 * @throws IOException
	 */
	public static Data getData(String dir)throws IOException{
		Data data=new Data();
		data.setFlowers(readFile(dir));
		data.setCount(data.getFlowers().size());
		data.setTypes(countType(data));
		data.setCollections(getCollection(data));
		return data;
	}

	/**
	 * 读取iris文件
	 * @param dir iris文件路径
	 * @return 鸢尾花集
	 * @throws IOException
	 */
	public static List<FlowerBean> readFile(String dir) throws IOException {
		List<FlowerBean> list=new ArrayList<FlowerBean>();
		BufferedReader in=new BufferedReader(new FileReader(dir));
		String line;
		String[] flower;
		String type;
		FlowerBean bean;
		double calyxLength,calyxWidth,petalLength,petalWidth;
		while (null!=(line=in.readLine())) {
			flower=line.split(",");
			if (flower.length==5) {
				type=flower[4];
				calyxLength=Double.valueOf(flower[0]);
				calyxWidth=Double.valueOf(flower[1]);
				petalLength=Double.valueOf(flower[2]);
				petalWidth=Double.valueOf(flower[3]);
				bean=new FlowerBean(type, calyxLength, calyxWidth, petalLength, petalWidth);
				list.add(bean);
			}
		}
		in.close();
		
		return list;
	}
	
	/**
	 * 统计鸢尾花的种类
	 * @param data 鸢尾花集
	 * @return 以Set形式返回鸢尾花类型
	 */
	public static Set<String> countType(Data data){
		Set<String> types=new HashSet<>();
		for(FlowerBean bean:data.getFlowers()) {
			types.add(bean.getType());
		}
		return types;
	}
	
	public static List<IrisCollection >getCollection(Data data) {
		//创建鸢尾花集
		ArrayList<IrisCollection> list=new ArrayList<IrisCollection>();
		Iterator<String> strIterator=data.getTypes().iterator();
		while (strIterator.hasNext()) {
			IrisCollection collection=new IrisCollection(strIterator.next());
			list.add(collection);
		}
		
		//将数据添加入鸢尾花集
		Iterator<FlowerBean> flowerIterator=data.getFlowers().iterator();
		while (flowerIterator.hasNext()) {
			FlowerBean flower=flowerIterator.next();
			for(IrisCollection collection:list) {
				if (collection.getType().equals(flower.getType())) {
					collection.add(flower);
				}
			}
		}
		
		for(IrisCollection collection:list) {
			computation(collection);
		}
		
		return list;
		
	}
	
	/**
	 * 对数据集进行计算，求各类别的均值，标准差
	 * @param collection
	 */
	public static void computation(IrisCollection collection) {
		
		//计算平均数
		double sumCLength=0,sumCWidth=0,sumPLength=0,sumPWidth=0;
		int size=collection.size();
		Iterator<FlowerBean> iterator=collection.iterator();
		while (iterator.hasNext()) {
			FlowerBean flowerBean=iterator.next();
			sumCLength+=flowerBean.getCalyxLength();
			sumCWidth+=flowerBean.getCalyxWidth();
			sumPLength+=flowerBean.getPetalLength();
			sumPWidth+=flowerBean.getPetalWidth();
		}
		double averageCLength=sumCLength/size;
		double averageCWidth=sumCWidth/size;
		double averagePLength=sumPLength/size;
		double averagePWidth=sumPWidth/size;
		collection.setAverageClength(averageCLength);
		collection.setAverageCWidth(averageCWidth);
		collection.setAveragePLength(averagePLength);
		collection.setAveragePWidth(averagePWidth);
		
		//计算标准差
		double varianceCLength=0;
		double varianceCLwidth=0;
		double variancePLength=0;
		double variancePWidth=0;
		iterator=collection.iterator();
		while (iterator.hasNext()) {
			FlowerBean flowerBean=iterator.next();
			varianceCLength+=Math.pow(flowerBean.getCalyxLength()-averageCLength, 2);
			varianceCLwidth+=Math.pow(flowerBean.getCalyxWidth()-averageCWidth, 2);
			variancePLength+=Math.pow(flowerBean.getPetalLength()-averagePLength, 2);
			variancePWidth+=Math.pow(flowerBean.getPetalWidth()-averagePWidth, 2);
		}
		collection.setStdDevCLength(Math.sqrt(varianceCLength/size));
		collection.setStdDevCWidth(Math.sqrt(varianceCLwidth/size));
		collection.setStdDevPLength(Math.sqrt(variancePLength/size));
		collection.setStdDevPWidth(Math.sqrt(variancePWidth/size));
		
	}
	
	
	/**
	 * 正态分布/高斯分布
	 * @param x 随机变量X
	 * @param average 平均数μ
	 * @param stdDev 标准差σ
	 * @return 概率结果
	 */
	public static double gaussianDistribution(double x,double average,double stdDev) {
		double result=(1/(Math.sqrt(2*Math.PI)*stdDev))
				*Math.exp(-(Math.pow((x-average), 2))/(2*Math.pow(stdDev, 2)));
		return result;
	}
	
	/**
	 * 朴素贝叶斯分类器算法
	 * p=p(花萼长度/类别)*p(花萼宽度/类别)*p(花瓣长度/类别)*p(花瓣宽度/类别)*p(类别)
	 * @param data 数据源
	 * @param cLength 花萼长度
	 * @param cWidth 花萼宽度
	 * @param pLength 花瓣长度
	 * @param pWidth 花瓣宽度
	 * @return 结果字符串
	 */
	public static String classifier(Data data,double cLength,double cWidth,double pLength,double pWidth) {
		StringBuffer buffer=new StringBuffer();
		String type="";
		double maxP=0;
		
		for(IrisCollection irisCollection:data.getCollections()) {
			double pCLength=gaussianDistribution(
					cLength, irisCollection.getAverageCLength(), irisCollection.getStdDevCLength());
			double pCWidth=gaussianDistribution(
					cWidth, irisCollection.getAverageCWidth(), irisCollection.getStdDevCWidth());
			double pPLength=gaussianDistribution(
					pLength, irisCollection.getAveragePLength(), irisCollection.getStdDevPLength());
			double pPWidth=gaussianDistribution(
					pWidth, irisCollection.getAveragePWidth(), irisCollection.getStdDevPWidth());
			double p=(double)irisCollection.size()/(double)data.getCount();
			double result=p*pCLength*pCWidth*pPLength*pPWidth;
			if (result>maxP) {
				maxP=result;
				type=irisCollection.getType();
			}
			buffer.append(irisCollection.getType()+"匹配度: "+String.valueOf(result)+"\n");
		}
		
		buffer.append("推测类型为: "+type);
		return buffer.toString();
	}
}
