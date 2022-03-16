package es.unex.smartgreenadapt.db;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import es.unex.smartgreenadapt.model.AirQuality;
import es.unex.smartgreenadapt.model.Humidity;
import es.unex.smartgreenadapt.model.Luminosity;
import es.unex.smartgreenadapt.model.Notification;
import es.unex.smartgreenadapt.model.Temperature;
import es.unex.smartgreenadapt.ui.notifications.ListNotificationAdapter;

public class DBConn {

    private Connection conn;

    private static DBConn mInstance;

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String SERVER_ADDRESS = "192.168.1.104";
    public static final String PORT = "3306";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String DATABASE = "smartgreenadapt";

    public static synchronized DBConn getInstance(){
        if(mInstance == null){
            mInstance = new DBConn();
        }
        return mInstance;
    }

    /**
     * Method to this.connect to the MySQL database
     * @return True if the this.connection is successful or False if not
     */
    public boolean dbConnect() {
        try {
            String driver = DRIVER;
            String serverAddress = SERVER_ADDRESS;
            String port = PORT;
            String user = USER;
            String password = PASSWORD;
            String database = DATABASE;

            Class.forName(driver);

            Log.println(Log.ASSERT, "info" , "driver ready");
            Log.println(Log.ASSERT, "info" , "jdbc:mysql://" + serverAddress +"/" + database + "?user=" + user + "&password=" + password);
            this.conn = DriverManager.getConnection("jdbc:mysql://" + serverAddress +"/" + database + "?user=" + user + "&password=" + password);
            System.out.println("Connection successful");

            if (!this.conn.isClosed())
                return true;
            else
                return false;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "info" , "NNNOOOOOOOOOOOOTTTT Connection");
            return false;
        }

    }

    /**
     * Method to disthis.connect to the MySQL database
     * @return True if the disthis.connection is successful or False if not
     */
    public boolean dbDisconnect() {
        try {
            this.conn.close();
            if (this.conn.isClosed())
                return true;
            else
                return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Method to get the info from the airQuality table
     */
    public ArrayList<AirQuality> getAirQuality() {
        try {
            ArrayList<AirQuality> arrAirQuality = new ArrayList<AirQuality>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM airquality"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                AirQuality a = new AirQuality((Date) rset.getDate(3), rset.getInt(2));
                arrAirQuality.add(a);
            }
            return arrAirQuality;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the info from the humidity table
     */
    public ArrayList<Humidity> getHumidity() {
        try {
            ArrayList<Humidity> arrHumidity = new ArrayList<Humidity>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM humidity"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                Humidity h = new Humidity((Date) rset.getDate(3), rset.getInt(2));
                arrHumidity.add(h);
            }
            return arrHumidity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the info from the luminosity table
     */
    public ArrayList<Luminosity> getLuminosity() {
        try {
            ArrayList<Luminosity> arrLuminosity = new ArrayList<Luminosity>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM luminosity"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                Luminosity l = new Luminosity((Date) rset.getDate(3), rset.getInt(2));
                arrLuminosity.add(l);
            }
            return arrLuminosity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the info from the noise table
     */
    public ArrayList<Notification> getNotification() {
        try {
            ArrayList<Notification> arrNoise = new ArrayList<Notification>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM notification"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                Notification n = new Notification(rset.getDate(3), rset.getString(2));
                arrNoise.add(n);
            }
            return arrNoise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the info from the temperature table
     */
    public ArrayList<Temperature> getTemperature() {
        try {
            ArrayList<Temperature> arrTemperature = new ArrayList<Temperature>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM temperature"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                Temperature t = new Temperature((Date) rset.getDate(3), rset.getInt(2));
                arrTemperature.add(t);
            }
            return arrTemperature;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the info from the weather table

    public ArrayList<Weather> getWeather() {
        try {
            ArrayList<Weather> arrWeather = new ArrayList<Weather>();
            Statement st;
            st = this.conn.createStatement();
            String sql = "SELECT * FROM weather"; // TODO Get those who has the date 5 minutes less than actual for example
            ResultSet rset = st.executeQuery(sql);
            while(rset.next()) {
                Weather w = new Weather(rset.getString(2), rset.getDouble(3), rset.getDouble(4), rset.getDouble(5), rset.getDouble(6), rset.getInt(7), rset.getInt(8), rset.getDouble(9), rset.getDate(10), rset.getString(11));
                arrWeather.add(w);
            }
            return arrWeather;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
