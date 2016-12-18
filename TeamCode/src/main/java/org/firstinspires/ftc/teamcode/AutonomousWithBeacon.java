package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Beacon w/ Encoders (Config Alliance & Position)", group="Autonomous")
//@Disabled
class AutonomousWithBeacon extends LinearOpMode //RobotCommands
{
    private Hardware robot = new Hardware(telemetry);
    private RobotConfiguration configs = new RobotConfiguration(telemetry);
    private RobotCommands cmds = new RobotCommands(telemetry);

    @Override
    public void runOpMode()
    {
        telemetry.addData("BEGIN","Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + RobotConfiguration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + RobotConfiguration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //***********************************************************************************
        /* Available commands: (preface each w/ cmds.)*/
        //  DriveForward(robot, DRIVE_POWER, drivetime)/
        //  DriveReverse(robot, DRIVE_POWER, drivetime);
        //  EncoderDrive(robot, DRIVE_POWER, <speed>, <leftinches>, <rightinches>, <timeS>);
        //  StopDriving(robot);
        //  TurnLeft(robot, TURN_POWER, turntime);
        //  TurnRight(robot, TURN_POWER, turntime);
        //  LaunchBallAndReset(robot, LAUNCH_POWER);
        //  DropNewBall(robot);
        //  ReadyBeaconArm (robot);
        //  DisarmBeaconArm (robot);
        //  SenseBeacon (robot);
        //  CalibrateGyro(robot);
        //***********************************************************************************

        cmds.LaunchBallAndReset(robot, RobotConfiguration.LAUNCH_POWER);

        cmds.DropNewBall(robot);

        cmds.LaunchBallAndReset(robot, RobotConfiguration.LAUNCH_POWER);

        //Turn to face center
        if(RobotConfiguration.ALLIANCE.equals("RED"))
        {
            cmds.TurnRight(robot,RobotConfiguration.TURN_POWER,100);
        }
        else
        {
            cmds.TurnLeft(robot,RobotConfiguration.TURN_POWER,100);
        }

        //To drive forward with distance:
        cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 48, 48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        //Turn to Beacon
        if(RobotConfiguration.ALLIANCE.equals("RED"))
        {
            cmds.TurnLeft(robot,RobotConfiguration.TURN_POWER,80);
        }
        else
        {
            cmds.TurnRight(robot,RobotConfiguration.TURN_POWER,80);
        }

        cmds.ReadyBeaconArm(robot);

        //Drive all the way up and push the button
        cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 36, 36, 5.0);

        //Backup 3 inches to assess color
        cmds.EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER,3,3,1.0);

        //wait 5 seconds before determining whether to drive forward again (wrong color)
        sleep(5000);

        //Sensing beacon will also invoke a 3 inch drive if the proper color is detected
        cmds.SenseBeacon(robot);

        //Back onto center
        cmds.EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER,33,33,5.0);

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
