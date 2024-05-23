package cz.bonoman.plants;

import java.time.LocalDate;
import java.util.ArrayList;

public class Catalogue {
    ArrayList<Plants> plantsList;

    public Catalogue(){
        this.plantsList = new ArrayList<>();
    }

    public void addToPlantsList(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException{
        this.plantsList.add(new Plants(name, notes, planted, watering, frequencyOfWatering));
    }

    public void removeFromPlantsList(int index){
        this.plantsList.remove(index);
    }

    public Plants getPlantFromList(int index){
       return this.plantsList.get(index);
    }

    public ArrayList<Plants> getPlantsList() {return this.plantsList;}

}
