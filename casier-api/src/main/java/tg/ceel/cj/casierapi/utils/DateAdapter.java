/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author believe
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    public DateAdapter() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        this.dateFormat.setTimeZone(tz);
    }

    @Override
    public Date unmarshal(String xml) throws Exception {
        synchronized (dateFormat) {
            return this.dateFormat.parse(xml);
        }
    }

    @Override
    public String marshal(Date object) throws Exception {
        synchronized (dateFormat) {
            return this.dateFormat.format(object);
        }
    }

}
