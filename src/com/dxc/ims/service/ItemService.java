package com.dxc.ims.service;

import java.util.List;

import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;

public interface ItemService {

	void addItem(Item item) throws InventoryException;

	void deleteItem(int icode) throws InventoryException;

	List<Item> getAllItems() throws InventoryException;

	Item getItemByIcode(int icode) throws InventoryException;

}