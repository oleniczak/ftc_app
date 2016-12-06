package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Red/Blue Basic Long with Encoders", group="Red/Blue Autonomous")
class AutonomousBasicLongWithDistance extends LinearOpMode //RobotCommands
{
    private Hardware robot = new Hardware();
    private RobotConfiguration configs = new RobotConfiguration();
    private RobotCommands cmds = new RobotCommands();

    @Override
    public void runOpMode()
    {
        robot.IdentifyHardware(hardwareMap);

        /* Available commands: (preface each w/ cmds.)*/
        //  Initialize();
        //  DriveForward(DRIVE_POWER)/
        //  DriveReverse(DRIVE_POWER);
        //  EncoderDrive(DRIVE_POWER, <speed>, <leftinches>, <rightinches>, <timeS>);
        //  StopDriving();
        //  TurnLeft(TURN_POWER);
        //  TurnRight(TURN_POWER)/
        //  LaunchBallAndReset(LAUNCH_POWER);
        //  DropNewBall();
        //  CalibrateGyro();
        //  FindBeacon();
        //  ReportBeaconColor();

        telemetry.addData("Status","Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();

        configs.InitializeHW();
        cmds.InitializeServos();

        waitForStart();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        cmds.DropNewBall();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        //If we were going to go for a beacon, we would add code to move to the beacon and sense color here
        //cmds.TurnLeft(TURN_POWER);
        //sleep(1000);
        //cmds.ReadyBeaconArm();
        //cmds.ReportBeaconColor();

        //If we were going to go for the beacon, we would leave this wait out
        //wait 10 seconds in case we cross zones
        sleep(configs.AUTO_DELAY_TIME);

        //To drive forward with distance:
        cmds.EncoderDrive(configs.DRIVE_POWER,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        cmds.StopDriving();

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
