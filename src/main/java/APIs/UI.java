package APIs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;


public class UI {


     static Scanner scanner = new Scanner(System.in);
     static UI methodAccess = new UI();
    ControllerApi controllerAccess = new ControllerApi();


     static int menuChoice;
     int warehouseId_showInventory;
     int warehouseId_regOutbound;
     static int warehouseId_regInbound;
     int amountOfPhones;
     int amountOfPads;
     int amountOfWatches;
     public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        log.info("open");
        label:
        do{
            System.out.println("\n-------------MENU----------");
            System.out.println("1: Show warehouse inventory");
            System.out.println("2: Outbound shipments");
            System.out.println("3: Inbound shipments");
            System.out.println("4: Exit");
            System.out.println("---------------------------");

            System.out.println("Enter menu choice: ");
            menuChoice = scanner.nextInt();
            System.out.println();

            switch (menuChoice) {
                case 1:
                    //Menu choice 1: show inventory from warehouse. 1=Cupertino, 2=Norrköping, 3=Frankfurt, 4=All
                    methodAccess.showInventory();
                    break;
                case 2:
                    //Menu choice 2: register outbound shipment.
                    methodAccess.menuOutboundShipment();
                    break;
                case 3:
                    //Menu choice 2: register inbound shipment.
                    methodAccess.menuInboundShipment();
                    break;
                case 4:
                    break label;
                default:
                    System.out.println("Error: Invalid menu choice");
                    break;
            }
        }while(menuChoice != 4);
    }

    private UI(){
    }

    //Shows inventory of a specific warehouse
    public void showInventory (){
        System.out.println("Enter warehouse ID: ");
        warehouseId_showInventory = scanner.nextInt();
        controllerAccess.showInventory(warehouseId_showInventory);
    }

    // method allows user to choose between different actions (show, reg, delete) regarding outbound shipments
    public void menuOutboundShipment() {

        System.out.println("/////////Outbound shipment/////////");
        System.out.println("1: Register outbound shipment");
        System.out.println("2: Show outbound shipments");
        System.out.println("3: Delete outbound shipment");
        int outMenuChoice = scanner.nextInt();

        if(outMenuChoice==1) {

            System.out.println("Enter warehouse Id: ");
            warehouseId_regOutbound = scanner.nextInt();

            if (warehouseId_regOutbound == 1 || warehouseId_regOutbound == 2 || warehouseId_regOutbound == 3) {

                System.out.println("Enter amount of products being sent");
                System.out.println("Amount of jTelefoner: ");
                amountOfPhones = scanner.nextInt();
                System.out.println("Amount of jPlattor:");
                amountOfPads = scanner.nextInt();
                System.out.println("Amount of Päronklockor: ");
                amountOfWatches = scanner.nextInt();

                controllerAccess.regOutboundShipment(warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);

            } else {
                System.out.println("Error: wrong warehouse ID");
            }
        } else if (outMenuChoice==2) {
            controllerAccess.showOutboundShipment();
        } else if (outMenuChoice==3) {
            System.out.println("Enter shipping Id of the shipment you would like to remove: ");
            int delshippingId = scanner.nextInt();
            controllerAccess.delOutboundshipment(delshippingId);
        }
    }

    // menu allows user to show, reg or delete inbound shipments
    public void menuInboundShipment() {
        System.out.println("/////////Inbound shipment/////////");
        System.out.println("1: Register inbound shipment");
        System.out.println("2: Show inbound shipments");
        System.out.println("3: Delete inbound shipment");
        int inMenuChoice = scanner.nextInt();

        if (inMenuChoice == 1) {
            regInbound();
        } else if (inMenuChoice == 2) {
            controllerAccess.showInboundShipment();
        } else if (inMenuChoice == 3) {
            System.out.println("Enter shipping Id of the shipment you would like to remove: ");
            int delshippingId = scanner.nextInt();
            controllerAccess.delInboundshipment(delshippingId);
        }
    }

        // User registers an inbound shipment
        public void regInbound(){
            System.out.println("Enter warehouse Id: ");
            warehouseId_regInbound = scanner.nextInt();

            if (warehouseId_regInbound == 1 || warehouseId_regInbound == 2 || warehouseId_regInbound == 3) {

                System.out.println("Enter amount of products being received");
                System.out.println("jTelefoner: ");
                int amountOfPhones = scanner.nextInt();
                System.out.println("jPlattor:");
                int amountOfPads = scanner.nextInt();
                System.out.println("Päronklockor: ");
                int amountOfWatches = scanner.nextInt();

                controllerAccess.regInboundShipment(warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);

            } else {
                System.out.println("Error: wrong warehouse ID");
            }
        }


}
