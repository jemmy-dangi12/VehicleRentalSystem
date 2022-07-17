package service.impl;

import entity.Invoice;
import entity.VehicleReservationItem;
import service.InvoiceService;

import java.time.Duration;
import java.time.LocalDateTime;

public class DefaultInvoiceService implements InvoiceService {

    @Override
    public Invoice computeInvoice(VehicleReservationItem vehicleReservationItem) {
        Invoice invoice = new Invoice();
        Duration duration ;
        if(vehicleReservationItem.getReturnDate() == null) {
            duration = Duration.between(vehicleReservationItem.getBookedFromDate(),vehicleReservationItem.getBookedTillDate());
        } else {
            duration = Duration.between(vehicleReservationItem.getBookedTillDate(),vehicleReservationItem.getReturnDate());
        }

        Double hours = Math.ceil(duration.toHours());
        if(hours == 0) {
            return null;
        }
        Double rentalCost = hours * vehicleReservationItem.getVehicleItem().getHourlyRentalCost();
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setTotalAmount(rentalCost);
        invoice.setInvoiceId(vehicleReservationItem.getReservationNo());
        return invoice;
    }
}
