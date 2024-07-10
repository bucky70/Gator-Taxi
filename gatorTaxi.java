import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class gatorTaxi{
    public static FileWriter fw = null;
    static Map<Integer,Ride> map=new TreeMap<>();
    static CustomPriorityQueue minHeap=new CustomPriorityQueue();
    static RedBlackTree rbt=new RedBlackTree();
    public static void main(String args[]) throws IOException {
      
         //Reading input from the file
        File f=new File(args[0]);
        fw=new FileWriter("output_file.txt");
        BufferedReader br=new BufferedReader(new FileReader(f));
        String s="";
        while((s = br.readLine()) != null)
        { 
            String[] arguments=s.split("\\(");
            
            arguments[1]=arguments[1].substring(0,arguments[1].length()-1);
            String[] newArgs=arguments[1].split(",");
   
            if(arguments[0].equals("Insert")){
                Insert(Integer.valueOf(newArgs[0]),Integer.valueOf(newArgs[1]),Integer.valueOf(newArgs[2]));
            }
            else if(arguments[0].equals("Print") && newArgs.length==1){
                Print(Integer.valueOf(newArgs[0]));
            }
            else if(arguments[0].equals("Print") && newArgs.length==2){
                Print(Integer.valueOf(newArgs[0]),Integer.valueOf(newArgs[1]));
            }
            else if(arguments[0].equals("UpdateTrip")){
                UpdateTrip(Integer.valueOf(newArgs[0]),Integer.valueOf(newArgs[1]));
            }
            else if(arguments[0].equals("GetNextRide")){
                GetNextRide();
            }
            else if(arguments[0].equals("CancelRide")){
                CancelRide(Integer.valueOf(newArgs[0]));
            }
            else{
                //
            }
        } 
        fw.close();
        
       
    }
    //Insert -> inserts in both the minHeap and RedBlacktree
    public static void Insert(int rideNumber,int rideCost,int tripDuration) throws IOException{ //theoretically O(logn)
        if(map.containsKey(rideNumber)){
            try {
                fw.write("Duplicate Ride Number"+"\n");
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.exit(0);
        }
        Ride ride=new Ride(rideNumber, rideCost, tripDuration);
        minHeap.insert(ride);
        rbt.put(ride);
        map.put(rideNumber,ride);
       // Print(rideNumber);
    }


    //Only to print the ride value
    public static void Print(int rideNumber)throws IOException{ //O(1)
        if(map.get(rideNumber)==null){
            Ride ride = new Ride(0,0,0);
            fw.write("(" + ride.getRideNumber() + "," + ride.getRideCost() + "," + ride.getTripDuration() +")"+"\n");
        }
        else{
            Ride ride=map.get(rideNumber);
            fw.write("(" + ride.getRideNumber() + "," + ride.getRideCost() + "," + ride.getTripDuration() +")"+"\n");
        }
        
    }
    //to print the ride values in a given range of ridenumbers
    public static void Print(int rideNumber1,int rideNumber2) throws IOException{ //searches in Red black BST
       /*  if(rideNumber1<rideNumber2){
            rbt.printInRange(rideNumber1,rideNumber2);
        }
        */
        ArrayList<Ride> list=new ArrayList<>();
        for(int rideNumber:map.keySet()){
            if(rideNumber1<= rideNumber && rideNumber<=rideNumber2){
                list.add(map.get(rideNumber));
            }
        }
        String temp="";
        if(list.size()!=0){
            for(int i=0;i<list.size();i++){
                temp+=list.get(i)+",";
                
             }
             fw.write(temp.substring(0,temp.length()-1)+"\n"); 
        }
        else{
            return;
        }
        
    }
    //fetches the ride with lowest costride and tripduration
    public static void GetNextRide(){ //theoretically O(logn)
        if(map.size()==0||rbt.size()==0 || minHeap.isEmpty()){
            try {
                fw.write("No active ride requests"+"\n");
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
            
        }

        Ride ride=minHeap.delete();
        map.remove(ride.getRideNumber());
        rbt.delete(ride);
        try {
            fw.write("(" + ride.getRideNumber() + "," + ride.getRideCost() + "," + ride.getTripDuration() +")"+"\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //to cancel a ride with lowest ridecost
    public static void CancelRide(int rideNumber){ //theoretically O(logn)
        if(map.get(rideNumber)==null){
            return;
        }
        Ride ride=map.get(rideNumber);
        map.remove(rideNumber, ride);
        minHeap.delete(ride);
        rbt.delete(ride);
        
        //fw.write("the ride has been deleted");
    }
    //updating trip duration with different cases
    public static void UpdateTrip(int rideNumber, int new_tripDuration){ //theoretically O(logn)
        //case 1:
        if(new_tripDuration<=map.get(rideNumber).getTripDuration()){
            //update trip duration
            Ride ride=map.get(rideNumber);
            minHeap.delete(ride);
            rbt.delete(ride);
            ride.setTripDuration(new_tripDuration);
            minHeap.insert(ride);
            rbt.put(ride);
            map.put(rideNumber,ride);
        }
        //case 2: 
        else if(map.get(rideNumber).getTripDuration()<new_tripDuration && new_tripDuration<= 2*map.get(rideNumber).getTripDuration()){
             /*the driver will cancel the existing ride and a new ride request would be created with a penalty of 10 on 
              existing rideCost . We update the entry in the data structure with (rideNumber, rideCost+10, new_tripDuration) */
              Ride ride=map.get(rideNumber);
              map.remove(rideNumber);
              minHeap.delete(ride);
              rbt.delete(ride);         
              ride.setRideCost(ride.getRideCost()+10);
              ride.setTripDuration(new_tripDuration);
              minHeap.insert(ride);
              rbt.put(ride);
              map.put(rideNumber,ride);

            
        }
        //case 3: 
        else if(new_tripDuration > 2*map.get(rideNumber).getTripDuration()){
             //ride declined delete the ride
            minHeap.delete(map.get(rideNumber));
            rbt.delete(map.get(rideNumber));
            map.remove(rideNumber);
        }
    }

}


