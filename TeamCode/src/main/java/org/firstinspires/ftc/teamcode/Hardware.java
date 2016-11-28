package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;


public class Hardware {

    HardwareMap hwMap = null;   //? private
    private ElapsedTime runtime  = new ElapsedTime();

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
    //public Hardware()
    //{
    //}

    /* Initialize standard Hardware interfaces */
    public void IdentifyHardware (HardwareMap ahwMap)
    {
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
    }

    public void waitForTick(long periodMs)
    {
        long  remaining = periodMs - (long)runtime.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
        {
            try
            {
                Thread.sleep(remaining);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        runtime.reset();
    }

}