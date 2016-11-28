package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotCommands extends LinearOpMode
{
    Hardware robot   = new Hardware();
    RobotConfiguration configs = new RobotConfiguration();
    private ElapsedTime runtime = new ElapsedTime();

    //private Telemetry telemetry;

    public void runOpMode()
    {
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

    public void TurnLeft(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.
        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(-power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(-power);
    }

    public void TurnRight(double power)
    {
        // No telemetry message since drive will not complete until sleep expires.
        TurnLeft(-power);
    }

    public void DropNewBall() //throws InterruptedException
    {
        telemetry.addData("DropNewBall", "Beginning Ball Drop ...");
        telemetry.update();

        robot.servoBallGate.setPosition(configs.OPEN_BALL_GATE_POS);

        //wait for ball to drop
        //  sleep will not work in OpMode
        //  sleep(configs.BALL_GATE_OPEN_TIME);
        runtime.reset();
        while (runtime.seconds() < configs.BALL_GATE_OPEN_TIME)
        {
            telemetry.addData("DropNewBall","> Wait for ball drop:  %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //close ball gate
        robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);

        telemetry.addData("DropNewBall", "Ball Drop Complete!");
        telemetry.update();
    }

    public void LaunchBallAndReset(double power) //throws InterruptedException
    {
        telemetry.addData("LaunchBallAndReset", "Beginning Ball Launch ...");
        telemetry.update();

        robot.motorLaunch.setPower(power);

        //run motor to launch ball
        //  sleep will not work in OpMode
        //  sleep(configs.LAUNCH_TIME);
        runtime.reset();
        while (runtime.seconds() < configs.LAUNCH_TIME)
        {
            telemetry.addData("LaunchBallAndReset","> Wait for one turn of launch motor:  %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.motorLaunch.setPower(0);

        telemetry.addData("LaunchBallAndReset", "Ball Launch Complete!");
        telemetry.update();
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
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int)(leftInches * configs.COUNTS_PER_INCH);
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int)(rightInches * configs.COUNTS_PER_INCH);
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int)(leftInches * configs.COUNTS_PER_INCH);
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
                telemetry.addData("EncoderDrive","> Destination of %7d :%7d :%7d :&7d",
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


    public void SenseBeacon(String Alliance)
    {
        telemetry.addData("SenseBeacon", "Beginning Beacon Sensing ...");
        telemetry.update();

        robot.sensorColor.red();
        robot.sensorColor.green();
        robot.sensorColor.blue();
        robot.sensorColor.alpha();
        robot.sensorColor.argb();

        telemetry.addData("SenseBeacon","> Red: " + robot.sensorColor.red());
        telemetry.addData("SenseBeacon","> Green: " + robot.sensorColor.green());
        telemetry.addData("SenseBeacon","> Blue: " + robot.sensorColor.blue());
        telemetry.addData("SenseBeacon","> Clear: " + robot.sensorColor.alpha());
        telemetry.addData("SenseBeacon","> Clear: " + robot.sensorColor.argb());
        telemetry.update();

        robot.sensorColor.enableLed(false);

        while (robot.sensorColor.alpha() < 20)
        {
            if ((robot.sensorColor.red() >= 8) && (Alliance.equals("Red")))
            {
                // Red beacon found
                // Take Red action
                telemetry.addData("SenseBeacon","> > Red Found :" + robot.sensorColor.red());
                telemetry.update();
                // Drive forward 3 inches with the sensor arm extended
                EncoderDrive(configs.APPROACH_SPEED,3,3,1.0);
            }
            else if ((robot.sensorColor.blue()<=3) && (Alliance.equals("Blue")))
            {
                // Blue beacon found
                //Take Blue action
                telemetry.addData("SenseBeacon","> > Blue Found: " +robot.sensorColor.blue());
                telemetry.update();
                // Dive forward 3 inches with the sensor arm extended
                EncoderDrive(configs.APPROACH_SPEED,3,3,1.0);
            }
//            else
//            {
//                //Red or blue not detected - move forward
//                DriveForward(configs.DRIVE_POWER);
//                sleep(250);
//            }
            idle();
        }
        robot.sensorColor.enableLed(false);

        robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);

        telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        telemetry.update();
    }

    public void ReadyBeaconArm()
    {
        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm ...");
        telemetry.update();

        robot.servoButtonArm.setPosition(configs.DETECTION_BUTTON_POS);
        robot.sensorColor.enableLed(true);

        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm Complete!");
        telemetry.update();
    }

    public void DiarmBeaconArm()
    {
        telemetry.addData("DiarmBeaconArm", "Disarming Beacon Arm ...");
        telemetry.update();

        robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);
        robot.sensorColor.enableLed(true);

        telemetry.addData("DiarmBeaconArm", "Disarming Beacon Arm Complete!");
        telemetry.update();
    }
}
