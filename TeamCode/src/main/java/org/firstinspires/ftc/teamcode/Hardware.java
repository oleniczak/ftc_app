package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Identifies hardware components
*/

public class Hardware
{
    //private ElapsedTime runtime  = new ElapsedTime();
    private Telemetry telemetry;
    private HardwareMap hwMap = null;

    DcMotor motorFrontLeft=null;
    DcMotor motorBackLeft=null;
    DcMotor motorFrontRight=null;
    DcMotor motorBackRight=null;

    DcMotor motorLaunch=null;
    DcMotor motorLift=null;
    DcMotor motorCollectLower=null;
    DcMotor motorCollectUpper=null;

    Servo servoBallGate=null;
    Servo servoForkLeft=null;
    Servo servoForkRight=null;
    Servo servoButtonArm=null;

    ModernRoboticsI2cGyro sensorGyro=null;
    ModernRoboticsI2cColorSensor sensorColor=null;

    /* Constructor */
    public Hardware(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        telemetry.addData("Hardware", "Begin Hardware Definition...");
        telemetry.update();

        hwMap = ahwMap;

        /* ******************************************************/
        // Define Motors
        /* ******************************************************/
         /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        motorFrontLeft = hwMap.dcMotor.get("front_left");
        motorFrontRight = hwMap.dcMotor.get("front_right");
        motorBackLeft = hwMap.dcMotor.get("back_left");
        motorBackRight = hwMap.dcMotor.get("back_right");

        motorLaunch = hwMap.dcMotor.get("launch");
        motorLift = hwMap.dcMotor.get("lift");
        motorCollectLower = hwMap.dcMotor.get("collect");
        motorCollectUpper = hwMap.dcMotor.get("collect1");

        /* ******************************************************/
        // Define Servos
        /* ******************************************************/
        servoBallGate = hwMap.servo.get("gate");
        servoForkLeft = hwMap.servo.get("fork");
        servoForkRight = hwMap.servo.get("fork1");
        servoButtonArm = hwMap.servo.get("button");

        /* ******************************************************/
        // Define Sensors
        /* ******************************************************/
        sensorColor = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");
        sensorGyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
/*
        //Set Starting values

        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //idle();

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        servoBallGate.setPosition(RobotConfiguration.CLOSED_BALL_GATE_POS);
        servoForkLeft.setPosition(RobotConfiguration.CLOSED_FORK_SERVO_POS);
        servoForkRight.setPosition(RobotConfiguration.CLOSED_FORK_SERVO_POS);
        servoButtonArm.setPosition(RobotConfiguration.START_BUTTON_POS);
*/
        telemetry.addData("Hardware", "Hardware Definition Complete!");
        telemetry.update();
    }
}