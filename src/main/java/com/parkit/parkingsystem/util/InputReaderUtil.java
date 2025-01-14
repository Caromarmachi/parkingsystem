package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in,"ISO-8859-1");
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    private boolean simulation;
    private int input;
    private String vehiculeNumber;
    
    
    public InputReaderUtil() {
	}
    public InputReaderUtil(boolean simulation, int input, String vehiculeNumber) {
    	this.simulation = simulation;
    	this.input = input;
    	this.vehiculeNumber = vehiculeNumber;
	}

	public int readSelection() {
        try {
        	if (simulation) {
	            int input = this.input;
	            return input;
        	} else {
	            int input = Integer.parseInt(scan.nextLine());
	            return input;
        	}
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            return -1;
        }
    }

    public String readVehicleRegistrationNumber() throws Exception {
        try {
        	if (simulation) {
	            String vehicleRegNumber= this.vehiculeNumber;
	            if(vehicleRegNumber == null || vehicleRegNumber.trim().length()==0) {
	                throw new IllegalArgumentException("Invalid input provided");
	            }
	            return vehicleRegNumber;
        	} else {
	            String vehicleRegNumber= scan.nextLine();
	            if(vehicleRegNumber == null || vehicleRegNumber.trim().length()==0) {
	                throw new IllegalArgumentException("Invalid input provided");
	            }
	            return vehicleRegNumber;
        	}
        }catch(Exception e){
          //  logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            throw e;
        }
    }


}
