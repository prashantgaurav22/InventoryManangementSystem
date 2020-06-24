package com.dxc.ims.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;
/*
 * this class is responsiible to store and retrieve items from a file
 * 
 */

public class ItemDAOImpl implements ItemDAO {

	private static final String DATA_FILE_NAME = "itemsData.dat";//file name to store data
	
	private Map<Integer, Item> itemsMap;//map will store all items
	/*
	 * This constructor will load data if the file is already exists,
	 * else the empty map is initalized
	 * 
	 */
	public ItemDAOImpl() throws InventoryException {
		File file =new File(DATA_FILE_NAME);
		if(file.exists()) {
			try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))){
				itemsMap = (Map<Integer, Item>)  oin.readObject();
			}
			catch(IOException | ClassNotFoundException exp){
				itemsMap = new TreeMap<>();
				throw new InventoryException("Unable to read data");
				
			}
		}
		else {
			itemsMap = new TreeMap<>();
		}
		
	}
	/*
	 * saveData() mathod will save all the map into file 
	 */
	private void saveData() throws InventoryException{
		try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))){
			oout.writeObject(itemsMap);
		}
		catch(IOException exp){
			itemsMap = new TreeMap<>();
			throw new InventoryException("Unable to save data");
			
		}
	}
	/*
	 * additem methid will accept a item and store in map
	 * icode will be the key and item will be value.
	 * if item is null it will throw an InventoryException.
	 */
	
	
	@Override
	public void addItem(Item item) throws InventoryException {
		if(item!=null) {
			itemsMap.put(item.getIcode(), item);
			saveData();
		}
		else {
			throw new InventoryException("Null item cannot be stored");
		}

	}
	/*
	 * deleteitem will acceppt the icode and removes that item from map and saves.
	 * if icode is found it will throw an InventoryException
	 */

	@Override
	public void deleteItem(int icode) throws InventoryException {
		if(itemsMap.containsKey(icode)) {
			itemsMap.remove(icode);
		}
		else {
			throw new InventoryException("Item#" + icode + "Not Found");
		}

	}
	/*
	 * getAllItems will return alll the items from the map as a list. 
	 */
	@Override
	public List<Item> getAllItems() {
		return new ArrayList<>(itemsMap.values());
	}

	/* 
	 *  getItemByIcode will return the items respective to the given icode. 
	 * if not found return null
	 */
	@Override
	public Item getItemByIcode(int icode) {
		return itemsMap.get(icode);
	}

}
