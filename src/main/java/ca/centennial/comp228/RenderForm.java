package ca.centennial.comp228;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class RenderForm extends GridPane {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final String FIELD_STYLE =
            "-fx-background-color: #3c3c3c; -fx-text-fill: #ffffff; "
                    + "-fx-prompt-text-fill: #aaaaaa; -fx-border-color: #555555;";

    private final TextField fullNameField = new TextField();
    private final TextField addressField = new TextField();
    private final TextField contactField = new TextField();
    private final TextField educationField = new TextField();
    private final TextField emailField = new TextField();
    private final DatePicker availableDatePicker = new DatePicker();
    private final TextField positionField = new TextField();
    private final TextField salaryField = new TextField();
    private final TextField explainField = new TextField();
    private final TextField signatureField = new TextField();

    private final ToggleGroup genderGroup = new ToggleGroup();
    private final RadioButton genderMale = new RadioButton("male");
    private final RadioButton genderFemale = new RadioButton("female");
    private final RadioButton genderOther = new RadioButton("other");

    private final ToggleGroup authorizedGroup = new ToggleGroup();
    private final RadioButton authorizedYes = new RadioButton("Yes");
    private final RadioButton authorizedNo = new RadioButton("No");

    private final ToggleGroup relativesGroup = new ToggleGroup();
    private final RadioButton relativesYes = new RadioButton("Yes");
    private final RadioButton relativesNo = new RadioButton("No");

    private final Button submitButton = new Button("Submit");
    private final Label statusLabel = new Label("Fill in the form and press Submit.");

    private final ApplicationDao dao = new ApplicationDao();

    private double parsedSalary;
    private Date parsedAvailableDate;

    public RenderForm() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #1e1e1e;");

        ColumnConstraints labels = new ColumnConstraints(160);
        ColumnConstraints fields = new ColumnConstraints();
        fields.setMinWidth(300);
        getColumnConstraints().addAll(labels, fields);

        fullNameField.setPromptText("e.g. Avery Thompson");
        addressField.setPromptText("Street, city, postal code");
        contactField.setPromptText("416-555-0142");
        educationField.setPromptText("e.g. Bachelor of Science");
        emailField.setPromptText("name@example.com");
        positionField.setPromptText("e.g. Java Developer");
        salaryField.setPromptText("e.g. 75000.00");
        explainField.setPromptText("Names and relationship, if applicable");
        signatureField.setPromptText("Type your full name");

        availableDatePicker.setPromptText("yyyy-mm-dd");

        for (TextField field : new TextField[]{
                fullNameField, addressField, contactField, educationField, emailField,
                positionField, salaryField, explainField, signatureField}) {
            field.setStyle(FIELD_STYLE);
        }
        availableDatePicker.setStyle(FIELD_STYLE);

        explainField.disableProperty().bind(relativesYes.selectedProperty().not());

        addRow(0, "Full name:", fullNameField);
        addRow(1, "Current address:", addressField);
        addRow(2, "Contact number:", contactField);
        addRow(3, "Highest education:", educationField);
        addRow(4, "Email:", emailField);
        addRow(5, "Gender:", radioRow(genderGroup, genderMale, genderFemale, genderOther));
        addRow(6, "Available date:", availableDatePicker);
        addRow(7, "Desired position:", positionField);
        addRow(8, "Desired salary:", salaryField);
        addRow(9, "Legally allowed to work:", radioRow(authorizedGroup, authorizedYes, authorizedNo));
        addRow(10, "Relatives working here:", radioRow(relativesGroup, relativesYes, relativesNo));
        addRow(11, "Explain relatives:", explainField);
        addRow(12, "Signature:", signatureField);

        submitButton.setStyle("-fx-background-color: #0e639c; -fx-text-fill: white; "
                + "-fx-background-radius: 4; -fx-padding: 6 16 6 16;");
        statusLabel.setStyle("-fx-text-fill: #cccccc;");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(event -> onSubmit());
        add(submitButton, 1, 13);
        add(statusLabel, 0, 14, 2, 1);
    }

    private Node radioRow(ToggleGroup group, RadioButton... buttons) {
        HBox row = new HBox(15);
        for (RadioButton button : buttons) {
            button.setToggleGroup(group);
            button.setStyle("-fx-text-fill: #e0e0e0;");
            row.getChildren().add(button);
        }
        return row;
    }

    private void addRow(int row, String label, Node control) {
        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-text-fill: #e0e0e0; -fx-font-weight: bold;");
        add(labelNode, 0, row);
        if (control instanceof Control) {
            ((Control) control).setMaxWidth(Double.MAX_VALUE);
        }
        add(control, 1, row);
    }

    private void onSubmit() {
        if (!validate()) {
            return;
        }

        ApplicationClass application = new ApplicationClass(
                text(fullNameField),
                text(addressField),
                text(contactField),
                text(educationField),
                text(emailField),
                selectedValue(genderGroup),
                parsedAvailableDate,
                text(positionField),
                parsedSalary,
                isYes(authorizedGroup),
                isYes(relativesGroup),
                isYes(relativesGroup) ? text(explainField) : null,
                text(signatureField)
        );

        try {
            int id = dao.saveApplication(application);
            if (id > 0) {
                statusLabel.setText("Application saved. Reference id: " + id);
                showAlert(Alert.AlertType.INFORMATION, "Application saved successfully. Id: " + id);
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "The application was not saved.");
            }
        } catch (SQLException exception) {
            statusLabel.setText("Database error: " + exception.getMessage());
            showAlert(Alert.AlertType.ERROR, "Database error: " + exception.getMessage());
        }
    }

    private boolean validate() {
        StringBuilder errors = new StringBuilder();

        require(fullNameField, "Full name", errors);
        require(addressField, "Current address", errors);
        require(contactField, "Contact number", errors);
        require(educationField, "Highest education", errors);
        require(emailField, "Email", errors);
        require(positionField, "Desired position", errors);
        require(salaryField, "Desired salary", errors);
        require(signatureField, "Signature", errors);

        if (genderGroup.getSelectedToggle() == null) {
            errors.append("Select a gender.\n");
        }
        if (authorizedGroup.getSelectedToggle() == null) {
            errors.append("Select whether you are legally allowed to work.\n");
        }
        if (relativesGroup.getSelectedToggle() == null) {
            errors.append("Select whether you have relatives working here.\n");
        }

        if (!text(emailField).isEmpty() && !EMAIL_PATTERN.matcher(text(emailField)).matches()) {
            errors.append("Enter a valid email address.\n");
        }

        LocalDate selectedDate = availableDatePicker.getValue();
        if (selectedDate == null) {
            errors.append("Choose an available date.\n");
        } else {
            parsedAvailableDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        try {
            parsedSalary = Double.parseDouble(text(salaryField));
            if (parsedSalary < 0) {
                errors.append("Desired salary cannot be negative.\n");
            }
        } catch (NumberFormatException exception) {
            if (!text(salaryField).isEmpty()) {
                errors.append("Desired salary must be a number.\n");
            }
        }

        if (isYes(relativesGroup) && text(explainField).isEmpty()) {
            errors.append("Explain which relatives work here.\n");
        }

        if (errors.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Please correct the following:\n\n" + errors);
            return false;
        }
        return true;
    }

    private String selectedValue(ToggleGroup group) {
        Toggle selected = group.getSelectedToggle();
        return selected == null ? null : ((RadioButton) selected).getText();
    }

    private boolean isYes(ToggleGroup group) {
        return "Yes".equals(selectedValue(group));
    }

    private void require(TextField field, String name, StringBuilder errors) {
        if (text(field).isEmpty()) {
            errors.append(name).append(" is required.\n");
        }
    }

    private String text(TextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private void clearForm() {
        fullNameField.clear();
        addressField.clear();
        contactField.clear();
        educationField.clear();
        emailField.clear();
        genderGroup.selectToggle(null);
        availableDatePicker.setValue(null);
        positionField.clear();
        salaryField.clear();
        authorizedGroup.selectToggle(null);
        relativesGroup.selectToggle(null);
        explainField.clear();
        signatureField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.initOwner(submitButton.getScene() == null ? null : submitButton.getScene().getWindow());
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
