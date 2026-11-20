package ca.centennial.comp228;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {

    private static final String INSERT_SQL =
            "INSERT INTO applications ("
                    + "full_name, current_address, contact_number, highest_edu_att, email, gender, "
                    + "available_date, desired_position, desired_salary, authorized_to_work, "
                    + "have_relatives_working, explain_relatives, signature"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int saveApplication(ApplicationClass application) throws SQLException {
        try (Connection connection = Database.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            int index = 1;
            statement.setString(index++, application.getFullName());
            statement.setString(index++, application.getCurrentAddress());
            statement.setString(index++, application.getContactNumber());
            statement.setString(index++, application.getHighestEduAtt());
            statement.setString(index++, application.getEmail());
            statement.setString(index++, application.getGender());
            statement.setDate(index++, new java.sql.Date(application.getAvaliableDate().getTime()));
            statement.setString(index++, application.getDesiredPosition());
            statement.setDouble(index++, application.getDesiredSalary());
            statement.setBoolean(index++, application.isAuthorizedToWork());
            statement.setBoolean(index++, application.isHaveRelativesWorking());
            statement.setString(index++, application.getExplainRelatives());
            statement.setString(index++, application.getSignature());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }
}
