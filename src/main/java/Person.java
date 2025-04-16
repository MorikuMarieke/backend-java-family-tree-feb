import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Person {
    private String name;
    private String middleName;
    private String lastName;
    private String sex;
    private int age;
    private Person parent1;
    private Person parent2;
    private Person partner;
    private List<Person> siblings = new ArrayList<>();
    private List<Person> children = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();

    public Person(String name, String lastName, String sex, int age) {
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
    }

    public Person(String name, String middleName, String lastName, String sex, int age) {
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.sex = sex;
        this.age = age;
    }

    //    methods
    public void addParent(Person parent) {
        if (this.parent1 == null) {
            setParent1(parent);
            parent1.addChild(this);
            System.out.println("Added " + parent1.name + " " + parent1.lastName + " as first parent");
        } else if (this.parent2 == null) {
            setParent2(parent);
            parent2.addChild(this);
            System.out.println("Added " + parent2.name + " " + parent2.lastName + " as second parent");
        } else {
            System.out.println("This person already has 2 parents registered.");
            return;
        }

    }

    public void addChild(Person child) {
        this.children.add(child);
        if (child.getParent1() == null) {
            child.setParent1(this);
        } else if (child.getParent2() == null && !Objects.equals(child.getParent1(), this)) {
            child.setParent2(this);
        }

        for (Person possibleSibing : this.children) {
            if (!possibleSibing.equals(child)) {
                child.addSibling(possibleSibing);
                possibleSibing.addSibling(child);
            }
        }
    }

    public void addPet(Pet pet) {
        if (!this.pets.contains(pet)) {
            this.pets.add(pet);
        }
        if (pet.getOwner() == null) {
            pet.setOwner(this);
        } else if (!pet.getOwner().equals(this)) {
            System.out.println("Warning: " + pet.getName() + " already has a legal guardian: " + pet.getOwner().toString());
        }
    }

    public void addSibling(Person sibling) {
        if (!this.siblings.contains(sibling)) {
            this.siblings.add(sibling);
            sibling.addSibling(this);
        }
    }

    public List<Person> getGrandChildren() {
        List<Person> grandChildren = new ArrayList<Person>();
        for (Person child : this.children) {
            grandChildren.addAll(child.getChildren());
        }
        return grandChildren;
    }

    public List<Pet> getPetsOfGrandchildren() {
        List<Pet> petsOfGrandchildren = new ArrayList<>();
        List<Person> grandChildren = this.getGrandChildren();
        for (Person grandchild : grandChildren) {
            petsOfGrandchildren.addAll(grandchild.getPets());
        }
        return petsOfGrandchildren;
    }

    public List<Person> getNieces() {
        List<Person> siblings = this.getSiblings();
        List<Person> nieces = new ArrayList<>();
        for (Person sibling : siblings) {
            for (Person child : sibling.getChildren()) {
                if ("female".equalsIgnoreCase(child.getSex())) {
                    nieces.add(child);
                }
            }
        }
        return nieces;
    }

    public void addPartner(Person partner) {
        this.partner = partner;
        if (partner.getPartner() != partner) {
            partner.setPartner(this);
        }
    }

    @Override
    public String toString() {
        return name + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
