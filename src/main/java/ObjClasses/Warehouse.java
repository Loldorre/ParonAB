package ObjClasses;

public class Warehouse {

    private int warehouseId;
    private String warehouseCity;
    private int inStockPhones;
    private int inStockPads;
    private int inStockWatches;


    public Warehouse(int warehouseId, String warehouseCity, int inStockPhones, int inStockPads, int inStockWatches){

        this.warehouseId = warehouseId;
        this.warehouseCity = warehouseCity;
        this.inStockPhones = inStockPhones;
        this.inStockPads = inStockPads;
        this.inStockWatches = inStockWatches;

    }

     public int getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseCity() {
        return warehouseCity;
    }

    public int getInStockPhones() {
        return inStockPhones;
    }

    public int getInStockPads() {
        return inStockPads;
    }

    public int getInStockWatches() {
        return inStockWatches;
    }
}
