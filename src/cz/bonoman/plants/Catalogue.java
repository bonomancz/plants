package cz.bonoman.plants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Catalogue {
    private ArrayList<Plants> plantsList;
    private DataHandler dataHandler;

    public Catalogue(){
        this.plantsList = new ArrayList<>();
        this.dataHandler = new DataHandler();
    }

    public void generateCatalogue() throws PlantException {
        if(this.dataHandler.isDataStorageAvailable()) {
            System.out.println("Loading plants catalogue from storage.");
            this.plantsList = new ArrayList<Plants>(dataHandler.loadPlantsFromStorage());
            this.fillPlantsList();
            this.removeFromPlantsList(2);
            this.dataHandler.savePlantsToStorage(this.plantsList);
        }else{
            System.out.println("Generating new plants catalogue.");
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

    public String printCatalogue(ArrayList<Plants> inputList){
        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        for(Plants plant : inputList){
            stringBuilder.append("ID ").append(plant.getId());
            stringBuilder.append(": ").append(plant.getName());
            stringBuilder.append(" - ").append(plant.getNotes());
            stringBuilder.append(" Planted: ").append(plant.getPlanted().format(dateTimeFormatter));
            stringBuilder.append(", Watering: ").append(plant.getWatering().format(dateTimeFormatter));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getWateringInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        for(Plants plant : this.getPlantsList()){
            stringBuilder.append("Name: ").append(plant.getName());
            stringBuilder.append(", Watering: ").append(plant.getWatering().format(dateTimeFormatter));
            LocalDate nextRecommendedWatering = plant.getWatering().plusDays(plant.getFrequencyOfWatering());
            stringBuilder.append(", Next recommended watering: ").append(nextRecommendedWatering.format(dateTimeFormatter));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void removeFromPlantsList(int index){
        this.plantsList.remove(index);
    }

    public Plants getPlantFromList(int index){
       return this.plantsList.get(index);
    }

    public ArrayList<Plants> getPlantsList() {return this.plantsList;}

}
