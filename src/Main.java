import cz.bonoman.plants.Catalogue;
import cz.bonoman.plants.PlantException;

public class Main {

    private static final Catalogue catalogue = new Catalogue();

    public static void main(String[] args){
        String report = "\n### PLANTS WATERING PROGRAM ###\n";
        try{
            catalogue.generateCatalogue();
            catalogue.sortCatalogue("name");
            report += "\nWatering plan (sorted by name):\n" + catalogue.getWateringInfo();
            catalogue.sortCatalogue("watering");
            report += "\nWatering plan (sorted by watering):\n" + catalogue.getWateringInfo();
            System.out.println(report);
        }catch(PlantException ex){
            System.out.println(ex.getMessage());
        }
    }

}
