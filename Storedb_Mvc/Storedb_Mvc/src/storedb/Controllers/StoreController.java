/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import storedb.model.Category;
import storedb.model.Product;
import storedb.model.StoreModel;
import storedb.model.exceptions.StoreDaException;
import storedb.newpackage.views.StoreView;

/**
 *
 * @author dax
 */
public class StoreController {

    private StoreModel model;
    private StoreView view;

    public StoreController(StoreModel model) {
        this.model = model;
        this.view = new StoreView(this, model);

    }

    public void start() {
        view.display();

    }

    public void processAction(String action) {
        System.out.println("Processing action " + action);
        if (action != null) {
            switch (action) {
                case "exit":
                    exitAplication();
                    break;

                case "category/all":
                    listAllCategories();
                    break;

                case "category/code":// search category by code
                    listCategoryByCode();
                    break;

                case "category/add":
                    addCategory();
                    break;

                case "category/modify"://modify category
                    modifyCategory();
                    break;

                case "category/remove"://remove category
                    removeCategory();
                    break;

//========================== Product Options ==================
                case "products/all":// lista all products
                    listAllProducts();
                    break;

                case "product/code":
                    listProductsByCode();
                    break;

                case "product/name"://search product by name
                    listProductByName();
                    break;

                case "product/add"://Add new product
                    addProduct();
                    break;

                case "product/modify":
                    modifyProduct();
                    break;

                case "product/remove":
                    removeProduct();
                    break;
                    
                case "product/category":
                    listProductsByCategory();
                    break;  
                    
                case "product/stockmin":
                    searchByMinStock();
                    break;
                    
                default:
                    view.displayMessage("Action not suported");
                    break;

            }

        }
    }

    /**
     * ask for confirmation an if so, exists aplication
     */
    public void exitAplication() {

        String answer = view.inputString("sure to exit? Yes/No");
        if (answer.equalsIgnoreCase("yes")) {
            view.close();

        }

    }

    /**
     * get all categories from database and display them
     */
    private void listAllCategories() {
        try {
            //retrieve all categories
            List<Category> data = model.findAllCategories();

            //display result
            if (data != null) {
                view.displayList(data);

            } else {

                view.displayMessage("Null data");
            }
        } catch (StoreDaException ex) {
            Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error retrieving data.");
        }
    }

    /**
     * Add new category if correctly read, add category to database prevent code
     * duplicates, null objects, null codes and report result to user.
     */
    private void addCategory() {
        //test insert a new category
        //test insert a new category
        view.displayMessage("Insert a new category");
        Category category = view.inputCategory();

        if (category != null) {
            try {
                int result = model.addCategory(category);

                String resultMsg = (result == 1) ? "Category added" : "Category not added";
                view.displayMessage(resultMsg);
//                
            } catch (StoreDaException ex) {
                
                view.displayMessage("===================================\n"
                        + "This category code is already used");
            }
        } else {
            view.displayMessage("Error reading data");
        }

    }

    /**
     * List all products
     */
    private void listAllProducts() {
        List<Product> products = new ArrayList();

        try {

            products = model.findAllProducts();

        } catch (Exception ex) {

        }

        if (products != null) {
            view.displayList(products);

        } else {
            view.displayMessage("Products not found");
        }

    }

    private void listProductsByCode() {
        //Read code
        String code = view.inputString("input product code: ");
        if (code != null) {
            Product found = model.findProductByCode(code);
            if (found != null) {
                //display product
                view.displayProduct(found);
            } else {
                view.displayMessage("Product not found");
            }

        } else {
            view.displayMessage("Error reading code");
        }

    }

    /**
     * ask user de code to search if correctly read, gets the category with that
     * code returns result to user
     */
    private void listCategoryByCode() {
        //Read code
        String code = view.inputString("input category code: ");
        if (code != null) {
            Category found = model.findCategoryByCode(code);
            if (found != null) {
                //display product
                view.displayCategory(found);
            } else {
                view.displayMessage("Category not found");
            }

        } else {
            view.displayMessage("Error reading code");
        }

    }

    private void modifyCategory() {
        //test update a category

        view.displayMessage("Update a category");

        //long id = inputLong("Category id (of category to be updated): ");
        String actual_code = view.inputString("Category code(of category to be updated):");
        Category actualCategory = model.findCategoryByCode(actual_code);
        if (actualCategory != null) {
            view.displayCategory(actualCategory);
            String respuesta = view.inputString("Is this the correct category?Y/N");
            if (respuesta.equalsIgnoreCase("Y")) {

                Category updatedCategory = view.inputCategory();

                int result = model.modifyCategory(actualCategory, updatedCategory);
                String resultMsg = (result == 1) ? "Category updated" : "Category not updated";

                view.displayMessage(resultMsg);

            } else if (respuesta.equalsIgnoreCase("N")) {
                view.displayMessage("Ending process. Not category updated");
            }

        } else {

            view.displayMessage("Category not found");
        }

    }

