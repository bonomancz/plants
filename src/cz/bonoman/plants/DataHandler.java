package cz.bonoman.plants;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {
    private final String sourceTxtFile, outputTxtFile;
    private final File sourceFile, outputFile;

    public DataHandler(){
        this.sourceTxtFile = "./appData/kvetiny.txt";
        this.outputTxtFile = "./appData/kvetiny_output.txt";
        this.sourceFile = new File(sourceTxtFile);
        this.outputFile = new File(outputTxtFile);
    }

    public ArrayList<Plants> loadPlantsFromStorage() throws PlantException {
        ArrayList<Plants> plants = new ArrayList<>();
        if(this.isReadable(this.sourceFile)){
            for(String record : this.fileRead(this.sourceFile)){
                try {
                    if(isValidPlantRecord(record)) {
                        String[] splPart = record.split("\\t");
                        String name = splPart[0].trim();
                        String notes = splPart[1].trim();
                        int frequencyOfWatering = Integer.parseInt(splPart[2].trim());
                        LocalDate watering = LocalDate.parse(splPart[3].trim());
                        LocalDate planted = LocalDate.parse(splPart[4].trim());
                        plants.add(new Plants(name, notes, planted, watering, frequencyOfWatering));
                    }
                }catch(PlantException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        return plants;
    }

    private boolean isValidPlantRecord(String input) throws PlantException{
        boolean isValidPlantRecord = true;
            String regex = "^[^\\t]+\\t[^\\t]+\\t\\d+\\t\\d{4}-\\d{2}-\\d{2}\\t\\d{4}-\\d{2}-\\d{2}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                isValidPlantRecord = false;
                throw new PlantException("Storage: Ignoring invalid plant record: " + input);
            }
        return isValidPlantRecord;
    }

    private void prepareDataStorage() throws RuntimeException{
        if(!isWriteable(this.outputFile)){
            this.fileCreate(this.outputFile);
        }
    }

    public void savePlantsToStorage(List<Plants> inputList) throws RuntimeException{
        this.prepareDataStorage();
        if(this.isWriteable(this.outputFile)){
            ArrayList<String> writeList = new ArrayList<>();
            for(Plants plant : inputList){
                writeList.add(plant.getName() + "\t" + plant.getNotes() + "\t" + plant.getFrequencyOfWatering() + "\t" + plant.getWatering() + "\t" + plant.getPlanted());
            }
            fileWrite(this.outputFile, writeList);
        }
    }

    public boolean isDataStorageAvailable(){
        boolean isDataStorageAvailable = true;
        try {
            if (!this.isReadable(this.sourceFile)) {
                isDataStorageAvailable = false;
            }
        }catch(RuntimeException e){
            isDataStorageAvailable = false;
            e.getMessage();
        }
        return isDataStorageAvailable;
    }

    private boolean isWriteable(File inputFile){
        boolean isWriteable = true;
        try {
            if (!inputFile.canWrite()) {
                isWriteable = false;
            }
        }catch(RuntimeException e){
            isWriteable = false;
            e.getMessage();
        }
        return isWriteable;
    }

    private boolean isReadable(File inputFile){
        boolean isReadable = true;
        try {
            if (!inputFile.canRead()) {
                isReadable = false;
            }
        }catch(RuntimeException e){
            isReadable = false;
            e.getMessage();
        }
        return isReadable;
    }

    public void fileWrite(File file, List<String> inputLines){
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            for (String line : inputLines){
                outputWriter.println(line);
            }
        }catch(IOException e){
            e.getMessage();
        }
    }

    private void fileClear(File file){
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            outputWriter.print("");
        }catch(IOException e){
            e.getMessage();
        }
    }

    public List<String> fileRead(File file){
        ArrayList<String> StringList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                StringList.add(line);
            }
        }catch(IOException e){
            e.getMessage();
        }
        return StringList;
    }

    private void fileCreate(File file){
        try {
            if(!file.exists()) {
                file.createNewFile();
            }else{
                this.fileClear(file);
            }
        }catch(IOException e){
            e.getMessage();
        }
    }

    public File getSourceFile(){return this.sourceFile;}
    public File getOutputFile(){return this.outputFile;}
}
