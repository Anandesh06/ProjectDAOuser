package com.conn;
import com.sun.tools.javac.Main;
import java.io.Console;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class connection123 {
    public Connection getdbconnection() {
        Connection connection = null;
        try {
            //register driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukebox";
            String user = "root";
            String password = "root@123";
            //get the com.conn.connection using DriverManager
            connection = DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }


    public  void jukeboxRegisteration() throws SQLException, ClassNotFoundException {
        Connection connection = getdbconnection();
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("email");
        String emal = scanner.nextLine();
        String str = "Select * from jukebox.user where user_email ='" + emal + "';";
        System.out.println(str);
        ResultSet resultSet = statement.executeQuery(str);
        String checkemail = null;
        while (resultSet.next()) {
            checkemail = resultSet.getString(3);
        }
        System.out.println(checkemail);

        String name = null;
        String password = null;
        String dob = null;
        if (emal.equals(checkemail)) {
            System.out.println("You already have a jukebox account.\n please login");
            jukeboxLogin();

        } else {
            System.out.print("enter name:");
            name = scanner.next();
            System.out.println("password");
            password = scanner.next();
            System.out.println("dob");
            dob = scanner.nextLine();
        }

        Statement statement1 = connection.createStatement();
        String regQuery = "Insert into jukebox.user(user_name,user_email,user_password,user_DOB) " +
                "values('" + name + "','" + emal + "','" + password + "','" + dob +"');";
        System.out.println(regQuery);
                int i= statement.executeUpdate(regQuery);
    }


 /*   public String  Password(){
        String password;
        Scanner paswrdSC=new Scanner(System.in);
        password=paswrdSC.nextLine();

        return  password;
    }*/

    public String  lgPassword(String email,String pass) throws SQLException {

        Scanner loginSc=new Scanner(System.in);
        System.out.println("Enter the password");
        String password=loginSc.nextLine();
        if (password.equals(pass)) {
            return  password;
        }else {
            Connection connection = getdbconnection();
            Statement stmtPaswrd = connection.createStatement();
            String str = "Select * from jukebox.user where user_email='" + email + "';";
            ResultSet rs = stmtPaswrd.executeQuery(str);
            String getpassword = null;
            String getdob = null;
            while (rs.next()) {
                getpassword = rs.getString(4);
                getdob = rs.getString(5);
            }
            if (password.equals(getpassword)) {
                System.out.println("password same");
            } else {
                System.out.println("Enter choice");
                int c = loginSc.nextInt();
                switch (c) {
                    case 1:
                        password=lgPassword(email, pass);
                        break;
                    case 2:
                        System.out.println("forgot password");
                        System.out.println("Enter the DOB:");
                        String dob1 = loginSc.next();
                        if (dob1.equals(getdob)) {
                            System.out.println("enter a new password");
                            String newPaswrd = loginSc.next();
                            stmtPaswrd.executeUpdate("update jukebox.user set user_password='" + newPaswrd + "' where user_email='" + email + "'; ");
                        }
                            break;
                    default:
                        System.out.println("enter the correct option");

                        break;
                }
            }
        }

            return password;
    }

    public void jukeboxLogin() throws SQLException, ClassNotFoundException {
        Connection connection=getdbconnection();
        Statement statement=connection.createStatement();
        Scanner loginSc=new Scanner(System.in);
        System.out.println("Please enter your email:");
        String loginemail=loginSc.nextLine();
        ResultSet resultSet1=statement.executeQuery("SELECT * from jukebox.user where user_email='"+loginemail+"';");
        String checkloginemial=null;
        String checkpass=null;
        while (resultSet1.next()) {
            checkloginemial= resultSet1.getString(3);
            checkpass=resultSet1.getString(4);
        }
        String loginpass=null;

        if(checkloginemial==null) {
            System.out.println("You don't have a juke box account\n please register the account.");
            jukeboxRegisteration();
        } else if (checkloginemial.equals(loginemail)) {
            System.out.println("Enter the password");
            loginpass = lgPassword(checkloginemial, checkpass);
            System.out.println(loginpass);
        }
        if (checkpass.equals(loginpass)){
            System.out.println("enjoy the music");
            }
    }

        public static void main(String[] args) throws SQLException, ClassNotFoundException , IOException {
        connection123 connection1= new connection123();
        connection1.jukeboxRegisteration();
        connection1.jukeboxLogin();
    }
}
