package cz.bonoman.plants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Catalogue {
    private ArrayList<Plants> plantsList;
    private DataHandler dataHandler;

    public Catalogue(){
        this.plantsList = new ArrayList<>();
        this.dataHandler = new DataHandler();
    }

    public void generateCatalogue() throws PlantException {
        if(this.dataHandler.isDataStorageAvailable()) {
            System.out.println("\nData storage available.\nLoading plants catalogue from storage.");
            this.plantsList = new ArrayList<Plants>(dataHandler.loadPlantsFromStorage());
            this.fillPlantsList();
            this.removeFromPlantsList(2);
            this.dataHandler.savePlantsToStorage(this.plantsList);
        }else{
            System.out.println("\nData storage not available.\nGenerating new plants catalogue.");
            this.fillPlantsList();
        }
    }

    public void fillPlantsList() throws PlantException{
        this.addToPlantsList("Šplhavnice zlatá", "Někdy se nazývá Potos.", LocalDate.of(2024, 5, 24), LocalDate.of(2024, 5, 24), 2);
        this.addToPlantsList("Diefenbachie", "Jedovatá rostlina.", LocalDate.of(2024, 5, 3), LocalDate.of(2024, 5, 4), 2);
        //this.addToPlantsList("Fíkus", "Fíkovník", LocalDate.of(2024, 5, 18), LocalDate.of(2024, 5, 19), 1);
        //this.addToPlantsList("Kulkas Zamiolistý", "Po požití je mírně toxická.", LocalDate.of(2024, 5, 10), LocalDate.of(2024, 5, 10), 1);
    }

    public void addToPlantsList(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        try {
            this.plantsList.add(new Plants(name, notes, planted, watering, frequencyOfWatering));
        }catch(PlantException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getWateringInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Plants plant : this.getPlantsList()){
            stringBuilder.append(plant.getWateringInfo());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void sortCatalogue(String sortBy){
        switch(sortBy) {
            case "name":        this.plantsList.sort(Comparator.comparing(Plants::getName));
                                break;
            case "watering":    this.plantsList.sort(Comparator.comparing(Plants::getWatering));
                                break;
            case "planted":     this.plantsList.sort(Comparator.comparing(Plants::getPlanted));
                                break;
            default:            this.plantsList.sort(Comparator.comparing(Plants::getId));
                                break;
        }
    }

    public void removeFromPlantsList(int index){
        this.plantsList.remove(index);
    }

    public Plants getPlantFromList(int index){
       return this.plantsList.get(index);
    }

    public ArrayList<Plants> getPlantsList() {return this.plantsList;}

}
