package com.example.authenticationapp;


public class TaskModel {

    String task;
    Long ch;

    public TaskModel(String task, Long ch) {
        this.task = task;
        this.ch = ch;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public double getCh() {
        return ch;
    }

    public void setCh(Long ch) {
        this.ch = ch;
    }
}
