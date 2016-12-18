package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Encoder Drive (Config Alliance & Position)", group="Autonomous")
//@Disabled
class AutonomousWithEncoders extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private RobotConfiguration configs = new RobotConfiguration(telemetry);
    private RobotCommands cmds = new RobotCommands(telemetry);

    @Override
    public void runOpMode()
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + RobotConfiguration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + RobotConfiguration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //***********************************************************************************
        //  Available commands:
        //      DriveForward(robot, DRIVE_POWER, drivetime)/
        //      DriveReverse(robot, DRIVE_POWER, drivetime);
        //      EncoderDrive(robot, DRIVE_POWER, <speed>, <leftinches>, <rightinches>, <timeS>);
        //      StopDriving(robot);
        //      TurnLeft(robot, TURN_POWER, turntime);
        //      TurnRight(robot, TURN_POWER, turntime);
        //      LaunchBallAndReset(robot, LAUNCH_POWER);
        //      DropNewBall(robot);
        //      ReadyBeaconArm (robot);
        //      DisarmBeaconArm (robot);
        //      SenseBeacon (robot);
        //      CalibrateGyro(robot);
        //***********************************************************************************

        cmds.LaunchBallAndReset(robot, RobotConfiguration.LAUNCH_POWER);

        cmds.DropNewBall(robot);

        cmds.LaunchBallAndReset(robot, RobotConfiguration.LAUNCH_POWER);

        //Wait 10 seconds in case we cross zones
        telemetry.addData("Status", "Delay before driving to avoid penalty ...");
        telemetry.update();

        sleep(RobotConfiguration.AUTO_DELAY_TIME);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Turn to face center
        if(RobotConfiguration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot,RobotConfiguration.TURN_POWER, RobotConfiguration.NINETY_DEGREE_TURN_INCHES, -RobotConfiguration.NINETY_DEGREE_TURN_INCHES, 3.0); //90 degree right turn
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot,RobotConfiguration.TURN_POWER, -RobotConfiguration.NINETY_DEGREE_TURN_INCHES, RobotConfiguration.NINETY_DEGREE_TURN_INCHES, 3.0);  //90 degree left turn
        }

        //Drive to center
        if (RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER,  RobotConfiguration.LONG_AUTO_DRIVE_INCHES,  RobotConfiguration.LONG_AUTO_DRIVE_INCHES, 3.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER,  RobotConfiguration.SHORT_AUTO_DRIVE_INCHES,  RobotConfiguration.SHORT_AUTO_DRIVE_INCHES, 3.0);
        }

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
