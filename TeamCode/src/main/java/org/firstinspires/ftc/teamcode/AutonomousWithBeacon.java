package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Beacon w/ Encoders (Config Alliance & Position)", group="Autonomous")
@Disabled
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

        //Turn to face center
        if(RobotConfiguration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 9, -9, 1.0);  //90 degree right turn
        }
        else
        {
            cmds.EncoderDrive(robot,RobotConfiguration.TURN_POWER, -9, 9, 1.0);  //90 degree left turn
        }

        //To drive forward with distance:
        if (RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 48, 48, 3.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 24, 24, 3.0);
        }

        //Turn to Beacon
        if(RobotConfiguration.ALLIANCE.equals("RED") && RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 5, -5, 1.0);  //75? degree left turn
        }
        else if (RobotConfiguration.ALLIANCE.equals("RED"))  //RED SHORT
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 9, -9, 1.0);  //90 degree right turn
        }
        else if(RobotConfiguration.ALLIANCE.equals("BLUE") && RobotConfiguration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, -5, 5, 1.0);  //75? degree left turn
        }
        else //BLUE SHORT
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, -9, 9, 1.0);  //90 degree right turn
        }

        cmds.ReadyBeaconArm(robot);

        if (RobotConfiguration.START_POSITION.equals("LONG"))
        {
            //Drive all the way up and push the button
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 36, 36, 3.0);
        }
        else
        {
            cmds.EncoderDrive(robot, RobotConfiguration.DRIVE_POWER, 24, 24, 3.0);
        }
        //Backup 3 inches to assess color
        cmds.EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER, 3, 3, 1.0);

        //wait 5 seconds before determining whether to drive forward again (wrong color)
        sleep(5000);

        //Sensing beacon will also invoke a 3 inch drive if the proper color is detected
        cmds.SenseBeacon(robot);

        //Back onto center
        cmds.EncoderDrive(robot,-RobotConfiguration.DRIVE_POWER, 33, 33, 5.0);

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
