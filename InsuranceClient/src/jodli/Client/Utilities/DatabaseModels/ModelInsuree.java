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

import java.util.Date;
import java.util.Objects;

/**
 * Model for the Insuree table.
 *
 * @author Jan-Olaf Becker
 */
@DatabaseTable(tableName = "Insuree")
public class ModelInsuree implements IModel {

    public static final String FIELD_ID = "ID";
    public static final String FIELD_PRENAME = "Vorname";
    public static final String FIELD_SURNAME = "Nachname";
    public static final String FIELD_PRENAME_PARTNER = "Vorname Partner";
    public static final String FIELD_SURNAME_PARTNER = "Nachname Partner";
    public static final String FIELD_BIRTHDATE = "Geburtsdatum";
    public static final String FIELD_BIRTHDATE_PARTNER = "GD Partner";
    public static final String FIELD_STREET = "Straße";
    public static final String FIELD_STREET_NUMBER = "Hausnummer";
    public static final String FIELD_ZIPCODE = "PLZ";
    public static final String FIELD_CITY = "Wohnort";
    public static final String FIELD_TELEPHONE = "Telefon";
    public static final String FIELD_FAX = "Fax";
    public static final String FIELD_CELLPHONE = "Handy";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_CONTRACT = "Vollmacht";
    public static final String FIELD_BANK_NAME = "Bank Name";
    public static final String FIELD_BANK_IBAN = "IBAN";
    public static final String FIELD_BANK_BIC = "BIC";
    public static final String FIELD_JOB = "Job";
    public static final String FIELD_JOB_PARTNER = "Job Partner";

    @DatabaseField(generatedId = true, columnName = FIELD_ID)
    private int ID;

    @DatabaseField(canBeNull = false, columnName = FIELD_PRENAME)
    private String Prename;
    @DatabaseField(canBeNull = false, columnName = FIELD_SURNAME)
    private String Surname;

    @DatabaseField(columnName = FIELD_PRENAME_PARTNER)
    private String Partner_Prename;
    @DatabaseField(columnName = FIELD_SURNAME_PARTNER)
    private String Partner_Surname;

    @DatabaseField(columnName = FIELD_BIRTHDATE)
    private Date BirthDate;
    @DatabaseField(columnName = FIELD_BIRTHDATE_PARTNER)
    private Date Partner_BirthDate;

    @DatabaseField(columnName = FIELD_STREET)
    private String Street_Address;
    @DatabaseField(columnName = FIELD_STREET_NUMBER)
    private String Street_Number;
    @DatabaseField(columnName = FIELD_ZIPCODE)
    private String Zipcode;
    @DatabaseField(columnName = FIELD_CITY)
    private String City;

    @DatabaseField(columnName = FIELD_TELEPHONE)
    private String Telephone_Number;
    @DatabaseField(columnName = FIELD_FAX)
    private String Fax_Number;
    @DatabaseField(columnName = FIELD_CELLPHONE)
    private String Cellphone_Number;

    @DatabaseField(columnName = FIELD_EMAIL)
    private String Email;

    @DatabaseField(columnName = FIELD_CONTRACT)
    private boolean Contract;

    @DatabaseField(columnName = FIELD_BANK_NAME)
    private String Bank_Name;
    @DatabaseField(columnName = FIELD_BANK_IBAN)
    private String Bank_IBAN;
    @DatabaseField(columnName = FIELD_BANK_BIC)
    private String Bank_BIC;

    @DatabaseField(columnName = FIELD_JOB)
    private String Job;
    @DatabaseField(columnName = FIELD_JOB_PARTNER)
    private String Partner_Job;

    public ModelInsuree() {
        this.ID = 0;
        this.Prename = "";
        this.Surname = "";
        this.Partner_Prename = "";
        this.Partner_Surname = "";
        this.BirthDate = null;
        this.Partner_BirthDate = null;
        this.Street_Address = "";
        this.Street_Number = "";
        this.Zipcode = "";
        this.City = "";
        this.Telephone_Number = "";
        this.Fax_Number = "";
        this.Cellphone_Number = "";
        this.Email = "";
        this.Contract = false;
        this.Bank_Name = "";
        this.Bank_IBAN = "";
        this.Bank_BIC = "";
        this.Job = "";
        this.Partner_Job = "";
    }

    /**
     * Copy constructor
     *
     * @param m ModelInsuree object to copy.
     */
    public ModelInsuree(ModelInsuree m) {
        this();

        this.ID = m.ID;
        this.Prename = m.Prename;
        this.Surname = m.Surname;
        this.Partner_Prename = m.Partner_Prename;
        this.Partner_Surname = m.Partner_Surname;
        this.BirthDate = m.BirthDate;
        this.Partner_BirthDate = m.Partner_BirthDate;
        this.Street_Address = m.Street_Address;
        this.Street_Number = m.Street_Number;
        this.Zipcode = m.Zipcode;
        this.City = m.City;
        this.Telephone_Number = m.Telephone_Number;
        this.Fax_Number = m.Fax_Number;
        this.Cellphone_Number = m.Cellphone_Number;
        this.Email = m.Email;
        this.Contract = m.Contract;
        this.Bank_Name = m.Bank_Name;
        this.Bank_IBAN = m.Bank_IBAN;
        this.Bank_BIC = m.Bank_BIC;
        this.Job = m.Job;
        this.Partner_Job = m.Partner_Job;
    }

