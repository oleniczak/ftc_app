package org.firstinspires.ftc.teamcode;

/*
PURPOSE:
    Loads configurations from config.properties
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RobotConfiguration
{
    double DRIVE_POWER = 0.0;
    double TURN_POWER = 0.0;
    double LAUNCH_POWER = 0.0;

    double CLOSED_BALL_GATE_POS = 0.0;
    double OPEN_BALL_GATE_POS = 0.0;
    double CLOSED_FORK_SERVO_POS = 0.0;
    double OPEN_FORK_SERVO_POS = 0.0;
    double START_BUTTON_POS = 0.0;
    double DETECTION_BUTTON_POS = 0.0;

    int BALL_GATE_OPEN_TIME = 0;
    int LAUNCH_TIME = 0;
    int LONG_AUTO_DRIVE_TIME = 0;
    int AUTO_DELAY_TIME = 0;

    double COUNTS_PER_MOTOR_REV = 0.0;
    double DRIVE_GEAR_REDUCTION = 0.0;
    double WHEEL_DIAMETER_INCHES = 0.0;
    double COUNTS_PER_INCH = 0.0;

    public void loadParameters()
    {
        Properties props = new Properties();
        //InputStream in = null;

        try
        {
            InputStream in = new FileInputStream("config.properties");
            props.load(in);

            DRIVE_POWER = Double.parseDouble(props.getProperty("DRIVE_POWER"));
            TURN_POWER = Double.parseDouble(props.getProperty("TURN_POWER"));
            LAUNCH_POWER = Double.parseDouble(props.getProperty("LAUNCH_POWER"));

            CLOSED_BALL_GATE_POS = Double.parseDouble(props.getProperty("CLOSED_BALL_GATE_POS"));
            OPEN_BALL_GATE_POS = Double.parseDouble(props.getProperty("OPEN_BALL_GATE_POS"));
            CLOSED_FORK_SERVO_POS = Double.parseDouble(props.getProperty("CLOSED_FORK_SERVO_POS"));
            OPEN_FORK_SERVO_POS = Double.parseDouble(props.getProperty("OPEN_FORK_SERVO_POS"));
            START_BUTTON_POS = Double.parseDouble(props.getProperty("START_BUTTON_POS"));
            DETECTION_BUTTON_POS = Double.parseDouble(props.getProperty("DETECTION_BUTTON_POS"));

            BALL_GATE_OPEN_TIME = Integer.parseInt(props.getProperty("BALL_GATE_OPEN_TIME"));
            LAUNCH_TIME = Integer.parseInt(props.getProperty("LAUNCH_TIME"));
            LONG_AUTO_DRIVE_TIME = Integer.parseInt(props.getProperty("LONG_AUTO_DRIVE_TIME"));
            AUTO_DELAY_TIME = Integer.parseInt(props.getProperty("AUTO_DELAY_TIME"));

            COUNTS_PER_MOTOR_REV = Double.parseDouble(props.getProperty("COUNTS_PER_MOTOR_REV"));
            DRIVE_GEAR_REDUCTION = Double.parseDouble(props.getProperty("DRIVE_GEAR_REDUCTION"));
            WHEEL_DIAMETER_INCHES = Double.parseDouble(props.getProperty("WHEEL_DIAMETER_INCHES"));
            COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

            in.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}