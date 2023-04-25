/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import storedb.DAO.CategoryDao;
import storedb.DAO.ProductDao;
import storedb.model.exceptions.StoreDaException;

/**
 *
 * @author Jonathan Segura
 */
public class StoreModel {

    private final CategoryDao categorydao;
    private final ProductDao productdao;

    public StoreModel() {
        this.categorydao = new CategoryDao();
        this.productdao = new ProductDao();
    }

    /**
     * adds nre category to database if prevents adding null categories or null
     * codes also prevents code duplicates.
     *
     * @param category the category to add
     * @return 1 if succesfull. 0 otherwise
     */
    public int addCategory(Category category) throws StoreDaException {

        int result = 0;
        //si la category no esta correcta
        if (category != null) {
            //si el codigo es valido
            if (category.getCode() != null) {
                //si este código no existe. para que no se repita
                Category c = categorydao.select(category);
                if (c == null) {
                    result = categorydao.insert(category);

                }
                //SQLIntegrityConstraintViolationException:

            }

        }
        return result;

    }

    /**
     * adds new Product to database if prevents adding null products or null
     * codes also prevents code duplicates.
     *
     * @param product the category to add
     * @return 1 if succesfull. 0 otherwise
     */
    public int addProduct(Product product) throws StoreDaException {

        int result = 0;
        //si el product ya no esta correcta
        if (product != null) {
            //si el codigo es valido
            if (product.getCode() != null) {
                //si este código no existe. para que no se repita
                Product c = productdao.select(product);
                if (c == null) {
                    result = productdao.insert(product);

                }

            }

        }
        return result;

    }

    /**
     * find all categories in database
     *
     * @return list with all categories or null in case of error
     */
    public List<Category> findAllCategories() throws StoreDaException {
        return categorydao.selectAll();

    }

    /**
     * find a category given its code
     *
     * @param code thhe code to search
     * @return the product with given code or null if not found or in case of
     */
    public Category findCategoryByCode(String code) {
        Category result = null;
        if (code != null) {
            result = categorydao.selectWhereCode(code);
        }
        return result;
    }

    /**
     * find a product given its code
     *
     * @param code thhe code to search
     * @return the product with given code or null if not found or in case of
     */
    public Product findProductByCode(String code) {
        Product result = null;
        if (code != null) {
            result = productdao.selectWhereCode(code);
        }
        return result;
    }

    /**
     * Search product by Category id
     *
     * @param code
     * @return products or null
     */
    public List<Product> findProductByCategory(String code) {
        Category result = null;
        List<Product> prod = null;
        
        if (code != null) {
            result = categorydao.selectWhereCode(code);
            if (result != null) {
                prod = productdao.selectWhereCategory(result);
            } else {
                prod = null;
            }
        }

        return prod;
    }

    /**
     * find all products in database
     *
     * @return lis all categories or null in case of error
     */
    public List<Product> findAllProducts() {
        return productdao.selectAll();

    }

    /**
     * Rturns lis of products or nul if not find any matches
     *
     * @param name
     * @return
     */
    public List<Product> findProductsByName(String name) {
        List<Product> products = null;

        try {
            products = productdao.selectLikeName(name);
        } catch (StoreDaException ex) {
            //Logger.getLogger(StoreModel.class.getName()).log(Level.SEVERE, null, ex);
            products = null;
        }
        return products;

    }

    /**
     * modify category
     *
     * @param actualCategory category to be modified
     * @param updatedCategory new category
     * @return 1 if statement succes, 0 if not
     */
    public int modifyCategory(Category actualCategory, Category updatedCategory) {

        return categorydao.update(actualCategory, updatedCategory);
    }

    /**
     * Remove category from data base
     *
     * @param code of the categpry to be erased
     * @return 1 if statement succes, 0 if not
     */
    public int removeCategory(String code) {
        return categorydao.dropCategory(code);
    }

    public int removeProduct(String code) {
        return productdao.dropProduct(code);
    }

    public int modifyProduct(Product actualProduct, Product updatedProduct) {
        return productdao.update(actualProduct, updatedProduct);

    }

    /**
     * Search product order bay minimun stock
     */
    public List<Product> findProductByMinStock(int num) {

        return productdao.selectWhereMinStock(num);
    }
}
