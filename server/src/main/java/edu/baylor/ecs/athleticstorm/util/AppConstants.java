package edu.baylor.ecs.athleticstorm.util;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    enum Role {
        REDSHIRT, STARTER, MVP;
    }

}
