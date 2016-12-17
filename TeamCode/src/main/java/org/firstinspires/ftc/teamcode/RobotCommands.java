package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Define all movement commands
*/

public class RobotCommands extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    //private Telemetry telemetry;
    //private Hardware robot = new Hardware(telemetry);
    //RobotConfiguration configs = new RobotConfiguration(telemetry);

    /* Constructor */
    public RobotCommands(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    public void runOpMode()
    {
    }

    public void LaunchBallAndReset(Hardware robot, double power) //throws InterruptedException
    {
        telemetry.addData("LaunchBallAndReset", "Beginning Ball Launch ...");
        telemetry.update();

        robot.motorLaunch.setPower(power);

        //run motor to launch ball
        //  sleep will not work in OpMode
        //  sleep(configs.LAUNCH_TIME);
        runtime.reset();
        while (runtime.milliseconds() < RobotConfiguration.LAUNCH_TIME)
        {
            telemetry.addData("Status", "Wait for one turn of launch motor:  %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.motorLaunch.setPower(0);

        telemetry.addData("LaunchBallAndReset", "Ball Launch Complete!");
        telemetry.update();
    }

    public void DropNewBall(Hardware robot) //throws InterruptedException
    {
        telemetry.addData("DropNewBall", "Beginning Ball Drop ...");
        telemetry.update();

        robot.servoBallGate.setPosition(RobotConfiguration.OPEN_BALL_GATE_POS);

        //wait for ball to drop
        //  sleep will not work in OpMode
        //  sleep(configs.BALL_GATE_OPEN_TIME);
        runtime.reset();
        while (runtime.milliseconds() < RobotConfiguration.BALL_GATE_OPEN_TIME) {
            telemetry.addData("Status", " Wait for ball drop:  %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //close ball gate
        robot.servoBallGate.setPosition(RobotConfiguration.CLOSED_BALL_GATE_POS);

        telemetry.addData("DropNewBall", "Ball Drop Complete!");
        telemetry.update();
    }

    public void DriveForward(Hardware robot, double power, int drivetime)
    {
        telemetry.addData("Drive", "Begin Drive ...");
        telemetry.update();

        // No telemetry message since drive will not complete until sleep expires.
        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(power);

        runtime.reset();
        while (runtime.milliseconds() < drivetime)
        {
            telemetry.addData("Status", "Drive time: " + runtime.toString());
            telemetry.update();
        }

        telemetry.addData("Drive", "Drive Complete!");
        telemetry.update();
    }

    public void DriveReverse(Hardware robot, double power, int drivetime)
    {
        // No telemetry message since drive will not complete until sleep expires.
        DriveForward(robot, -power, drivetime);
    }

    public void StopDriving(Hardware robot)
    {
        telemetry.addData("Stop Drive", "Halting ...");
        telemetry.update();

        DriveForward(robot, 0, 0);

        telemetry.addData("Stop Drive", "Halt Complete!");
        telemetry.update();
    }

    public void InitializeHW(Hardware robot)
    {
        //robot.DefineHardware(hardwareMap);

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
        // Initialize Gyro
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Gyro ...");
        telemetry.update();

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

        telemetry.addData("InitializeHW", "> Initializing Gyro Complete!");
        telemetry.update();

         /* ******************************************************/
        // Initialize Servos
        /* ******************************************************/
        telemetry.addData("Initialize Servos", "> Initializing Servo Positions...");
        telemetry.update();

        robot.servoBallGate.setPosition(RobotConfiguration.OPEN_BALL_GATE_POS);
        robot.servoForkLeft.setPosition(RobotConfiguration.CLOSED_FORK_SERVO_POS);
        robot.servoForkRight.setPosition(RobotConfiguration.CLOSED_FORK_SERVO_POS);
        robot.servoButtonArm.setPosition(RobotConfiguration.START_BUTTON_POS);

        telemetry.addData("InitializeHW", "> > Ball gate position: " + robot.servoBallGate.getPosition());
        telemetry.addData("InitializeHW", "> > Fork left position: " + robot.servoForkLeft.getPosition());
        telemetry.addData("InitializeHW", "> > Fork right position: " + robot.servoForkRight.getPosition());
        telemetry.addData("InitializeHW", "> > Button arm position: " + robot.servoButtonArm.getPosition());

        telemetry.addData("InitializeHW", "> Initializing Servo Positions Complete!");
        telemetry.update();

        telemetry.addData("InitializeHW", "Initialization HW Complete!");
        telemetry.update();
    }

    public void TurnLeft(Hardware robot, double power, int turntime)
    {
        telemetry.addData("Turn", "Begin Turn ...");
        telemetry.update();

        // No telemetry message since drive will not complete until sleep expires.
        robot.motorFrontRight.setPower(power);
        robot.motorFrontLeft.setPower(-power);
        robot.motorBackRight.setPower(power);
        robot.motorBackLeft.setPower(-power);

        runtime.reset();
        while (runtime.milliseconds() < turntime)
        {
            telemetry.addData("Status", "Turn time: " + runtime.toString());
            telemetry.update();
        }

        telemetry.addData("Turn", "Turn Complete!");
        telemetry.update();
    }

    public void TurnRight(Hardware robot, double power, int turntime)
    {
        TurnLeft(robot, -power, turntime);
    }

    public void ReadyBeaconArm(Hardware robot)
    {
        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm ...");
        telemetry.update();

        robot.servoButtonArm.setPosition(RobotConfiguration.DETECTION_BUTTON_POS);
        robot.sensorColor.enableLed(true);

        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm Complete!");
        telemetry.update();
    }

    public void DisarmBeaconArm(Hardware robot)
    {
        telemetry.addData("DisArmBeacon", "Disarming Beacon Arm ...");
        telemetry.update();

        robot.servoButtonArm.setPosition(RobotConfiguration.START_BUTTON_POS);
        robot.sensorColor.enableLed(false);

        telemetry.addData("DisArmBeacon", "Disarming Beacon Arm Complete!");
        telemetry.update();
    }

    //*****************************************

    public void SenseBeacon(Hardware robot) {
        telemetry.addData("SenseBeacon", "Beginning Beacon Sensing ...");
        telemetry.update();

        robot.sensorColor.red();
        robot.sensorColor.green();
        robot.sensorColor.blue();
        robot.sensorColor.alpha();
        robot.sensorColor.argb();

        telemetry.addData("SenseBeacon", "> Red: " + robot.sensorColor.red());
        telemetry.addData("SenseBeacon", "> Green: " + robot.sensorColor.green());
        telemetry.addData("SenseBeacon", "> Blue: " + robot.sensorColor.blue());
        telemetry.addData("SenseBeacon", "> Clear: " + robot.sensorColor.alpha());
        telemetry.addData("SenseBeacon", "> Clear: " + robot.sensorColor.argb());
        telemetry.update();

        robot.sensorColor.enableLed(false);

        while (robot.sensorColor.alpha() < 20) {
            if ((robot.sensorColor.red() >= 8) && (RobotConfiguration.ALLIANCE.equals("RED")))
            {
                // Red beacon found
                // Take Red action
                telemetry.addData("SenseBeacon", "> > Red Found :" + robot.sensorColor.red());
                telemetry.update();
                // Drive forward 3 inches with the sensor arm extended
                EncoderDrive(robot, RobotConfiguration.APPROACH_SPEED, 3, 3, 1.0);
                //Back off button
                EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER,3,3,1.0);
            }
            else if ((robot.sensorColor.blue() <= 3) && (RobotConfiguration.ALLIANCE.equals("BLUE")))
            {
                // Blue beacon found
                //Take Blue action
                telemetry.addData("SenseBeacon", "> > Blue Found: " + robot.sensorColor.blue());
                telemetry.update();
                // Drive forward 3 inches with the sensor arm extended
                EncoderDrive(robot, RobotConfiguration.APPROACH_SPEED, 3, 3, 1.0);
                //Back off button
                EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER,3,3,1.0);
            }
            idle();
        }
        robot.sensorColor.enableLed(false);

        robot.servoButtonArm.setPosition(RobotConfiguration.START_BUTTON_POS);

        telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        telemetry.update();
    }

    public void EncoderDrive(Hardware robot,
                             double speed,
                             double leftInches, double rightInches,
                             double timeoutS)
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        telemetry.addData("EncoderDrive", "EncoderDrive Starting...");
        telemetry.update();

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {
            // Calculate new target position
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * RobotConfiguration.COUNTS_PER_INCH);
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * RobotConfiguration.COUNTS_PER_INCH);
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * RobotConfiguration.COUNTS_PER_INCH);
            newRightBackTarget = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * RobotConfiguration.COUNTS_PER_INCH);

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
                    ) {
                // Display it for the driver.
                telemetry.addData("EncoderDrive", "> Currently at %7d :%7d :%7d :&7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition());
                telemetry.addData("EncoderDrive", "> Destination of %7d :%7d :%7d :&7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.update();
            }

            // Stop all motion;
            StopDriving(robot);

            // Turn off RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
        telemetry.addData("EncoderDrive", "EncoderDrive Complete!");
        telemetry.update();
    }

    public void CalibrateGyro(Hardware robot)
    {
        telemetry.addData("CalibrateGyro", "Calibrating Gyro. Do Not move!");
        telemetry.update();

        robot.sensorGyro.calibrate();

        // make sure the gyro is calibrated.
        while (!isStopRequested() && robot.sensorGyro.isCalibrating()) {
            sleep(50);
            idle();
        }

        telemetry.addData("CalibrateGyro", "Gyro Calibration Complete!");
        telemetry.update();
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