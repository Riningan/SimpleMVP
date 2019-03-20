package com.riningan.sberbankte.data.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root
public class ValCurs {
    @Attribute
    public String Date;

    @Attribute
    public String name;

    @ElementList(inline = true)
    public List<Valute> valutes;


    public Valute getValuteById(String id) {
        if (valutes != null) {
            for (Valute valute : valutes) {
                if (valute.ID.equals(id)) {
                    return valute;
                }
            }
        }
        return null;
    }
}
