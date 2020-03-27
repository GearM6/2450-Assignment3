package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    private class Item {
        private String text;
        private double price;

        public Item(String text, double price) {
            this.text = text;
            this.price = price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private class PriceLabel<Item> extends Label{
        private Label label = new Label();
        private double price = 0;
        private String text = "";

        public PriceLabel(String text, double price){
            this.text = text;
            this.price = price;
            this.label.setText(text + ": $" + price);
        }

        public Label getLabel() {
            return this.label;
        }

        @Override
        public String toString(){
            return this.label.getText();
        }

        public double getPrice() {
            return price;
        }

        public void updateLabel(String text){
            this.text = text;
            this.label.setText(text + " : " + this.price);
        }

        public void updateLabel(double price){
            this.price = price;
            this.label.setText(this.text + ": $" + price);
        }
    }

    private class ItemCell{
        private ListCell cell = new ListCell();
        private double price = 0;
        private String text = "";

        public ItemCell(String text, double price){
            this.text = text;
            this.price = price;
            this.cell.setText(text + ": $" + price);
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString(){
            return this.cell.getText();
        }

        public ListCell getCell() {
            return cell;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ListView decks = new ListView();

        decks.getItems().addAll("Decks",
                new ItemCell("The Master Thrasher" ,60),
                new ItemCell("The Dictator", 45),
                new ItemCell("The Street King", 50)

        );
        ListView truckAssemblies = new ListView();
        truckAssemblies.getItems().addAll("Truck Assemblies" ,
                new ItemCell("7.75-inch axle", 35),
                new ItemCell("8-inch axle", 40),
                new ItemCell("8.5 inch axle", 45)
        );
        ListView wheels = new ListView();
        wheels.getItems().addAll("Wheels",
                new ItemCell("51mm", 20),
                new ItemCell("55mm", 22),
                new ItemCell("58mm", 24),
                new ItemCell("61mm", 28)
        );


        HBox table = new HBox(decks, truckAssemblies, wheels);
        PriceLabel subtotalLabel = new PriceLabel("Subtotal", 0);
        PriceLabel taxLabel = new PriceLabel("Sales Tax", 0);
        PriceLabel totalLabel = new PriceLabel("Total", 0);
        VBox verticalContainer = new VBox(table, subtotalLabel.getLabel(), taxLabel.getLabel(), totalLabel.getLabel());
        primaryStage.setTitle("Skateboard Designer");
        primaryStage.setScene(new Scene(verticalContainer, 500, 170));
        primaryStage.setResizable(false);
        decks.getSelectionModel().select(1);
        truckAssemblies.getSelectionModel().select(1);
        wheels.getSelectionModel().select(1);

        decks.setOnMouseClicked(event -> {
            calculateTotals(decks, truckAssemblies, wheels, subtotalLabel, taxLabel, totalLabel);
        });
        truckAssemblies.setOnMouseClicked(event -> {
            calculateTotals(decks, truckAssemblies, wheels, subtotalLabel, taxLabel, totalLabel);
        });
        wheels.setOnMouseClicked(event -> {
            calculateTotals(decks, truckAssemblies, wheels, subtotalLabel, taxLabel, totalLabel);
        });

        primaryStage.show();
    }

    private void calculateTotals(ListView decks, ListView truckAssemblies, ListView wheels, PriceLabel subtotalLabel, PriceLabel taxLabel, PriceLabel totalLabel) {
        double price = 0;
        try{
            ItemCell selectedDeck = (ItemCell) decks.getSelectionModel().getSelectedItem();
            ItemCell selectedTrucks = (ItemCell) truckAssemblies.getSelectionModel().getSelectedItem();
            ItemCell selectedWheels = (ItemCell) wheels.getSelectionModel().getSelectedItem();
            price = selectedDeck.getPrice() + selectedTrucks.getPrice() + selectedWheels.getPrice();
            double tax  = 0.07 * price;
            double total = price + tax;

            subtotalLabel.updateLabel(price);
            taxLabel.updateLabel(tax);
            totalLabel.updateLabel(total);
        }catch (Exception e){
            Alert selectionAlert = new Alert(Alert.AlertType.ERROR);
            selectionAlert.setTitle("Selection Error");
            selectionAlert.setHeaderText(null);
            selectionAlert.setContentText("Please do not select the titles.");
            selectionAlert.showAndWait();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
