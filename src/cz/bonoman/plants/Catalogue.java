package cz.bonoman.plants;

import java.rmi.server.ExportException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Catalogue {
    private ArrayList<Plant> plantsList;
    private DataHandler dataHandler;

    public Catalogue(){
        this.plantsList = new ArrayList<>();
        this.dataHandler = new DataHandler();
    }

    public void generateCatalogue() throws PlantException {
        if(this.dataHandler.isDataStorageAvailable()) {
            System.out.println("\nData storage available.\nLoading plants catalogue from storage.");
            this.plantsList = new ArrayList<Plant>(dataHandler.loadPlantsFromStorage());
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
            this.plantsList.add(new Plant(name, notes, planted, watering, frequencyOfWatering));
        }catch (PlantException e){
            System.err.println("Catalogue - addToPlantsList(): " + e.getMessage());
            //throw new PlantException("addToPlantsList(): " + e.getMessage());
        }
    }

    public String getWateringInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Plant plant : this.getPlantsList()){
            stringBuilder.append(plant.getWateringInfo());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void sortCatalogue(String sortBy){
        switch(sortBy) {
            case "name":        this.plantsList.sort(Comparator.comparing(Plant::getName));
                                break;
            case "watering":    this.plantsList.sort(Comparator.comparing(Plant::getWatering));
                                break;
            case "planted":     this.plantsList.sort(Comparator.comparing(Plant::getPlanted));
                                break;
            default:            this.plantsList.sort(Comparator.comparing(Plant::getId));
                                break;
        }
    }

    public void waterPlant(int index){
        try {
            this.plantsList.get(index).setWatering(LocalDate.now());
        }catch (Exception e){
            System.err.println("waterPlant(): " + e.getMessage());
        }
    }

    public void removeFromPlantsList(int index){
        this.plantsList.remove(index);
    }

    public Plant getPlantFromList(int index){
       return this.plantsList.get(index);
    }

    public ArrayList<Plant> getPlantsList() {return this.plantsList;}

}
