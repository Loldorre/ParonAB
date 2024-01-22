package APIs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;


public class UI {

     static Scanner scanner = new Scanner(System.in);
     static UI methodAccess = new UI();

     static int menuChoice;
     int warehouseId_showInventory;
     int warehouseId_regOutbound;
     int warehouseId_regInbound;
     int amountOfPhones;
     int amountOfPads;
     int amountOfWatches;
     public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        log.info("open");
        label:
        do{
            System.out.println("-------------MENU----------");
            System.out.println("1: Show warehouse inventory");
            System.out.println("2: Register outbound shipment");
            System.out.println("3: Register inbound shipment");
            System.out.println("4: Exit");
            System.out.println("---------------------------");

            System.out.println("Enter menu choice: ");
            menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    //Menu choice 1: show inventory from warehouse. 1=Capurtino, 2=Norrköping, 3=Frankfurt, 4=All
                    methodAccess.showInventory();
                    break;
                case 2:
                    //Menu choice 2: register outbound shipment.
                    methodAccess.regOutboundShipment();
                    break;
                case 3:
                    //Menu choice 2: register inbound shipment.
                    methodAccess.regInboundShipment();
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

    public void showInventory (){
        System.out.println("Enter warehouse ID: ");
        warehouseId_showInventory = scanner.nextInt();
        ControllerApi.showInventory(warehouseId_showInventory);
    }

    public void regOutboundShipment() {
        System.out.println("Enter sending warehouse ID: ");
        warehouseId_regOutbound = scanner.nextInt();

        if(warehouseId_regOutbound == 1 || warehouseId_regOutbound == 2 || warehouseId_regOutbound == 3) {

            System.out.println("Enter amount of products being sent");
            System.out.println("Amount of jTelefoner: ");
            amountOfPhones = scanner.nextInt();
            System.out.println("Amount of jPlattor:");
            amountOfPads = scanner.nextInt();
            System.out.println("Amount of Päronklockor: ");
            amountOfWatches = scanner.nextInt();

            ControllerApi.regOutboundShipment(warehouseId_regOutbound, amountOfPhones, amountOfPads, amountOfWatches);
        } else {
            System.out.println("Error: wrong warehouse ID");
        }
    }

    public void regInboundShipment() {
        System.out.println("Enter receiving warehouse ID: ");
        warehouseId_regInbound = scanner.nextInt();

        if(warehouseId_regInbound == 1 || warehouseId_regInbound == 2 || warehouseId_regInbound == 3) {

            System.out.println("Enter amount of products being received");
            System.out.println("Amount of jTelefoner: ");
            amountOfPhones = scanner.nextInt();
            System.out.println("Amount of jPlattor:");
            amountOfPads = scanner.nextInt();
            System.out.println("Amount of Päronklockor: ");
            amountOfWatches = scanner.nextInt();

            ControllerApi.regInboundShipment(warehouseId_regInbound, amountOfPhones, amountOfPads, amountOfWatches);
        } else {
            System.out.println("Error: wrong warehouseId");
        }
    }

}
