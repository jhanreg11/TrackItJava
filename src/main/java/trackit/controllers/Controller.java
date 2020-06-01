// MADE BY: Jacob Hanson-Regalado

package trackit.controllers;

import trackit.views.Page;

import java.util.Calendar;

public interface Controller {
    /**
     * Gets calendar object representing the start date for the given time period from a ComboBox.
     * @param period one of the options from a time-period combobox ["Past Month" | "Past Year" | "All Time"]
     * @return start date for that period
     */
    static Calendar getStartDate(String period) {
        if (period == null)
            period = "";

        Calendar date = Calendar.getInstance();
        if (period.equals("Past Month"))
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
        else if (period.equals("Past Year"))
            date.set(Calendar.YEAR, date.get(Calendar.YEAR) - 1);
        else
            date.setTimeInMillis(Long.valueOf("0"));

        return date;
    }

    /**
     * Gets Page object to be used for setting on stage.
     */
    Page getPage();

    /**
     * prepares controller's page to be viewed.
     */
    void loadPage();
}
