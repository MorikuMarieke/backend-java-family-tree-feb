import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {



    @Test
    void getGrandChildren() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 65);
        Person child = new Person("child", "Testson", "male", 45);
        Person grandchild = new Person("grandchild", "Testson", "female", 12);
        Person grandchild2 = new Person("grandchild2", "Testson", "male", )
        person.addChild(child);
        child.addChild(grandchild);
//        Act
        List<Person> grandchildren = person.getGrandChildren();
//        Assert
        assertTrue(grandchildren.size() > 0);
    }

    @Test
    void addParents() {
//        Arrange
        Person person = new Person("Test", "Testson", "female", 12);
        Person parent1 = new Person("Mommy", "Testson", "female", 40);
        Person parent2 = new Person("Daddy", "Testson", "female", 44);
        person.addParents(parent1, parent2);
//        Act

//        Assert

    }

    @Test
    void addChild() {
//        Arrange

//        Act

//        Assert

    }

    @Test
    void addPet() {
//        Arrange

//        Act

//        Assert

    }

    @Test
    void addSibling() {
//        Arrange

//        Act

//        Assert

    }
}