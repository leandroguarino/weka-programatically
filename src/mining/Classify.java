/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import weka.associations.ItemSet;
import weka.associations.PredictiveApriori;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 *
 * @author leandro
 */
public class Classify {

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
                inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
                System.err.println("File not found: " + filename);
        }

        return inputReader;
    }
    
    public static void main(String[] args) {
        try{
            //java -jar mining.jar classify
            String file = Functions.getDirectoryForMining() + "vote.arff";
            BufferedReader datafile = readDataFile(file); 
            Instances data = new Instances(datafile);
            data.randomize(new Random(100));
            data.setClass(data.attribute("Class"));
            // build associator
            J48 j48 = new J48();
            j48.setBinarySplits(false);
            j48.setConfidenceFactor(0.25f);
            j48.setMinNumObj(2);
            j48.setNumFolds(3);
            j48.setReducedErrorPruning(false);
            j48.setSaveInstanceData(false);
            j48.setSeed(1);
            j48.setSubtreeRaising(true);
            j48.setUnpruned(false);
            j48.setUseLaplace(false);
            j48.buildClassifier(data);
            String result = j48.toString();
            //System.out.println(j48.toString());
            String[] lines = result.split("\n");
            
            for(int i=3; i <= lines.length-5; i++){
                System.out.println(lines[i]);
            }            
            Evaluation evaluation = new Evaluation(data);
            evaluation.evaluateModel(j48, data);
            System.out.println(evaluation.correct());
            System.out.println(evaluation.pctCorrect());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
