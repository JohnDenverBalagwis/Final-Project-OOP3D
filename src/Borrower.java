import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

//inheriting User class to Borrower class
public class Borrower extends User {
    private Date date;
    private String name;

    // constructor overload for "Borrower"
    public Borrower() {

    }

    public Borrower(String name) {
        this.name = name;
    }

    // encapsulation for name
    // getter for name
    public String getName() {
        return this.name;
    }

    // setter for name
    public void setName(String name) {
        this.name = name;
    }

    // method overload for "Borrow"
    // borrow the minimum amount
    public void Borrow() {
        date = new Date(System.currentTimeMillis());
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter("Listahan/" + name + ".txt", true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(500d + ", " + date);
            out.close();

            System.out.println("Utang has been saved to listahan.");
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
            }
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
            }
        }
    }

    // borrow the indicated amount
    public void Borrow(double amount) {
        date = new Date(System.currentTimeMillis());
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter("Listahan/" + this.name + ".txt", true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(amount + ", " + date);
            out.close();

            System.out.println("Utang has been saved to listahan.");
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
            }
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
            }
        }
    }
}

