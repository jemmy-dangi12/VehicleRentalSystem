package service;

import entity.Invoice;
import entity.VehicleReservationItem;

public interface InvoiceService {

     Invoice computeInvoice(VehicleReservationItem vehicleReservationItem);

}
