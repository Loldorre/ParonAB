package APIs;


import ObjClasses.InboundShipment;
import ObjClasses.OutboundShipment;
import ObjClasses.Warehouse;

import java.util.ArrayList;
import java.util.Random;

public class ControllerApi {
    DatabaseAPI databaseAccess = new DatabaseAPI();
    public ControllerApi(){}


    // retries row of specified warehouse
    public void showInventory(int warehouseId_showInventory){

        try {
            Warehouse[] warehouses = databaseAccess.showInventory(warehouseId_showInventory);

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

    // sends user input and randomly generated shipping ID and request databas api to insert it into the database. Then receives the registered
    // shipment to display to user.
    public void regOutboundShipment(int warehouseId_regOutbound, int amountOfPhones, int amountOfPads, int amountOfWatches){

        try {
            ArrayList<OutboundShipment> newShipment = databaseAccess.regOutboundShipment(genRandomOutboundShippingId(),warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);
            System.out.println("\n///////////////Shipment registered///////////////");
            System.out.println("Shipping ID: \t\t"+newShipment.get(0).getShippingId());
            System.out.println("Date: \t\t\t\t"+newShipment.get(0).getShippingDate());
            System.out.println("Warehouse: \t\t\t"+newShipment.get(0).getFromWarehouse());
            System.out.println("jTelefon: \t\t\t"+newShipment.get(0).getAmountOfPhones());
            System.out.println("jPlatta: \t\t\t"+newShipment.get(0).getAmountOfPads());
            System.out.println("Päronklocka: \t\t"+newShipment.get(0).getAmountOfWatches()+"\n\n");

        } catch (Exception e) {
            UI.log.error(e);
        }
    }
    public void showOutboundShipment(){
        ArrayList<OutboundShipment> outboundList;

        try {
            outboundList = databaseAccess.showOutboundShipment();
            for (OutboundShipment outboundShipment : outboundList) {
                System.out.println("Shipping Id: " + outboundShipment.getShippingId() +
                        ", Date: " + outboundShipment.getShippingDate() +
                        ", From: " + outboundShipment.getFromWarehouse() +
                        ", jTelefoner: " + outboundShipment.getAmountOfPhones() +
                        ", jPlattor: " + outboundShipment.getAmountOfPads() +
                        ", Päronklockor: " + outboundShipment.getAmountOfWatches());
            }
        }catch(Exception e){
            UI.log.warn(e);
        }
    }

    public void delOutboundshipment(int shippingId){
        try {
            databaseAccess.deleteOutboundShipment(shippingId);
            showOutboundShipment();
        }catch(Exception e){
            UI.log.warn(e);
        }
    }

    public void regInboundShipment(int warehouseId_regInbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        try {
            ArrayList<InboundShipment> newShipment = databaseAccess.regInboundShipment(genRandomInboundShippingId(),warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);

            System.out.println("\n///////////////Shipment registered///////////////");
            System.out.println("Shipping ID: \t\t"+newShipment.get(0).getShippingId());
            System.out.println("Date: \t\t\t\t"+newShipment.get(0).getShippingDate());
            System.out.println("Warehouse: \t\t\t"+newShipment.get(0).getToWarehouse());
            System.out.println("jTelefon: \t\t\t"+newShipment.get(0).getAmountOfPhones());
            System.out.println("jPlatta: \t\t\t"+newShipment.get(0).getAmountOfPads());
            System.out.println("Päronklocka: \t\t"+newShipment.get(0).getAmountOfWatches()+"\n\n");

        } catch (Exception e) {
            UI.log.error(e);
        }
    }

    public void showInboundShipment(){
        ArrayList<InboundShipment> inboundList;

        try {
            inboundList = databaseAccess.showInboundShipment();
            for (InboundShipment inboundShipment : inboundList) {
                System.out.println("Shipping Id: " + inboundShipment.getShippingId() +
                        ", Date: " + inboundShipment.getShippingDate() +
                        ", From: " + inboundShipment.getToWarehouse() +
                        ", jTelefoner: " + inboundShipment.getAmountOfPhones() +
                        ", jPlattor: " + inboundShipment.getAmountOfPads() +
                        ", Päronklockor: " + inboundShipment.getAmountOfWatches());
            }
        }catch(Exception e){
            UI.log.warn(e);
        }
    }

    public void delInboundshipment(int shippingId){
        try {
            databaseAccess.deleteInboundShipment(shippingId);
            showInboundShipment();
        }catch(Exception e){
            UI.log.warn(e);
        }
    }

    public int genRandomInboundShippingId(){

        Random ranNum = new Random();
        int shippingId;

        do {
            shippingId = ranNum.nextInt(999999999);
        }while(databaseAccess.checkInboundShippingId().contains(shippingId));

        return shippingId;
    }

    public int genRandomOutboundShippingId(){

        Random ranNum = new Random();
        int shippingId;

        do {
            shippingId = ranNum.nextInt(999999999);
        }while(databaseAccess.checkOutboundShippingId().contains(shippingId));

        return shippingId;
    }
}
