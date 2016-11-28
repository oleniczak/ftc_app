package org.firstinspires.ftc.teamcode;

/*
PURPOSE:
    Sets default hardware
    Loads configurations from config.properties
*/

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RobotConfiguration extends LinearOpMode
{
    Hardware robot   = new Hardware();
    RobotConfiguration configs = new RobotConfiguration();
    Properties properties = new Properties();

    //Defaults are being set within the configuration to ensure that if the configs fail to load,
    //settings will still take place.  The config.properties would/should override these defaults.
    double DRIVE_POWER = 0.6;
    double TURN_POWER = 0.5;
    double LAUNCH_POWER = 1.0;
    double APPROACH_SPEED = 0.25;

    double CLOSED_BALL_GATE_POS = 0.7;
    double OPEN_BALL_GATE_POS = 0.0;
    double CLOSED_FORK_SERVO_POS = 1.0;
    double OPEN_FORK_SERVO_POS = 0.4;
    double START_BUTTON_POS = 1.0;
    double DETECTION_BUTTON_POS = 0.6;

    int BALL_GATE_OPEN_TIME = 2000;
    int LAUNCH_TIME = 1000;
    int LONG_AUTO_DRIVE_TIME = 1000;
    int AUTO_DELAY_TIME = 10000;

    private double COUNTS_PER_MOTOR_REV = 1120;
    private double DRIVE_GEAR_REDUCTION = 0.3;
    private double WHEEL_DIAMETER_INCHES = 4.0;
    double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

    // Constructor
    public void runOpMode()
    {
    }

    public void loadParameters()
    {
        telemetry.addData("loadParameters", "Loading Parameters...");
        telemetry.update();

        try
        {
            InputStream in = new FileInputStream("config.properties");

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
            AUTO_DELAY_TIME = Integer.parseInt(properties.getProperty("AUTO_DELAY_TIME"));

            COUNTS_PER_MOTOR_REV = Double.parseDouble(properties.getProperty("COUNTS_PER_MOTOR_REV"));
            DRIVE_GEAR_REDUCTION = Double.parseDouble(properties.getProperty("DRIVE_GEAR_REDUCTION"));
            WHEEL_DIAMETER_INCHES = Double.parseDouble(properties.getProperty("WHEEL_DIAMETER_INCHES"));
            COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592652589);

            in.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        telemetry.addData("loadParameters", "Loading Parameters Complete!");
        telemetry.update();
    }

    public void InitializeHW()
    {
        telemetry.addData("InitializeHW","Beginning HW Initialization...");
        telemetry.update();

        /* ******************************************************/
        // Initialize motors to off
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Motors...");
        telemetry.update();

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        robot.motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        robot.motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        robot.motorCollectLower.setDirection(DcMotor.Direction.REVERSE);

        robot.motorFrontLeft.setPower(0);
        robot.motorFrontRight.setPower(0);
        robot.motorBackLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorLaunch.setPower(0);
        robot.motorLift.setPower(0);
        robot.motorCollectLower.setPower(0);
        robot.motorCollectUpper.setPower(0);

        // Set motors without Encoder
        robot.motorLaunch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.motorCollectLower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.motorCollectUpper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("InitializeHW", "> Initializing Motors Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Encoders
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Resetting Encoders...");
        telemetry.update();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData
                ("InitializeHW",  "> > Starting at %7d :%7d :%7d :%7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition()
                );
        telemetry.update();

        telemetry.addData("InitializeHW", "> Resetting Encoders Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Servos
        /* ******************************************************/
        telemetry.addData("InitializeHW","> Initializing Servo Positions...");
        telemetry.update();

        robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);
        robot.servoForkLeft.setPosition(configs.CLOSED_FORK_SERVO_POS);
        robot.servoForkRight.setPosition(configs.CLOSED_FORK_SERVO_POS);
        robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);

        telemetry.addData("InitializeHW", "> > Ball gate position: " + robot.servoBallGate.getPosition());
        telemetry.addData("InitializeHW", "> > Fork left position: " + robot.servoForkLeft.getPosition());
        telemetry.addData("InitializeHW", "> > Fork right position: " + robot.servoForkRight.getPosition());
        telemetry.addData("InitializeHW", "> > Button arm position: " + robot.servoButtonArm.getPosition());
        telemetry.addData("InitializeHW","> Initializing Servo Positions Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Gyro
        /* ******************************************************/

           /*
    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // Gyro integrated heading
    int angleZ = 0;
    boolean lastResetState = false;
    boolean curResetState  = false;

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;
    */

        telemetry.addData("InitializeHW", "Initialization HW Complete!");
    }

}