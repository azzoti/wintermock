package org.lazyluke.wintermock.testclasses;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;

public class DateFieldsType {

    private Date javaDateField;
    private LocalDate jodaLocalDateField;
    private DateTime jodaDateTimeField;
    private Calendar javaCalendarField;

    public DateFieldsType(Date javaDateField, LocalDate jodaLocalDateField, DateTime jodaDateTimeField, Calendar javaCalendarField) {
        this.javaDateField = javaDateField;
        this.jodaLocalDateField = jodaLocalDateField;
        this.jodaDateTimeField = jodaDateTimeField;
        this.javaCalendarField = javaCalendarField;
    }

    public DateFieldsType() {
    }

    public Date getJavaDateField() {
        return javaDateField;
    }

    public void setJavaDateField(Date javaDateField) {
        this.javaDateField = javaDateField;
    }

    public LocalDate getJodaLocalDateField() {
        return jodaLocalDateField;
    }

    public void setJodaLocalDateField(LocalDate jodaLocalDateField) {
        this.jodaLocalDateField = jodaLocalDateField;
    }

    public Calendar getJavaCalendarField() {
        return javaCalendarField;
    }

    public void setJavaCalendarField(Calendar javaCalendarField) {
        this.javaCalendarField = javaCalendarField;
    }

    public DateTime getJodaDateTimeField() {
        return jodaDateTimeField;
    }

    public void setJodaDateTimeField(DateTime jodaDateTimeField) {
        this.jodaDateTimeField = jodaDateTimeField;
    }
}
