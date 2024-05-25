import cz.bonoman.plants.Catalogue;
import cz.bonoman.plants.PlantException;

public class Main {

    private static final Catalogue catalogue = new Catalogue();

    public static void main(String[] args){
        try{
            catalogue.generateCatalogue();
            System.out.println("\n### WATERING PLAN:\n" + catalogue.getWateringInfo());
        }catch(PlantException ex){
            System.out.println(ex.getMessage());
        }
    }

}
