public class Contact {
    private int transaction_id;
    private String client_name;
    private int account_num;
    private String transaction_type;
    private String amount_deb_cred;
    private double amount;

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getAccount_num() {
        return account_num;
    }

    public void setAccount_num(int account_num) {
        this.account_num = account_num;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getAmount_deb_cred() {
        return amount_deb_cred;
    }

    public void setAmount_deb_cred(String amount_deb_cred) {
        this.amount_deb_cred = amount_deb_cred;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  transaction_id + " | " +
                client_name + " | " +
                account_num + " | " +
                transaction_type + " | " +
                amount_deb_cred + " | " +
                amount;
    }
}
