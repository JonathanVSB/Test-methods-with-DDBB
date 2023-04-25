/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.Main;

import storedb.Controllers.StoreController;
import storedb.model.StoreModel;

/**
 *
 * @author dax
 */
public class MainMvc {

    public static void main( String[] args) {
        
        StoreModel model = new StoreModel();
        
        StoreController controller = new StoreController(model);
        //start
        
        controller.start();
    }
    
    
    
}
