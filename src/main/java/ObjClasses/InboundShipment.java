package ObjClasses;

import java.util.*;

public class InboundShipment {

    private int shippingId;
    private Date shippingDate;
    private int amountOfPhones;
    private int amountOfPads;
    private int amountOfWatches;
    private int toWarehouse;


    public InboundShipment(int shippingId, Date shippingDate, int amountOfPhones, int amountOfPads, int amountOfWatches, int toWarehouse){
        this.shippingId = shippingId;
        this.shippingDate = shippingDate;
        this.amountOfPhones = amountOfPhones;
        this.amountOfPads = amountOfPads;
        this.amountOfWatches = amountOfWatches;
        this.toWarehouse = toWarehouse;
    }

    public int getShippingId() {
        return shippingId;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public int getAmountOfPhones() {
        return amountOfPhones;
    }

    public int getAmountOfPads() {
        return amountOfPads;
    }

    public int getAmountOfWatches() {
        return amountOfWatches;
    }

    public int getToWarehouse() {
        return toWarehouse;
    }
}
