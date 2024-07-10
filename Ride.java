

import java.util.Map;
import java.util.TreeMap;

//Ride class that has our rideNumber rideCost and tripduration parameters
public class Ride {
    private int rideNumber;
    private int rideCost;
    private int tripDuration;
    Map<Integer,Ride> map=new TreeMap<>();
    public Ride(){
        super();
    }
    public Ride(int rideNumber,int rideCost,int tripDuration){
        this.rideNumber=rideNumber;
        this.rideCost=rideCost;
        this.tripDuration=tripDuration;
    }
    public int getRideNumber(){
        return this.rideNumber;
    }
    public Integer getRideCost(){
        return this.rideCost;
    }
    public int getTripDuration(){
        return this.tripDuration;
    }
    public void setRideNumber(int rideNumber){
        this.rideNumber=rideNumber;
    }
    public void setRideCost(int rideCost){
        this.rideCost=rideCost;
    }
    public void setTripDuration(int tripDuration){
        this.tripDuration=tripDuration;
    }
    public Ride getRide(int rideNumber){
        return map.get(rideNumber);
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Ride))
            return false;
        if (obj == this)
            return true;
        Ride ride=(Ride) obj;
        boolean result=false;
        if(this.getRideCost()==ride.getRideCost() && this.getRideNumber()==ride.getRideNumber() && this.getTripDuration()==ride.getTripDuration()){
            result=true;
        }
        return result;
    }
    @Override 
    public int hashCode(){
        int hash = 3;
        hash = 53 * hash + this.rideNumber;
        hash = 53 * hash + this.rideCost;
        return hash;
    }
    @Override
    public String toString() { 
        return "(" + this.rideNumber + "," + this.rideCost + "," + this.tripDuration + ")";
    } 
   
}
