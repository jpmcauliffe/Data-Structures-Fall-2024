public class Dessert {

    int flavor;
    int price;
    public static int numDesserts = 0;

    public Dessert(int myflavor, int myprice) {
        flavor= myflavor;
        price = myprice;
        numDesserts++;
    }

    public void printDessert() {
        System.out.print(flavor + " " + price + " " + numDesserts);
    }

    public static void main (String[] args) {
        System.out.println("I love dessert!");
    }

}
