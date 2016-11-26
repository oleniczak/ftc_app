package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotCommands extends LinearOpMode
{
    private final Hardware robot   = new Hardware();
    private final RobotConfiguration configs = new RobotConfiguration();
    private final ElapsedTime runtime = new ElapsedTime();

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

    public void runOpMode()
    {
    }

    public void InitializeHW()
    {
        telemetry.addData("InitializeHW","Beginning HW Initialization...");
        telemetry.update();

        /* ******************************************************/
        // Initialize motors to off
        /* ******************************************************/
        robot.motorFrontLeft.setPower(0);
        robot.motorFrontRight.setPower(0);
        robot.motorBackLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorLaunch.setPower(0);
        robot.motorLift.setPower(0);
        robot.motorCollectLower.setPower(0);
        robot.motorCollectUpper.setPower(0);

        /* ******************************************************/
        // Initialize Servos
        /* ******************************************************/
        telemetry.addData("InitializeHW","Initializing Servo Positions...");
        telemetry.update();

        robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);
        robot.servoForkLeft.setPosition(configs.CLOSED_FORK_SERVO_POS);
        robot.servoForkRight.setPosition(configs.CLOSED_FORK_SERVO_POS);
        robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);

        telemetry.addData("InitializeHW", "> Ball gate position: " + robot.servoBallGate.getPosition());
        telemetry.addData("InitializeHW", "> Fork left position: " + robot.servoForkLeft.getPosition());
        telemetry.addData("InitializeHW", "> Fork right position: " + robot.servoForkRight.getPosition());
        telemetry.addData("InitializeHW", "> Button arm position: " + robot.servoButtonArm.getPosition());
        telemetry.addData("InitializeHW","Initializing Servo Positions Complete!");
        telemetry.update();

        /* ******************************************************/
        // Initialize Encoders
        /* ******************************************************/
        telemetry.addData("InitializeHW", "Resetting Encoders...");
        telemetry.update();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        telemetry.addData
                ("InitializeHW",  "> Starting at %7d :%7d :%7d :%7d",
                robot.motorFrontLeft.getCurrentPosition(),
                robot.motorFrontRight.getCurrentPosition(),
                robot.motorBackLeft.getCurrentPosition(),
                robot.motorBackRight.getCurrentPosition()
                );

        telemetry.addData("InitializeHW", "Resetting Encoders Complete!");
        telemetry.update();

        telemetry.addData("InitializeHW", "Initialization HW Complete!");
    }

    public void EncoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS)
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        telemetry.addData("EncoderDrive","EncoderDrive Starting...");
        telemetry.update();

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Calculate new target position
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int)((double) 48 * configs.COUNTS_PER_INCH);
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int)(rightInches * configs.COUNTS_PER_INCH);
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int)((double) 48 * configs.COUNTS_PER_INCH);
            newRightBackTarget = robot.motorBackRight.getCurrentPosition() + (int)(rightInches * configs.COUNTS_PER_INCH);

            // Pass target position to motor controller
            robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
            robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
            robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
            robot.motorBackRight.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.motorFrontLeft.setPower(Math.abs(speed));
            robot.motorFrontRight.setPower(Math.abs(speed));
            robot.motorBackLeft.setPower(Math.abs(speed));
            robot.motorBackRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    runtime.seconds() < timeoutS &&
                    robot.motorFrontLeft.isBusy() &&
                    robot.motorFrontRight.isBusy() &&
                    robot.motorBackLeft.isBusy() &&
                    robot.motorBackRight.isBusy()
                    )
            {
                // Display it for the driver.
                telemetry.addData("EncoderDrive","> Currently at %7d :%7d :%7d :&7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition());
                telemetry.addData("EncoderDrive","> Running to %7d :%7d :%7d :&7d",
                        newLeftFrontTarget,  newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.update();
            }

            // Stop all motion;
            StopDriving();

            // Turn off RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
        telemetry.addData("EncoderDrive","EncoderDrive Complete!");
        telemetry.update();
    }

    public void CalibrateGyro()
    {
        // start calibrating the gyro.
        telemetry.addData("CalibrateGyro", "Calibrating Gyro. Do Not move!");
        telemetry.update();

        robot.sensorGyro.calibrate();

        // make sure the gyro is calibrated.
        while (!isStopRequested() && robot.sensorGyro.isCalibrating())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("CalibrateGyro", "Gyro Calibration Complete!");
        telemetry.update();
    }

    public void DriveForward(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.
        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(power);
    }

    public void DriveReverse(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.
        DriveForward(-power);
    }


    public void StopDriving()
    {
        telemetry.addData("StopDriving", "Halting ...");
        telemetry.update();

        DriveForward(0);

        telemetry.addData("StopDriving", "Halt Complete!");
        telemetry.update();
    }

    private void TurnLeft(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.

        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(-power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(-power);

        // No telemetry message since drive will not complete until sleep expires.
    }

    public void TurnRight(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.

        TurnLeft(-power);

        // No telemetry message since drive will not complete until sleep expires.
    }

    public void DropNewBall() //throws InterruptedException
    {
        telemetry.addData("DropNewBall", "Beginning Ball Drop ...");
        telemetry.update();

        robot.servoBallGate.setPosition(configs.OPEN_BALL_GATE_POS);
        sleep(configs.BALL_GATE_OPEN_TIME);
        robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);

        telemetry.addData("DropNewBall", "Ball Drop Complete!");
        telemetry.update();

    }

    public void LaunchBallAndReset(double power) //throws InterruptedException
    {
        telemetry.addData("LaunchBallAndReset", "Beginning Ball Launch ...");
        telemetry.update();

        robot.motorLaunch.setPower(power);
        sleep(configs.LAUNCH_TIME);
        robot.motorLaunch.setPower(0);

        telemetry.addData("LaunchBallAndReset", "Ball Launch Complete!");
        telemetry.update();
    }

    public void ReportBeaconColor()
    {
        telemetry.addData("ReportBeaconColor", "Beginning Beacon Sensing ...");
        telemetry.update();

        robot.sensorColor.enableLed(true);

        telemetry.addData("ReportBeaconColor","> Red: " + robot.sensorColor.red());
        telemetry.addData("ReportBeaconColor","> Green: " + robot.sensorColor.green());
        telemetry.addData("ReportBeaconColor","> Blue: " + robot.sensorColor.blue());
        telemetry.addData("ReportBeaconColor","> Clear: " + robot.sensorColor.alpha());
        telemetry.addData("ReportBeaconColor","> Clear: " + robot.sensorColor.argb());
        telemetry.update();

        robot.sensorColor.enableLed(false);

        telemetry.addData("ReportBeaconColor", "Beacon Sensing Complete!");
        telemetry.update();
    }

    public void FindBeacon()
    {
        /* ***** CODE HERE IS NOT APPLICABLE TO OUR ROBOT - COPIED IN AS AN EXAMPLE ONLY ***** */
        robot.servoButtonArm.setPosition(configs.DETECTION_BUTTON_POS);
        robot.sensorColor.enableLed(true);

        robot.sensorColor.red();
        robot.sensorColor.green();
        robot.sensorColor.blue();
        robot.sensorColor.alpha();
        robot.sensorColor.argb();
//
//        //Color.RGBToHSV(robot.sensorColor.red() * 8, robot.sensorColor.green() * 8, robot.sensorColor.blue() * 8, hsvValues);
//
        while (robot.sensorColor.alpha() < 20)
        {
//            //if (robot.sensorColor.red() > robot.sensorColor.blue() && robot.sensorColor.red() > robot.sensorColor.green())
            if (robot.sensorColor.red() >= 8)
            {
                // Red servo is activated
                // Take Red action
                telemetry.addData("Red Found",robot.sensorColor.red());
                // robot.claw.setPosition(0.1);
//                //robot.arm.setPosition(0.1);
            }
//            //else if (robot.sensorColor.blue() > robot.sensorColor.red() && robot.sensorColor.blue() > robot.sensorColor.green())
            else if (robot.sensorColor.blue()<=3)
            {
                // Blue servo is activated
                //Take Blue action
                telemetry.addData("Blue Found",robot.sensorColor.blue());
//                //robot.claw.setPosition(0.9);
//                //robot.arm.setPosition(0.9);
            }
            else
            {
                //Red or blue not detected - move forward
                DriveForward(configs.DRIVE_POWER);
                sleep(1000);

//                robot.servoButtonArm.setPosition(START_BUTTON_POS);
            }

            idle();
        }
        robot.sensorColor.enableLed(false);
        robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);
    }
}