    /**
     * @param prename
     * @param surname
     * @param partner_Prename   the value of partner_Prename
     * @param partner_Surname   the value of partner_Surname
     * @param birthDate         the value of birthDate
     * @param partner_BirthDate the value of partner_BirthDate
     * @param street_Address
     * @param street_Number
     * @param zipcode
     * @param city
     * @param telephone_Number
     * @param fax_Number
     * @param cellphone_Number
     * @param email             the value of email
     * @param contract          the value of contract
     * @param bank_Name         the value of bank_Name
     * @param bank_IBAN         the value of bank_IBAN
     * @param bank_BIC          the value of bank_BIC
     * @param job               the value of job
     * @param partner_Job       the value of partner_Job
     */
    public ModelInsuree(String prename, String surname, String partner_Prename, String partner_Surname, Date birthDate, Date partner_BirthDate, String street_Address, String street_Number, String zipcode, String city, String telephone_Number, String fax_Number, String cellphone_Number, String email, boolean contract, String bank_Name, String bank_IBAN, String bank_BIC, String job, String partner_Job) {
        this();

        this.Prename = prename;
        this.Surname = surname;
        this.Partner_Prename = partner_Prename;
        this.Partner_Surname = partner_Surname;
        this.BirthDate = birthDate;
        this.Partner_BirthDate = partner_BirthDate;
        this.Street_Address = street_Address;
        this.Street_Number = street_Number;
        this.Zipcode = zipcode;
        this.City = city;
        this.Telephone_Number = telephone_Number;
        this.Fax_Number = fax_Number;
        this.Cellphone_Number = cellphone_Number;
        this.Email = email;
        this.Contract = contract;
        this.Bank_Name = bank_Name;
        this.Bank_IBAN = bank_IBAN;
        this.Bank_BIC = bank_BIC;
        this.Job = job;
        this.Partner_Job = partner_Job;
    }

    /**
     * @return the prename
     */
    public String getPrename() {
        return Prename;
    }

    /**
     * @param prename the prename to set
     */
    public boolean setPrename(String prename) {
        if (!Objects.equals(this.Prename, prename)) {
            this.Prename = prename;
            return true;
        }
        return false;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return Surname;
    }

    /**
     * @param surname the surname to set
     */
    public boolean setSurname(String surname) {
        if (!Objects.equals(this.Surname, surname)) {
            this.Surname = surname;
            return true;
        }
        return false;
    }

    /**
     * @return the value of Partner_Prename
     */
    public String getPartner_Prename() {
        return Partner_Prename;
    }

