package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
PURPOSE:
    Loads configurations constants from configproperties.txt
*/

public class Configuration
{
    private Telemetry telemetry;

    private Properties properties = new Properties();

    //InputStream in = null;

    //Defaults are being set within the configuration to ensure that if the configs fail to load,
    //settings will still take place.  The configproperties.txt would/should override these defaults.

    public static String ALLIANCE = "RED";
    public static String START_POSITION = "SHORT";

    //Motor Power
        public static double DRIVE_POWER = 0.2;
        public static double TURN_POWER = 0.2;
        public static double APPROACH_SPEED = 0.2;
        public static double LAUNCH_POWER = 0.55;

    //Servo Positions
        public static double CLOSED_LIFT_SERVO_POS = 1.0;
        public static double OPEN_LIFT_SERVO_POS = 0.4;

    //Time Settings
        public static int AUTO_DELAY_TIME = 0000;

        public static int LAUNCH_TIME = 2000;
//      public static int LONG_AUTO_DRIVE_TIME = 3000;
//      public static int SHORT_AUTO_DRIVE_TIME = 1500;
//      public static int NINETY_DEGREE_TURN_TIME = 100;

    //Measurement Settings
        public static double NINETY_DEGREE_TURN_INCHES = 9.0;
    //  public static double LONG_AUTO_DRIVE_INCHES = 48;
    //  public static double SHORT_AUTO_DRIVE_INCHES = 36;
        private static double COUNTS_PER_MOTOR_REV = 1120;
        private static double DRIVE_GEAR_REDUCTION = 0.3;
        private static double WHEEL_DIAMETER_INCHES = 4.0;
        public static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

    //Heading Settings
        public static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
        public static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
        public static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable


    /* Constructor */
    public Configuration(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    public void loadParameters()
    {
        telemetry.addData("Configuration", "Begin Loading Parameters...");
        telemetry.update();

//        try
//        {
            //* Keep for troubleshooting
            //System.out.println(System.getenv());
            //System.out.println(Environment.getExternalStorageDirectory());

            // ** Requires configproperties.xt to reside in root of Phone/FIRST directory (next to 9019.xml file) **
            //FileInputStream in = new FileInputStream("/storage/emulated/0/FIRST/configproperties.txt");

            //properties.load(in);

            ALLIANCE = properties.getProperty("ALLIANCE");
            START_POSITION = properties.getProperty("START_POSITION");

            DRIVE_POWER = Double.parseDouble(properties.getProperty("DRIVE_POWER"));
            TURN_POWER = Double.parseDouble(properties.getProperty("TURN_POWER"));
            APPROACH_SPEED = Double.parseDouble(properties.getProperty("APPROACH_SPEED"));
            LAUNCH_POWER = Double.parseDouble(properties.getProperty("LAUNCH_POWER"));

            CLOSED_LIFT_SERVO_POS = Double.parseDouble(properties.getProperty("CLOSED_LIFT_SERVO_POS"));
            OPEN_LIFT_SERVO_POS = Double.parseDouble(properties.getProperty("OPEN_LIFT_SERVO_POS"));
//
            AUTO_DELAY_TIME = Integer.parseInt(properties.getProperty("AUTO_DELAY_TIME"));
            LAUNCH_TIME = Integer.parseInt(properties.getProperty("LAUNCH_TIME"));
//            LONG_AUTO_DRIVE_TIME = Integer.parseInt(properties.getProperty("LONG_AUTO_DRIVE_TIME"));
//            SHORT_AUTO_DRIVE_TIME = Integer.parseInt(properties.getProperty("SHORT_AUTO_DRIVE_TIME"));
//            NINETY_DEGREE_TURN_TIME = Integer.parseInt(properties.getProperty("NINETY_DEGREE_TURN_TIME"));

            NINETY_DEGREE_TURN_INCHES = Integer.parseInt(properties.getProperty("NINETY_DEGREE_TURN_INCHES"));
//            LONG_AUTO_DRIVE_INCHES = Integer.parseInt(properties.getProperty("LONG_AUTO_DRIVE_INCHES"));
//            SHORT_AUTO_DRIVE_INCHES = Integer.parseInt(properties.getProperty("SHORT_AUTO_DRIVE_INCHES"));

        //in.close();

            // * Keep for troubleshooting *
            //System.out.println(properties.getProperty("DRIVE_POWER"));

//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }

        telemetry.addData("Configuration", "Loading Parameters Complete!");
        telemetry.update();
    }
}