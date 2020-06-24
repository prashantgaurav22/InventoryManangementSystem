package com.dxc.ims.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;
import com.dxc.ims.service.ItemService;
import com.dxc.ims.service.ItemServiceImpl;

/**
 * Servlet implementation class InventoryController
 */
@WebServlet({ "/viewItems", "/addItem", "/confirmAddItem", "/deleteItem" })
public class InventoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		String view = null;
		try {
			ItemService itemService = new ItemServiceImpl();
			switch (path) {
			case "/viewItems":
				view = "itemsPage.jsp";
				request.setAttribute("items", itemService.getAllItems());
				break;
			case "/addItem":
				view = "itemFormPage.jsp";
				break;
			case "/confirmAddItem":
				Item item = new Item();
				item.setIcode(Integer.parseInt(request.getParameter("icode")));
				item.setTitle(request.getParameter("title"));
				item.setPrice(Double.parseDouble(request.getParameter("price")));
				item.setPackageDate(LocalDate.parse(request.getParameter("packageDate")));

				itemService.addItem(item);

				view = "index.jsp";
				request.setAttribute("msg", "Item is saved with icode: " + item.getIcode());
				break;

			case "/deleteItem":
				int icode = Integer.parseInt(request.getParameter("icode"));
				itemService.deleteItem(icode);

				view = "index.jsp";
				request.setAttribute("msg", "Item is deleted having icode: " + icode);
				break;
			}
		} catch (InventoryException e) {
			view = "errorPage.jsp";
			request.setAttribute("errMsg", e.getMessage());
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
