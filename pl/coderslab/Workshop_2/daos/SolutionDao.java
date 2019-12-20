package pl.coderslab.Workshop_2.daos;

import pl.coderslab.Workshop_2.DBUtil;
import pl.coderslab.Workshop_2.models.Solution;
import java.sql.*;
import java.util.Arrays;

public class SolutionDao {

    private static final String
            CREATE_SOLUTION_QUERY = "INSERT INTO solutions(created, description, exercise_id, user_id) VALUES (?, ?, ?, ?)";
    private static final String
            READ_SOLUTION_QUERY = "SELECT * FROM solutions where id = ?";
    private static final String
            UPDATE_SOLUTION_QUERY = "UPDATE solutions SET updated = ?, description = ? where id = ?";
    private static final String
            DELETE_SOLUTION_QUERY = "DELETE FROM solutions WHERE id = ?";
    private static final String
            FIND_ALL_SOLUTIONS_QUERY = "SELECT * FROM solutions";
    private static final String
            FIND_ALL_USER_SOLUTIONS_QUERY = "SELECT * FROM solutions WHERE user_id = ?";
    private static final String
            FIND_ALL_EXERCISE_SOLUTIONS_QUERY = "SELECT s.* FROM solutions AS s LEFT JOIN exercises e on s.exercises_id = e.id\n" +
            "WHERE exercises_id = ? ORDER BY created DESC ;";



    public Solution create(Solution solution) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, solution.getCreated());
            statement.setString(2, solution.getDescription());
            statement.setInt(3, solution.getExerciseId());
            statement.setInt(4, solution.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution read(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution[] solutions = new Solution[0];
                System.out.println("ID / Created / Updated / Description / Exercise ID / User ID");
                solutions = getSolutions(solutions, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Solution solution) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setString(1, solution.getUpdated());
            statement.setString(2, solution.getDescription());
            statement.setInt(3, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Solution[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_QUERY);
            System.out.println("ID / Created / Updated / Description / Exercise ID / User ID");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                solutions = getSolutions(solutions, resultSet);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution[] addToArray(Solution s, Solution[] solutions) {
        Solution[] tmpUsers = Arrays.copyOf(solutions, solutions.length + 1);
        tmpUsers[solutions.length] = s;
        return tmpUsers;
    }

    public Solution [] findAllByUserId (int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_SOLUTIONS_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("ID / Created / Updated / Description / Exercise ID / User ID");
            while (resultSet.next()) {
                solutions = getSolutions(solutions, resultSet);
            }
            return solutions;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution[] findAllByExerciseId (int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_SOLUTIONS_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                solutions = getSolutions(solutions, resultSet);
            }
            return solutions;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Solution[] getSolutions(Solution[] solutions, ResultSet resultSet) throws SQLException {
        Solution solution = new Solution();
        solution.setId(resultSet.getInt("id"));
        solution.setCreated(resultSet.getString("created"));
        solution.setUpdated(resultSet.getString("updated"));
        solution.setDescription(resultSet.getString("description"));
        solution.setExerciseId(resultSet.getInt("exercise_id"));
        solution.setUserId(resultSet.getInt("user_id"));
        System.out.println(solution.getId() + " / " + solution.getCreated() + " / "
                + solution.getUpdated() + " / " + solution.getDescription() + " / " + solution.getExerciseId() + " / " +
                solution.getUserId());
        solutions = addToArray(solution, solutions);
        return solutions;
    }
}
