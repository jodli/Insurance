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
 * Model for the Insurance table.
 *
 * @author Jan-Olaf Becker
 */
@DatabaseTable(tableName = "Insurance")
public class ModelInsurance {

    public static final String FIELD_ID = "ID";
    public static final String FIELD_INSURANCENO = "Versicherungsnummer";

    @DatabaseField(generatedId = true, columnName = FIELD_ID)
    private int ID;

    @DatabaseField(canBeNull = false, foreign = true)
    private ModelInsuree Insuree;

    @DatabaseField(canBeNull = false, columnName = FIELD_INSURANCENO)
    private String InsuranceNo;


    public ModelInsurance() {
    }

    public ModelInsurance(ModelInsurance m) {
        this();

        this.ID = m.ID;
        this.Insuree = m.Insuree;
        this.InsuranceNo = m.InsuranceNo;
    }

    public ModelInsurance(String insuranceNo, ModelInsuree insureeID) {
        this.Insuree = insureeID;
        this.InsuranceNo = insuranceNo;
    }

    public ModelInsuree getInsuree() {
        return Insuree;
    }

    public int getID() {
        return ID;
    }

    public String getInsuranceNo() {
        return InsuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        InsuranceNo = insuranceNo;
    }
}
