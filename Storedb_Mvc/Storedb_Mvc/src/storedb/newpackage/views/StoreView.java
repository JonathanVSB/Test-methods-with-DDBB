/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.newpackage.views;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Scanner;
import storedb.Controllers.StoreController;
import storedb.model.Category;
import storedb.model.Product;
import storedb.model.StoreModel;

/**
 *
 * @author dax
 */
public class StoreView {

    private final StoreController controller;
    private final StoreModel model;

    private MainMenu mainMenu;
    private boolean exit;

    public StoreView(StoreController controller, StoreModel model) {
        this.controller = controller;
        this.model = model;
        this.mainMenu = new MainMenu();
    }

    /**
     * display MainMenu to interact and collects the option selected
     */
    public void display() {

        exit = false;
        do {

            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            controller.processAction(action);

        } while (!exit);
    }

    /**
     * show the user the massage of @param collects the String answer of the
     * user
     *
     * @param message
     * @return String introduced by user
     */
    public String inputString(String message) {
        System.out.println(message);

        Scanner entrada = new Scanner(System.in);
        return entrada.next();

    }

    
   

    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * close the view
     */
    public void close() {

        this.exit = true;
    }

    /**
     * display list of data
     *
     * @param <T>
     * @param data
     */
    public <T> void displayList(List<T> data) {
        if (data != null) {
            data.forEach(System.out::println);
            System.out.format("%d elements displayed\n ", data.size());

        }

    }

    /**
     * asks user for the category data
     *
     * @return
     */
    public Category inputCategory() {
        String code = inputString("insert the new code: ");
        String name = inputString("insert the new name: ");
        Category category = new Category(0, code, name);
        return category;
    }

    /**
     * Display the product
     *
     * @param product
     */
    public void displayProduct(Product product) {
        System.out.println(product);
    }

    /**
     * display the category
     *
     * @param category
     */
    public void displayCategory(Category category) {
        System.out.println(category);
    }

    /**
     * ask to user the data to introduce a product in database
     * checks if int and double data are in the correct format
     * prevents code coincidences in database.
     *
     * @return the new product inserted
     */
    public Product inputProduct() {
        Scanner entrada = new Scanner(System.in);
        Category cat = null;
        boolean validate = false;

        String code = inputString("New Product code:");

        String name = inputString("New Product name:");

        

        int stock = validateInt();

        
        double price = validateDouble();

        String categorycode = inputString("Add New Category code:");

        cat = model.findCategoryByCode(categorycode);
        if (cat != null) {
            Product product = new Product(0, code, name, stock, price, cat);

            return product;
        } else {

            return null;
        }
    }
    /**
     * Validates the data introduced by user
     * if the data format is numeric retuns the number
     * else informs the user and ask again for the data.
     * @return Int number introduced by user
     */

    public int validateInt() {

        boolean validar = false;
        int num = 0;

        while (!validar) {
            // gets the data as String.
            String data = inputString("New Product stock: ");

            try {
                
                //if the String data can be parsed to int the number is correct
                num = Integer.parseInt(data);
                validar = true;
            } catch (NumberFormatException nfe) {
                
                //else return false and ask again for te data
                displayMessage("=======================\n"
                        + "Data type is incorrect");
                validar = false;
            }

        }

        return num;

    }
    
    /**
     * Validates the data introduced by user
     * if the data format is numeric retuns the number
     * else informs the user and ask again for the data.
     * @return Double number introduced by user
     */

    public double validateDouble() {

        boolean validar = false;
        double num = 0.0;

        while (!validar) {
            // gets the data as String.
            String data = inputString("New Product price:");
            
            
            try {
                
                //if the String data can be parsed to int the number is correct
                num = Double.parseDouble(data);
                validar = true;
            } catch (NumberFormatException nfe) {
                
                //else return false and ask again for te data
                displayMessage("=======================\n"
                        + "Data type is incorrect");
                validar = false;
            }

        }

        return num;

    }

}
