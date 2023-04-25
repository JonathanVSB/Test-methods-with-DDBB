/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import storedb.DbConnect.DbConnect;
import storedb.model.Category;
import storedb.model.exceptions.StoreDaException;

/**
 * Data Access Object for category table
 *
 * @author ProvenSoft
 */
public class CategoryDao {

    private final DbConnect dbConnect;
    private Map<String, String> queries;

    public CategoryDao() {
        this.dbConnect = new DbConnect();
        this.queries = new HashMap<>();
        initQueries();
    }
    
    /**
     * Build a category
     * @param rs
     * @return
     * @throws SQLException 
     */

    private Category fromResultSet(ResultSet rs) throws SQLException {
        Category cat;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String name = rs.getString("name");
        cat = new Category(id, code, name);
        return cat;
    }

    /**
     * inserts a new category to database
     *
     * @param category
     * @return
     */
    public int insert(Category category) throws StoreDaException {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = 
                    "insert into categories values (null, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, category.getCode());
            st.setString(2, category.getName());
            result = st.executeUpdate();
        } catch (SQLException ex) {

            throw new StoreDaException("Integrity Constraint Violation", -24);
        }
        return result;
    }

    public int update(Category actualCategory, Category updatedCategory) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = """
                           update categories set 
                           code=?, name=?  
                           where id=?
                           """;
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, updatedCategory.getCode());
            st.setString(2, updatedCategory.getName());
            st.setLong(3, actualCategory.getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Category select(Category category) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, category.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    /**
     * Search Category by his code
     *
     * @param code
     * @return
     */
    public Category selectWhereCode(String code) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public Category selectWhereId(String id) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    /**
     * remove Category from database
     *
     * @param code
     * @return 1 if category is dropped, 0 if not
     */
    public int dropCategory(String code) {
        int result = 0;

        try ( Connection conn = dbConnect.getConnection()) {
            String query = "delete from categories where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            st.executeUpdate();
            result = 1;
            //ResultSet rs = st.executeQuery();

        } catch (SQLException ex) {
            result = 0;
        }

        return result;

    }

    public List<Category> selectAll() throws StoreDaException {
        List<Category> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = getQuery("sAll");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category cat = fromResultSet(rs);
                if (cat != null) {
                    result.add(cat);
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new StoreDaException("no connection", -10);

        }
        return result;
    }

    private void initQueries() {
        queries.put("sAll", "select * from categories");
        //TODO add all queries
    }

    private String getQuery(String queryKey) {
        return queries.get(queryKey);
        
    }

}
