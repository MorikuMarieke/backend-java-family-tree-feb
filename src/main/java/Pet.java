import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pet {
    private String name;
    private int age;
    private String species;
    private Person owner;

    public Pet(String name, int age, String species) {
        this.name = name;
        this.age = age;
        this.species = species;
    }

}
