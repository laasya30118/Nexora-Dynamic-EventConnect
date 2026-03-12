// ============================================================
// FILE NAME  : NexoraEventConnect.java
// PROJECT    : Nexora Dynamic Event Connect
// WEBSITE    : Login Page - Event Registration & Management
// HOW TO RUN :
//   1. Open this file in VS Code
//   2. Make sure Java JDK is installed
//   3. Open Terminal  (Ctrl + `)
//   4. Compile : javac NexoraEventConnect.java
//   5. Run     : java  NexoraEventConnect
// ============================================================
//
// CO1 - Algorithm Efficiency & Searching/Sorting:
//         Big-O / Ω / Θ analysis in comments
//         Linear Search, Binary Search,
//         Bubble Sort, Selection Sort,
//         Insertion Sort, Merge Sort, Quick Sort
//         Applied to: Event names, User IDs, Ticket prices
//
// CO2 - ADT using Arrays & Linked Lists:
//         Singly Linked List  - Registered users chain
//         Doubly Linked List  - Event history (forward/backward)
//         Circular Linked List- Rotating event banner slots
//         Operations: insert, delete, search, traversal
//
// CO3 - Stacks & Queues:
//         Stack       - Login undo / session history (LIFO)
//         Queue       - Event registration waitlist (FIFO)
//         Circular Queue - Ticket booking slots
//         Deque       - VIP + General entry management
//
// CO4 - Hashing & Java Collections:
//         HashMap     - User credentials store
//         HashMap     - Event details store
//         List        - Registered event list per user
//         Queue/Deque - Java Collections usage
//         Map         - Ticket price mapping
//
// CO5 - Real-World Application:
//         Full Login System simulation
//         Event Registration with waitlist
//         Ticket booking with circular queue
//         User session management with stack
//         Complete event management workflow
//
// ============================================================

import java.util.*;

// ============================================================
// CO2 - Node for Singly Linked List (Registered Users)
// ============================================================
class UserNode {
    String username, email, password;
    UserNode next;

    UserNode(String username, String email, String password) {
        this.username = username;
        this.email    = email;
        this.password = password;
        this.next     = null;
    }
}

// ============================================================
// CO2 - Singly Linked List : Registered Users Chain
//       (Like the user database behind the Login page)
//       insert  -> O(n)
//       search  -> O(n)
//       delete  -> O(n)
//       traversal -> O(n)
// ============================================================
class UserSinglyList {
    UserNode head = null;
    int size = 0;

    // CO2: Insert user at end
    void insert(String username, String email, String password) {
        UserNode node = new UserNode(username, email, password);
        if (head == null) {
            head = node;
        } else {
            UserNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        }
        size++;
        System.out.println("  [INSERT] User \"" + username + "\" added to singly linked list.");
    }

    // CO2: Search user by username - Linear Search O(n)
    UserNode search(String username) {
        UserNode cur = head;
        int step = 0;
        while (cur != null) {
            step++;
            if (cur.username.equalsIgnoreCase(username)) {
                System.out.println("  [SEARCH] Found \"" + username + "\" at node " + step);
                return cur;
            }
            cur = cur.next;
        }
        System.out.println("  [SEARCH] \"" + username + "\" not found after " + step + " steps.");
        return null;
    }

    // CO2: Delete user by username
    void delete(String username) {
        if (head == null) { System.out.println("  [DELETE] List is empty."); return; }
        if (head.username.equalsIgnoreCase(username)) {
            head = head.next;
            size--;
            System.out.println("  [DELETE] User \"" + username + "\" removed from head.");
            return;
        }
        UserNode cur = head;
        while (cur.next != null) {
            if (cur.next.username.equalsIgnoreCase(username)) {
                cur.next = cur.next.next;
                size--;
                System.out.println("  [DELETE] User \"" + username + "\" removed.");
                return;
            }
            cur = cur.next;
        }
        System.out.println("  [DELETE] \"" + username + "\" not found.");
    }

    // CO2: Traversal - print all users
    void traversal() {
        System.out.println("  [TRAVERSAL] All registered users (Singly Linked List):");
        UserNode cur = head;
        int i = 1;
        while (cur != null) {
            System.out.printf("    Node %d -> Username: %-15s | Email: %s%n",
                    i++, cur.username, cur.email);
            cur = cur.next;
        }
        System.out.println("  Total nodes: " + size);
    }
}

// ============================================================
// CO2 - Node for Doubly Linked List (Event History)
// ============================================================
class EventDNode {
    String eventName;
    String date;
    EventDNode prev, next;

    EventDNode(String eventName, String date) {
        this.eventName = eventName;
        this.date      = date;
        this.prev      = null;
        this.next      = null;
    }
}