    /**
     * remove category from database shearch category by given code if found,
     * show all products from this category and verifies the delete process with
     * the user if user accepts the delete process it will remove all catogories
     * and products on cascade.
     */
    private void removeCategory() {

        //Find category by code
        String code = view.inputString("Insert the code of the category to erase");
        Category category = model.findCategoryByCode(code);
        if (category != null) {
            view.displayCategory(category);
            String idCat = String.valueOf(category.getId());
            List<Product> products = model.findProductByCategory(idCat);
            if (products != null) {
                view.displayMessage("The category has the folowing products related");
                view.displayList(products);
                String respuesta = view.inputString("You want to continue? Y/N");
                if (respuesta.equalsIgnoreCase("Y")) {

                    for (Product var : products) {
                        model.removeProduct(var.getCode());
                    }

                    int result = model.removeCategory(code);

                    String resultMsg = (result == 1) ? "Category erased" : "Category not erased";
                    view.displayMessage(resultMsg);

                } else if (respuesta.equalsIgnoreCase("N")) {
                    view.displayMessage("Ending process. Not category removed");
                }

            } else {
                String respuesta = view.inputString("You want to continue? Y/N");
                if (respuesta.equalsIgnoreCase("Y")) {

                    int result = model.removeCategory(code);

                    String resultMsg = (result == 1) ? "Category erased" : "Category not erased";
                    view.displayMessage(resultMsg);
                } else if (respuesta.equalsIgnoreCase("N")) {
                    view.displayMessage("Ending process. Not category removed");
                }

            }

        } else {
            view.displayMessage("Category not found");
        }

    }

    /**
     * Find products by name like param
     * Gets the name introduced by user
     * Checks if than name exist and controls and empty variable
     * if the name exist, display the products
     * else informs user that this name does not exist
     * 
     * @param of name to search
     */
    private void listProductByName() {
        String name = view.inputString("Insert the name of the product:");
        List<Product> similar_products = null;

        if (name != "") {
            similar_products = model.findProductsByName(name);
            if (similar_products != null) {
                view.displayList(similar_products);

            } else {
                view.displayMessage("Not product found");

            }

        } else {
            view.displayMessage("Not name introduced");
        }

    }

    /**
     * Add new Product to database.
     * 
     */
    private void addProduct() {
        Category cat = null;
        int result = 0;
        Scanner entrada = new Scanner(System.in);
        view.displayMessage("Insert a new product");
        Product prod = view.inputProduct();
//      
//
          if (prod != null) {
                try {
                    result = model.addProduct(prod);

                String resultMsg = (result == 1) ? "Product saved" : "Product not saved";
                view.displayMessage(resultMsg);
//                
            } catch (StoreDaException ex) {
                
                view.displayMessage("===================================\n"
                        + "This product code is already in use");
            }
        } else {
            view.displayMessage("Error reading data");
        }

 }

    /**
     * modify existing Product ask user code of the Product to modify search the
     * product in database with given code if found, it asks new data for the
     * product, either wise it reports to user ask confirmation to user ask user
     * data for the product to modify if new data correctly read then, it
     * modifies the product from database it prevents code duplicates or null
     * codes...
     */
    private void modifyProduct() {
        Scanner entrada = new Scanner(System.in);
        view.displayMessage("Update a Product");

        int result = 0;

        String actual_code = view.inputString("Product code(of product to be updated):");
        Product actualProduct = model.findProductByCode(actual_code);
        if (actualProduct != null) {
            view.displayProduct(actualProduct);
            String respuesta = view.inputString("Is this the correct product?Y/N");
            if (respuesta.equalsIgnoreCase("Y")) {

                Product updatedProduct = view.inputProduct();
                if (updatedProduct != null) {
                    result = model.modifyProduct(actualProduct, updatedProduct);
                    String resultMsg = (result == 1) ? "Product updated" : "Product not updated";

                    view.displayMessage(resultMsg);
                }

            } else if (respuesta.equalsIgnoreCase("N")) {
                view.displayMessage("Ending process. Not Product updated");
            }
        } else {

            view.displayMessage("Product not found");
        }

    }

    /**
     * Remove product from list of products.
     */
    private void removeProduct() {
        String code = view.inputString("Insert code of the product to erase");

        if (model.findProductByCode(code) != null) {
            int result = model.removeProduct(code);

            String resultMsg = (result == 1) ? "Product erased" : "Product not erased";
            view.displayMessage(resultMsg);
        } else {

            view.displayMessage("No matches for this code");
        }

    }
    
    /**
     * Search products by category code
     * if found products coincicences, it shows it
     * if not informs the user
     * .
     */
    private void listProductsByCategory() {
        String code = view.inputString("Insert the code of the category");

        List<Product> products = model.findProductByCategory(code);
        
        if(products!= null)
        {
            view.displayList(products);
        }else
        {
            view.displayMessage("Not product found by this category code. Make sure the code is correct");
        }
    }
     private void searchByMinStock() {
        Scanner entrada = new Scanner(System.in);

        view.displayMessage("Enter the number to find:");

        int stock = view.validateInt();
        

        List<Product> prod = model.findProductByMinStock(stock);

        view.displayList(prod);

    }

}
