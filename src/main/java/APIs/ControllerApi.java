package APIs;


import ObjClasses.InboundShipment;
import ObjClasses.OutboundShipment;
import ObjClasses.Warehouse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ControllerApi {

    public ControllerApi(){}

    public static void showInventory(int warehouseId_showInventory){

        try {
            Warehouse[] warehouses = DatabaseApi.showInventory(warehouseId_showInventory);

            for (Warehouse warehouse : warehouses) {
                System.out.println("Warehouse ID: " + warehouse.getWarehouseId() + "\n" +
                        "Warehouse City: " + warehouse.getWarehouseCity() + "\n" +
                        "Phones in stock: " + warehouse.getInStockPhones() + "\n" +
                        "Pads in stock: " + warehouse.getInStockPads() + "\n" +
                        "Watches in stock: " + warehouse.getInStockWatches() + "\n\n");
            }
            if(warehouses.length==0){
                UI.log.info("No result");
                System.out.println("No result matching warehouse ID");
            }

        }catch (Exception e){
            UI.log.error(e);
        }
    }

    public static void regOutboundShipment(int warehouseId_regOutbound, int amountOfPhones, int amountOfPads, int amountOfWatches){

        try {
            ArrayList<OutboundShipment> newShipment = DatabaseApi.regOutboundShipment(genRandomOutboundShippingId(),warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);

            System.out.println("Shipping ID: "+newShipment.get(0).getShippingId());
            System.out.println("Date: "+newShipment.get(0).getShippingDate());
            System.out.println("Warehouse: "+newShipment.get(0).getFromWarehouse());
            System.out.println("jTelefon: "+newShipment.get(0).getAmountOfPhones());
            System.out.println("jPlatta: "+newShipment.get(0).getAmountOfPads());
            System.out.println("Päronklocka: "+newShipment.get(0).getAmountOfWatches());

        } catch (Exception e) {
            UI.log.error(e);
        }
    }

    public static void regInboundShipment(int warehouseId_regInbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        try {
            ArrayList<InboundShipment> newShipment = DatabaseApi.regInboundShipment(genRandomInboundShippingId(),warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);

            System.out.println("Shipping ID: "+newShipment.get(0).getShippingId());
            System.out.println("Date: "+newShipment.get(0).getShippingDate());
            System.out.println("Warehouse: "+newShipment.get(0).getToWarehouse());
            System.out.println("jTelefon: "+newShipment.get(0).getAmountOfPhones());
            System.out.println("jPlatta: "+newShipment.get(0).getAmountOfPads());
            System.out.println("Päronklocka: "+newShipment.get(0).getAmountOfWatches());

        } catch (Exception e) {
            UI.log.error(e);
        }

    }

    public static int genRandomInboundShippingId(){

        Random ranNum = new Random();
        int shippingId;

        do {
            shippingId = ranNum.nextInt(999999999);
        }while(DatabaseApi.checkInboundShippingId().contains(shippingId));

        return shippingId;
    }

    public static int genRandomOutboundShippingId(){

        Random ranNum = new Random();
        int shippingId;

        do {
            shippingId = ranNum.nextInt(999999999);
        }while(DatabaseApi.checkOutboundShippingId().contains(shippingId));

        return shippingId;
    }
}
