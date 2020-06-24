package com.dxc.ims.dao;

 

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;

 

public class ItemDaoJdbcImpl implements ItemDAO {

 

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException exp) {
            System.out.println(exp.getMessage());
            System.exit(0);
        }
    }
    
    private static final Logger logger = Logger.getLogger("ItemDAO");
    private static final String dbUrl = "jdbc:mysql://localhost:3306/dxcbatch";
    private static final String dbUnm = "root";
    private static final String dbPwd = "Summu_889";
    
    private static final String INS_ITEM_QRY=
            "INSERT INTO myitems(icode,title,price,pdate) VALUES(?,?,?,?)";
    private static final String DEL_ITEM_QRY=
            "DELETE FROM myitems WHERE icode=?";
    private static final String SEL_ALL_ITEMS_QRY=
            //rdr
    		"SELECT icode,title,price,pdate FROM myitems";
    private static final String SEL_ITEM_BU_ICODE_QRY=
            "SELECT icode,title,price,pdate FROM myitems WHERE icode=?";
    
    @Override
    public void addItem(Item item) throws InventoryException {
        if(item!=null) {
            try(Connection con = DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
                PreparedStatement pinsert = con.prepareStatement(INS_ITEM_QRY);
                
                pinsert.setInt(1,item.getIcode());
                pinsert.setString(2, item.getTitle());
                pinsert.setDouble(3, item.getPrice());
                pinsert.setDate(4, Date.valueOf(item.getPackageDate())); //java.time.LoclaDate into java.sql.Date
                
                pinsert.executeUpdate();
                
                logger.info("item added successfully!");
            }catch(SQLException exp) {
            	logger.error(exp.toString());
                throw new InventoryException("Sorry! Add Item Operation Failed!"); 
            }
        }
    }

 

    @Override
    public void deleteItem(int icode) throws InventoryException {
        try(Connection con = DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
            PreparedStatement pdelete = con.prepareStatement(DEL_ITEM_QRY);
            
            pdelete.setInt(1,icode);
            
            pdelete.executeUpdate();
            logger.info("item deleted successfully!");
        }catch(SQLException exp) {
        	logger.error(exp.toString());
            throw new InventoryException("Sorry! Delete Item Operation Failed!"); 
        }
    }

 

    @Override
    public List<Item> getAllItems() throws InventoryException {
        List<Item> items =new ArrayList<Item>();
        
        try(Connection con = DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
            PreparedStatement pselect = con.prepareStatement(SEL_ALL_ITEMS_QRY);
            
            ResultSet rs = pselect.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setIcode(rs.getInt(1));
                item.setTitle(rs.getString(2));
                item.setPrice(rs.getDouble(3));
                item.setPackageDate(rs.getDate(4).toLocalDate());
                
                items.add(item);
            }
            logger.info("item displayed successfully!");
        }catch(SQLException exp) {
        	logger.error(exp.toString());
            throw new InventoryException("Sorry! Could Not Retrive Data!"); 
        }
        return items;
    }

 

    @Override
    public Item getItemByIcode(int icode) throws InventoryException {
        Item item =null;
        
        try(Connection con = DriverManager.getConnection(dbUrl,dbUnm,dbPwd)){
            PreparedStatement pselect = con.prepareStatement(SEL_ITEM_BU_ICODE_QRY);
            
            pselect.setInt(1,icode);
            
            ResultSet rs = pselect.executeQuery();
            
            if(rs.next()) {
                item = new Item();
                item.setIcode(rs.getInt(1));
                item.setTitle(rs.getString(2));
                item.setPrice(rs.getDouble(3));
                item.setPackageDate(rs.getDate(4).toLocalDate());
            }
            
            logger.info("Item displayed successfully");
            
        }catch(SQLException exp) {
            throw new InventoryException("Sorry! Could Not Retrive Data!"); 
        }

        return item;
    }

 

}