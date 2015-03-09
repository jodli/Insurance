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
import src.jodli.Client.Utilities.EInsurance;

import java.util.Date;
import java.util.Objects;

/**
 * Model for the Insurance table.
 *
 * @author Jan-Olaf Becker
 */
@DatabaseTable(tableName = "Insurance")
public class ModelInsurance implements IModel{

    public static final String FIELD_ID = "ID";
    public static final String FIELD_INSURANCENO = "Versicherungsnummer";
    public static final String FIELD_TYPE = "Versicherungsart";
    public static final String FIELD_CONTRACT_DATE = "Antragsdatum";
    public static final String FIELD_START = "Beginn";
    public static final String FIELD_END = "Ende";

    @DatabaseField(generatedId = true, columnName = FIELD_ID)
    private int ID;
    @DatabaseField(canBeNull = false, foreign = true)
    private ModelInsuree Insuree;
    @DatabaseField(canBeNull = false, columnName = FIELD_INSURANCENO)
    private String InsuranceNo;
    @DatabaseField(canBeNull = false, columnName = FIELD_TYPE)
    private EInsurance Type;
    @DatabaseField(columnName = FIELD_CONTRACT_DATE)
    private Date ContractDate;
    @DatabaseField(columnName = FIELD_START)
    private Date Start;
    @DatabaseField(columnName = FIELD_END)
    private Date End;

    public ModelInsurance() {

        this.ID = 0;
        this.Insuree = new ModelInsuree();
        this.InsuranceNo = "";
        this.Start = null;
        this.ContractDate = null;
        this.Type = EInsurance.UNDEFINIERT;
        this.End = null;
    }

    public ModelInsurance(ModelInsurance m) {
        this();

        this.ID = m.ID;
        this.Insuree = m.Insuree;
        this.InsuranceNo = m.InsuranceNo;
        this.Start = m.Start;
        this.ContractDate = m.ContractDate;
        this.Type = m.Type;
        this.End = m.End;
    }

    public ModelInsurance(String insuranceNo, ModelInsuree insuree, Date start, Date contractDate, EInsurance type, Date end) {
        this.Insuree = insuree;
        this.InsuranceNo = insuranceNo;
        this.Start = start;
        this.ContractDate = contractDate;
        this.Type = type;
        this.End = end;
    }

    public EInsurance getType() {
        return Type;
    }

    public boolean setType(EInsurance type) {
        if (Type == type) {
            Type = type;
            return true;
        }
        return false;
    }

    public Date getEnd() {
        return End;
    }

    public boolean setEnd(Date end) {
        if (End == null || End.compareTo(end) != 0) {
            End = end;
            return true;
        }
        return false;
    }

    public Date getStart() {
        return Start;
    }

    public boolean setStart(Date start) {
        if (Start == null || Start.compareTo(start) != 0) {
            Start = start;
            return true;
        }
        return false;
    }

    public Date getContractDate() {
        return ContractDate;
    }

    public boolean setContractDate(Date contractDate) {
        if (ContractDate == null || ContractDate.compareTo(contractDate) != 0) {
            ContractDate = contractDate;
            return true;
        }
        return false;
    }

    public ModelInsuree getInsuree() {
        return Insuree;
    }

    public void setInsuree(ModelInsuree insuree) {
        Insuree = insuree;
    }

    public int getID() {
        return ID;
    }

    public String getInsuranceNo() {
        return InsuranceNo;
    }

    public boolean setInsuranceNo(String insuranceNo) {
        if (!Objects.equals(InsuranceNo, insuranceNo)) {
            InsuranceNo = insuranceNo;
            return true;
        }
        return false;
    }
}
