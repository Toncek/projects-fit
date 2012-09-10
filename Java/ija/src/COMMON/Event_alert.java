/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package COMMON;

import java.util.*;
import org.jdom.*;

/**
 *Represents alert which contains iformations about it.
 * @author Ivan Homoliak
 */
public class Event_alert {
     /**
      * Represents date of alert.
      */
     protected Date start;
     /**
      * Represents whether alert is repeatable. 
      */
     protected Boolean Repeatable;
     /**
      * Represents interval of repeating.
      */
     protected long interval;
     /**
      * Creates new Event_alert object with specified date.
      * @param start Date representation of date.
      */
     public Event_alert(Date start) {
	  this.start = start;
	  this.interval = 0;
	  this.Repeatable = false;
     }
     /**
      *  Creates new Event_alert object with specified date interval and repeatability.
      * @param start
      * @param interval
      * @param r
      */
     public Event_alert(Date start, long interval, boolean r) {
	  this.start = start;
	  this.interval = interval;
	  this.Repeatable = r;
     }

     /**
      * Returns the start date of alert notification.
      * @return Date representation of start date.
      */
     public Date get_start_date() {
	  return this.start;
     }

     /**
      * Finds out whether alert of this object is repeatable or not.
      * @return True if alert is repeatable, false otherwise
      */
     public boolean is_Repeatable() {
	  return this.Repeatable;
     }

     /**
      * Finds out interval of repeating alerts.
      * @return Number of hours which represents interval of repeating.
      */
     public long get_interval() {
	  return this.interval;
     }

     /**
      * Sets the interval of this event to specified value.
      * @param i number of hours which represents interval of repeating.
      */
     public void set_interval(long i) {
	  this.interval = i;
     }

     /**
      * Sets the alert as repeatable or not, speciefied in parameter.
      * @param r if true, repetable attribute will be set as true, false otherwise.
      */
     public void set_repeatable(boolean r) {
	  this.Repeatable = r;
     }
     /**
      * Sets the start date of the alert.
      * @param d Date representation of some date, which will be used to set start date.
      */
     public void set_start(Date d) {
	  this.start = d;
     }
     /**
      * Returns the XML Element representation of this object.
      * @return  Alert object.
      */
     public Element toXML() {
	  Element alrt = new Element("Alert");
	  alrt.setAttribute("Start", Long.toString(this.get_start_date().getTime()));
	  alrt.setAttribute("Repeatable", Boolean.toString(this.is_Repeatable()));
	  alrt.setAttribute("Interval", Long.toString(this.get_interval()));
	  return alrt;
     }
     /**
      * Returns the Alert object from XML representation of alert object.
      * @param alrt Element which contains info about alert object.
      * @return Alert object.
      */
     public static Event_alert fromXML(Element alrt) {
	  Date s = new Date(Long.parseLong(alrt.getAttributeValue("Start")));
	  boolean r = Boolean.parseBoolean(alrt.getAttributeValue("Repeatable"));
	  long i = Long.parseLong(alrt.getAttributeValue("Interval"));
	  return new Event_alert(s, i, r);
     }
     /**
      * Prints the object's content to standard out.
      */
     public void print_content() {
	  System.out.printf("\t\tAlert. Start is: %s, Repeatable: %b, Interval: %d\n", this.start.toString(), this.Repeatable, this.interval);
     }
       /**
      * Compares Event_alert object with another.
      * @param o object of type Object.
      * @return true if objects are equal, false otherwise.
      */
     public boolean equals(Object o) {
	  if (o instanceof Event_alert) {
	       Event_alert ce = (Event_alert) o;
	       return this.Repeatable.equals(ce.Repeatable) && this.get_interval() == ce.interval &&this.start.equals(ce.start);
	  } else {
	       return false;
	  }
     }

     /**
      * Returns hash code of Event_alert object.
      * @return integer representation of hash code of Event_alert object.
      */
     public int hashCode() {
	  return (int)interval * start.hashCode() * this.Repeatable.toString().hashCode() - (int)interval;
     }
}
