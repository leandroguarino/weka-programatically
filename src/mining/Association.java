/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import static mining.Classify.readDataFile;
import org.json.JSONArray;
import weka.associations.Apriori;
import weka.associations.ItemSet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

/**
 *
 * @author leandro
 */
public class Association {
    public static void main(String[] args) {
        try{
            Apriori apriori = new Apriori();
            String file = Functions.getDirectoryForMining() + "supermarket.arff";
            BufferedReader datafile = readDataFile(file); 
            Instances data = new Instances(datafile);
            data.randomize(new Random(100));
            apriori.buildAssociations(data);
            //OUTPUT
            //line 1 - lines.length-3: tree
            //lines.length-2: number of instances classified correctly
            //lines.length-1: percentage of instances classified correctly
            //System.out.println("Rules");
            //System.out.println(apriori.getNumRules());
            FastVector[] rules = apriori.getAllTheRules();

            Instances instances = apriori.getInstancesNoClass();

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
        }catch(Exception ex){
    
        }
    
    }    
}
