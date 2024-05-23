package cz.bonoman.plants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plants {
    private String name, notes;
    private LocalDate planted, watering;
    private int id, frequencyOfWatering;
    private static int nextId = 0;

    public Plants(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException{
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.id = nextId++;

        if(frequencyOfWatering < 1){
            throw new PlantException("Watering frequency can't be lower than once per day.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
        if(watering.isBefore(planted)){
            throw new PlantException("Watering date can't be older than planting date.");
        }
        this.watering = watering;
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

    public String getWateringInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate nextWatering = watering.plusDays(this.frequencyOfWatering);
        return(this.name + ", " + this.watering.format(formatter) + ", " + nextWatering.format(formatter));
    }

    public int getFrequencyOfWatering() {return frequencyOfWatering;}
    public String getName() {return name;}
    public String getNotes() {return notes;}
    public LocalDate getPlanted() {return planted;}
    public LocalDate getWatering() {return watering;}
    public int getId() {return id;}
}
