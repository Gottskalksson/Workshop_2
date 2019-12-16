package pl.coderslab.Workshop_2.daos;

import pl.coderslab.Workshop_2.DBUtil;
import pl.coderslab.Workshop_2.models.Exercise;
import java.sql.*;
import java.util.Arrays;

public class ExerciseDao {

    private static final String
            CREATE_EXERCISE_QUERY = "INSERT INTO exercises(title, description) VALUES (?, ?)";
    private static final String
            READ_EXERCISE_QUERY = "SELECT * FROM exercises where id = ?";
    private static final String
            UPDATE_EXERCISE_QUERY = "UPDATE exercises SET title = ?, description = ? where id = ?";
    private static final String
            DELETE_EXERCISE_QUERY = "DELETE FROM exercises WHERE id = ?";
    private static final String
            FIND_ALL_EXERCISES_QUERY = "SELECT * FROM exercises";



    public Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exercise[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("ID / Title / Description");
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                System.out.println(exercise.getId() + " / " + exercise.getTitle() + " / "
                        + exercise.getDescription());
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Exercise[] addToArray(Exercise e, Exercise[] exercises) {
        Exercise[] tmpUsers = Arrays.copyOf(exercises, exercises.length + 1);
        tmpUsers[exercises.length] = e;
        return tmpUsers;
    }
}
