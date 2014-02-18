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

    @DatabaseField(generatedId = true)
    private int ID;

    @DatabaseField(canBeNull = false)
    private String Prename;
    @DatabaseField(canBeNull = false)
    private String Surname;

    @DatabaseField
    private String Partner_Prename;
    @DatabaseField
    private String Partner_Surname;

    @DatabaseField(canBeNull = false)
    private String BirthDate;
    @DatabaseField
    private String Partner_BirthDate;

    @DatabaseField(canBeNull = false)
    private String Street_Address;
    @DatabaseField(canBeNull = false)
    private String Street_Number;
    @DatabaseField(canBeNull = false)
    private String Zipcode;
    @DatabaseField(canBeNull = false)
    private String City;

    @DatabaseField
    private String Telephone_Number;
    @DatabaseField
    private String Fax_Number;
    @DatabaseField
    private String Cellphone_Number;

    @DatabaseField
    private String Email;

    @DatabaseField
    private boolean Contract;

    @DatabaseField
    private String Bank_Name;
    @DatabaseField
    private String Bank_IBAN;
    @DatabaseField
    private String Bank_BIC;

    @DatabaseField
    private String Job;
    @DatabaseField
    private String Partner_Job;

    public ModelInsuree () {
    }

    /**
     * @param prename
     * @param surname
     * @param street_Address
     * @param street_Number
     * @param zipcode
     * @param city
     * @param telephone_Number
     * @param fax_Number
     * @param cellphone_Number
     */
    public ModelInsuree ( String prename, String surname, String street_Address,
                          String street_Number, String zipcode, String city,
                          String telephone_Number, String fax_Number,
                          String cellphone_Number ) {
        this.Prename = prename;
        this.Surname = surname;
        this.Street_Address = street_Address;
        this.Zipcode = zipcode;
        this.City = city;
        this.Telephone_Number = telephone_Number;
        this.Fax_Number = fax_Number;
        this.Cellphone_Number = cellphone_Number;
    }

    /**
     * @return the prename
     */
    public String getPrename () {
        return Prename;
    }

    /**
     * @param prename the prename to set
     */
    public void setPrename ( String prename ) {
        this.Prename = prename;
    }

    /**
     * @return the surname
     */
    public String getSurname () {
        return Surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname ( String surname ) {
        this.Surname = surname;
    }

    /**
     * @return the value of Partner_Prename
     */
    public String getPartner_Prename () {
        return Partner_Prename;
    }

    /**
     * @param Partner_Prename new value of Partner_Prename
     */
    public void setPartner_Prename ( String Partner_Prename ) {
        this.Partner_Prename = Partner_Prename;
    }

    /**
     * Get the value of Partner_Surname
     *
     * @return the value of Partner_Surname
     */
    public String getPartner_Surname () {
        return Partner_Surname;
    }

    /**
     * Set the value of Partner_Surname
     *
     * @param Partner_Surname new value of Partner_Surname
     */
    public void setPartner_Surname ( String Partner_Surname ) {
        this.Partner_Surname = Partner_Surname;
    }

    /**
     * Get the value of BirthDate
     *
     * @return the value of BirthDate
     */
    public String getBirthDate () {
        return BirthDate;
    }

    /**
     * Set the value of BirthDate
     *
     * @param BirthDate new value of BirthDate
     */
    public void setBirthDate ( String BirthDate ) {
        this.BirthDate = BirthDate;
    }

    /**
     * Get the value of Partner_BirthDate
     *
     * @return the value of Partner_BirthDate
     */
    public String getPartner_BirthDate () {
        return Partner_BirthDate;
    }

    /**
     * Set the value of Partner_BirthDate
     *
     * @param Partner_BirthDate new value of Partner_BirthDate
     */
    public void setPartner_BirthDate ( String Partner_BirthDate ) {
        this.Partner_BirthDate = Partner_BirthDate;
    }

    /**
     * @return the street_Address
     */
    public String getStreet_Address () {
        return Street_Address;
    }

    /**
     * @param street_Address the street_Address to set
     */
    public void setStreet_Address ( String street_Address ) {
        this.Street_Address = street_Address;
    }

    /**
     * @return the street_Number
     */
    public String getStreet_Number () {
        return Street_Number;
    }

    /**
     * @param street_Number the street_Number to set
     */
    public void setStreet_Number ( String street_Number ) {
        this.Street_Number = street_Number;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode () {
        return Zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode ( String zipcode ) {
        this.Zipcode = zipcode;
    }

    /**
     * @return the city
     */
    public String getCity () {
        return City;
    }

    /**
     * @param city the city to set
     */
    public void setCity ( String city ) {
        this.City = city;
    }

    /**
     * @return the telephone_Number
     */
    public String getTelephone_Number () {
        return Telephone_Number;
    }

    /**
     * @param telephone_Number the telephone_Number to set
     */
    public void setTelephone_Number ( String telephone_Number ) {
        this.Telephone_Number = telephone_Number;
    }

    /**
     * @return the fax_Number
     */
    public String getFax_Number () {
        return Fax_Number;
    }

    /**
     * @param fax_Number the fax_Number to set
     */
    public void setFax_Number ( String fax_Number ) {
        this.Fax_Number = fax_Number;
    }

    /**
     * @return the cellphone_Number
     */
    public String getCellphone_Number () {
        return Cellphone_Number;
    }

    /**
     * @param cellphone_Number the cellphone_Number to set
     */
    public void setCellphone_Number ( String cellphone_Number ) {
        this.Cellphone_Number = cellphone_Number;
    }

    /**
     * Get the value of Email
     *
     * @return the value of Email
     */
    public String getEmail () {
        return Email;
    }

    /**
     * Set the value of Email
     *
     * @param Email new value of Email
     */
    public void setEmail ( String Email ) {
        this.Email = Email;
    }

    /**
     * Get the value of Contract
     *
     * @return the value of Contract
     */
    public boolean isContract () {
        return Contract;
    }

    /**
     * Set the value of Contract
     *
     * @param Contract new value of Contract
     */
    public void setContract ( boolean Contract ) {
        this.Contract = Contract;
    }

    /**
     * Get the value of Bank_Name
     *
     * @return the value of Bank_Name
     */
    public String getBank_Name () {
        return Bank_Name;
    }

    /**
     * Set the value of Bank_Name
     *
     * @param Bank_Name new value of Bank_Name
     */
    public void setBank_Name ( String Bank_Name ) {
        this.Bank_Name = Bank_Name;
    }

    /**
     * Get the value of Bank_IBAN
     *
     * @return the value of Bank_IBAN
     */
    public String getBank_IBAN () {
        return Bank_IBAN;
    }

    /**
     * Set the value of Bank_IBAN
     *
     * @param Bank_IBAN new value of Bank_IBAN
     */
    public void setBank_IBAN ( String Bank_IBAN ) {
        this.Bank_IBAN = Bank_IBAN;
    }

    /**
     * Get the value of Bank_BIC
     *
     * @return the value of Bank_BIC
     */
    public String getBank_BIC () {
        return Bank_BIC;
    }

    /**
     * Set the value of Bank_BIC
     *
     * @param Bank_BIC new value of Bank_BIC
     */
    public void setBank_BIC ( String Bank_BIC ) {
        this.Bank_BIC = Bank_BIC;
    }

    /**
     * Get the value of Job
     *
     * @return the value of Job
     */
    public String getJob () {
        return Job;
    }

    /**
     * Set the value of Job
     *
     * @param Job new value of Job
     */
    public void setJob ( String Job ) {
        this.Job = Job;
    }

    /**
     * Get the value of Partner_Job
     *
     * @return the value of Partner_Job
     */
    public String getPartner_Job () {
        return Partner_Job;
    }

    /**
     * Set the value of Partner_Job
     *
     * @param Partner_Job new value of Partner_Job
     */
    public void setPartner_Job ( String Partner_Job ) {
        this.Partner_Job = Partner_Job;
    }

    /**
     * @return the iD
     */
    public int getID () {
        return ID;
    }

}
