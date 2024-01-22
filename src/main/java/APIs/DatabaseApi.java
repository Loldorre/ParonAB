package APIs;

import ObjClasses.InboundShipment;
import ObjClasses.OutboundShipment;
import ObjClasses.Warehouse;

import java.util.*;
import java.sql.*;


public class DatabaseApi {
    static Connection con;

    DatabaseApi() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "Paron2024");
        } catch (Exception e) {
            UI.log.error(e);
        }
    }

    public static Warehouse[] showInventory(int warehouseId_showInventory) {
        DatabaseApi checkConn = new DatabaseApi();
        ArrayList<Warehouse> warehouseArrayList = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String getWarehouse = "SELECT * FROM warehouse WHERE warehouseId=" + warehouseId_showInventory + ";";
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


    public static ArrayList<OutboundShipment> regOutboundShipment(int shippingId, int warehouseId_regOutbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        DatabaseApi accessDB = new DatabaseApi();
        ArrayList<OutboundShipment> newShipment = new ArrayList<>();

        try {
            //Insert data from database
            String insertQuery = "insert into outboundshipment (shippingId, amountOfPhones, amountOfPads, amountOfWatches, fromWarehouseId) " + "values (" + shippingId + "," + amountOfPhones + "," + amountOfPads + "," + amountOfWatches + "," + warehouseId_regOutbound + ");";

            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.execute();
            removeFromWarehouseInv(warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);


            //Get data from database
            Statement stmtSelect = con.createStatement();
            String getInbound = "SELECT * FROM outboundshipment WHERE shippingId=" + shippingId + ";";
            ResultSet rS = stmtSelect.executeQuery(getInbound);
            while (rS.next()) {
                newShipment.add(new OutboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("fromWarehouseId")));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }


        return newShipment;
    }


    public static ArrayList<InboundShipment> regInboundShipment(int shippingId, int warehouseId_regInbound, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        DatabaseApi accessDB = new DatabaseApi();

        try {
            String insertQuery = "insert into inboundshipment (shippingId, amountOfPhones, amountOfPads, amountOfWatches, toWarehouseId) " + "values (" + shippingId + "," + amountOfPhones + "," + amountOfPads + "," + amountOfWatches + "," + warehouseId_regInbound + ");";

            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.execute();
            addToWarehouseInv(warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);
        } catch (Exception e) {
            UI.log.error(e);
        }

        ArrayList<InboundShipment> newShipment = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String getInbound = "SELECT * FROM inboundshipment WHERE shippingId=" + shippingId + ";";
            ResultSet rS = stmt.executeQuery(getInbound);
            while (rS.next()) {
                newShipment.add(new InboundShipment(rS.getInt("shippingId"), rS.getDate("shippingDate"), rS.getInt("amountOfPhones"), rS.getInt("amountOfPads"), rS.getInt("amountOfWatches"), rS.getInt("toWarehouseId")));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }


        return newShipment;
    }

    public static void addToWarehouseInv(int warehouseId_addStock, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        DatabaseApi checkConn = new DatabaseApi();
        String query = "update warehouse set inStockPhones=inStockPhones+" + amountOfPhones + ", inStockPads=inStockPads+" + amountOfPads + ", inStockWatches=inStockWatches+" + amountOfWatches + " where warehouseId=" + warehouseId_addStock;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.execute();
        } catch (SQLException e) {
            UI.log.error(e);
        }
    }

    public static void removeFromWarehouseInv(int warehouseId_removeStock, int amountOfPhones, int amountOfPads, int amountOfWatches) {
        DatabaseApi checkConn = new DatabaseApi();
        String query = "update warehouse set inStockPhones=inStockPhones-" + amountOfPhones + ", inStockPads=inStockPads-" + amountOfPads + ", inStockWatches=inStockWatches-" + amountOfWatches + " where warehouseId=" + warehouseId_removeStock;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.execute();
        } catch (SQLException e) {
            UI.log.error(e);
        }
    }

    public static ArrayList<Integer> checkInboundShippingId() {
        DatabaseApi checkConn = new DatabaseApi();

        ArrayList<Integer> existingShippingIds = new ArrayList<Integer>();

        try {
            Statement stmt = con.createStatement();
            String getShippingId = "SELECT shippingId FROM inboundshipment;";
            ResultSet rS = stmt.executeQuery(getShippingId);
            while (rS.next()) {
                existingShippingIds.add(rS.getInt("shippingId"));
            }

        } catch (SQLException e) {
            UI.log.error(e);
        }

        return existingShippingIds;
    }

    public static ArrayList<Integer> checkOutboundShippingId() {
        DatabaseApi checkConn = new DatabaseApi();

        ArrayList<Integer> existingShippingIds = new ArrayList<Integer>();

        try {
            Statement stmt = con.createStatement();
            String getShippingId = "SELECT shippingId FROM outboundshipment;";
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
