package pl.coderslab.Workshop_2.daos;

public class GroupDao {
    private static final String
            CREATE_GROUP_QUERY = "INSERT INTO user_groups(name) VALUES (?)";
    private static final String
            READ_GROUP_QUERY = "SELECT * FROM user_groups where id = ?";
    private static final String
            UPDATE_GROUP_QUERY = "UPDATE user_groups SET name = ? WHERE id = ?";
    private static final String
            DELETE_GROUP_QUERY = "DELETE FROM user_groups WHERE id = ?";
    private static final String
            FIND_ALL_GROUPS_QUERY = "SELECT * FROM user_groups";
}
