/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining;

import java.lang.reflect.Field;

/**
 *
 * @author leandro
 */
public class Functions {
    public static String getDirectoryForMining(){
        return "C:\\xampp7\\htdocs\\fatec\\evasao-mineracao\\";
    }
    
    public String toString(Object object) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( object.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = object.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
          result.append("  ");
          try {
            result.append( field.getName() );
            result.append(": ");
            //requires access to private field:
            result.append( field.get(object) );
          } catch ( IllegalAccessException ex ) {
            System.out.println(ex);
          }
          result.append(newLine);
        }
        result.append("}");

        return result.toString();
      }
}
