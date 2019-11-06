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
            String file = Functions.getDirectoryForMining() + "students-attributes.arff";
            BufferedReader datafile = readDataFile(file); 
            Instances data = new Instances(datafile);
            data.randomize(new Random(100));
            data.setClass(data.attribute("status"));
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
            
            //OUTPUT
            //line 1 - lines.length-3: tree
            //lines.length-2: number of instances classified correctly
            //lines.length-1: percentage of instances classified correctly
            /*
            //System.out.println("Rules");
            //System.out.println(apriori.getNumRules());
            FastVector[] rules = apriori.getAllTheRules();
            
            Instances instances = apriori.getInstancesNoClass();
                        
            //https://danepiyush.wordpress.com/2014/05/28/how-to-get-associations-rules-programmatically-from-weka-aproiri-object/
            
            FastVector premises = rules[0];
            FastVector consequences = rules[1];
            FastVector confidences = rules[2];
            
            //armazena as regras de associação sem duplicidade
            ArrayList<String> uniqueRules = new ArrayList<String>();
            //armazena as mesmas regras de associação sem duplicidade, porém armazena como lista
            ArrayList<HashMap<String, Object>> listUniqueRules = new ArrayList<HashMap<String, Object>>();
            
            for(int i=0; i < premises.size(); i++){
                //System.out.println("Nova regra");
                
                double confidence = Double.parseDouble(confidences.elementAt(i).toString());
                
                ItemSet itemPremise = (ItemSet)premises.elementAt(i);
                
                ArrayList<String> listBefore = new ArrayList<String>();
                ArrayList<String> listAfter = new ArrayList<String>();                
                
                for(int j=0; j < itemPremise.items().length; j++){
                    if (j < instances.numAttributes()){
                        Attribute attribute = instances.attribute(j);
                        int k = itemPremise.itemAt(j);
                        if (k != -1){
                            listBefore.add(attribute.name());
                        }
                    }
                }
                
                ItemSet itemConsequence = (ItemSet)consequences.elementAt(i);
                
                for(int j=0; j < itemConsequence.items().length; j++){
                    if (j < instances.numAttributes()){
                        Attribute attribute = instances.attribute(j);
                        int k = itemConsequence.itemAt(j);
                        if (k != -1){
                            listAfter.add(attribute.name());
                        }
                    }
                }
                
                //System.out.println(listBefore + " => " + listAfter + " (accuracy: "+confidence+")");
                
                //concatena os ítens das regras de associação em uma única lista
                ArrayList<String> listItems = new ArrayList<String>();                                
                for(String item : listBefore){
                    listItems.add(item);
                }
                for(String item : listAfter){
                    listItems.add(item);
                }
                
                //concatena os ítens das regras de associação com um separador
                Collections.sort(listItems);                
                StringBuilder builder = new StringBuilder();
                for(String item : listItems){
                    builder.append(item.concat(";"));
                }
                
                //armazena a regra somente se já não existir na lista uniqueRules
                String rule = builder.toString();
                if (!uniqueRules.contains(rule)){
                    uniqueRules.add(rule);
                    
                    HashMap<String, Object> newRule = new HashMap<String, Object>();
                    newRule.put("items", listItems);
                    newRule.put("confidence", confidence);
                    
                    listUniqueRules.add(newRule);
                }                
            }            
            
            //Imprime as regras como JSON
            JSONArray obj = new JSONArray(listUniqueRules);            
            System.out.println(obj.toString());            
            */
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
