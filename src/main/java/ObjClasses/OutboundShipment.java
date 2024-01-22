package ObjClasses;

import java.util.*;

public class OutboundShipment {

    private int shippingId;
    private Date shippingDate;
    private int amountOfPhones;
    private int amountOfPads;
    private int amountOfWatches;
    private int fromWarehouse;


    public OutboundShipment(int shippingId, Date shippingDate, int amountOfPhones, int amountOfPads, int amountOfWatches, int fromWarehouse){
        this.shippingId = shippingId;
        this.shippingDate = shippingDate;
        this.amountOfPhones = amountOfPhones;
        this.amountOfPads = amountOfPads;
        this.amountOfWatches = amountOfWatches;
        this.fromWarehouse = fromWarehouse;
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

    public int getFromWarehouse() {
        return fromWarehouse;
    }
}
