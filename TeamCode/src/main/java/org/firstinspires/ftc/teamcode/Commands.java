package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
PURPOSE:
    Define all movement commands
*/

public class Commands extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    //private Telemetry telemetry;
    //private Hardware robot = new Hardware(telemetry);
    //Configuration configs = new Configuration(telemetry);

    /* Constructor */
    public Commands(Telemetry telemetry)
    {
        this.telemetry = telemetry;
    }

    public void runOpMode()
    {
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

        robot.motorFrontLeft.setPower(0);
        robot.motorFrontRight.setPower(0);
        robot.motorBackLeft.setPower(0);
        robot.motorBackRight.setPower(0);

//        robot.motorLaunch.setPower(0);
//        robot.motorLift.setPower(0);
//        robot.motorCollect.setPower(0);

        // Set motors without Encoder
//        robot.motorLaunch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.motorCollect.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //since not reset, position doesn't mean anything - telemetry only ensures they are being read
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
        // Initialize Sensors
        /* ******************************************************/
        telemetry.addData("InitializeHW", "> Initializing Sensors ...");
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

        robot.sensorGyro.calibrate();

        // make sure the gyro is calibrated.
        while (!isStopRequested() && robot.sensorGyro.isCalibrating())
        {
            sleep(50);
            idle();
        }

//        robot.sensorColor.enableLed(false);

        telemetry.addData("InitializeHW", "> Initializing Gyro Complete!");
        telemetry.update();

         /* ******************************************************/
        // Initialize Servos
        /* ******************************************************/
        telemetry.addData("Initialize Servos", "> Initializing Servo Positions...");
        telemetry.update();

//        robot.servoBallGate.setPosition(Configuration.OPEN_BALL_GATE_POS);
//        robot.servoForkLeft.setPosition(Configuration.CLOSED_FORK_SERVO_POS);
//        robot.servoForkRight.setPosition(Configuration.CLOSED_FORK_SERVO_POS);
//        robot.servoButtonArm.setPosition(Configuration.START_BUTTON_POS);

//        telemetry.addData("InitializeHW", "> > Ball gate position: " + robot.servoBallGate.getPosition());
//        telemetry.addData("InitializeHW", "> > Fork left position: " + robot.servoForkLeft.getPosition());
//        telemetry.addData("InitializeHW", "> > Fork right position: " + robot.servoForkRight.getPosition());
//        telemetry.addData("InitializeHW", "> > Button arm position: " + robot.servoButtonArm.getPosition());

        telemetry.addData("InitializeHW", "> Initializing Servo Positions Complete!");
        telemetry.update();

        telemetry.addData("InitializeHW", "Initialization HW Complete!");
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

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {
            // Calculate new target position
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * Configuration.COUNTS_PER_INCH);
            newRightBackTarget = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * Configuration.COUNTS_PER_INCH);

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
            speed = Math.abs(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);

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
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
        telemetry.addData("EncoderDrive", "EncoderDrive Complete!");
        telemetry.update();
    }

    public void GyroDrive (Hardware robot,
                           double speed,
                           double distance,
                           double angle,
                           double timeoutS)
    {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        telemetry.addData("GyroDrive", "GyroDrive Starting...");
        telemetry.update();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {
            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * Configuration.COUNTS_PER_INCH);

            // Calculate new target position
            newLeftFrontTarget = robot.motorFrontLeft.getCurrentPosition() + moveCounts;
            newRightFrontTarget = robot.motorFrontRight.getCurrentPosition() + moveCounts;
            newLeftBackTarget = robot.motorBackLeft.getCurrentPosition() + moveCounts;
            newRightBackTarget = robot.motorBackRight.getCurrentPosition() + moveCounts;

            // Set Target and Turn On RUN_TO_POSITION
            robot.motorFrontLeft.setTargetPosition(newLeftFrontTarget);
            robot.motorFrontRight.setTargetPosition(newRightFrontTarget);
            robot.motorBackLeft.setTargetPosition(newLeftBackTarget);
            robot.motorBackRight.setTargetPosition(newRightBackTarget);

            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            runtime.reset();
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    runtime.seconds() < timeoutS &&
                    robot.motorFrontLeft.isBusy() &&
                    robot.motorFrontRight.isBusy() &&
                    robot.motorBackLeft.isBusy() &&
                    robot.motorBackRight.isBusy()
                    )
            {
                // adjust relative speed based on heading error.
                error = getError(robot, angle);
                steer = getSteer(error, Configuration.P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if any one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.motorFrontLeft.setPower(leftSpeed);
                robot.motorFrontRight.setPower(rightSpeed);
                robot.motorBackLeft.setPower(leftSpeed);
                robot.motorBackRight.setPower(rightSpeed);

                // Display it for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("GyroDrive", "> Currently at %7d :%7d :%7d :&7d",
                        robot.motorFrontLeft.getCurrentPosition(),
                        robot.motorFrontRight.getCurrentPosition(),
                        robot.motorBackLeft.getCurrentPosition(),
                        robot.motorBackRight.getCurrentPosition());
                telemetry.addData("GyroDrive", "> Destination of %7d :%7d :%7d :&7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            StopDriving(robot);

            // Turn off RUN_TO_POSITION
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER );
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        telemetry.addData("GyroDrive", "GyroDrive Complete!");
        telemetry.update();
    }

    public void GyroTurn(Hardware robot, double speed, double angle)
    {
        robot.sensorGyro.resetZAxisIntegrator();

        while (opModeIsActive() && !onHeading(robot, speed,angle, Configuration.P_TURN_COEFF))
        {
            telemetry.update();
        }
    }

    boolean onHeading(Hardware robot, double speed, double angle, double PCoeff)
    {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(robot, angle);

        if (Math.abs(error) <= Configuration.HEADING_THRESHOLD)
        {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else
        {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        robot.motorFrontLeft.setPower(leftSpeed);
        robot.motorFrontRight.setPower(rightSpeed);
        robot.motorBackLeft.setPower(leftSpeed);
        robot.motorBackRight.setPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    public double getError(Hardware robot, double targetAngle)
    {
        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.sensorGyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public double getSteer(double error, double PCoeff)
    {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void GyroHold(Hardware robot, double speed, double angle, double holdTime)
    {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime))
        {
            // Update telemetry & Allow time for other processes to run.
            onHeading(robot, speed, angle, Configuration.P_TURN_COEFF);
//            telemetry.update();
        }

        StopDriving(robot);
    }

    public void StopDriving(Hardware robot)
    {
        telemetry.addData("Stop Drive", "Halting ...");
        telemetry.update();

        robot.motorFrontRight.setPower(0);
        robot.motorFrontLeft.setPower(0);
        robot.motorBackRight.setPower(0);
        robot.motorBackLeft.setPower(0);

        telemetry.addData("Stop Drive", "Halt Complete!");
        telemetry.update();
    }

    public void DropAndShoot(Hardware robot) //throws InterruptedException
    {
        telemetry.addData("DropNewBall", "Beginning Ball Drop ...");
        telemetry.update();

//        robot.servoBallGate.setPosition(Configuration.OPEN_BALL_GATE_POS);

        //wait for ball to drop
        //  sleep will not work in OpMode
        //  sleep(configs.BALL_GATE_OPEN_TIME);
        runtime.reset();
        while (runtime.milliseconds() < Configuration.BALL_GATE_OPEN_TIME) {
            telemetry.addData("Status", " Wait for ball drop:  %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //close ball gate
//        robot.servoBallGate.setPosition(Configuration.CLOSED_BALL_GATE_POS);

        telemetry.addData("DropNewBall", "Ball Drop Complete!");
        telemetry.update();
    }

    public void SenseBeacon(Hardware robot)
    {
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

        while (robot.sensorColor.alpha() < 20)
        {
            if ((robot.sensorColor.red() >= 8) && (Configuration.ALLIANCE.equals("BLUE")))
            {
                // Red beacon found, need to press button
                telemetry.addData("SenseBeacon", "> > Red Found :" + robot.sensorColor.red());
                telemetry.update();
                // Drive forward 3 inches with the sensor arm extended
                EncoderDrive(robot, Configuration.APPROACH_SPEED, 3, 3, 1.0);
                //Back off button
                EncoderDrive(robot,-Configuration.APPROACH_SPEED,3,3,1.0);
            }
            else if ((robot.sensorColor.blue() <= 3) && (Configuration.ALLIANCE.equals("RED")))
            {
                // Blue beacon found, need to press button
                telemetry.addData("SenseBeacon", "> > Blue Found: " + robot.sensorColor.blue());
                telemetry.update();
                // Drive forward 3 inches with the sensor arm extended
                EncoderDrive(robot, Configuration.APPROACH_SPEED, 3, 3, 1.0);
                //Back off button
                EncoderDrive(robot,-Configuration.APPROACH_SPEED,3,3,1.0);
            }
            //wait 5 seconds before determining whether to drive forward again (wrong color)
            sleep(5000);

            idle();
        }

        //robot.servoButtonArm.setPosition(Configuration.START_BUTTON_POS);

        telemetry.addData("SenseBeacon", "Beacon Sensing Complete!");
        telemetry.update();
    }

//    public void waitForTick(long periodMs)
//    {
//        long  remaining = periodMs - (long)runtime.milliseconds();
//
//        // sleep for the remaining portion of the regular cycle period.
//        if (remaining > 0)
//        {
//            try
//            {
//                Thread.sleep(remaining);
//            }
//            catch (InterruptedException e)
//            {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        // Reset the cycle clock for the next pass.
//        runtime.reset();
//    }

    //    public void CalibrateGyro(Hardware robot)
//    {
//        telemetry.addData("CalibrateGyro", "Calibrating Gyro. Do Not move!");
//        telemetry.update();
//
//        robot.sensorGyro.calibrate();
//
//        // make sure the gyro is calibrated.
//        while (!isStopRequested() && robot.sensorGyro.isCalibrating())
//        {
//            sleep(50);
//            idle();
//        }
//
//        telemetry.addData("CalibrateGyro", "Gyro Calibration Complete!");
//        telemetry.update();
//    }


//    public void DriveForward(Hardware robot, double power, int drivetime)
//    {
//        telemetry.addData("Drive", "Begin Drive ...");
//        telemetry.update();
//
//        // No telemetry message since drive will not complete until sleep expires.
//        robot.motorFrontRight.setPower(power);
//        robot.motorFrontLeft.setPower(power);
//        robot.motorBackRight.setPower(power);
//        robot.motorBackLeft.setPower(power);
//
//        runtime.reset();
//        while (runtime.milliseconds() < drivetime)
//        {
//            telemetry.addData("Status", "Drive time: " + runtime.toString());
//            telemetry.update();
//        }
//
//        telemetry.addData("Drive", "Drive Complete!");
//        telemetry.update();
//    }

//    public void DriveReverse(Hardware robot, double power, int drivetime)
//    {
//        // No telemetry message since drive will not complete until sleep expires.
//        DriveForward(robot, -power, drivetime);
//    }



//    public void TurnLeft(Hardware robot, double power, int turntime)
//    {
//        telemetry.addData("Turn", "Begin Turn ...");
//        telemetry.update();
//
//        // No telemetry message since drive will not complete until sleep expires.
//        robot.motorFrontRight.setPower(power);
//        robot.motorFrontLeft.setPower(-power);
//        robot.motorBackRight.setPower(power);
//        robot.motorBackLeft.setPower(-power);
//
//        runtime.reset();
//        while (runtime.milliseconds() < turntime)
//        {
//            telemetry.addData("Status", "Turn time: " + runtime.toString());
//            telemetry.update();
//        }
//
//        telemetry.addData("Turn", "Turn Complete!");
//        telemetry.update();
//    }

//    public void TurnRight(Hardware robot, double power, int turntime)
//    {
//        TurnLeft(robot, -power, turntime);
//    }


//    public void ReadyBeaconArm(Hardware robot)
//    {
//        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm ...");
//        telemetry.update();
//
//        robot.servoButtonArm.setPosition(Configuration.DETECTION_BUTTON_POS);
//        robot.sensorColor.enableLed(true);
//
//        telemetry.addData("ReadyBeaconArm", "Readying Beacon Arm Complete!");
//        telemetry.update();
//    }

//    public void DisarmBeaconArm(Hardware robot)
//    {
//        telemetry.addData("DisArmBeacon", "Disarming Beacon Arm ...");
//        telemetry.update();
//
//        robot.servoButtonArm.setPosition(Configuration.START_BUTTON_POS);
//        robot.sensorColor.enableLed(false);
//
//        telemetry.addData("DisArmBeacon", "Disarming Beacon Arm Complete!");
//        telemetry.update();
//    }

//    public void LaunchBallAndReset(Hardware robot, double power) //throws InterruptedException
//    {
//        telemetry.addData("LaunchBallAndReset", "Beginning Ball Launch ...");
//        telemetry.update();
//
//        robot.motorLaunch.setPower(power);
//
//        //run motor to launch ball
//        //  sleep will not work in OpMode
//        //  sleep(configs.LAUNCH_TIME);
//        runtime.reset();
//        while (runtime.milliseconds() < Configuration.LAUNCH_TIME)
//        {
//            telemetry.addData("Status", "Wait for one turn of launch motor:  %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//
//        robot.motorLaunch.setPower(0);
//
//        telemetry.addData("LaunchBallAndReset", "Ball Launch Complete!");
//        telemetry.update();
//    }

}