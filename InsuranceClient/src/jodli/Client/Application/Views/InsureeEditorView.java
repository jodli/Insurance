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
import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.InsureeUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Observable;

/**
 * Created by job87 on 3/6/2015.
 */
public class InsureeEditorView extends Observable implements IEditorView {
    private JPanel content;
    private JTextField telephoneTextField;
    private JTextField streetTextField;
    private JTextField numberTextField;
    private JTextField zipcodeTextField;
    private JTextField cityTextField;
    private JTextField faxTextField;
    private JTextField cellphoneTextField;
    private JTextField emailTextField;
    private JTextField bankTextField;
    private JTextField ibanTextField;
    private JTextField bicTextField;
    private JCheckBox contractCheckBox;
    private JTextField surnameTextField;
    private JTextField prenameTextField;
    private JTextField jobTextField;
    private JTextField prenamePartnerTextField;
    private JTextField jobPartnerTextField;
    private JTextField surnamePartnerTextField;
    private JXDatePicker birthdatePartnerDatePicker;
    private JXDatePicker birthdateDatePicker;

    private ModelInsuree m_Insuree;

    private void createUIComponents() {
        content = new JPanel();
        m_Insuree = new ModelInsuree();

        birthdateDatePicker = new JXDatePicker();
        birthdateDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        birthdatePartnerDatePicker = new JXDatePicker();
        birthdatePartnerDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    public boolean saveSettings() {
        boolean changed = false;
        changed |= m_Insuree.setPrename(prenameTextField.getText());
        changed |= m_Insuree.setSurname(surnameTextField.getText());
        changed |= m_Insuree.setBirthDate(birthdateDatePicker.getDate());
        changed |= m_Insuree.setJob(jobTextField.getText());

        changed |= m_Insuree.setPartner_Prename(prenamePartnerTextField.getText());
        changed |= m_Insuree.setPartner_Surname(surnamePartnerTextField.getText());
        changed |= m_Insuree.setPartner_BirthDate(birthdatePartnerDatePicker.getDate());
        changed |= m_Insuree.setPartner_Job(jobPartnerTextField.getText());

        changed |= m_Insuree.setStreet_Address(streetTextField.getText());
        changed |= m_Insuree.setStreet_Number(numberTextField.getText());
        changed |= m_Insuree.setZipcode(zipcodeTextField.getText());
        changed |= m_Insuree.setCity(cityTextField.getText());

        changed |= m_Insuree.setTelephone_Number(telephoneTextField.getText());
        changed |= m_Insuree.setFax_Number(faxTextField.getText());
        changed |= m_Insuree.setCellphone_Number(cellphoneTextField.getText());
        changed |= m_Insuree.setEmail(emailTextField.getText());

        changed |= m_Insuree.setBank_Name(bankTextField.getText());
        changed |= m_Insuree.setBank_IBAN(ibanTextField.getText());
        changed |= m_Insuree.setBank_BIC(bicTextField.getText());

        changed |= m_Insuree.setContract(contractCheckBox.isSelected());

        if (changed) {
            if (changed = InsureeUtils.getInstance().setValue(m_Insuree)) {
                setChanged();
                notifyObservers();
            }
        }
        return changed;
    }

    @Override
    public void loadSettings() {
        if (m_Insuree != null) {
            Logger.logDebug("Loading Insuree data.");
        } else {
            Logger.logDebug("Creating new Insuree.");
            m_Insuree = new ModelInsuree();
        }
        prenameTextField.setText(m_Insuree.getPrename());
        surnameTextField.setText(m_Insuree.getSurname());
        birthdateDatePicker.setDate(m_Insuree.getBirthDate());
        jobTextField.setText(m_Insuree.getJob());

        prenamePartnerTextField.setText(m_Insuree.getPartner_Prename());
        surnamePartnerTextField.setText(m_Insuree.getPartner_Surname());
        birthdatePartnerDatePicker.setDate(m_Insuree.getPartner_BirthDate());
        jobPartnerTextField.setText(m_Insuree.getPartner_Job());

        streetTextField.setText(m_Insuree.getStreet_Address());
        numberTextField.setText(m_Insuree.getStreet_Number());
        zipcodeTextField.setText(m_Insuree.getZipcode());
        cityTextField.setText(m_Insuree.getCity());

        telephoneTextField.setText(m_Insuree.getTelephone_Number());
        faxTextField.setText(m_Insuree.getFax_Number());
        cellphoneTextField.setText(m_Insuree.getCellphone_Number());
        emailTextField.setText(m_Insuree.getEmail());

        bankTextField.setText(m_Insuree.getBank_Name());
        ibanTextField.setText(m_Insuree.getBank_IBAN());
        bicTextField.setText(m_Insuree.getBank_BIC());

        contractCheckBox.setSelected(m_Insuree.hasContract());
    }

    public void setInsuree(ModelInsuree insuree) {
        m_Insuree = insuree;
    }

}
