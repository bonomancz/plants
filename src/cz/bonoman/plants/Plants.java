package cz.bonoman.plants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import cz.bonoman.plants.PlantException;

public class Plants {
    private String name, notes;
    private LocalDate planted, watering;
    private int frequencyOfWatering;

    public Plants(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering){
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public Plants(){
        this.notes = "";
        this.watering = LocalDate.now();
    }

    public Plants(String name){
        this.name = name;
        this.notes = "";
        this.watering = LocalDate.now();
        this.frequencyOfWatering = 7;
        this.planted = LocalDate.now();
    }

    public int getFrequencyOfWatering() {return frequencyOfWatering;}
    public String getName() {return name;}
    public String getNotes() {return notes;}
    public LocalDate getPlanted() {return planted;}
    public LocalDate getWatering() {return watering;}

    public String getWateringInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate nextWatering = watering.plusDays(this.frequencyOfWatering);
        return(this.name + ", " + this.watering.format(formatter) + ", " + nextWatering.format(formatter));
    }

}
