package com.matches.project.model;

import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
public class UpcomingMatch extends Match {

     private LocalTime time;

     public UpcomingMatch() {
          status = Status.UPCOMING;
     }

     public LocalTime getTime() {
          return time;
     }

     public void setTime(LocalTime time) {
          this.time = time;
     }
}
