package models;

public class Animal
{
    private int id;
    private String name;
    private String species;
    private int age;
    private boolean isHerbivore;

    public Animal(int id, String name, String species, int age, boolean isHerbivore)
    {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.isHerbivore = isHerbivore;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSpecies() { return this.species; }
    public int getAge() { return this.age; }
    public boolean isHerbivore() { return this.isHerbivore; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecies(String species) { this.species = species; }
    public void setAge(int age) { this.age = age; }
    public void setHerbivore(boolean herbivore) { isHerbivore = herbivore; }

    public String getDiet()
    {
        return this.isHerbivore ? "Herbivore" : "Carnivore";
    }

    @Override
    public String toString()
    {
        return "Animal ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Species: " + species + "\n" +
                "Age: " + age + "\n" +
                "Diet: " + (isHerbivore ? "Herbivore" : "Carnivore") + "\n";
    }
}
