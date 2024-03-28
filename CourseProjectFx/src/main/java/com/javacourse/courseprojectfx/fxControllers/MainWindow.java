package com.javacourse.courseprojectfx.fxControllers;

import com.javacourse.courseprojectfx.HelloApplication;
import com.javacourse.courseprojectfx.fxControllers.tableParameters.ManagerTableParameters;
import com.javacourse.courseprojectfx.hibernate.ShopHibernate;
import com.javacourse.courseprojectfx.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    //I add @FXML above all attributes that are linked to form element ids
    //<editor-fold desc="Here are the fields for Shop tab">
    @FXML
    //ListView without a type is just a raw usage. It is best to specify the type of objects that will be stored in that list
    //In our case it is Product
    public ListView<Product> shopProducts;
    @FXML
    public Tab shopTab;
    @FXML
    public ListView<Product> myCartItems;
    //</editor-fold>

    //<editor-fold desc="Here are the fields for products tab">
    @FXML
    public Tab productsTab;
    @FXML
    public ListView<Product> productAdminList;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public TextField productQuantityField;
    @FXML
    public TextField productWeightField;
    @FXML
    public DatePicker productDatePickField;
    @FXML
    public DatePicker productPlantDateField;
    @FXML
    public DatePicker productValidTillField;
    @FXML
    public TextField productColourField;
    @FXML
    public TextField productTypeField;
    @FXML
    public TextField productFarmField;
    @FXML
    public DatePicker productSeedPickDate;
    @FXML
    //This ComboBox stores SeedTypes. The values are populated on initialize
    public ComboBox<SeedType> seedTypeField;
    @FXML
    public TextField productManufacturerField;
    @FXML
    public RadioButton productPlantRadio;
    @FXML
    public RadioButton productSeedRadio;
    @FXML
    public RadioButton productToolRadio;
    //</editor-fold>

    //<editor-fold desc="Here are the fields for User tab">
    public TableColumn<ManagerTableParameters, Integer> managerColId;
    public TableColumn<ManagerTableParameters, String> managerColLogin;
    public TableColumn<ManagerTableParameters, String> managerColName;
    public TableView<ManagerTableParameters> managerTable;
    public TableView<Customer> customerTable;
    public TableColumn<ManagerTableParameters, Void> dummyCol;
    public Tab usersTab;
    private ObservableList<ManagerTableParameters> data = FXCollections.observableArrayList();
    //</editor-fold>

    public Tab ordersTab;
    public Tab warehousesTab;
    @FXML
    public TabPane tabPane;
    private EntityManagerFactory entityManagerFactory;
    //This class has methods for entity manipulation
    private ShopHibernate shopHibernate;
    //I need to know which user is selected
    private User user;

    //When class implements Initializable interface, you will be required to implements this method.
    // It allows us to access all the fields before they are rendered
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Populating ComboBox with SeedType values
        seedTypeField.getItems().addAll(SeedType.values());

        //Initializing TableViews
        //TODO Complete remaining columns
        managerTable.setEditable(true);
        //setCellValueFactory allows to display the data
        managerColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        //setCellFactory and setOnEditCommit allows us to edit cell value
        managerColName.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColName.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            //Before updating, get the latest version from database
            Manager manager = shopHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setName(event.getNewValue());
            shopHibernate.update(manager);
        });
        managerColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //This portion of the code is responsible for generating a graphic element (button) in a cell
        Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> callback = param -> {
            final TableCell<ManagerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());
                        shopHibernate.delete(Manager.class, row.getId());
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        dummyCol.setCellFactory(callback);
        //TODO complete Customer TableView
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.user = user;
        loadTabData();
        setCustomerView();
    }

    private void setCustomerView() {
        //Customer should not have any access or knowledge about tabs that are intended for Managers/Admins
        if (user instanceof Customer) {
            //You could simply disable tabs, but it is better to not render them
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(productsTab);
            tabPane.getTabs().remove(warehousesTab);
        } else if (!((Manager) user).isAdmin()) {
            //TODO disable fields or functions that simple managers should not be able to access
        }
    }

    //<editor-fold desc="Logic for User Tab">
    private void fillManagerTable() {
        //get all records from the database for Manager TableView
        List<Manager> managers = shopHibernate.getAllRecords(Manager.class);
        for (Manager m : managers) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();
            managerTableParameters.setId(m.getId());
            managerTableParameters.setLogin(m.getLogin());
            managerTableParameters.setName(m.getName());
            //TODO complete remaining columns
            data.add(managerTableParameters);
        }
        managerTable.setItems(data);
    }
    //</editor-fold>

    //<editor-fold desc="Logic for Products Tab">
    //A method that is called once Add button is clicked
    public void createRecord() {
        //TODO change product creation based on your classes
        //Check if a plant is selected. If true, then get the data from the fields that are required to be filled for Plants and create Plant object
        if (productPlantRadio.isSelected()) {
            Plant plant = new Plant(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productPlantDateField.getValue(),
                    productColourField.getText(),
                    productTypeField.getText());
            //Save to database
            shopHibernate.create(plant);
        } else if (productSeedRadio.isSelected()) {
            //Check if seed is selected. Create a seed object
            Seed seed = new Seed(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productFarmField.getText(),
                    productSeedPickDate.getValue(),
                    seedTypeField.getValue());
            //Save to database
            shopHibernate.create(seed);
        } else {
            //Check if tool is selected. Create a tool object
            Tool tool = new Tool(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productManufacturerField.getText());
            //Save to database
            shopHibernate.create(tool);
        }
        //refresh the product list
        productAdminList.getItems().clear();
        productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void updateRecord() {
        //Once the product is selected, load that information to the fields for easier editing
        //You can also implement a TableView for easier manipulation
        Product product = shopHibernate.getEntityById(Product.class, productAdminList.getSelectionModel().getSelectedItem().getId());
        //We need to determine what kind of object it is.
        //If it is Plant - I need to disable non plant fields and fill plant fields with data
        if (product instanceof Plant) {
            Plant plant = (Plant) product;
            product.setTitle(productTitleField.getText());
            //productDescriptionField.setText(plant.getDescription());
            plant.setDescription(productDescriptionField.getText());
            //Once all information from the fields is collected, update the record
            shopHibernate.update(plant);
        } else if (product instanceof Seed) {
            //TODO complete for remaining product types
        } else {
            //TODO complete for remaining product types
        }
    }

    //Delete operations are more complicated, because we need to control what stays in the database and what should be removed
    //For this reason generic delete will not work for us, therefore I create custom delete methods
    public void deleteRecord() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        productAdminList.getItems().remove(product);
    }

    //This method enables/disables fields based on product type
    public void disableFields() {
        if (productPlantRadio.isSelected()) {
            productFarmField.setDisable(true);
            productSeedPickDate.setDisable(true);
            seedTypeField.setDisable(true);
            productManufacturerField.setDisable(true);
            productDatePickField.setDisable(false);
            productPlantDateField.setDisable(false);
            productValidTillField.setDisable(false);
            productColourField.setDisable(false);
            productTypeField.setDisable(false);
        } else if (productSeedRadio.isSelected()) {
            productFarmField.setDisable(false);
            productSeedPickDate.setDisable(false);
            seedTypeField.setDisable(false);
            productManufacturerField.setDisable(true);
            productDatePickField.setDisable(true);
            productPlantDateField.setDisable(true);
            productValidTillField.setDisable(true);
            productColourField.setDisable(true);
            productTypeField.setDisable(true);
        } else {
            productManufacturerField.setDisable(false);
            productFarmField.setDisable(true);
            productSeedPickDate.setDisable(true);
            seedTypeField.setDisable(true);
            productDatePickField.setDisable(true);
            productPlantDateField.setDisable(true);
            productValidTillField.setDisable(true);
            productColourField.setDisable(true);
            productTypeField.setDisable(true);
        }
    }

    //This method is called when you select a product from ListView. This ListView displays all products that are currently in the database
    //It populates the data in the GUI fields for faster data manipulation
    public void loadProductData() {
        //ListView element has getSelectionModel().getSelectedItem() method, it will return the selected item, which is a Product
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        //Because ListView<Product> stores Product, we can add all child class objects
        //This way we can access only those attributes and methods that are defined in Product class
        //Use instanceof to determine what child class object is there and fill the appropriate fields
        if (product instanceof Plant plant) {
            //See above, I have a pattern variable, this way I do not have to initialize in a separate line
            productTitleField.setText(plant.getTitle());
            productDescriptionField.setText(plant.getDescription());
            productQuantityField.setText(String.valueOf(plant.getQty()));
            productWeightField.setText(String.valueOf(plant.getWeight()));
        } else if (product instanceof Seed seed) {
            productTitleField.setText(seed.getTitle());
            productDescriptionField.setText(seed.getDescription());
            productQuantityField.setText(String.valueOf(seed.getQty()));
            productWeightField.setText(String.valueOf(seed.getWeight()));
            productFarmField.setText(seed.getFarm());
            productSeedPickDate.setValue(seed.getCollectDate());
            seedTypeField.setValue(seed.getSeedType());
        } else if (product instanceof Tool tool) {
            productTitleField.setText(tool.getTitle());
            productDescriptionField.setText(tool.getDescription());
            productQuantityField.setText(String.valueOf(tool.getQty()));
            productWeightField.setText(String.valueOf(tool.getWeight()));
            productManufacturerField.setText(tool.getManufacturer());
        }
    }
    //</editor-fold>

    //<editor-fold desc="Logic for Shop tab">
    //Do not create cart, only when user clicks "Buy"
    public void removeFromCart() {
        Product product = myCartItems.getSelectionModel().getSelectedItem();
        shopProducts.getItems().add(product);
        myCartItems.getItems().remove(product);
    }

    public void addToCart() {
        Product product = shopProducts.getSelectionModel().getSelectedItem();
        myCartItems.getItems().add(product);
        shopProducts.getItems().remove(product);
    }

    public void buyItems() {
        //When the user clicks buy, call a specific method, not generic, because this is more complicated
        shopHibernate.createCart(myCartItems.getItems(), user);
    }
    //</editor-fold>

    public void loadTabData() {
        if (shopTab.isSelected()) {
            shopProducts.getItems().addAll(shopHibernate.loadAvailableProducts());
        } else if (usersTab.isSelected()) {
            fillManagerTable();
            //TODO complete Customer TableView
            //fillCustomerTable();
        }
        //TODO fill only when the tab is clicked
    }

//    public void deleteCart() {
//        shopHibernate.deleteCart();
//    }

    public void loadProductReviewForm() throws IOException {
        //Get resources: fxml, controller, grapics, styles...
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product-review.fxml"));
        //Load resources, without this step I cannot access controllers
        Parent parent = fxmlLoader.load();

        ProductReview productReview = fxmlLoader.getController();
        //Forms do not know about each other, therefore I must pass info between them
        productReview.setData(entityManagerFactory, user);
        //Create a completely new window
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }
}
