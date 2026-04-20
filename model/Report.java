package model;

import java.util.List;

// Abstract base class for all report types

public abstract class Report {

    protected String title;

    public Report(String title) {
        this.title = title;
    }

   
    public abstract List<String> generate();

    public String getTitle() { return title; }
}
