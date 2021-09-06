import java.io.*;
import java.sql.*;
import java.util.*;

public class Process {

    private static Connection conn = null;

    private static Properties loadPropertiesFile() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/MyProperties.properties"));
            return properties;
        } catch (IOException e) {
            System.out.println("Error loading properties " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean open() {
        try {
            Properties properties = loadPropertiesFile();
            //String DB_DRIVER = properties.getProperty("db.driver.class.name");
            if(properties == null) {
                return false;
            }
            String DB_NAME = properties.getProperty("db.name");
            String URL = properties.getProperty("db.url") + DB_NAME;
            String USER = properties.getProperty("db.user");
            String PASSWORD = properties.getProperty("db.password");

            //Class.forName(properties.getProperty(DB_DRIVER));
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            return conn != null;
        }catch(SQLException e) {
            System.out.println("Error while connecting " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if(conn != null) {
                conn.close();
            }
            }catch(SQLException e) {
                System.out.println("Error while closing connection " + e.getMessage());
        }
    }

    private static void closeStatement(Statement st) {
        try {
            if(st != null) {
                st.close();
            }
        }catch(SQLException e) {
            System.out.println("Error closing Statement " + e.getMessage() );
            e.printStackTrace();
        }
    }

    private static void closeResultSet(ResultSet result) {
        try {
            if(result != null) {
                result.close();
            }
        }catch(SQLException e) {
            System.out.println("Error closing ResultSet " + e.getMessage() );
            e.printStackTrace();
        }
    }
    private static Contact getContactDetails() {
        Contact con = new Contact();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter contact details");

        System.out.println("Enter transaction id");
        int transaction_id = sc.nextInt();sc.nextLine();
        System.out.println("Enter client name");
        String client_name = sc.nextLine();
        System.out.println("Enter account number");
        int account_num = sc.nextInt();sc.nextLine();
        System.out.println("Enter transaction type");
        String transaction_type = sc.nextLine();
        System.out.println("Enter amount credit/debit");
        String amount_cred_debit = sc.nextLine();
        System.out.println("Enter amount");
        double amount = sc.nextDouble();

        con.setTransaction_id(transaction_id);
        con.setClient_name(client_name);
        con.setAccount_num(account_num);
        con.setTransaction_type(transaction_type);
        con.setAmount_deb_cred(amount_cred_debit);
        con.setAmount(amount);

        return con;
    }

    public int insertRecord() {
        Contact con = getContactDetails();
        String query = String.format("INSERT INTO transactions VALUES(%d,'%s',%d,'%s','%s',%f)",
                                    con.getTransaction_id(),
                                    con.getClient_name(),
                                    con.getAccount_num(),
                                    con.getTransaction_type(),
                                    con.getAmount_deb_cred(),
                                    con.getAmount());
        Statement st = null;
        try {
            st = conn.createStatement();
            st.execute(query);
            return st.getUpdateCount();
        }catch(NullPointerException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch(SQLException e) {
            System.out.println("Error inserting record " + e.getMessage());
            return 0;
        }finally {
            closeStatement(st);
        }
    }

    public List<Contact> queryContact() {
        Statement st = null;
        ResultSet result = null;
        List<Contact> contacts = new ArrayList<>();
        String query1 = "SELECT * FROM transactions";
        try {
            st = conn.createStatement();
            result = st.executeQuery(query1);
            while(result.next()) {
                Contact contact = new Contact();
                contact.setTransaction_id(result.getInt(1));
                contact.setClient_name(result.getString(2));
                contact.setAccount_num(result.getInt(3));
                contact.setTransaction_type(result.getString(4));
                contact.setAmount_deb_cred(result.getString(5));
                contact.setAmount(result.getDouble(6));
                contacts.add(contact);
            }
            return contacts;
            }catch(SQLException e) {
                System.out.println("Error in queryContact " + e.getMessage());
                return null;
            }finally {
                closeResultSet(result);
                closeStatement(st);
        }
    }

    public int deleteRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter transaction id to delete account");
        int transaction_id = sc.nextInt();

        String query = String.format("DELETE FROM transactions WHERE transaction_id = %d",transaction_id);
        Statement st = null;

        try {
            st = conn.createStatement();
            st.execute(query);
            return st.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }finally {
            closeStatement(st);
        }
    }

    public int updateRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter transaction id to update amount");
        int transaction_id = sc.nextInt();
        System.out.println("Enter amount to update");
        double amount = sc.nextDouble();

        String query = String.format("UPDATE transactions SET amount = %f WHERE transaction_id = %d",amount,transaction_id);
        Statement st = null;

        try {
            st = conn.createStatement();
            st.execute(query);
            return st.getUpdateCount();
        } catch (SQLException e) {
            System.out.println("Error updating record " + e.getMessage());
            e.printStackTrace();
            return 0;
        }finally{
            closeStatement(st);
        }
    }
}