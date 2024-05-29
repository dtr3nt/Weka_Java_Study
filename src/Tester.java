package team2.uco.edu;

import java.util.ArrayList;

import weka.classifiers.Classifier;
//import team2.uco.edu.ModelClassifier;
//import team2.uco.edu.ModelGenerator;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class Tester {

	    public static final String DATASETPATH = "C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\breast-cancer.arff";
	    public static final String MODELPATH = "C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\model.bin";
	    private  Instances rawData;
	    private  Instance singleInstance;
	    private  Instances insts;
	    private  ArrayList<Attribute> attributes;
	    private  ArrayList<String> classValue;
	    
	    private  Attribute age;
	    private  String inputAge;
	    private  FastVector ageValues = new FastVector(9);
	    
	    private  Attribute menopause;
	    private  String inputMenopause;
	    private  FastVector menopauseValues = new FastVector(3);
	 
	    private  Attribute tumor_size;
	    private  String inputTumor_size;
	    private  FastVector tumor_sizeValues = new FastVector(12);
	 
	    private  Attribute inv_nodes;
	    private  String inputInv_nodes;
	    private  FastVector inv_nodesValues = new FastVector(13);
	    
	    private  Attribute node_caps;
	    private  String inputNode_caps;
	    private  FastVector node_capsValues = new FastVector(2);
	    
	    private  Attribute deg_malig;
	    private  String inputDeg_malig;
	    private  FastVector deg_maligValues = new FastVector(3);
	    
	    private  Attribute breast;
	    private  String inputBreast;
	    private  FastVector breastValues = new FastVector(2);
	    
	    private  Attribute breast_quad;
	    private  String inputBreast_quad;
	    private  FastVector breast_quadValues = new FastVector(5);
	    
	    private  Attribute irradiat;
	    private  String inputIrradiat;
	    private  FastVector irradiatValues = new FastVector(2);
	    
	public Tester(String inAge, String inMenopause,String inTumor_size, String inInv_nodes,String inNode_caps,String inDeg_malig,String inbreast,String inbreast_quad,String inirradiat  ) {
		this.inputAge = inAge;
		this.inputMenopause = inMenopause;
		this.inputTumor_size = inTumor_size;
    	this.inputInv_nodes = inInv_nodes;
    	this.inputNode_caps = inNode_caps;
    	this.inputDeg_malig = inDeg_malig;
    	this.inputBreast = inbreast;
    	this.inputBreast_quad = inbreast_quad;
    	this.inputIrradiat =inirradiat;
	}
	    
    public String test() throws Exception {
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);
      //  System.out.print("\n Original Data: \n"+ dataset);
        Filter filter = new Normalize();

        // divide dataset to train dataset 90% and test dataset 10%
        int trainSize = (int) Math.round(dataset.numInstances() * 0.92);
        // System.out.println("trainSize:" + trainSize);
       
        int testSize = dataset.numInstances() - trainSize;
        // System.out.println("testSize:" + testSize);
     
        // Better Accuracy
        dataset.randomize(new Debug.Random(1));
        
        //Normalize dataset
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
     //   System.out.println("traindataset:\n"+traindataset);
        
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);
     //   System.out.println("testdataset:\n"+testdataset);
        
        // build classifier with train dataset             
        MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);

        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);

        //Save model 
        mg.saveModel(ann, MODELPATH);

        //Classify a single instance 
        	attributes = new ArrayList();
        	classValue = new ArrayList();
        	
        	classValue.add("no-recurrence-events");
        	classValue.add("recurrence-events");
        	
        	attributes.add(datasetnor.attribute(0));
        	attributes.add(datasetnor.attribute(1));
        	attributes.add(datasetnor.attribute(2));
        	attributes.add(datasetnor.attribute(3));
        	attributes.add(datasetnor.attribute(4));
        	attributes.add(datasetnor.attribute(5));
        	attributes.add(datasetnor.attribute(6));
        	attributes.add(datasetnor.attribute(7));
        	attributes.add(datasetnor.attribute(8));
        	attributes.add(new Attribute("class",classValue));
        	
        	String classname = "Not Classified";
        	rawData = new Instances("TestInstances", attributes, 0);
        	rawData.setClassIndex(rawData.numAttributes() - 1);
        	//System.out.println("\nRaw Data:\n"+rawData);
        	
        	singleInstance = dataset.lastInstance();
        	//System.out.println("\nSingle Instance:\n"+ singleInstance);
        	
        	rawData.add(singleInstance);
        	//System.out.println("\nNew Raw Data:\n"+rawData);
        	
        	//System.out.println("\nChecking returned Value:" + datasetnor.attribute(1));
        	//System.out.println("\nChecking returned Value:" + datasetnor.enumerateAttributes());
        	//System.out.println("\nChecking if nominal attribute:" + datasetnor.attribute(1).isNominal());
        	ageValues.addElement("10-19");
        	ageValues.addElement("20-29");
        	ageValues.addElement("30-39");
        	ageValues.addElement("40-49");
        	ageValues.addElement("50-59");
        	ageValues.addElement("60-69");
        	ageValues.addElement("70-79");
        	ageValues.addElement("80-89");
        	ageValues.addElement("90-99");
        	Attribute age = new Attribute("age",ageValues);
        	
        	menopauseValues.addElement("lt40");
        	menopauseValues.addElement("ge40");
        	menopauseValues.addElement("premeno");
        	Attribute menopause = new Attribute("menopause",menopauseValues);
        	
        	tumor_sizeValues.addElement("0-4");
        	tumor_sizeValues.addElement("5-9");
        	tumor_sizeValues.addElement("10-14");
        	tumor_sizeValues.addElement("15-19");
        	tumor_sizeValues.addElement("20-24");
        	tumor_sizeValues.addElement("25-29");
        	tumor_sizeValues.addElement("30-34");
        	tumor_sizeValues.addElement("35-39");
        	tumor_sizeValues.addElement("40-44");
        	tumor_sizeValues.addElement("45-49");
        	tumor_sizeValues.addElement("50-54");
        	tumor_sizeValues.addElement("55-59");
        	Attribute tumor_size = new Attribute("tumor-size",tumor_sizeValues);
        	
        	inv_nodesValues.addElement("0-2");
        	inv_nodesValues.addElement("3-5");
        	inv_nodesValues.addElement("6-8");
        	inv_nodesValues.addElement("9-11");
        	inv_nodesValues.addElement("12-14");
        	inv_nodesValues.addElement("15-17");
        	inv_nodesValues.addElement("18-20");
        	inv_nodesValues.addElement("21-23");
        	inv_nodesValues.addElement("24-26");
        	inv_nodesValues.addElement("27-29");
        	inv_nodesValues.addElement("30-32");
        	inv_nodesValues.addElement("33-35");
        	inv_nodesValues.addElement("36-39");
        	Attribute inv_nodes = new Attribute("inv-nodes",inv_nodesValues);
        	
        	node_capsValues.addElement("yes");
        	node_capsValues.addElement("no");
        	Attribute node_caps = new Attribute("node-caps",node_capsValues);
        	

        	deg_maligValues.addElement("1");
        	deg_maligValues.addElement("2");
        	deg_maligValues.addElement("3");
        	Attribute deg_malig = new Attribute("deg-malig",deg_maligValues);
        	
        	breastValues.addElement("left");
        	breastValues.addElement("right");
        	Attribute breast = new Attribute("breast",breastValues);
        	
        	breast_quadValues.addElement("left_up");
        	breast_quadValues.addElement("left_low");
        	breast_quadValues.addElement("right_up");
        	breast_quadValues.addElement("right_low");
        	breast_quadValues.addElement("central");
        	Attribute breast_quad = new Attribute("breast-quad",breast_quadValues);
        	
        	irradiatValues.addElement("yes");
        	irradiatValues.addElement("no");
        	Attribute irradiat = new Attribute("irradiat",irradiatValues);
        	
        	      	
        	singleInstance.setValue(rawData.attribute(0),inputAge);
        	singleInstance.setValue(rawData.attribute(1),inputMenopause);
        	singleInstance.setValue(rawData.attribute(2),inputTumor_size);
        	singleInstance.setValue(rawData.attribute(3),inputInv_nodes);
        	singleInstance.setValue(rawData.attribute(4),inputNode_caps);
        	singleInstance.setValue(rawData.attribute(5),inputDeg_malig);
        	singleInstance.setValue(rawData.attribute(6),inputBreast);
        	singleInstance.setValue(rawData.attribute(7),inputBreast_quad);
        	singleInstance.setValue(rawData.attribute(8),inputIrradiat);
        	
        	rawData.clear();
        	rawData.add(singleInstance);
        //	System.out.println("\nNew Updated Raw Data:\n"+rawData);
        	
          Classifier cls = null;
          cls = (MultilayerPerceptron) SerializationHelper.read(MODELPATH);
          System.out.println("Evaluating Request..");
          insts = Filter.useFilter(rawData, filter);
          classname = classValue.get((int) cls.classifyInstance(insts.firstInstance()));
         // System.out.println("\nThe class for the given instance is  "+ classname );
          return classname;

    }

}
