package com.coursework.courseprojectfxeng.fxControllers;

import com.coursework.courseprojectfxeng.HelloApplication;
import com.coursework.courseprojectfxeng.hibernate.GenericHibernate;
import com.coursework.courseprojectfxeng.model.Comment;
import com.coursework.courseprojectfxeng.model.Plant;
import com.coursework.courseprojectfxeng.model.Product;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductReview {

    @FXML
    public ListView<Product> productListField;
    @FXML
    public TreeView<Comment> commentTreeField;

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        fillLists();
    }

    private void fillLists() {
        productListField.getItems().clear();
        productListField.getItems().addAll(genericHibernate.getAllRecords(Product.class));
    }

    public void previewProduct() {
        //Panaudosime alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Information Dialog");
        Product product = genericHibernate.getEntityById(Product.class, productListField.getSelectionModel().getSelectedItem().getId());
        if (product instanceof Plant) {
            Plant plant = (Plant) product;
            alert.setHeaderText(plant.getTitle());
            alert.setContentText(plant.toString());
        }
        //reiktu likusius suziureti


        alert.showAndWait();
    }

    public void addReview() throws IOException {
        //Surink resources: fxml, constoller, vaizdo, grafine, stiliaus
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        //Uzkrauk resursus. Be sio zingsnio as nepasieksiu kontrolerio ar kitu resursu
        Parent parent = fxmlLoader.load();

        CommentForm commentForm = fxmlLoader.getController();
        //Formos nezino viena apie kita, vadinasi turiu perduoti connection ir siuo metu prisijungusi vartotoja
        commentForm.setData(entityManagerFactory, null, productListField.getSelectionModel().getSelectedItem());
        //Sukuriu dar viena langa
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Kazka prasmingo!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void loadReviews() {
        //Kad butu up to date informacija, istraukiu pasirinkto produkto info pagal id
        Product product = genericHibernate.getEntityById(Product.class, productListField.getSelectionModel().getSelectedItem().getId());
        commentTreeField.setRoot(new TreeItem<>());
        commentTreeField.setShowRoot(false);
        commentTreeField.getRoot().setExpanded(true);
        product.getComments().forEach(comment -> addTreeItem(comment, commentTreeField.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void updateComment(ActionEvent actionEvent) throws IOException {
        //Surink resources: fxml, constoller, vaizdo, grafine, stiliaus
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        //Uzkrauk resursus. Be sio zingsnio as nepasieksiu kontrolerio ar kitu resursu
        Parent parent = fxmlLoader.load();

        CommentForm commentForm = fxmlLoader.getController();
        //Formos nezino viena apie kita, vadinasi turiu perduoti connection ir siuo metu prisijungusi vartotoja
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue());
        //Sukuriu dar viena langa
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Kazka prasmingo!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void reply() throws IOException {
        //Surink resources: fxml, constoller, vaizdo, grafine, stiliaus
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        //Uzkrauk resursus. Be sio zingsnio as nepasieksiu kontrolerio ar kitu resursu
        Parent parent = fxmlLoader.load();

        CommentForm commentForm = fxmlLoader.getController();
        //Formos nezino viena apie kita, vadinasi turiu perduoti connection ir siuo metu prisijungusi vartotoja
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue(), null);
        //Sukuriu dar viena langa
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Kazka prasmingo!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void delete() {

        genericHibernate.delete(Comment.class, commentTreeField.getSelectionModel().getSelectedItem().getValue().getId());
    }
}
