package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Red/Blue Basic Long without Encoders", group="Autonomous")
class AutonomousBasicLongWithTime extends LinearOpMode //**RobotCommands
{
    private final Hardware robot = new Hardware();
    private final RobotConfiguration configs = new RobotConfiguration();
    private final RobotCommands cmds = new RobotCommands();

    @Override
    public void runOpMode()
    {
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

        robot.IdentifyHardware(hardwareMap);

        cmds.InitializeHW();

         /* ******************************************************/
        // Initialize Parameters
        /* ******************************************************/
        telemetry.addData("Initialize", "Loading Parameters...");
        telemetry.update();

        configs.loadParameters();

        telemetry.addData("Initialize", "Loading Parameters Complete!");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status","Autonomous Starting...");
        telemetry.update();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        cmds.DropNewBall();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        //If we were going to go for a beacon, we would add code to move to the beacon and sense color here
        //cmds.TurnLeft(TURN_POWER);
        //sleep(1000);
        //cmds.FindBeacon();
        //cmds.ReportBeaconColor();

        //If we were going to go for the beacon, we would leave this wait out
        //wait 10 seconds in case we cross zones
        sleep(configs.AUTO_DELAY_TIME);

        //To drive forward with time:
        cmds.DriveForward(configs.DRIVE_POWER);
        sleep(configs.LONG_AUTO_DRIVE_TIME);
        // -OR
        //runtime.reset();
        //while (opModeIsActive() && (runtime.seconds() < 1.5)) {

        cmds.StopDriving();

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
