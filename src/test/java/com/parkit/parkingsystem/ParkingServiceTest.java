package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		
	}

	@Test
	public void processExitingVehicleTest() {
		try {
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void testEqualityParking() {
		ParkingSpot parkingSpotA = new ParkingSpot(1, ParkingType.CAR, true);
		ParkingSpot parkingSpotB = new ParkingSpot(1, ParkingType.CAR, true);
		assertEquals(parkingSpotA, parkingSpotB); // on teste l'override de la methode equals de parkingSpot
		parkingSpotA.setParkingType(ParkingType.BIKE);
		assertEquals(parkingSpotA, parkingSpotB); // on s'assure que le changement de type ne change pas l'egalite
		parkingSpotA.setId(2);
		assertNotEquals(parkingSpotA, parkingSpotB); // on verifie qu'ils ne sont plus égaux si on change le nombre
	}
	
	@Test
	public void testReadSelection() {
        InputReaderUtil inputReaderUtil2 = new InputReaderUtil(true,1,"ABCDEF");
		int option = inputReaderUtil2.readSelection();
		assertTrue(option==1);
	}
	
	@Test
	public void testReadVehicleRegistrationNumber() {
        InputReaderUtil inputReaderUtil2 = new InputReaderUtil(true,1,"ABCDEF");
        String vehiculeNumber = "";
		try {
			vehiculeNumber = inputReaderUtil2.readVehicleRegistrationNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(vehiculeNumber.equals("ABCDEF"));
	}
	
	@Test
	public void testReadVehicleRegistrationNumberException() {
        InputReaderUtil inputReaderUtil2 = new InputReaderUtil(true,1,null);
        String vehiculeNumber = null;
		try {
			vehiculeNumber = inputReaderUtil2.readVehicleRegistrationNumber();
		} catch (Exception e) {
			System.out.println("Erreur volontaire pour le test. Ignorée.");
		}
		assertTrue(vehiculeNumber==null);
		
	}

}