// ============================================================
// CO2 - Doubly Linked List : Event History
//       (Browse events forward and backward like a history tab)
//       insert  -> O(1) at end
//       delete  -> O(n)
//       traversal forward/backward -> O(n)
// ============================================================
class EventDoublyList {
    EventDNode head = null, tail = null;
    int size = 0;

    // CO2: Insert event at end
    void insert(String eventName, String date) {
        EventDNode node = new EventDNode(eventName, date);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
        System.out.println("  [INSERT] Event \"" + eventName + "\" added to doubly linked list.");
    }

    // CO2: Forward traversal (oldest to newest)
    void traverseForward() {
        System.out.println("  [FORWARD TRAVERSAL] Event History (oldest to newest):");
        EventDNode cur = head;
        int i = 1;
        while (cur != null) {
            System.out.printf("    [%d] %-30s | Date: %s%n", i++, cur.eventName, cur.date);
            cur = cur.next;
        }
    }

    // CO2: Backward traversal (newest to oldest)
    void traverseBackward() {
        System.out.println("  [BACKWARD TRAVERSAL] Event History (newest to oldest):");
        EventDNode cur = tail;
        int i = size;
        while (cur != null) {
            System.out.printf("    [%d] %-30s | Date: %s%n", i--, cur.eventName, cur.date);
            cur = cur.prev;
        }
    }

    // CO2: Delete event by name
    void delete(String eventName) {
        EventDNode cur = head;
        while (cur != null) {
            if (cur.eventName.equalsIgnoreCase(eventName)) {
                if (cur.prev != null) cur.prev.next = cur.next;
                else head = cur.next;
                if (cur.next != null) cur.next.prev = cur.prev;
                else tail = cur.prev;
                size--;
                System.out.println("  [DELETE] Event \"" + eventName + "\" removed from doubly list.");
                return;
            }
            cur = cur.next;
        }
        System.out.println("  [DELETE] Event \"" + eventName + "\" not found.");
    }
}

// ============================================================
// CO2 - Node for Circular Linked List (Rotating Banner Slots)
// ============================================================
class BannerNode {
    String eventTitle;
    BannerNode next;

    BannerNode(String eventTitle) {
        this.eventTitle = eventTitle;
        this.next       = null;
    }
}

// ============================================================
// CO2 - Circular Linked List : Rotating Event Banner
//       (Like the rotating banners/cards on the event website)
//       insert -> O(n) to find tail
//       traversal (n rounds) -> O(n)
// ============================================================
class EventCircularList {
    BannerNode head = null;
    int size = 0;

    // CO2: Insert banner slot at end, tail points to head
    void insert(String eventTitle) {
        BannerNode node = new BannerNode(eventTitle);
        if (head == null) {
            head = node;
            node.next = head;
        } else {
            BannerNode cur = head;
            while (cur.next != head) cur = cur.next;
            cur.next = node;
            node.next = head;
        }
        size++;
        System.out.println("  [INSERT] Banner \"" + eventTitle + "\" added to circular list.");
    }

    // CO2: Rotate and display banners (simulates scrolling)
    void rotateBanners(int rounds) {
        System.out.println("  [CIRCULAR TRAVERSAL] Rotating event banners (" + rounds + " rounds):");
        BannerNode cur = head;
        int total = rounds * size;
        int slot = 1;
        for (int i = 0; i < total; i++) {
            System.out.println("    Banner Slot " + slot + " -> " + cur.eventTitle);
            cur = cur.next;
            slot++;
            if (slot > size) slot = 1;
        }
    }
}

// ============================================================
// MAIN CLASS
// ============================================================
public class NexoraEventConnect {

    // CO4: Java Collections - HashMap for users & events
    static HashMap<String, String>       credentialStore = new HashMap<>(); // username -> password
    static HashMap<String, String>       userEmailStore  = new HashMap<>(); // username -> email
    static HashMap<String, List<String>> userEvents      = new HashMap<>(); // username -> registered events
    static HashMap<String, Integer>      ticketPrices    = new HashMap<>(); // eventName -> price
    static HashMap<String, String>       eventDetails    = new HashMap<>(); // eventName -> details

    // CO2: Linked Lists
    static UserSinglyList  registeredUsers = new UserSinglyList();
    static EventDoublyList eventHistory    = new EventDoublyList();
    static EventCircularList bannerSlots   = new EventCircularList();

    static Scanner sc = new Scanner(System.in);

