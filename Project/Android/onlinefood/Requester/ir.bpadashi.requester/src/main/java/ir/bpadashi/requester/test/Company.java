package ir.bpadashi.requester.test;

import java.io.Serializable;

/**
 * Created by BabakPadashi on 6/9/2016.
 */
public class Company implements Serializable {

    static final long serialVersionUID =8996890340799609057L;

    public double Xposition;
    public double Yposition;
    public String companyName;
    public String address;

    public String shortDescription;
    public String fullDescription;
    public String img;
    public String phone;
    public String Mobile;
    public String messageDay;

    public int saleType;
}
