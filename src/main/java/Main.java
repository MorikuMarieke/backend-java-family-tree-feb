public class Main {

    public static void main(String[] args) {
        Person harry = new Person("Harry", "Potter", "male", 37);
        Pet hedwig = new Pet("Hedwig", 3, "owl");
        harry.addPet(hedwig);
        System.out.println(hedwig.getOwner().getName());
    }
}
