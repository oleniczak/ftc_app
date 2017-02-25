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

    //Motors
        DcMotor motorFrontLeft=null;
        DcMotor motorBackLeft=null;
        DcMotor motorFrontRight=null;
        DcMotor motorBackRight=null;

        DcMotor motorLaunch=null;

        DcMotor motorCollect=null;

        DcMotor motorLiftLeft=null;
        DcMotor motorLiftRight=null;

    //Servos
        Servo servoLift=null;

    //Sensors
        ModernRoboticsI2cGyro sensorGyro=null;

        ModernRoboticsI2cColorSensor sensorColorLeft=null;
        ModernRoboticsI2cColorSensor sensorColorRight=null;

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

        /*
        * Tie the hardware variables to the hardware defined in the FTC Robot Controller app on the phone.
        */

        // Define Motors
            motorFrontLeft = hwMap.dcMotor.get("front_left");
            motorBackLeft = hwMap.dcMotor.get("back_left");
            motorFrontRight = hwMap.dcMotor.get("front_right");
            motorBackRight = hwMap.dcMotor.get("back_right");

            motorLaunch = hwMap.dcMotor.get("launch");

            motorCollect = hwMap.dcMotor.get("collect");

            motorLiftLeft = hwMap.dcMotor.get("lift_left");
            motorLiftRight = hwMap.dcMotor.get("lift_right");

        // Define Servos
            servoLift = hwMap.servo.get("fork");

        // Define Sensors
            sensorGyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
            sensorColorLeft = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");
            sensorColorRight = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color");

        telemetry.addData("Hardware", "Hardware Definition Complete!");
        telemetry.update();
    }
}