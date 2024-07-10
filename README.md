# Gator-Taxi

## Execution
$ java gatorTaxi input.txt

# GatorTaxi Ride-Sharing Service

GatorTaxi is an up-and-coming ride-sharing service. This README outlines the operations and data structures used to manage ride requests in the system.

## Ride Identification

Each ride is identified by a unique triplet:
- **rideNumber**: Unique integer identifier for each ride.
- **rideCost**: Estimated cost (in integer dollars) for the ride.
- **tripDuration**: Total time (in integer minutes) needed to get from pickup to destination.

## Operations

1. **Print(rideNumber)**
   - Prints the triplet `(rideNumber, rideCost, tripDuration)`.

2. **Print(rideNumber1, rideNumber2)**
   - Prints all triplets `(rx, rideCost, tripDuration)` for which `rideNumber1 <= rx <= rideNumber2`.

3. **Insert (rideNumber, rideCost, tripDuration)**
   - Inserts a new ride into the data structures. The `rideNumber` must differ from existing ride numbers.

4. **GetNextRide()**
   - Outputs the ride with the lowest `rideCost`. Ties are broken by selecting the ride with the lowest `tripDuration`. This ride is then deleted from the data structure. If no rides remain, outputs "No active ride requests".

5. **CancelRide(rideNumber)**
   - Deletes the triplet `(rideNumber, rideCost, tripDuration)` from the data structures. If the entry does not exist, it is ignored.

6. **UpdateTrip(rideNumber, new_tripDuration)**
   - Updates the trip duration for the specified ride.
     - If `new_tripDuration <= existing tripDuration`, no action is needed.
     - If `existing_tripDuration < new_tripDuration <= 2 * (existing tripDuration)`, the existing ride is canceled and a new ride request is created with a penalty of 10 on the existing `rideCost`. The entry is updated to `(rideNumber, rideCost+10, new_tripDuration)`.
     - If `new_tripDuration > 2 * (existing tripDuration)`, the ride is automatically declined and removed from the data structure.

## Data Structures

To efficiently manage ride requests, the following data structures are used:

1. **Min-Heap**
   - Stores `(rideNumber, rideCost, tripDuration)` triplets ordered by `rideCost`.
   - If there are multiple triplets with the same `rideCost`, the one with the shortest `tripDuration` is given higher priority.

2. **Red-Black Tree (RBT)**
   - Stores `(rideNumber, rideCost, tripDuration)` triplets ordered by `rideNumber`.

## Constraints

- The number of active rides will not exceed 2000.
- GatorTaxi can handle only one ride at a time. When it is time to select a new ride request, the ride with the lowest `rideCost` (ties broken by the lowest `tripDuration`) is selected.



## Implementation

The program should implement the following:

1. **Min-Heap**: For efficiently retrieving and deleting the ride with the lowest `rideCost`.
2. **Red-Black Tree (RBT)**: For efficiently inserting, deleting, and querying rides by `rideNumber`.

### Min-Heap

The min-heap is used to store and manage rides based on `rideCost` and `tripDuration`.

### Red-Black Tree (RBT)

The RBT is used to store and manage rides based on `rideNumber`, allowing efficient range queries and updates.

