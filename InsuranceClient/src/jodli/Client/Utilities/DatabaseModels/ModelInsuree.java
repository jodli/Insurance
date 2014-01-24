/*
 * Copyright (C) 2013 Jan-Olaf Becker
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package src.jodli.Client.Utilities.DatabaseModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model for the Insuree table.
 * 
 * @author Jan-Olaf Becker
 * 
 */
@DatabaseTable(tableName = "Insuree")
public class ModelInsuree {

	@DatabaseField(generatedId = true, id = true)
	private int ID;

	@DatabaseField
	private String Prename = "";
	@DatabaseField
	private String Surname = "";

	@DatabaseField
	private String Street_Address = "";
	@DatabaseField
	private String Zipcode = "";
	@DatabaseField
	private String City = "";

	@DatabaseField
	private String Telephone_Number = "";
	@DatabaseField
	private String Fax_Number = "";
	@DatabaseField
	private String Cellphone_Number = "";

	public ModelInsuree() {
	}

	/**
	 * @param prename
	 * @param surname
	 * @param street_Address
	 * @param zipcode
	 * @param city
	 * @param telephone_Number
	 * @param fax_Number
	 * @param cellphone_Number
	 */
	public ModelInsuree(String prename, String surname, String street_Address,
			String zipcode, String city, String telephone_Number,
			String fax_Number, String cellphone_Number) {
		super();
		Prename = prename;
		Surname = surname;
		Street_Address = street_Address;
		Zipcode = zipcode;
		City = city;
		Telephone_Number = telephone_Number;
		Fax_Number = fax_Number;
		Cellphone_Number = cellphone_Number;
	}

	/**
	 * @return the prename
	 */
	public String getPrename() {
		return Prename;
	}

	/**
	 * @param prename
	 *            the prename to set
	 */
	public void setPrename(String prename) {
		Prename = prename;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return Surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		Surname = surname;
	}

	/**
	 * @return the street_Address
	 */
	public String getStreet_Address() {
		return Street_Address;
	}

	/**
	 * @param street_Address
	 *            the street_Address to set
	 */
	public void setStreet_Address(String street_Address) {
		Street_Address = street_Address;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return Zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		Zipcode = zipcode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		City = city;
	}

	/**
	 * @return the telephone_Number
	 */
	public String getTelephone_Number() {
		return Telephone_Number;
	}

	/**
	 * @param telephone_Number
	 *            the telephone_Number to set
	 */
	public void setTelephone_Number(String telephone_Number) {
		Telephone_Number = telephone_Number;
	}

	/**
	 * @return the fax_Number
	 */
	public String getFax_Number() {
		return Fax_Number;
	}

	/**
	 * @param fax_Number
	 *            the fax_Number to set
	 */
	public void setFax_Number(String fax_Number) {
		Fax_Number = fax_Number;
	}

	/**
	 * @return the cellphone_Number
	 */
	public String getCellphone_Number() {
		return Cellphone_Number;
	}

	/**
	 * @param cellphone_Number
	 *            the cellphone_Number to set
	 */
	public void setCellphone_Number(String cellphone_Number) {
		Cellphone_Number = cellphone_Number;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

}
