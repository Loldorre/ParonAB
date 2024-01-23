package APIs;

import ObjClasses.InboundShipment;
import ObjClasses.OutboundShipment;
import ObjClasses.Warehouse;

import java.util.*;
import java.sql.*;


public class DatabaseAPI {
    static Connection con;
    static PreparedStatement stmt;




    DatabaseAPI() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "Paron2024");
        } catch (Exception e) {
            UI.log.error(e);
        }
    }

    //Shows all info of a specified warehouse
    public Warehouse[] showInventory(int warehouseId_showInventory) {
        ArrayList<Warehouse> warehouseArrayList = new ArrayList<>();

        try {
            String getWarehouse = "SELECT * FROM warehouse WHERE warehouseId=" + warehouseId_showInventory + ";";
            stmt = con.prepareStatement(getWarehouse);
            ResultSet rS = stmt.executeQuery(getWarehouse);
            while (rS.next()) {
                warehouseArrayList.add(new Warehouse(rS.getInt("warehouseId"), rS.getString("warehouseCity"), rS.getInt("inStockPhones"), rS.getInt("inStockPads"), rS.getInt("inStockWatches")));
            }
        } catch (SQLException e) {
            UI.log.error(e);
        }

        Warehouse[] warehouseArray = new Warehouse[warehouseArrayList.size()];
        warehouseArrayList.toArray(warehouseArray);

        return warehouseArray;
    }


    // Inserts an outbound shipment into database and the retrieves the info to show
    public ArrayList<OutboundShipment> regOutboundShipment(int shippingId, int warehouseId_regOutbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        ArrayList<OutboundShipment> outboundList = new ArrayList<>();

        try {
            //Insert outbound shipment into database and remove stock from warehouse
            removeFromWarehouseInv(warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);
            UI.log.info("efter remove");

            String insertQuery = "insert into outboundshipment (shippingId, amountOfPhones, amountOfPads, amountOfWatches, fromWarehouseId) " + "values (" + shippingId + "," + amountOfPhones + "," + amountOfPads + "," + amountOfWatches + "," + warehouseId_regOutbound + ");";
            stmt = con.prepareStatement(insertQuery);
            stmt.execute();

            //Get newly created outbound shipment
            String getOutbound = "SELECT * FROM outboundshipment WHERE shippingId=" + shippingId + ";";
            ResultSet rS = stmt.executeQuery(getOutbound);
            while (rS.next()) {
                outboundList.add(new OutboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("fromWarehouseId")));
            }
        } catch (SQLException e) {
            UI.log.error(e);
        }

        return outboundList;
    }


    //Get all outbound shipments from database
    public ArrayList<OutboundShipment> showOutboundShipment(){
        ArrayList<OutboundShipment> outboundList = new ArrayList<>();


        try {
            String getOutbound = "SELECT * FROM outboundshipment ORDER BY shippingDate;";
            stmt = con.prepareStatement(getOutbound);
            ResultSet rS = stmt.executeQuery(getOutbound);
            while (rS.next()) {
                outboundList.add(new OutboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("fromWarehouseId")));
            }
        }catch (Exception e){UI.log.error(e);}

        return outboundList;
    }

    public void deleteOutboundShipment(int shippingId){
        ArrayList<OutboundShipment> outboundList = new ArrayList<>();

        // Get newly created outbound shipment
        try {
            String getQuery = "SELECT * FROM outboundshipment WHERE shippingId=" + shippingId + ";";
            stmt = con.prepareStatement(getQuery);
            ResultSet rS = stmt.executeQuery(getQuery);
            while (rS.next()) {
                outboundList.add(new OutboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("fromWarehouseId")));
            }

            // removes from the warehouse stock
            addToWarehouseInv(outboundList.get(0).getFromWarehouse(), outboundList.get(0).getAmountOfPhones(), outboundList.get(0).getAmountOfPads(), outboundList.get(0).getAmountOfWatches());

            // Insert inbound shipment into database
            String delQuery = "DELETE FROM outboundshipment WHERE shippingId="+outboundList.get(0).getShippingId()+";";
            stmt = con.prepareStatement(delQuery);
            stmt.execute();
        } catch (Exception e) {
            UI.log.error(e);
        }
    }


    public ArrayList<InboundShipment> regInboundShipment(int shippingId, int warehouseId_regInbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        ArrayList<InboundShipment> inboundList = new ArrayList<>();


        // Insert inbound shipment into database
        try {
            String insertQuery = "insert into inboundshipment (shippingId, amountOfPhones, amountOfPads, amountOfWatches, toWarehouseId) " + "values (" + shippingId + "," + amountOfPhones + "," + amountOfPads + "," + amountOfWatches + "," + warehouseId_regInbound + ");";
            stmt = con.prepareStatement(insertQuery);
            stmt.execute();
            addToWarehouseInv(warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);
        } catch (Exception e) {
            UI.log.error(e);
        }


        //Get newly created inbound shipment
        try {
            String getInbound = "SELECT * FROM inboundshipment WHERE shippingId=" + shippingId + ";";
            stmt = con.prepareStatement(getInbound);
            ResultSet rS = stmt.executeQuery(getInbound);
            while (rS.next()) {
                inboundList.add(new InboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("toWarehouseId")));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }


        return inboundList;
    }

    public ArrayList<InboundShipment> showInboundShipment(){
        ArrayList<InboundShipment> inboundList = new ArrayList<>();

        try {
            String getInbound = "SELECT * FROM inboundshipment ORDER BY shippingDate;";
            stmt = con.prepareStatement(getInbound);
            ResultSet rS = stmt.executeQuery(getInbound);
            while (rS.next()) {
                inboundList.add(new InboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("toWarehouseId")));
            }
        }catch (Exception e){UI.log.error(e);}

        return inboundList;
    }

    public void deleteInboundShipment(int shippingId){
        ArrayList<InboundShipment> inboundList = new ArrayList<>();

        // Get newly created inbound shipment
        try {
            String getQuery = "SELECT * FROM inboundshipment WHERE shippingId=" + shippingId + ";";
            stmt = con.prepareStatement(getQuery);
            ResultSet rS = stmt.executeQuery(getQuery);
            while (rS.next()) {
                inboundList.add(new InboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("toWarehouseId")));
            }

            // removes from the warehouse stock
            removeFromWarehouseInv(inboundList.get(0).getToWarehouse(), inboundList.get(0).getAmountOfPhones(), inboundList.get(0).getAmountOfPads(), inboundList.get(0).getAmountOfWatches());

        // Insert inbound shipment into database
            String delQuery = "DELETE FROM inboundshipment WHERE shippingId="+inboundList.get(0).getShippingId()+";";
            stmt = con.prepareStatement(delQuery);
            stmt.execute();
        } catch (Exception e) {
            UI.log.error(e);
        }

    }

    public void addToWarehouseInv(int warehouseId_addStock, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        String query = "update warehouse set inStockPhones=inStockPhones+" + amountOfPhones + ", inStockPads=inStockPads+" + amountOfPads + ", inStockWatches=inStockWatches+" + amountOfWatches + " where warehouseId=" + warehouseId_addStock;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.execute();
        } catch (SQLException e) {
            UI.log.error(e);
        }
    }

    public void removeFromWarehouseInv(int warehouseId_removeStock, int amountOfPhones, int amountOfPads, int amountOfWatches) throws SQLException {
        String query = "update warehouse set inStockPhones=inStockPhones-" + amountOfPhones + ", inStockPads=inStockPads-" + amountOfPads + ", inStockWatches=inStockWatches-" + amountOfWatches + " where warehouseId=" + warehouseId_removeStock;

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.execute();

    }

    public ArrayList<Integer> checkInboundShippingId() {
        ArrayList<Integer> existingShippingIds = new ArrayList<>();


        try {
            String getShippingId = "SELECT shippingId FROM inboundshipment;";
            stmt = con.prepareStatement(getShippingId);
            ResultSet rS = stmt.executeQuery(getShippingId);
            while (rS.next()) {
                existingShippingIds.add(rS.getInt("shippingId"));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }

        return existingShippingIds;
    }

    public ArrayList<Integer> checkOutboundShippingId() {
        ArrayList<Integer> existingShippingIds = new ArrayList<>();

        try {
            String getShippingId = "SELECT shippingId FROM outboundshipment;";
            stmt = con.prepareStatement(getShippingId);
            ResultSet rS = stmt.executeQuery(getShippingId);
            while (rS.next()) {
                existingShippingIds.add(rS.getInt("shippingId"));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }

        return existingShippingIds;
    }

}
