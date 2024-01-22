package ObjClasses;

public class Product {

    private String prodNr;
    private String prodName;
    private int price;

    public Product(String prodNr){
        this.prodNr = prodNr;

        if(prodNr.equals("P001")){
            prodName = "jTelefon";
            price = 8900;
        } else if (prodNr.equals("P002")) {
            prodName = "jPlatta";
            price = 5700;
        } else if (prodNr.equals("P003")) {
            prodName = "Päronklocka";
            price = 11000;
        }

    }
}
