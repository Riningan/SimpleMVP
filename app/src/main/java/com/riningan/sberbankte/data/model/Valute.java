package com.riningan.sberbankte.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Valute")
public class Valute {
    /*
     * <Valute ID="R01010">
     * <NumCode>036</NumCode>
     * <CharCode>AUD</CharCode>
     * <Nominal>1</Nominal>
     * <Name>Австралийский доллар</Name>
     * <Value>45,6456</Value>
     * </Valute>
     */

    @Attribute(name = "ID")
    public String ID;

    @Element
    public String NumCode;

    @Element
    public String CharCode;

    @Element
    public int Nominal;

    @Element
    public String Name;

    @Element
    public String Value;


    public float getValue() {
        return Float.parseFloat(Value.replace(",", "."));
    }
}
