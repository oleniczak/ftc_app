package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Basic (Config Alliance & Position)", group="Autonomous")
//@Disabled
class AutonomousWithTime extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private RobotConfiguration configs = new RobotConfiguration(telemetry);
    private RobotCommands cmds = new RobotCommands(telemetry);

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode()
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        //System.out.println("Configured for " + RobotConfiguration.ALLIANCE + " Alliance.");
        //System.out.println("Configured for " + RobotConfiguration.START_POSITION + " Starting Position.");
        telemetry.addData("Config", "Configured for " + RobotConfiguration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + RobotConfiguration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        runtime.reset();

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

        //Wait 10 seconds in case we cross zones
        telemetry.addData("Status", "Delay before driving to avoid penalty ...");
        telemetry.update();

        sleep(RobotConfiguration.AUTO_DELAY_TIME);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Turn to face center
        if(RobotConfiguration.ALLIANCE.equals("RED"))
        {
            cmds.TurnRight(robot,RobotConfiguration.TURN_POWER,RobotConfiguration.NINETY_DEGREE_TURN_TIME);
        }
        else
        {
            cmds.TurnLeft(robot,RobotConfiguration.TURN_POWER,RobotConfiguration.NINETY_DEGREE_TURN_TIME);
        }

        //Drive to center
        if (RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.DriveForward(robot, RobotConfiguration.DRIVE_POWER, RobotConfiguration.LONG_AUTO_DRIVE_TIME);
        }
        else
        {
            cmds.DriveForward(robot, RobotConfiguration.DRIVE_POWER, RobotConfiguration.SHORT_AUTO_DRIVE_TIME);
        }

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}