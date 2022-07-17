package driver;

import static constants.Constants.ADD_BRANCH;
import static constants.Constants.ADD_VEHICLE;
import static constants.Constants.BOOK_VEHICLE;
import static constants.Constants.DISPLAY_VEHICLES;
import static constants.Constants.FALSE;
import static constants.Constants.FILE_DOES_NOT_EXIST;
import static constants.Constants.TRUE;

import entity.VehicleReservationItem;
import manager.DriverManager;
import repository.VehicleRepository;
import service.impl.DefaultInvoiceService;
import service.impl.VehicleInventoryImpl;
import service.impl.VehicleRentalService;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner file = null;
        try {
            URL resource = Driver.class.getClassLoader().getResource("command.txt");
            if(resource == null) {
                System.out.println(FILE_DOES_NOT_EXIST);
                throw new FileNotFoundException();
            }
            file = new Scanner(new File(resource.toURI()));
        } catch (FileNotFoundException | URISyntaxException e) {
            System.out.println(FILE_DOES_NOT_EXIST);
            throw new RuntimeException();
        }
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        while(file.hasNextLine()){
            String command = file.nextLine();
            String[]  commandItems =  command.split(" ");
            switch (commandItems[0]){
                case ADD_BRANCH:
                    try {
                        manager.addBranch(commandItems[1],commandItems[2]);
                        System.out.println(TRUE);
                    } catch (Exception e) {
                        System.out.println(FALSE);
                    }
                    break;
                case ADD_VEHICLE :
                    try {
                        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
                        System.out.println(TRUE);
                    } catch (Exception e) {
                        System.out.println(FALSE);
                    }
                    break;
                case BOOK_VEHICLE :
                    try {
                        VehicleReservationItem vehicleReservationItem = manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
                        System.out.println(vehicleReservationItem.getReservationAmount());
                    } catch (Exception e) {
                        System.out.println("-1");
                    }
                    break;
                case DISPLAY_VEHICLES :
                    try {
                        List<String> vehicleIds = manager.displayAvailableVehicles(commandItems[1],Integer.parseInt(commandItems[2]),Integer.parseInt(commandItems[3]));
                        System.out.println(vehicleIds);
                    } catch (Exception e) {
                        System.out.println("-1");
                    }
                    break;
                default:
                    System.out.println("Enter a valid command");
            }
        }

    }
}
