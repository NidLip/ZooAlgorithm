package utils;

import models.Animal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler
{
    public static List<Animal> importAnimalsFromCSV(File file) throws IOException
    {
        List<Animal> importedAnimals = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                try
                {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String species = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    boolean isHerbivore = Boolean.parseBoolean(parts[4].trim());
                    importedAnimals.add(new Animal(id, name, species, age, isHerbivore));
                } catch (NumberFormatException e)
                {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e)
                {
                    System.err.println("Error: Not enough data in line: " + line);
                } catch (Exception e)
                {
                    System.err.println("Unexpected error: " + e.getMessage());
                }
            }
        }
        return importedAnimals;
    }
}