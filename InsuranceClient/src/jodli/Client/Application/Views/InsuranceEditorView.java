/*
 *
 *  * Copyright (C) 2013 Jan-Olaf Becker
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package src.jodli.Client.Application.Views;

import org.jdesktop.swingx.JXDatePicker;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsurance;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.EInsurance;
import src.jodli.Client.Utilities.InsuranceUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Observable;

/**
 * Created by job87 on 3/7/2015.
 */
public class InsuranceEditorView extends Observable implements IEditorView {
    private JPanel content;
    private JTextField insuranceNoTextField;
    private JComboBox<EInsurance> insuranceTypeComboBox;
    private JXDatePicker contractDatePicker;
    private JXDatePicker startDatePicker;
    private JXDatePicker endDatePicker;

    private ModelInsurance m_Insurance;
    private ModelInsuree m_Insuree;

    private void createUIComponents() {
        content = new JPanel();

        contractDatePicker = new JXDatePicker();
        contractDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startDatePicker = new JXDatePicker();
        startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePicker = new JXDatePicker();
        endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        insuranceTypeComboBox = new JComboBox<>(EInsurance.values());
    }

    @Override
    public boolean saveSettings() {
        boolean changed = false;

        changed |= m_Insurance.setInsuranceNo(insuranceNoTextField.getText());
        changed |= m_Insurance.setType((EInsurance) insuranceTypeComboBox.getSelectedItem());
        changed |= m_Insurance.setContractDate(contractDatePicker.getDate());
        changed |= m_Insurance.setStart(startDatePicker.getDate());
        changed |= m_Insurance.setEnd(endDatePicker.getDate());
        m_Insurance.setInsuree(m_Insuree);

        if (changed) {
            if (changed = InsuranceUtils.getInstance().setValue(m_Insurance)) {
                setChanged();
                notifyObservers();
            }
        }
        return changed;
    }

    @Override
    public void loadSettings() {
        if (m_Insurance != null) {
            Logger.logDebug("Loading Insurance data.");
        } else {
            Logger.logDebug("Creating new Insurance.");
            m_Insurance = new ModelInsurance();
            m_Insurance.setInsuree(m_Insuree);
        }
        insuranceNoTextField.setText(m_Insurance.getInsuranceNo());
        insuranceTypeComboBox.setSelectedItem(m_Insurance.getType());
        contractDatePicker.setDate(m_Insurance.getContractDate());
        startDatePicker.setDate(m_Insurance.getStart());
        endDatePicker.setDate(m_Insurance.getEnd());
        m_Insuree = m_Insurance.getInsuree();
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    public void setInsurance(ModelInsurance modelInsurance) {
        m_Insurance = modelInsurance;
    }

    public ModelInsuree getInsuree() {
        return m_Insuree;
    }

    public void setInsuree(ModelInsuree insuree) {
        m_Insuree = insuree;
    }
}
