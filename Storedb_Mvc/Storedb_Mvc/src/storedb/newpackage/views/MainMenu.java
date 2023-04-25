/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.newpackage.views;

import cat.proven.menu.Menu;
import cat.proven.menu.Option;

/**
 *
 * @author Jonathan Segura
 */
public class MainMenu extends Menu{

    public MainMenu() {
        
        title = "Store Menu";
        addOption(new Option("Exit","exit"));
        addOption(new Option("List all categories","category/all"));
        addOption(new Option("Search category by code", "category/code"));
        addOption(new Option ("Add new category", "category/add"));
        addOption(new Option ("Modify category", "category/modify"));
        addOption(new Option ("Remove category", "category/remove"));
        //
        
        addOption(new Option("List all products","products/all"));
        addOption(new Option("Search product by code", "product/code"));
        addOption(new Option ("Search product by name", "product/name"));
        addOption(new Option ("Add new product", "product/add"));
        addOption(new Option ("Remove product", "product/remove"));
        addOption(new Option ("Modify product", "product/modify"));
        
        //TODO
        addOption(new Option ("Search product by category", "product/category"));
        addOption(new Option ("Search product by minim stock", "product/stockmin"));
        
    }
    
    
    
    
}
