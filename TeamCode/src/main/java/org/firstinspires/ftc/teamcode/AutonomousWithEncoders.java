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
            //cmds.TurnRight(robot,RobotConfiguration.TURN_POWER,100);
            cmds.EncoderDrive(robot,RobotConfiguration.TURN_POWER, 9, -9, 1.0); //90 degree right turn
        }
        else
        {
            //cmds.TurnLeft(robot,RobotConfiguration.TURN_POWER,100);
            cmds.EncoderDrive(robot,RobotConfiguration.TURN_POWER,-9, 9, 1.0);  //90 degree left turn
        }

        //Drive to center
        if (RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER,  48,  48, 3.0);  //Forward 48 Inches with 3 Sec timeout
        }
        else
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER,  36,  36, 3.0);  //Forward 36 Inches with 3 Sec timeout
        }

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