    // ============================================================
    public static void main(String[] args) {

        banner("NEXORA DYNAMIC EVENT CONNECT  -  JAVA DSA DEMO");

        // Pre-load data
        loadCredentials();
        loadEventData();
        loadLinkedLists();

        // ========================================================
        // CO1 - BIG-O / Ω / Θ ANALYSIS
        // ========================================================
        section("CO1 - ALGORITHM EFFICIENCY : Big-O, Ω, Θ Analysis");
        System.out.println("  +----------------------------------------------------------+");
        System.out.println("  | Algorithm        | Best(Ω)  | Avg(Θ)   | Worst(O)       |");
        System.out.println("  +----------------------------------------------------------+");
        System.out.println("  | Linear Search    | Ω(1)     | Θ(n)     | O(n)           |");
        System.out.println("  | Binary Search    | Ω(1)     | Θ(log n) | O(log n)       |");
        System.out.println("  | Bubble Sort      | Ω(n)     | Θ(n²)    | O(n²)          |");
        System.out.println("  | Selection Sort   | Ω(n²)    | Θ(n²)    | O(n²)          |");
        System.out.println("  | Insertion Sort   | Ω(n)     | Θ(n²)    | O(n²)          |");
        System.out.println("  | Merge Sort       | Ω(nlogn) | Θ(nlogn) | O(n log n)     |");
        System.out.println("  | Quick Sort       | Ω(nlogn) | Θ(nlogn) | O(n²)          |");
        System.out.println("  | HashMap get/put  | Ω(1)     | Θ(1)     | O(n) collision |");
        System.out.println("  +----------------------------------------------------------+");
        System.out.println("\n  Applied to Nexora:");
        System.out.println("  -> Login credential lookup  : HashMap O(1) average");
        System.out.println("  -> Event name search        : Linear O(n) / Binary O(log n)");
        System.out.println("  -> Event list by price sort : Merge Sort O(n log n)");
        System.out.println("  -> Ticket ID sort           : Quick Sort O(n log n) avg");

        // ========================================================
        // CO1 - LINEAR SEARCH : Find event by name
        // ========================================================
        section("CO1 - LINEAR SEARCH : Find Event by Name");
        // Events list for searching
        String[] eventNames = {
            "Tech Summit 2025", "Music Fest", "Startup Expo",
            "AI Workshop", "Cultural Night", "Sports Meet",
            "Art Exhibition", "Food Festival", "Career Fair", "Hackathon 2025"
        };
        System.out.println("  Events in system:");
        for (int i = 0; i < eventNames.length; i++)
            System.out.printf("  [%2d] %s%n", i, eventNames[i]);

        System.out.println("\n  Searching for 'AI Workshop' (Linear Search - O(n)):");
        linearSearch(eventNames, "AI Workshop");

        System.out.println("  Searching for 'Hackathon 2025' (Linear Search - O(n)):");
        linearSearch(eventNames, "Hackathon 2025");

        System.out.println("  Searching for 'Dance Night' (not in list):");
        linearSearch(eventNames, "Dance Night");

        // ========================================================
        // CO1 - BUBBLE SORT : Sort events by ticket price
        // ========================================================
        section("CO1 - BUBBLE SORT : Sort Events by Ticket Price (Low to High) - O(n²)");
        int[] prices = {500, 150, 800, 200, 1200, 100, 600, 350, 900, 250};
        String[] priceEvents = eventNames.clone();
        System.out.println("  Before Sort:");
        for (int i = 0; i < priceEvents.length; i++)
            System.out.printf("  %-25s -> Rs.%d%n", priceEvents[i], prices[i]);
        bubbleSort(prices, priceEvents);
        System.out.println("\n  After Bubble Sort (Price Low to High):");
        for (int i = 0; i < priceEvents.length; i++)
            System.out.printf("  %-25s -> Rs.%d%n", priceEvents[i], prices[i]);

        // ========================================================
        // CO1 - SELECTION SORT : Sort by event capacity
        // ========================================================
        section("CO1 - SELECTION SORT : Sort Events by Capacity (High to Low) - O(n²)");
        int[] capacity = {500, 1200, 300, 800, 2000, 150, 600, 1000, 400, 750};
        String[] capEvents = eventNames.clone();
        System.out.println("  Before Sort:");
        for (int i = 0; i < capEvents.length; i++)
            System.out.printf("  %-25s -> Capacity: %d%n", capEvents[i], capacity[i]);
        selectionSort(capacity, capEvents);
        System.out.println("\n  After Selection Sort (Capacity High to Low):");
        for (int i = 0; i < capEvents.length; i++)
            System.out.printf("  %-25s -> Capacity: %d%n", capEvents[i], capacity[i]);

        // ========================================================
        // CO1 - INSERTION SORT : Sort usernames alphabetically
        // ========================================================
        section("CO1 - INSERTION SORT : Sort Usernames Alphabetically - O(n²)");
        String[] usernames = {"riya", "aarav", "mohana", "zara", "dev", "priya", "arjun"};
        System.out.println("  Before Sort: " + Arrays.toString(usernames));
        insertionSort(usernames);
        System.out.println("  After Insertion Sort: " + Arrays.toString(usernames));

        // ========================================================
        // CO1 - MERGE SORT : Sort ticket IDs
        // ========================================================
        section("CO1 - MERGE SORT : Sort Ticket IDs - O(n log n)");
        int[] ticketIDs = {1045, 1002, 1089, 1011, 1067, 1034, 1078, 1023};
        System.out.println("  Before Merge Sort: " + Arrays.toString(ticketIDs));
        mergeSort(ticketIDs, 0, ticketIDs.length - 1);
        System.out.println("  After  Merge Sort: " + Arrays.toString(ticketIDs));

        // ========================================================
        // CO1 - QUICK SORT : Sort registration timestamps
        // ========================================================
        section("CO1 - QUICK SORT : Sort Registration Timestamps - O(n log n) avg");
        int[] timestamps = {1720, 1645, 1800, 1530, 1910, 1700, 1615, 1755};
        System.out.println("  Before Quick Sort (HHMM format): " + Arrays.toString(timestamps));
        quickSort(timestamps, 0, timestamps.length - 1);
        System.out.println("  After  Quick Sort: " + Arrays.toString(timestamps));

        // ========================================================
        // CO1 - BINARY SEARCH : Search sorted ticket ID
        // ========================================================
        section("CO1 - BINARY SEARCH : Search Ticket ID (list must be sorted) - O(log n)");
        System.out.println("  Sorted ticket IDs: " + Arrays.toString(ticketIDs));
        System.out.println("\n  Searching for ticket ID 1067:");
        binarySearch(ticketIDs, 1067);
        System.out.println("  Searching for ticket ID 1023:");
        binarySearch(ticketIDs, 1023);
        System.out.println("  Searching for ticket ID 9999 (not present):");
        binarySearch(ticketIDs, 9999);

        // ========================================================
        // CO2 - SINGLY LINKED LIST : Registered Users
        // ========================================================
        section("CO2 - SINGLY LINKED LIST : Registered Users Chain");
        System.out.println("  Inserting users into singly linked list:");
        registeredUsers.traversal();
        System.out.println();
        System.out.println("  Searching for user 'mohana':");
        registeredUsers.search("mohana");
        System.out.println("\n  Searching for user 'unknown_user':");
        registeredUsers.search("unknown_user");
        System.out.println("\n  Deleting user 'guest_user':");
        registeredUsers.delete("guest_user");
        System.out.println("\n  Updated list after deletion:");
        registeredUsers.traversal();

        // ========================================================
        // CO2 - DOUBLY LINKED LIST : Event History
        // ========================================================
        section("CO2 - DOUBLY LINKED LIST : Event History (Forward & Backward)");
        eventHistory.traverseForward();
        System.out.println();
        eventHistory.traverseBackward();
        System.out.println("\n  Deleting 'Music Fest' from history:");
        eventHistory.delete("Music Fest");
        System.out.println("  Updated forward traversal:");
        eventHistory.traverseForward();

        // ========================================================
        // CO2 - CIRCULAR LINKED LIST : Rotating Event Banners
        // ========================================================
        section("CO2 - CIRCULAR LINKED LIST : Rotating Event Banner Slots");
        bannerSlots.rotateBanners(2); // simulate 2 full rotations

        // ========================================================
        // CO3 - STACK : Login Session History (LIFO)
        // ========================================================
        section("CO3 - STACK : Login Session History (LIFO - like browser back button)");
        Stack<String> sessionStack = new Stack<>();
        String[] pages = {
            "Login Page",
            "Dashboard",
            "Browse Events",
            "Event Details: Tech Summit 2025",
            "Register for Event",
            "Payment Page",
            "Booking Confirmation"
        };
        System.out.println("  User navigating pages (PUSH onto session stack):");
        for (String page : pages) {
            sessionStack.push(page);
            System.out.println("  PUSH -> " + page + "  [Stack size: " + sessionStack.size() + "]");
        }
        System.out.println("\n  User pressing BACK button (POP from stack - LIFO):");
        int backClicks = 3;
        for (int i = 0; i < backClicks && !sessionStack.isEmpty(); i++) {
            System.out.println("  POP  <- " + sessionStack.pop() + "  [Remaining: " + sessionStack.size() + "]");
        }
        System.out.println("\n  Current page (top of stack): " + sessionStack.peek());

        // ========================================================
        // CO3 - STACK : Undo Registration (LIFO)
        // ========================================================
        section("CO3 - STACK : Undo Event Registration Actions (LIFO)");
        Stack<String> actionStack = new Stack<>();
        String[] actions = {
            "Filled Name: Mohana Asmitha",
            "Filled Email: mohana@gmail.com",
            "Selected Event: AI Workshop",
            "Selected Ticket: VIP - Rs.800",
            "Applied Coupon: NEXORA20",
            "Confirmed Booking"
        };
        System.out.println("  Performing registration actions (PUSH):");
        for (String action : actions) {
            actionStack.push(action);
            System.out.println("  PUSH -> " + action);
        }
        System.out.println("\n  User clicked UNDO twice (POP - LIFO):");
        actionStack.pop(); System.out.println("  UNDO: " + "Confirmed Booking removed");
        actionStack.pop(); System.out.println("  UNDO: " + "Coupon removed");
        System.out.println("  Current state: " + actionStack.peek());

        // ========================================================
        // CO3 - QUEUE : Event Registration Waitlist (FIFO)
        // ========================================================
        section("CO3 - QUEUE : Event Registration Waitlist (FIFO)");
        Queue<String> waitlistQueue = new LinkedList<>();
        String[] waitlistUsers = {
            "Riya Sharma", "Aarav Patel", "Mohana Asmitha",
            "Zara Khan", "Dev Raj", "Priya Singh",
            "Arjun Mehta", "Neha Gupta"
        };
        System.out.println("  Users joining waitlist (ENQUEUE):");
        for (String user : waitlistUsers) {
            waitlistQueue.offer(user);
            System.out.println("  ENQUEUE -> " + user + "  [Queue size: " + waitlistQueue.size() + "]");
        }
        System.out.println("\n  3 spots opened! Processing waitlist (DEQUEUE - FIFO):");
        for (int i = 0; i < 3; i++) {
            System.out.println("  DEQUEUE -> " + waitlistQueue.poll() + " -> Seat Confirmed!");
        }
        System.out.println("  Remaining in waitlist: " + waitlistQueue.size());
        System.out.println("  Next in line: " + waitlistQueue.peek());

        // ========================================================
        // CO3 - CIRCULAR QUEUE : Ticket Booking Slots
        // ========================================================
        section("CO3 - CIRCULAR QUEUE : Ticket Booking Time Slots");
        int SLOT_SIZE = 5;
        String[] circularQueue = new String[SLOT_SIZE];
        int front = 0, rear = 0, count = 0;

        System.out.println("  Booking slots (Circular Queue capacity = " + SLOT_SIZE + "):");
        String[] bookings = {"Slot-9AM", "Slot-10AM", "Slot-11AM", "Slot-12PM", "Slot-1PM"};
        for (String b : bookings) {
            if (count < SLOT_SIZE) {
                circularQueue[rear] = b;
                rear = (rear + 1) % SLOT_SIZE;
                count++;
                System.out.println("  ENQUEUE -> " + b + "  [Count: " + count + "]");
            }
        }
        System.out.println("\n  Processing 2 booking slots (DEQUEUE):");
        for (int i = 0; i < 2; i++) {
            System.out.println("  DEQUEUE -> " + circularQueue[front] + " processed.");
            circularQueue[front] = null;
            front = (front + 1) % SLOT_SIZE;
            count--;
        }
        System.out.println("  Adding new slot after dequeue (circular wrap-around):");
        circularQueue[rear] = "Slot-2PM";
        rear = (rear + 1) % SLOT_SIZE;
        count++;
        System.out.println("  ENQUEUE -> Slot-2PM  [Count: " + count + "]");
        System.out.print("  Current queue state: ");
        for (int i = 0; i < SLOT_SIZE; i++)
            if (circularQueue[i] != null) System.out.print(circularQueue[i] + " ");
        System.out.println();

        // ========================================================
        // CO3 - DEQUE : VIP & General Entry Management
        // ========================================================
        section("CO3 - DEQUE : VIP + General Entry Management");
        Deque<String> entryDeque = new ArrayDeque<>();
        System.out.println("  Adding General attendees to REAR:");
        entryDeque.offerLast("General-001: Riya");
        entryDeque.offerLast("General-002: Aarav");
        entryDeque.offerLast("General-003: Mohana");
        System.out.println("  Deque: " + entryDeque);

        System.out.println("\n  Adding VIP attendees to FRONT (priority entry):");
        entryDeque.offerFirst("VIP-001: Zara");
        entryDeque.offerFirst("VIP-002: Dev");
        System.out.println("  Deque: " + entryDeque);

        System.out.println("\n  Processing entries (from FRONT):");
        while (!entryDeque.isEmpty())
            System.out.println("  ENTRY -> " + entryDeque.pollFirst() + " -> Entered venue.");

        // ========================================================
        // CO4 - HASHMAP : User Credential Store (O(1) login check)
        // ========================================================
        section("CO4 - HASHMAP : Login Credential Store (O(1) lookup)");
        System.out.println("  Simulating Login Page - verifying credentials:");
        System.out.println("  Total registered users in HashMap: " + credentialStore.size());

        // Get login input
        System.out.print("\n  Enter Username to login: ");
        String loginUser = sc.nextLine().trim();
        System.out.print("  Enter Password         : ");
        String loginPass = sc.nextLine().trim();
        loginCheck(loginUser, loginPass);

        // ========================================================
        // CO4 - HASHMAP : Event Details Store
        // ========================================================
        section("CO4 - HASHMAP : Event Details Store");
        System.out.println("  All events stored in HashMap (" + eventDetails.size() + " events):");
        for (Map.Entry<String, String> e : eventDetails.entrySet())
            System.out.printf("  %-25s -> %s%n", e.getKey(), e.getValue());

        // ========================================================
        // CO4 - HASHMAP : Ticket Price Mapping
        // ========================================================
        section("CO4 - HASHMAP : Ticket Price Mapping (Map<String, Integer>)");
        System.out.println("  Event ticket prices:");
        for (Map.Entry<String, Integer> e : ticketPrices.entrySet())
            System.out.printf("  %-25s -> Rs. %d%n", e.getKey(), e.getValue());

        // CO4: Dynamic update
        System.out.println("\n  [PUT]    Adding new event 'Robotics Expo' -> Rs.700");
        ticketPrices.put("Robotics Expo", 700);
        System.out.println("  [UPDATE] Changing 'Music Fest' price to Rs.250");
        ticketPrices.put("Music Fest", 250);
        System.out.println("  [REMOVE] Removing 'Food Festival'");
        ticketPrices.remove("Food Festival");
        System.out.println("  Updated ticket prices:");
        for (Map.Entry<String, Integer> e : ticketPrices.entrySet())
            System.out.printf("  %-25s -> Rs. %d%n", e.getKey(), e.getValue());

        // ========================================================
        // CO4 - LIST : Events registered by each user
        // ========================================================
        section("CO4 - LIST (Java Collections) : Events Registered per User");
        for (Map.Entry<String, List<String>> e : userEvents.entrySet())
            System.out.printf("  User: %-15s -> Events: %s%n", e.getKey(), e.getValue());

        // ========================================================
        // CO5 - REAL WORLD APPLICATION : Full Registration Flow
        // ========================================================
        section("CO5 - REAL WORLD APPLICATION : Complete Event Registration Flow");

        System.out.print("  Enter your name to register for an event: ");
        String regName = sc.nextLine().trim();

        System.out.println("\n  Available Events & Prices:");
        int idx = 1;
        List<String> eventKeys = new ArrayList<>(ticketPrices.keySet());
        for (String ev : eventKeys)
            System.out.printf("  [%2d] %-25s -> Rs. %d%n", idx++, ev, ticketPrices.get(ev));

        System.out.print("\n  Enter event number to register: ");
        int choice = 0;
        try { choice = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { choice = 1; }
        if (choice < 1 || choice > eventKeys.size()) choice = 1;
        String chosenEvent = eventKeys.get(choice - 1);

        System.out.println("\n  Processing registration for: " + regName);
        System.out.println("  Event   : " + chosenEvent);
        System.out.println("  Price   : Rs. " + ticketPrices.get(chosenEvent));

        // CO3: Push to session stack
        Stack<String> regStack = new Stack<>();
        regStack.push("Opened Registration Form");
        regStack.push("Entered Name: " + regName);
        regStack.push("Selected Event: " + chosenEvent);
        regStack.push("Payment: Rs." + ticketPrices.get(chosenEvent));
        System.out.println("\n  Registration steps stacked (LIFO):");
        while (!regStack.isEmpty())
            System.out.println("  <- " + regStack.pop());

        // CO4: Add to user events map
        userEvents.put(regName, Arrays.asList(chosenEvent));
        System.out.println("\n  [CO4-HashMap] Registration saved: " + regName + " -> " + chosenEvent);

        // CO2: Add to singly linked list
        registeredUsers.insert(regName, regName.toLowerCase().replace(" ", "") + "@nexora.com", "pass123");
        System.out.println("\n  [CO2-SinglyList] User added to registered users chain.");

        // CO3: Add to confirmation queue
        Queue<String> confirmQueue = new LinkedList<>();
        confirmQueue.offer(regName + " | " + chosenEvent + " | CONFIRMED");
        System.out.println("\n  [CO3-Queue] Confirmation queued: " + confirmQueue.poll());

        // Final ticket
        System.out.println("\n  +---------------------------------------------------+");
        System.out.println("  |           NEXORA EVENT CONNECT - TICKET           |");
        System.out.println("  +---------------------------------------------------+");
        System.out.printf ("  |  Name    : %-39s|%n", regName);
        System.out.printf ("  |  Event   : %-39s|%n", chosenEvent);
        System.out.printf ("  |  Price   : Rs. %-36s|%n", ticketPrices.get(chosenEvent));
        System.out.printf ("  |  Ticket# : NEX-%04d                               |%n", (int)(Math.random() * 9000 + 1000));
        System.out.println("  |  Status  : CONFIRMED                              |");
        System.out.println("  +---------------------------------------------------+");
        System.out.println("  |       Thank you for registering with Nexora!      |");
        System.out.println("  +---------------------------------------------------+");

        System.out.println();
        System.out.println("================================================================");
        System.out.println("  All CO1 to CO5 demonstrated successfully!");
        System.out.println("  Nexora Dynamic Event Connect - DSA Implementation");
        System.out.println("================================================================");

        sc.close();
    }

    // ============================================================
    // CO1 - LINEAR SEARCH  O(n)
    // Best: Ω(1) - found at first position
    // Worst: O(n) - found at last or not found
    // ============================================================
    static void linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase(target)) {
                System.out.println("  FOUND \"" + target + "\" at index [" + i + "]");
                return;
            }
        }
        System.out.println("  \"" + target + "\" NOT FOUND in event list.");
    }

    // ============================================================
    // CO1 - BINARY SEARCH  O(log n)
    // Best: Ω(1) - found at mid
    // Worst: O(log n) - keeps halving
    // Array must be sorted before calling
    // ============================================================
    static void binarySearch(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1, step = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            step++;
            if (arr[mid] == target) {
                System.out.println("  FOUND " + target + " at index [" + mid + "] in " + step + " step(s).");
                return;
            } else if (arr[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        System.out.println("  " + target + " NOT FOUND after " + step + " step(s).");
    }

    // ============================================================
    // CO1 - BUBBLE SORT  O(n²)
    // Best: Ω(n) - already sorted
    // Sorts prices with paired event names
    // ============================================================
    static void bubbleSort(int[] prices, String[] events) {
        int n = prices.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (prices[j] > prices[j + 1]) {
                    int tmp = prices[j]; prices[j] = prices[j+1]; prices[j+1] = tmp;
                    String te = events[j]; events[j] = events[j+1]; events[j+1] = te;
                }
    }

    // ============================================================
    // CO1 - SELECTION SORT  O(n²)
    // Best/Worst: O(n²) - always scans remaining
    // Sorts capacity descending with paired event names
    // ============================================================
    static void selectionSort(int[] cap, String[] events) {
        int n = cap.length;
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++)
                if (cap[j] > cap[maxIdx]) maxIdx = j;
            if (maxIdx != i) {
                int tmp = cap[i]; cap[i] = cap[maxIdx]; cap[maxIdx] = tmp;
                String te = events[i]; events[i] = events[maxIdx]; events[maxIdx] = te;
            }
        }
    }

    // ============================================================
    // CO1 - INSERTION SORT  O(n²)
    // Best: Ω(n) - nearly sorted input
    // Sorts string array alphabetically
    // ============================================================
    static void insertionSort(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            String key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareToIgnoreCase(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ============================================================
    // CO1 - MERGE SORT  O(n log n)
    // Best/Avg/Worst: Θ(n log n) - always divides equally
    // Stable sort - good for ticket ID ordering
    // ============================================================
    static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        int[] L = new int[n1], R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // ============================================================
    // CO1 - QUICK SORT  O(n log n) avg, O(n²) worst
    // Best/Avg: Ω(n log n) - good pivot choices
    // Worst: O(n²) - sorted input with bad pivot
    // ============================================================
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++)
            if (arr[j] <= pivot) { i++; int t = arr[i]; arr[i] = arr[j]; arr[j] = t; }
        int t = arr[i+1]; arr[i+1] = arr[high]; arr[high] = t;
        return i + 1;
    }

    // ============================================================
    // CO4 - Login Check using HashMap O(1) average
    // ============================================================
    static void loginCheck(String username, String password) {
        System.out.println("\n  [LOGIN ATTEMPT] Username: " + username);
        if (credentialStore.containsKey(username)) {
            if (credentialStore.get(username).equals(password)) {
                System.out.println("  [SUCCESS] Login successful! Welcome, " + username + "!");
                System.out.println("  [CO4-HashMap] Email on record: " + userEmailStore.get(username));
            } else {
                System.out.println("  [FAILED] Incorrect password for \"" + username + "\".");
            }
        } else {
            System.out.println("  [FAILED] Username \"" + username + "\" not found. Please create account.");
        }
    }

    // ============================================================
    // LOAD DATA METHODS
    // ============================================================

    // CO4 - Load credential HashMap & CO2 - Load Singly List
    static void loadCredentials() {
        // CO4: HashMap - O(1) login lookup
        credentialStore.put("mohana",     "pass123");
        credentialStore.put("riya",       "riya@456");
        credentialStore.put("aarav",      "aarav789");
        credentialStore.put("zara",       "zara2025");
        credentialStore.put("dev",        "dev@nexora");
        credentialStore.put("priya",      "priya321");
        credentialStore.put("guest_user", "guest");

        userEmailStore.put("mohana",     "mohana@gmail.com");
        userEmailStore.put("riya",       "riya@gmail.com");
        userEmailStore.put("aarav",      "aarav@gmail.com");
        userEmailStore.put("zara",       "zara@gmail.com");
        userEmailStore.put("dev",        "dev@gmail.com");
        userEmailStore.put("priya",      "priya@gmail.com");
        userEmailStore.put("guest_user", "guest@nexora.com");

        // CO4: List per user
        userEvents.put("mohana", Arrays.asList("Tech Summit 2025", "AI Workshop"));
        userEvents.put("riya",   Arrays.asList("Music Fest", "Cultural Night"));
        userEvents.put("aarav",  Arrays.asList("Hackathon 2025"));
        userEvents.put("zara",   Arrays.asList("Art Exhibition", "Food Festival"));
    }

    // CO4 - Load event data HashMaps
    static void loadEventData() {
        eventDetails.put("Tech Summit 2025",    "Annual tech conference | Venue: Chennai Trade Centre");
        eventDetails.put("Music Fest",           "Live music event | Venue: Marina Beach Stage");
        eventDetails.put("Startup Expo",         "Startup pitching event | Venue: IIT Madras");
        eventDetails.put("AI Workshop",          "Hands-on AI/ML workshop | Venue: Anna University");
        eventDetails.put("Cultural Night",       "Cultural performances | Venue: College Auditorium");
        eventDetails.put("Hackathon 2025",       "24-hour coding hackathon | Venue: Online + Offline");
        eventDetails.put("Art Exhibition",       "Modern art showcase | Venue: Lalit Kala Akademi");
        eventDetails.put("Food Festival",        "Street food festival | Venue: Express Avenue Mall");
        eventDetails.put("Career Fair",          "Top companies hiring | Venue: CODISSIA Trade Fair");
        eventDetails.put("Sports Meet",          "Inter-college sports | Venue: Jawaharlal Stadium");

        ticketPrices.put("Tech Summit 2025", 500);
        ticketPrices.put("Music Fest",        150);
        ticketPrices.put("Startup Expo",      800);
        ticketPrices.put("AI Workshop",       200);
        ticketPrices.put("Cultural Night",    100);
        ticketPrices.put("Hackathon 2025",    600);
        ticketPrices.put("Art Exhibition",    350);
        ticketPrices.put("Food Festival",     250);
        ticketPrices.put("Career Fair",       0);
        ticketPrices.put("Sports Meet",       300);
    }

    // CO2 - Load all Linked Lists
    static void loadLinkedLists() {
        // Singly Linked List - registered users
        registeredUsers.insert("mohana",     "mohana@gmail.com",  "pass123");
        registeredUsers.insert("riya",       "riya@gmail.com",    "riya@456");
        registeredUsers.insert("aarav",      "aarav@gmail.com",   "aarav789");
        registeredUsers.insert("zara",       "zara@gmail.com",    "zara2025");
        registeredUsers.insert("dev",        "dev@gmail.com",     "dev@nexora");
        registeredUsers.insert("guest_user", "guest@nexora.com",  "guest");

        // Doubly Linked List - event history
        eventHistory.insert("Music Fest",        "2025-01-10");
        eventHistory.insert("AI Workshop",        "2025-02-15");
        eventHistory.insert("Startup Expo",       "2025-03-20");
        eventHistory.insert("Hackathon 2025",     "2025-04-05");
        eventHistory.insert("Tech Summit 2025",   "2025-05-18");

        // Circular Linked List - banner slots
        bannerSlots.insert("Tech Summit 2025  - Register Now!");
        bannerSlots.insert("Hackathon 2025    - Win Big Prizes!");
        bannerSlots.insert("AI Workshop       - Limited Seats!");
        bannerSlots.insert("Cultural Night    - Free Entry!");
    }

    // ============================================================
    // HELPERS
    // ============================================================
    static void banner(String title) {
        System.out.println("================================================================");
        System.out.println("  " + title);
        System.out.println("================================================================\n");
    }

    static void section(String title) {
        System.out.println("\n----------------------------------------------------------------");
        System.out.println("  " + title);
        System.out.println("----------------------------------------------------------------");
    }
}