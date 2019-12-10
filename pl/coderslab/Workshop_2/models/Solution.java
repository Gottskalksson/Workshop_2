package pl.coderslab.Workshop_2.models;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;
    private int exerciseId;
    private int userId;

    public Solution() {

    }

    public Solution(String created, String updated, String description) {
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Exercise exercise) {
        this.exerciseId = exercise.getId();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user.getId();
    }
}
