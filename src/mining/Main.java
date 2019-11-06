/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining;

/**
 *
 * @author leandro
 */
public class Main {
    public static void main(String[] args) {
        
        if (args[0].equalsIgnoreCase("classify")){
            Classify.main(args);
        }
        
    }
}
