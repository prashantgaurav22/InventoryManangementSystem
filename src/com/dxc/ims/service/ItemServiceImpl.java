package com.dxc.ims.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dxc.ims.dao.ItemDAO;
import com.dxc.ims.dao.ItemDaoJpaImpl;
import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;

public class ItemServiceImpl implements ItemService {

	private ItemDAO itemDAO;

	public ItemServiceImpl() throws InventoryException {
		// this.itemDAO = new ItemDAOImpl();
		// this.itemDAO = new ItemDaoJdbcImpl();
		this.itemDAO = new ItemDaoJpaImpl();
	}

	/*
	 * icode can not be zero or negative, icode should be unique
	 */
	private boolean isValidIcode(int icode) throws InventoryException {
		return icode > 0 && (itemDAO.getItemByIcode(icode) == null);
	}

	/*
	 * title can not null, can not be empty, min of 5 chars and max of 20 chars
	 */
	private boolean isValidTitle(String title) {
		return title != null && title.length() > 5 && title.length() < 20;
	}

	/*
	 * price can not be zero or negative
	 */
	private boolean isValidPrice(double price) {
		return price > 0;
	}

	/*
	 * packageDate can not be a future date.
	 */
	private boolean isValidPackageDate(LocalDate packageDate) {
		LocalDate today = LocalDate.now();
		// return packageDate.isBefore(today) || packageDate.equals(today);
		return !packageDate.isAfter(today);
	}

	private boolean isValidItem(Item item) throws InventoryException {

		boolean isValid = true;

		if (item == null) {
			isValid = false;
			throw new InventoryException("Item can not null");
		}

		List<String> errMsgs = new ArrayList<String>();

		if (!isValidIcode(item.getIcode())) {
			errMsgs.add("Err: Icode can be zero or negative, Icode can not be repetative.");
		}
		if (!isValidTitle(item.getTitle())) {
			errMsgs.add("Err: Title can not be blank, and must be of 5 to 20 chars in length.");
		}
		if (!isValidPrice(item.getPrice())) {
			errMsgs.add("Err: Price can be zero or negative.");
		}
		if (!isValidPackageDate(item.getPackageDate())) {
			errMsgs.add("Err: Package Date can not be a future date.");
		}

		if (errMsgs.size() > 0) {
			isValid = false;
			throw new InventoryException(errMsgs.toString());
		}

		return isValid;
	}

	@Override
	public void addItem(Item item) throws InventoryException {
		if (isValidItem(item)) {
			itemDAO.addItem(item);
		}
	}

	@Override
	public void deleteItem(int icode) throws InventoryException {
		itemDAO.deleteItem(icode);
	}

	@Override
	public List<Item> getAllItems() throws InventoryException {
		return itemDAO.getAllItems();
	}

	@Override
	public Item getItemByIcode(int icode) throws InventoryException {
		return itemDAO.getItemByIcode(icode);
	}

}