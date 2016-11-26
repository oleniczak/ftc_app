package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

class Hardware {

    HardwareMap hwMap = null;   //?

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
//    public Hardware()
//    {
//    }

    /* Initialize standard Hardware interfaces */
    public void IdentifyHardware(HardwareMap ahwMap)
    {
        hwMap = ahwMap;

        /* ******************************************************/
        // Define and Initialize Motors
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

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorCollectLower.setDirection(DcMotor.Direction.REVERSE);

        // Set motors with or without Encoder
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLaunch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorCollectLower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorCollectUpper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /* ******************************************************/
        // Define and Initialize Servos
        /* ******************************************************/
        servoBallGate = hwMap.servo.get("gate");
        servoForkLeft = hwMap.servo.get("fork");
        servoForkRight = hwMap.servo.get("fork1");
        servoButtonArm = hwMap.servo.get("button");

        /* ******************************************************/
        // Define and Initialize Sensors
        /* ******************************************************/
        sensorColor = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");
        sensorGyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
    }
}