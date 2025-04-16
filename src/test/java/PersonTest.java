import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonTest {


    @Test
    void getGrandChildren_returnsAllGrandchildrenAndExcludesChildren() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 65);
        Person child = new Person("child", "Testson", "male", 45);
        Person grandchild = new Person("grandchild", "Testson", "female", 12);
        Person grandchild2 = new Person("grandchild2", "Testson", "male", 13);
        person.addChild(child);
        child.addChild(grandchild);
        child.addChild(grandchild2);
//        Act
        List<Person> grandchildren = person.getGrandChildren();
//        Assert
        assertFalse(grandchildren.isEmpty());
        assertTrue(grandchildren.contains(grandchild));
        assertTrue(grandchildren.contains(grandchild2));
        assertFalse(grandchildren.contains(child));
        assertEquals(2, grandchildren.size());
        assertEquals(grandchild, grandchildren.get(0));
        assertEquals(grandchild2, grandchildren.get(1));
    }

    @Test
    void addParents_addingParentShouldAlsoAddChildToParent() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 12);
        Person parent1 = new Person("Mommy", "Testson", "female", 40);
        Person parent2 = new Person("Daddy", "Testson", "female", 44);
        Person parent3 = new Person("SecondDaddy", "Testson", "female", 46);
//        Act
        person.addParent(parent1);
        person.addParent(parent2);
        PrintStream mockedPrintStream = mock(PrintStream.class);
        System.setOut(mockedPrintStream);
        person.addParent(parent3);
//        Assert
        assertEquals(person.getParent1(), parent1);
        assertEquals(person.getParent2(), parent2);
        assertTrue(parent1.getChildren().contains(person));
        assertTrue(parent2.getChildren().contains(person));
        verify(mockedPrintStream).println("This person already has 2 parents registered.");
    }

    @Test
    void addChild_addingChildAlsoAddsParent1ToChild() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 44);
        Person child = new Person("child", "Testson", "male", 12);

//        Act
        person.addChild(child);
//        Assert
        assertTrue(person.getChildren().contains(child));
        assertEquals(child.getParent1(), person);
        assertNotEquals(child.getParent2(), person);
    }

    @Test
    void addChild_shouldAddSiblingsToChildrenWithBothSharedParents() {
//        Arrange
        Person person1 = new Person("TestParent1", "Testson", "female", 44);
        Person person2 = new Person("TestParent2", "Testson", "male", 46);
        Person child1 = new Person("child1", "Testson", "male", 12);
        Person child2 = new Person("child2", "Testson", "female", 13);
        Person child3 = new Person("child3", "Testson", "catPerson", 14);
//        Act
        person1.addChild(child1);
        person1.addChild(child2);
        person2.addChild(child2);
        person2.addChild(child3);
//        Assert
        assertTrue(child1.getSiblings().contains(child2));
        assertFalse(child1.getSiblings().contains(child3));

        assertTrue(child2.getSiblings().contains(child1));
        assertTrue(child2.getSiblings().contains(child3));

        assertFalse(child3.getSiblings().contains(child1));
        assertTrue(child3.getSiblings().contains(child2));

        assertFalse(person1.getChildren().contains(child3));
        assertFalse(person2.getChildren().contains(child1));
    }

    @Test
    void addPet_addingPetAlsoAddsOwnerToPet() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 44);
        Pet pet = new Pet("Bobby", 3, "Dog");
//        Act
        person.addPet(pet);
//        Assert
        assertTrue(person.getPets().contains(pet));
        assertEquals(pet.getOwner(), person);
    }

    @Test
    void addSibling_addingSiblingsDirectlyToPersonDoesNotAddsSiblingToOtherSiblingsOfPerson() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 44);
        Person person2 = new Person("TestSibling", "Testson", "male", 43);
        Person person3 = new Person("TestSibling2", "Testson", "female", 42);
//        Act
        person.addSibling(person2);
        person.addSibling(person3);
//        Assert
        assertTrue(person.getSiblings().contains(person2));
        assertTrue(person2.getSiblings().contains(person));
        assertTrue(person.getSiblings().contains(person3));
        assertFalse(person2.getSiblings().contains(person3));
    }


    @Test
    void getPetsOfGrandchildren_shouldReturnAListOfAllPetsOfGrandchildren() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 65);
        Person child = new Person("child", "Testson", "male", 45);
        Person grandchild = new Person("grandchild", "Testson", "female", 12);
        Person grandchild2 = new Person("grandchild2", "Testson", "male", 13);
        Pet pet1 = new Pet("Pickle", 2, "Dog with a sour attitude");
        Pet pet2 = new Pet("Limp Bisquit", 14, "Paraplegic cat");
        Pet pet3 = new Pet("Wiener", 5, "Obviously a dachshund");
        Pet pet4 = new Pet("Gandalf", 55000, "Wizard disguised as bearded dragon");
        person.addChild(child);
        child.addChild(grandchild);
        child.addChild(grandchild2);
        grandchild.addPet(pet1);
        grandchild.addPet(pet2);
        grandchild2.addPet(pet3);
        grandchild2.addPet(pet4);
//        Act
        List<Pet> result = person.getPetsOfGrandchildren();
//        Assert
        assertEquals(4, result.size());
        assertTrue(result.contains(pet1));
        assertTrue(result.contains(pet2));
        assertTrue(result.contains(pet3));
        assertTrue(result.contains(pet4));
    }

    @Test
    void getNieces_shouldReturnListOfDaughtersOfSibling() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 65);
        Person child1 = new Person("child1", "Testson", "male", 45);
        Person child2 = new Person("child2", "Testson", "female", 45);
        Person grandchild = new Person("grandchild", "Testson", "female", 12);
        Person grandchild2 = new Person("grandchild2", "Testson", "male", 13);
        Person grandchild3 = new Person("grandchild", "Testson", "female", 11);

        person.addChild(child1);
        person.addChild(child2);
        child1.addChild(grandchild);
        child1.addChild(grandchild2);
        child1.addChild(grandchild3);
//        Act
        List<Person> niecesOfChild2 = child2.getNieces();
//        Assert
        assertEquals(2, niecesOfChild2.size());
        assertTrue(niecesOfChild2.contains(grandchild));
        assertTrue(niecesOfChild2.contains(grandchild3));
        assertFalse(niecesOfChild2.contains(grandchild2));
    }

    @Test
    void addPartner_shouldAddPartnerToBothPersons() {
//        Arrange
        Person person1 = new Person("Person1", "Testson", "female", 44);
        Person person2 = new Person("Person1", "Testson", "male", 43);
//        Act
        person1.addPartner(person2);
//        Assert
        assertEquals(person1, person2.getPartner());
        assertEquals(person2, person1.getPartner());
    }
}