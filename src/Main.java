import java.util.*;

public class Main {
    public static void main(String[] args) {

        Process process = new Process();
        if(!process.open()) {
            System.out.println("Failed to connect ");
            return;
        }
        System.out.println("Connected to database");

        Scanner sc = new Scanner(System.in);
        optionsDisplay();
        System.out.println("Enter your choice ( 1 is to display options )");
        int opt = sc.nextInt();
        do {
            switch (opt) {
                case 1:
                    optionsDisplay();
                    break;
                case 2:
                    int count = process.insertRecord();
                    if (count <= 0) {
                        System.out.println("Failed to insert record(s)");
                    } else {
                        System.out.println(count + " Records inserted");
                    }
                    break;
                case 3:
                    int count1 = process.deleteRecord();
                    if (count1 <= 0) {
                        System.out.println("Failed to delete record(s)");
                    } else {
                        System.out.println(count1 + " Records deleted");
                    }
                    break;
                case 4:
                    int count2 = process.updateRecord();
                    if (count2 <= 0) {
                        System.out.println("Failed to update record(s)");
                    } else {
                        System.out.println(count2 + " Records updated");
                    }
                    break;
                case 5:
                    List<Contact> contacts = process.queryContact();
                    if (contacts == null) {
                        System.out.println("No data found");
                        return;
                    }
                    for (Contact contact : contacts) {
                        System.out.println(contact.toString());
                    }
                    break;
                default:
                    System.out.println("Enter valid choice");
                    break;
            }
            System.out.println("Enter your choice ( 1 to display options )");
            //optionsDisplay();
            opt = sc.nextInt();
        } while (opt <= 5);
        process.close();
        System.out.println("Connection closed");
    }

    private static void optionsDisplay() {
        System.out.println("1. Display Available Options");
        System.out.println("2. Insert Record");
        System.out.println("3. Delete Record");
        System.out.println("4. Update Record");
        System.out.println("5. Display All Records");
    }
}