    /**
     * @param Partner_Prename new value of Partner_Prename
     */
    public boolean setPartner_Prename(String Partner_Prename) {
        if (!Objects.equals(this.Partner_Prename, Partner_Prename)) {
            this.Partner_Prename = Partner_Prename;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Partner_Surname
     *
     * @return the value of Partner_Surname
     */
    public String getPartner_Surname() {
        return Partner_Surname;
    }

    /**
     * Set the value of Partner_Surname
     *
     * @param Partner_Surname new value of Partner_Surname
     */
    public boolean setPartner_Surname(String Partner_Surname) {
        if (!Objects.equals(this.Partner_Surname, Partner_Surname)) {
            this.Partner_Surname = Partner_Surname;
            return true;
        }
        return false;
    }

    /**
     * Get the value of BirthDate
     *
     * @return the value of BirthDate
     */
    public Date getBirthDate() {
        return BirthDate;
    }

    /**
     * Set the value of BirthDate
     *
     * @param BirthDate new value of BirthDate
     */
    public boolean setBirthDate(Date BirthDate) {
        if (this.BirthDate == null || this.BirthDate.compareTo(BirthDate) != 0) {
            this.BirthDate = BirthDate;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Partner_BirthDate
     *
     * @return the value of Partner_BirthDate
     */
    public Date getPartner_BirthDate() {
        return Partner_BirthDate;
    }

    /**
     * Set the value of Partner_BirthDate
     *
     * @param Partner_BirthDate new value of Partner_BirthDate
     */
    public boolean setPartner_BirthDate(Date Partner_BirthDate) {
        if (this.Partner_BirthDate == null || this.Partner_BirthDate.compareTo(Partner_BirthDate) != 0) {
            this.Partner_BirthDate = Partner_BirthDate;
            return true;
        }
        return false;
    }

    /**
     * @return the street_Address
     */
    public String getStreet_Address() {
        return Street_Address;
    }

    /**
     * @param street_Address the street_Address to set
     */
    public boolean setStreet_Address(String street_Address) {
        if (!Objects.equals(this.Street_Address, street_Address)) {
            this.Street_Address = street_Address;
            return true;
        }
        return false;
    }

    /**
     * @return the street_Number
     */
    public String getStreet_Number() {
        return Street_Number;
    }

    /**
     * @param street_Number the street_Number to set
     */
    public boolean setStreet_Number(String street_Number) {
        if (!Objects.equals(this.Street_Number, street_Number)) {
            this.Street_Number = street_Number;
            return true;
        }
        return false;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return Zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public boolean setZipcode(String zipcode) {
        if (!Objects.equals(this.Zipcode, zipcode)) {
            this.Zipcode = zipcode;
            return true;
        }
        return false;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return City;
    }

    /**
     * @param city the city to set
     */
    public boolean setCity(String city) {
        if (!Objects.equals(this.City, city)) {
            this.City = city;
            return true;
        }
        return false;
    }

    /**
     * @return the telephone_Number
     */
    public String getTelephone_Number() {
        return Telephone_Number;
    }

    /**
     * @param telephone_Number the telephone_Number to set
     */
    public boolean setTelephone_Number(String telephone_Number) {
        if (!Objects.equals(this.Telephone_Number, telephone_Number)) {
            this.Telephone_Number = telephone_Number;
            return true;
        }
        return false;
    }

    /**
     * @return the fax_Number
     */
    public String getFax_Number() {
        return Fax_Number;
    }

    /**
     * @param fax_Number the fax_Number to set
     */
    public boolean setFax_Number(String fax_Number) {
        if (!Objects.equals(this.Fax_Number, fax_Number)) {
            this.Fax_Number = fax_Number;
            return true;
        }
        return false;
    }

    /**
     * @return the cellphone_Number
     */
    public String getCellphone_Number() {
        return Cellphone_Number;
    }

    /**
     * @param cellphone_Number the cellphone_Number to set
     */
    public boolean setCellphone_Number(String cellphone_Number) {
        if (!Objects.equals(this.Cellphone_Number, cellphone_Number)) {
            this.Cellphone_Number = cellphone_Number;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Email
     *
     * @return the value of Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Set the value of Email
     *
     * @param Email new value of Email
     */
    public boolean setEmail(String Email) {
        if (!Objects.equals(this.Email, Email)) {
            this.Email = Email;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Contract
     *
     * @return the value of Contract
     */
    public boolean hasContract() {
        return Contract;
    }

    /**
     * Set the value of Contract
     *
     * @param Contract new value of Contract
     */
    public boolean setContract(boolean Contract) {
        if (this.Contract != Contract) {
            this.Contract = Contract;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Bank_Name
     *
     * @return the value of Bank_Name
     */
    public String getBank_Name() {
        return Bank_Name;
    }

    /**
     * Set the value of Bank_Name
     *
     * @param Bank_Name new value of Bank_Name
     */
    public boolean setBank_Name(String Bank_Name) {
        if (!Objects.equals(this.Bank_Name, Bank_Name)) {
            this.Bank_Name = Bank_Name;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Bank_IBAN
     *
     * @return the value of Bank_IBAN
     */

    public String getBank_IBAN() {
        return Bank_IBAN;
    }

    /**
     * Set the value of Bank_IBAN
     *
     * @param Bank_IBAN new value of Bank_IBAN
     */
    public boolean setBank_IBAN(String Bank_IBAN) {
        if (!Objects.equals(this.Bank_IBAN, Bank_IBAN)) {
            this.Bank_IBAN = Bank_IBAN;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Bank_BIC
     *
     * @return the value of Bank_BIC
     */
    public String getBank_BIC() {
        return Bank_BIC;
    }

    /**
     * Set the value of Bank_BIC
     *
     * @param Bank_BIC new value of Bank_BIC
     */
    public boolean setBank_BIC(String Bank_BIC) {
        if (!Objects.equals(this.Bank_BIC, Bank_BIC)) {
            this.Bank_BIC = Bank_BIC;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Job
     *
     * @return the value of Job
     */
    public String getJob() {
        return Job;
    }

    /**
     * Set the value of Job
     *
     * @param Job new value of Job
     */
    public boolean setJob(String Job) {
        if (!Objects.equals(this.Job, Job)) {
            this.Job = Job;
            return true;
        }
        return false;
    }

    /**
     * Get the value of Partner_Job
     *
     * @return the value of Partner_Job
     */
    public String getPartner_Job() {
        return Partner_Job;
    }

    /**
     * Set the value of Partner_Job
     *
     * @param Partner_Job new value of Partner_Job
     */
    public boolean setPartner_Job(String Partner_Job) {
        if (!Objects.equals(this.Partner_Job, Partner_Job)) {
            this.Partner_Job = Partner_Job;
            return true;
        }
        return false;
    }

    /**
     * @return the iD
     */
    public int getID() {
        return ID;
    }
}
