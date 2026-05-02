/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;

public class Task {

    private int id;
    private String title;
    private String status;
    private String addedBy;
    private LocalDate creationDate;

    public Task(int id, String title, String status, String addedBy, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.addedBy = addedBy;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public static Task createHeader(String label) {
        return new Task(-1, "── " + label, "", "", null);
    }

    @Override
    public String toString() {
        if (id == -1) {
            return title;
        }

        return id + " - " + title + " - " + status + " - " + addedBy + " - " + creationDate;
    }
}