import entity.VehicleReservationItem;
import manager.DriverManager;
import org.junit.Before;
import repository.VehicleRepository;
import service.impl.DefaultInvoiceService;
import service.impl.VehicleInventoryImpl;
import service.impl.VehicleRentalService;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotNull;


public class DriverManagerTest {

    @Before
    public void clean() {
        VehicleRepository.vehicleItemMap.clear();
        VehicleRepository.branchVehicleList.clear();
        VehicleRepository.vehicleReservations.clear();
    }

    @Test
    public void AddBranchTestSuccess() {
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);
        assertNotNull(VehicleRepository.branchVehicleList);
        Assert.assertEquals(1,VehicleRepository.branchVehicleList.size());
    }

    @Test(expected = exceptions.BranchAlreadyExistsException.class)
    public void AddBranchTestException() {

        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);
        assertNotNull(VehicleRepository.branchVehicleList);
        Assert.assertEquals(1,VehicleRepository.branchVehicleList.size());

        command = "ADD_BRANCH B1 BUS,BIKE,VAN";
        commandItems =  command.split(" ");
        manager.addBranch(commandItems[1],commandItems[2]);
        Assert.assertEquals(1,VehicleRepository.branchVehicleList.size());
    }

    @Test
    public void AddVehicleTestSuccess() {
        //add branch
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());


        //will get added
        command = "ADD_VEHICLE B1 CAR V2 1000";
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(2,VehicleRepository.vehicleItemMap.size());

    }


    @Test(expected = exceptions.VehicleNotAddedException.class)
    public void AddVehicleTestException_01() {
        //add branch B1
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //try to add in branch B2
        command = "ADD_VEHICLE B2 CAR V1 500"; // wont be added since branch does not exist
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
    }

    @Test(expected = exceptions.VehicleNotAddedException.class)
    public void AddVehicleTestException_02() {

        //add branch B1
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());

        //try to add with same vehicleId
        command = "ADD_VEHICLE B1 VAN V1 50"; //can not add with same vehicle Id
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());
    }

    @Test
    public void BookVehicleTestSuccess() {
        //add branch
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());

        //add one more CAR vehicle with High Rental Price
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V2 1000"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(2,VehicleRepository.vehicleItemMap.size());

        // book a car, should return car with least rental price
        command = "BOOK B1 CAR 14 17";
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        VehicleReservationItem vehicleReservationItem = manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
        assertNotNull(vehicleReservationItem);
        Assert.assertEquals("1500.0",vehicleReservationItem.getReservationAmount().toString());

    }

    @Test(expected = exceptions.VehicleNotBookedException.class)
    public void BookVehicleTestException_01() {
        //add branch
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add a CAR vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());

        //Try to book a VAN
        command = "BOOK B1 VAN 13 15"; // wont be added since B1 does not contain VAN
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
    }

    @Test(expected = exceptions.VehicleNotBookedException.class)
    public void BookVehicleTestException_02() {
        //add branch
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add a CAR vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());

        //Try to book a CAR
        command = "BOOK B1 CAR 13 15";
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        VehicleReservationItem vehicleReservationItem = manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
        assertNotNull(vehicleReservationItem);
        Assert.assertEquals("1000.0",vehicleReservationItem.getReservationAmount().toString());

        //Try to book one more CAR . Should throw Exception
        command = "BOOK B1 CAR 14 15"; // wont be added since B1 does not contain VAN
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
    }

    @Test
    public void displayVehicleTest() {

        //add branch
        String command = "ADD_BRANCH B1 CAR,BIKE,VAN";
        String[]  commandItems =  command.split(" ");
        DriverManager manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        manager.addBranch(commandItems[1],commandItems[2]);

        //add vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 CAR V1 500"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(1,VehicleRepository.vehicleItemMap.size());

        //add a VAN vehicle
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        command = "ADD_VEHICLE B1 VAN V2 50"; //will get added
        commandItems =  command.split(" ");
        manager.addVehicle(commandItems[1],commandItems[2],commandItems[3],commandItems[4]);
        Assert.assertEquals(2,VehicleRepository.vehicleItemMap.size());

        //book a CAR
        command = "BOOK B1 CAR 13 15";
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        VehicleReservationItem vehicleReservationItem = manager.bookVehicle(commandItems[1],commandItems[2],Integer.parseInt(commandItems[3]),Integer.parseInt(commandItems[4]));
        assertNotNull(vehicleReservationItem);
        Assert.assertEquals("1000.0",vehicleReservationItem.getReservationAmount().toString());

        //display both VAN and CAR since Car was booked at 13pm
        command = "DISPLAY_VEHICLES B1 11 12";
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        List<String> vehicleIds = manager.displayAvailableVehicles(commandItems[1],Integer.parseInt(commandItems[2]),Integer.parseInt(commandItems[3]));
        assertNotNull(vehicleIds);
        Assert.assertEquals(2,vehicleIds.size());

        //should be sorted as per rental Price
        Assert.assertEquals("V2",vehicleIds.get(0));
        Assert.assertEquals("V1",vehicleIds.get(1));


        //display only VAN since Car is booked from 13pm to 15pm
        command = "DISPLAY_VEHICLES B1 14 16";
        commandItems =  command.split(" ");
        manager = new DriverManager(new VehicleRentalService(),new VehicleInventoryImpl(new VehicleRepository(), new DefaultInvoiceService()));
        vehicleIds = manager.displayAvailableVehicles(commandItems[1],Integer.parseInt(commandItems[2]),Integer.parseInt(commandItems[3]));
        assertNotNull(vehicleIds);
        Assert.assertEquals(1,vehicleIds.size());
        Assert.assertEquals("V2",vehicleIds.get(0));

    }
}
