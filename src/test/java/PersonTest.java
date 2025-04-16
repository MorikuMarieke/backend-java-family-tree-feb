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
}