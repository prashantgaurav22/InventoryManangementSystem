package com.dxc.ims.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="allItemsQry",query="SELECT i FROM Item i"),
	@NamedQuery(name="itemQry",query="SELECT i FROM Item i WHERE i.icode=:icode")
})
@Entity
@Table(name="myitems")
public class Item implements Serializable {
	
	@Id
	@Column(name="icode")
	private int icode;
	
	@Column(name="title",nullable=false)
	private String title;
	
	@Column(name="price",nullable=false)
	private double price;

	@Column(name="pdate",nullable=true)
	private LocalDate packageDate;
	
	public Item() {
		//left
	}

	public Item(int icode, String title, double price, LocalDate packageDate) {
		super();
		this.icode = icode;
		this.title = title;
		this.price = price;
		this.packageDate = packageDate;
	}

	public int getIcode() {
		return icode;
	}

	public void setIcode(int icode) {
		this.icode = icode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getPackageDate() {
		return packageDate;
	}

	public void setPackageDate(LocalDate packageDate) {
		this.packageDate = packageDate;
	}
	
	

}
