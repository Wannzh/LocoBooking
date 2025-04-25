package com.uas.locobooking.services.booking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.booking.BookingRequestDto;
import com.uas.locobooking.dto.booking.BookingResponseDto;
import com.uas.locobooking.dto.booking.CheckPesananDto;
import com.uas.locobooking.models.Booking;
import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.models.Schedule;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.repositories.BookingRepository;
import com.uas.locobooking.repositories.CarriageRepository;
import com.uas.locobooking.repositories.CustomersRepository;
import com.uas.locobooking.repositories.ScheduleRepository;
import com.uas.locobooking.repositories.SeatRepository;
import com.uas.locobooking.services.EmailServicelmpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingServiceImpl implements BookingService{
    
    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private CarriageRepository carriageRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private EmailServicelmpl emailServicelmpl;

    @Override
    public BookingResponseDto createPesananTiket(BookingRequestDto dto){
        
        Booking booking = new Booking();
        
        Carriage carriage = carriageRepository.findByCarriageNumber(dto.getCarriageNumber()).orElse(null);
        System.out.println("cek800"+carriage);
        Schedule schedule = scheduleRepository.findByScheduleCode(dto.getScheduleCode())
        .orElseThrow(
            () -> new EntityNotFoundException("Schedule not found with scheduleCode: " + dto.getScheduleCode()));
            
            
            Seat seat = seatRepository.findBySeatNumberAndCarriage(dto.getSeatNumber(),carriage).orElse(null);
            System.out.println("cek 500");
            Customer customer = customersRepository.findByEmail(dto.getUsername()).orElse(null);
            


        booking.setSchedule(schedule);
        booking.setSeat(seat);
        booking.setCarriage(carriage);
        booking.setCustomer(customer);
        bookingRepository.save(booking);
        emailServicelmpl.successfulTicketBooking();
        
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setId(booking.getId());
        bookingResponseDto.setCarriage(booking.getCarriage());
        bookingResponseDto.setSchedule(booking.getSchedule());
        bookingResponseDto.setSeat(booking.getSeat());
        bookingResponseDto.setCustomer(booking.getCustomer());

        return null;

    }

    @Override
    public List<CheckPesananDto> checkPesananTiket(String email) {
    List<Booking> bookingList = bookingRepository.findBookingsByCustomerEmail(email);
    List<CheckPesananDto> checkPesananDtoList = new ArrayList<>();

    for (Booking booking : bookingList) {
        CheckPesananDto dto = new CheckPesananDto();
        dto.setName(booking.getCustomer().getName());
        dto.setSeatNumber(booking.getSeat().getSeatNumber());
        dto.setCarriageNumber(booking.getCarriage().getCarriageNumber());
        dto.setPrice(booking.getCarriage().getPrice());
        dto.setDepartureTime(booking.getSchedule().getDepartureTime());
        dto.setIdBooking(booking.getId());
        System.out.println("cek"+dto);
        checkPesananDtoList.add(dto);
    }

    return checkPesananDtoList;
}

  @Override
public void batalPemesanan(String bookingId){
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new RuntimeException("Booking not found"));

    emailServicelmpl.successCancel();

    bookingRepository.delete(booking);
}

    
}
