package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Login El Naggar
 */
public class JavaFXProject extends Application {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<student> data;
    TableView<student> table;

    @Override
    public void start(Stage primaryStage) {

        Text txt1 = new Text("FX Project");

        Button ifadd = new Button("Add");
        Button ifupdate = new Button("Update");
        Button ifdelete = new Button("Delete");
        Button ifselect = new Button("Select");
        Button showall = new Button("Show All");
        Button Back = new Button("Back");

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));

        TableColumn c1 = new TableColumn("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn c2 = new TableColumn("Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn c3 = new TableColumn("CGPA");
        c3.setCellValueFactory(new PropertyValueFactory<>("cgpa"));

        //TableColumn c4 = new TableColumn("Major");
        //c4.setCellValueFactory(new PropertyValueFactory<>("major"));

        table.getColumns().addAll(c1, c2, c3);
        VBox v = new VBox(table);
        v.setPadding(new Insets(20));

        GridPane r = new GridPane();
        r.add(txt1, 0, 0, 2, 1);
        r.add(ifadd, 0, 1);
        r.add(ifselect, 1, 1);
        r.add(v, 2, 0, 4, 3);
        r.add(ifupdate, 0, 2);
        r.add(ifdelete, 1, 2);
        r.add(showall, 0, 3);
        r.setVgap(30);
        r.setHgap(20);
        r.setAlignment(Pos.CENTER);
        Scene scene = new Scene(r, 700, 700);

        primaryStage.setTitle("Scene1");
        primaryStage.setScene(scene);
        primaryStage.show();

        Label id = new Label("ID:");
        Label name = new Label("Name:");
        Label cgpa = new Label("cGPA:");
        //Label major = new Label("Major:");
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField cgpaField = new TextField();
        //TextField majorField = new TextField();
        Button add = new Button("Add");

        Back.setOnAction((ActionEvent event) -> {
            primaryStage.setTitle("Scene1");
            primaryStage.setScene(scene);
            primaryStage.show();

        });

        Label selectid = new Label("ID:");
        TextField SelectidField = new TextField();
        Button Select = new Button("Select");

        GridPane SelectPane = new GridPane();

        SelectPane.add(selectid, 0, 0);
        SelectPane.add(SelectidField, 1, 0);
        SelectPane.add(Select, 1, 1);
        SelectPane.add(Back, 0, 1);

        SelectPane.setVgap(20);
        SelectPane.setHgap(50);
        SelectPane.setAlignment(Pos.CENTER);

        Scene SelectScene = new Scene(SelectPane, 500, 500);

        ifselect.setOnAction((ActionEvent event) -> {

            try {
                SelectPane.add(Back, 0, 1);
                primaryStage.setTitle("Scene5");
                primaryStage.setScene(SelectScene);
                primaryStage.show();

            } catch (Exception exx) {
                primaryStage.setTitle("Scene5");
                primaryStage.setScene(SelectScene);
                primaryStage.show();
            }

        });

        Select.setOnAction((ActionEvent event) -> {
            data = FXCollections.observableArrayList();
            conn = dbconn.DBConnection();

            try {
                pst = conn.prepareStatement("select * from students Where id = "+SelectidField.getText());

                res = pst.executeQuery();

                while (res.next()) {
                    data.add(new student(res.getInt(1), res.getString(2), res.getDouble(3), res.getString(4)));

                }

                pst.close();
                conn.close();
                table.setItems(data);
                SelectidField.setText("");
            } catch (Exception exx) {
                System.out.println(exx.toString());
            }
        });

        add.setOnAction((ActionEvent event) -> {
            conn = dbconn.DBConnection();
            String sql = "Insert into students (id, name, cgpa, major) Values(?,?,?,?)";

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, idField.getText());
                pst.setString(2, nameField.getText());
                pst.setString(3, cgpaField.getText());
                pst.setString(4, "cs");

                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data is inserted");
                }
                pst.close();
                conn.close();
                show();
                idField.setText("");
                nameField.setText("");
                cgpaField.setText("");
                //majorField.setText("");
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        GridPane root = new GridPane();
        root.add(id, 0, 1);
        root.add(idField, 1, 1);
        root.add(name, 0, 2);
        root.add(nameField, 1, 2);
        root.add(cgpa, 0, 3);
        root.add(cgpaField, 1, 3);
        //root.add(major, 0, 4);
        //root.add(majorField, 1, 4);

        root.add(Back, 0, 6);
        root.add(add, 1, 6);

        root.setVgap(20);
        root.setHgap(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene s = new Scene(root, 500, 500);

        ifadd.setOnAction((ActionEvent e) -> {

            try {
                root.add(Back, 0, 6);
                primaryStage.setTitle("Scene2");
                primaryStage.setScene(s);
                primaryStage.show();
            } catch (Exception exx) {
                primaryStage.setTitle("Scene2");
                primaryStage.setScene(s);
                primaryStage.show();
            }
        });

        Text txt2 = new Text("Delete Student");
        Label idl = new Label("ID:");
        TextField idFieldd = new TextField();
        Button delete = new Button("Delete");

        delete.setOnAction(e -> {
            String sql = "Delete from students where id = "+idFieldd.getText();

            conn = dbconn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
                idFieldd.setText("");
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        GridPane m = new GridPane();
        m.add(txt2, 0, 0, 2, 1);
        m.add(idl, 0, 1);
        m.add(idFieldd, 1, 1);

        m.add(Back, 0, 2);
        m.add(delete, 1, 2);

        m.setVgap(30);
        m.setHgap(50);
        m.setPadding(new Insets(40));
        m.setAlignment(Pos.CENTER);

        Scene sce = new Scene(m, 500, 500);

        ifdelete.setOnAction((ActionEvent e) -> {

            try {
                m.add(Back, 0, 2);
                primaryStage.setTitle("Scene3");
                primaryStage.setScene(sce);
                primaryStage.show();
            } catch (Exception exx) {
                primaryStage.setTitle("Scene3");
                primaryStage.setScene(sce);
                primaryStage.show();
            }
        });

        Text txt3 = new Text("Update Student's cGPA");
        Label idk = new Label("Student's ID:");
        Label majorl = new Label("cGPA");
        TextField idFielddd = new TextField();
        TextField majorFieldd = new TextField();
        Button update = new Button("Update");

        update.setOnAction((ActionEvent event) -> {

            String sql = "Update students set cgpa = "+majorFieldd.getText()+" where id = "+idFielddd.getText();
            conn = dbconn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
                majorFieldd.setText("");
                idFielddd.setText("");
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        GridPane g2 = new GridPane();

        g2.add(txt3, 0, 0, 2, 1);
        g2.add(idk, 0, 1);
        g2.add(idFielddd, 1, 1);
        g2.add(majorl, 0, 2);
        g2.add(majorFieldd, 1, 2);
        g2.add(update, 1, 3, 2, 1);
        g2.add(Back, 0, 3, 2, 1);

        g2.setVgap(20);
        g2.setHgap(20);
        g2.setPadding(new Insets(40));
        g2.setAlignment(Pos.CENTER);

        Scene scen = new Scene(g2, 500, 500);

        ifupdate.setOnAction((ActionEvent e) -> {

            try {
                g2.add(Back, 0, 3, 2, 1);
                primaryStage.setTitle("Scene4");
                primaryStage.setScene(scen);
                primaryStage.show();
            } catch (Exception exx) {
                primaryStage.setTitle("Scene4");
                primaryStage.setScene(scen);
                primaryStage.show();
            }

        });

        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbconn.DBConnection();

        pst = conn.prepareStatement("select * from students");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new student(res.getInt(1), res.getString(2), res.getDouble(3), res.getString(4)));

        }
        pst.close();
        conn.close();
        table.setItems(data);
    }

}
