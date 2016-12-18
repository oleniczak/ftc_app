package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
PURPOSE:
    Loads configurations constants from configproperties.txt
*/

public class RobotConfiguration
{
    private Telemetry telemetry;

    private Properties properties = new Properties();

    //InputStream in = null;

    //Defaults are being set within the configuration to ensure that if the configs fail to load,
    //settings will still take place.  The configproperties.txt would/should override these defaults.

    public static String ALLIANCE ;
    public static String START_POSITION ;

    public static double DRIVE_POWER = 0.6;
    public static double TURN_POWER = 0.5;
    public static double LAUNCH_POWER = 1.0;
    public static double APPROACH_SPEED = 0.25;

    public static double CLOSED_BALL_GATE_POS = 0.7;
    public static double OPEN_BALL_GATE_POS = 0.0;
    public static double CLOSED_FORK_SERVO_POS = 1.0;
    public static double OPEN_FORK_SERVO_POS = 0.4;
    public static double START_BUTTON_POS = 1.0;
    public static double DETECTION_BUTTON_POS = 0.6;

    public static int BALL_GATE_OPEN_TIME = 2000;
    public static int LAUNCH_TIME = 1000;
    public static int LONG_AUTO_DRIVE_TIME = 3000;
    public static int SHORT_AUTO_DRIVE_TIME = 1500;
    public static int NINETY_DEGREE_TURN_TIME = 100;
    public static int AUTO_DELAY_TIME = 10000;

    private static double COUNTS_PER_MOTOR_REV = 1120;
    private static double DRIVE_GEAR_REDUCTION = 0.3;
    private static double WHEEL_DIAMETER_INCHES = 4.0;
    public static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

    /* Constructor */
    public RobotConfiguration(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    public void loadParameters()
    {
        telemetry.addData("Configuration", "Begin Loading Parameters...");
        telemetry.update();

        try
        {
            //* Keep for troubleshooting
            //System.out.println(System.getenv());
            //System.out.println(Environment.getExternalStorageDirectory());

            // ** Requires configproperties.xt to reside in root of Phone/FIRST directory (next to 9019.xml file) **
            FileInputStream in = new FileInputStream("/storage/emulated/0/FIRST/configproperties.txt");

            properties.load(in);

            DRIVE_POWER = Double.parseDouble(properties.getProperty("DRIVE_POWER"));
            TURN_POWER = Double.parseDouble(properties.getProperty("TURN_POWER"));
            LAUNCH_POWER = Double.parseDouble(properties.getProperty("LAUNCH_POWER"));
            APPROACH_SPEED = Double.parseDouble(properties.getProperty("APPROACH_SPEED"));

            CLOSED_BALL_GATE_POS = Double.parseDouble(properties.getProperty("CLOSED_BALL_GATE_POS"));
            OPEN_BALL_GATE_POS = Double.parseDouble(properties.getProperty("OPEN_BALL_GATE_POS"));
            CLOSED_FORK_SERVO_POS = Double.parseDouble(properties.getProperty("CLOSED_FORK_SERVO_POS"));
            OPEN_FORK_SERVO_POS = Double.parseDouble(properties.getProperty("OPEN_FORK_SERVO_POS"));
            START_BUTTON_POS = Double.parseDouble(properties.getProperty("START_BUTTON_POS"));
            DETECTION_BUTTON_POS = Double.parseDouble(properties.getProperty("DETECTION_BUTTON_POS"));

            BALL_GATE_OPEN_TIME = Integer.parseInt(properties.getProperty("BALL_GATE_OPEN_TIME"));
            LAUNCH_TIME = Integer.parseInt(properties.getProperty("LAUNCH_TIME"));
            LONG_AUTO_DRIVE_TIME = Integer.parseInt(properties.getProperty("LONG_AUTO_DRIVE_TIME"));
            SHORT_AUTO_DRIVE_TIME = Integer.parseInt(properties.getProperty("SHORT_AUTO_DRIVE_TIME"));
            NINETY_DEGREE_TURN_TIME = Integer.parseInt(properties.getProperty("NINETY_DEGREE_TURN_TIME"));
            AUTO_DELAY_TIME = Integer.parseInt(properties.getProperty("AUTO_DELAY_TIME"));

            COUNTS_PER_MOTOR_REV = Double.parseDouble(properties.getProperty("COUNTS_PER_MOTOR_REV"));
            DRIVE_GEAR_REDUCTION = Double.parseDouble(properties.getProperty("DRIVE_GEAR_REDUCTION"));
            WHEEL_DIAMETER_INCHES = Double.parseDouble(properties.getProperty("WHEEL_DIAMETER_INCHES"));
            COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

            ALLIANCE = properties.getProperty("ALLIANCE");
            START_POSITION = properties.getProperty("START_POSITION");

            in.close();

            // * Keep for troubleshooting *
            //System.out.println(properties.getProperty("DRIVE_POWER"));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        telemetry.addData("Configuration", "Loading Parameters Complete!");
        telemetry.update();
    }

}