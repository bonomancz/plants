package cz.bonoman.plants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {
    private String name, notes;
    private LocalDate planted, watering;
    private int id, frequencyOfWatering;
    private static int nextId = 0;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.planted = planted;
        this.setFrequencyOfWatering(frequencyOfWatering);
        this.setWatering(watering);
        this.notes = notes;
        this.id = nextId++;
    }

    public Plant(){
        this.notes = "";
        this.watering = LocalDate.now();
    }

    public Plant(String name){
        this.name = name;
        this.notes = "";
        this.watering = LocalDate.now();
        this.frequencyOfWatering = 7;
        this.planted = LocalDate.now();
    }

    public String getWateringInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate nextWatering = watering.plusDays(this.frequencyOfWatering);
        return("Name: " + this.name + ", Watering: " + this.watering.format(formatter) + ", Next recommended watering: " + nextWatering.format(formatter));
    }

    public int getFrequencyOfWatering() {return frequencyOfWatering;}
    public String getName() {return name;}
    public void setName(String input){ this.name = input;}
    public String getNotes() {return notes;}
    public void setNotes(String input){this.notes = input;}
    public LocalDate getPlanted() {return planted;}
    public LocalDate getWatering() {return watering;}
    public int getId() {return id;}

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException{
        if(frequencyOfWatering < 1){
            throw new PlantException("(" + this.name + ") Watering frequency can't be lower than once per day.");
        }else {
            this.frequencyOfWatering = frequencyOfWatering;
        }
    }

    public void setWatering(LocalDate watering) throws PlantException{
        if(watering.isBefore(this.planted)){
            throw new PlantException("(" + this.name + ") Watering date can't be older than planting date.");
        }else {
            this.watering = watering;
        }
    }
}
