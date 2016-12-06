package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Long with Beacon", group="Red Autonomous")
class AutonomousLongWithBeacon extends LinearOpMode //RobotCommands
{
    private final Hardware robot = new Hardware();
    private final RobotConfiguration configs = new RobotConfiguration();
    private final RobotCommands cmds = new RobotCommands();

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

        //To drive forward with distance:
        cmds.EncoderDrive(configs.DRIVE_POWER,  60,  60, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        cmds.TurnLeft(configs.TURN_POWER);
        sleep(500);

        cmds.ReadyBeaconArm();

        cmds.EncoderDrive(configs.DRIVE_POWER,  36,  40, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        cmds.SenseBeacon("RedAlliance");

        //If we were going to go for the beacon, we would leave this wait out
        //wait 10 seconds in case we cross zones
        sleep(configs.AUTO_DELAY_TIME);


        cmds.StopDriving();

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
